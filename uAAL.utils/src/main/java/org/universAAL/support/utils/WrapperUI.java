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
package org.universAAL.support.utils;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.ui.UICaller;
import org.universAAL.middleware.ui.UIResponse;

/**
 * This class is for internal use by the utility API only. It is just a UI
 * Caller that associates calls to its handleUIResponse method to an associated
 * ICListener.
 * 
 * @author alfiva
 * 
 */
public class WrapperUI extends UICaller {

    /**
     * The associated IUIListener.
     */
    IUIListener listener;

    /**
     * Constructor.
     * 
     * @param context
     *            The uAAL Module Context.
     */
    protected WrapperUI(ModuleContext context) {
	super(context);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.universAAL.middleware.ui.UICaller#communicationChannelBroken()
     */
    @Override
    public void communicationChannelBroken() {
	// TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.universAAL.middleware.ui.UICaller#dialogAborted(java.lang.String)
     */
    @Override
    public void dialogAborted(String arg0) {
	// TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.universAAL.middleware.ui.UICaller#handleUIResponse(org.universAAL
     * .middleware.ui.UIResponse)
     */
    @Override
    public void handleUIResponse(UIResponse r) {
	if (listener != null) {
	    listener.handleUIResponse(r);
	}
    }

    /**
     * Set the IUIListener.
     * 
     * @param listener
     *            The IUIListener to associate.
     */
    public void setListener(IUIListener listener) {
	this.listener = listener;
    }
}
