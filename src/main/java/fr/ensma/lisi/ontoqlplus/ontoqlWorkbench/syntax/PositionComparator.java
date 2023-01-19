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

import java.util.Comparator;

/**
 * @author Mickael BARON
 */
public class PositionComparator implements Comparator {

	public int compare(Object o1, Object o2) {
		// param checking
		if (!(o1 instanceof Position))
			throw new IllegalArgumentException("1st Object supplied is not a Position Object.");
		if (!(o2 instanceof Position))
			throw new IllegalArgumentException("2nd Object supplied is not a Position Object.");

		Position p1 = (Position) o1;
		Position p2 = (Position) o2;

		return p1.getPosition() - p2.getPosition();
	}

	/**
	 * Since all PositionComparators are the same, they are all equal to one another
	 */
	public boolean equals(Object arg) {
		if (arg instanceof PositionComparator)
			return true;

		return false;
	}
}