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
package org.universAAL.support.utils.service.mid;

import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.owl.OntologyManagement;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.owl.Service;
import org.universAAL.middleware.service.owls.process.ProcessInput;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.universAAL.support.utils.service.Add;
import org.universAAL.support.utils.service.Arg;
import org.universAAL.support.utils.service.Change;
import org.universAAL.support.utils.service.Output;
import org.universAAL.support.utils.service.Remove;
import org.universAAL.support.utils.service.Variable;
import org.universAAL.support.utils.service.low.Profile;
import org.universAAL.support.utils.service.low.Request;

/**
 * This is a helper class for those who want to use the typical services of an
 * editor (get, add, change and remove) over an ontological service. It provides
 * methods for automatically generating service profiles and service requests
 * that can be used in ServiceCallees and ServiceCallers. If they are both with
 * he same parameters in both sides, the services are guaranteed to match.
 *
 * @author alfiva
 *
 */
public class UtilEditor {
	/**
	 * Service suffix.
	 */
	public static final String SERVICE_GET = "servEditorGet";
	/**
	 * Service suffix.
	 */
	public static final String SERVICE_ADD = "servEditorAdd";
	/**
	 * Service suffix.
	 */
	public static final String SERVICE_CHANGE = "servEditorChange";
	/**
	 * Service suffix.
	 */
	public static final String SERVICE_REMOVE = "servEditorRemove";
	/**
	 * Argument suffix.
	 */
	public static final String FAKE_URI = "placeholderURI";
	/**
	 * Argument suffix.
	 */
	public static final String IN_GET = "inputEditorGet";
	/**
	 * Argument suffix.
	 */
	public static final String OUT_GET = "outputEditorGet";
	/**
	 * Argument suffix.
	 */
	public static final String IN_ADD = "inputEditorAdd";
	/**
	 * Argument suffix.
	 */
	public static final String IN_CHANGE = "inputEditorChange";
	/**
	 * Argument suffix.
	 */
	public static final String IN_REMOVE = "inputEditorRemove";

	/**
	 * Gives you the 4 typical service profiles of an editor service: Get, Add,
	 * Change and Remove. When handling requests in you Callee, you can use the
	 * references to services and arguments URIs prepending
	 * <code>namespace</code> to UtilEditor constants.
	 * <p>
	 * Example:
	 * <p>
	 * <code>
	 * new SCallee(context, getServiceProfiles("http://ontology.universAAL.org/ProfilingServer.owl#", ProfilingService.MY_URI, Path.start(ProfilingService.PROP_CONTROLS).path, Profilable.MY_URI))
	 * </code>
	 * <p>
	 *
	 * @param namespace
	 *            The namespace of your server, ending with the character #. You
	 *            can optionally add some prefix after the # if you use
	 *            UtilEditor more than once in the same Callee.
	 * @param ontologyURI
	 *            The MY_URI of the class of Service ontology you are going to
	 *            implement
	 * @param path
	 *            The property path from the root of the Service ontology
	 *            concept to the exact concept you want to manage
	 * @param editedURI
	 *            The MY_URI of the class of the concept ontology that you want
	 *            to manage, which is at the end of the property path
	 * @return An array with the 4 typical service profiles
	 */
	public static ServiceProfile[] getServiceProfiles(String namespace, String ontologyURI, String[] path,
			String editedURI) {

		ServiceProfile[] profiles = new ServiceProfile[4];
		// This, and the usage of Arg.s do not work when editedURI are of
		// abstract ont classes. Because they cannot be instantiated.
		// Resource argument = OntologyManagement.getInstance().getResource(
		// editedURI, FAKE_URI);

		// Get
		Profile prof1 = new Profile(
				(Service) OntologyManagement.getInstance().getResource(ontologyURI, namespace + SERVICE_GET));
		// prof1.put(path, Arg.in(argument), namespace + IN_GET);
		ProcessInput input1 = new ProcessInput(namespace + IN_GET);
		input1.setParameterType(editedURI);
		input1.setCardinality(1, 1);
		MergedRestriction restr1 = MergedRestriction.getFixedValueRestriction(path[path.length - 1],
				input1.asVariableReference());
		prof1.service.addInstanceLevelRestriction(restr1, path);
		prof1.service.getProfile().addInput(input1);
		// prof1.put(path, Arg.out(argument), namespace + OUT_GET);
		ProcessOutput output = new ProcessOutput(namespace + OUT_GET);
		output.setParameterType(editedURI);
		prof1.service.getProfile().addOutput(output);
		prof1.service.getProfile().addSimpleOutputBinding(output, path);
		profiles[0] = prof1.getTheProfile();

		// Add
		Profile prof2 = new Profile(
				(Service) OntologyManagement.getInstance().getResource(ontologyURI, namespace + SERVICE_ADD));
		// prof2.put(path, Arg.add(argument), namespace + IN_ADD);
		ProcessInput input2 = new ProcessInput(namespace + IN_ADD);
		input2.setParameterType(editedURI);
		input2.setCardinality(1, 1);
		prof2.service.getProfile().addInput(input2);
		prof2.service.getProfile().addAddEffect(path, input2.asVariableReference());
		profiles[1] = prof2.getTheProfile();

		// Change
		Profile prof3 = new Profile(
				(Service) OntologyManagement.getInstance().getResource(ontologyURI, namespace + SERVICE_CHANGE));
		// prof3.put(path, Arg.change(argument), namespace + IN_CHANGE);
		ProcessInput input3 = new ProcessInput(namespace + IN_CHANGE);
		input3.setCardinality(1, 1);
		input3.setParameterType(editedURI);
		prof3.service.getProfile().addInput(input3);
		prof3.service.getProfile().addChangeEffect(path, input3.asVariableReference());
		profiles[2] = prof3.getTheProfile();

		// Remove
		Profile prof4 = new Profile(
				(Service) OntologyManagement.getInstance().getResource(ontologyURI, namespace + SERVICE_REMOVE));
		// prof4.put(path, Arg.remove(argument), namespace + IN_REMOVE);
		ProcessInput input4 = new ProcessInput(namespace + IN_REMOVE);
		input4.setParameterType(editedURI);
		input4.setCardinality(1, 1);
		prof4.service.getProfile().addInput(input4);
		MergedRestriction restr4 = MergedRestriction.getFixedValueRestriction(path[path.length - 1],
				input4.asVariableReference());
		prof4.service.addInstanceLevelRestriction(restr4, path);
		prof4.service.getProfile().addRemoveEffect(path);
		profiles[3] = prof4.getTheProfile();

		return profiles;
	}

