package music.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ListIterator;

import de.matthiasmann.twlthemeeditor.gui.SaveFileSelector;

public class Writer {
	
	/*TODO Changer la fa�on de d�terminer le saveDirectory, actuellement c'est d�gueulasse (en dur) */
	/** Dossier o� seront stock�es les sauvegardes */
	//final String path = System.getProperty("user.dir");
	//final String saveDirectory = path.substring(0, path.length() - 18) + "save";
	
	public Writer()	{}
	 
	 public boolean write(String saveDirectory, String pathFile, double songTime, ListIterator<Event> beatPtr, double[][] spectro){
		try
		{
			/**
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
			/** 
			 * FileWriter poss�de 2 arguments :
			 * 	- adresse du fichier (String)
			 *  - false signifie qu'on �crase le contenu du fichier et qu'on ne fait pas d'append
			 */
			
			FileWriter fileWriter = new FileWriter(dataFilePath, true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			
			//on �crit dans le BufferedWriter qui sert de tampon(stream)
//			bufferedWriter.write("pathSong " + pathFile);
//			bufferedWriter.write("\n");
			bufferedWriter.write("maxTimeSong " + Math.floor(songTime*100)/100);
			
			bufferedWriter.write("\n ---beats--- \n");
			/**on va au d�but de l'eventlist*/
			while(beatPtr.hasPrevious())
				beatPtr.previous();
			/**on �crit les beats sur une ligne, arrondis au centi�me*/
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
	 
}

