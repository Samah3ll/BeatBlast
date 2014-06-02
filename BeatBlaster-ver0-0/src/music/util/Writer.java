package music.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Writer {
	
	public Writer()
	 {
	 }
	 
	 public boolean write(String pathFiles, String fileName, String data){
		try
		{
			/**
			 * BufferedWriter a besoin d un FileWriter, 
			 * les 2 vont ensemble, on donne comme argument le nom du fichier
			 * true signifie qu on ajoute dans le fichier (append), on ne marque pas par dessus 
			 
			 */
			String adressedufichier = pathFiles +"\\"+ fileName;
			System.out.println(adressedufichier);
			
			//methode pour tester l'existence
			File f = new File(adressedufichier);
			if ( f.exists() ) {
				System.out.println("This file already exists : " + adressedufichier);
				return false;
			}
			
			/* 
			 * FileWriter possède 2 arguments :
			 * 	- adresse du fichier (String)
			 *  - false signifie qu'on écrase le contenu du fichier et qu'on ne fait pas d'append
			 */
			
			FileWriter fileWriter = new FileWriter(adressedufichier, false);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			
			//on écrit dans le BufferedWriter qui sert de tampon(stream)
			bufferedWriter.write(data);
			// Remarque : on peut utiliser plusieurs fois la methode write
			
			//flush envoie dans le fichier
			bufferedWriter.flush();
			
			//On ferme le BufferedWriter
			bufferedWriter.close();
			
			System.out.println("File " + fileName + " made");
		}
		
		catch(IOException ioe){
			System.err.println("Erreur dans la méthode write ");
			ioe.printStackTrace();
		}
		
		return true;
	 }
	 
	 public boolean eraseFile(String pathFiles, String fileName){

				String adressedufichier = pathFiles + "\\" +fileName;
				
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
	 
}

