/*
	Copyright 2008-2014 ITACA-TSB, http://www.tsb.upv.es
	Instituto Tecnologico de Aplicaciones de Comunicacion
	Avanzadas - Grupo Tecnologias para la Salud y el
	Bienestar (TSB)

	See the NOTICE file distributed with this work for additional
	information regarding copyright ownership

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

	  http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */
package org.universAAL.support.utils;

import java.util.ArrayList;
import java.util.Iterator;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextPublisher;
import org.universAAL.middleware.context.owl.ContextProvider;
import org.universAAL.middleware.context.owl.ContextProviderType;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.universAAL.middleware.ui.UIRequest;
import org.universAAL.support.utils.context.mid.UtilPublisher;

/**
 * This is a central helper class that allows you to interact with universAAL
 * from a very high level, use its main features, and doing it with few or no
 * extra classes and little code.
 * <p>
 * Not all the possible features of universAAL are accessible from it, only the
 * most typical and straight/forward, namely:
 * <li>Send Context Events
 * <li>Subscribe for Context Events
 * <li>Call Services
 * <li>Provide Services
 * <li>Request User Interaction and handle Response
 * <p>
 * And all of these without (virtually) having to implement extensions of universAAL
 * classes, which may come in handy for already existing applications which just
 * want to connect easily to universAAL. Actually some of these provided simplified
 * features use a Listener Interface-oriented approach, for which it may be
 * necessary to create additional classes for implementing them or do it in
 * existing classes, although in general it is possible to create the
 * implementation in the method calls themselves without much complication.
 * <p>
 * Notice that for allowing this, this universAAL class takes care of instantiating
 * and maintaining all needed universAAL wrapper classes, so for as long as you don't
 * call <code>terminate()</code> it will keep hold of these resources. This also
 * means that the performance might not be as polite as it could if you handled
 * your own implementations of universAAL wrappers with the native API or the other
 * sublayers of this utilities API. The purpose of this universAAL class was never to
 * improve the performance but to simplify the coding.
 * <p>
 * To use the class methods, just instantiate it with a valid universAAL
 * ModuleContext. For instance, from an Activator: <code>
 * <pre>
    private universAAL u;

    public void start(BundleContext bcontext) throws Exception {
	Activator.osgiContext = bcontext;
	Activator.context = OSGiContainer.THE_CONTAINER
		.registerModule(new Object[] { bcontext });
	u = new universAAL(context);
	...
    }
 * </pre>
 * </code>
 * <p>
 * Remember to release the resources when you stop your application (like in
 * <code>Activator.stop()</code> method) by calling <code>terminate()</code>.
 *
 * @author alfiva
 *
 */
public class UAAL {

	/**
	 * The single Context Publisher universAAL wrapper class used by this class to
	 * publish all Context Events.
	 */
	private ContextPublisher publisher;
	/**
	 * A list of all Context Subscriber universAAL wrapper classes used by this class
	 * to receive subscribed Context Events. The reason for having a list of
	 * subscribers is that it simplifies the association between the subscribed
	 * pattern and the handling to perform associated to it.
	 */
	private ArrayList<WrapperC> subscribers;
	/**
	 * The single Service Caller universAAL wrapper class used by this class to call
	 * all Service Requests.
	 */
	private ServiceCaller caller;
	/**
	 * A list of all Service Callee universAAL wrapper classes used by this class to
	 * handle calls to provided Service Profiles. The reason for having a list
	 * of callees is that it simplifies the association between the provided
	 * profile and the handling to perform associated to it.
	 */
	private ArrayList<WrapperS> callees;
	/**
	 * The single UI Caller universAAL wrapper class used by this class to send all UI
	 * Requests.
	 */
	private WrapperUI requester;
	/**
	 * The universAAL Module Context.
	 */
	private ModuleContext context;

	/**
	 * This constructor just assigns the Module Context: the rest of resources
	 * used by this class are not initialized. They will be automatically
	 * initialized as they are needed when you use the universAAL features methods of
	 * the class, so you don't have to do anything else. All resources will be
	 * maintained until you call <code>terminate()</code>.
	 * <p>
	 * Although it is possible to have as many instances of this class as
	 * desired (no restrictions are set about it), it is recommended to maintain
	 * just a single(ton) instance of it, and make it accessible to the rest of
	 * your application code.
	 *
	 * @param context
	 *            The universAAL Module Context.
	 */
	public UAAL(ModuleContext context) {
		this.context = context;
	}

