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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.universAAL.middleware.container.utils.StringUtils;
import org.universAAL.middleware.ui.rdf.Group;
import org.universAAL.middleware.ui.rdf.Input;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.Submit;
import org.universAAL.support.utils.service.Path;

/**
 * Class representing a Submit UI element. Submits are commands that can be issued by the user,
 * whether it's a button, spoken command or any other way, depending on the UI renderer.
 * <p>
 * Example render:
 * <p>
 * <pre>
 * [Label]
 * </pre>
 * @author alfiva
 * 
 */
public class SubmitCmd extends Control{
    
    /**
     * Confirmation message for submit.
     */
    protected String confirmMessage=null;
    /**
     * Type of confirmation message.
     */
    protected int confirmType=Submit.CONFIRMATION_TYPE_OK_CANCEL;
    /**
     * List of required mandatory inputs.
     */
    protected List l = new ArrayList();
    /**
     * Hidden object associated with the submit
     */
    protected Object h = null;
    /**
     * Auxiliary property URI for the path to reach the hidden object.
     */
    public static final String HIDDENREF = MY_NAMESPACE+"hiddenRef";
    
    /**
     * Generic empty constructor. The Submit will be generated with default
     * values (empty).
     */
    public SubmitCmd(){
    }
    
    /**
     * Constructor with the reference of the submit to be used in request and
     * response. The reference is a single ID String. All other properties of
     * the input are set to defaults (empty).
     * 
     * @param ref
     *            The simple reference identifying the input. Set to null to
     *            auto-generate.
     */
    public SubmitCmd(String ref){
	setReference(ref);
    }
    
    /**
     * Constructor with the reference of the submit to be used in request and
     * response. The reference is a single ID String.
     * 
     * @param ref
     *            The simple reference identifying the input. Set to null to
     *            auto-generate.
     * @param label
     *            The label text that identifies the submit to the user.
     */
    public SubmitCmd(String ref, String label){
	setReference(ref);
	this.label=new Label(label,null);
    }

    /* (non-Javadoc)
     * @see org.universAAL.support.utils.ui.Control#create(org.universAAL.middleware.ui.rdf.Group)
     */
    public String[] create(Group group) {
	if(ref==null){
	    setReference(MY_NAMESPACE+StringUtils.createUniqueID());
	}
	Submit sub=new Submit(group, label, ref.getLastPathElement());
	if(confirmMessage!=null){
	    switch (confirmType) {
	    case Submit.CONFIRMATION_TYPE_OK_CANCEL:
		sub.setConfirmationOkCancel(confirmMessage);
		break;
	    case Submit.CONFIRMATION_TYPE_YES_NO:
		sub.setConfirmationYesNo(confirmMessage);
		break;
	    default :
		sub.setConfirmationOkCancel(confirmMessage);
		break;
	    }
	}
	if(!l.isEmpty()){
	    Iterator iter=l.iterator();
	    while(iter.hasNext()){
		sub.addMandatoryInput((Input)iter.next());
	    }
	}
	if(h!=null){
	    Path hiddenPath=Path.parse(ref.getThePath());
	    sub.getFormObject().getData().setPropertyPath(hiddenPath.to(HIDDENREF).path, h, true);
	}
	return ref.getThePath();
    }
    
    /**
     * Indicates that the InputControl passed as parameter must be filled with a
     * value by the user before this Submit is pressed.
     * 
     * @param input
     *            The InputControl to make mandatory for this Submit.
     */
    public void addMandatoryInput(InputControl input){
	Input in=input.getModel();
	l.add(in);
    }
    
    /**
     * Associate an object to a submit so it is sent within the UI request, but
     * not shown to the user. When the UI response is being handled by the UI
     * caller, this hidden input can be retrieved by calling
     * <code>uiresponse.getUserInput( new String[] { REFSUBMIT, SubmitCmd.HIDDENREF });</code>
     * <p>
     * This is useful when you are building a list of items in a form, each of
     * which has a Submit associated to them. You can use this method to know,
     * later when handling the response, which item of the list had its Submit
     * pressed. Tip: to do this it's useful that all the references of these
     * listed submits start with the same string, so you can detect any of them
     * when handling the response, and then get the exact submission ID for use
     * as REFSUBMIT in the example above.
     * 
     * @param hidden
     *            The InputControl to make mandatory for this Submit.
     */
    public void setHiddenObject(Object hidden){
	h=hidden;
    }

    /**
     * Get the confirmation message that will appear when this Submit is
     * selected by the user. Confirmation is for avoiding accidental selection
     * of important Submits.
     * 
     * @return The confirmation message.
     */
    public String getConfirmMessage() {
        return confirmMessage;
    }

    /**
     * Set the confirmation message that will appear when this Submit is
     * selected by the user. Confirmation is for avoiding accidental selection
     * of important Submits.
     * 
     * @param confirmMessage
     *            The confirmation message.
     */
    public void setConfirmMessage(String confirmMessage) {
        this.confirmMessage = confirmMessage;
    }
    
    /**
     * Set the confirmation message that will appear when this Submit is
     * selected by the user. Confirmation is for avoiding accidental selection
     * of important Submits.
     * 
     * @param confirmMessage
     *            The confirmation message.
     * @param confirmType
     *            The type of confirmation. One of
     *            org.universAAL.middleware.ui.rdf
     *            .Submit.CONFIRMATION_TYPE_OK_CANCEL (0) or
     *            org.universAAL.middleware
     *            .ui.rdf.Submit.CONFIRMATION_TYPE_YES_NO (1)
     */
    public void setConfirmMessage(String confirmMessage, int confirmType) {
        this.confirmMessage = confirmMessage;
        this.confirmType = confirmType;
    }

    /**
     * Get the type of confirmation message.
     * 
     * @return The type of confirmation. One of org.universAAL.middleware.ui.rdf
     *         .Submit.CONFIRMATION_TYPE_OK_CANCEL (0) or
     *         org.universAAL.middleware .ui.rdf.Submit.CONFIRMATION_TYPE_YES_NO
     *         (1)
     */
    public int getConfirmType() {
        return confirmType;
    }

    /**
     * Set the type of confirmation message.
     * 
     * @param confirmType
     *            The type of confirmation. One of
     *            org.universAAL.middleware.ui.rdf
     *            .Submit.CONFIRMATION_TYPE_OK_CANCEL (0) or
     *            org.universAAL.middleware
     *            .ui.rdf.Submit.CONFIRMATION_TYPE_YES_NO (1)
     */
    public void setConfirmType(int confirmType) {
        this.confirmType = confirmType;
    }

}
