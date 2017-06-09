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
import org.universAAL.middleware.context.DefaultContextPublisher;
import org.universAAL.middleware.context.owl.ContextProviderType;
import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.support.utils.context.low.Provider;

/**
 * Class that can be used to easily create a Context Publisher that can be used
 * in almost all situations. This simple Publisher extends the
 * DefaultContextPublisher included in the MW, but allows specifying everything
 * it needs in its constructor. Also because of this it can be used as well with
 * the native API to apply further restrictions and configuration over it.
 * <p/>
 * Example: A publisher that gives events about status of a Heater Actuator.
 * <code>
 * <p/>publisher = new UtilPublisher(context, DEVICE_INSTANCE_URI,
 * <p/>		ContextProviderType.controller, 
 * <p/>		new HeaterActuator(DEVICE_INSTANCE_URI), 
 * <p/>		HeaterActuator.PROP_STATUS,
 * <p/>		TypeMapper.getDatatypeURI(Boolean.class));
 * </code>
 * <p/>
 * 
 * @author alfiva
 * 
 */
public class UtilPublisher extends DefaultContextPublisher {

	/**
	 * Create a simple <b>gauge</b> Context Publisher which provided events can
	 * be of any type and is identified with the given URI.
	 * 
	 * @param context
	 *            The universAAL module context.
	 * @param uri
	 *            The URI that identifies the Context Provider.
	 */
	public UtilPublisher(ModuleContext context, String uri) {
		super(context, new Provider(uri));
	}

	/**
	 * Create a simple Context Publisher of the given type which provided events
	 * can be of any type and is identified with the given URI.
	 * 
	 * @param context
	 *            The universAAL module context.
	 * @param uri
	 *            The URI that identifies the Context Provider.
	 * @param type
	 *            The type of Context Provider, one of Gauge, Controller or
	 *            Reasoner.
	 */
	public UtilPublisher(ModuleContext context, String uri, ContextProviderType type) {
		super(context, new Provider(uri, type));
	}

	/**
	 * Create a simple Context Publisher of the given type which provided events
	 * are described by a pattern and is identified with the given URI.
	 * 
	 * @param context
	 *            The universAAL module context.
	 * @param uri
	 *            The URI that identifies the Context Provider.
	 * @param type
	 *            The type of Context Provider, one of Gauge, Controller or
	 *            Reasoner.
	 * @param pattern
	 *            Array of Patterns describing the provided events, as normally
	 *            used in the native API.
	 */
	public UtilPublisher(ModuleContext context, String uri, ContextProviderType type, ContextEventPattern[] pattern) {
		super(context, new Provider(uri, type, pattern));
	}

	/**
	 * Create a simple Context Publisher of the given type which provided events
	 * are described by the arguments and is identified with the given URI.
	 * 
	 * @param context
	 *            The universAAL module context.
	 * @param uri
	 *            The URI that identifies the Context Provider.
	 * @param type
	 *            The type of Context Provider, one of Gauge, Controller or
	 *            Reasoner.
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
	public UtilPublisher(ModuleContext context, String uri, ContextProviderType type, String subjTypeURI,
			String predicate, String objTypeURI) {
		super(context, new Provider(uri, type, subjTypeURI, predicate, objTypeURI));
	}

	/**
	 * Create a simple Context Publisher of the given type which provided events
	 * are described by the arguments and is identified with the given URI.
	 * 
	 * @param context
	 *            The universAAL module context.
	 * @param uri
	 *            The URI that identifies the Context Provider.
	 * @param type
	 *            The type of Context Provider, one of Gauge, Controller or
	 *            Reasoner.
	 * @param sub
	 *            The exact instance that the provided events subject must
	 *            equal. Null for any.
	 * @param predicate
	 *            The exact predicate that the provided events must equal. Null
	 *            for any.
	 * @param objTypeURI
	 *            The type URI that the provided events object must have. Null
	 *            for any.
	 */
	public UtilPublisher(ModuleContext context, String uri, ContextProviderType type, ManagedIndividual sub,
			String predicate, String objTypeURI) {
		super(context, new Provider(uri, type, sub, predicate, objTypeURI));
	}

}
