package game.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Reader {

		public String read(String pathFiles, String fileName) {

			Scanner scanner = null;
			String line = null;
			StringBuffer str = new StringBuffer();
			try {
				scanner = new Scanner(new File(pathFiles +"\\"+ fileName));

				// On boucle sur chaque champ detect�
				while (scanner.hasNextLine()) {
					line = scanner.nextLine();

					if (line != null)
						str.append(line + "\r\n");
				}

				scanner.close();
			} catch (FileNotFoundException e) {
				System.err.println("Erreur dans la m�thode reader ");
				e.printStackTrace();
			}
			
			return str.toString();
		}
		
		//Permet de faire une liste des fichiers � l'int�rieur d'un dossier
		public ArrayList<String> getListFiles(String directoryPath) {
			
			ArrayList<String> listFiles = new ArrayList<String>();
			File directory = new File(directoryPath);
			
			if(!directory.exists()){
				System.out.println("Le fichier/r�pertoire '"+directoryPath+"' n'existe pas");
			}else if(!directory.isDirectory()){
				System.out.println("Le chemin '"+directoryPath+"' correspond � un fichier et non � un r�pertoire");
			}else{
				File[] subfiles = directory.listFiles();
				String message = "Le r�pertoire '"+directoryPath+"' contient "+ subfiles.length+" fichier"+(subfiles.length>1?"s":"");
				System.out.println(message);
				for(int i=0 ; i<subfiles.length; i++){
					listFiles.add(subfiles[i].getName());
				}
			}
			
			return listFiles;
		}
		
	}// End public class Utilities


