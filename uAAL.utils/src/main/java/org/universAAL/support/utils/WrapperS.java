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
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;

/**
 * This class is for internal use by the utility API only. It is just a Service
 * Callee that associates calls to its handleCall method to an associated
 * ISListener.
 *
 * @author alfiva
 *
 */
public class WrapperS extends ServiceCallee {

	/**
	 * The associated ISListener.
	 */
	private ISListener listener;

	/**
	 * Constructor that takes the listener to associate.
	 *
	 * @param context
	 *            The uAAL Module Context.
	 * @param realizedServices
	 *            Service Profiles describing the services to provide.
	 * @param l
	 *            The associated ISListener.
	 */
	protected WrapperS(ModuleContext context, ServiceProfile[] realizedServices, ISListener l) {
		super(context, realizedServices);
		listener = l;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.universAAL.middleware.service.ServiceCallee#
	 * communicationChannelBroken ()
	 */
	@Override
	public void communicationChannelBroken() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.universAAL.middleware.service.ServiceCallee#handleCall(org.universAAL
	 * .middleware.service.ServiceCall)
	 */
	@Override
	public ServiceResponse handleCall(ServiceCall s) {
		return listener.handleCall(s);
	}

}
