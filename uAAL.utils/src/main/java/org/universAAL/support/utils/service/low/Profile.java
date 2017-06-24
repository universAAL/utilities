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
package org.universAAL.support.utils.service.low;

import org.universAAL.middleware.container.utils.StringUtils;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.service.owl.Service;
import org.universAAL.middleware.service.owls.process.ProcessInput;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.universAAL.support.utils.service.Add;
import org.universAAL.support.utils.service.Change;
import org.universAAL.support.utils.service.Output;
import org.universAAL.support.utils.service.Path;
import org.universAAL.support.utils.service.Remove;
import org.universAAL.support.utils.service.Typematch;
import org.universAAL.support.utils.service.Variable;

/**
 * A helper class that lets you build ServiceProfile easily so you can use them
 * in your ProvidedService class. Just create an instance of this and add
 * arguments to it to define a profile, in the same way you would to generate
 * the request from the client. Then get the resulting profile and store it in
 * your ProvidedService list of profiles.
 * <p/>
 * Example: Creating a Lighting service profile that controls LightSources of
 * type ElectricLight, find a specific light and turn it to a given value
 * <p/>
 * <code>
 * <p/>Profile prof=new Profile(new ProvidedLightingService(REF_URI_NEW_SERVICE));
 * <p/>prof.put(new String[]{Lighting.PROP_CONTROLS,LightSource.PROP_HAS_TYPE}, new Typematch(ElectricLight.MY_URI),REF_URI_1);
 * <p/>prof.put(new String[]{Lighting.PROP_CONTROLS}, new Variable(new LightSource()),REF_URI_2);
 * <p/>prof.put(new String[]{Lighting.PROP_CONTROLS,LightSource.PROP_SOURCE_BRIGHTNESS }, new Change(new Integer(0)),REF_URI_3);
 * <p/>profiles[0]=prof.getTheProfile();
 * </code>
 * <p/>
 * You have the advantage that it is almost the same code than you would use to
 * call the service with a Request. You just need to add the reference URIs to
 * each argument. Notice that you may create instances of Resources passed as
 * parameters to the SimpleValues constructors. These are only used to get the
 * Type URI, so you can use any given instance. In the example we use an
 * anonymous LightSource and an Integer with value 0.
 * <p/>
 * This is not necessarily faster nor better than the usual way of doing it with
 * ServiceProfile and Service. It's just an alternative way that might help
 * those less familiarized with universAAL.
 *
 * @author alfiva
 *
 */
public class Profile {

	/**
	 * Default namespace.
	 */
	public static final String MY_NAMESPACE = "http://org.universAAL.ontology/SimpleUtils.owl#";
	/**
	 * Allocate a service.
	 */
	public Service service;

	/**
	 * Use this helper class to create a ServiceProfile that is easy to use.
	 * This Profile does not extend ServiceProfile. you will have to get it with
	 * getTheProfile();.
	 * <p/>
	 * Example: <code>
	 * Profile prof=new Profile(new ProvidedLightingService(REF_URI_NEW_SERVICE));</code>
	 *
	 * @param profiledServiceRoot
	 *            An instance of the ProvidedService class that you are using to
	 *            register the profiles, with an appropriate reference URI.
	 */
	public Profile(Service profiledServiceRoot) {
		this.service = profiledServiceRoot;
	}

	/**
	 * Use this helper method to declare an argument over a Profile, specifying
	 * that the created profile must have, in the given branch of properties, an
	 * instance of the <b>same type</b> expressed with Typematch argument.
	 * <p/>
	 * Example: <code>
	 * req.put(new String[]{Lighting.PROP_CONTROLS,LightSource.PROP_HAS_TYPE}, new Typematch(ElectricLight.MY_URI));
	 * </code>
	 *
	 * @param branch
	 *            The path of properties from the Service root class to the
	 *            value to be restricted
	 * @param leaf
	 *            The Typematch describing the type of instance expected at the
	 *            end of the branch
	 */
	public void put(String[] branch, Typematch leaf) {
		this.service.addInstanceLevelRestriction(
				MergedRestriction.getAllValuesRestriction(branch[branch.length - 1], leaf.getURI()), branch);
	}

