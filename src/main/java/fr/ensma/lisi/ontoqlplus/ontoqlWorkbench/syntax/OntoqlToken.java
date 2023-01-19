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

/**
 * @author Stéphane JEAN
 */
public class OntoqlToken extends Token {

	/**
	 * Represents a transact sql keyword
	 **/
	public static final int KEYWORD = 100;

	/**
	 * Represents a string enclosed in single quotes
	 */
	public static final int STRING_LITERAL = 101;

	/**
	 * Represents whitespace
	 */
	public static final int WHITESPACE = 102;

	/**
	 * Plain text
	 */
	public static final int PLAIN = 103;

	private int id;

	private String contents;

	private int lineNumber;

	private int p0;

	private int p1;

	private int state;

	/**
	 * Create a new SqlToken with undefined state. This is called by the lexer
	 */
	public OntoqlToken(int id, String contents, int lineNumber, int charBegin, int charEnd) {
		this(id, contents, lineNumber, charBegin, charEnd, Token.UNDEFINED_STATE);
	}

	/**
	 * Create a new SqlToken. This is called by the lexer
	 */
	public OntoqlToken(int id, String contents, int lineNumber, int charBegin, int charEnd, int state) {
		this.id = id;
		this.contents = contents;
		this.lineNumber = lineNumber;
		this.p0 = charBegin;
		this.p1 = charEnd;
		this.state = state;
	}

	public int getId() {
		return id;
	}

	public String getContent() {
		return contents;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public int getCharBegin() {
		return p0;
	}

	public int getCharEnd() {
		return p1;
	}

	public int getState() {
		return state;
	}

	public boolean isWhiteSpace() {
		return id == WHITESPACE;
	}

	public boolean isStringLiteral() {
		return id == STRING_LITERAL;
	}

	public boolean isKeyWord() {
		return id == KEYWORD;
	}

	public boolean isPlainText() {
		return id == PLAIN;
	}

	public String getDesc() {
		if (isKeyWord()) {
			return "keyword";
		} else if (isWhiteSpace()) {
			return "whitespace";
		} else if (isStringLiteral()) {
			return "string-literal";
		} else if (isPlainText()) {
			return "plain";
		} else {
			return "no value for this one";
		}
	}
}