	/**
	 * Sends a Context Event.
	 * <p>
	 * universAAL helper automatically creates its own internal Context Publisher if
	 * this is the first time the method is called. If the passed event contains
	 * ContextProvider information, it is used to instantiate the Publisher.
	 * Otherwise the missing information is automatically generated: The
	 * Publisher is described as either a Gauge or a Controller depending on if
	 * you are providing Services (you called method <code>provideS()</code>
	 * before). The URI of the Provider is then set to
	 * <i>http://ontology.universAAL.org/SimpleUAAL
	 * .owl#ContextEventsProvider</i> and it cannot be changed. Currently, the
	 * pattern that describes the provided events of this provider is empty,
	 * which means it can publish any type of event. Notice this Provider info
	 * is set ONLY THE FIRST TIME and cannot be changed by later calls.
	 *
	 * @param e
	 *            The Context Event to send.
	 */
	public void sendC(ContextEvent e) {
		// TODO: Handle multiple provided patterns
		ContextProvider cp = e.getProvider();
		// Because we are building the provider here, it will not be the same
		// object
		// This would fail the match in bus and not send the event. Remove it so
		// it is set by the bus:
		if (cp != null)
			e.changeProperty(ContextEvent.PROP_CONTEXT_PROVIDER, null);
		// This only happens the first time:
		if (publisher == null) {
			// Yeah lots of ifs and returns, ugly but easy to read
			if (cp != null) {
				ContextProviderType cpt = cp.getProviderType();
				if (cpt != null) {
					ContextEventPattern[] cpe = cp.getProvidedEvents();
					if (cpe != null) {
						publisher = new UtilPublisher(context, cp.getURI(), cpt, cpe);
						publisher.publish(e);
						return;
					}
					publisher = new UtilPublisher(context, cp.getURI(), cpt, (String) null, null, null);
					publisher.publish(e);
					return;
				}
				publisher = new UtilPublisher(context, cp.getURI(), (callees != null && !callees.isEmpty())
						? ContextProviderType.controller : ContextProviderType.gauge, (String) null, null, null);
				publisher.publish(e);
				return;
			}
			publisher = new UtilPublisher(context,
					"http://ontology.universAAL.org/SimpleUAAL.owl#ContextEventsProvider",
					(callees != null && !callees.isEmpty()) ? ContextProviderType.controller
							: ContextProviderType.gauge,
					(String) null, null, null);

		}
		publisher.publish(e);
	}

	/**
	 * Calls a Service with a Service Request.
	 * <p>
	 * universAAL helper automatically creates its own internal Default Service Caller
	 * if this is the first time the method is called. The call to the service
	 * is synchronous: this method returns the response of the call straight
	 * from Service Bus. You will have to deal with it.
	 * <p>
	 * Remember that the simplified API in this library (
	 * <code>org.universAAL.support.utils.service</code> packages) can assist
	 * you in creating Service Requests and handling Service Responses.
	 *
	 * @param r
	 *            The Service Request describing the Service to call.
	 * @return The Service Response to the call returned by the Service Bus.
	 *
	 * @see org.universAAL.support.utils.service.low.Request
	 */
	public ServiceResponse callS(ServiceRequest r) {
		if (caller == null) {
			caller = new DefaultServiceCaller(context);
		}
		return caller.call(r);
	}

	/**
	 * Requests User Interaction and specifies how the response would be
	 * handled.
	 * <p>
	 * universAAL helper automatically creates its own internal UI Caller if this is
	 * the first time the method is called. An implementation of
	 * {@link IUIListener} must be passed that will handle the response to the
	 * UI Request that is being sent. The response contains the user input to
	 * the forms described in the UI Request, so the listener must be ready to
	 * work with the fields, controls, submits and identifiers used in the
	 * request. There can only be a single listener associated to the UI Caller
	 * at a time, which means this method cannot be called again with a new
	 * listener until the previous response has been received and processed by
	 * the old listener. Another solution is using always the same instance of
	 * the listener, which should then be ready to handle any possible UI
	 * response to your application.
	 * <p>
	 * Remember that the simplified API in this library (
	 * <code>org.universAAL.support.utils.ui</code> packages) can assist you in
	 * creating UI Requests.
	 *
	 * @param ui
	 *            The UI request with the output to display to the user.
	 * @param l
	 *            The listener that will handle the response to that UI request.
	 * @see org.universAAL.support.utils.ui.low.Dialog
	 * @see org.universAAL.support.utils.ui.low.Message
	 * @see org.universAAL.support.utils.ui.low.SubDialog
	 */
	public void requestUI(UIRequest ui, IUIListener l) {
		if (requester == null) {
			requester = new WrapperUI(context);
		}
		requester.setListener(l);
		requester.sendUIRequest(ui);
	}

