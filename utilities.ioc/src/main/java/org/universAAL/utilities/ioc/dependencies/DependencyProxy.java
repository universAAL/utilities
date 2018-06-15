/*
    Copyright 2014-2014 CNR-ISTI, http://isti.cnr.it
    Institute of Information Science and Technologies
    of the Italian National Research Council

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
package org.universAAL.utilities.ioc.dependencies;

/**
 * This interface is used to wrap shared object. All implementations of this
 * interface will implement different methods to find a shared object.
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * 
 */
public interface DependencyProxy<T> {

	/**
	 * Retrieve the shared object filters.
	 * 
	 * @return The current object filters used.
	 */
	public Object[] getFilters();

	/**
	 * Retrieve the shared object wrapped by this {@link DependencyProxy}. This
	 * call may be blocking.
	 * 
	 * @return The object, or null if unable to resolve.
	 */
	public T getObject();

	/**
	 * Change the wrapped object. Requesters may not want to use this method.
	 * 
	 * @param value
	 *            The wrapped object.
	 */
	public void setObject(T value);

	/**
	 * Inquiry as whether the object is resolved or not. If returns true,
	 * {@link DependencyProxy#getObject()} will not return null.
	 * 
	 * @return true if object is resolved.
	 */
	public boolean isResolved();

}
