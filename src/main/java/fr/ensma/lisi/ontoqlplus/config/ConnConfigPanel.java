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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jdom.JDOMException;

import fr.ensma.lisi.ontoqlplus.common.util.GridBagUtil;
import fr.ensma.lisi.ontoqlplus.common.util.GuiUtils;
import fr.ensma.lisi.ontoqlplus.common.util.SwingWorker;
import fr.ensma.lisi.ontoqlplus.config.data_access.ConnConfigXmlDao;
import fr.ensma.lisi.ontoqlplus.main.Main;

/**
 * @author Stéphane JEAN
 */
public class ConnConfigPanel extends JPanel implements ListSelectionListener {

	private static final long serialVersionUID = 7465606041802229755L;

	private ConnConfigDialog parent;

	private GuiUtils gUtils = new GuiUtils(this);

	private final int COL_SIZE = 20;

	// Actions

	private final NewAction newAction = new NewAction();

	private final DeleteAction deleteAction = new DeleteAction();

	private final SaveAction saveAction = new SaveAction();

	private final ConnectAction connectAction = new ConnectAction();

	// Widgets
	private JList savedConfigsList;

	private DefaultListModel listModel;

	private final JTextField nameText = new JTextField(COL_SIZE);

	private final JTextField ipText = new JTextField(COL_SIZE);

	private final JTextField sidText = new JTextField(COL_SIZE);

	private final JTextField userText = new JTextField(COL_SIZE);

	private final JTextField portText = new JTextField("1521", COL_SIZE);

	private final JPasswordField passText = new JPasswordField(COL_SIZE);

	private final JButton newButton = new JButton(newAction);

	private final JButton deleteButton = new JButton(deleteAction);

	private final JButton saveButton = new JButton(saveAction);

	private final JButton connectButton = new JButton(connectAction);

	public ConnConfigPanel(ConnConfigDialog parent) {
		try {
			this.parent = parent;
			setLayout(new GridBagLayout());
			GridBagUtil.add(this, createSavedConfigsPanel(), 0, 0, 0, 0, 1.0d, 1.0d, GridBagConstraints.BOTH,
					GridBagConstraints.NORTH, new Insets(12, 12, 6, 12));
			GridBagUtil.add(this, createConfigPanel(), 0, 1, 0, 0, 0.0d, 0.0d, GridBagConstraints.HORIZONTAL,
					GridBagConstraints.NORTH, new Insets(0, 12, 12, 11));
		} catch (JDOMException je) {
			gUtils.displayWarning("Corrupt configuration file");
			je.printStackTrace(); // DEBUG
		}
	}

	public void waitCursor(boolean on) {
		if (on)
			parent.getGlassPane().setVisible(true);
		else
			parent.getGlassPane().setVisible(false);
	}

	public String getSchema() {
		return userText.getText().trim();
	}

	// -------------------------------------------------------
	// ListSelectionListener interface implementation
	// -------------------------------------------------------

	public void valueChanged(ListSelectionEvent lse) {
		if (lse.getValueIsAdjusting()) {
			deleteButton.setEnabled(true);
			return;
		}

		JList list = (JList) lse.getSource();
		if (!list.isSelectionEmpty()) {
			ConnConfig config = (ConnConfig) list.getSelectedValue();
			nameText.setText(config.getName());
			ipText.setText(config.getIp());
			portText.setText(Integer.toString(config.getPort()));
			sidText.setText(config.getSid());
			userText.setText(config.getUser());
			passText.setText(config.getPassword());
		}
	}

