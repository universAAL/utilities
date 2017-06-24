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

import org.universAAL.middleware.container.utils.StringUtils;
import org.universAAL.middleware.ui.rdf.Group;
import org.universAAL.middleware.ui.rdf.Select1;

/**
 * Class representing a Single-selection Input UI element. Single-selection
 * Inputs get a single Object value from a list, whether it's a drop-down,
 * sorted column or any other way, depending on the UI renderer. The input is
 * represented as the same type of Object stored in the list.
 * <p>
 * Example render:
 * <p>
 *
 * <pre>
 * Label [_initialValue_|V]
 *       | val2         |
 *       | val3         |
 *       | val4         |
 *       |______________|
 * </pre>
 * <p>
 * The Objects in the list are displayed in a textual form by trying to convert
 * them to a readable String, by using their .toString() method or their URI if
 * they are Resources. When retrieving the input, remember to cast to the
 * appropriate original type of Object.
 *
 * @author alfiva
 *
 */
public class SelectOne extends SelectMulti {

	/**
	 * Generic empty constructor. The Input will be generated with default
	 * values (first).
	 */
	public SelectOne() {
	}

	/**
	 * Constructor with the reference of the input to be used in request and
	 * response. The reference is a property path, but in this constructor it is
	 * simplified as a single String (a single-property path). All other
	 * properties of the input are set to defaults (first). Use method
	 * setReference(String[] path) to set a path through several properties.
	 *
	 * @param ref
	 *            The simple reference identifying the input. Set to null to
	 *            auto-generate.
	 */
	public SelectOne(String ref) {
		super(ref);
	}

	/**
	 * Constructor with the reference of the input to be used in request and
	 * response. The reference is a property path, but in this constructor it is
	 * simplified as a single String (a single-property path). Use method
	 * setReference(String[] path) to set a path through several properties.
	 *
	 * @param ref
	 *            The simple reference identifying the input. Set to null to
	 *            auto-generate.
	 * @param label
	 *            The label text that identifies the input to the user.
	 */
	public SelectOne(String ref, String label) {
		super(ref, label);
	}

	/**
	 * Constructor with the reference of the input to be used in request and
	 * response. The reference is a property path, but in this constructor it is
	 * simplified as a single String (a single-property path). All other
	 * properties of the input are set to defaults (first). Use method
	 * setReference(String[] path) to set a path through several properties.
	 *
	 * @param ref
	 *            The simple reference identifying the input. Set to null to
	 *            auto-generate.
	 * @param label
	 *            The label text that identifies the input to the user.
	 * @param initialOptions
	 *            An array of Objects that represent the different possible
	 *            options to select.
	 */
	public SelectOne(String ref, String label, Object[] initialOptions) {
		super(ref, label, initialOptions);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.universAAL.support.utils.ui.SelectMulti#create(org.universAAL.
	 * middleware.ui.rdf.Group)
	 */
	public String[] create(Group group) {
		if (ref == null) {
			setReference(MY_NAMESPACE + StringUtils.createUniqueID());
		}
		// if(initialValue==null)initialValue=Integer.valueOf(0);
		model = new Select1(group, label, ref, null,
				initialValue != null ? getOptions()[initialValue.intValue()] : null);
		((Select1) model).generateChoices(getOptions());
		// TODO: use storeUserInput for the initial value, because it seems
		// generatechoices overrides it
		return ref.getThePath();
	}

}
