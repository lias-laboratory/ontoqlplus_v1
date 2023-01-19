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

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import fr.ensma.lisi.ontoqlplus.common.util.GridBagUtil;
import fr.ensma.lisi.ontoqlplus.common.util.GuiUtils;
import fr.ensma.lisi.ontoqlplus.ontoqlWorkbench.history.CommandHistoryListModel;
import fr.ensma.lisi.ontoqlplus.ontoqlWorkbench.history.HistoryItem;
import fr.ensma.lisi.ontoqlplus.ontoqlWorkbench.syntax.OntoqlDocument;

/**
 * @author Mickael BARON
 */
public class CommandEditorPanel extends JPanel {

	private static final long serialVersionUID = -5268356334143993920L;

	private final JTextPane sqlEditorPane = new JTextPane();

	private final JLabel historyLabel = new JLabel("Session Command History");

	private final CommandHistoryListModel historyComboModel = new CommandHistoryListModel();

	private final JComboBox historyCombo;

	private final JButton clearButton = new JButton("Clear");

	private final GuiUtils gUtils;

	public CommandEditorPanel() {
		gUtils = new GuiUtils(this);
		historyCombo = new JComboBox(historyComboModel);

		setLayout(new GridBagLayout());

		GridBagUtil.add(this, createEditorPanel(), 0, 0, 0, 0, 1.0d, 1.0d, GridBagConstraints.BOTH,
				GridBagConstraints.WEST, new Insets(0, 0, 2, 0));
		GridBagUtil.add(this, createHistoryPanel(), 0, 1, 0, 0, 1.0d, 0.0d, GridBagConstraints.HORIZONTAL,
				GridBagConstraints.WEST, new Insets(0, 6, 3, 6));
	}

	public String getCommand() {
		return sqlEditorPane.getText();
	}

	public void addHistoryItem(String command) {
		historyComboModel.addElement(new HistoryItem(command));
	}

	public void setEditorText(String text) {
		sqlEditorPane.setText(text);
	}

	public String getEditorText() {
		return sqlEditorPane.getText();
	}

	public void addEditorDocumentListener(DocumentListener l) {
		sqlEditorPane.getDocument().addDocumentListener(l);
	}

	private JPanel createEditorPanel() {
		JPanel editorPanel = new JPanel();
		editorPanel.setLayout(new GridBagLayout());

		sqlEditorPane.setEditable(true);
		sqlEditorPane.setDocument(new OntoqlDocument());
		sqlEditorPane.setPreferredSize(new Dimension(getWidth() / 2, getHeight() / 2));
		sqlEditorPane.setFont(new Font("Courier", 0, 13));

		JScrollPane scrollPane = new JScrollPane(sqlEditorPane);
		scrollPane.setVerticalScrollBarPolicy(22);
		scrollPane.setHorizontalScrollBarPolicy(31);
		GridBagUtil.add(editorPanel, scrollPane, 0, 0, 0, 0, 1.0d, 1.0d, GridBagConstraints.BOTH,
				GridBagConstraints.NORTH, new Insets(0, 0, 2, 0));

		return editorPanel;
	}

	private JPanel createHistoryPanel() {
		JPanel historyPanel = new JPanel();
		historyPanel.setLayout(new GridBagLayout());

		historyLabel.setEnabled(false);
		GridBagUtil.add(historyPanel, historyLabel, 0, 0, 0, 0, 0.0d, 0.0d, GridBagConstraints.NONE,
				GridBagConstraints.WEST, new Insets(0, 0, 0, 6));

		historyCombo.setEditable(false);
		historyCombo.setEnabled(false);
		historyCombo.addItemListener(new HistoryItemListener());
		historyComboModel.addListDataListener(new HistoryDataListener());
		GridBagUtil.add(historyPanel, historyCombo, 1, 0, 0, 0, 1.0d, 0.0d, GridBagConstraints.HORIZONTAL,
				GridBagConstraints.WEST, new Insets(0, 0, 0, 3));

		clearButton.setEnabled(false);
		clearButton.addActionListener(new ClearActionListener());
		GridBagUtil.add(historyPanel, clearButton, 2, 0, 0, 0, 0.0d, 0.0d, GridBagConstraints.NONE,
				GridBagConstraints.WEST, new Insets(0, 0, 0, 0));

		return historyPanel;
	}

	protected class HistoryItemListener implements ItemListener {
		private HistoryItemListener() {
			super();
		}

		public void itemStateChanged(ItemEvent ie) {
			if (ie.getStateChange() == 1) {
				String histText = ((HistoryItem) historyComboModel.getSelectedItem()).getSql();
				sqlEditorPane.setText(histText);
			}
		}
	}

	protected class ClearActionListener implements ActionListener {
		private ClearActionListener() {
			super();
		}

		public void actionPerformed(ActionEvent ae) {
			int confirm = gUtils.requestConfirm("Are you sure you want to clear history?");
			if (confirm == JOptionPane.NO_OPTION) {
				return;
			} else {
				historyComboModel.removeAllElements();
				return;
			}
		}
	}

	protected class HistoryDataListener implements ListDataListener {
		private HistoryDataListener() {
			super();
		}

		public void intervalAdded(ListDataEvent lde) {
			historyLabel.setEnabled(true);
			clearButton.setEnabled(true);
			historyCombo.setEnabled(true);
		}

		public void intervalRemoved(ListDataEvent lde) {
			historyLabel.setEnabled(false);
			clearButton.setEnabled(false);
			historyCombo.setEnabled(false);
		}

		public void contentsChanged(ListDataEvent listdataevent) { /*
																	 * Not supported
																	 */
		}
	}
}