	private JPanel createSavedConfigsPanel() throws JDOMException {
		//
		// savedConfigsPanel
		JPanel savedConfigsPanel = new JPanel();
		savedConfigsPanel.setLayout(new GridBagLayout());
		//
		// listModel
		listModel = new SortedListModel();
		Vector savedConfigs = ConnConfigXmlDao.loadSavedConfigs();
		for (int i = 0; i < savedConfigs.size(); i++)
			listModel.addElement(savedConfigs.get(i));
		//
		// savedConfigsList and scroller
		savedConfigsList = new JList(listModel);
		savedConfigsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		savedConfigsList.addListSelectionListener(this);
		if (!listModel.isEmpty())
			savedConfigsList.setSelectedIndex(0);
		JScrollPane scrollPane = new JScrollPane(savedConfigsList);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		GridBagUtil.add(savedConfigsPanel, scrollPane, 0, 0, 0, 0, 1.0d, 1.0d, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, new Insets(0, 0, 0, 5));
		//
		// buttonPanel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridBagLayout());
		//
		// newButton
		GridBagUtil.add(buttonPanel, newButton, 0, 0, 0, 0, 0.0d, 0.0d, GridBagConstraints.HORIZONTAL,
				GridBagConstraints.NORTH, new Insets(0, 0, 0, 0));
		//
		// deleteButton
		GridBagUtil.add(buttonPanel, deleteButton, 0, 1, 0, 0, 0.0d, 0.0d, GridBagConstraints.HORIZONTAL,
				GridBagConstraints.NORTH, new Insets(0, 0, 0, 0));

		GridBagUtil.add(savedConfigsPanel, buttonPanel, 1, 0, 0, 0, 0.0d, 0.0d, GridBagConstraints.HORIZONTAL,
				GridBagConstraints.NORTH, new Insets(0, 0, 0, 0));

		return savedConfigsPanel;
	}

