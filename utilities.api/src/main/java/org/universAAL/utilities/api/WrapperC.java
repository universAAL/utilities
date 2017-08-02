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

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextSubscriber;

/**
 * This class is for internal use by the utility API only. It is just a Context
 * Subscriber that associates calls to its handleContextEvent method to an
 * associated ICListener.
 *
 * @author alfiva
 *
 */
public class WrapperC extends ContextSubscriber {

	/**
	 * The associated ICListener.
	 */
	private ICListener listener;

	/**
	 * Constructor that takes the listener to associate.
	 *
	 * @param context
	 *            The universAAL Module Context.
	 * @param initialSubscriptions
	 *            Event Patterns to subscriber to.
	 * @param l
	 *            The associated ICListener.
	 */
	protected WrapperC(ModuleContext context, ContextEventPattern[] initialSubscriptions, ICListener l) {
		super(context, initialSubscriptions);
		listener = l;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.universAAL.middleware.context.ContextSubscriber#
	 * communicationChannelBroken()
	 */
	@Override
	public void communicationChannelBroken() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.universAAL.middleware.context.ContextSubscriber#handleContextEvent
	 * (org.universAAL.middleware.context.ContextEvent)
	 */
	@Override
	public void handleContextEvent(ContextEvent event) {
		listener.handleContextEvent(event);
	}
}
