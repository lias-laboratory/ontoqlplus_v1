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

import java.util.Date;

/**
 * @author Stéphane JEAN
 */
public class HistoryItem implements Comparable {

	private String sql;

	private Date creationTime;

	public HistoryItem(String sql) {
		creationTime = new Date();
		this.sql = sql;
	}

	public String getSql() {
		return sql;
	}

	public Date getCreationDate() {
		return creationTime;
	}

	public boolean equals(Object arg) {
		if (!(arg instanceof HistoryItem)) {
			return false;
		} else {
			HistoryItem other = (HistoryItem) arg;
			String thisText = removeFormatting(sql);
			String otherText = removeFormatting(other.sql);
			return thisText.equals(otherText);
		}
	}

	public int compareTo(Object arg) {
		HistoryItem other = (HistoryItem) arg;
		return creationTime.compareTo(other.creationTime);
	}

	public String toString() {
		String s = removeFormatting(sql);
		if (s.length() < 103)
			return s;
		else
			return s.substring(0, 99) + "...";
	}

	private String removeFormatting(String text) {
		text = text.trim();
		String pieces[] = text.split("['^'']");
		StringBuffer pure = new StringBuffer();
		for (int i = 0; i < pieces.length; i++)
			if (i % 2 != 0)
				pure.append((new StringBuffer(pieces[i])).insert(0, "'").append("'"));
			else
				pure.append(pieces[i].replaceAll("\\s+", " ").toLowerCase());

		return pure.toString();
	}
}
