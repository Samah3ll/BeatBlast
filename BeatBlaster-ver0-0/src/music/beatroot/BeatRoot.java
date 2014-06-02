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
	protected GUI gui;
	
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

	/** Process command line arguments.
	 * Arguments are: <I>[option]* [audioFile]</I>, where
	 * <I>audioFile</I> is the file name of the audio input file
	 * containing WAV format data.
	 * The <I>option</I> arguments can be any of the following (in any order):<BL>
	 * <LI><I><B>-m</B> argsFile</I> File name of text file containing lines of arguments for batch processing</LI>
	 * <LI><I><B>-a</B> beatFile</I> File name of text file containing annotated beat times for evaluation</LI>
	 * <LI><I><B>-i</B> beatFile</I> File name of TMF file containing beat times for editing</LI>
	 * <LI><I><B>-b</B></I> Process in batch mode (save results and exit immediately after processing)</LI>
	 * <LI><I><B>-f</B> featureFile</I> Feature file on which to perform beat tracking (for ICASSP'07 paper)</LI>
	 * <LI><I><B>-h</B> highThreshold</I> Spectrogram energy threshold corresponding
	 *  to maximum value in colour map</LI>
	 * <LI><I><B>-l</B> lowThreshold</I> Spectrogram energy threshold corresponding
	 *  to minimum value in colour map</LI>
	 * <LI><I><B>-o</B> outputFile</I> Save output to this file (implies <I><B>-b</B></I>)</LI>
	 * <LI><I><B>-O</B> Output the times of onsets, not beats, and exit (use -o
	 * flag to specify the output file; implies batch mode)</LI>
	 * <LI><I><B>-p</B></I> Play audio with beats as soon as processing is completed</LI>
	 * <LI><I><B>-q</B></I> Suppress output of warnings (TODO) </LI>
	 * <LI><I><B>-s</B> audioScaleFactor</I> Constant for scaling amplitude envelope display</LI>
	 * <LI><I><B>-t</B> hopTime</I> spacing of audio frames (in seconds, default 0.01)</LI>
	 * <LI><I><B>-T</B> frameTime</I> size of FFT (in seconds, default 0.01161)</LI>
	 * <LI><I><B>-w</B></I> live input (not used)</LI>
	 * <LI><I><B>-c</B></I> cursor is always at centre; data scrolls past it</LI>
	 * <LI><I><B>-e</B> allowedError</I> allowed error in beat position for evaluation</LI>
	 * <LI><I><B>-E</B> allowedRelativeError</I> allowed relative error (0-1) in beat position for evaluation</LI>
	 * </BL>
	 */
	public void processArgs() {
		audioIn = null;
		beatsIn = null;
		textOutputFile = null;
		beatsFile = null;
		featureFile = null;
//		for (int i=0; i < args.length; i++) {
//			if ((args[i].length() == 2) && (args[i].charAt(0) == '-')) {
//				switch (args[i].charAt(1)) {
//				case 'A':
//					useAnnotation = Integer.parseInt(args[++i]);
//					System.out.println("case A : " + i);
//					break;
//				case 'a':
//					beatsFile = args[++i];
//					System.out.println("case a : " + i);
//					break;
//				case 'i':
//					beatsIn = args[++i];
//					System.out.println("case i : " + i);
//					break;
//				case 'b': batchMode = true;System.out.println("case b "); break;
//				case 'q': silentFlag = true;System.out.println("case q "); break;
//				case 'f':
//					featureFile = args[++i];
//					System.out.println("case f : " + i);
//					break;
//				case 'e':
//					BeatTrackDisplay.allowedError = Double.parseDouble(args[++i]);
//					BeatTrackDisplay.useRelativeError = false;
//					System.out.println("case e : " + i);
//					break;
//				case 'E':
//					BeatTrackDisplay.allowedError = Double.parseDouble(args[++i]);
//					BeatTrackDisplay.useRelativeError = true;
//					System.out.println("case E : " + i);
//					break;
//				case 'P':
//					BeatTrackDisplay.usePScore = true;
//					System.out.println("case P : " + i);
//					break;
//				case 'h':
//					GUI.DEFAULT_HIGH_THRESHOLD = Double.parseDouble(args[++i]);
//					System.out.println("case h : " + i);
//					break;
//				case 'l':
//					GUI.DEFAULT_LOW_THRESHOLD = Double.parseDouble(args[++i]);
//					System.out.println("case l : " + i);
//					break;
//				case 's':
//					GUI.DEFAULT_SCALE_FACTOR = Double.parseDouble(args[++i]);
//					System.out.println("case s : " + i);
//					break;
//				case 'p':
//					playWithBeats = true;
//					System.out.println("case p");
//					break;
//				case 'o':
//					textOutputFile = args[++i];
//					batchMode = true;
//					System.out.println("case o : " + i);
//					break;
//				case 'O':
//					onsetOnly = true;
//					batchMode = true;
//					System.out.println("case O");
//					break;
//				case 'm':
//					argsFile = args[++i];
//					batchMode = true;
//					reader = null;
//					System.out.println("case m : " + i);
//					break;
//				case 't':
//					audioProcessor.hopTime = Double.parseDouble(args[++i]);
//					System.out.println("case t : " + i);
//					break;
//				case 'T':
//					audioProcessor.fftTime = Double.parseDouble(args[++i]);
//					System.out.println("case T : " + i);
//					break;
//				case 'w':
//					// audioProcessor.setLiveInput();
//					System.out.println("case w : lol");
//					break;
//				case 'c':
//					BeatTrackDisplay.centred = true;
//					System.out.println("case c");
//					break;
//				default:
//					warning("Illegal command line argument");
//				}
//			} else {
//				if (audioIn != null)
//					warning("Extra audio files ignored: " + args[i]);
//				else if (new File(args[i]).exists())
//					audioIn = args[i];
//				else
//					warning("Audio file not found: " + args[i]);
//			}
//		}
	} // processArgs()

	/** Constructor. Initialises the BeatRoot application,
	 *  including the GUI, and processes any command line arguments.
	 *  @param args Optional command line arguments.
	 *  @see #processArgs(String[])
	 */
	public BeatRoot() {
		batchMode = false;
		playWithBeats = false;
		argsFile = null;
		reader = null;
		fileChooser = null;
		gui = null;
		processArgs();
		audioProcessor = new AudioProcessor();
		
		if (!batchMode)
			fileChooser = new Chooser();
		audioPlayer = new AudioPlayer(null, fileChooser);
		if (!batchMode) {
			gui = new GUI(audioPlayer, audioProcessor, fileChooser);
			//gui.setVisible(true);
			if (audioIn != null) {
				System.out.println("audioIn ==> " +audioIn);
				gui.loadAudioData(audioIn);
				if (beatsIn != null)
					gui.loadBeatData(beatsIn);
				else
					gui.displayPanel.beatTrack();
				if (playWithBeats)
					audioPlayer.play();
			}
		}
		
		//avec visu
		gui.setVisible(false);
		gui.loadAudioData();
		gui.displayPanel.beatTrack();
		gui.displayPanel.resizeSpectroForVisu(12);
		gui.displayPanel.repaintImage();
		System.out.println("Dur�e de la musique (en s) : " + gui.displayPanel.maximumTime);
		/*
		//pour le projet (pas de visu, cr�ation de fichiers
		gui.loadAudioData();
		gui.displayPanel.beatTrack();
		double spectroMatrice = gui.displayPanel.resizeSpectroForFile();
		*/
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
	
	/** Entry point for BeatRoot application.
	 *  @param args Optional command line arguments (see constructor for details)
	 */
	public static void main(String[] args) {
		new BeatRoot();
	} // main()

} // class BeatRoot
