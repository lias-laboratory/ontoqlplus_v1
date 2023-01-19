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

import java.io.*;

/**
 * @author Stéphane JEAN
 */
public class OntoqlLexer implements Lexer {

	/** This character denotes the end of file */
	public static final int YYEOF = -1;

	/** initial size of the lookahead buffer */
	private static final int ZZ_BUFFERSIZE = 16384;

	/** lexical states */
	public static final int YYINITIAL = 0;

	/**
	 * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
	 * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l at the
	 * beginning of a line l is of the form l = 2*k, k a non negative integer
	 */
	private static final int ZZ_LEXSTATE[] = { 0, 0 };

	/**
	 * Translates characters to character classes
	 */
	private static final char[] ZZ_CMAP = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 2, 0, 3, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 5, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 22, 20, 9, 26, 7, 11, 17, 16, 24, 28, 29, 8, 14, 25, 13, 19, 0, 12, 6, 10, 18, 23, 15, 27, 21,
			0, 0, 4, 0, 0, 0, 0, 22, 20, 9, 26, 7, 11, 17, 16, 24, 28, 29, 8, 14, 25, 13, 19, 0, 12, 6, 10, 18, 23, 15,
			27, 21, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

	/**
	 * Translates DFA states to action switch labels.
	 */
	private static final int[] ZZ_ACTION = zzUnpackAction();

	private static final String ZZ_ACTION_PACKED_0 = "\1\0\2\1\1\2\1\1\1\3\24\1\2\3\15\1"
			+ "\2\4\2\1\1\4\10\1\1\4\4\1\1\4\6\1" + "\1\3\61\1\1\4\30\1";

	private static int[] zzUnpackAction() {
		int[] result = new int[141];
		int offset = 0;
		offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
		return result;
	}

	private static int zzUnpackAction(String packed, int offset, int[] result) {
		int i = 0; /* index in packed string */
		int j = offset; /* index in unpacked array */
		int l = packed.length();
		while (i < l) {
			int count = packed.charAt(i++);
			int value = packed.charAt(i++);
			do
				result[j++] = value;
			while (--count > 0);
		}
		return j;
	}

	/**
	 * Translates a state to a row index in the transition table
	 */
	private static final int[] ZZ_ROWMAP = zzUnpackRowMap();

	private static final String ZZ_ROWMAP_PACKED_0 = "\0\0\0\36\0\74\0\132\0\132\0\170\0\226\0\264"
			+ "\0\322\0\360\0\u010e\0\u012c\0\u014a\0\u0168\0\u0186\0\u01a4"
			+ "\0\u01c2\0\u01e0\0\u01fe\0\u021c\0\u023a\0\u0258\0\u0276\0\u0294"
			+ "\0\u02b2\0\u02d0\0\u02ee\0\36\0\u030c\0\u032a\0\u0348\0\u0366"
			+ "\0\u0384\0\u03a2\0\u03c0\0\u03de\0\u03fc\0\u041a\0\u0438\0\u0456"
			+ "\0\u0474\0\36\0\u0492\0\u04b0\0\u04ce\0\u04ec\0\u050a\0\u0528"
			+ "\0\u0546\0\u0564\0\u0582\0\u05a0\0\u05be\0\u05dc\0\u05fa\0\u0618"
			+ "\0\u0636\0\u0654\0\u0672\0\u0690\0\u06ae\0\u06cc\0\u06ea\0\u0708"
			+ "\0\u0726\0\u0744\0\74\0\u0762\0\u0780\0\u079e\0\u07bc\0\u07da"
			+ "\0\u07f8\0\u0816\0\u0834\0\u0852\0\u0870\0\u088e\0\u08ac\0\u08ca"
			+ "\0\u08e8\0\u0906\0\u0924\0\u0942\0\u0960\0\u097e\0\u099c\0\u09ba"
			+ "\0\u09d8\0\u09f6\0\u0a14\0\u0a32\0\u0a50\0\u0a6e\0\u0a8c\0\u0aaa"
			+ "\0\u0ac8\0\u0ae6\0\u0b04\0\u0b22\0\u0b40\0\u0b5e\0\u0b7c\0\u0b9a"
			+ "\0\u0bb8\0\u0bd6\0\u0bf4\0\u0c12\0\u0c30\0\u0c4e\0\u0c6c\0\u0c8a"
			+ "\0\u0ca8\0\u0cc6\0\u0ce4\0\u0d02\0\u0d20\0\u0d3e\0\u0d5c\0\u0d7a"
			+ "\0\u0d98\0\u0db6\0\u0dd4\0\u0df2\0\u0e10\0\u0e2e\0\u0e4c\0\u0e6a"
			+ "\0\u0e88\0\u0ea6\0\u0ec4\0\u0ee2\0\u0f00\0\u0f1e\0\u0f3c\0\u0f5a"
			+ "\0\u0f78\0\u0f96\0\u0fb4\0\u0fd2\0\u0ff0";

