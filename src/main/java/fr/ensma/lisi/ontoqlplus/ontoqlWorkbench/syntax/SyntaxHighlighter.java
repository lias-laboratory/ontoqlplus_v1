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

import java.io.IOException;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;

import javax.swing.text.SimpleAttributeSet;

/**
 * @author Stéphane JEAN
 */
public class SyntaxHighlighter extends Thread {

	/**
	 * Keep a list of positions in the file that it is safe to restart the
	 * highlighting. This set implementation provides linear performance.
	 */
	private TreeSet iniPositions = new TreeSet(new PositionComparator());

	/**
	 * As we go through and remove invalid positions we will also find new valid
	 * ones. Since we can't read and write to iniPositions at the same time, we will
	 * store new positions here and merge them with iniPositions once its done
	 * removing. These will be sorted when merged, so we can get 0(1) performance if
	 * we go with HashSet in the meantime.
	 */
	private HashSet newPositions = new HashSet();

	/**
	 * Vector that stores communication between this thread and the event dispatcher
	 */
	private volatile Vector messageQueue = new Vector();

	/**
	 * The amount of change that has occured before the position that we are
	 * currently highlighting (lastPosition)
	 */
	private volatile int change = 0;

	/**
	 * The last Position colored
	 */
	private volatile int lastPosition = -1;

	private volatile boolean asleep = false;

	/**
	 * When accessing the Vector, we need to create a critical section. We will lock
	 * on this to make sure that we don't have funny thread behavior
	 */
	private Object lock = new Object();

	/**
	 * Our document
	 */
	private OntoqlDocument document;

	/**
	 * The DocumentReader that will feed our lexer
	 */
	private DocumentReader documentReader;

	/**
	 * The Document lock
	 */
	private Object doclock = new Object();

	/**
	 * Our styles
	 */
	private Hashtable styles;

	/**
	 * Our lexer
	 */
	private Lexer syntaxLexer;

	/**
	 * Constructs a new SyntaxHighlighter with the given document reader and the
	 * given document lock
	 */
	public SyntaxHighlighter(OntoqlDocument doc, DocumentReader reader, Object doclock, Hashtable styles) {
		this.document = doc;
		this.documentReader = reader;
		this.doclock = doclock;
		this.styles = styles;

		this.syntaxLexer = new OntoqlLexer(documentReader);
	}

	/**
	 * Tell the syntax highlighter to take another look at this section in the
	 * document Requests will be queued. This should be synchronized externally
	 */
	public void highlight(int position, int adjustment) {
		// figure out if this adjustment affects the current run. if it does
		// adjust the place in the document that gets highlighted.
		if (position < lastPosition) {
			if (lastPosition < position - adjustment)
				change -= lastPosition - position;
			else
				change += adjustment;
		}
		synchronized (lock) {
			messageQueue.add(new RecolorEvent(position, adjustment));
			if (asleep)
				this.interrupt();
		}
	}

