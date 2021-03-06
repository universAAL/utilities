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

package org.universAAL.utilities.ioc.dependencies.impl;

import java.util.Arrays;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.utilities.ioc.dependencies.DependencyProxy;

/**
 * {@link NPEDependencyProxy} or Null Pointer Exception Dependency Proxy will
 * wrap the shared object and attempt resolution only on
 * {@link NPEDependencyProxy#getObject()}. Resoultion might fail, thus you may
 * have an null (and therefore an {@link NullPointerException} when trying to
 * use the object.
 * 
 * This {@link DependencyProxy} might be useful in cases where the shared object
 * is optional and the requester needs to know whether the object is shared or
 * not; or the requester has mechanisms to handle other tasks when the object is
 * not shared.
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * 
 */
public class NPEDependencyProxy<T> implements DependencyProxy<T> {

	private Object[] filters;
	private T proxy;
	private ModuleContext context;

	public NPEDependencyProxy(ModuleContext mc, Object[] filters) {
		this.filters = Arrays.copyOf(filters, filters.length);
		this.context = mc;
	}

	/** {@inheritDoc} */
	public boolean isResolved() {
		synchronized (this) {
			return proxy != null;
		}
	}

	/** {@inheritDoc} */
	public Object[] getFilters() {
		return filters;
	}

	/** {@inheritDoc} */
	public T getObject() {
		if (isResolved() == false && context != null) {
			setObject((T) context.getContainer().fetchSharedObject(context,
					getFilters()));
		}
		synchronized (this) {
			return proxy;
		}
	}

	/** {@inheritDoc} */
	public void setObject(T value) {
		synchronized (this) {
			this.proxy = value;
		}
	}

}