	/**
	 * Subscribes for Context Events and specifies how the events would be
	 * handled.
	 * <p>
	 * universAAL helper automatically creates a new own internal Context Subscriber
	 * each time this method is called. An implementation of {@link ICListener}
	 * must be passed that will handle the reception of an event that matches
	 * the passed patterns. The listener is associated to the patterns: All
	 * events that match the patterns are received by the listener (regardless
	 * of any other calls to this method). The listener remains active and
	 * associated to the patterns until <code>terminate()</code> is called.
	 * Since every call to this method creates a new subscriber, you should be
	 * careful with how many times you call this method. Usually, a limited
	 * number of subscriptions is needed. Remember that you can combine
	 * different patterns to be handled by a single listener.
	 * <p>
	 * Remember that the simplified API in this library (
	 * <code>org.universAAL.support.utils.context</code> packages) can assist
	 * you in creating Context Event Patterns.
	 *
	 * @param p
	 *            An array of Context Event Patterns describing the Context
	 *            Events that will be handled by the listener.
	 * @param l
	 *            The listener that will handle the received event that matches
	 *            the patterns.
	 * @see org.universAAL.support.utils.context.Pattern
	 */
	public void subscribeC(ContextEventPattern[] p, ICListener l) {
		if (subscribers == null) {
			subscribers = new ArrayList<WrapperC>(5);
		}
		subscribers.add(new WrapperC(context, p, l));
	}

	/**
	 * Registers Service Profiles and specifies how the calls to these profiles
	 * would be handled.
	 * <p>
	 * universAAL helper automatically creates a new own internal Service Callee each
	 * time this method is called. An implementation of {@link ISListener} must
	 * be passed that will handle the call request that matches the provided
	 * profiles. The listener is associated to the profiles: All call requests
	 * that match the profiles are received by the listener (regardless of any
	 * other calls to this method). The listener remains active and associated
	 * to the profiles until <code>terminate()</code> is called. Since every
	 * call to this method creates a new callee, you should be careful with how
	 * many times you call this method. Usually, a limited number of callees is
	 * needed.
	 * <p>
	 * Remember that the simplified API in this library (
	 * <code>org.universAAL.support.utils.service</code> packages) can assist
	 * you in creating Service Profiles.
	 *
	 * @param p
	 *            An array of Service Profiles describing the provided services
	 *            that will be handled by the listener.
	 * @param l
	 *            The listener that will handle the received call request that
	 *            matches the provided service profiles.
	 * @see org.universAAL.support.utils.service.low.Profile
	 */
	public void provideS(ServiceProfile[] p, ISListener l) {
		if (callees == null) {
			callees = new ArrayList<WrapperS>(5);
		}
		callees.add(new WrapperS(context, p, l));
	}

	/**
	 * Closes all universAAL wrapper classes created by this universAAL helper until now (by
	 * calling their <code>.close()</code> method) and eliminates them. The
	 * reference to the Module Context is maintained, however. This allows to
	 * perform new calls to the universAAL features methods, which will create new
	 * universAAL wrappers in the helper.
	 */
	public void terminate() {
		if (publisher != null) {
			publisher.close();
			publisher = null;
		}
		if (caller != null) {
			caller.close();
			caller = null;
		}
		if (requester != null) {
			requester.close();
			requester = null;
		}

		if (subscribers != null) {
			Iterator<WrapperC> iter = subscribers.iterator();
			while (iter.hasNext()) {
				((WrapperC) iter.next()).close();
			}
			subscribers.clear();
			subscribers = null;
		}

		if (callees != null) {
			Iterator<WrapperS> iter = callees.iterator();
			while (iter.hasNext()) {
				((WrapperS) iter.next()).close();
			}
			callees.clear();
			callees = null;
		}
	}

}
