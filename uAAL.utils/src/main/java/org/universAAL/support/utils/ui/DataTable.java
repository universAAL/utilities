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
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.ui.rdf.Group;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.Repeat;

/**
 * Class representing a Data Table UI element. Data Tables show data from a
 * repetitive data model composed by equally structured entries, whether it's a
 * table, list of groups or any other way, depending on the UI renderer.
 * <p>
 * A Data Table is composed by the Data Model backing up the actual content and
 * an Entry Model defining what is shown from each entry and how. The Entry
 * Model is like a Group, any Control (except Submits or other Data Tables) can
 * be added to it. Each Control added must reference a Path from the entries in
 * Data Model, and must not have an initial value. The Entry Model will be
 * repeated for each entry in the Data Model, and each of its Controls
 * initialized with the data of that Entry found in the given reference Path of
 * the Control.
 * <p>
 * <b>WARNING:</b> A Data Table is like a Group, it must be added to a
 * SimpleUIRequest or a Group BEFORE any element can be added to its Entry
 * Model. This is because the Tables�s Entry Model is not actually created until
 * it's added. The Data Model however must be set BEFORE the Data Table is
 * added, thus the order of operations is the following: 1) Create Data Table.
 * 2) Set Data Model. 3) Add Data Table to parent. 4) Add Controls to Entry
 * Model.
 * <p>
 * There are two ways of constructing Data Tables: Inferring the Data Model
 * entries from the parent's root resource or setting the Data Model entries
 * explicitly.
 * <p>
 * Example render:
 * <p>
 * 
 * <pre>
 * Label ---------------------------------------
 *      | Control1Label     | Control2Label     |
 *       ---------------------------------------
 *      | Control1Render(1) | Control2Render(1) | 
 *       ---------------------------------------
 *      | Control1Render(2) | Control2Render(2) | 
 *       ---------------------------------------
 *      | Control1Render(3) | Control2Render(3) | 
 *       ---------------------------------------
 *                         ...
 * </pre>
 * 
 * @author alfiva
 * 
 */
public class DataTable extends Control implements IContainer {

	/**
	 * Holds the data model.
	 */
	private Group model;
	/**
	 * Holds the items repeated.
	 */
	private List l = new ArrayList();
	/**
	 * Table parameter.
	 */
	private Boolean deletable = Boolean.FALSE;
	/**
	 * Table parameter.
	 */
	private Boolean editable = Boolean.FALSE;
	/**
	 * Table parameter.
	 */
	private Boolean expandable = Boolean.FALSE;

	/**
	 * Generic empty constructor. The Data table will be generated with default
	 * values (empty).
	 */
	public DataTable() {
	}

	/**
	 * Constructor with the reference of the Table Data Model to be used . The
	 * reference is a property path, but in this constructor it is simplified as
	 * a single String (a single-property path). All other properties of the
	 * input are set to defaults (empty). Use method setReference(String[] path)
	 * to set a path through several properties.
	 * 
	 * If a root Resource is used in the parent of the Table (to which it is
	 * added) then the values reachable through the reference Path from the root
	 * will be used as Data Model for the Table.
	 * <p/>
	 * Example:
	 * <p/>
	 * <code>
	 * <br/>Location root=new Location(URI1);
	 * <br/>Location adj=new Location(URI2);
	 * <br/>root.addAdjacentLocation(adj);
	 * <br/>Dialog d=new Dialog(user,"Title",root);
	 * <br/>DataTable rp=new DataTable(Location.PROP_IS_ADJACENT_TO);
	 * </code>
	 * <p/>
	 * take into account that the referenced property must allow cardinality > 1
	 * 
	 * @param ref
	 *            The simple reference identifying the Table Data Model from the
	 *            parent root. Set to null to auto-generate and not use a Data
	 *            Model from the root.
	 */
	public DataTable(String ref) {
		setReference(ref);
	}

	/**
	 * Constructor with the reference of the Table Data Model to be used . The
	 * reference is a property path, but in this constructor it is simplified as
	 * a single String (a single-property path). All other properties of the
	 * input are set to defaults (empty). Use method setReference(String[] path)
	 * to set a path through several properties.
	 * 
	 * If a root Resource is used in the parent of the Table (to which it is
	 * added) then the values reachable through the reference Path from the root
	 * will be used as Data Model for the Table.
	 * <p/>
	 * Example:
	 * <p/>
	 * <code>
	 * <br/>Location root=new Location(URI1);
	 * <br/>Location adj=new Location(URI2);
	 * <br/>root.addAdjacentLocation(adj);
	 * <br/>Dialog d=new Dialog(user,"Title",root);
	 * <br/>DataTable rp=new DataTable(Location.PROP_IS_ADJACENT_TO);
	 * </code>
	 * <p/>
	 * take into account that the referenced property must allow cardinality > 1
	 * 
	 * @param ref
	 *            The simple reference identifying the Table Data Model from the
	 *            parent root. Set to null to auto-generate and not use a Data
	 *            Model from the root.
	 * @param label
	 *            The label text that identifies the Table to the user.
	 */
	public DataTable(String ref, String label) {
		setReference(ref);
		this.label = new Label(label, null);
	}

