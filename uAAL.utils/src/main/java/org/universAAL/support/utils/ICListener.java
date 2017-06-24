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

import org.universAAL.middleware.context.ContextEvent;

/**
 * Implementations of this interface act like a Context Subscriber: When an
 * event matches a pattern associated to it by UAAL helper, it is passed to its
 * hadleContextEvent method.
 *
 * @author alfiva
 *
 */
public interface ICListener {
	/**
	 * When an event matches a pattern associated to this listener by UAAL
	 * helper, this method is called and is passed the event.
	 *
	 * @param event
	 *            The received Context Event.
	 * @see org.universAAL.middleware.context.ContextSubscriber#handleContextEvent(org.universAAL.middleware.context.ContextEvent)
	 */
	void handleContextEvent(ContextEvent event);
}
