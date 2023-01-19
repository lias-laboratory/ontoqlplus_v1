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
package fr.ensma.lisi.ontoqlplus.common.util;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JComponent;

/**
 * @author Stéphane JEAN
 */
public class GridBagUtil {

	/**
	 * Helper routine for putting components on a panels GridBagLayout
	 */
	public static void add(Container cont, JComponent comp, int gridx, int gridy, int ipadx, int ipady, double weightx,
			double weighty, int fill, int anchor, Insets insets) throws IllegalArgumentException {
		// Check that cont has correct layout applied
		if (!(cont.getLayout() instanceof GridBagLayout))
			throw new IllegalArgumentException("Supplied cont must contain layout of the "
					+ "type java.awt.GridBagLayout. Currently you have supplied " + " a "
					+ cont.getLayout().getClass().getName() + ".");

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = gridx;
		constraints.gridy = gridy;
		constraints.ipadx = ipadx;
		constraints.ipady = ipady;
		constraints.weightx = weightx;
		constraints.weighty = weighty;
		constraints.fill = fill;
		constraints.anchor = anchor;
		constraints.insets = insets;
		cont.add(comp, constraints);
	}
}