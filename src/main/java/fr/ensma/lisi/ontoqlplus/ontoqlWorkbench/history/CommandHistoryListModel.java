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
package fr.ensma.lisi.ontoqlplus.ontoqlWorkbench.history;

import java.util.Collections;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;

/**
 * @author Stéphane JEAN
 */
public class CommandHistoryListModel extends DefaultComboBoxModel {

	private static final long serialVersionUID = -7333660936385566459L;

	public CommandHistoryListModel() {
	}

	public CommandHistoryListModel(Object historyItems[]) {
		super(historyItems);
	}

	public CommandHistoryListModel(Vector historyItems) {
		super(historyItems);
	}

	public void addElement(Object o) {
		Vector temp = new Vector(getSize());
		for (int i = 0; i < getSize(); i++)
			temp.add(getElementAt(i));

		if (!temp.contains(o)) {
			temp.add(o);
			Collections.sort(temp);
			Collections.reverse(temp);
			super.removeAllElements();
			for (int i = 0; i < temp.size(); i++)
				super.addElement(temp.get(i));
		}
	}
}
