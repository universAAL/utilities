/*
	Copyright 2008 ITACA-SABIEN, http://www.sabien.upv.es
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
package org.universAAL.utilities.api;

import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceResponse;

/**
 * Implementations of this interface act like a Service Callee: When a service
 * call request matches a service profile associated to it by universAAL helper, it is
 * passed to its hadleCall method.
 *
 * @author alfiva
 *
 */
public interface ISListener {
	/**
	 * When a service call request matches a service profile associated to this
	 * listener by universAAL helper, this method is called and is passed the call. It
	 * must then act accordingly and return the appropriate Service Response.
	 *
	 * @param call
	 *            The received Service Call request.
	 * @return The Service Response to return to the caller.
	 * @see org.universAAL.middleware.service.ServiceCallee#handleCall(org.universAAL.middleware.service.ServiceCall)
	 */
	ServiceResponse handleCall(ServiceCall call);
}