	/**
	 * Use this helper method to declare an argument over a Profile, specifying
	 * that the created profile must have, in the given branch of properties, an
	 * instance of the <b>same type</b> expressed with Typematch argument.
	 * <p/>
	 * Example: <code>
	 * req.put(new String[]{Lighting.PROP_CONTROLS,LightSource.PROP_HAS_TYPE}, new Typematch(ElectricLight.MY_URI));
	 * </code>
	 *
	 * @param branch
	 *            The path of properties from the Service root class to the
	 *            value to be restricted
	 * @param leaf
	 *            The Typematch describing the type of instance expected at the
	 *            end of the branch
	 */
	public void put(Path branch, Typematch leaf) {
		put(branch.path, leaf);
	}

	/**
	 * Use this helper method to declare an argument over a Profile, specifying
	 * that the created profile must have, in the given branch of properties, an
	 * instance of the <b>same type</b> expressed with Typematch argument, and
	 * with the specific allowed cardinality.
	 * <p/>
	 * Example: <code>
	 * req.put(new String[]{Lighting.PROP_CONTROLS,LightSource.PROP_HAS_TYPE}, new Typematch(ElectricLight.MY_URI));
	 * </code>
	 *
	 * @param branch
	 *            The path of properties from the Service root class to the
	 *            value to be restricted
	 * @param leaf
	 *            The Typematch describing the type of instance expected at the
	 *            end of the branch
	 * @param minCard
	 *            Minimum cardinality
	 * @param maxCard
	 *            Maximum cardinality. Must be greater than minimum, of course
	 */
	public void put(Path branch, Typematch leaf, int minCard, int maxCard) {
		this.service.addInstanceLevelRestriction(MergedRestriction.getAllValuesRestrictionWithCardinality(
				branch.path[branch.path.length - 1], leaf.getURI(), maxCard, maxCard), branch.path);
	}

	/**
	 * Use this helper method to declare an argument over a Profile, specifying
	 * that the created profile will receive as <b>variable input</b>, in the
	 * given branch of properties, <b>an instance of type</b> expressed with
	 * Variable argument. However it can also be used to specify that the
	 * variable input that can only be handled by this profile is a specific
	 * instance. This can be done by using the byValue constructor of the
	 * Variable.
	 * <p/>
	 * Example: <code>
	 * req.put(new String[]{Lighting.PROP_CONTROLS}, new Variable(LightSource.MY_URI),REF_URI);
	 * </code>
	 *
	 * @param branch
	 *            The path of properties from the Service root class to the
	 *            value to be received as input by the service
	 * @param leaf
	 *            The Variable describing the type of the value that you want to
	 *            receive as variable input in the service. If it is built with
	 *            a specific instance value then it means that this profile will
	 *            answer only to calls with that value in this variable input.
	 * @param uriID
	 *            The reference URI to be used by the ServiceCallee when dealing
	 *            with this value. Set to null to return an auto-generated one
	 * @return The URI_ID. If it was set to null, it will be automatically
	 *         generated.
	 */
	public String put(String[] branch, Variable leaf, String uriID) {
		// like this.service.addFilteringInput(URI_ID,
		// ManagedIndividual.getTypeURI(leaf.getObject()), 0, 0,branch);
		// only that it's not visible, so we have to make it "manually"
		if (uriID == null) {
			uriID = MY_NAMESPACE + StringUtils.createUniqueID();
		}
		ProcessInput input = new ProcessInput(uriID);
		input.setParameterType(leaf.getURI());
		MergedRestriction restr;
		if (leaf.byURI()) {
			restr = MergedRestriction.getFixedValueRestriction(branch[branch.length - 1], input.asVariableReference());
		} else {
			restr = MergedRestriction.getFixedValueRestriction(branch[branch.length - 1], leaf.getObject());
		}
		this.service.addInstanceLevelRestriction(restr, branch);
		this.service.getProfile().addInput(input);
		return uriID;
	}

