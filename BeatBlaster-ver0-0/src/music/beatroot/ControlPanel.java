/*  BeatRoot: An interactive beat tracking system
    Copyright (C) 2001, 2006 by Simon Dixon

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License along
    with this program (the file gpl.txt); if not, download it from
	http://www.gnu.org/licenses/gpl.txt or write to the Free Software Foundation, Inc.,
    51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package music.beatroot;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

/** The panel at the bottom of BeatRoot's GUI containing the buttons */
class ControlPanel extends JPanel {

	static final long serialVersionUID = 0; // avoid compiler warning

	/** The listener object which handles key, button and menu events */
	EventProcessor jobq;

	/** For layout of the grid of buttons (i.e. not including the zoom buttons) */
	class ButtonPanel extends JPanel {
		static final long serialVersionUID = 0; // avoid compiler warning

		/**
		 * Constructor:
		 * 
		 * @param r
		 *            Number of rows of buttons
		 * @param c
		 *            NUmber of columns of buttons
		 */
		public ButtonPanel(int r, int c) {
			super(new GridLayout(r, c));
		} // constructor

		/**
		 * Adds a button to this panel.
		 * 
		 * @param s
		 *            The text on the button
		 * @param al
		 *            The event handler for when the button is pressed
		 */
		public void addButton(String s, ActionListener al) {
			JButton b = new JButton(s);
			b.addActionListener(al);
			add(b);
		} // addButton()
	} // inner class ButtonPanel

	/**
	 * Constructor:
	 * 
	 * @param displayPanel
	 *            The main panel of BeatRoot's GUI
	 * @param scroller
	 *            The scrollbar on BeatRoot's GUI
	 * @param args
	 *            Not used - was used for a file name in BeatRoot 0.4 for
	 *            experiments reported in Dixon, Goebl and Cambouropoulos, Music
	 *            Perception, 2006
	 * @param j
	 *            The object which handles button, key and menu events
	 */
	public ControlPanel(BeatTrackDisplay displayPanel, JScrollBar scroller,
			String args, EventProcessor j) {
		setPreferredSize(new Dimension(1000, 50));
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createLineBorder(Color.black, 3));
		jobq = j;

		ButtonPanel buttonPanel;
			buttonPanel = new ButtonPanel(1, 4);
			buttonPanel.addButton(GUI.LOAD_AUDIO, jobq);
			buttonPanel.addButton(GUI.PLAY_AUDIO, jobq);
			buttonPanel.addButton(GUI.STOP, jobq);
			buttonPanel.addButton(GUI.EXIT, jobq);
		add(buttonPanel, BorderLayout.CENTER);
	} // ControlPanel() constructor

} // class ControlPanel
