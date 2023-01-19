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

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 * @author Stéphane JEAN
 */
public class StatusBar extends JLabel implements ActionListener {

	private static final long serialVersionUID = 6380379042376227082L;

	/**
	 * The text in the status bar while a message isn't set
	 */
	private static final String CLEAR_TEXT = " ";

	private int displayIntervalMillis;

	private boolean autoClear = true;

	private Timer clearTextTimer;

	public StatusBar() {
		this(7000);
	}

	public StatusBar(int displayIntervalMillis) {
		super(" ");
		this.displayIntervalMillis = displayIntervalMillis;
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(1, 2, 1, 2),
				BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.GRAY),
						BorderFactory.createEmptyBorder(1, 5, 1, 5))));
		clearTextTimer = new Timer(this.displayIntervalMillis, this);
	}

	public void setText(String status) {
		super.setText(status);
		if (autoClear) {
			// restart the timer on subsequent setTexts
			if (clearTextTimer.isRunning())
				clearTextTimer.stop();
			clearTextTimer.start();
		}
	}

	public void clear() {
		super.setText(CLEAR_TEXT);
	}

	public void setDisplayInterval(int millis) {
		displayIntervalMillis = millis;
	}

	public void setAutoClear(boolean autoClear) {
		this.autoClear = autoClear;
	}

	public void actionPerformed(ActionEvent ae) {
		clear();
	}
}
