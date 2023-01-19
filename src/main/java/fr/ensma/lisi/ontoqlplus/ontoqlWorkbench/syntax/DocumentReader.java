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

import java.io.Reader;

import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;

/**
 * Since the lexer only works with readers and strings, this implementation will
 * be used for the syntax highlighting package. The close method does nothing,
 * and the seek method has been added, so we should be able to get some
 * performance increase.
 * 
 * @author Stéphane JEAN
 */
public class DocumentReader extends Reader {

	/**
	 * The current position in the document
	 */
	private long pos = 0;

	/**
	 * Saved position used in the mark and reset methods
	 */
	private long mark = -1;

	/**
	 * The document we are reading
	 */
	private AbstractDocument doc;

	public DocumentReader(AbstractDocument document) {
		this.doc = document;
	}

	/**
	 * Modifying the document while the reader is reading corrupts the reader. This
	 * method should be called in a thread safe way to allow the reader to
	 * compensate.
	 */
	public void update(int pos, int adj) {
		if (pos < this.pos) {
			// text inserted before text present
			if (this.pos < pos - adj)
				this.pos = pos;
			// text inserted after text present, probably most Common
			else
				this.pos += adj;
		}
	}

	/**
	 * Has no effect. The reader can be used even after close is called.
	 */
	public void close() {
	}

	/**
	 * Save a position for reset
	 * 
	 * @param readAheadLimit ignored
	 */
	public void mark(int readAheadLimit) {
		mark = pos;
	}

	/**
	 * This reader supports mark and reset
	 */
	public boolean markSupported() {
		return true;
	}

	/**
	 * Read a single character of the document
	 * 
	 * @return The character or -1 if the end of the document has been reached
	 */
	public int read() {
		if (pos < doc.getLength()) {
			try {
				char c = doc.getText((int) pos, 1).charAt(0);
				pos++;
				return c;
			} catch (BadLocationException ble) {
				return -1;
			}
		} else {
			return -1;
		}
	}

	/**
	 * Read and fill the buffer. This method will always fill the buffer until the
	 * end of the document is reached.
	 * 
	 * @param cbuf The buffer to fill
	 * @return The number of characters read or -1 if the end of the document is
	 *         reached.
	 */
	public int read(char[] cbuf) {
		return read(cbuf, 0, cbuf.length);
	}

	public int read(char[] cbuf, int offs, int len) {
		// position must be less than the document length, or we return EOF
		if (pos < doc.getLength()) {
			int length = len;

			// if the amount to be read goes beyond the document
			// shorten the length to the end of the document
			if (pos + length >= doc.getLength())
				length = doc.getLength() - (int) pos;
			// ? what does this do ?
			if (offs + length >= cbuf.length)
				length = cbuf.length - offs;

			try {
				String s = doc.getText((int) pos, length);
				pos += length; // adjust our internal position for the next read

				for (int i = 0; i < length; i++)
					cbuf[offs + i] = s.charAt(i);

				return length;
			} catch (BadLocationException ble) {
				return -1;
			}
		} else {
			return -1;
		}
	}

	public boolean ready() {
		return true; // never closes, always handy
	}

	public long skip(long n) {
		if (pos + n <= doc.getLength()) {
			pos += n;
			return n;
		} else {
			long oldPos = pos;
			pos = doc.getLength();
			return doc.getLength() - oldPos;
		}
	}

	public void seek(long n) {
		if (n <= doc.getLength())
			pos = n;
		else
			pos = doc.getLength();
	}
}