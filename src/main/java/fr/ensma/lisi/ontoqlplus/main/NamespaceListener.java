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

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import fr.ensma.lisi.ontoql.jobdbc.OntoQLSession;

/**
 * @author Stéphane JEAN
 */
public class NamespaceListener implements ItemListener {

	protected String namespace = null;

	protected OntoQLSession session;

	public NamespaceListener(OntoQLSession s, String namespace) {

		this.namespace = namespace;
		session = s;
	}

	public void itemStateChanged(ItemEvent ie) {
		if (ie.getStateChange() == ItemEvent.SELECTED)
			session.setDefaultNameSpace(namespace);
		else
			session.setDefaultNameSpace(null);
	}
}