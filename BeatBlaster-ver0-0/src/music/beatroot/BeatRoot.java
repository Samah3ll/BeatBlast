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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;
import javax.swing.JOptionPane;
import music.util.Writer;
import music.util.Event;
import music.util.EventList;

/** The main class of the BeatRoot application.
 *  Processes the command line arguments (see processArgs()) and creates
 *  the three main objects:
 *  <UL>
 *  <LI><code>gui</code> - the graphical user interface object, which
 *  displays the audio and beat data and processes mouse and key events;</LI>
 *  <LI><code>audioPlayer</code> - the object which deals with audio output,
 *  playing the audio data with or without percussion sounds marking the beats;</LI>
 *  <LI><code>audioProcessor</code> - processes audio to find onsets, and calls
 *  the tempo induction and beat tracking methods</LI>
 *  </UL>
 *  @author Simon Dixon
 */
public class BeatRoot {

	/** The graphical user interface (frame) object */
	public GUI gui;
	
	/** The object that deals with audio output */
	protected AudioPlayer audioPlayer;
	
	/** The object that deals with processing the audio data */
	protected AudioProcessor audioProcessor;

	/** The dialog window for selecting files for opening and saving data */
	protected Chooser fileChooser;

	/** File name of audio input file */
	protected String audioIn = null;

	/** Output file name for saving the beat times in text format, one to a line */
	protected String textOutputFile;

	/** Input file name of beat times in TMF format (instead of automatic beat tracking) */
	protected String beatsIn = null;

	/** Input file name for annotated beat times (for evaluating BeatRoot) */
	protected String beatsFile;

	/** Input file name for (onset) feature file, instead of audio input, for ICASSP'07 paper */ 
	protected String featureFile;

	/** Input file for batch processing where each line contains a list of arguments for a BeatRoot run */
	protected String argsFile;
	
	/** Flag to load/save onsets */
	protected boolean onsetOnly = false;
	
	/** For beat tracking with the first n notes given, this is the value of n.
	 *  (e.g. n==1 determines the initial phase only; n==2 determines the initial phase and tempo)
	 */
	protected int useAnnotation = 0;

	/** For reading argsFile */
	protected BufferedReader reader;

	/** Flag indicating whether audio with beats should be played after processing is complete */
	protected boolean playWithBeats;

	/** Flag indicating that no GUI input is expected, i.e. that the program exits after processing the command line */
	protected boolean batchMode;

	/** Flag for suppressing messages to standard output */
	protected boolean silentFlag = false;

	/** Flag indicating whether warning messages should be ignored or displayed */
	protected static boolean ignoreWarnings = false;
	
	/** Avec ou sans visuel ?*/
	private Boolean visu = false;
	
	public static Writer writer;

	/**
	 *  Process command line arguments.
	 *  no command line
	 */
	public void processArgs() {
		audioIn = null;
		beatsIn = null;
		textOutputFile = null;
		beatsFile = null;
		featureFile = null;
	} // processArgs()

	/** Constructor. Initialises the BeatRoot application,
	 *  including the GUI, and processes any command line arguments.
	 *  @param args Optional command line arguments.
	 *  @see #processArgs(String[])
	 */
	public BeatRoot(String saveDirectory) {
		batchMode = false;
		playWithBeats = false;
		argsFile = null;
		reader = null;
		fileChooser = null;
		gui = null;
		processArgs();
		audioProcessor = new AudioProcessor();
		fileChooser = new Chooser();
		writer = new Writer(saveDirectory);
		audioPlayer = new AudioPlayer(null, fileChooser);
		gui = new GUI(audioPlayer, audioProcessor, fileChooser);
		gui.setVisible(visu);
	}  // constructor

	/** Reads a line from the arguments file <code>argsFile</code>, and converts
	 *  it into an array of Strings, allowing for quoted strings.
	 *  @return The next line of arguments as a <code>String[]</code> 
	 */
	protected String[] getArgs() {
		try {
			if (reader == null)
				reader = new BufferedReader(new FileReader(argsFile));
		} catch (FileNotFoundException e) {
			System.err.println(e);
			return null;
		}
		try {
			String s = reader.readLine();
			while ((s != null) && (s.startsWith("#")))	// skip comments
				s = reader.readLine();
			return stringToArgs(s);
		} catch (IOException e) {
			System.err.println(e);
			return null;
		}
	} // getArgs()
	
	public static String[] stringToArgs(String s) {
		if (s == null)
			return null;
		ArrayList<String> argBuff = new ArrayList<String>();
		int start = 0;
		while (start < s.length()) {
			if (s.charAt(start) == ' ') {
				start++;
				continue;
			}
			int delim = s.indexOf('"', start);
			int space = s.indexOf(' ', start);
			if ((space < 0) && (delim < 0)) {
				argBuff.add(s.substring(start));
				break;
			} else if ((delim < 0) || ((space >= start) && (space < delim))) {
				argBuff.add(s.substring(start,space));
				start = space + 1;
			} else if (delim == start) {
				delim = s.indexOf('"', start+1);
				if (delim <= start + 1) {
					System.err.println("Parse error in args file: " + s + " (" + start + "," + space + "," + delim + ")");
					return null;
				}
				argBuff.add(s.substring(start+1, delim));
				start = delim + 1;
			} else {
				System.err.println("Parse error in args file: " + s + " (" + start + "," + space + "," + delim + ")");
				return null;
			}
		}
		return argBuff.toArray(new String[argBuff.size()]);
	} // getArgs()
	
	/** Open an exit dialog.  Protects against inadvertant presses of the exit button.
	 *  Could be extended to save settings and data automatically.
	 */
	public static void quit() {
		if (JOptionPane.showConfirmDialog(null, "Warning: " +
				"this operation may result in loss of data :)\n" + 
				"Do you really want to quit?", "Just checking ...",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
				System.exit(0);
	} // quit()

	/** Print a warning message.
	 *  Could be extended to save warning messages to a log file. 
	 *  @param message The warning message
	 */
	public static void warning(String message) {
		System.err.println("Warning: " + message);
		// JOptionPane.showMessageDialog(this, "Warning: " + message);
		if (!ignoreWarnings) {
			String string1 = "OK";
			String string2 = "Ignore future warnings";
			Object[] options = {string1, string2};
			int n = JOptionPane.showOptionDialog(null, "Warning: " + message,
					"Warning", JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE, null, options, string1);
			ignoreWarnings = (n != JOptionPane.YES_OPTION);
		}
	} // warning()
	
	/** Print an error message and opens an exit dialog.  Generally better than an 
	 *  immediate exit, since the user might want to save some data before exiting.
	 *  @param message The error message
	 */
	public static void error(String message) {
		if (JOptionPane.showConfirmDialog(null, "Error: " + message + "\nContinue?", "Error",
				JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION)
			System.exit(0);
	} // error()
	
	public Boolean getVisu(){
		return visu;
	}
	
	public GUI getGui() {
		return gui;
	}

	public AudioProcessor getAudioProcessor() {
		return audioProcessor;
	}

	/** Entry point for BeatRoot application.
	 *  @param args Optional command line arguments (see constructor for details)
	 */
	public static void main(String[] args) {
		String path = System.getProperty("user.dir");
		new BeatRoot(path);
	} // main()
	
} // class BeatRoot