	/**
	 * Use this helper method to declare an argument over a Profile, specifying
	 * that the created profile will receive as <b>variable input</b>, in the
	 * given branch of properties, <b>an instance of type</b> expressed with
	 * Variable argument. However it can also be used to specify that the
	 * variable input that can only be handled by this profile is a specific
	 * instance. This can be done by using the byValue constructor of the
	 * Variable.
	 * <p/>
	 * Example: <code>
	 * req.put(new String[]{Lighting.PROP_CONTROLS}, new Variable(new LightSource()),REF_URI);
	 * </code>
	 *
	 * @param branch
	 *            The path of properties from the Service root class to the
	 *            value to be received as input by the service
	 * @param leaf
	 *            The Variable describing the type of the value that you want to
	 *            receive as variable input in the service. If it is built with
	 *            a specific instance value then it means that this profile will
	 *            answer only to calls with that value in this variable input.
	 * @param uriID
	 *            The reference URI to be used by the ServiceCallee when dealing
	 *            with this value. Set to null to return an auto-generated one
	 * @return The URI_ID. If it was set to null, it will be automatically
	 *         generated.
	 */
	public String put(Path branch, Variable leaf, String uriID) {
		return put(branch.path, leaf, uriID);
	}

	/**
	 * Use this helper method to declare an argument over a Profile, specifying
	 * that the created profile will receive as <b>variable input</b>, in the
	 * given branch of properties, <b>an instance of type</b> expressed with
	 * Variable argument, and with the specific allowed cardinality. However it
	 * can also be used to specify that the variable input that can only be
	 * handled by this profile is a specific instance. This can be done by using
	 * the byValue constructor of the Variable.
	 * <p/>
	 * Example: <code>
	 * req.put(new String[]{Lighting.PROP_CONTROLS}, new Variable(new LightSource()),REF_URI);
	 * </code>
	 *
	 * @param branch
	 *            The path of properties from the Service root class to the
	 *            value to be received as input by the service
	 * @param leaf
	 *            The Variable describing the type of the value that you want to
	 *            receive as variable input in the service. If it is built with
	 *            a specific instance value then it means that this profile will
	 *            answer only to calls with that value in this variable input.
	 * @param uriID
	 *            The reference URI to be used by the ServiceCallee when dealing
	 *            with this value. Set to null to return an auto-generated one
	 * @param minCard
	 *            Minimum cardinality
	 * @param maxCard
	 *            Maximum cardinality. Must be greater than minimum, of course
	 * @return The URI_ID. If it was set to null, it will be automatically
	 *         generated.
	 */
	public String put(Path branch, Variable leaf, String uriID, int minCard, int maxCard) {
		if (uriID == null) {
			uriID = MY_NAMESPACE + StringUtils.createUniqueID();
		}
		ProcessInput input = new ProcessInput(uriID);
		input.setParameterType(leaf.getURI());
		input.setCardinality(maxCard, minCard);
		MergedRestriction restr;
		if (leaf.byURI()) {
			restr = MergedRestriction.getFixedValueRestriction(branch.path[branch.path.length - 1],
					input.asVariableReference());
		} else {
			restr = MergedRestriction.getFixedValueRestriction(branch.path[branch.path.length - 1], leaf.getObject());
		}
		this.service.addInstanceLevelRestriction(restr, branch.path);
		this.service.getProfile().addInput(input);
		return uriID;
	}

