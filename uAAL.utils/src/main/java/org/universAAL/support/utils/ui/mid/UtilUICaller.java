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
package org.universAAL.support.utils.ui.mid;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owl.InitialServiceDialog;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.universAAL.middleware.ui.UICaller;

/**
 * This abstract class can be extended to create a UICaller that automatically
 * registers its own Service Callee with the typical Main Menu "Start" service,
 * that will be called by the Dialog Manager when the user requests the
 * application form the Main Menu. It is exactly like extending the typical
 * UICaller only that:
 * <p>
 * 1: You have to implement method executeStartUI which will be called when the
 * user requests interaction. The user is passed to the method.
 * <p>
 * 2: Main Menu is automatically updated to include the Submit for this
 * application. Until release 1.1.0 this is done with files "main_menu_*.txt"
 * which are modified to include this application ID.
 *
 * @author alfiva
 *
 */
public abstract class UtilUICaller extends UICaller {

	/**
	 * The root directory of the runtime configuration.
	 */
	public static final String CONF_ROOT_DIR = "bundles.configuration.location";

	/**
	 * Associated Service Callee.
	 */
	private UIcallee callee;

	/**
	 * Constructor to create a Simple UI Caller that handles first call
	 * interaction and addition to Main Menu.
	 *
	 * @param context
	 *            The universAAL module context.
	 * @param namespace
	 *            Namespace to be used by the UI Caller references (only for
	 *            initial UI request).
	 * @param url
	 *            The URL of your company, for identificative purposes only.
	 * @param title
	 *            Name of the application, which will appear in the Main Menu
	 *            submit.
	 */
	protected UtilUICaller(ModuleContext context, String namespace, String url, String title) {
		super(context);
		callee = new UIcallee(context, namespace, url, title, this);
		File dm = new File(new File(System.getProperty(CONF_ROOT_DIR, System.getProperty("user.dir"))), "ui.dm");
		File dmmob = new File(new File(System.getProperty(CONF_ROOT_DIR, System.getProperty("user.dir"))),
				"ui.dm.mobile");
		try {
			if (dm.exists()) {
				populate(dm, namespace, url, title);
			}
			if (dmmob.exists()) {
				populate(dmmob, namespace, url, title);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Scans all Dialog Manager Main Menu files and inserts the application
	 * button.
	 *
	 * @param dm
	 *            Dialog Manger configuration folder.
	 * @param namespace
	 *            Namespace to be used by the UI Caller references (only for
	 *            initial UI request).
	 * @param url
	 *            The URL of your company, for identificative purposes only.
	 * @param title
	 *            Name of the application, which will appear in the Main Menu
	 *            submit.
	 * @throws IOException
	 *             If there was any problem with the files.
	 */
	private void populate(File dm, String namespace, String url, String title) throws IOException {
		File[] files = dm.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.toLowerCase().startsWith("main_menu");
			}
		});
		for (int i = 0; i < files.length; i++) {
			BufferedReader br = new BufferedReader(new FileReader(files[i]));
			String readline = br.readLine();
			boolean found = false;
			while (readline != null) {
				if (readline.contains(namespace + "UIService")) {
					readline = null;
					found = true;
				} else {
					readline = br.readLine();
				}
			}
			if (!found) {
				BufferedWriter bw = new BufferedWriter(new FileWriter(files[i], true));
				bw.newLine();
				bw.write("/" + title + "|" + url + "|" + namespace + "UIService");
				bw.newLine();
				bw.close();
			}
			br.close();
		}
	}

	/**
	 * This method is called whenever the user selects your application submit
	 * from the Main Menu. You should initiate the interaction with the user
	 * here by publishing some kind of Dialog.
	 *
	 * @param resource
	 *            The User which initiated the interaction. May be an instance
	 *            of User, AssistedPerson or Caregiver.
	 */
	public abstract void executeStartUI(Resource resource);

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.universAAL.support.utils.ui.UICaller#communicationChannelBroken()
	 */
	@Override
	public void communicationChannelBroken() {
		callee.close();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.universAAL.middleware.ui.UICaller#close()
	 */
	@Override
	public void close() {
		callee.close();
		super.close();
	}

	/**
	 * Internal Service Callee that handles the call to the "start UI" service
	 * from the Dialog Manager when a user selects the application Submit form
	 * the Main Menu.
	 *
	 * @author alfiva
	 *
	 */
	private class UIcallee extends ServiceCallee {

		/**
		 * Placeholder for composed URI for start service.
		 */
		private String startUI = "";
		/**
		 * Reference to UI caller.
		 */
		private UtilUICaller ui;

		/**
		 * Constructs a Service Callee for the "start UI" service.
		 *
		 * @param context
		 *            The universAAL module context.
		 * @param namespace
		 *            Namespace to be used by the UI Caller references (only for
		 *            initial UI request).
		 * @param url
		 *            The URL of your company, for identificative purposes only.
		 * @param desc
		 *            Name of the application, which will appear in the Main
		 *            Menu submit.
		 * @param uicaller
		 *            The Simple UI Caller that requires this service.
		 */
		protected UIcallee(ModuleContext context, String namespace, String url, String desc, UtilUICaller uicaller) {
			super(context, new ServiceProfile[] { InitialServiceDialog
					.createInitialDialogProfile(namespace + "UIService", url, desc, namespace + "startUI") });
			startUI = namespace + "startUI";
			ui = uicaller;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see org.universAAL.middleware.service.ServiceCallee#
		 * communicationChannelBroken()
		 */
		@Override
		public void communicationChannelBroken() {
			// Nothing
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see org.universAAL.middleware.service.ServiceCallee#handleCall(org.
		 * universAAL .middleware.service.ServiceCall)
		 */
		@Override
		public ServiceResponse handleCall(ServiceCall call) {
			if (call != null) {
				String operation = call.getProcessURI();
				if (operation != null && operation.startsWith(startUI)) {
					ui.executeStartUI(call.getInvolvedUser());
					ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
					return sr;
				}
			}
			return null;
		}

	}

}