	private static int[] zzUnpackRowMap() {
		int[] result = new int[141];
		int offset = 0;
		offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
		return result;
	}

	private static int zzUnpackRowMap(String packed, int offset, int[] result) {
		int i = 0; /* index in packed string */
		int j = offset; /* index in unpacked array */
		int l = packed.length();
		while (i < l) {
			int high = packed.charAt(i++) << 16;
			result[j++] = high | packed.charAt(i++);
		}
		return j;
	}

	/**
	 * The transition table of the DFA
	 */
	private static final int[] ZZ_TRANS = zzUnpackTrans();

	private static final String ZZ_TRANS_PACKED_0 = "\1\2\1\3\1\4\1\5\1\2\1\6\1\7\1\10"
			+ "\1\11\1\12\1\13\1\14\1\15\1\16\1\2\1\17" + "\1\20\1\21\1\22\1\23\1\24\1\2\1\25\1\26"
			+ "\1\27\1\30\1\31\1\2\1\32\2\2\3\0\32\2" + "\40\0\2\4\32\0\1\6\1\33\1\0\1\33\1\2"
			+ "\1\34\30\6\1\2\3\0\3\2\1\35\27\2\3\0" + "\4\2\1\36\20\2\1\37\1\2\1\40\3\2\3\0"
			+ "\3\2\1\41\16\2\1\42\1\2\1\43\6\2\3\0" + "\10\2\1\44\11\2\1\36\10\2\3\0\14\2\1\45"
			+ "\4\2\1\46\11\2\3\0\10\2\1\47\13\2\1\50" + "\6\2\3\0\24\2\1\51\6\2\3\0\7\2\1\52"
			+ "\1\53\5\2\1\54\1\55\5\2\1\56\5\2\3\0" + "\14\2\1\57\16\2\3\0\22\2\1\60\10\2\3\0"
			+ "\10\2\1\61\22\2\3\0\2\2\1\62\14\2\1\63" + "\5\2\1\64\5\2\3\0\10\2\1\65\22\2\3\0"
			+ "\3\2\1\66\15\2\1\52\11\2\3\0\2\2\1\67" + "\1\2\1\70\1\2\1\71\16\2\1\72\5\2\3\0"
			+ "\22\2\1\73\10\2\3\0\2\2\1\52\22\2\1\74" + "\5\2\3\0\11\2\1\75\4\2\1\76\3\2\1\77"
			+ "\10\2\3\0\3\2\1\100\20\2\1\101\6\2\3\0" + "\11\2\1\102\20\2\2\33\1\0\1\33\1\0\1\103"
			+ "\30\33\1\2\3\0\4\2\1\104\1\2\1\52\24\2" + "\3\0\2\2\1\105\30\2\3\0\6\2\1\106\17\2"
			+ "\1\52\4\2\3\0\5\2\1\107\25\2\3\0\7\2" + "\1\110\23\2\3\0\25\2\1\111\5\2\3\0\31\2"
			+ "\1\105\1\2\3\0\3\2\1\112\27\2\3\0\3\2" + "\1\113\27\2\3\0\17\2\1\114\13\2\3\0\11\2"
			+ "\1\115\21\2\3\0\4\2\1\54\26\2\3\0\15\2" + "\1\116\15\2\3\0\26\2\1\117\4\2\3\0\6\2"
			+ "\1\117\24\2\3\0\6\2\1\120\24\2\3\0\4\2" + "\1\121\26\2\3\0\3\2\1\122\27\2\3\0\23\2"
			+ "\1\62\7\2\3\0\11\2\1\123\21\2\3\0\24\2" + "\1\124\6\2\3\0\26\2\1\112\4\2\3\0\24\2"
			+ "\1\125\1\126\1\117\4\2\3\0\3\2\1\127\5\2" + "\1\130\21\2\3\0\6\2\1\131\24\2\3\0\5\2"
			+ "\1\52\25\2\3\0\4\2\1\52\26\2\3\0\6\2" + "\1\132\24\2\3\0\26\2\1\52\4\2\3\0\4\2"
			+ "\1\133\26\2\3\0\2\2\1\134\3\2\1\135\16\2" + "\1\117\5\2\3\0\6\2\1\52\16\2\1\105\5\2"
			+ "\3\0\4\2\1\70\26\2\3\0\12\2\1\136\20\2" + "\3\0\2\2\1\137\1\2\1\140\26\2\3\0\2\2"
			+ "\1\141\30\2\3\0\24\2\1\113\6\2\3\0\3\2" + "\1\142\27\2\3\0\3\2\1\52\27\2\3\0\24\2"
			+ "\1\143\6\2\3\0\3\2\1\144\27\2\3\0\6\2" + "\1\52\24\2\3\0\15\2\1\145\15\2\3\0\22\2"
			+ "\1\146\10\2\3\0\25\2\1\52\5\2\3\0\3\2" + "\1\147\27\2\3\0\12\2\1\52\20\2\3\0\14\2"
			+ "\1\110\16\2\3\0\3\2\1\150\27\2\3\0\24\2" + "\1\151\6\2\3\0\21\2\1\52\11\2\3\0\10\2"
			+ "\1\105\14\2\1\52\5\2\3\0\16\2\1\152\14\2" + "\3\0\25\2\1\153\5\2\3\0\11\2\1\113\21\2"
			+ "\3\0\3\2\1\154\27\2\3\0\7\2\1\155\23\2" + "\3\0\17\2\1\156\13\2\3\0\13\2\1\157\17\2"
			+ "\3\0\10\2\1\160\22\2\3\0\16\2\1\161\14\2" + "\3\0\3\2\1\162\27\2\3\0\3\2\1\163\5\2"
			+ "\1\52\21\2\3\0\3\2\1\164\27\2\3\0\5\2" + "\1\165\25\2\3\0\3\2\1\146\27\2\3\0\6\2"
			+ "\1\166\24\2\3\0\5\2\1\110\25\2\3\0\6\2" + "\1\121\24\2\3\0\17\2\1\110\13\2\3\0\16\2"
			+ "\1\167\14\2\3\0\6\2\1\105\24\2\3\0\11\2" + "\1\170\21\2\3\0\10\2\1\52\22\2\3\0\11\2"
			+ "\1\171\21\2\3\0\17\2\1\52\13\2\3\0\15\2" + "\1\52\15\2\3\0\2\2\1\110\30\2\3\0\3\2"
			+ "\1\172\20\2\1\173\6\2\3\0\3\2\1\174\27\2" + "\3\0\3\2\1\45\27\2\3\0\24\2\1\175\6\2"
			+ "\3\0\3\2\1\176\27\2\3\0\10\2\1\110\22\2" + "\3\0\10\2\1\177\22\2\3\0\2\2\1\200\30\2"
			+ "\3\0\10\2\1\201\22\2\3\0\24\2\1\202\6\2" + "\3\0\22\2\1\203\10\2\3\0\7\2\1\52\23\2"
			+ "\3\0\25\2\1\204\5\2\3\0\10\2\1\205\22\2" + "\3\0\27\2\1\52\3\2\3\0\10\2\1\206\22\2"
			+ "\3\0\20\2\1\207\12\2\3\0\2\2\1\52\30\2" + "\3\0\2\2\1\104\30\2\3\0\17\2\1\210\13\2"
			+ "\3\0\24\2\1\211\6\2\3\0\25\2\1\142\5\2" + "\3\0\15\2\1\105\15\2\3\0\22\2\1\70\10\2"
			+ "\3\0\10\2\1\62\22\2\3\0\6\2\1\212\24\2" + "\3\0\16\2\1\146\14\2\3\0\22\2\1\213\10\2"
			+ "\3\0\17\2\1\214\13\2\3\0\24\2\1\161\6\2" + "\3\0\5\2\1\105\25\2\3\0\6\2\1\215\24\2"
			+ "\3\0\11\2\1\150\20\2";

