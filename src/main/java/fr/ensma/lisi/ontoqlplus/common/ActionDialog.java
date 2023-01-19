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
package fr.ensma.lisi.ontoqlplus.common;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import fr.ensma.lisi.ontoqlplus.common.util.GridBagUtil;

/**
 * @author Stéphane JEAN
 */
public class ActionDialog extends JDialog {

	private static final long serialVersionUID = 5601079071986697463L;

	private JLabel label = new JLabel();

	public ActionDialog(Frame owner, ImageIcon icon, String a) {
		super(owner, true);
		setActionText(a);

		JPanel content = new JPanel(new GridBagLayout());
		content.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED),
				BorderFactory.createEtchedBorder()));

		label.setIcon(icon);
		GridBagUtil.add(content, label, 0, 0, 0, 0, 1.0d, 1.0d, GridBagConstraints.BOTH, GridBagConstraints.CENTER,
				new Insets(12, 12, 12, 12));

		getContentPane().add(content);

		// defaults
		setResizable(false);
		setUndecorated(true);
		setSize(300, 75);
	}

	public void setActionText(String a) {
		label.setText(a);
	}
}
