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

import java.util.Hashtable;
import java.util.List;

import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owl.Service;
import org.universAAL.support.utils.service.Add;
import org.universAAL.support.utils.service.Change;
import org.universAAL.support.utils.service.Output;
import org.universAAL.support.utils.service.Path;
import org.universAAL.support.utils.service.Remove;
import org.universAAL.support.utils.service.Typematch;
import org.universAAL.support.utils.service.Variable;

/**
 * A helper class that extends ServiceRequest and adds utility methods to build
 * requests. You can still use this class like ServiceRequest. It just adds some
 * more methods that allow you to create requests in a simple argument-oriented
 * way.
 * <p/>
 * Example: Calling a Lighting service that controls LightSources of type
 * ElectricLight, find the light with URI "desiredURI" and turn it off (set to
 * brightness 0) <code>
 * <p/>Request req=new Request(new Lighting(null));
 * <p/>req.put(new String[]{Lighting.PROP_CONTROLS,LightSource.PROP_HAS_TYPE}, new Typematch(ElectricLight.MY_URI));
 * <p/>req.put(new String[]{Lighting.PROP_CONTROLS}, new Variable(new LightSource(desiredURI)));
 * <p/>req.put(new String[]{Lighting.PROP_CONTROLS,LightSource.PROP_SOURCE_BRIGHTNESS }, new Change(new Integer(0)));
 * <p/>caller.call(req);
 * </code>
 * <p/>
 * This is not necessarily faster nor better than the usual way of doing it with
 * ServiceRequest. It's just an alternative way that might help those less
 * familiarized with universAAL.
 * 
 * @author alfiva
 * 
 */
public class Request extends ServiceRequest {

    /**
     * Default namespace.
     */
    public static final String MY_NAMESPACE = "http://org.universAAL.ontology/SimpleUtils.owl#";

    /**
     * Use this helper class to create a ServiceRequest that is easy to use. You
     * can use this Request also as you could use a normal ServiceRequest.
     * <p/>
     * Example: <code>
     * Request req=new Request(new Lighting(null));</code>
     * 
     * @param requestedServiceRoot
     *            An instance of the Service class that you want to call,
     *            created with null URI.
     */
    public Request(Service requestedServiceRoot) {
	super(requestedServiceRoot, null);
    }

    /**
     * Use this helper method to declare an argument over a Request,
     * specifying that the requested service must have, in the given branch of
     * properties, an instance of the <b>same type</b> expressed with
     * Typematch argument.
     * <p/>
     * Example: <code>
     * req.put(new String[]{Lighting.PROP_CONTROLS,LightSource.PROP_HAS_TYPE}, new Typematch(ElectricLight.MY_URI));
     * </code>
     * 
     * @param branch
     *            The path of properties from the Service root class to the
     *            value to be restricted
     * @param leaf
     *            The Typematch describing the type of instance expected
     *            at the end of the branch
     */
    public void put(String[] branch, Typematch leaf) {
	this.addTypeFilter(branch, leaf.getURI());
    }

    /**
     * Equivalent to put(String[] branch, Typematch leaf).
     * 
     * @param branch
     *            The path of properties from the Service root class to the
     *            value to be restricted
     * @param leaf
     *            The Typematch describing the type of instance expected
     *            at the end of the branch
     */
    public void put(Path branch, Typematch leaf) {
	put(branch.path, leaf);
    }

    /**
     * Use this helper method to declare an argument over a Request,
     * specifying that the requested service will receive as <b>variable
     * input</b>, in the given branch of properties, <b>the instance</b>
     * expressed with Variable argument.
     * <p/>
     * Example: <code>
     * req.put(new String[]{Lighting.PROP_CONTROLS}, new Variable(new LightSource(exactURI)));
     * </code>
     * 
     * @param branch
     *            The path of properties from the Service root class to the
     *            value to be received as input by the service
     * @param leaf
     *            The Variable describing the value that you want to pass
     *            as variable input to the service
     */
    public void put(String[] branch, Variable leaf) {
	this.addValueFilter(branch, leaf.getObject());
    }

    /**
     * Equivalent to put(Path branch, Variable leaf).
     * 
     * @param branch
     *            The path of properties from the Service root class to the
     *            value to be received as input by the service
     * @param leaf
     *            The Variable describing the value that you want to pass
     *            as variable input to the service
     */
    public void put(Path branch, Variable leaf) {
	put(branch.path, leaf);
    }

    /**
     * Use this helper method to declare an argument over a Request,
     * specifying that <b>you want the kind of output</b> specified by the given
     * branch of properties, and you want it to be put in the ServiceResponse
     * <b>under the ID</b> specified with the Output argument.
     * <p/>
     * Example: <code>
     * req.put(new String[]{Lighting.PROP_CONTROLS}, new Output("http://ontology.igd.fhg.de/LightingConsumer.owl#listOfLamps"));
     * </code>
     * 
     * @param branch
     *            The path of properties from the Service root class to the
     *            output that you want
     * @param leaf
     *            The Output describing the URI (the ID) that you will use
     *            when dealing with the ServiceResponse
     */
    public void put(String[] branch, Output leaf) {
	this.addRequiredOutput(leaf.getURI(), branch);
    }

    /**
     * Equivalent to put(String[] branch, Output leaf).
     * 
     * @param branch
     *            The path of properties from the Service root class to the
     *            output that you want
     * @param leaf
     *            The Output describing the URI (the ID) that you will use
     *            when dealing with the ServiceResponse
     */
    public void put(Path branch, Output leaf) {
	put(branch.path, leaf);
    }

