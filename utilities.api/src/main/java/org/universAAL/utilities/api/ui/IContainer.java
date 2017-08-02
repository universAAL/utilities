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

/**
 * Interface for everything that can contain Form Controls. This can be Dialogs
 * and similar but also Group elements.
 *
 * @author alfiva
 *
 */
public interface IContainer {

	/**
	 * Add the given Simple Control UI element to this container. Once the
	 * control is added it cannot be modified. Controls are renedered in the
	 * same order they are added. Controls that are Containers and can add
	 * elements as well, like Group or Repeat, must be added BEFORE other
	 * controls can be added to them. Controls that need references, like Inputs
	 * (with property paths), or Submits (with IDs) will be given an automatic
	 * reference if none was set. This reference will be returned by this
	 * method, in the form of an Array of Strings representing the reference: a
	 * Path for Inputs, or a single String for Submits IDs.
	 *
	 * @param ctrl
	 *            The Simple Control UI element to be added.
	 * @return The Array of Strings representing the reference Property Path for
	 *         Input Controls, or with a single String for Submits IDs.
	 */
	String[] add(Control ctrl);

}
