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
import org.universAAL.middleware.ui.rdf.TextArea;

/**
 * Class representing a Text Area Input UI element. Text Area Inputs get an
 * usually long textual value, whether it´s written, spoken or any other way,
 * depending on the UI renderer. The input is represented as a String.
 * <p>
 * Example render:
 * <p>
 * <pre>
 * Label | initialValue     | 
 *       |                  |
 *       |__________________|
 * </pre>
 * @author alfiva
 * 
 */
public class Area extends Text{
    
    /**
     * Generic empty constructor. The Input will be generated with default
     * values (empty).
     */
    public Area(){
    }
    
    /**
     * Constructor with the reference of the input to be used in request and
     * response. The reference is a property path, but in this constructor it is
     * simplified as a single String (a single-property path). All other
     * properties of the input are set to defaults (empty). Use method
     * setReference(String[] path) to set a path through several properties.
     * 
     * @param ref
     *            The simple reference identifying the input. Set to null to
     *            auto-generate.
     */
    public Area(String ref){
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
    public Area(String ref, String label){
	super(ref,label);
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
     * @param initialValue
     *            The value the text input field has by default. If it is not
     *            changed by the user this will be the value of the input in the
     *            response.
     */
    public Area(String ref, String label, String initialValue){
	super(ref,label,initialValue);
    }

    /* (non-Javadoc)
     * @see org.universAAL.support.utils.ui.Text#create(org.universAAL.middleware.ui.rdf.Group)
     */
    public String[] create(Group group) {
	if(ref==null){
	    setReference(MY_NAMESPACE+StringUtils.createUniqueID());
	}
	model=new TextArea(group, label, ref, null, initialValue);
	return ref.getThePath();
    }

}
