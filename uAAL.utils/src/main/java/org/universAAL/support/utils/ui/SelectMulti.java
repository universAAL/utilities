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
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.universAAL.middleware.container.utils.StringUtils;
import org.universAAL.middleware.ui.rdf.Group;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.Select;

/**
 * Class representing a Multi-selection Input UI element. Multi-selection Inputs
 * get one or many textual values from a list, whether it´s a list of
 * checkboxes, sorted column or any other way, depending on the UI renderer. The
 * input is represented as a String.
 * <p>
 * Example render:
 * <p>
 * <pre>
 * Label |  val1  |
 *       |[ val2 ]|
 *       |[ val3 ]|
 *       |  val4  |
 *       |  val5  |
 * </pre>
 * @author alfiva
 * 
 */
public class SelectMulti extends InputControl{

    /**
     * Initial selected index.
     */
    protected Integer initialValue;
    /**
     * List of options.
     */
    private List l = new ArrayList();
    
    /**
     * Generic empty constructor. The Input will be generated with default
     * values (first).
     */
    public SelectMulti(){
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
    public SelectMulti(String ref){
	setReference(ref);
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
    public SelectMulti(String ref, String label){
	setReference(ref);
	this.label=new Label(label,null);
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
     *            An array of Strings that represent the different possible
     *            options to select.
     */
    public SelectMulti(String ref, String label, String[] initialOptions){
	setReference(ref);
	this.label=new Label(label,null);
	setOptions(initialOptions);
    }

    /* (non-Javadoc)
     * @see org.universAAL.support.utils.ui.Control#create(org.universAAL.middleware.ui.rdf.Group)
     */
    public String[] create(Group group) {
	if(ref==null){
	    setReference(MY_NAMESPACE+StringUtils.createUniqueID());
	}
	model = new Select(group, label, ref, null, initialValue!=null?getOptions()[initialValue.intValue()]:null);
	((Select)model).generateChoices(getOptions());
	return ref.getThePath();
    }
    
    /**
     * Get the initial value of the selection as an index in the array of
     * options.
     * 
     * @return The index of the initial value
     */
    public Integer getInitialIndex() {
        return initialValue;
    }

    /**
     * Set the initial value of the selection as the index in the array of
     * options.
     * 
     * @param initialIndex
     *            The index of the initial value
     */
    public void setInitialIndex(Integer initialIndex) {
        this.initialValue = initialIndex;
    }

    /**
     * Get the possible options to select.
     * 
     * @return An array of Strings representing the different options to select,
     *         or null if none were set.
     */
    public String[] getOptions() {
	if(l!=null && !l.isEmpty()){
	    int i=0;
		String[] res=new String[l.size()];
	    Iterator iter=l.iterator();
	    while(iter.hasNext()){
		res[i++]=(String)iter.next();
	    }
	    return res;
	}
	return null;
    }
    
    /**
     * Add an option to the list of possible options. It will be added in the
     * last place.
     * 
     * @param option
     *            The option to add
     */
    public void addOption(String option){
	l.add(option);
    }

    /**
     * Get the possible options to select. Overrides any previous values.
     * 
     * @param options
     *            The array of Strings representing the different options to
     *            select
     */
    public void setOptions(String[] options) {
        this.l = Arrays.asList(options);
    }
    
}
