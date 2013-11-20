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

/**
 * Class representing a Type-match argument in a service request. Arguments are
 * the "leaf" objects at the end of a property path and can be of several types
 * depending on what you want to do with them. An Type-match argument means that
 * values in the property path must be of the type represented by this argument.
 * 
 * @author alfiva
 * 
 */
public class Typematch extends Value {

    /**
     * <b>Recommended for use by SimpleRequests and SimpleProfiles</b>.
     * Value used to define types to match as arguments of a
     * Request.
     * 
     * @param byTypeURI
     *            The URI of the type that you want to match (you can get it
     *            with NameOfClass.MY_URI)
     */
    public Typematch(String byTypeURI) {
	this.uri = byTypeURI;
	if (!Resource.isQualifiedName(byTypeURI)) {
	    System.out
		    .println("---SIMPLE UTILS TIP: You must pass a valid " +
			    "\"MY URI\" of a ManagedIndivdual to this "
			    + "Typematch constructor");
	}
    }

    /**
     * Value used to define types to match as arguments of a
     * Request.
     * <p>
     * DEPRECATED: The use of this constructor is discouraged as it behaves
     * differently to other constructors of SimpleValues that take instance
     * values. This one uses the passed value to extract its type, not to
     * specify it as explicit instance.
     * <p>
     * This is a helper constructor that takes an instance of the
     * type you want and extracts its type. The value of the instance is
     * irrelevant, it's just intended for situations in which you already have
     * an instance which type you want to match.
     * 
     * @param byValue
     *            An instance of which type you want to match.
     */
    @Deprecated
    public Typematch(Object byValue) {
	this.uri = ManagedIndividual.getTypeURI(byValue);
    }

    /**
     * This is only supposed to be used internally.
     * 
     * @return The type URI to match
     */
    public String getURI() {
	return uri;
    }

}
