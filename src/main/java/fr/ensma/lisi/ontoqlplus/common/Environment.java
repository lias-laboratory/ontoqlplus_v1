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

import java.io.File;

import javax.swing.ImageIcon;

/**
 * @author Stéphane JEAN
 */
public class Environment {

	public static final String CONFIG_DIR_PATH = System.getProperty("user.home") + File.separator + ".ProDBA"
			+ File.separator;
	public static final String CONN_CONFIG_FILE_PATH = CONFIG_DIR_PATH + "connections.conf";

	public static final ImageIcon OPEN_ICON = new ImageIcon(
			Environment.class.getClassLoader().getResource("images/open.gif"));
	public static final ImageIcon SAVE_ICON = new ImageIcon(
			Environment.class.getClassLoader().getResource("images/save.gif"));
	public static final ImageIcon SAVE_AS_ICON = new ImageIcon(
			Environment.class.getClassLoader().getResource("images/save_as.gif"));
	public static final ImageIcon RUN_ICON = new ImageIcon(
			Environment.class.getClassLoader().getResource("images/run_arrow.gif"));
	public static final ImageIcon SPARQL_ICON = new ImageIcon(
			Environment.class.getClassLoader().getResource("images/sparql.gif"));
	public static final ImageIcon GENERATED_SQL_ICON = new ImageIcon(
			Environment.class.getClassLoader().getResource("images/generated_sql.gif"));

	public static final ImageIcon ENGLISH_ICON = new ImageIcon(
			Environment.class.getClassLoader().getResource("images/english.gif"));
	public static final ImageIcon FRENCH_ICON = new ImageIcon(
			Environment.class.getClassLoader().getResource("images/french.gif"));
	public static final ImageIcon SPANISH_ICON = new ImageIcon(
			Environment.class.getClassLoader().getResource("images/spanish.gif"));

	public static final ImageIcon PK_KEY_ICON = new ImageIcon(
			Environment.class.getClassLoader().getResource("images/pk.gif"));
	public static final ImageIcon PLUG_ICON = new ImageIcon(
			Environment.class.getClassLoader().getResource("images/plug.gif"));

	public static final String ddlFileExt = "ddl";
	public static final String sqlFileExt = "sql";
	public static final String ontoqlFileExt = "ontoql";
}
