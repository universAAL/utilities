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
package org.universAAL.support.utils.service;

import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.TypeMapper;

/**
 * Class representing a Variable-Input-type argument in a service request.
 * Arguments are the "leaf" objects at the end of a property path and can be of
 * several types depending on what you want to do with them. A
 * Variable-Input-type argument means that you intend to call a service that
 * "handles" the value represented by this argument. The value of the argument
 * is passed onto the service so it can "handle" it.
 *
 * @author alfiva
 *
 */
public class Variable extends Value {
	/**
	 * Holds the value of the argument.
	 */
	private Object value;

	/**
	 * <b>Recommended for use by SimpleRequests</b>. Value used to define values
	 * to be used as variable inputs in arguments of a Request or Profile. If
	 * this constructor is used by Simple Profiles with a specific instance
	 * value the input is not treated as a variable reference but as a explicit
	 * required input.
	 *
	 * @param byValue
	 *            An instance of a ManagedIndividual or native type representing
	 *            the value that must be passed as variable input to the service
	 */
	public Variable(Object byValue) {
		this.isByURI = false;
		this.value = byValue;
		this.uri = ManagedIndividual.getTypeURI(byValue);
	}

	/**
	 * <b>Recommended for use by SimpleProfiles</b>. Value used to define values
	 * to be used as variable inputs in arguments of a Profile.
	 *
	 * @param byTypeURI
	 *            A type URI of a ManagedIndividual or native type representing
	 *            the type of value that must be passed as variable input to the
	 *            service. However if the parameter is not a valid URI, it will
	 *            be understood as an instance of a String, as if it used the
	 *            "byValue" constructor instead.
	 */
	public Variable(String byTypeURI) {
		this.isByURI = true;
		this.uri = byTypeURI;
		this.value = Resource.getResource(byTypeURI, Resource.generateAnonURI());

		if (this.value == null) {
			if (TypeMapper.isRegisteredDatatypeURI(byTypeURI)) {
				this.value = createEmptyInstance(byTypeURI);
			} else {
				// An arbitrary String, not an URI, intended use is like byValue
				this.isByURI = false;
				this.uri = TypeMapper.getDatatypeURI(String.class);
				this.value = byTypeURI;
			}
		}
	}

	/**
	 * This is only supposed to be used internally.
	 *
	 * @return The actual value of the variable
	 */
	public Object getObject() {
		return value;
	}

	/**
	 * This is only supposed to be used internally.
	 *
	 * @return The type URI of the variable
	 */
	public String getURI() {
		return uri;
	}

}