	/**
	 * Gives you the typical GET service request for editor services. If the
	 * editor service also used UtilEditor the match is guaranteed.
	 *
	 * @param ontologyURI
	 *            The MY_URI of the class of Service ontology you want to call
	 * @param path
	 *            The property path from the root of the Service ontology
	 *            concept to the exact concept you want to manage
	 * @param argIn
	 *            Value representing the input you want to pass as parameter.
	 *            The editor GET service will return you the full Resource with
	 *            the URI of this argument.
	 * @param argOut
	 *            The returned value of the editor GET service will be placed in
	 *            the URI represented by this Output. Look for it there in the
	 *            response.
	 * @return The ServiceRequest that will call the matching GET service of an
	 *         editor
	 */
	public static ServiceRequest requestGet(String ontologyURI, String[] path, Variable argIn, Output argOut) {
		Request req = new Request((Service) OntologyManagement.getInstance().getResource(ontologyURI, null));
		req.put(path, argIn);
		req.put(path, argOut);
		return req;
	}

	/**
	 * Gives you the typical GET service request for editor services. If the
	 * editor service also used UtilEditor the match is guaranteed.
	 *
	 * @param ontologyURI
	 *            The MY_URI of the class of Service ontology you want to call
	 * @param path
	 *            The property path from the root of the Service ontology
	 *            concept to the exact concept you want to manage
	 * @param in
	 *            Object representing the input you want to pass as parameter.
	 *            The editor GET service will return you the full Resource with
	 *            the URI of this argument.
	 * @param out
	 *            The returned value of the editor GET service will be placed in
	 *            this URI. Look for it there in the response.
	 * @return The ServiceRequest that will call the matching GET service of an
	 *         editor
	 */
	public static ServiceRequest requestGet(String ontologyURI, String[] path, Object in, String out) {
		Request req = new Request((Service) OntologyManagement.getInstance().getResource(ontologyURI, null));
		req.put(path, Arg.in(in));
		req.put(path, Arg.out(out));
		return req;
	}

	/**
	 * Gives you the typical ADD service request for editor services. If the
	 * editor service also used UtilEditor the match is guaranteed.
	 *
	 * @param ontologyURI
	 *            The MY_URI of the class of Service ontology you want to call
	 * @param path
	 *            The property path from the root of the Service ontology
	 *            concept to the exact concept you want to manage
	 * @param argAdd
	 *            Add representing the input you want to pass as parameter. The
	 *            editor ADD service will add the full Resource passed in this
	 *            argument.
	 * @return The ServiceRequest that will call the matching ADD service of an
	 *         editor
	 */
	public static ServiceRequest requestAdd(String ontologyURI, String[] path, Add argAdd) {
		Request req = new Request((Service) OntologyManagement.getInstance().getResource(ontologyURI, null));
		req.put(path, argAdd);
		return req;
	}

