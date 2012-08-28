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
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.ui.rdf.Group;
import org.universAAL.middleware.ui.rdf.Label;

/**
 * Class representing a Grouping of UI element. Groups round up UI elements to 
 * be logically and visually arranged , whether it´s a frame line, layout or 
 * any other way, depending on the UI renderer. <b>WARNING:</b> A Grouping must
 * be added to a SimpleUIRequest or other Group BEFORE any element can be added
 * to it. This is because the Grouping is not actually created until it's added.
 * <p>
 * Example render:
 * <p>
 * <pre>
 *   _Label________
 *  |              |
 *  | [X] Child    |
 *  | [_Child_]    |
 *  |______________|
 * </pre>
 * @author alfiva
 * 
 */
public class Grouping extends Control implements IContainer{
    
    /**
     * Resource backing up data in group.
     */
    private Resource root=null;
    /**
     * Holds the group model.
     */
    private Group model;
    
    /**
     * Generic empty constructor. The Group will be generated with default
     * values (empty).
     */
    public Grouping() {
    }
    
    /**
     * Constructor with the reference of the Group to be used in response. The
     * reference is a property path, but in this constructor it is simplified as
     * a single String (a single-property path). All other properties of the
     * input are set to defaults (empty). Use method setReference(String[] path)
     * to set a path through several properties.
     * 
     * @param ref
     *            The simple reference identifying the group. Set to null to
     *            auto-generate.
     */
    public Grouping(String ref){
	setReference(ref);
    }
    
    /**
     * Constructor with the reference of the group to be used in response. The
     * reference is a property path, but in this constructor it is simplified as
     * a single String (a single-property path). Use method
     * setReference(String[] path) to set a path through several properties.
     * 
     * @param ref
     *            The simple reference identifying the group. Set to null to
     *            auto-generate.
     * @param label
     *            The label text that identifies the group to the user.
     */
    public Grouping(String ref, String label){
	setReference(ref);
	this.label=new Label(label,null);
    }
    
    /**
     * Constructor with the reference of the group to be used in response. The
     * reference is a property path, but in this constructor it is simplified as
     * a single String (a single-property path). Use method
     * setReference(String[] path) to set a path through several properties.
     * 
     * @param ref
     *            The simple reference identifying the group. Set to null to
     *            auto-generate.
     * @param label
     *            The label text that identifies the group to the user.
     * @param root
     *            The root Resource for the child elements to be added to this
     *            group.
     */
    public Grouping(String ref, String label, Resource root){
	setReference(ref);
	this.label=new Label(label,null);
	this.root=root;
    }

    /* (non-Javadoc)
     * @see org.universAAL.support.utils.ui.Control#create(org.universAAL.middleware.ui.rdf.Group)
     */
    public String[] create(Group group) {
	if(ref==null){
	    setReference(MY_NAMESPACE+StringUtils.createUniqueID());
	}
	model=new Group(group, label, ref, null, root);
	return ref.getThePath();
    }
    
    /* (non-Javadoc)
     * @see org.universAAL.support.utils.ui.IContainer#add(org.universAAL.samples.ui.utils.SimpleControl)
     */
    public String[] add(Control ctrl){
	return ctrl.create(model);
    }

}
