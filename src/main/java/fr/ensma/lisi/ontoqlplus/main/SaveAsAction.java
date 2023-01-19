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

import fr.ensma.lisi.ontoqlplus.common.AbstractSaveAsAction;
import fr.ensma.lisi.ontoqlplus.ontoqlWorkbench.OntoQLWorkBench;
import fr.ensma.lisi.ontoqlplus.ontoqlWorkbench.WBSaveAsAction;

/**
 * @author Stéphane JEAN
 */
public class SaveAsAction extends AbstractSaveAsAction {

	private static final long serialVersionUID = -4971634810804665934L;

	private Main main;

	public SaveAsAction(String name, Main parent) {
		super(name);
		this.main = parent;
	}

	public void actionPerformed(ActionEvent e) {
		if (main.isWorkBenchInFocus()) {
			OntoQLWorkBench swb = main.getWorkBenchInFocus();
			WBSaveAsAction saveAsAction = swb.getSaveAsAction();
			saveAsAction.actionPerformed(e);
		}
	}
}
