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
package org.universAAL.utilities.api.ui;

import org.universAAL.middleware.ui.rdf.Input;

/**
 * Abstract class representing an Input Form Control in a Simple UI request.
 * Input Form Controls are UI elements that can be filled with a value by the
 * user and later recovered in a response. All Inputs need a reference Property
 * Path for this.
 *
 * @author alfiva
 *
 */
public abstract class InputControl extends Control {

	/**
	 * Holds the input model.
	 */
	protected Input model;

	/**
	 * Because Inputs can be mandatory when a Submit is activated, these have to
	 * be represented by a model to be referenced by the Submit.
	 *
	 * @return The model of the Input.
	 */
	public Input getModel() {
		return model;
	}

}
