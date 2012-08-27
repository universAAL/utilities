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

import org.universAAL.middleware.ui.UIResponse;

/**
 * Implementations of this interface act like a UI Caller: When a UI response is
 * received to a UI request associated to the listener by UAAL helper, it is
 * passed to its hadleUIResponse method.
 * 
 * @author alfiva
 * 
 */
public interface IUIListener {
    /**
     * When a UI response is received to a UI request associated to this
     * listener by UAAL helper, this method is called and is passed the
     * response.
     * 
     * @param response
     *            The received UI Response.
     * @see org.universAAL.middleware.ui.UICaller#handleUIResponse(org.universAAL.middleware.ui.UIResponse)
     */
    void handleUIResponse(UIResponse response);
}
