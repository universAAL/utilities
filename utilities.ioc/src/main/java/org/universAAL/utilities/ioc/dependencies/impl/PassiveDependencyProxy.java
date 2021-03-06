/*
    Copyright 2014 Universidad Politécnica de Madrid UPM

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

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.SharedObjectListener;
import org.universAAL.middleware.container.utils.LogUtils;
import org.universAAL.utilities.ioc.dependencies.DependencyProxy;

/**
 * This {@link DependencyProxy} can be created normally, resolution will be
 * passively attempted on init. If the resolution was not The thread will be
 * blocked indefinitely until the sharedObject is asynchronously notified. It
 * will also adapt when the shared object is removed.
 * 
 * This {@link DependencyProxy} may be used when the shared object is required,
 * but only on specific occasions, but these usages may be blocked until the
 * shared object is available. It is also useful when the shared object may be
 * shared and removed in a very dynamic manner.
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author amedrano
 * @version $LastChangedRevision: 386 $ ($LastChangedDate: 2014-07-22 11:47:16
 *          +0200 (mar, 22 jul 2014) $)
 * 
 * @param <T>
 */
public class PassiveDependencyProxy<T> implements DependencyProxy<T>,
		SharedObjectListener {

	private final Object[] filters;
	private T proxy;
	private Object remH;
	private Class<?> objectType;
	private ModuleContext context;

	public PassiveDependencyProxy(final ModuleContext ctxt,
			final Object[] filters) {
		context = ctxt;
		try {
			this.objectType = Class.forName((String) filters[0]);
		} catch (final ClassNotFoundException ex) {
			throw new RuntimeException("Bad filtering", ex);
		}
		this.filters = filters;
		final Object[] ref = context.getContainer().fetchSharedObject(context,
				filters, this);
		if (ref != null && ref.length > 0) {
			try {
				proxy = (T) ref[0];
			} catch (final Exception e) {
			}
		}
	}

	public boolean isResolved() {
		synchronized (this) {
			return proxy != null;
		}
	}

	public Object[] getFilters() {
		return filters;
	}

	public T getObject() {
		synchronized (this) {
			while (proxy == null) {
				try {
					wait();
				} catch (final InterruptedException e) {
					return proxy;
				}
			}
			return proxy;
		}
	}

	public void setObject(final T value) {
		synchronized (this) {
			this.proxy = value;
			notifyAll();
		}
	}

	public void sharedObjectAdded(final Object sharedObj,
			final Object removeHook) {
		try {
			if (sharedObj == null
					|| objectType.isAssignableFrom(sharedObj.getClass()) == false) {
				return;
				/*
				 * //XXX This is a workaround: Workaround to avoid issue in the
				 * middleware that notifies leaving and departing of
				 * sharedObject that do not match the filters
				 */
			}
			if (proxy != null) {
				//already received a valid object, ignore.
				return;
			}
			setObject((T) sharedObj);
			this.remH = removeHook;
		} catch (final Exception e) {
			LogUtils.logError(context, getClass(), "sharedObjectAdded",
					new String[] { "unexpected Exception" }, e);
		}
	}

	public void sharedObjectRemoved(final Object removeHook) {
		if (removeHook == remH) {
			proxy = null;
		}

	}
}