	/**
	 * Use this helper method to declare an argument over a Profile, specifying
	 * that <b>you will return an output</b> in the given branch of properties,
	 * and will be of the <b>type</b> specified with the Output argument.
	 * <p/>
	 * Example: <code>
	 * req.put(new String[]{Lighting.PROP_CONTROLS}, new Output(LightSource.MY_URI));
	 * </code>
	 *
	 * @param branch
	 *            The path of properties from the Service root class to the
	 *            output that you will return
	 * @param leaf
	 *            The Output describing the type of Output you are returning.
	 *            Take into account that this differs from how it is used in
	 *            Request. Here you must create it with a Type URI (a
	 *            ManagedIndividual.MY_URI).
	 * @param uriID
	 *            The reference URI to be used by the ServiceCallee when dealing
	 *            with this value. Set to null to return an auto-generated one
	 * @return The URI_ID. If it was set to null, it will be automatically
	 *         generated.
	 */
	public String put(String[] branch, Output leaf, String uriID) {
		// like this.service.addOutput(URI_ID, leaf.getURI(), 0, 0, branch);
		// only that it's not visible, so we have to make it "manually"
		if (uriID == null) {
			uriID = MY_NAMESPACE + StringUtils.createUniqueID();
		}
		ProcessOutput output = new ProcessOutput(uriID);
		output.setParameterType(leaf.getURI());
		this.service.getProfile().addOutput(output);
		this.service.getProfile().addSimpleOutputBinding(output, branch);
		return uriID;
	}

	/**
	 * Use this helper method to declare an argument over a Profile, specifying
	 * that <b>you will return an output</b> in the given branch of properties,
	 * and will be of the <b>type</b> specified with the Output argument.
	 * <p/>
	 * Example: <code>
	 * req.put(new String[]{Lighting.PROP_CONTROLS}, new Output(LightSource.MY_URI));
	 * </code>
	 *
	 * @param branch
	 *            The path of properties from the Service root class to the
	 *            output that you will return
	 * @param leaf
	 *            The Output describing the type of Output you are returning.
	 *            Take into account that this differs from how it is used in
	 *            Request. Here you must create it with a Type URI (a
	 *            ManagedIndividual.MY_URI).
	 * @param uriID
	 *            The reference URI to be used by the ServiceCallee when dealing
	 *            with this value. Set to null to return an auto-generated one
	 * @return The URI_ID. If it was set to null, it will be automatically
	 *         generated.
	 */
	public String put(Path branch, Output leaf, String uriID) {
		return put(branch.path, leaf, uriID);
	}

	/**
	 * Use this helper method to declare an argument over a Profile, specifying
	 * that <b>you will return an output</b> in the given branch of properties,
	 * and will be of the <b>type</b> specified with the Output argument, and
	 * with the specific allowed cardinality.
	 * <p/>
	 * Example: <code>
	 * req.put(new String[]{Lighting.PROP_CONTROLS}, new Output(LightSource.MY_URI));
	 * </code>
	 *
	 * @param branch
	 *            The path of properties from the Service root class to the
	 *            output that you will return
	 * @param leaf
	 *            The Output describing the type of Output you are returning.
	 *            Take into account that this differs from how it is used in
	 *            Request. Here you must create it with a Type URI (a
	 *            ManagedIndividual.MY_URI).
	 * @param uriID
	 *            The reference URI to be used by the ServiceCallee when dealing
	 *            with this value. Set to null to return an auto-generated one
	 * @param minCard
	 *            Minimum cardinality
	 * @param maxCard
	 *            Maximum cardinality. Must be greater than minimum, of course
	 * @return The URI_ID. If it was set to null, it will be automatically
	 *         generated.
	 */
	public String put(Path branch, Output leaf, String uriID, int minCard, int maxCard) {
		if (uriID == null) {
			uriID = MY_NAMESPACE + StringUtils.createUniqueID();
		}
		ProcessOutput output = new ProcessOutput(uriID);
		output.setParameterType(leaf.getURI());
		output.setCardinality(maxCard, minCard);
		this.service.getProfile().addOutput(output);
		this.service.getProfile().addSimpleOutputBinding(output, branch.path);
		return uriID;
	}

