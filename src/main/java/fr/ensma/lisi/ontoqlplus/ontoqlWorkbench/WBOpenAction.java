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
package fr.ensma.lisi.ontoqlplus.ontoqlWorkbench;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

import fr.ensma.lisi.ontoqlplus.common.AbstractOpenAction;
import fr.ensma.lisi.ontoqlplus.common.util.GuiUtils;

/**
 * @author Stéphane JEAN
 */
public class WBOpenAction extends AbstractOpenAction {

	private static final long serialVersionUID = 709239536142293335L;

	private OntoQLWorkBench wb;

	private GuiUtils gUtils;

	private WBSaveAction saveAction;

	public WBOpenAction(String name, OntoQLWorkBench parent, WBSaveAction related) {
		super(name);
		this.saveAction = related;
		this.wb = parent;
		this.gUtils = new GuiUtils(wb);
	}

	public void actionPerformed(ActionEvent e) {
		int result = fc.showOpenDialog(wb);
		if (result == JFileChooser.APPROVE_OPTION) {
			try {
				File f = fc.getSelectedFile();
				wb.setEditorText(readFile(f));
				wb.setTitleFileString(f.getAbsolutePath());
				wb.setModifiedShown(false);
				saveAction.setFile(f);
			} catch (IOException ioe) {
				gUtils.displayError("Could not read file");
			}
		}
	}
}