	private static int[] zzUnpackTrans() {
		int[] result = new int[4110];
		int offset = 0;
		offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
		return result;
	}

	private static int zzUnpackTrans(String packed, int offset, int[] result) {
		int i = 0; /* index in packed string */
		int j = offset; /* index in unpacked array */
		int l = packed.length();
		while (i < l) {
			int count = packed.charAt(i++);
			int value = packed.charAt(i++);
			value--;
			do
				result[j++] = value;
			while (--count > 0);
		}
		return j;
	}

	/* error codes */
	private static final int ZZ_UNKNOWN_ERROR = 0;
	private static final int ZZ_NO_MATCH = 1;
	private static final int ZZ_PUSHBACK_2BIG = 2;

	/* error messages for the codes above */
	private static final String ZZ_ERROR_MSG[] = { "Unkown internal scanner error", "Error: could not match input",
			"Error: pushback value was too large" };

	/**
	 * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
	 */
	private static final int[] ZZ_ATTRIBUTE = zzUnpackAttribute();

	private static final String ZZ_ATTRIBUTE_PACKED_0 = "\1\0\1\1\1\11\77\1\1\11\112\1";

	private static int[] zzUnpackAttribute() {
		int[] result = new int[141];
		int offset = 0;
		offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
		return result;
	}

