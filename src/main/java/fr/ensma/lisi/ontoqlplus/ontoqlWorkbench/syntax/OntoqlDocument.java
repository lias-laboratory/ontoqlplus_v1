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
package fr.ensma.lisi.ontoqlplus.ontoqlWorkbench.syntax;

import java.awt.Color;
import java.util.Hashtable;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

/**
 * @author Stéphane JEAN
 */
public class OntoqlDocument extends DefaultStyledDocument {

	private static final long serialVersionUID = -4038530235883248484L;

	protected DocumentReader reader;

	protected SyntaxHighlighter lexer;

	protected Object doclock = new Object();

	protected Hashtable styles = new Hashtable();

	public OntoqlDocument() {
		initStyles();

		reader = new DocumentReader(this);
		lexer = new SyntaxHighlighter(this, reader, doclock, styles);
		lexer.start();
	}

	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
		synchronized (doclock) {
			super.insertString(offs, str, a);
			color(offs, str.length());
			reader.update(offs, str.length());
		}
	}

	public void remove(int offs, int len) throws BadLocationException {
		synchronized (doclock) {
			super.remove(offs, len);
			color(offs, -len);
			reader.update(offs, -len);
		}
	}

	protected void colorAll() {
		color(0, getLength());
	}

	protected void color(int position, int adjustment) {
		lexer.highlight(position, adjustment);
	}

	protected void initStyles() {
		SimpleAttributeSet keyword = new SimpleAttributeSet();
		StyleConstants.setBold(keyword, true);
		StyleConstants.setForeground(keyword, Color.BLUE);
		styles.put("keyword", keyword);

		SimpleAttributeSet string_lit = new SimpleAttributeSet();
		StyleConstants.setBold(string_lit, true);
		StyleConstants.setForeground(string_lit, Color.RED);
		styles.put("string-literal", string_lit);

		SimpleAttributeSet whiteSpace = new SimpleAttributeSet();
		StyleConstants.setBold(whiteSpace, false);
		StyleConstants.setForeground(whiteSpace, Color.BLACK);
		styles.put("whitespace", whiteSpace);

		SimpleAttributeSet plain = new SimpleAttributeSet();
		StyleConstants.setBold(plain, false);
		StyleConstants.setForeground(plain, Color.BLACK);
		styles.put("plain", plain);
	}
}