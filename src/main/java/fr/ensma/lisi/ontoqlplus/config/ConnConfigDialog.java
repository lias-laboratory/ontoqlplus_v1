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
package fr.ensma.lisi.ontoqlplus.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JDialog;

import fr.ensma.lisi.ontoql.exception.JOBDBCException;
import fr.ensma.lisi.ontoql.jobdbc.OntoQLSession;
import fr.ensma.lisi.ontoql.jobdbc.impl.OntoQLSessionImpl;
import fr.ensma.lisi.ontoql.util.OntoQLHelper;
import fr.ensma.lisi.ontoqlplus.common.WaitAndBlockPane;
import fr.ensma.lisi.ontoqlplus.common.util.GuiUtils;
import fr.ensma.lisi.ontoqlplus.main.Main;

/**
 * @author Stéphane JEAN
 */
public class ConnConfigDialog extends JDialog {

	private static final long serialVersionUID = 1963822407952304906L;

	private OntoQLSession session;

	private Main main;

	public GuiUtils gUtils = new GuiUtils(this);

	private ConnConfigPanel ccp = new ConnConfigPanel(this);

	public ConnConfigDialog(Main main) {
		super(main, "Session configurations", true);
		this.main = main;
		setResizable(false);
		getContentPane().add(ccp);
		setGlassPane(new WaitAndBlockPane());
		pack();
		setLocationRelativeTo(main);
	}

	public void setSession(String url, String user, String password) throws SQLException {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (Exception ex) {
			gUtils.displayWarning("PostgreSQL driver is missing");
		}
		Connection c = DriverManager.getConnection(url, user, password);
		session = new OntoQLSessionImpl(c);
		session.setReferenceLanguage(OntoQLHelper.ENGLISH);
		main.updateAppMenuBar();

	}

	public OntoQLSession getSession() {
		return session;
	}

	public boolean sessionEstablished() {
		return session != null;
	}

	public void closeSession() {
		if (session != null) {
			try {
				session.close();
				System.out.println("Session successfully closed..."); // DEBUG
				session = null;
			} catch (JOBDBCException sqle) {
				main.setStatusBarText(sqle.getMessage());
				gUtils.displayWarning("Warning: Unable to close previously open " + "session:\n" + sqle.getMessage());
			}
		}
	}
}