	private JPanel createConfigPanel() {
		JPanel fieldsPanel = new JPanel();
		fieldsPanel.setLayout(new GridBagLayout());
		//
		// nameLabel
		JLabel nameLabel = new JLabel("Connection Name:");
		GridBagUtil.add(fieldsPanel, nameLabel, 0, 0, 0, 0, 0.0d, 0.0d, GridBagConstraints.NONE,
				GridBagConstraints.EAST, new Insets(0, 0, 0, 0));
		//
		// ipLabel
		JLabel ipLabel = new JLabel("IP/Server Name:");
		GridBagUtil.add(fieldsPanel, ipLabel, 0, 1, 0, 0, 0.0d, 0.0d, GridBagConstraints.NONE, GridBagConstraints.EAST,
				new Insets(5, 0, 0, 0));
		//
		// portLabel
		JLabel portLabel = new JLabel("Port:");
		GridBagUtil.add(fieldsPanel, portLabel, 0, 2, 0, 0, 0.0d, 0.0d, GridBagConstraints.NONE,
				GridBagConstraints.EAST, new Insets(5, 0, 0, 0));
		//
		// sidLabel
		JLabel sidLabel = new JLabel("SID:");
		GridBagUtil.add(fieldsPanel, sidLabel, 0, 3, 0, 0, 0.0d, 0.0d, GridBagConstraints.NONE, GridBagConstraints.EAST,
				new Insets(5, 0, 0, 0));
		//
		// userLabel
		JLabel userLabel = new JLabel("User:");
		GridBagUtil.add(fieldsPanel, userLabel, 0, 4, 0, 0, 0.0d, 0.0d, GridBagConstraints.NONE,
				GridBagConstraints.EAST, new Insets(5, 0, 0, 0));
		//
		// passLabel
		JLabel passLabel = new JLabel("Password:");
		GridBagUtil.add(fieldsPanel, passLabel, 0, 5, 0, 0, 0.0d, 0.0d, GridBagConstraints.NONE,
				GridBagConstraints.EAST, new Insets(5, 0, 0, 0));
		//
		// nameText
		GridBagUtil.add(fieldsPanel, nameText, 1, 0, 0, 0, 1.0d, 0.0d, GridBagConstraints.NONE, GridBagConstraints.WEST,
				new Insets(0, 6, 0, 0));
		//
		// ipText
		GridBagUtil.add(fieldsPanel, ipText, 1, 1, 0, 0, 1.0d, 0.0d, GridBagConstraints.NONE, GridBagConstraints.WEST,
				new Insets(5, 6, 0, 0));
		//
		// portText
		GridBagUtil.add(fieldsPanel, portText, 1, 2, 0, 0, 1.0d, 0.0d, GridBagConstraints.NONE, GridBagConstraints.WEST,
				new Insets(5, 6, 0, 0));
		//
		// sidText
		GridBagUtil.add(fieldsPanel, sidText, 1, 3, 0, 0, 1.0d, 0.0d, GridBagConstraints.NONE, GridBagConstraints.WEST,
				new Insets(5, 6, 0, 0));
		//
		// userText
		GridBagUtil.add(fieldsPanel, userText, 1, 4, 0, 0, 1.0d, 0.0d, GridBagConstraints.NONE, GridBagConstraints.WEST,
				new Insets(5, 6, 0, 0));
		//
		// passText
		GridBagUtil.add(fieldsPanel, passText, 1, 5, 0, 0, 1.0d, 0.0d, GridBagConstraints.NONE, GridBagConstraints.WEST,
				new Insets(5, 6, 0, 0));
		//
		// buttonPanel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridBagLayout());
		//
		// saveButton
		// make save button same size as connect button to maintain consistency
		// across LnF
		saveButton.setPreferredSize(connectButton.getPreferredSize());
		GridBagUtil.add(buttonPanel, saveButton, 0, 0, 0, 0, 1.0d, 0.0d, GridBagConstraints.NONE,
				GridBagConstraints.EAST, new Insets(0, 0, 0, 0));
		//
		// connectButton
		GridBagUtil.add(buttonPanel, connectButton, 1, 0, 0, 0, 0.0d, 0.0d, GridBagConstraints.NONE,
				GridBagConstraints.EAST, new Insets(0, 0, 0, 0));
		//
		// configPanel
		JPanel configPanel = new JPanel();
		configPanel.setLayout(new GridBagLayout());
		GridBagUtil.add(configPanel, fieldsPanel, 0, 0, 0, 0, 1.0d, 0.0d, GridBagConstraints.HORIZONTAL,
				GridBagConstraints.NORTH, new Insets(0, 0, 17, 0));
		GridBagUtil.add(configPanel, buttonPanel, 0, 1, 0, 0, 0.0d, 0.0d, GridBagConstraints.NONE,
				GridBagConstraints.EAST, new Insets(0, 0, 0, 0));

		return configPanel;
	}

	private void clearFields() {
		nameText.setText("");
		ipText.setText("");
		portText.setText("1521");
		sidText.setText("");
		userText.setText("");
		passText.setText("");
	}

	@SuppressWarnings("unchecked")
	private void persist() {
		try {
			Vector configs = new Vector(listModel.size());
			for (int i = 0; i < listModel.size(); i++)
				configs.add(listModel.get(i));
			ConnConfigXmlDao.writeConfigs(configs);
		} catch (IOException ioe) {
			gUtils.displayWarning("Warning: Couldn't create connection configuration file.\n"
					+ "You will not be able to save connection configuration data.");
		}
	}

	private boolean fieldsValidate() {
		if (empty(nameText, "Name") || empty(ipText, "IP/Server Name") || empty(portText, "Port")
				|| empty(sidText, "SID") || empty(userText, "User"))
			return false;
		else
			return true;
	}

	private boolean empty(JTextField field, String id) {
		if ("".equals(field.getText())) {
			JOptionPane.showMessageDialog(parent, id + " must not be empty.", "Problem with configuration",
					JOptionPane.ERROR_MESSAGE);
			field.requestFocus();
			return true;
		}
		return false;
	}

	// NEW ACTION
	private class NewAction extends AbstractAction {

		private static final long serialVersionUID = -1984794211343011662L;

		public NewAction() {
			super("New");
		}

