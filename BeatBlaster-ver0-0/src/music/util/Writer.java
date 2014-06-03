package music.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ListIterator;

public class Writer {
	
	/*TODO Changer la façon de déterminer le saveDirectory, actuellement c'est dégueulasse (en dur) */
	/** Dossier où seront stockées les sauvegardes */
	final String path = System.getProperty("user.dir");
	final String saveDirectory = path.substring(0, path.length() - 18) + "save";
	
	public Writer()	{}
	 
	 public boolean write(String pathFile, double songTime, ListIterator<Event> beatPtr, double[][] spectro){
		try
		{
			/**
			 * BufferedWriter a besoin d un FileWriter, 
			 * les 2 vont ensemble, on donne comme argument le nom du fichier
			 * true signifie qu on ajoute dans le fichier (append), on ne marque pas par dessus 
			 */
			
			String newFileName = pathFile + ".dat";
			
			//methode pour tester l'existence
			File f = new File(newFileName);
			if ( f.exists() ) {
				System.out.println("This file already exists : " + newFileName);
				return false;
			}
			// sauter une ligne \n
			/* 
			 * FileWriter possède 2 arguments :
			 * 	- adresse du fichier (String)
			 *  - false signifie qu'on écrase le contenu du fichier et qu'on ne fait pas d'append
			 */
			
			FileWriter fileWriter = new FileWriter(newFileName, true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			
			//on écrit dans le BufferedWriter qui sert de tampon(stream)
			bufferedWriter.write(pathFile);
			bufferedWriter.write("\n");
			bufferedWriter.write("maxTimeSong " + songTime);
			
			bufferedWriter.write("\n ---beats--- \n");
			/**on va au début de l'eventlist*/
			while(beatPtr.hasPrevious())
				beatPtr.previous();
			/**on écrit les beats sur une ligne, arrondis au centième*/
			while(beatPtr.hasNext()){
				//System.out.println(Math.floor(beatPtr.next().keyDown*100)/100);
				bufferedWriter.write(Math.floor(beatPtr.next().keyDown*100)/100 + " ");
			}
			
			bufferedWriter.write("\n ---spectro--- \n");
			//System.out.println(spectro.length + " " + spectro[0].length);
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
			
			System.out.println("File " + newFileName + " made");
		}
		
		catch(IOException ioe){
			System.err.println("Erreur dans la méthode write ");
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
		//Crée le dossier de sauvegarde
		public void createSaveRepertory() {
			File folder = new File(saveDirectory);
			if(folder.mkdir()) {
				System.out.println("new folder created");
			}
		}
		
		public String getSaveDirectory() {
			return saveDirectory;
		}
	 
}

