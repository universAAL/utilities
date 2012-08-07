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
import org.universAAL.middleware.rdf.PropertyPath;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.owl.Service;
import org.universAAL.middleware.service.owls.process.ProcessInput;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.universAAL.ontology.phThing.DeviceService;
import org.universAAL.ontology.phThing.OnOffActuator;
import org.universAAL.support.utils.service.*;
import org.universAAL.support.utils.service.low.Request;

/**
 * This is a helper class for those who want to use the typical services of an
 * on/off actuator (get status, set on and off) over an ontological service. It
 * provides methods for automatically generating service profiles and service
 * requests that can be used in ServiceCallees and ServiceCallers. If they are
 * both with the same parameters in both sides, the services are guaranteed to
 * match.
 * 
 * @author alfiva
 * 
 */
public class UtilActuator {

    public static final String SERVICE_GET_ON_OFF = "servActuatorGet";
    public static final String OUT_GET_ON_OFF = "outputActuatorGet";
    public static final String SERVICE_TURN_OFF = "servActuatorOff";
    public static final String SERVICE_TURN_ON = "servActuatorOn";
    public static final String IN_DEVICE = "inputActuatorAll";

    /**
     * Gives you the 3 typical service profiles of an on/off actuator service:
     * Get status, Set On, and Set Off. When handling requests in your Callee,
     * you can use the references to services and arguments URIs prepending
     * <code>namespace</code> to UtilActuator constants.
     * <p>
     * Example:
     * <p>
     * <code>
     * new SCallee(context, getServiceProfiles("http://ontology.universAAL.org/OvenServer.owl#", OvenService.MY_URI, myOvenDevice))
     * </code>
     * <p>
     * 
     * @param namespace
     *            The namespace of your server, ending with the character #. You
     *            can optionally add some prefix after the # if you use
     *            UtilActuator more than once in the same Callee.
     * @param ontologyURI
     *            The MY_URI of the class of DeviceService ontology you are
     *            going to implement. It MUST be a subclass of DeviceService.
     * @param actuator
     *            The ontology instance of the actuator you are controlling. The
     *            more properties it has set, the better.
     * @return An array with the 3 typical service profiles
     */
    public static ServiceProfile[] getServiceProfiles(String namespace,
	    String ontologyURI, OnOffActuator actuator) {

	ServiceProfile[] profiles = new ServiceProfile[3];

	PropertyPath ppath = new PropertyPath(null, true, new String[] {
		DeviceService.PROP_CONTROLS, OnOffActuator.PROP_STATUS });

	ProcessInput input = new ProcessInput(namespace + IN_DEVICE);
	input.setParameterType(actuator.getClassURI());
	input.setCardinality(1, 0);

	MergedRestriction r = MergedRestriction.getFixedValueRestriction(
		DeviceService.PROP_CONTROLS, actuator);

	Service getOnOff = (Service) OntologyManagement.getInstance()
		.getResource(ontologyURI, namespace + SERVICE_GET_ON_OFF);
	profiles[0] = getOnOff.getProfile();
	ProcessOutput output = new ProcessOutput(namespace + OUT_GET_ON_OFF);
	output.setCardinality(1, 1);
	profiles[0].addOutput(output);
	profiles[0].addSimpleOutputBinding(output, ppath.getThePath());
	profiles[0].addInput(input);
	profiles[0].getTheService().addInstanceLevelRestriction(r,
		new String[] { DeviceService.PROP_CONTROLS });

	Service turnOff = (Service) OntologyManagement.getInstance()
		.getResource(ontologyURI, namespace + SERVICE_TURN_OFF);
	profiles[1] = turnOff.getProfile();
	profiles[1].addChangeEffect(ppath.getThePath(), Boolean.valueOf(false));
	profiles[1].addInput(input);
	profiles[1].getTheService().addInstanceLevelRestriction(r,
		new String[] { DeviceService.PROP_CONTROLS });

	Service turnOn = (Service) OntologyManagement.getInstance()
		.getResource(ontologyURI, namespace + SERVICE_TURN_ON);
	profiles[2] = turnOn.getProfile();
	profiles[2].addChangeEffect(ppath.getThePath(), Boolean.valueOf(true));
	profiles[2].addInput(input);
	profiles[2].getTheService().addInstanceLevelRestriction(r,
		new String[] { DeviceService.PROP_CONTROLS });

	return profiles;
    }

