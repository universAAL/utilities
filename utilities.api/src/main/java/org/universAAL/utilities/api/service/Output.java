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
package org.universAAL.utilities.api.service;

import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.middleware.rdf.Resource;

/**
 * Class representing an Output-type argument in a service request. Arguments
 * are the "leaf" objects at the end of a property path and can be of several
 * types depending on what you want to do with them. An Output-type argument
 * means that you want to get in return the value represented by this argument
 * from the service you intend to call.
 *
 * @author alfiva
 *
 */
public class Output extends Value {

	/**
	 * <b>Recommended for use by SimpleRequests and
	 * SimpleProfiles</b>.SimpleValue used to define the URI (the ID) of an
	 * output. It can be used for two purposes.
	 * <p/>
	 * If you use it from a Request, you must put a newly created placeholder
	 * URI where outputs will be placed in ServiceResponses.
	 * <p/>
	 * If you use it from a Profile, you must put the type URI of the kind of
	 * Output the profile will be handling, like ManagedIndividual.MY_URI. Use
	 * only this constructor
	 *
	 * @param byTypeURIorPLACEHOLDER
	 *            In Request: An URI used by ServiceResponse to place matching
	 *            outputs. In Profile: The URI of the type that is handled by
	 *            the Profile
	 */
	public Output(String byTypeURIorPLACEHOLDER) {
		this.uri = byTypeURIorPLACEHOLDER;
		if (!Resource.isQualifiedName(byTypeURIorPLACEHOLDER)) {
			System.out
					.println("---SIMPLE UTILS TIP: You must pass a valid " + "\"MY URI\" of a ManagedIndivdual to this "
							+ "Typematch constructor if you are calling " + "this from a Profile.");
		}
	}

	/**
	 * <b>For use only by SimpleRequests</b>. Value used to define the URI (the
	 * ID) of an output.
	 * <p>
	 * DEPRECATED: The use of this constructor is discouraged as it behaves
	 * differently to other constructors of SimpleValues that take instance
	 * values. This one uses the passed value to extract its type, not to
	 * specify it as explicit output.
	 * <p>
	 * This is a helper constructor that takes an instance of the type you want
	 * and extracts its type. The value of the instance is irrelevant, it's just
	 * intended for situations in which you already have an instance which type
	 * you want to output.
	 *
	 * @param byValue
	 *            An instance of a ManagedIndividual or native type representing
	 *            the value to output in this argument, but only its type is
	 *            relevant.
	 */
	@Deprecated
	public Output(Object byValue) {
		this.uri = ManagedIndividual.getTypeURI(byValue);
	}

	/**
	 * This is only supposed to be used internally.
	 *
	 * @return The type URI/placeholder to output
	 */
	public String getURI() {
		return uri;
	}

}
