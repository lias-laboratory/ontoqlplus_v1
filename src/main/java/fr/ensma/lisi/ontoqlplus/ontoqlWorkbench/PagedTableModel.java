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

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

/**
 * @author Stéphane JEAN
 */
public class PagedTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 6532303691848130961L;

	protected int pSize;

	protected int pOffset;

	protected String columnNames[];

	protected Vector rows;

	public PagedTableModel(int size) {
		pSize = size;
	}

	public int getPageOffset() {
		return pOffset;
	}

	public int getPageSize() {
		return pSize;
	}

	public void setPageSize(int s) {
		if (s == pSize)
			return;
		int oldPSize = pSize;
		pSize = s;
		if (pSize < oldPSize)
			fireTableRowsDeleted(pSize, oldPSize - 1);
		else
			fireTableRowsUpdated(oldPSize, pSize - 1);
	}

	public void pageUp() {
		if (pOffset > 0) {
			pOffset--;
			fireTableDataChanged();
		}
	}

	public void pageDown() {
		if (pOffset < getPageCount() - 1) {
			pOffset++;
			fireTableDataChanged();
		}
	}

	public int getPageCount() {
		return (int) Math.ceil((double) rows.size() / pSize);
	}

	public int getRealRowCount() {
		return rows.size();
	}

	public int getRowCount() {
		return pSize;
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public Object getValueAt(int aRow, int aCol) {
		int realRow = aRow + (pOffset * pSize);
		Vector row = (Vector) rows.elementAt(realRow);
		return row.elementAt(aCol);
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}
}
