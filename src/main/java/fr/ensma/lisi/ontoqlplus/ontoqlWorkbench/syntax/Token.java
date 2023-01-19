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
 * A generic abstract token class which all tokens should extend
 */
public abstract class Token {

	/**
	 * A unique ID for this token which should be a static variable of a subclass
	 */
	public abstract int getId();

	/**
	 * A description of this token
	 */
	public abstract String getDesc();

	/**
	 * The actual token's text
	 */
	public abstract String getContent();

	/**
	 * Tells whether or not the token is a comment so you can decide if you want to
	 * ignore them or not
	 */
	// public abstract boolean isComment();

	/**
	 * Tells whether or not this token is whitespace so you can decide if you want
	 * to ignore it or not
	 */
	public abstract boolean isWhiteSpace();

	/**
	 * Return the line number which this token started
	 */
	public abstract int getLineNumber();

	/**
	 * The offset into input in characters which this token started
	 */
	public abstract int getCharBegin();

	/**
	 * The offset into input in characters which this token ended
	 */
	public abstract int getCharEnd();

	/**
	 * The state of the lexer if undefined
	 */
	public static final int UNDEFINED_STATE = -1;

	/**
	 * The state when the lexer is ready
	 */
	public static final int INITIAL_STATE = 0;

	/**
	 * An integer returning the state the lexer is in after returning this token
	 */
	public abstract int getState();
}