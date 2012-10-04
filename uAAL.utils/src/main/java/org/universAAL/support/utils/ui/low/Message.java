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
 * Message, which is a single-time interaction unit between applications and
 * users. Messages take control of a portion of the interaction space (e.g. a
 * pop-up appearing in the screen) until they are dismissed by the application,
 * another Dialog comes in front of them, or are finished with the default
 * commands set by the system (e.g. Cancel). Messages have two groups for
 * Controls: The main Controls group is for any kind of control. The Submits
 * group is only for Submits intended to end the message.
 * <p/>
 * Example: Creating a Message for alerting the user that a light is off.
 * <p/>
 * <code>
 * <p/>Message m=new Message(user,"Light interface","Light1 has been turned off");
 * <p/>caller.sendUIRequest(m);
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
public class Message extends UIRequest implements IContainer{
    
    /**
     * Use this helper class to create a UIRequest that is easy to use. This
     * Message extends UIRequest so you can use it with a UICaller.
     * Default values are used for priority (low) and privacy (insensible).
     * 
     * @param user
     *            The user to which the request is addressed.
     * @param title
     *            The title of the Message.
     * @param message
     *            The default output message to be shown to the user.
     */
    public Message(User user, String title, String message) {
	super();
	addType(MY_URI, true);
	configInstance(user, title, message, LevelRating.low, PrivacyLevel.insensible);
    }
   
    /**
     * Use this helper class to create a UIRequest that is easy to use. This
     * Message extends UIRequest so you can use it with a UICaller.
     * 
     * @param user
     *            The user to which the request is addressed.
     * @param title
     *            The title of the Message.
     * @param message
     *            The default output message to be shown to the user.
     * @param priority
     *            Set a custom priority for the Message.
     * @param privacy
     *            Set the required privacy level for the Message.
     */
    public Message(User user, String title, String message, LevelRating priority, PrivacyLevel privacy) {
	super();
	addType(MY_URI, true);
	configInstance(user, title, message, priority, privacy);
    }
    
    /**
     * Sets the properties of the request to the right initial values specified
     * by the constructors.
     * 
     * @param user
     *            The user to which the request is addressed.
     * @param title
     *            The title of the Message.
     * @param message
     *            The default output message to be shown to the user.
     * @param priority
     *            Set a custom priority for the Message.
     * @param privacy
     *            Set the required privacy level for the Message.
     */
    private void configInstance(User user, String title, String message, LevelRating priority, PrivacyLevel privacy){
	props.put(PROP_ADDRESSED_USER, user);
	props.put(PROP_DIALOG_FORM, Form.newMessage(title, message));
	props.put(PROP_DIALOG_PRIORITY, priority);
	props.put(PROP_DIALOG_LANGUAGE, Locale.getDefault());
	props.put(PROP_DIALOG_PRIVACY_LEVEL, privacy);
    }
    
    /* (non-Javadoc)
     * @see oorg.universAAL.support.utils.ui.IContainer#add(org.universAAL.samples.ui.utils.SimpleControl)
     */
    public String[] add(Control ctrl){
	return ctrl.create(getDialogForm().getIOControls());
    }
    
    /**
     * Add a Submit Form Control to the Submit group of the Message. Submit
     * group is for Submits that end the message. By default two Submits are
     * added by the system, Delete and Preserve.
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
    
    /**
     * Add a hidden object so it is sent within the UI request, but not shown to
     * the user. When the UI response is being handled by the UI caller, this
     * hidden input can be retrieved by calling
     * <code>uiresponse.getUserInput(new String[]{ref});</code> , being
     * <code>ref</code> the one you used in this method.
     * 
     * @param ref
     *            The reference you will use to access the hidden object later
     *            from the response
     * @param hidden
     *            The object you want to hide
     */
    public void addHidden(String ref, Object hidden){
	this.getDialogForm().getData().setPropertyPath(new String[]{ref}, hidden);
    }

}