	/**
	 * Constructor without reference of Table Data Model. Instead, the entries
	 * are passed explicitly, as an array of Resource elements. All of these
	 * elements in the array (the entries) must be of the same class, because
	 * their properties will be used by the controls in the Entry Model, for
	 * every entry. An automatic reference for the Table will be generated but
	 * only to identify it in the response.
	 * 
	 * @param label
	 *            The label text that identifies the Table to the user.
	 * @param initialEntries
	 *            Array of Resources representing the entries of the Data Model.
	 */
	public DataTable(String label, Resource[] initialEntries) {
		this.label = new Label(label, null);
		setEntries(initialEntries);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.universAAL.support.utils.ui.Control#create(org.universAAL.middleware.
	 * ui.rdf.Group)
	 */
	public String[] create(Group group) {
		if (ref == null) {
			setReference(MY_NAMESPACE + StringUtils.createUniqueID());
		}
		Repeat r = new Repeat(group, label, ref, null, l.isEmpty() ? null : l);
		if (!deletable.booleanValue()) {
			r.banEntryDeletion();
		}
		if (!editable.booleanValue()) {
			r.banEntryEdit();
		}
		if (!expandable.booleanValue()) {
			r.banEntryAddition();
		}
		model = new Group(r, null, null, null, null);
		return ref.getThePath();
	}

	/**
	 * Add the given Simple Control UI element to the Table Entry Model. Submits
	 * and Data Tables ARE NOT ALLOWED and will not be added. The initial value
	 * of the Control MUST be null, because the value will be set to the Data
	 * Model entry property specified by the reference. The Control�s reference
	 * path must follow a valid path starting where the Table�s own path
	 * finished. Example:
	 * 
	 * <pre>
	 * (Root) --PROP1-- (ResA) --PROP2-- (ResB)
	 *      _____________
	 *       Table Path      _____________
	 *                        Control Path
	 * </pre>
	 * 
	 * If the Table used explicit entries instead of a reference path, then the
	 * reference of the Controls must start with (or be) one of the properties
	 * of the type of Resource used for the explicit entries.
	 * <p>
	 * Once the control is added it cannot be modified. Controls are renedered
	 * in the same order they are added. Controls that are Containers and can
	 * add elements as well, like Group or DataTables, must be added BEFORE
	 * other controls can be added to them. Controls that need references, like
	 * Inputs (with property paths), or Submits (with IDs) will be given an
	 * automatic reference if none was set. This reference will be returned by
	 * this method, in the form of an Array of Strings representing the
	 * reference: a Path for Inputs, or a single String for Submits IDs.
	 * 
	 * @param ctrl
	 *            The Simple Control UI element to be added.
	 * @return The Array of Strings representing the reference Property Path for
	 *         Input Controls, or with a single String for Submits IDs. Returns
	 *         null if attempted to add a SubmitCmd or DataTable.
	 */
	public String[] add(Control ctrl) {
		if ((ctrl instanceof SubmitCmd) || (ctrl instanceof DataTable)) {
			return null;
		}
		return ctrl.create(model);
	}

	/**
	 * Check if entries can be removed by the user.
	 * 
	 * @return If entries can be removed by the user.
	 */
	public Boolean isDeletable() {
		return deletable;
	}

	/**
	 * Set if entries can be removed by the user.
	 * 
	 * @param deletable
	 *            If entries can be removed by the user.
	 */
	public void setDeletable(Boolean deletable) {
		this.deletable = deletable;
	}

	/**
	 * Check if entries can be edited by the user.
	 * 
	 * @return If entries can be edited by the user.
	 */
	public Boolean isEditable() {
		return editable;
	}

	/**
	 * Set if entries can be edited by the user.
	 * 
	 * @param editable
	 *            If entries can be edited by the user.
	 */
	public void setEditable(Boolean editable) {
		this.editable = editable;
	}

	/**
	 * Check if entries can be added by the user.
	 * 
	 * @return If entries can be added by the user.
	 */
	public Boolean isExpandable() {
		return expandable;
	}

	/**
	 * Set if entries can be added by the user.
	 * 
	 * @param expandable
	 *            If entries can be added by the user.
	 */
	public void setExpandable(Boolean expandable) {
		this.expandable = expandable;
	}

	/**
	 * Get the entries of the Data Model backing up this Data Table.
	 * 
	 * @return An array of Resources representing the entries in the Data Model.
	 */
	public Resource[] getEntries() {
		if (l != null && !l.isEmpty()) {
			int i = 0;
			Resource[] res = new Resource[l.size()];
			Iterator iter = l.iterator();
			while (iter.hasNext()) {
				res[i++] = (Resource) iter.next();
			}
			return res;
		}
		return null;
	}

	/**
	 * Add an entry to the Data Model backing up this Data Table. Must be of the
	 * same class of those already added.
	 * 
	 * @param option
	 *            The entry to add to the Data Model.
	 */
	public void addEntry(Resource entry) {
		l.add(entry);
	}

	/**
	 * Set the entries of the Data Model backing up this Data Table. Overrides
	 * any previous values.
	 * 
	 * @param entries
	 *            An array of Resources representing the entries the Data Model.
	 */
	public void setEntries(Resource[] entries) {
		this.l = Arrays.asList(entries);
	}

}
