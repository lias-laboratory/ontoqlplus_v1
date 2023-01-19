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
import fr.ensma.lisi.ontoql.util.StringHelper;

/**
 * @author Stéphane JEAN
 */
public class DataBoundTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -4404731923427616665L;

	private OntoQLSession session;

	private OntoQLStatement statement;

	private ResultSet resultSet;

	private ResultSetMetaData rmeta;

	public String sqlString;

	private String columnNames[];

	private int columnTypes[];

	private boolean writables[];

	private Vector rows;

	public DataBoundTableModel(OntoQLSession s) {
		session = s;
		columnNames = new String[0];
		rows = new Vector();
	}

	public synchronized long executeQuery(String sql) throws JOBDBCException {
		statement = session.createOntoQLStatement();
		Date start = new Date();
		resultSet = statement.executeQuery(sql);
		sqlString = StringHelper.formatSQL(statement.getSQLString());
		Date end = new Date();
		return end.getTime() - start.getTime();
	}

	public synchronized long executeSPARQLQuery(String sql) throws JOBDBCException {
		statement = session.createOntoQLStatement();
		Date start = new Date();
		resultSet = statement.executeSPARQLQuery(sql);
		sqlString = StringHelper.formatSQL(statement.getSQLString());
		Date end = new Date();
		return end.getTime() - start.getTime();
	}

	public synchronized int executeUpdate(String sql) throws SQLException {
		statement = session.createOntoQLStatement();
		return statement.executeUpdate(sql);
	}

	@SuppressWarnings("unchecked")
	public synchronized void bind() throws SQLException {
		rmeta = resultSet.getMetaData();
		int numCols = rmeta.getColumnCount();
		// column names
		columnNames = new String[numCols];
		for (int col = 0; col < numCols; col++)
			columnNames[col] = rmeta.getColumnName(col + 1);
		// column types
		columnTypes = new int[numCols];
		for (int col = 1; col < numCols; col++)
			columnTypes[col] = rmeta.getColumnType(col + 1);
		// writables
		writables = new boolean[numCols];
		for (int col = 1; col < numCols; col++)
			writables[col] = rmeta.isWritable(col + 1);

		rows = new Vector();
		while (resultSet.next()) {
			Vector aRow = new Vector(numCols);
			for (int i = 1; i <= numCols; i++)
				aRow.addElement(resultSet.getObject(i));
			rows.add(aRow);
		}
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
