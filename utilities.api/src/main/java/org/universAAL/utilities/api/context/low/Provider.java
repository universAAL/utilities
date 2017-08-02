/*
	Copyright 2008 ITACA-SABIEN, http://www.sabien.upv.es
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
package org.universAAL.utilities.api.context.low;

import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.owl.ContextProvider;
import org.universAAL.middleware.context.owl.ContextProviderType;
import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.utilities.api.context.Pattern;

/**
 * Class that can be used to create a Context Provider descriptor with the most
 * commonly used types of simple Context Event Patterns. The provided
 * constructors allow to automatically give the Provider the typical
 * restrictions over the basic mandatory triple of the provided context events:
 * the subject, the predicate and the object, with the possibility of leaving
 * them blank (no restriction) by using null. Because the class extends
 * ContextProvider it can be used as well in the native API, and the other way
 * around to use the native API to apply further restrictions over the Provider.
 *
 * @author alfiva
 *
 */
public class Provider extends ContextProvider {

	/**
	 * Simple constructor to create a <b>gauge</b> Context Provider with no
	 * restrictions on the provided events. This means the provided events can
	 * be of any type.
	 *
	 * @param uri
	 *            The URI that identifies the Context Provider.
	 */
	public Provider(String uri) {
		super(uri);
		this.setType(ContextProviderType.gauge);
		this.setProvidedEvents(new ContextEventPattern[] { new ContextEventPattern() });
	}

	/**
	 * Simple constructor to create a Context Provider of a given type with no
	 * restrictions on the provided events. This means the provided events can
	 * be of any type.
	 *
	 * @param uri
	 *            The URI that identifies the Context Provider.
	 * @param type
	 *            The type of Context Provider, one of Gauge, Controller or
	 *            Reasoner
	 */
	public Provider(String uri, ContextProviderType type) {
		super(uri);
		this.setType(type != null ? type : ContextProviderType.gauge);
		this.setProvidedEvents(new ContextEventPattern[] { new ContextEventPattern() });
	}

	/**
	 * Simple constructor to create a Context Provider of a given type with the
	 * given restrictions on the provided events.
	 *
	 * @param uri
	 *            The URI that identifies the Context Provider.
	 * @param type
	 *            The type of Context Provider, one of Gauge, Controller or
	 *            Reasoner.
	 * @param events
	 *            Array of Patterns describing the provided events, as normally
	 *            used in the native API.
	 */
	public Provider(String uri, ContextProviderType type, ContextEventPattern[] events) {
		super(uri);
		this.setType(type != null ? type : ContextProviderType.gauge);
		this.setProvidedEvents(events);
	}

	/**
	 * Simple constructor to create a Context Provider of a given type with the
	 * given restrictions on the subject, the predicate, and the object of the
	 * provided events.
	 *
	 * @param uri
	 *            The URI that identifies the Context Provider.
	 * @param type
	 *            The type of Context Provider, one of Gauge, Controller or
	 *            Reasoner
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
	public Provider(String uri, ContextProviderType type, String subjTypeURI, String predicate, String objTypeURI) {
		super(uri);
		this.setType(type != null ? type : ContextProviderType.gauge);
		this.setProvidedEvents(new ContextEventPattern[] { new Pattern(subjTypeURI, predicate, objTypeURI) });
	}

	/**
	 * Simple constructor to create a Context Provider of a given type with the
	 * given restrictions on the subject, the predicate, and the object of the
	 * provided events.
	 *
	 * @param uri
	 *            The URI that identifies the Context Provider.
	 * @param type
	 *            The type of Context Provider, one of Gauge, Controller or
	 *            Reasoner
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
	public Provider(String uri, ContextProviderType type, ManagedIndividual subj, String predicate, String objTypeURI) {
		super(uri);
		this.setType(type != null ? type : ContextProviderType.gauge);
		this.setProvidedEvents(new ContextEventPattern[] { new Pattern(subj, predicate, objTypeURI) });
	}

}
