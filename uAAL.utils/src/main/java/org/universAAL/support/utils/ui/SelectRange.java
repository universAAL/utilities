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
import org.universAAL.middleware.owl.IntRestriction;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.ui.rdf.Group;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.Range;

/**
 * Class representing a Range Input UI element. Range Inputs get a numeric value
 * within a range, whether it´s a spinner, slider or any other way, depending on
 * the UI renderer. The input is represented as an Integer.
 * <p>
 * Example render:
 * <p>
 * <pre>
 * Label min[---|--------]max 
 *         initialValue
 * </pre>
 * @author alfiva
 * 
 */
public class SelectRange extends InputControl{

    /**
     * Min value.
     */
    private Integer min=Integer.valueOf(0);
    /**
     * Initial value.
     */
    private Integer initialValue=Integer.valueOf(0);
    private Integer max=Integer.valueOf(1);
    private Integer step=null;
    
    /**
     * Generic empty constructor. The Input will be generated with default
     * values (0 to 1).
     */
    public SelectRange(){
    }
    
    /**
     * Constructor with the reference of the input to be used in request and
     * response. The reference is a property path, but in this constructor it is
     * simplified as a single String (a single-property path). All other
     * properties of the input are set to defaults (0 to 1). Use method
     * setReference(String[] path) to set a path through several properties.
     * 
     * @param ref
     *            The simple reference identifying the input. Set to null to
     *            auto-generate.
     */
    public SelectRange(String ref){
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
    public SelectRange(String ref, String label){
	setReference(ref);
	this.label=new Label(label,null);
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
     * 
     * @param min
     *            The low end of the range that the value can get (inclusive)
     * @param max
     *            The high end of the range that the value can get (inclusive)
     * @param initialValue
     *            The value the range input has by default, between min and max.
     *            If it is not changed by the user this will be the value of the
     *            input in the response.
     */
    public SelectRange(String ref, String label, int min, int max, int initialValue){
	setReference(ref);
	this.label=new Label(label,null);
	setMinMaxInitial(min, max, initialValue);
    }

    /* (non-Javadoc)
     * @see org.universAAL.support.utils.ui.Control#create(org.universAAL.middleware.ui.rdf.Group)
     */
    public String[] create(Group group) {
	if(ref==null){
	    setReference(MY_NAMESPACE+StringUtils.createUniqueID());
	}
	model = new Range(group, label, ref, MergedRestriction.getAllValuesRestriction(ref.getLastPathElement(),
				new IntRestriction(min, true, max, true)),initialValue);
	if(step!=null){
	    ((Range)model).setStep(step);
	}
	return ref.getThePath();
    }
    
    /**
     * Get the minimum value of the range.
     * 
     * @return The minimum value
     */
    public Integer getMin() {
        return min;
    }

    /**
     * Set the minimum value of the range.
     * 
     * @param min
     *            The minimum value
     */
    public void setMin(int min) {
        this.min = Integer.valueOf(min);
    }

    /**
     * Get the initial value of the range, between min and max.
     * 
     * @return The initial value
     */
    public Integer getInitialValue() {
        return initialValue;
    }

    /**
     * Set the initial value of the range, between min and max.
     * 
     * @param initialValue
     *            The initial value
     */
    public void setInitialValue(int initialValue) {
        this.initialValue = Integer.valueOf(initialValue);
    }

    /**
     * Get the maximum value of the range.
     * 
     * @return The maximum value
     */
    public Integer getMax() {
        return max;
    }

    /**
     * Set the maximum value of the range.
     * 
     * @param max
     *            The maximum value
     */
    public void setMax(int max) {
        this.max = Integer.valueOf(max);
    }
    
    /**
     * Get the amount which the value increases or decreases with every step.
     * 
     * @return The step value
     */
    public Integer getStep() {
        return step;
    }

    /**
     * Set the amount which the value increases or decreases with every step.
     * 
     * @param step The step value
     */
    public void setStep(int step) {
        this.step = Integer.valueOf(step);
    }
    
    /**
     * Set the minimum and maximum values of the range, and the initial value
     * within them. The method does not check the validity, it is only checked
     * when the control is added.
     * 
     * @param min
     *            The minimum value
     * @param max
     *            The maximum value
     * @param initialValue
     *            The initial value
     */
    public void setMinMaxInitial(int min, int max, int initialValue){
	this.min = Integer.valueOf(min);
	this.max = Integer.valueOf(max);
	this.initialValue = Integer.valueOf(initialValue);
    }
    
}
