package music.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ListIterator;

import de.matthiasmann.twlthemeeditor.gui.SaveFileSelector;

public class Writer {
	
	/*TODO Changer la fa�on de d�terminer le saveDirectory, actuellement c'est d�gueulasse (en dur) */
	/* Dossier o� seront stock�es les sauvegardes */
	//final String path = System.getProperty("user.dir");
	//final String saveDirectory = path.substring(0, path.length() - 18) + "save";
	private String saveDirectory=null;
	
	public Writer(String saveDirectory)	{
		this.saveDirectory = saveDirectory;
	}
	 
	 public boolean write(String pathFile, double songTime, ListIterator<Event> beatPtr, double[][] spectro){
		try
		{
			/*
			 * BufferedWriter a besoin d un FileWriter, 
			 * les 2 vont ensemble, on donne comme argument le nom du fichier
			 * true signifie qu on ajoute dans le fichier (append), on ne marque pas par dessus 
			 */
			
			String dataFilePath = saveDirectory + "\\" +getFileName(pathFile) + ".dat";
			
			//methode pour tester l'existence
			File f = new File(dataFilePath);
			if ( f.exists() ) {
				System.out.println("This file already exists : " + dataFilePath);
				return false;
			}
			// sauter une ligne \n
			/*
			 * FileWriter poss�de 2 arguments :
			 * 	- adresse du fichier (String)
			 *  - false signifie qu'on �crase le contenu du fichier et qu'on ne fait pas d'append
			 */
			
			FileWriter fileWriter = new FileWriter(dataFilePath, false);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			
			//on �crit dans le BufferedWriter qui sert de tampon(stream)
//			bufferedWriter.write("pathSong " + pathFile);
//			bufferedWriter.write("\n");
			bufferedWriter.write("maxTimeSong " + Math.floor(songTime*100)/100);
			
			bufferedWriter.write("\n ---beats--- \n");
			/*on va au d�but de l'eventlist*/
			while(beatPtr.hasPrevious())
				beatPtr.previous();
			/*on �crit les beats sur une ligne, arrondis au centi�me*/
			while(beatPtr.hasNext()){
				//System.out.println(Math.floor(beatPtr.next().keyDown*100)/100);
				bufferedWriter.write(Math.floor(beatPtr.next().keyDown*100)/100 + " ");
			}
			
			bufferedWriter.write("\n ---spectro--- \n");
			bufferedWriter.write(spectro.length + " " + spectro[0].length +"\n");
			for(int i =0;i<spectro.length;i++){
				for(int j =0;j<spectro[0].length;j++)
					bufferedWriter.write(Math.floor(spectro[i][j]*100)/100 + " ");
				bufferedWriter.write("\n");
			}
			// Remarque : on peut utiliser plusieurs fois la methode write
			
			//flush envoie dans le fichier
			bufferedWriter.flush();
			
			//On ferme le BufferedWriter
			bufferedWriter.close();
			
			System.out.println("File " + dataFilePath + " made");
		}
		
		catch(IOException ioe){
			System.err.println("Erreur dans la m�thode write ");
			ioe.printStackTrace();
		}
		
		return true;
	 }
	 
	 public boolean eraseFile(String pathFile, String fileName){

				String adressedufichier = pathFile + "\\" +fileName;
				
				//methode pour tester l'existence
				File f = new File(adressedufichier);
				if ( !f.exists() ) {
					System.out.println("This file does not exists : " + adressedufichier);
					return false;
				}
				
				boolean tmp =f.delete();
				if(tmp){
					System.out.println("File " + fileName + " deleted");
					return true;
				}
				else{
					System.out.println("File " + fileName + " failed to be deleted");
					return false;
				}
	 }
	 
	 public String getFileName(String pathFile){
		 String[] tmp = pathFile.split("\\\\");
		 return tmp[tmp.length-1];
	 }
	 
	 public void copyFileBuffered(final String currentFile, final int bufferSize) throws FileNotFoundException, IOException {
		 

		 FileChannel in = null; // canal d'entr�e
		 FileChannel out = null; // canal de sortie
 
		 try {
			 // Init
			 in = new FileInputStream(currentFile).getChannel();
			 out = new FileOutputStream(saveDirectory + "\\" + getFileName(currentFile)).getChannel();
 
			 // Copie depuis le in vers le out
			 in.transferTo(0, in.size(), out);
		 } catch (Exception e) {
			 e.printStackTrace(); // n'importe quelle exception
		 } finally { // finalement on ferme
			 if(in != null) {
				 try {
					 in.close();
				 } catch (IOException e) {}
			 }
			 if(out != null) {
				 try {
					 out.close();
				 } catch (IOException e) {}
			 }
		 }

		 /*
		 final BufferedReader in = new BufferedReader(new FileReader(currentFile), bufferSize * 1024);
		    
	    String newDataFilePath = saveDirectory + "\\" +getFileName(currentFile);
	    
	    try {
	    	final BufferedWriter out = new BufferedWriter(new FileWriter(newDataFilePath), bufferSize * 1024);
	    	try {
	    		int s = in.read();
	    		while(s != -1) {
	    			out.write(s);
	    			s = in.read();
	        }//end while
	    		out.flush();
	    	} finally {
	    		out.close();
	    	}//end try
	    } finally {
	    	in.close();
	    }//end try
	    */
	 }//end copyFileBuffered
}