	/**
	 * Use this helper method to declare an argument over a Profile, specifying
	 * that you will <b>add</b>, in the given branch of properties, a <b>value
	 * instance of type</b> expressed with Add argument. However it can also be
	 * used to specify that the added input that can only be handled by this
	 * profile is a specific instance. This can be done by using the byValue
	 * constructor of the Add.
	 * <p/>
	 * Example: <code>
	 * req.put(new String[]{Lighting.PROP_CONTROLS}, new Add(new LightSource()));
	 * </code>
	 *
	 * @param branch
	 *            The path of properties from the Service root class to the
	 *            value to be added
	 * @param leaf
	 *            The Add describing the type of instance to be added at the end
	 *            of the branch. If it is built with a specific instance value
	 *            then it means that this profile will answer only to calls with
	 *            that value in this added input.
	 * @param uriID
	 *            The reference URI to be used by the ServiceCallee when dealing
	 *            with this value. Set to null to return an auto-generated one
	 * @return The URI_ID. If it was set to null, it will be automatically
	 *         generated.
	 */
	public String put(String[] branch, Add leaf, String uriID) {
		// like this.service.addInputWithAddEffect(URI_ID,
		// ManagedIndividual.getTypeURI(leaf.getObject()), 0, 0,branch);
		// only that it's not visible, so we have to make it "manually"
		if (uriID == null) {
			uriID = MY_NAMESPACE + StringUtils.createUniqueID();
		}
		ProcessInput input = new ProcessInput(uriID);
		input.setParameterType(leaf.getURI());
		this.service.getProfile().addInput(input);
		if (leaf.byURI()) {
			this.service.getProfile().addAddEffect(branch, input.asVariableReference());
		} else {
			this.service.getProfile().addAddEffect(branch, leaf.getObject());
		}
		return uriID;
	}

	/**
	 * Use this helper method to declare an argument over a Profile, specifying
	 * that you will <b>add</b>, in the given branch of properties, a <b>value
	 * instance of type</b> expressed with Add argument. However it can also be
	 * used to specify that the added input that can only be handled by this
	 * profile is a specific instance. This can be done by using the byValue
	 * constructor of the Add.
	 * <p/>
	 * Example: <code>
	 * req.put(new String[]{Lighting.PROP_CONTROLS}, new Add(new LightSource()));
	 * </code>
	 *
	 * @param branch
	 *            The path of properties from the Service root class to the
	 *            value to be added
	 * @param leaf
	 *            The Add describing the type of instance to be added at the end
	 *            of the branch. If it is built with a specific instance value
	 *            then it means that this profile will answer only to calls with
	 *            that value in this added input.
	 * @param uriID
	 *            The reference URI to be used by the ServiceCallee when dealing
	 *            with this value. Set to null to return an auto-generated one
	 * @return The URI_ID. If it was set to null, it will be automatically
	 *         generated.
	 */
	public String put(Path branch, Add leaf, String uriID) {
		return put(branch.path, leaf, uriID);
	}

	/**
	 * Use this helper method to declare an argument over a Profile, specifying
	 * that you will <b>add</b>, in the given branch of properties, a <b>value
	 * instance of type</b> expressed with Add argument, and with the specific
	 * allowed cardinality. However it can also be used to specify that the
	 * added input that can only be handled by this profile is a specific
	 * instance. This can be done by using the byValue constructor of the Add.
	 * <p/>
	 * Example: <code>
	 * req.put(new String[]{Lighting.PROP_CONTROLS}, new Add(new LightSource()));
	 * </code>
	 *
	 * @param branch
	 *            The path of properties from the Service root class to the
	 *            value to be added
	 * @param leaf
	 *            The Add describing the type of instance to be added at the end
	 *            of the branch. If it is built with a specific instance value
	 *            then it means that this profile will answer only to calls with
	 *            that value in this added input.
	 * @param uriID
	 *            The reference URI to be used by the ServiceCallee when dealing
	 *            with this value. Set to null to return an auto-generated one
	 * @param minCard
	 *            Minimum cardinality
	 * @param maxCard
	 *            Maximum cardinality. Must be greater than minimum, of course
	 * @return The URI_ID. If it was set to null, it will be automatically
	 *         generated.
	 */
	public String put(Path branch, Add leaf, String uriID, int minCard, int maxCard) {
		if (uriID == null) {
			uriID = MY_NAMESPACE + StringUtils.createUniqueID();
		}
		ProcessInput input = new ProcessInput(uriID);
		input.setParameterType(leaf.getURI());
		input.setCardinality(maxCard, minCard);
		this.service.getProfile().addInput(input);
		if (leaf.byURI()) {
			this.service.getProfile().addAddEffect(branch.path, input.asVariableReference());
		} else {
			this.service.getProfile().addAddEffect(branch.path, leaf.getObject());
		}
		return uriID;
	}