	private static int zzUnpackAttribute(String packed, int offset, int[] result) {
		int i = 0; /* index in packed string */
		int j = offset; /* index in unpacked array */
		int l = packed.length();
		while (i < l) {
			int count = packed.charAt(i++);
			int value = packed.charAt(i++);
			do
				result[j++] = value;
			while (--count > 0);
		}
		return j;
	}

	/** the input device */
	private java.io.Reader zzReader;

	/** the current state of the DFA */
	private int zzState;

	/** the current lexical state */
	private int zzLexicalState = YYINITIAL;

	/**
	 * this buffer contains the current text to be matched and is the source of the
	 * yytext() string
	 */
	private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

	/** the textposition at the last accepting state */
	private int zzMarkedPos;

	/** the current text position in the buffer */
	private int zzCurrentPos;

	/** startRead marks the beginning of the yytext() string in the buffer */
	private int zzStartRead;

	/**
	 * endRead marks the last character in the buffer, that has been read from input
	 */
	private int zzEndRead;

	/** number of newlines encountered up to the start of the matched text */
	private int yyline;

	/** the number of characters up to the start of the matched text */
	private int yychar;

	/**
	 * the number of characters from the last newline up to the start of the matched
	 * text
	 */
	private int yycolumn;

	/**
	 * zzAtBOL == true <=> the scanner is currently at the beginning of a line
	 */
	private boolean zzAtBOL = true;

	/** zzAtEOF == true <=> the scanner is at the EOF */
	private boolean zzAtEOF;

	/* user code: */
	private int lastToken;
	private int nextState = YYINITIAL;

	public void reset(java.io.Reader reader, int yyline, int yychar, int yycolumn) throws IOException {
		yyreset(reader);
		this.yyline = yyline;
		this.yychar = yychar;
		this.yycolumn = yycolumn;
	}

	/**
	 * Creates a new scanner There is also a java.io.InputStream version of this
	 * constructor.
	 *
	 * @param in the java.io.Reader to read input from.
	 */
	public OntoqlLexer(java.io.Reader in) {
		this.zzReader = in;
	}

	/**
	 * Creates a new scanner. There is also java.io.Reader version of this
	 * constructor.
	 *
	 * @param in the java.io.Inputstream to read input from.
	 */
	public OntoqlLexer(java.io.InputStream in) {
		this(new java.io.InputStreamReader(in));
	}

