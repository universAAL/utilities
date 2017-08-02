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
package org.universAAL.utilities.api.service;

import java.util.Locale;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.middleware.xsd.NonNegativeInteger;

/**
 * Abstract class representing an argument in a service request. Arguments are
 * the "leaf" objects at the end of a property path and can be of several types
 * depending on what you want to do with them.
 *
 * @author alfiva
 *
 */
public abstract class Value {
	/**
	 * Holds the URI of the argument.
	 */
	protected String uri;
	/**
	 * Determines if it was set by Value or by type URI.
	 */
	protected boolean isByURI = true;

	protected static Object createEmptyInstance(String uri) {
		try {
			Class c = TypeMapper.getJavaClass(uri);
			if (c.equals(XMLGregorianCalendar.class)) {
				return DatatypeFactory.newInstance().newXMLGregorianCalendar();
			} else if (c.equals(Duration.class)) {
				return DatatypeFactory.newInstance().newDuration(0);
			} else if (c.equals(Boolean.class)) {
				return Boolean.FALSE;
			} else if (c.equals(Locale.class)) {
				return Locale.getDefault();
			} else if (c.equals(Integer.class) || c.equals(Double.class) || c.equals(Float.class)
					|| c.equals(Long.class) || c.equals(NonNegativeInteger.class)) {
				return c.getConstructor(String.class).newInstance("0");
			} else {
				return c.getConstructor().newInstance();
			}
		} catch (Exception e) {
			System.out.println(
					"---SIMPLE UTILS TIP: it was not possible to " + "create an empty instance of this Variable "
							+ "type. This is not a problem if you are calling this "
							+ "constructor from a Profile, but if it's from "
							+ "a Request you should use an Object instance " + "with the other constructor.");
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * This is only supposed to be used internally.
	 *
	 * @return true if constructed for type URI. false if constructed for
	 *         specific value.
	 */
	public boolean byURI() {
		return isByURI;
	}

}
