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

import org.universAAL.middleware.rdf.PropertyPath;
import org.universAAL.middleware.ui.rdf.Group;
import org.universAAL.middleware.ui.rdf.Label;

/**
 * Abstract class representing a Form Control in a Simple UI request. Form
 * Controls are UI elements that can be added to Containers, be them Dialogs,
 * SubDialogs, Messages or even other Controls such as Groups or Repeats.
 * 
 * @author alfiva
 * 
 */
public abstract class Control {
    
    public static final String MY_NAMESPACE = "http://org.universAAL.ontology/SimpleUtils.owl#";
    
    protected PropertyPath ref;
    protected Label label;
    protected String help;
    protected String hint;
    
    /**
     * This method is for internal use of utils only. It creates the actual
     * official uaal control configured with the properties defined by this
     * class. Once it is created it cannot be modified.
     * 
     * @param group
     *            The UI group to which the control is being added.
     * @return An array of Strings representing the property path used to
     *         identify the control in the response. This is only of interest
     *         for Input Controls. Submits and Triggers have only one String
     *         value. If the path has not been set manually by the time this
     *         method is called, an automatic one must be generated.
     */
    public abstract String[] create(Group group);
    
    /**
     * Get the Help string associated with this control.
     * 
     * @return The Help String
     */
    public String getHelp() {
        return help;
    }

    /**
     * Set the Help string associated with this control.
     * 
     * @param help
     *            The Help String
     */
    public void setHelp(String help) {
        this.help = help;
    }

    /**
     * Get the Hint string associated with this control.
     * 
     * @return The Help String
     */
    public String getHint() {
        return hint;
    }

    /**
     * Set the Hint string associated with this control.
     * 
     * @param hint
     *            The Hint String
     */
    public void setHint(String hint) {
        this.hint = hint;
    }

    /**
     * Get the reference Property Path that identifies this control within the
     * request and the response in the case of Input Controls. For Submits, it is a single String ID.
     * 
     * @return The Array of Strings representing the reference Property path
     */
    public String[] getReference() {
        return ref.getThePath();
    }

    /**
     * Set the reference Property Path that identifies this control within the
     * request and the response in the case of Input Controls. For Submits, it is a single String ID. 
     * 
     * @param reference The String representing the reference Property path or ID
     */
    public void setReference(String reference){
	if(reference!=null)
	    ref=new PropertyPath(null, false, new String[]{reference});
	else
	    ref=null;
    }
    
    /**
     * Set the reference Property Path that identifies this control within the
     * request and the response in the case of Input Controls. For Submits, it
     * is a single String ID, so it only counts the last value.
     * 
     * @param reference
     *            The Array of Strings representing the reference Property path
     *            or ID
     */
    public void setReference(String[] reference){
	if(reference!=null)
	    ref=new PropertyPath(null, false, reference);
	else
	    ref=null;
    }
    
    /**
     * Get the Label string associated with this control.
     * 
     * @return The Label String
     */
    public String getLabel() {
        return label.getText();
    }
    
    /**
     * Set the Label string associated with this control.
     * 
     * @param txt
     *            The Label String
     */
    public void setLabel(String txt){
	label=new Label(txt,null);
    }

}