	/**
	 * Use this helper method to declare an argument over a Profile, specifying
	 * that you will <b>remove</b>, from the given branch of properties, <b>a
	 * value instance of type</b> expressed with Remove argument. However it can
	 * also be used to specify that the removed input that can only be handled
	 * by this profile is a specific instance. This can be done by using the
	 * byValue constructor of the Remove.
	 * <p/>
	 * Example: <code>
	 * req.put(new String[]{Lighting.PROP_CONTROLS}, new Remove(new LightSource()));
	 * </code>
	 *
	 * @param branch
	 *            The path of properties from the Service root class to the
	 *            value to be removed
	 * @param leaf
	 *            The Remove describing the type of instance to be removed at
	 *            the end of the branch. If it is built with a specific instance
	 *            value then it means that this profile will answer only to
	 *            calls with that value in this removed input.
	 * @param uriID
	 *            The reference URI to be used by the ServiceCallee when dealing
	 *            with this value. Set to null to return an auto-generated one
	 * @return The URI_ID. If it was set to null, it will be automatically
	 *         generated.
	 */
	public String put(String[] branch, Remove leaf, String uriID) {
		// like this.service.addInputWithRemoveEffect(URI_ID,
		// ManagedIndividual.getTypeURI(leaf.getObject()), 0, 0,branch);
		// only that it's not visible, so we have to make it "manually"
		if (uriID == null) {
			uriID = MY_NAMESPACE + StringUtils.createUniqueID();
		}
		ProcessInput input = new ProcessInput(uriID);
		input.setParameterType(leaf.getURI());
		this.service.getProfile().addInput(input);
		// TODO The addRemoveEffect does not take value to remove!!! Will this
		// work?
		MergedRestriction restr;
		if (leaf.byURI()) {
			restr = MergedRestriction.getFixedValueRestriction(branch[branch.length - 1], input.asVariableReference());
		} else {
			restr = MergedRestriction.getFixedValueRestriction(branch[branch.length - 1], leaf.getObject());
		}
		this.service.addInstanceLevelRestriction(restr, branch);
		this.service.getProfile().addRemoveEffect(branch);
		return uriID;
	}

	/**
	 * Use this helper method to declare an argument over a Profile, specifying
	 * that you will <b>remove</b>, from the given branch of properties, <b>a
	 * value instance of type</b> expressed with Remove argument. However it can
	 * also be used to specify that the removed input that can only be handled
	 * by this profile is a specific instance. This can be done by using the
	 * byValue constructor of the Remove.
	 * <p/>
	 * Example: <code>
	 * req.put(new String[]{Lighting.PROP_CONTROLS}, new Remove(new LightSource()));
	 * </code>
	 *
	 * @param branch
	 *            The path of properties from the Service root class to the
	 *            value to be removed
	 * @param leaf
	 *            The Remove describing the type of instance to be removed at
	 *            the end of the branch. If it is built with a specific instance
	 *            value then it means that this profile will answer only to
	 *            calls with that value in this removed input.
	 * @param uriID
	 *            The reference URI to be used by the ServiceCallee when dealing
	 *            with this value. Set to null to return an auto-generated one
	 * @return The URI_ID. If it was set to null, it will be automatically
	 *         generated.
	 */
	public String put(Path branch, Remove leaf, String uriID) {
		return put(branch.path, leaf, uriID);
	}

