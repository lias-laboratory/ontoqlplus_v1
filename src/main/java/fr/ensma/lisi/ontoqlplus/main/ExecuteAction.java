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
package fr.ensma.lisi.ontoqlplus.main;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.StringReader;

import javax.swing.JFileChooser;

import fr.ensma.lisi.ontoql.exception.JOBDBCException;
import fr.ensma.lisi.ontoql.jobdbc.OntoQLResultSet;
import fr.ensma.lisi.ontoql.jobdbc.OntoQLSession;
import fr.ensma.lisi.ontoql.jobdbc.OntoQLStatement;
import fr.ensma.lisi.ontoql.util.JDBCHelper;
import fr.ensma.lisi.ontoql.util.OntoQLHelper;
import fr.ensma.lisi.ontoqlplus.common.AbstractExecuteAction;
import fr.ensma.lisi.ontoqlplus.common.util.GuiUtils;
import fr.ensma.lisi.ontoqlplus.ontoqlWorkbench.OntoQLWorkBench;

/**
 * @author Stéphane JEAN
 */
public class ExecuteAction extends AbstractExecuteAction {

	private static final long serialVersionUID = -8269057687157839591L;

	private Main main;

	private GuiUtils gUtils;

	private OntoQLWorkBench owb;

	private String editorText = "";

	private final static int DML = 0;

	private final static int DDL = 1;

	private String ontoQLStatement;

	public ExecuteAction(String name, Main parent) {
		super(name);
		this.main = parent;
		this.gUtils = new GuiUtils(parent);
	}

	public void actionPerformed(ActionEvent e) {
		// reinit the editor
		editorText = "";

		// must not be hard coded but for the moment Laura needs it
		main.configUI.getSession().setDefaultNameSpace("INTERNATIONAL_ID");

		// choose the file to be executed
		File scriptFile = chooseScriptFile();

		try {
			owb = main.openOntoqlWorkBench("", scriptFile);
			parseScript(scriptFile);
		} catch (Exception ex) {
			gUtils.displayError("Script execution error : " + ex.getMessage());
		}
	}

	/**
	 * Choose a .ontoql file with the SQL script
	 **/
	private File chooseScriptFile() {

		File result = null;

		int isOpenDialog = fc.showOpenDialog(main);
		if (isOpenDialog == JFileChooser.APPROVE_OPTION) {
			result = fc.getSelectedFile();
		}

		return result;
	}

	private void parseScript(File scriptFile) throws IOException {
		String scriptPath = scriptFile.getPath();
		FileReader freader = new FileReader(scriptPath);
		BufferedReader bufferedReader = new BufferedReader(freader);
		StringBuffer buffer = new StringBuffer();
		StreamTokenizer st = null;

		st = new StreamTokenizer(bufferedReader);
		st.eolIsSignificant(true); // EOL is considered in the reading
		st.slashStarComments(true); // C-like comments (/* */) are ignored
		st.slashSlashComments(true); // C-like comments (// ) are ignored
		st.ordinaryChar(' '); // white space will be considered a character to
		// be read
		st.ordinaryChar('/');
		st.ordinaryChar('\''); // the quotes will be considered a character to
		// be read (otherwise the token just after is
		// ignored)
		st.ordinaryChars(45, 57); // the characters in the range 45-57 have to
		// be read. They are the: "-", ".", "/", and
		// numbers from 0 to 9.
		st.wordChars(45, 57);

		do {
			st.nextToken();

			switch (st.ttype) {
			case StreamTokenizer.TT_NUMBER:
				buffer.append(st.nval); // append a number to the SQL buffer
				break;

			case StreamTokenizer.TT_WORD:
				buffer.append(st.sval); // append a word to the SQL buffer
				break;
			case ';': // When the ";" character is found and when in the End of
				// File,
			case StreamTokenizer.TT_EOF: // it marks the execution of the OntoQL
				// statement that is in the buffer.
				String bufferString = buffer.toString();
				bufferString = bufferString.trim();
				if (bufferString.length() != 0) {
					if (bufferString.startsWith("\n")) {
						bufferString = bufferString.replaceFirst("\n", "");
					}
					if (bufferString.startsWith("@")) {
						parseScript(new File(scriptFile.getParent() + "\\" + bufferString.substring(1) + ".ontoql"));
					} else {
						ontoQLStatement = bufferString;
						editorText += "\nOntoQL> " + ontoQLStatement + "\n\n";
						String result = performOntoQLStatement(ontoQLStatement);
						editorText += result;
					}
					// reinitialize buffer for next SQL statement
					buffer.delete(0, buffer.length());
					buffer.setLength(0);
				}
				break;

			default: // a single character found in ttype: "#", '>', EOL,
				// whitespace, etc.
				buffer.append((char) st.ttype); // append a character to the SQL
				// buffer
				break;
			}
		} while (st.ttype != StreamTokenizer.TT_EOF);

		owb.setEditorText("------------------------\n BEGINING OF THE SCRIPT\n------------------------\n " + editorText
				+ "\n-------------------\n END OF THE SCRIPT\n-------------------\n ");

		bufferedReader.close();

	}

