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

import java.util.Iterator;

import org.universAAL.middleware.container.utils.StringUtils;
import org.universAAL.middleware.ui.rdf.Group;
import org.universAAL.middleware.ui.rdf.Input;
import org.universAAL.middleware.ui.rdf.SubdialogTrigger;
import org.universAAL.middleware.ui.rdf.Submit;

/**
 * Class representing a SubDialog TriggerCmd UI element. Triggers are commands
 * that can be issued by the user, whether it's a button, spoken command or any
 * other way, depending on the UI renderer. Triggers differ from Submits in that
 * they can lead to SubDialogs without closing previous dialogs, which normal
 * Submits do.
 * <p>
 * Example render:
 * <p>
 *
 * <pre>
 * [Label]
 * </pre>
 *
 * @author alfiva
 *
 */
public class TriggerCmd extends SubmitCmd {

	/**
	 * Generic empty constructor. The TriggerCmd will be generated with default
	 * values (empty).
	 */
	public TriggerCmd() {
	}

	/**
	 * Constructor with the reference of the trigger to be used in request and
	 * response. The reference is a single ID String. All other properties of
	 * the input are set to defaults (empty).
	 *
	 * @param ref
	 *            The simple reference identifying the input. Set to null to
	 *            auto-generate.
	 */
	public TriggerCmd(String ref) {
		super(ref);
	}

	/**
	 * Constructor with the reference of the trigger to be used in request and
	 * response. The reference is a single ID String.
	 *
	 * @param ref
	 *            The simple reference identifying the input. Set to null to
	 *            auto-generate.
	 * @param label
	 *            The label text that identifies the trigger to the user.
	 */
	public TriggerCmd(String ref, String label) {
		super(ref, label);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.universAAL.utilities.api.ui.SubmitCmd#create(org.universAAL.
	 * middleware.ui.rdf.Group)
	 */
	public String[] create(Group group) {
		if (ref == null) {
			setReference(MY_NAMESPACE + StringUtils.createUniqueID());
		}
		SubdialogTrigger sub = new SubdialogTrigger(group, label, ref.getLastPathElement());
		if (confirmMessage != null) {
			switch (confirmType) {
			case Submit.CONFIRMATION_TYPE_OK_CANCEL:
				sub.setConfirmationOkCancel(confirmMessage);
				break;
			case Submit.CONFIRMATION_TYPE_YES_NO:
				sub.setConfirmationYesNo(confirmMessage);
				break;
			default:
				sub.setConfirmationOkCancel(confirmMessage);
				break;
			}
		}
		if (!l.isEmpty()) {
			Iterator iter = l.iterator();
			while (iter.hasNext()) {
				sub.addMandatoryInput((Input) iter.next());
			}
		}
		if (sub.needsSelection()) {
			sub.setRepeatableIDPrefix(ref.getLastPathElement());
		}
		return ref.getThePath();
	}

}