	/**
	 * Use this helper method to declare an argument over a Profile, specifying
	 * that you will <b>remove</b>, from the given branch of properties, <b>a
	 * value instance of type</b> expressed with Remove argument, and with the
	 * specific allowed cardinality. However it can also be used to specify that
	 * the removed input that can only be handled by this profile is a specific
	 * instance. This can be done by using the byValue constructor of the
	 * Remove.
	 * <p/>
	 * Example: <code>
	 * req.put(new String[]{Lighting.PROP_CONTROLS}, new Remove(new LightSource()));
	 * </code>
	 *
	 * @param branch
	 *            The path of properties from the Service root class to the
	 *            value to be removed
	 * @param leaf
	 *            The Remove describing the type of instance to be removed at
	 *            the end of the branch. If it is built with a specific instance
	 *            value then it means that this profile will answer only to
	 *            calls with that value in this removed input.
	 * @param uriID
	 *            The reference URI to be used by the ServiceCallee when dealing
	 *            with this value. Set to null to return an auto-generated one
	 * @param minCard
	 *            Minimum cardinality
	 * @param maxCard
	 *            Maximum cardinality. Must be greater than minimum, of course
	 * @return The URI_ID. If it was set to null, it will be automatically
	 *         generated.
	 */
	public String put(Path branch, Remove leaf, String uriID, int minCard, int maxCard) {
		if (uriID == null) {
			uriID = MY_NAMESPACE + StringUtils.createUniqueID();
		}
		ProcessInput input = new ProcessInput(uriID);
		input.setParameterType(leaf.getURI());
		input.setCardinality(maxCard, minCard);
		this.service.getProfile().addInput(input);
		MergedRestriction restr;
		if (leaf.byURI()) {
			restr = MergedRestriction.getFixedValueRestriction(branch.path[branch.path.length - 1],
					input.asVariableReference());
		} else {
			restr = MergedRestriction.getFixedValueRestriction(branch.path[branch.path.length - 1], leaf.getObject());
		}
		this.service.addInstanceLevelRestriction(restr, branch.path);
		this.service.getProfile().addRemoveEffect(branch.path);
		return uriID;
	}

	/**
	 * Use this helper method to declare an argument over a Profile, specifying
	 * that you will <b>change</b>, at the given branch of properties, an old
	 * value with <b>the new one</b>, an instance of type expressed with Change
	 * argument. However it can also be used to specify that the changed input
	 * that can only be handled by this profile is a specific instance. This can
	 * be done by using the byValue constructor of the Change.
	 * <p/>
	 * Example: <code>
	 * req.put(new String[]{Lighting.PROP_CONTROLS,LightSource.PROP_SOURCE_BRIGHTNESS }, new Change(new Integer(0)));
	 * </code>
	 *
	 * @param branch
	 *            The path of properties from the Service root class to the
	 *            value to be changed
	 * @param leaf
	 *            The Change describing the type of instance to be changed at
	 *            the end of the branch. If it is built with a specific instance
	 *            value then it means that this profile will answer only to
	 *            calls with that value in this changed input.
	 * @param uriID
	 *            The reference URI to be used by the ServiceCallee when dealing
	 *            with this value. Set to null to return an auto-generated one
	 * @return The URI_ID. If it was set to null, it will be automatically
	 *         generated.
	 */
	public String put(String[] branch, Change leaf, String uriID) {
		// like this.service.addInputWithChangeEffect(URI_ID,
		// ManagedIndividual.getTypeURI(leaf.getObject()), 0, 0,branch);
		// only that it's not visible, so we have to make it "manually"
		if (uriID == null) {
			uriID = MY_NAMESPACE + StringUtils.createUniqueID();
		}
		ProcessInput input = new ProcessInput(uriID);
		input.setParameterType(leaf.getURI());
		this.service.getProfile().addInput(input);
		if (leaf.byURI()) {
			this.service.getProfile().addChangeEffect(branch, input.asVariableReference());
		} else {
			this.service.getProfile().addChangeEffect(branch, leaf.getObject());
		}
		return uriID;
	}

