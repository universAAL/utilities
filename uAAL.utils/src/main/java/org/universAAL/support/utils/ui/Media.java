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
package org.universAAL.support.utils.ui;

import org.universAAL.middleware.ui.rdf.Group;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.MediaObject;

/**
 * Class representing a Multimedia Output UI element. Multimedia Output can be
 * of different types and their display depends completely on the UI renderer (a
 * voice renderer cannot display an image). The output is represented as a
 * String URL in the Resource server.
 * <p>
 * Example render:
 * <p>
 *
 * <pre>
 *        __________
 * Label |          |
 *       |    :)    |
 *       |__________|
 * </pre>
 *
 * @author alfiva
 *
 */
public class Media extends Control {

	/**
	 * Type of media.
	 */
	private String type = "IMG";
	/**
	 * URL to media in resource server.
	 */
	private String url;

	/**
	 * Generic empty constructor. The Output will be generated with default
	 * values (empty).
	 */
	public Media() {
	}

	/**
	 * Constructor with only the url. All other properties of the input are set
	 * to defaults (empty image).
	 *
	 * @param url
	 *            The URL to the value to be displayed, from the Resource
	 *            server.
	 */
	public Media(String url) {
		this.url = url;
	}

	/**
	 * Constructor with the URL value.
	 *
	 * @param label
	 *            The label text that identifies the output to the user.
	 * @param url
	 *            The URL to the value to be displayed, from the Resource
	 *            server.
	 */
	public Media(String label, String url) {
		this.label = new Label(label, null);
		this.url = url;
	}

	/**
	 * Constructor with the URL value.
	 *
	 * @param label
	 *            The label text that identifies the output to the user.
	 * @param url
	 *            The URL to the value to be displayed, from the Resource
	 *            server.
	 * @param type
	 *            The type of media element. By default it's IMG.
	 */
	public Media(String label, String url, String type) {
		this.label = new Label(label, null);
		this.url = url;
		this.type = type;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.universAAL.support.utils.ui.Control#create(org.universAAL.middleware.
	 * ui.rdf.Group)
	 */
	public String[] create(Group group) {
		new MediaObject(group, label, type, url);
		return new String[] {};
	}

	/**
	 * Get the URL to the the value to be displayed, from the Resource server.
	 *
	 * @return The URL
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Set the URL to the the value to be displayed, from the Resource server.
	 *
	 * @param url
	 *            The URL
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Get the type of media element of the value.
	 *
	 * @return The media type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Set the type of media element of the value.
	 *
	 * @param type
	 *            The media type
	 */
	public void setType(String type) {
		this.type = type;
	}

}
