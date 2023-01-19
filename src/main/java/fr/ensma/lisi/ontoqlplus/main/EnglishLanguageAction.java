/*********************************************************************************
* This file is part of OntoQLPlus Project.
* Copyright (C) 2006  LISI - ENSMA
*   Teleport 2 - 1 avenue Clement Ader
*   BP 40109 - 86961 Futuroscope Chasseneuil Cedex - FRANCE
* 
* OntoQLPlus is free software: you can redistribute it and/or modify
* it under the terms of the GNU Lesser General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* OntoQLPlus is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU Lesser General Public License for more details.
* 
* You should have received a copy of the GNU Lesser General Public License
* along with OntoQLPlus.  If not, see <http://www.gnu.org/licenses/>.
**********************************************************************************/
package fr.ensma.lisi.ontoqlplus.main;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import fr.ensma.lisi.ontoql.jobdbc.OntoQLSession;
import fr.ensma.lisi.ontoql.util.OntoQLHelper;
import fr.ensma.lisi.ontoqlplus.common.Environment;

/**
 * @author Stéphane JEAN
 */
public class EnglishLanguageAction extends AbstractAction {

	private static final long serialVersionUID = -1402502472430169201L;

	protected String language_code = OntoQLHelper.ENGLISH;

	protected OntoQLSession session;

	public EnglishLanguageAction(OntoQLSession s) {

		super("English", Environment.ENGLISH_ICON);
		language_code = OntoQLHelper.ENGLISH;
		session = s;

	}

	public String getLanguageCode() {
		return language_code;
	}

	public void actionPerformed(ActionEvent event) {
		session.setReferenceLanguage(OntoQLHelper.ENGLISH);
	}

	/**
	 * @return Returns the session.
	 */
	public OntoQLSession getSession() {
		return session;
	}

	/**
	 * @param session The session to set.
	 */
	public void setSession(OntoQLSession session) {
		this.session = session;
	}
}