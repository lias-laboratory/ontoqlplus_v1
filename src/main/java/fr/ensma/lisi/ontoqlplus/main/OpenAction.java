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
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

import fr.ensma.lisi.ontoqlplus.common.AbstractOpenAction;
import fr.ensma.lisi.ontoqlplus.common.util.GuiUtils;
import fr.ensma.lisi.ontoqlplus.ontoqlWorkbench.OntoQLWorkBench;
import fr.ensma.lisi.ontoqlplus.ontoqlWorkbench.WBOpenAction;

/**
 * @author Stéphane JEAN
 */
public class OpenAction extends AbstractOpenAction {

	private static final long serialVersionUID = 2798021910047191389L;

	private Main main;

	private GuiUtils gUtils;

	public OpenAction(String name, Main parent) {
		super(name);
		this.main = parent;
		this.gUtils = new GuiUtils(parent);
	}

	public void actionPerformed(ActionEvent e) {
		if (main.isWorkBenchInFocus()) {
			OntoQLWorkBench aBench = main.getWorkBenchInFocus();
			WBOpenAction openAction = aBench.getOpenAction();
			openAction.actionPerformed(e);
		} else {
			int result = fc.showOpenDialog(main);
			if (result == JFileChooser.APPROVE_OPTION) {
				try {
					File file = fc.getSelectedFile();
					String fileText = readFile(file);
					main.openOntoqlWorkBench(fileText, file);
				} catch (IOException ioe) {
					gUtils.displayError("Could not read file");
				}
			}
		}
	}
}