	/**
	 * Refills the input buffer.
	 *
	 * @return <code>false</code>, iff there was new input.
	 * 
	 * @exception java.io.IOException if any I/O-Error occurs
	 */
	private boolean zzRefill() throws java.io.IOException {

		/* first: make room (if you can) */
		if (zzStartRead > 0) {
			System.arraycopy(zzBuffer, zzStartRead, zzBuffer, 0, zzEndRead - zzStartRead);

			/* translate stored positions */
			zzEndRead -= zzStartRead;
			zzCurrentPos -= zzStartRead;
			zzMarkedPos -= zzStartRead;
			zzStartRead = 0;
		}

		/* is the buffer big enough? */
		if (zzCurrentPos >= zzBuffer.length) {
			/* if not: blow it up */
			char newBuffer[] = new char[zzCurrentPos * 2];
			System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
			zzBuffer = newBuffer;
		}

		/* finally: fill the buffer with new input */
		int numRead = zzReader.read(zzBuffer, zzEndRead, zzBuffer.length - zzEndRead);

		if (numRead > 0) {
			zzEndRead += numRead;
			return false;
		}
		// unlikely but not impossible: read 0 characters, but not at end of stream
		if (numRead == 0) {
			int c = zzReader.read();
			if (c == -1) {
				return true;
			} else {
				zzBuffer[zzEndRead++] = (char) c;
				return false;
			}
		}

		// numRead < 0
		return true;
	}

	/**
	 * Closes the input stream.
	 */
	public final void yyclose() throws java.io.IOException {
		zzAtEOF = true; /* indicate end of file */
		zzEndRead = zzStartRead; /* invalidate buffer */

		if (zzReader != null)
			zzReader.close();
	}

	/**
	 * Resets the scanner to read from a new input stream. Does not close the old
	 * reader.
	 *
	 * All internal variables are reset, the old input stream <b>cannot</b> be
	 * reused (internal buffer is discarded and lost). Lexical state is set to
	 * <tt>ZZ_INITIAL</tt>.
	 *
	 * @param reader the new input stream
	 */
	public final void yyreset(java.io.Reader reader) {
		zzReader = reader;
		zzAtBOL = true;
		zzAtEOF = false;
		zzEndRead = zzStartRead = 0;
		zzCurrentPos = zzMarkedPos = 0;
		yyline = yychar = yycolumn = 0;
		zzLexicalState = YYINITIAL;
	}

	/**
	 * Returns the current lexical state.
	 */
	public final int yystate() {
		return zzLexicalState;
	}

	/**
	 * Enters a new lexical state
	 *
	 * @param newState the new lexical state
	 */
	public final void yybegin(int newState) {
		zzLexicalState = newState;
	}

	/**
	 * Returns the text matched by the current regular expression.
	 */
	public final String yytext() {
		return new String(zzBuffer, zzStartRead, zzMarkedPos - zzStartRead);
	}

	/**
	 * Returns the character at position <tt>pos</tt> from the matched text.
	 * 
	 * It is equivalent to yytext().charAt(pos), but faster
	 *
	 * @param pos the position of the character to fetch. A value from 0 to
	 *            yylength()-1.
	 *
	 * @return the character at position pos
	 */
	public final char yycharat(int pos) {
		return zzBuffer[zzStartRead + pos];
	}

	/**
	 * Returns the length of the matched text region.
	 */
	public final int yylength() {
		return zzMarkedPos - zzStartRead;
	}

	/**
	 * Reports an error that occured while scanning.
	 *
	 * In a wellformed scanner (no or only correct usage of yypushback(int) and a
	 * match-all fallback rule) this method will only be called with things that
	 * "Can't Possibly Happen". If this method is called, something is seriously
	 * wrong (e.g. a JFlex bug producing a faulty scanner etc.).
	 *
	 * Usual syntax/scanner level error handling should be done in error fallback
	 * rules.
	 *
	 * @param errorCode the code of the errormessage to display
	 */
	private void zzScanError(int errorCode) {
		String message;
		try {
			message = ZZ_ERROR_MSG[errorCode];
		} catch (ArrayIndexOutOfBoundsException e) {
			message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
		}

		throw new Error(message);
	}

	/**
	 * Pushes the specified amount of characters back into the input stream.
	 *
	 * They will be read again by then next call of the scanning method
	 *
	 * @param number the number of characters to be read again. This number must not
	 *               be greater than yylength()!
	 */
	public void yypushback(int number) {
		if (number > yylength())
			zzScanError(ZZ_PUSHBACK_2BIG);

		zzMarkedPos -= number;
	}