	/**
	 * Use this helper method to declare an argument over a Profile, specifying
	 * that you will <b>change</b>, at the given branch of properties, an old
	 * value with <b>the new one</b>, an instance of type expressed with Change
	 * argument. However it can also be used to specify that the changed input
	 * that can only be handled by this profile is a specific instance. This can
	 * be done by using the byValue constructor of the Change.
	 * <p/>
	 * Example: <code>
	 * req.put(new String[]{Lighting.PROP_CONTROLS,LightSource.PROP_SOURCE_BRIGHTNESS }, new Change(new Integer(0)));
	 * </code>
	 *
	 * @param branch
	 *            The path of properties from the Service root class to the
	 *            value to be changed
	 * @param leaf
	 *            The Change describing the type of instance to be changed at
	 *            the end of the branch. If it is built with a specific instance
	 *            value then it means that this profile will answer only to
	 *            calls with that value in this changed input.
	 * @param uriID
	 *            The reference URI to be used by the ServiceCallee when dealing
	 *            with this value. Set to null to return an auto-generated one
	 * @return The URI_ID. If it was set to null, it will be automatically
	 *         generated.
	 */
	public String put(Path branch, Change leaf, String uriID) {
		return put(branch.path, leaf, uriID);
	}

	/**
	 * Use this helper method to declare an argument over a Profile, specifying
	 * that you will <b>change</b>, at the given branch of properties, an old
	 * value with <b>the new one</b>, an instance of type expressed with Change
	 * argument, and with the specific allowed cardinality. However it can also
	 * be used to specify that the changed input that can only be handled by
	 * this profile is a specific instance. This can be done by using the
	 * byValue constructor of the Change.
	 * <p/>
	 * Example: <code>
	 * req.put(new String[]{Lighting.PROP_CONTROLS,LightSource.PROP_SOURCE_BRIGHTNESS }, new Change(new Integer(0)));
	 * </code>
	 *
	 * @param branch
	 *            The path of properties from the Service root class to the
	 *            value to be changed
	 * @param leaf
	 *            The Change describing the type of instance to be changed at
	 *            the end of the branch. If it is built with a specific instance
	 *            value then it means that this profile will answer only to
	 *            calls with that value in this changed input.
	 * @param uriID
	 *            The reference URI to be used by the ServiceCallee when dealing
	 *            with this value. Set to null to return an auto-generated one
	 * @param minCard
	 *            Minimum cardinality
	 * @param maxCard
	 *            Maximum cardinality. Must be greater than minimum, of course
	 * @return The URI_ID. If it was set to null, it will be automatically
	 *         generated.
	 */
	public String put(Path branch, Change leaf, String uriID, int minCard, int maxCard) {
		if (uriID == null) {
			uriID = MY_NAMESPACE + StringUtils.createUniqueID();
		}
		ProcessInput input = new ProcessInput(uriID);
		input.setParameterType(leaf.getURI());
		input.setCardinality(maxCard, minCard);
		this.service.getProfile().addInput(input);
		if (leaf.byURI()) {
			this.service.getProfile().addChangeEffect(branch.path, input.asVariableReference());
		} else {
			this.service.getProfile().addChangeEffect(branch.path, leaf.getObject());
		}
		return uriID;
	}

	/**
	 * Use this method when you are done defining your Profile in order to get
	 * the actual ServiceProfile that you can use in your ProvidedService class.
	 * Assign the returned ServiceProfile to a value of the "profiles" array of
	 * that class. Or you can use it first to add it more restrictions or
	 * arguments the old-fashioned way.
	 *
	 * @return The resulting ServiceProfile of this Profile
	 */
	public ServiceProfile getTheProfile() {
		return this.service.getProfile();
	}

}
