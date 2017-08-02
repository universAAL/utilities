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
 * Helper class to build property paths in a service request. Property Paths are
 * the routes from the root service concept to the arguments of the requests,
 * through the ontology concepts. This class adds an additional help level to
 * using Request and Profile. Following the example in Profile, thanks to Path
 * it could be:
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
public class Path {
	/**
	 * Holds the value of the path.
	 */
	public String[] path;

	/**
	 * Main constructor. It can be used to type
	 * <code>new Path(firstproperty)...</code> instead of
	 * <code>Path.at(firstproperty)...</code>
	 *
	 * @param path
	 *            The first property of the path, coming from the service root
	 */
	public Path(String path) {
		this.path = new String[] { path };
	}

	/**
	 * Additional constructor if you already have a property path but want to
	 * turn it into a Path object. You can add more steps to the path
	 * afterwards.
	 *
	 * @param path
	 *            The property path, coming from the service root
	 */
	public Path(String[] path) {
		this.path = path;
	}

	/**
	 * Deprecated. Use at() instead. You save 2 letters.
	 *
	 * @param to
	 *            The first property of the path, coming from the service root
	 * @return New Path instance
	 */
	@Deprecated
	public static Path start(String to) {
		return new Path(to);
	}

	/**
	 * Return a new instance of Path starting its branch path at parameter to.
	 *
	 * @param to
	 *            The first property of the path, coming from the service root
	 * @return New Path instance
	 */
	public static Path at(String to) {
		return new Path(to);
	}

	/**
	 * Puts a new step (a new property) in the branch path. Allows successive
	 * calls to add further properties.
	 *
	 * @param to
	 *            The next property in the path, coming from a previous call to
	 *            .start
	 * @return Itself
	 */
	public Path to(String to) {
		String[] obj = new String[this.path.length + 1];
		for (int i = 0; i < this.path.length; i++) {
			obj[i] = this.path[i];
		}
		obj[obj.length - 1] = to;
		this.path = obj;
		return this;
	}

	/**
	 * Get a Path object with the given property path.
	 *
	 * @param path
	 *            The property path
	 * @return The Path object representing it
	 */
	public static Path parse(String[] path) {
		return new Path(path);
	}

	/**
	 * Get a Path object with the given property path.
	 *
	 * @param path
	 *            The single step property path
	 * @return The Path object representing it
	 */
	public static Path parse(String path) {
		return new Path(path);
	}

}