	/**
	 * The Syntax Highlighter runs forever and may sleep for long periods of time,
	 * It should be interrupted when there is something for it to do.
	 */
	public void run() {
		int position = -1;
		int adjustment = 0;
		// if we finish, we can go to sleep so long as there is nothing for us
		// to do.
		// use tryAgain to keep track of this.
		boolean tryAgain = false;
		do {
			if (messageQueue.size() > 0) {
				RecolorEvent re = (RecolorEvent) messageQueue.elementAt(0);
				messageQueue.removeElementAt(0); // about to be processed
				position = re.pos;
				adjustment = re.adj;
			} else { // reset and allow sleep
				tryAgain = false;
				position = -1;
				adjustment = 0;
			}

			if (position != -1) {
				SortedSet workingSet;
				Iterator workingIt;
				Position startRequest = new Position(position);
				Position endRequest = new Position(position + ((adjustment >= 0) ? adjustment : -adjustment));
				Position p;
				Position pStart = null, pEnd = null;

				// find the starting position. we must start at least one token
				// before the current position
				try {
					// all the good positions before
					workingSet = iniPositions.headSet(startRequest);
					// the last of the good positions
					pStart = (Position) workingSet.last();
				} catch (NoSuchElementException e) {
					// if nothing available, start from beginning
					pStart = new Position(0);
				}

				// if stuff was removed, take it off the list
				if (adjustment < 0) {
					workingSet = iniPositions.subSet(startRequest, endRequest);
					workingIt = workingSet.iterator();
					while (workingIt.hasNext()) {
						workingIt.next();
						workingIt.remove();
					}
				}

				// adjust the positions of everything after the
				// insertion/removal
				workingSet = iniPositions.tailSet(startRequest);
				workingIt = workingSet.iterator();
				while (workingIt.hasNext())
					((Position) workingIt.next()).adjust(adjustment);

				// now go through and highlight as much as needed
				workingSet = iniPositions.tailSet(pStart);
				workingIt = workingSet.iterator();
				p = null;
				if (workingIt.hasNext())
					p = (Position) workingIt.next();

				try {
					Token t;
					boolean done = false;
					pEnd = pStart;

					synchronized (doclock) {
						// We are playing some games here with the lexer for
						// efficiency.
						// Instead of creating a new lexer each time around, we
						// just reset it
						// and give it a funny start position
						syntaxLexer.reset(documentReader, 0, pStart.getPosition(), 0);
						// Scroll the reader in sync with the lexer
						documentReader.seek(pStart.getPosition());
						// we will highlight tokens until we reach a good
						// stopping place
						// The end of the document will return null so we need
						// to stop there.
						t = syntaxLexer.getNextToken();

						newPositions.add(pStart);
						while (!done && t != null) {
							// this is where the actual coloring occurs. color
							// stuff with the passed
							// in hashtable based on the category of the token
							synchronized (doclock) {
								if (t.getCharEnd() <= document.getLength()) {
									document.setCharacterAttributes(t.getCharBegin() + change,
											t.getCharEnd() - t.getCharBegin(),
											(SimpleAttributeSet) styles.get(t.getDesc()), true);
									pEnd = new Position(t.getCharEnd());
								}
								lastPosition = t.getCharEnd() + change;
								// The other complicated reason for doing no
								// more highlighting is
								// that all the colors are the same from here on
								// out anyway.
								// We can detect this by seeing if the place the
								// lexer returned to
								// its initial state is the same as the place
								// that we are returning to
								// this time. As long as the place is after the
								// last changed text,
								// everything is fine already.
								if (t.getState() == Token.INITIAL_STATE) {
									// look at all the positions from last time
									// that are less than
									// or equal to the current position.
									while (p != null && p.getPosition() <= t.getCharEnd()) {
										if (p.getPosition() == t.getCharEnd()
												&& p.getPosition() >= endRequest.getPosition()) {
											// we have found a state that is the
											// same
											done = true;
											p = null;
										} else if (workingIt.hasNext()) {
											// didn't find it, try again
											p = (Position) workingIt.next();
										} else {
											// didn't find it and there is no
											// more info from last time
											// so we will keep going till the
											// end of the document
											p = null;
										}
									}

									newPositions.add(pEnd);
									synchronized (doclock) {
										t = syntaxLexer.getNextToken();
									}
								}

								// remove all of the old initial positions right
								// up to the
								// place we started highlighting right up to the
								// text we touched
								workingIt = workingSet.subSet(pStart, pEnd).iterator();
								while (workingIt.hasNext()) {
									workingIt.next();
									workingIt.remove();
								}

								// remove all positions that are after the end
								// of the file
								workingIt = workingSet.tailSet(new Position(document.getLength())).iterator();
								while (workingIt.hasNext()) {
									workingIt.next();
									workingIt.remove();
								}

								// put the new positions that we have found on
								// the list
								iniPositions.addAll(newPositions);
								newPositions.clear();
							}
						}
					}
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
				synchronized (doclock) {
					lastPosition = -1;
					change = 0;
				}
				// since we did something, we should check that there is nothing
				// left to do before going back
				// to sleep
				tryAgain = true;
			}

			asleep = true;

			if (!tryAgain) {
				try {
					sleep(0xFFFFFF); // as long as possible
				} catch (InterruptedException x) {
					// absorb, its ok
				}
			}

			asleep = false;
		} while (true);
	}

	/**
	 * A simple wrapper around int positions and adjustments that need to be
	 * recolored. Wrapped in an object so it can be stored in a list
	 */
	private class RecolorEvent {
		public int pos;
		public int adj;

		public RecolorEvent(int p, int a) {
			pos = p;
			adj = a;
		}
	}
}