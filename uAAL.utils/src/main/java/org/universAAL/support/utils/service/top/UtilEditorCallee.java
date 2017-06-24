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
package org.universAAL.support.utils.service.top;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.support.utils.service.mid.UtilEditor;

/**
 * This is an abstract class for those who want to use the typical services of
 * an editor (get, add, change and remove) over an ontological service. Classes
 * extending this abstract class will be ServiceCallees which handle by default
 * these 4 services. Those considering using UtilEditor could take advantage of
 * this class if they want only to handle those 4 typical services profiles and
 * no more.
 *
 * @author alfiva
 *
 */
public abstract class UtilEditorCallee extends ServiceCallee {

	/**
	 * Namespace for auxiliary URIs used in this class.
	 */
	private String calleeNamespace;

	/**
	 * Default error response when an input parameter does not match.
	 */
	private static final ServiceResponse ERROR_INPUT = new ServiceResponse(CallStatus.serviceSpecificFailure);

	/**
	 * Default constructor of the class. Takes the same parameters needed by a
	 * UtilEditor profile method, in addition to the ModuleContext.
	 *
	 * @param context
	 *            The Module Context of universAAL
	 * @param namespace
	 *            The namespace of your server, ending with the character #
	 * @param ontologyURI
	 *            The MY_URI of the class of Service ontology you are going to
	 *            implement
	 * @param path
	 *            The property path from the root of the Service ontology
	 *            concept to the exact concept you want to manage
	 * @param editedURI
	 *            The MY_URI of the class of the concept ontology that you want
	 *            to manage, which is at the end of the property path
	 */
	public UtilEditorCallee(ModuleContext context, String namespace, String ontologyURI, String[] path,
			String editedURI) {
		super(context, UtilEditor.getServiceProfiles(namespace, ontologyURI, path, editedURI));
		this.calleeNamespace = namespace;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.universAAL.middleware.service.ServiceCallee#handleCall(org.universAAL
	 * .middleware.service.ServiceCall)
	 */
	@Override
	public ServiceResponse handleCall(ServiceCall call) {
		if (call == null) {
			return null;
		}
		String operation = call.getProcessURI();
		if (operation == null) {
			return null;
		}
		if (operation.startsWith(calleeNamespace + UtilEditor.SERVICE_GET)) {
			Object input = call.getInputValue(calleeNamespace + UtilEditor.IN_GET);
			if (input == null) {
				return ERROR_INPUT;
			}
			Resource result = executeGet((Resource) input);
			ServiceResponse response = new ServiceResponse(CallStatus.succeeded);
			response.addOutput(new ProcessOutput(calleeNamespace + UtilEditor.OUT_GET, result));
			return response;
		}

		if (operation.startsWith(calleeNamespace + UtilEditor.SERVICE_ADD)) {
			Object input = call.getInputValue(calleeNamespace + UtilEditor.IN_ADD);
			if (input == null) {
				return ERROR_INPUT;
			}
			ServiceResponse response = new ServiceResponse(CallStatus.serviceSpecificFailure);
			if (executeAdd((Resource) input)) {
				response = new ServiceResponse(CallStatus.succeeded);
			}
			return response;
		}

		if (operation.startsWith(calleeNamespace + UtilEditor.SERVICE_CHANGE)) {
			Object input = call.getInputValue(calleeNamespace + UtilEditor.IN_CHANGE);
			if (input == null) {
				return ERROR_INPUT;
			}
			ServiceResponse response = new ServiceResponse(CallStatus.serviceSpecificFailure);
			if (executeChange((Resource) input)) {
				response = new ServiceResponse(CallStatus.succeeded);
			}
			return response;
		}

		if (operation.startsWith(calleeNamespace + UtilEditor.SERVICE_REMOVE)) {
			Object input = call.getInputValue(calleeNamespace + UtilEditor.IN_REMOVE);
			if (input == null) {
				return ERROR_INPUT;
			}
			ServiceResponse response = new ServiceResponse(CallStatus.serviceSpecificFailure);
			if (executeRemove((Resource) input)) {
				response = new ServiceResponse(CallStatus.succeeded);
			}
			return response;
		}

		ServiceResponse response = new ServiceResponse(CallStatus.serviceSpecificFailure);
		response.addOutput(new ProcessOutput(ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR,
				"The service requested has not been implemented in this simple editor callee"));
		return response;
	}

	/**
	 * When a GET service request is received, this method is called
	 * automatically. The recommended purpose is to get a Resource with only a
	 * URI and return the matching complete Resource. Because it would be
	 * pointless to call this method if the client had the complete Resource.
	 * They only have the URI.
	 *
	 * @param input
	 *            The Resource object with the instance URI for which the GET is
	 *            issued. Take into account that only the instance URI is of
	 *            interest here. You can cast it to the right class as you
	 *            defined it in the constructor for the managedURI parameter.
	 * @return The complete Resource object with its full properties that is to
	 *         be returned.
	 */
	public abstract Resource executeGet(Resource input);

	/**
	 * When a ADD service request is received, this method is called
	 * automatically.
	 *
	 * @param input
	 *            The Resource object to add. You can cast it to the right class
	 *            as you defined it in the constructor for the managedURI
	 *            parameter.
	 * @return <code>true</code> if the addition succeeded.
	 */
	public abstract boolean executeAdd(Resource input);

	/**
	 * When a CHANGE service request is received, this method is called
	 * automatically.
	 *
	 * @param input
	 *            The Resource object to change. It is assumed, although that is
	 *            up to the implementation, that the Resource to change already
	 *            existed. You can cast it to the right class as you defined it
	 *            in the constructor for the managedURI parameter.
	 * @return <code>true</code> if the change succeeded.
	 */
	public abstract boolean executeChange(Resource input);

	/**
	 * When a REMOVE service request is received, this method is called
	 * automatically.
	 *
	 * @param input
	 *            The Resource object to remove. Take into account that only the
	 *            instance URI is of interest here. It is assumed, although that
	 *            is up to the implementation, that the Resource to remove
	 *            already existed. You can cast it to the right class as you
	 *            defined it in the constructor for the managedURI parameter.
	 * @return <code>true</code> if the removal succeeded.
	 */
	public abstract boolean executeRemove(Resource input);

}