	/**
	 * Resumes scanning until the next regular expression is matched, the end of
	 * input is encountered or an I/O-Error occurs.
	 *
	 * @return the next token
	 * @exception java.io.IOException if any I/O-Error occurs
	 */
	public Token getNextToken() throws java.io.IOException {
		int zzInput;
		int zzAction;

		// cached fields:
		int zzCurrentPosL;
		int zzMarkedPosL;
		int zzEndReadL = zzEndRead;
		char[] zzBufferL = zzBuffer;
		char[] zzCMapL = ZZ_CMAP;

		int[] zzTransL = ZZ_TRANS;
		int[] zzRowMapL = ZZ_ROWMAP;
		int[] zzAttrL = ZZ_ATTRIBUTE;

		while (true) {
			zzMarkedPosL = zzMarkedPos;

			yychar += zzMarkedPosL - zzStartRead;

			boolean zzR = false;
			for (zzCurrentPosL = zzStartRead; zzCurrentPosL < zzMarkedPosL; zzCurrentPosL++) {
				switch (zzBufferL[zzCurrentPosL]) {
				case '\u000B':
				case '\u000C':
				case '\u0085':
				case '\u2028':
				case '\u2029':
					yyline++;
					zzR = false;
					break;
				case '\r':
					yyline++;
					zzR = true;
					break;
				case '\n':
					if (zzR)
						zzR = false;
					else {
						yyline++;
					}
					break;
				default:
					zzR = false;
				}
			}

			if (zzR) {
				// peek one character ahead if it is \n (if we have counted one line too much)
				boolean zzPeek;
				if (zzMarkedPosL < zzEndReadL)
					zzPeek = zzBufferL[zzMarkedPosL] == '\n';
				else if (zzAtEOF)
					zzPeek = false;
				else {
					boolean eof = zzRefill();
					zzEndReadL = zzEndRead;
					zzMarkedPosL = zzMarkedPos;
					zzBufferL = zzBuffer;
					if (eof)
						zzPeek = false;
					else
						zzPeek = zzBufferL[zzMarkedPosL] == '\n';
				}
				if (zzPeek)
					yyline--;
			}
			zzAction = -1;

			zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;

			zzState = ZZ_LEXSTATE[zzLexicalState];

			zzForAction: {
				while (true) {

					if (zzCurrentPosL < zzEndReadL)
						zzInput = zzBufferL[zzCurrentPosL++];
					else if (zzAtEOF) {
						zzInput = YYEOF;
						break zzForAction;
					} else {
						// store back cached positions
						zzCurrentPos = zzCurrentPosL;
						zzMarkedPos = zzMarkedPosL;
						boolean eof = zzRefill();
						// get translated positions and possibly new buffer
						zzCurrentPosL = zzCurrentPos;
						zzMarkedPosL = zzMarkedPos;
						zzBufferL = zzBuffer;
						zzEndReadL = zzEndRead;
						if (eof) {
							zzInput = YYEOF;
							break zzForAction;
						} else {
							zzInput = zzBufferL[zzCurrentPosL++];
						}
					}
					int zzNext = zzTransL[zzRowMapL[zzState] + zzCMapL[zzInput]];
					if (zzNext == -1)
						break zzForAction;
					zzState = zzNext;

					int zzAttributes = zzAttrL[zzState];
					if ((zzAttributes & 1) == 1) {
						zzAction = zzState;
						zzMarkedPosL = zzCurrentPosL;
						if ((zzAttributes & 8) == 8)
							break zzForAction;
					}

				}
			}

			// store back cached position
			zzMarkedPos = zzMarkedPosL;

			switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
			case 3: {
				lastToken = OntoqlToken.STRING_LITERAL;
				String text = yytext();
				OntoqlToken t = new OntoqlToken(lastToken, text, yyline, yychar, yychar + text.length(), nextState);
				return t;
			}
			case 5:
				break;
			case 4: {
				lastToken = OntoqlToken.KEYWORD;
				String text = yytext();
				OntoqlToken t = new OntoqlToken(lastToken, text, yyline, yychar, yychar + text.length(), nextState);
				return t;
			}
			case 6:
				break;
			case 2: {
			}
			case 7:
				break;
			case 1: {
				lastToken = OntoqlToken.PLAIN;
				String text = yytext();
				OntoqlToken t = new OntoqlToken(lastToken, text, yyline, yychar, yychar + text.length(), nextState);
				return t;
			}
			case 8:
				break;
			default:
				if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
					zzAtEOF = true;
					return null;
				} else {
					zzScanError(ZZ_NO_MATCH);
				}
			}
		}
	}
}