    /**
     * Gives you the 3 typical service profiles of an on/off actuator service:
     * Get status, Set On, and Set Off. When handling requests in your Callee,
     * you can use the references to services and arguments URIs prepending
     * <code>namespace</code> to UtilActuator constants. The service is
     * treated as the default DeviceService, in case you don´t have a specific
     * service ontology for the type of actuator you are handling.
     * 
     * @param namespace
     *            The namespace of your server, ending with the character #. You
     *            can optionally add some prefix after the # if you use
     *            UtilActuator more than once in the same Callee.
     * @param actuator
     *            The ontology instance of the actuator you are controlling. The
     *            more properties it has set, the better.
     * @return An array with the 3 typical service profiles
     */
    public static ServiceProfile[] getServiceProfiles(String namespace,
	    OnOffActuator actuator) {
	return getServiceProfiles(namespace, DeviceService.MY_URI, actuator);
    }

    /**
     * Gives you the typical GET STATUS service request for actuator services.
     * If the editor service also used UtilActuator the match is guaranteed.
     * 
     * @param ontologyURI
     *            The MY_URI of the class of Service ontology you want to call
     * @param argIn
     *            Value representing the input you want to pass as
     *            parameter. The actuator GET service will be called for this
     *            specific actuator.
     * @param argOut
     *            The returned value of the actuator GET service will be placed
     *            in the URI represented by this Output. Look for it there
     *            in the response.
     * @return The ServiceRequest that will call the matching GET STATUS service
     *         of an actuator
     */
    public static ServiceRequest requestGetOnOff(String ontologyURI,
	    Variable argIn, Output argOut) {
	Request req = new Request((Service) OntologyManagement
		.getInstance().getResource(ontologyURI, null));
	req.put(Path.at(DeviceService.PROP_CONTROLS), argIn);
	req.put(Path.at(DeviceService.PROP_CONTROLS).to(
		OnOffActuator.PROP_STATUS), argOut);
	return req;
    }

    /**
     * Gives you the typical GET STATUS service request for actuator services.
     * If the editor service also used UtilActuator the match is guaranteed.
     * 
     * @param ontologyURI
     *            The MY_URI of the class of Service ontology you want to call
     * @param actuator
     *            The ontology instance of the actuator you want to get the
     *            status from.
     * @param out
     *            The returned value of the actuator GET service will be placed
     *            in the URI represented by this String. Look for it there in
     *            the response.
     * @return The ServiceRequest that will call the matching GET STATUS service
     *         of an actuator
     */
    public static ServiceRequest requestGetOnOff(String ontologyURI,
	    OnOffActuator actuator, String out) {
	return requestGetOnOff(ontologyURI, Arg.in(actuator), Arg.out(out));
    }

    /**
     * Gives you the typical GET STATUS service request for actuator services.
     * If the editor service also used UtilActuator the match is guaranteed.
     * 
     * @param actuator
     *            The ontology instance of the actuator you want to get the
     *            status from.
     * @param out
     *            The returned value of the actuator GET service will be placed
     *            in the URI represented by this String. Look for it there in
     *            the response.
     * @return The ServiceRequest that will call the matching GET STATUS service
     *         of an actuator
     */
    public static ServiceRequest requestGetOnOff(OnOffActuator actuator,
	    String out) {
	return requestGetOnOff(DeviceService.MY_URI, actuator, out);
    }

