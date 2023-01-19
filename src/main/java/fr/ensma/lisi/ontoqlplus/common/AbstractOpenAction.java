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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 * @author Stéphane JEAN
 */
public abstract class AbstractOpenAction extends AbstractAction {

	private static final long serialVersionUID = 4960104943665527299L;

	protected JFileChooser fc = new JFileChooser();

	public AbstractOpenAction(String name) {
		super(name, Environment.OPEN_ICON);
		fc.addChoosableFileFilter(new FileFilter() {
			public boolean accept(File f) {
				if (f.isDirectory())
					return true;

				String fileName = f.getName();
				String ext = getExt(fileName).toLowerCase();
				if (ext != null) {
					if (ext.equals(Environment.ddlFileExt) || ext.equals(Environment.sqlFileExt))
						return true;
				}
				return false;
			}

			public String getDescription() {
				return "Sql Files(*.sql, *.ddl)";
			}

			private String getExt(String fileName) {
				StringTokenizer st = new StringTokenizer(fileName, ".");
				String ext = null;
				while (st.hasMoreTokens())
					ext = st.nextToken();
				return ext;
			}
		});
	}

	protected String readFile(File file) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		StringBuffer sb = new StringBuffer();
		String line;
		while ((line = br.readLine()) != null)
			sb.append(line + "\n");
		br.close();
		return sb.toString();
	}
}