	/**
	 * Discover whether the type of the SQL statement is a Query or manipulation
	 * verifies if it starts with SELECT or with some other keyword.
	 */
	private String performOntoQLStatement(String ontoqlStatement) throws IOException {
		String result = "";
		Reader bufferedReader = new StringReader(ontoqlStatement);
		StreamTokenizer st = null;

		st = new StreamTokenizer(bufferedReader);
		st.eolIsSignificant(false); // EOL is not considered in the reading
		st.slashStarComments(true); // C-like comments (/* */) are ignored
		st.slashSlashComments(true); // C-like comments (// ) are ignored

		st.nextToken();
		if (st.ttype == StreamTokenizer.TT_WORD) {
			if (st.sval.compareToIgnoreCase("SELECT") == 0) { // if the first
				// token is equal to
				// the word "SELECT"
				result = performOntoQLQuery(ontoqlStatement);
			} else if ((st.sval.compareToIgnoreCase("INSERT") == 0) || (st.sval.compareToIgnoreCase("UPDATE") == 0)
					|| (st.sval.compareToIgnoreCase("DELETE") == 0)) {
				// if it is not SELECT, it is not a query, so it needs to be
				// executed
				result = performOntoQLUpdate(ontoqlStatement, DML);
				// if the first token is not a word, then it is not to be
				// executed
				// ...
			} else {
				result = performOntoQLUpdate(ontoqlStatement, DDL);
			}
		}
		return result;
	}

	/**
	 * perform the OntoQL query and return result.
	 */
	private String performOntoQLQuery(String ontoqlQuery) {
		String result;

		try {
			OntoQLSession session = main.configUI.getSession();
			OntoQLStatement stmt = session.createOntoQLStatement();
			OntoQLResultSet rset = stmt.executeQuery(ontoqlQuery);
			result = JDBCHelper.resultSetToString(rset);
		} catch (JOBDBCException e) {
			result = "Error :" + e.getMessage();
		}

		return result;
	}

	/**
	 * perform the OntoQL update and return the number of rows affected.
	 */
	private String performOntoQLUpdate(String ontoqlQuery, int typeOfUpdate) {
		String result;

		try {
			OntoQLSession session = main.configUI.getSession();
			OntoQLStatement stmt = session.createOntoQLStatement();
			int nbRows = stmt.executeUpdate(ontoqlQuery);
			if (OntoQLHelper.isNamespaceParameterCommand(ontoqlQuery)) {
				main.updateAppMenuBar();
			}
			if (OntoQLHelper.isLanguageParameterCommand(ontoqlQuery)) {
				main.updateLanguageMenu();
			}
			if (typeOfUpdate == DML)
				result = nbRows + " rows affected\n";
			else {
				if (nbRows == 0) {
					result = "statement successfully executed\n";
				} else {
					result = "an error has occured\n";
				}
			}
		} catch (JOBDBCException e) {
			result = "Error: " + e.getMessage() + "\n";
		}

		return result;
	}
}
