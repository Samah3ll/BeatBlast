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

import javax.swing.JCheckBoxMenuItem;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** Key, menu, and button event processing. All user interaction with the
 *  system is processed by the single EventProcessor object, which has
 *  handles to the other main objects for performing the requested actions.
 */
class EventProcessor implements ActionListener, KeyListener {

	/** Handle to BeatRoot's GUI */
	protected GUI gui;
	
	/** Handle to BeatRoot's audio player */
	protected AudioPlayer audioPlayer;
	
	/** Handle to BeatRoot's audio processor */
	protected AudioProcessor audioProcessor;
	
	/** Handle to BeatRoot's file chooser */
	protected Chooser chooser;

	/** Flag for enabling debugging output */
	public static boolean debug = false;

	/** Constructor:
	 *  @param g Handle to BeatRoot's GUI
	 *  @param ap Handle to BeatRoot's audio player
	 *  @param proc Handle to BeatRoot's audio processor
	 *  @param ch Handle to BeatRoot's file chooser
	 */
	public EventProcessor(GUI g, AudioPlayer ap, AudioProcessor proc, Chooser ch) {
		gui = g;
		audioPlayer = ap;
		audioProcessor = proc;
		chooser = ch;
	} // constructor

	/** Processes all user menu and button actions.
	 *  @param e The Java event handling system's representation of the user action */
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equals(GUI.EXIT))
			BeatRoot.quit();
		else if (command.equals(GUI.PLAY_AUDIO))
			audioPlayer.play(true);
		else if (command.equals(GUI.STOP))
			audioPlayer.stop();
		else if (command.equals(GUI.LOAD_AUDIO)){
			gui.loadAudioData();
			gui.displayPanel.beatTrack();
			gui.displayPanel.resizeSpectroForVisu(12);
			gui.displayPanel.repaintImage();
		}
		else
			BeatRoot.warning("Operation not implemented");
	} // actionPerformed()
	
	/** Processes user key events which are not associated with menu items.
	 *  Keystrokes are only processed if no modifiers are present (e.g. shift, alt, mouse buttons).
	 *  Since key releases are considered irrelevant, all processing is done here. */
	public void keyPressed(KeyEvent e) {}
	
	/** Ignore key releases, since processing is performed as soon as the key is pressed.
	 *  Implements part of interface KeyListener */
	public void keyReleased(KeyEvent e) {}
	
	/** Ignore KeyEvents indicating that a key was typed, since keyPressed() has
	 *  already dealt with this keystroke.
	 *  Implements part of interface KeyListener */
	public void keyTyped(KeyEvent e) {}
	
} // class EventProcessor
