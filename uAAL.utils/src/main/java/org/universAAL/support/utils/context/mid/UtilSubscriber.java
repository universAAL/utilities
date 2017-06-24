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
package org.universAAL.support.utils.context.mid;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextSubscriber;
import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.support.utils.context.Pattern;

/**
 * This abstract class can be extended to create a simple Context Subscriber.
 * This simple Subscriber extends the normal ContextSubscriber, but allows
 * specifying everything it needs in its constructor. Also because of this it
 * can be used as well with the native API to apply further restrictions and
 * configuration over it. Your class that extends this will receive the
 * specified events in the handleContextEvent method.
 * <p/>
 * Example: A subscriber that listens for events of status of Heater Actuators.
 * <code>
 * <p/>subscriber = new SubscriberExample(context, HeaterActuator.MY_URI,
 * <p/>		HeaterActuator.PROP_STATUS, null);
 * </code>
 * <p/>
 *
 * @author alfiva
 *
 */
public abstract class UtilSubscriber extends ContextSubscriber {

	/**
	 * Create a simple Context Subscriber that will listen to the events
	 * matching what is specified in the constructor.
	 *
	 * @param context
	 *            The universAAL module context.
	 * @param subjTypeURI
	 *            The type URI that the provided events subject must have. Null
	 *            for any.
	 * @param predicate
	 *            The exact predicate that the provided events must equal. Null
	 *            for any.
	 * @param objTypeURI
	 *            The type URI that the provided events object must have. Null
	 *            for any.
	 */
	protected UtilSubscriber(ModuleContext context, String subjTypeURI, String predicate, String objTypeURI) {
		super(context, new ContextEventPattern[] { new Pattern(subjTypeURI, predicate, objTypeURI) });
	}

	/**
	 * Create a simple Context Subscriber that will listen to the events
	 * matching what is specified in the constructor.
	 *
	 * @param context
	 *            The universAAL module context.
	 * @param subj
	 *            The exact instance that the provided events subject must
	 *            equal. Null for any.
	 * @param predicate
	 *            The exact predicate that the provided events must equal. Null
	 *            for any.
	 * @param objTypeURI
	 *            The type URI that the provided events object must have. Null
	 *            for any.
	 */
	protected UtilSubscriber(ModuleContext context, ManagedIndividual subj, String predicate, String objTypeURI) {
		super(context, new ContextEventPattern[] { new Pattern(subj, predicate, objTypeURI) });
	}

	/**
	 * Create a simple Context Subscriber that will listen to the events
	 * matching what is specified in the constructor.
	 *
	 * @param context
	 *            The universAAL module context.
	 * @param subj
	 *            The exact instance that the event subject must equal. Null for
	 *            any.
	 * @param predicate
	 *            The exact predicate that the event must equal. Null for any.
	 * @param obj
	 *            The exact instance that the event object must equal. Null for
	 *            any.
	 */
	protected UtilSubscriber(ModuleContext context, ManagedIndividual subj, String predicate, Object obj) {
		super(context, new ContextEventPattern[] { new Pattern(subj, predicate, obj) });
	}

	/**
	 * Create a simple Context Subscriber that will listen to the events
	 * matching what is specified in the constructor.
	 *
	 * @param context
	 *            The universAAL module context.
	 * @param subjTypeURI
	 *            The type URI that the event subject must have. Null for any.
	 * @param predicate
	 *            The exact predicate that the event must equal. Null for any.
	 * @param obj
	 *            The exact instance that the event object must equal. Null for
	 *            any.
	 */
	protected UtilSubscriber(ModuleContext context, String subjTypeURI, String predicate, Object obj) {
		super(context, new ContextEventPattern[] { new Pattern(subjTypeURI, predicate, obj) });
	}

}