	/**
	 * Gives you the typical ADD service request for editor services. If the
	 * editor service also used UtilEditor the match is guaranteed.
	 *
	 * @param ontologyURI
	 *            The MY_URI of the class of Service ontology you want to call
	 * @param path
	 *            The property path from the root of the Service ontology
	 *            concept to the exact concept you want to manage
	 * @param add
	 *            Object representing the input you want to pass as parameter.
	 *            The editor ADD service will add the full Resource passed in
	 *            this argument.
	 * @return The ServiceRequest that will call the matching ADD service of an
	 *         editor
	 */
	public static ServiceRequest requestAdd(String ontologyURI, String[] path, Object add) {
		Request req = new Request((Service) OntologyManagement.getInstance().getResource(ontologyURI, null));
		req.put(path, Arg.add(add));
		return req;
	}

	/**
	 * Gives you the typical CHANGE service request for editor services. If the
	 * editor service also used UtilEditor the match is guaranteed.
	 *
	 * @param ontologyURI
	 *            The MY_URI of the class of Service ontology you want to call
	 * @param path
	 *            The property path from the root of the Service ontology
	 *            concept to the exact concept you want to manage
	 * @param argChange
	 *            Change representing the input you want to pass as parameter.
	 *            The editor CHANGE service will replace the Resource of the
	 *            same URI with this new value
	 * @return The ServiceRequest that will call the matching CHANGE service of
	 *         an editor
	 */
	public static ServiceRequest requestChange(String ontologyURI, String[] path, Change argChange) {
		Request req = new Request((Service) OntologyManagement.getInstance().getResource(ontologyURI, null));
		req.put(path, argChange);
		return req;
	}

	/**
	 * Gives you the typical CHANGE service request for editor services. If the
	 * editor service also used UtilEditor the match is guaranteed.
	 *
	 * @param ontologyURI
	 *            The MY_URI of the class of Service ontology you want to call
	 * @param path
	 *            The property path from the root of the Service ontology
	 *            concept to the exact concept you want to manage
	 * @param change
	 *            Object representing the input you want to pass as parameter.
	 *            The editor CHANGE service will replace the Resource of the
	 *            same URI with this new value
	 * @return The ServiceRequest that will call the matching CHANGE service of
	 *         an editor
	 */
	public static ServiceRequest requestChange(String ontologyURI, String[] path, Object change) {
		Request req = new Request((Service) OntologyManagement.getInstance().getResource(ontologyURI, null));
		req.put(path, Arg.change(change));
		return req;
	}

	/**
	 * Gives you the typical REMOVE service request for editor services. If the
	 * editor service also used UtilEditor the match is guaranteed.
	 *
	 * @param ontologyURI
	 *            The MY_URI of the class of Service ontology you want to call
	 * @param path
	 *            The property path from the root of the Service ontology
	 *            concept to the exact concept you want to manage
	 * @param argRemove
	 *            Object representing the input you want to pass as parameter.
	 *            The editor REMOVE service will remove all occurences of the
	 *            Resource of the same URI of this argument
	 * @return The ServiceRequest that will call the matching CHANGE service of
	 *         an editor
	 */
	public static ServiceRequest requestRemove(String ontologyURI, String[] path, Remove argRemove) {
		Request req = new Request((Service) OntologyManagement.getInstance().getResource(ontologyURI, null));
		req.put(path, argRemove);
		return req;
	}

	/**
	 * Gives you the typical REMOVE service request for editor services. If the
	 * editor service also used UtilEditor the match is guaranteed.
	 *
	 * @param ontologyURI
	 *            The MY_URI of the class of Service ontology you want to call
	 * @param path
	 *            The property path from the root of the Service ontology
	 *            concept to the exact concept you want to manage
	 * @param remove
	 *            Object representing the input you want to pass as parameter.
	 *            The editor REMOVE service will remove all occurences of the
	 *            Resource of the same URI of this object
	 * @return The ServiceRequest that will call the matching CHANGE service of
	 *         an editor
	 */
	public static ServiceRequest requestRemove(String ontologyURI, String[] path, Object remove) {
		Request req = new Request((Service) OntologyManagement.getInstance().getResource(ontologyURI, null));
		req.put(path, Arg.remove(remove));
		return req;
	}

}
