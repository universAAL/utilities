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
package org.universAAL.support.utils.context;

import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.rdf.Resource;

/**
 * Class that can be used to create the most commonly used types of simple
 * Context Event Patterns. The provided constructors allow to automatically
 * generate the typical restrictions over the basic mandatory triple of the
 * context event: the subject, the predicate and the object, with the
 * possibility of leaving them blank (no restriction) by using null. Because the
 * class extends ContextEventPattern it can be used as well in the native API,
 * and the other way around to use the native API to apply further restrictions
 * over the Pattern.
 *
 * @author alfiva
 *
 */
public class Pattern extends ContextEventPattern {
	/**
	 * Helper error message.
	 */
	private static final String MSG_STR = "---SIMPLE UTILS TIP: You must "
			+ "pass a valid \"MY URI\" of a ManagedIndivdual to this " + "UtilSubscriber constructor, or null.";
	/**
	 * Helper error message.
	 */
	private static final String MSG_PRED = "---SIMPLE UTILS TIP: You must "
			+ "pass a valid \"URI\" of a Property to this " + "UtilSubscriber constructor, or null.";
	/**
	 * Helper error message.
	 */
	private static final String MSG_INST = "---SIMPLE UTILS TIP: You must "
			+ "pass a valid ManagedIndivdual or DataType to this " + "UtilSubscriber constructor, or null.";

	/**
	 * Simple constructor to create a Context Event Pattern with restrictions on
	 * the type of subject, the predicate, and the type of object. Any of these
	 * can be null if no restriction is desired over that concept.
	 *
	 * @param subjTypeURI
	 *            The type URI that the event subject must have. Null for any.
	 * @param predicate
	 *            The exact predicate that the event must equal. Null for any.
	 * @param objTypeURI
	 *            The type URI that the event object must have. Null for any.
	 */
	public Pattern(String subjTypeURI, String predicate, String objTypeURI) {
		super();
		if (subjTypeURI != null) {
			if (Resource.isQualifiedName(subjTypeURI)) {
				this.addRestriction(
						MergedRestriction.getAllValuesRestriction(ContextEvent.PROP_RDF_SUBJECT, subjTypeURI));
			} else {
				System.out.println(MSG_STR);
			}
		}
		if (predicate != null) {
			if (Resource.isQualifiedName(predicate)) {
				this.addRestriction(
						MergedRestriction.getFixedValueRestriction(ContextEvent.PROP_RDF_PREDICATE, predicate));
			} else {
				System.out.println(MSG_PRED);
			}
		}
		if (objTypeURI != null) {
			if (Resource.isQualifiedName(objTypeURI)) {
				this.addRestriction(
						MergedRestriction.getAllValuesRestriction(ContextEvent.PROP_RDF_OBJECT, objTypeURI));
			} else {
				System.out.println(MSG_STR);
			}
		}
	}

	/**
	 * Simple constructor to create a Context Event Pattern with restrictions on
	 * the type of subject, the predicate, and the exact object. Any of these
	 * can be null if no restriction is desired over that concept.
	 *
	 * @param subjTypeURI
	 *            The type URI that the event subject must have. Null for any.
	 * @param predicate
	 *            The exact predicate that the event must equal. Null for any.
	 * @param obj
	 *            The exact instance that the event object must equal. Null for
	 *            any.
	 */
	public Pattern(String subjTypeURI, String predicate, Object obj) {
		super();
		if (subjTypeURI != null) {
			if (Resource.isQualifiedName(subjTypeURI)) {
				this.addRestriction(
						MergedRestriction.getAllValuesRestriction(ContextEvent.PROP_RDF_SUBJECT, subjTypeURI));
			} else {
				System.out.println(MSG_STR);
			}
		}
		if (predicate != null) {
			if (Resource.isQualifiedName(predicate)) {
				this.addRestriction(
						MergedRestriction.getFixedValueRestriction(ContextEvent.PROP_RDF_PREDICATE, predicate));
			} else {
				System.out.println(MSG_PRED);
			}
		}
		if (obj != null) {
			String uri = ManagedIndividual.getTypeURI(obj);
			if (uri != null && Resource.isQualifiedName(uri)) {
				this.addRestriction(MergedRestriction.getFixedValueRestriction(ContextEvent.PROP_RDF_OBJECT, obj));
			} else {
				System.out.println(MSG_INST);
			}
		}
	}

	/**
	 * Simple constructor to create a Context Event Pattern with restrictions on
	 * the exact subject, the predicate, and the type of object. Any of these
	 * can be null if no restriction is desired over that concept.
	 *
	 * @param subj
	 *            The exact instance that the event subject must equal. Null for
	 *            any.
	 * @param predicate
	 *            The exact predicate that the event must equal. Null for any.
	 * @param objTypeURI
	 *            The type URI that the event object must have. Null for any.
	 */
	public Pattern(ManagedIndividual subj, String predicate, String objTypeURI) {
		super();
		if (subj != null) {
			String uri = ManagedIndividual.getTypeURI(subj);
			if (uri != null && Resource.isQualifiedName(uri)) {
				this.addRestriction(MergedRestriction.getFixedValueRestriction(ContextEvent.PROP_RDF_SUBJECT, subj));
			} else {
				System.out.println(MSG_INST);
			}
		}
		if (predicate != null) {
			if (Resource.isQualifiedName(predicate)) {
				this.addRestriction(
						MergedRestriction.getFixedValueRestriction(ContextEvent.PROP_RDF_PREDICATE, predicate));
			} else {
				System.out.println(MSG_PRED);
			}
		}
		if (objTypeURI != null) {
			if (Resource.isQualifiedName(objTypeURI)) {
				this.addRestriction(
						MergedRestriction.getAllValuesRestriction(ContextEvent.PROP_RDF_OBJECT, objTypeURI));
			} else {
				System.out.println(MSG_STR);
			}
		}
	}

	/**
	 * Simple constructor to create a Context Event Pattern with restrictions on
	 * the exact subject, the predicate, and the exact object. Any of these can
	 * be null if no restriction is desired over that concept.
	 *
	 * @param subj
	 *            The exact instance that the event subject must equal. Null for
	 *            any.
	 * @param predicate
	 *            The exact predicate that the event must equal. Null for any.
	 * @param obj
	 *            The exact instance that the event object must equal. Null for
	 *            any.
	 */
	public Pattern(ManagedIndividual subj, String predicate, Object obj) {
		super();
		if (subj != null) {
			String uri = ManagedIndividual.getTypeURI(subj);
			if (uri != null && Resource.isQualifiedName(uri)) {
				this.addRestriction(MergedRestriction.getFixedValueRestriction(ContextEvent.PROP_RDF_SUBJECT, subj));
			} else {
				System.out.println(MSG_INST);
			}
		}
		if (predicate != null) {
			if (Resource.isQualifiedName(predicate)) {
				this.addRestriction(
						MergedRestriction.getFixedValueRestriction(ContextEvent.PROP_RDF_PREDICATE, predicate));
			} else {
				System.out.println(MSG_PRED);
			}
		}
		if (obj != null) {
			String uri = ManagedIndividual.getTypeURI(obj);
			if (uri != null && Resource.isQualifiedName(uri)) {
				this.addRestriction(MergedRestriction.getFixedValueRestriction(ContextEvent.PROP_RDF_OBJECT, obj));
			} else {
				System.out.println(MSG_INST);
			}
		}
	}
}
