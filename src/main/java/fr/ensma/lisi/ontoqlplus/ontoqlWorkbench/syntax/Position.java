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
package fr.ensma.lisi.ontoqlplus.ontoqlWorkbench.syntax;

/**
 * @author Stéphane JEAN
 */
public class Position {

	/**
	 * The actual position
	 */
	private int p;

	/**
	 * Construct a Position within the given offset of the document
	 */
	public Position(int position) {
		p = position;
	}

	/**
	 * Return the position
	 */
	public int getPosition() {
		return p;
	}

	/**
	 * This is useful if the text is to be inserted before or after this position
	 *
	 * @param amount The amount of adjustment to make, either +/-
	 * @return This position adjusted accordingly
	 */
	public Position adjust(int amount) {
		p += amount;
		return this;
	}

	public boolean equals(Object arg) {
		if (!(arg instanceof Position))
			return false;

		Position other = (Position) arg;
		return p == other.p;
	}
}