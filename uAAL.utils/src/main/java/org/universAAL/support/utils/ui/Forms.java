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

/**
 * Helper shortcut class with static methods to generate all kinds of
 * SimpleControls UI elements more quickly, directly usable from the add methods
 * of the Simple UI Requests.
 * <p/>
 * Example: Creating a dialog for selecting a light from a list and have
 * commands for turning it on and off.
 * <p/>
 * <code>
 * <p/>Dialog d=new Dialog(user,"Light interface");
 * <p/>d.add(Forms.out("-","Select one of the following lights"));
 * <p/>d.add(Forms.one(LIST_URI, "Lights",new String[]{"ligth1","light2","light3"}));
 * <p/>d.addSubmit(Forms.submit(SUBMIT_ON,"Turn On"));
 * <p/>d.addSubmit(Forms.submit(SUBMIT_OFF,"Turn Off"));
 * <p/>caller.sendUIRequest(d);
 * </code>
 * 
 * @author alfiva
 * 
 */
public class Forms {
    
    private Forms(){
	// Disallow instantiating
    }
    
    /**
     * Create a Area control.
     * 
     * @param ref
     *            The simple reference identifying the input. Set to null to
     *            auto-generate.
     * @param label
     *            The label text that identifies the input to the user.
     * @return The Area
     */
    public static Area area(String ref, String label){
	return new Area(ref,label);
    }
    
    /**
     * Create a Area control.
     * 
     * @param ref
     *            The simple reference identifying the input. Set to null to
     *            auto-generate.
     * @param label
     *            The label text that identifies the input to the user.
     * @return The Area
     */
    public static Check check(String ref, String label){
	return new Check(ref,label);
    }
    
    /**
     * Create a Media control.
     * 
     * @param label
     *            The label text that identifies the output to the user.
     * @param url
     *            The URL to the value to be displayed, from the Resource
     *            server.
     * @return The Media
     */
    public static Media media(String label, String url){
	return new Media(label,url);
    }
    
    /**
     * Create a Out control.
     * 
     * @param label
     *            The label text that identifies the output to the user.
     * @param value
     *            The output value to be displayed.
     * @return The Out
     */
    public static Out out(String label, String value){
	return new Out(label,value);
    }

    /**
     * Create a SelectRange control.
     * 
     * @param ref
     *            The simple reference identifying the input. Set to null to
     *            auto-generate.
     * @param label
     *            The label text that identifies the input to the user.
     * @param min
     *            The low end of the range that the value can get (inclusive)
     * @param max
     *            The high end of the range that the value can get (inclusive)
     * @return The SelectRange
     */
    public static SelectRange range(String ref, String label, int min, int max){
 	return new SelectRange(ref,label,min,max,min);
    }
    
    /**
     * Create a SelectMulti control.
     * 
     * @param ref
     *            The simple reference identifying the input. Set to null to
     *            auto-generate.
     * @param label
     *            The label text that identifies the input to the user.
     * @param initialOptions
     *            An array of Strings that represent the different possible
     *            options to select.
     * @return The SelectMulti
     */
    public static SelectMulti multi(String ref, String label, String[] initialOptions){
	return new SelectMulti(ref,label,initialOptions);
    }
    
    /**
     * Create a SelectOne control.
     * 
     * @param ref
     *            The simple reference identifying the input. Set to null to
     *            auto-generate.
     * @param label
     *            The label text that identifies the input to the user.
     * @param initialOptions
     *            An array of Strings that represent the different possible
     *            options to select.
     * @return The SelectOne
     */
    public static SelectOne one(String ref, String label, String[] initialOptions){
	return new SelectOne(ref,label,initialOptions);
    }
    
    /**
     * Create a SubmitCmd control.
     * 
     * @param ref
     *            The simple reference identifying the input. Set to null to
     *            auto-generate.
     * @param label
     *            The label text that identifies the submit to the user.
     * @return The SubmitCmd
     */
    public static SubmitCmd submit(String ref, String label){
	return new SubmitCmd(ref,label);
    }
    
    /**
     * Create a Text control.
     * 
     * @param ref
     *            The simple reference identifying the input. Set to null to
     *            auto-generate.
     * @param label
     *            The label text that identifies the input to the user.
     * @return The Text
     */
    public static Text text(String ref, String label){
	return new Text(ref,label);
    }
    
    /**
     * Create a TriggerCmd control.
     * 
     * @param ref
     *            The simple reference identifying the input. Set to null to
     *            auto-generate.
     * @param label
     *            The label text that identifies the submit to the user.
     * @return The TriggerCmd
     */
    public static TriggerCmd trigger(String ref, String label){
	return new TriggerCmd(ref,label);
    }

}
