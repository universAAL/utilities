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
package org.universAAL.support.utils.ui.low;

import java.util.Locale;

import org.universAAL.middleware.owl.supply.LevelRating;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.ui.UIRequest;
import org.universAAL.middleware.ui.owl.PrivacyLevel;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.ontology.profile.User;
import org.universAAL.support.utils.ui.Control;
import org.universAAL.support.utils.ui.IContainer;
import org.universAAL.support.utils.ui.SubmitCmd;

/**
 * A helper class that lets you build UIRequests easily so you can send them
 * with your UICaller class. Just create an instance of this for the addressed
 * user and add Simple Form Controls to it. This one specifically creates a
 * Dialog, which is the typical interaction unit between applications and users.
 * Dialogs take control of the whole interaction space (e.g. occupy the full
 * screen) until they are dismissed by the application or another Dialog comes
 * in front of them. Dialogs have two groups for Controls: The main Controls
 * group is for any kind of control. The Submits group is only for Submits
 * intended to end the dialog or lead to new dialogs.
 * <p/>
 * Example: Creating a dialog for selecting a light from a list and have
 * commands for turning it on and off.
 * <p/>
 * <code>
 * <p/>Dialog d=new Dialog(user,"Light interface");
 * <p/>Out out=new Out("-","Select one of the following lights");
 * <p/>d.add(out);
 * <p/>SelectOne list=new SelectOne(LIST_URI, "Lights");
 * <p/>list.setOptions(new String[]{"ligth1","light2","light3"});
 * <p/>d.add(list);
 * <p/>SubmitCmd s1=new SubmitCmd(SUBMIT_ON,"Turn On");
 * <p/>d.addSubmit(s1);
 * <p/>SubmitCmd s2=new SubmitCmd(SUBMIT_OFF,"Turn Off");
 * <p/>d.addSubmit(s2);
 * <p/>caller.sendUIRequest(d);
 * </code>
 * <p/>
 * Notice that Simple Form Controls will be rendered in the same order as they
 * are added. Once they are added they can no longer be modified, so set all
 * their properties before adding them to the Dialog. Take into account however
 * that Simple Group controls need to be added to the Dialog BEFORE other
 * controls can be added to those Groups.
 * <p/>
 * This is not necessarily faster nor better than the usual way of doing it with
 * Form and UIRequest. It´s just an alternative way that might help those less
 * familiarized with universAAL.
 * 
 * @author alfiva
 * 
 */
public class Dialog extends UIRequest implements IContainer{

    /**
     * Use this helper class to create a UIRequest that is easy to use. This
     * Dialog extends UIRequest so you can use it with a UICaller. Default
     * values are used for priority (low) and privacy (insensible).
     * 
     * @param user
     *            The user to which the request is addressed.
     * @param title
     *            The title of the Dialog.
     */
    public Dialog(User user, String title) {
	super();
	addType(MY_URI, true);
	configInstance(user, title, (Resource)null, LevelRating.low, PrivacyLevel.insensible);
    }
    
    /**
     * Use this helper class to create a UIRequest that is easy to use. This
     * Dialog extends UIRequest so you can use it with a UICaller. This
     * constructor can get a root Resource from which references to Controls can
     * be reused. When creating a Form Control, pass it the property path in the
     * root Resource to the right value and it will be set as initial value for
     * that Control. Default values are used for priority (low) and privacy
     * (insensible).
     * 
     * @param user
     *            The user to which the request is addressed.
     * @param title
     *            The title of the Dialog.
     * @param root
     *            The Resource to use as root for the contained Form Controls.
     */
    public Dialog(User user, String title, Resource root) {
	super();
	addType(MY_URI, true);
	configInstance(user, title, root, LevelRating.low, PrivacyLevel.insensible);
    }
    
    /**
     * Use this helper class to create a UIRequest that is easy to use. This
     * Dialog extends UIRequest so you can use it with a UICaller.
     * 
     * @param user
     *            The user to which the request is addressed.
     * @param title
     *            The title of the Dialog.
     * @param priority
     *            Set a custom priority for the Dialog.
     * @param privacy
     *            Set the required privacy level for the Dialog.
     */
    public Dialog(User user, String title, LevelRating priority, PrivacyLevel privacy) {
	super();
	addType(MY_URI, true);
	configInstance(user, title, (Resource)null, priority, privacy);
    }
    
    /**
     * Use this helper class to create a UIRequest that is easy to use. This
     * Dialog extends UIRequest so you can use it with a UICaller. This
     * constructor can get a root Resource from which references to Controls can
     * be reused. When creating a Form Control, pass it the property path in the
     * root Resource to the right value and it will be set as initial value for
     * that Control.
     * 
     * @param user
     *            The user to which the request is addressed.
     * @param title
     *            The title of the Dialog.
     * @param root
     *            The Resource to use as root for the contained Form Controls.
     * @param priority
     *            Set a custom priority for the Dialog.
     * @param privacy
     *            Set the required privacy level for the Dialog.
     */
    public Dialog(User user, String title, Resource root, LevelRating priority, PrivacyLevel privacy) {
	super();
	addType(MY_URI, true);
	configInstance(user, title, root, priority, privacy);
    }
    
    /**
     * Sets the properties of the request to the right initial values specified
     * by the constructors.
     * 
     * @param user
     *            The user to which the request is addressed.
     * @param title
     *            The title of the Dialog.
     * @param root
     *            The Resource to use as root for the contained Form Controls.
     * @param priority
     *            Set a custom priority for the Dialog.
     * @param privacy
     *            Set the required privacy level for the Dialog.
     */
    private void configInstance(User user, String title, Resource root, LevelRating priority, PrivacyLevel privacy){
	props.put(PROP_ADDRESSED_USER, user);
	props.put(PROP_DIALOG_FORM, Form.newDialog(title, root));
	props.put(PROP_DIALOG_PRIORITY, priority);
	props.put(PROP_DIALOG_LANGUAGE, Locale.getDefault());
	props.put(PROP_DIALOG_PRIVACY_LEVEL, privacy);
    }
    
    /* (non-Javadoc)
     * @see org.universAAL.support.utils.ui.IContainer#add(org.universAAL.samples.ui.utils.SimpleControl)
     */
    public String[] add(Control ctrl){
	return ctrl.create(getDialogForm().getIOControls());
    }
    
    /**
     * Add a Submit Form Control to the Submit group of the Dialog. Submit group
     * is for Submits that end the dialog or lead to new dialogs.
     * 
     * @param ctrl
     *            The Submit to add
     * @return The String representing the ID to be used to identify the Submit
     *         in the response.
     */
    public String addSubmit(SubmitCmd ctrl){
	String[] ref=ctrl.create(getDialogForm().getSubmits());
	return ref[ref.length-1];
    }

}
