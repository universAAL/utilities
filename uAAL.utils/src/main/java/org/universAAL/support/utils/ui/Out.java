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
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.SimpleOutput;

/**
 * Class representing a Text Output UI element. Text Output show a textual value,
 * whether it´s displayed, played or any other way, depending on the UI renderer.
 * The output is represented as a String.
 * <p>
 * Example render:
 * <p>
 * <pre>
 * Label <b>Value</b>
 * <pre>
 * @author alfiva
 * 
 */
public class Out extends Control{

    private String value;
    
    /**
     * Generic empty constructor. The Output will be generated with default
     * values (empty).
     */
    public Out(){
    }
    
    /**
     * Constructor with the output value. All other properties of the input are
     * set to defaults (empty).
     * 
     * @param value
     *            The output value to be displayed.
     */
    public Out(String value){
	this.value=value;
    }
    
    /**
     * Constructor with the output value.
     * 
     * @param label
     *            The label text that identifies the output to the user.
     * @param value
     *            The output value to be displayed.
     */
    public Out(String label, String value){
	this.label=new Label(label,null);
	this.value=value;
    }
    
    /**
     * Constructor with the reference of the output to be used in request. The
     * reference is a property path, but in this constructor it is simplified as
     * a single String (a single-property path). For outputs, references are
     * only used in conjunction with initial root Resources. Use method
     * setReference(String[] path) to set a path through several properties.
     * 
     * @param ref
     *            The simple reference identifying the input. Set to null to
     *            auto-generate.
     * @param label
     *            The label text that identifies the output to the user.
     * @param value
     *            The output value to be displayed.
     */
    public Out(String ref, String label, String value){
	setReference(ref);
	this.label=new Label(label,null);
	this.value=value;
    }

    /* (non-Javadoc)
     * @see org.universAAL.samples.ui.utils.SimpleControl#create(org.universAAL.middleware.ui.rdf.Group)
     */
    public String[] create(Group group) {
	if(ref==null){
	    setReference(MY_NAMESPACE+StringUtils.createUniqueID());
	}
	new SimpleOutput(group, label, ref, value);
	return ref.getThePath();
    }
    
    /**
     * Get the output value to be displayed.
     * 
     * @return The output value
     */
    public String getValue() {
        return value;
    }

    /**
     * Set the output value to be displayed.
     * 
     * @param value
     *            The output value
     */
    public void setValue(String value) {
        this.value = value;
    }

}