		public void actionPerformed(ActionEvent ae) {

			deleteButton.setEnabled(false);
			int i = savedConfigsList.getSelectedIndex();
			savedConfigsList.removeSelectionInterval(i, i);
			clearFields();
			nameText.requestFocus();
		}
	}

	// DELETE ACTION
	private class DeleteAction extends AbstractAction {

		private static final long serialVersionUID = 2011305255498017103L;

		public DeleteAction() {
			super("Delete");
		}

		public void actionPerformed(ActionEvent ae) {

			if (listModel.isEmpty())
				return;
			if (!savedConfigsList.isSelectionEmpty()) {
				int confirmed = gUtils.requestConfirm("Are you sure you want to delete?");
				if (confirmed == JOptionPane.YES_OPTION) {
					int selectedIndex = savedConfigsList.getSelectedIndex();
					listModel.remove(selectedIndex);
					// if the last Config was removed from the list, clear the
					// fields but dont select
					if (listModel.isEmpty())
						clearFields();
					else
						savedConfigsList.setSelectedIndex(
								selectedIndex == listModel.getSize() ? listModel.getSize() - 1 : selectedIndex);
					persist();
				}
			}
		}
	}

	// SAVE ACTION
	private class SaveAction extends AbstractAction {

		private static final long serialVersionUID = -150576847273255673L;

		public SaveAction() {
			super("Save");
		}

		public void actionPerformed(ActionEvent ae) {

			if (fieldsValidate()) {
				ConnConfig config = new ConnConfig(nameText.getText().trim(), ipText.getText().trim(),
						Integer.parseInt(portText.getText().trim()), sidText.getText().trim(),
						userText.getText().trim(), new String(passText.getPassword()));
				// does Config with same name already exist?
				if (!listModel.contains(config)) {
					// handleNew will clear the selections so we can test if the
					// save is for a new Config or to edit an old one
					if (!savedConfigsList.isSelectionEmpty()) // edit being
						// performed
						listModel.remove(savedConfigsList.getSelectedIndex()); // don't
					// retain
					// old
					// Config
					listModel.addElement(config);
					savedConfigsList.setSelectedIndex(listModel.indexOf(config));
					persist();
				} else {
					gUtils.displayError("Configuration already exists.");
				}
			}
		}
	}

	// SESSION ACTION
	private class ConnectAction extends AbstractAction {

		private static final long serialVersionUID = -7512306090808929846L;

		public ConnectAction() {
			super("Begin session");
		}

		public void actionPerformed(ActionEvent ae) {

			if (fieldsValidate()) {
				final ConnConfig config = new ConnConfig(nameText.getText().trim(), ipText.getText().trim(),
						Integer.parseInt(portText.getText().trim()), sidText.getText().trim(),
						userText.getText().trim(), new String(passText.getPassword()));

				SwingWorker worker = new SwingWorker() {
					public Object construct() {

						waitCursor(true);
						try {
							parent.setSession(config.url(), userText.getText(), passText.getText()); // long op
						} catch (final SQLException sqle) {
							Runnable doError = new Runnable() {
								public void run() {

									gUtils.displayError("Unable to establish a connection: " + sqle.getMessage());
								}
							};
							SwingUtilities.invokeLater(doError);
						}
						return null;
					}

					public void finished() {

						waitCursor(false);
						if (parent.sessionEstablished()) {
							parent.setVisible(false);
							((Main) parent.getOwner()).openOntoqlWorkBench();
						}
					}
				};
				worker.start();
			}
		}
	}

	protected class SortedListModel extends DefaultListModel {

		private static final long serialVersionUID = 8598825462614161140L;

		@SuppressWarnings("unchecked")
		public void addElement(Object element) {
			super.addElement(element);
			Enumeration e = super.elements();
			Vector v = new Vector();
			while (e.hasMoreElements())
				v.add(e.nextElement());
			Collections.sort(v);
			super.clear();
			for (int i = 0; i < v.size(); i++)
				super.addElement(v.get(i));
		}
	}
}