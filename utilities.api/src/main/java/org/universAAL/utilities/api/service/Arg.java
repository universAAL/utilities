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

/**
 * Helper class to build arguments in a service request. Arguments are the
 * "leaf" objects at the end of a property path and can be of several types
 * depending on what you want to do with them. This class adds an additional
 * help level to using Request and Profile. Following the example in Profile,
 * thanks to Arg it could be:
 * <p/>
 * Example: Calling a Lighting service that controls LightSources of type
 * ElectricLight, find the light with URI "desiredURI" and turn it off (set to
 * brightness 0) <code>
 * <p/>Request req=new Request(new Lighting(null));
 * <p/>req.put(Path.at(Lighting.PROP_CONTROLS).to(LightSource.PROP_HAS_TYPE), Arg.type(ElectricLight.MY_URI));
 * <p/>req.put(Path.at(Lighting.PROP_CONTROLS), Arg.in(new LightSource(desiredURI)));
 * <p/>req.put(Path.at(Lighting.PROP_CONTROLS).to(LightSource.PROP_SOURCE_BRIGHTNESS), Arg.change(new Integer(0)));
 * <p/>caller.call(req);
 * </code>
 * <p/>
 *
 * @author alfiva
 *
 */
public class Arg {

	private Arg() {
		// Disallow instantiating
	}

	/**
	 * Equivalent to new Add(byValue);.
	 *
	 * @param byValue
	 *            Value to represent.
	 * @return The argument.
	 */
	public static Add add(Object byValue) {
		return new Add(byValue);
	}

	/**
	 * Equivalent to new Add(byTypeURI);.
	 *
	 * @param byTypeURI
	 *            Type to represent.
	 * @return The argument.
	 */
	public static Add add(String byTypeURI) {
		return new Add(byTypeURI);
	}

	/**
	 * Equivalent to new Change(inputNewValue);.
	 *
	 * @param byValue
	 *            Value to represent.
	 * @return The argument.
	 */
	public static Change change(Object byValue) {
		return new Change(byValue);
	}

	/**
	 * Equivalent to new Change(byTypeURI);.
	 *
	 * @param byTypeURI
	 *            Type to represent.
	 * @return The argument.
	 */
	public static Change change(String byTypeURI) {
		return new Change(byTypeURI);
	}

	/**
	 * Equivalent to new Output(byTypeURIorPLACEHOLDER);.
	 *
	 * @param byTypeURIorPLACEHOLDER
	 *            Type to represent, or placeholder URI.
	 * @return The argument.
	 */
	public static Output out(String byTypeURIorPLACEHOLDER) {
		return new Output(byTypeURIorPLACEHOLDER);
	}

	/**
	 * Equivalent to new Output(byValue);.
	 *
	 * @param byValue
	 *            Value to represent.
	 * @return The argument.
	 */
	public static Output out(Object byValue) {
		return new Output(byValue);
	}

	/**
	 * Equivalent to new Remove(byValue);.
	 *
	 * @param byValue
	 *            Value to represent.
	 * @return The argument.
	 */
	public static Remove remove(Object byValue) {
		return new Remove(byValue);
	}

	/**
	 * Equivalent to new Remove(byTypeURI);.
	 *
	 * @param byTypeURI
	 *            Type to represent.
	 * @return The argument.
	 */
	public static Remove remove(String byTypeURI) {
		return new Remove(byTypeURI);
	}

	/**
	 * Equivalent to new Typematch(byTypeURI);.
	 *
	 * @param byTypeURI
	 *            Type to represent.
	 * @return The argument.
	 */
	public static Typematch type(String byTypeURI) {
		return new Typematch(byTypeURI);
	}

	/**
	 * Equivalent to new Typematch(byValue);.
	 *
	 * @param byValue
	 *            Value to represent.
	 * @return The argument.
	 */
	public static Typematch type(Object byValue) {
		return new Typematch(byValue);
	}

	/**
	 * Equivalent to new Variable(byValue);.
	 *
	 * @param byValue
	 *            Value to represent.
	 * @return The argument.
	 */
	public static Variable in(Object byValue) {
		return new Variable(byValue);
	}

	/**
	 * Equivalent to new Variable(byTypeURI);.
	 *
	 * @param byTypeURI
	 *            Type to represent.
	 * @return The argument.
	 */
	public static Variable in(String byTypeURI) {
		return new Variable(byTypeURI);
	}

}