    /**
     * Gives you the typical SET ON service request for actuator services. If
     * the editor service also used UtilActuator the match is guaranteed.
     * 
     * @param ontologyURI
     *            The MY_URI of the class of Service ontology you want to call
     * @param argIn
     *            Value representing the input you want to pass as
     *            parameter. The actuator SET ON service will be called for this
     *            specific actuator.
     * @return The ServiceRequest that will call the matching SET ON service of
     *         an actuator
     */
    public static ServiceRequest requestSetOn(String ontologyURI,
	    Variable argIn) {
	Request req = new Request((Service) OntologyManagement
		.getInstance().getResource(ontologyURI, null));
	req.put(Path.at(DeviceService.PROP_CONTROLS), argIn);
	req.put(Path.at(DeviceService.PROP_CONTROLS).to(
		OnOffActuator.PROP_STATUS), Arg.change(Boolean.valueOf(true)));
	return req;
    }

    /**
     * Gives you the typical SET ON service request for actuator services. If
     * the editor service also used UtilActuator the match is guaranteed.
     * 
     * @param ontologyURI
     *            The MY_URI of the class of Service ontology you want to call
     * @param actuator
     *            The ontology instance of the actuator you want to set on.
     * @return The ServiceRequest that will call the matching SET ON service of
     *         an actuator
     */
    public static ServiceRequest requestSetOn(String ontologyURI,
	    OnOffActuator actuator) {
	return requestSetOn(ontologyURI, Arg.in(actuator));
    }

    /**
     * Gives you the typical SET ON service request for actuator services. If
     * the editor service also used UtilActuator the match is guaranteed.
     * 
     * @param actuator
     *            The ontology instance of the actuator you want to set on.
     * @return The ServiceRequest that will call the matching SET ON service of
     *         an actuator
     */
    public static ServiceRequest requestSetOn(OnOffActuator actuator) {
	return requestSetOn(DeviceService.MY_URI, actuator);
    }

    /**
     * Gives you the typical SET OFF service request for actuator services. If
     * the editor service also used UtilActuator the match is guaranteed.
     * 
     * @param ontologyURI
     *            The MY_URI of the class of Service ontology you want to call
     * @param argIn
     *            Value representing the input you want to pass as
     *            parameter. The actuator SET OFF service will be called for
     *            this specific actuator.
     * @return The ServiceRequest that will call the matching SET OFF service of
     *         an actuator
     */
    public static ServiceRequest requestSetOff(String ontologyURI,
	    Variable argIn) {
	Request req = new Request((Service) OntologyManagement
		.getInstance().getResource(ontologyURI, null));
	req.put(Path.at(DeviceService.PROP_CONTROLS), argIn);
	req.put(Path.at(DeviceService.PROP_CONTROLS).to(
		OnOffActuator.PROP_STATUS), Arg.change(Boolean.valueOf(false)));
	return req;
    }

    /**
     * Gives you the typical SET OFF service request for actuator services. If
     * the editor service also used UtilActuator the match is guaranteed.
     * 
     * @param ontologyURI
     *            The MY_URI of the class of Service ontology you want to call
     * @param actuator
     *            The ontology instance of the actuator you want to set off.
     * @return The ServiceRequest that will call the matching SET OFF service of
     *         an actuator
     */
    public static ServiceRequest requestSetOff(String ontologyURI,
	    OnOffActuator actuator) {
	return requestSetOff(ontologyURI, Arg.in(actuator));
    }

    /**
     * Gives you the typical SET OFF service request for actuator services. If
     * the editor service also used UtilActuator the match is guaranteed.
     * 
     * @param actuator
     *            The ontology instance of the actuator you want to set off.
     * @return The ServiceRequest that will call the matching SET OFF service of
     *         an actuator
     */
    public static ServiceRequest requestSetOff(OnOffActuator actuator) {
	return requestSetOff(DeviceService.MY_URI, actuator);
    }

}
