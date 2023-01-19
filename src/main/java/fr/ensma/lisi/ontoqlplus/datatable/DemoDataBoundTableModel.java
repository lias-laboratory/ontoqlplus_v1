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
package fr.ensma.lisi.ontoqlplus.datatable;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import fr.ensma.lisi.ontoql.exception.JOBDBCException;
import fr.ensma.lisi.ontoql.jobdbc.OntoQLSession;
import fr.ensma.lisi.ontoql.jobdbc.OntoQLStatement;

public class DemoDataBoundTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -4404731923427616665L;

	private OntoQLStatement statement;

	private ResultSet resultSet;

	private ResultSetMetaData rmeta;

	public String sqlString;

	private String columnNames[];

	private int columnTypes[];

	private boolean writables[];

	private Vector rows;

	public DemoDataBoundTableModel(OntoQLSession s) {
		columnNames = new String[0];
		rows = new Vector();
	}

	public synchronized long executeQuery(String sql) throws JOBDBCException {
		Date start = new Date();
		Date end = new Date();
		return end.getTime() - start.getTime();
	}

	public synchronized long executeSPARQLQuery(String sql) throws JOBDBCException {
		Date start = new Date();
		Date end = new Date();
		return end.getTime() - start.getTime();
	}

	public synchronized int executeUpdate(String sql) throws SQLException {
		return 1;
	}

	@SuppressWarnings("unchecked")
	public synchronized void bind() throws SQLException {
		int numCols = 3;

		// column names
		columnNames = new String[numCols];
		columnNames[0] = "oid";
		columnNames[1] = "name";
		columnNames[2] = "firstname";
		// column types
		columnTypes = new int[numCols];
		columnTypes[0] = Types.BIGINT;
		columnTypes[1] = Types.VARCHAR;
		columnTypes[2] = Types.VARCHAR;

		// writables
		writables = new boolean[numCols];
		writables[0] = false;
		writables[1] = true;
		writables[2] = true;

		rows = new Vector();
		Vector aRow = new Vector(numCols);
		aRow.add("1");
		aRow.add("Baron");
		aRow.add("Mickael");
		rows.add(aRow);

		fireTableChanged(null);
	}

	public synchronized void close() throws SQLException {
		if (resultSet != null) {
			resultSet.close();
			resultSet = null;
		}
		if (statement != null) {
			// statement.close();
			statement = null;
		}
		rmeta = null;
	}

	public synchronized String getColumnName(int column) {
		if (columnNames[column] != null)
			return columnNames[column];
		else
			return " ";
	}

	@SuppressWarnings("unchecked")
	public synchronized Class getColumnClass(int column) {
		int type = columnTypes[column];

		switch (type) {
		case Types.CHAR:
		case Types.VARCHAR:
		case Types.LONGVARCHAR:
			return String.class;

		case Types.BIT:
			return Boolean.class;

		case Types.TINYINT:
		case Types.SMALLINT:
		case Types.INTEGER:
			return Integer.class;

		case Types.BIGINT:
			return Long.class;

		case Types.FLOAT:
		case Types.DOUBLE:
			return Double.class;

		case Types.DATE:
			return java.sql.Date.class;

		default:
			return Object.class;
		}
	}

	public synchronized int getColumnCount() {
		return columnNames.length;
	}

	public synchronized boolean isCellEditable(int row, int col) {
		return writables[col];
	}

	public synchronized int getRowCount() {
		return rows.size();
	}

	public synchronized Object getValueAt(int aRow, int aCol) {
		Vector row = (Vector) rows.elementAt(aRow);
		if (row.elementAt(aCol) != null)
			return row.elementAt(aCol);
		else
			return "<NULL>";
	}

	@Override
	public synchronized void setValueAt(Object value, int row, int col) {
		System.out.println("Not yet implemented");
	}

	protected synchronized void finalize() throws Throwable {
		close();
		super.finalize();
	}
}