    /**
     * Use this helper method to declare an argument over a Request,
     * specifying that you want to <b>add</b>, in the given branch of
     * properties, <b>the value</b> instance expressed with Add argument.
     * <p/>
     * Example: <code>
     * req.put(new String[]{Lighting.PROP_CONTROLS}, new Add(new LightSource(addURI)));
     * </code>
     * 
     * @param branch
     *            The path of properties from the Service root class to the
     *            value to be added
     * @param leaf
     *            The Add describing the instance to be added at the end
     *            of the branch
     */
    public void put(String[] branch, Add leaf) {
	this.addAddEffect(branch, leaf.getObject());
    }

    /**
     * Equivalent to put(String[] branch, Add leaf).
     * 
     * @param branch
     *            The path of properties from the Service root class to the
     *            value to be added
     * @param leaf
     *            The Add describing the instance to be added at the end
     *            of the branch
     */
    public void put(Path branch, Add leaf) {
	put(branch.path, leaf);
    }

    /**
     * Use this helper method to declare an argument over a Request,
     * specifying that you want to <b>remove</b>, from the given branch of
     * properties, <b>the value</b> instance expressed with Remove
     * argument.
     * <p/>
     * Example: <code>
     * req.put(new String[]{Lighting.PROP_CONTROLS}, new Remove(new LightSource(quitURI)));
     * </code>
     * 
     * @param branch
     *            The path of properties from the Service root class to the
     *            value to be removed
     * @param leaf
     *            The Remove describing the instance to be removed at the
     *            end of the branch
     */
    public void put(String[] branch, Remove leaf) {
	MergedRestriction r1 = MergedRestriction.getFixedValueRestriction(
		branch[branch.length - 1], leaf.getObject());
	this.getRequestedService().addInstanceLevelRestriction(r1, branch);
	this.addRemoveEffect(branch);
    }

    /**
     * Equivalent to put(String[] branch, Remove leaf).
     * 
     * @param branch
     *            The path of properties from the Service root class to the
     *            value to be removed
     * @param leaf
     *            The Remove describing the instance to be removed at the
     *            end of the branch
     */
    public void put(Path branch, Remove leaf) {
	put(branch.path, leaf);
    }

    /**
     * Use this helper method to declare an argument over a Request,
     * specifying that you want to <b>change</b>, at the given branch of
     * properties, an old value with <b>a new one</b>, an instance expressed
     * with Change argument.
     * <p/>
     * Example: <code>
     * req.put(new String[]{Lighting.PROP_CONTROLS,LightSource.PROP_SOURCE_BRIGHTNESS }, new Change(new Integer(0)));
     * </code>
     * 
     * @param branch
     *            The path of properties from the Service root class to the
     *            value to be changed
     * @param leaf
     *            The Change describing the new instance to be put at the
     *            end of the branch
     */
    public void put(String[] branch, Change leaf) {
	this.addChangeEffect(branch, leaf.getObject());
    }

    /**
     * Equivalent to put(String[] branch, Change leaf).
     * 
     * @param branch
     *            The path of properties from the Service root class to the
     *            value to be changed
     * @param leaf
     *            The Change describing the new instance to be put at the
     *            end of the branch
     */
    public void put(Path branch, Change leaf) {
	put(branch.path, leaf);
    }

    /**
     * Equivalent to recoverOutputs(ServiceResponse sr, String outputURI).
     * 
     * @param sr
     *            The ServiceResponse returned by the .call method
     * @param output
     *            The Output you used when building the request
     * @return An array of Objects that contain all the returned values stored
     *         at the given Output URI (the ID). You will have to cast
     *         them to the appropriate class yourself, which you know in
     *         advance, as you have built the request... Returns
     *         <code>null</code> in all other cases (Service errors, no
     *         outputs,...)
     */
    public static Object[] recoverOutputs(ServiceResponse sr,
	    Output output) {
	return recoverOutputs(sr, output.getURI());
    }

    /**
     * Use this helper method when you have called a ServiceRequest (or a
     * Request) and you want to get an array containing all the outputs
     * returned.
     * <p/>
     * 
     * @param sr
     *            The ServiceResponse returned by the .call method
     * @param outputURI
     *            The URI (the ID) you used to refer to the output when building
     *            the request
     * @return An array of Objects that contain all the returned values stored
     *         at the given Output URI (the ID). You will have to cast
     *         them to the appropriate class yourself, which you know in
     *         advance, as you have built the request... Returns
     *         <code>null</code> in all other cases (Service errors, no
     *         outputs,...)
     */
    public static Object[] recoverOutputs(ServiceResponse sr, String outputURI) {
	if (sr.getCallStatus() == CallStatus.succeeded) {
	    List outs = sr.getOutput(outputURI, true);
	    if (outs == null) {
		System.out
			.println("---SIMPLE UTILS TIP: No outputs in the " +
					"response. Review your requests.");
	    } else if (outs.size() == 0) {
		System.out
			.println("---SIMPLE UTILS TIP: No outputs with this URI" +
					" in the response. Review your requests.");
	    } else {
		Object[] values = (Object[]) outs.toArray(new Object[outs
			.size()]);
		return values;
	    }
	} else {
	    System.out.println("---SIMPLE UTILS TIP: Service call failed: "
		    + sr.getCallStatus() + ". Review your requests.");
	}
	return null;
    }

    /**
     * I have to put this because of all service refactoring stuff.
     * 
     * @return Nothing, null.
     */
    protected Hashtable getInput() {
	return null;
    }
}
