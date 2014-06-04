package game.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Reader {

		public DataSong read(String pathFiles, String fileName) {

			Scanner scanner = null;
			String line = null;
			
			/**Initialisation des variables nécessaires à la création du DataSong*/
			String pathSong = "";
			double maxTimeSong = 0;
			ArrayList<Float> beats = new ArrayList<Float>();
			double[][] spectro = null;
			
			/** Lecture */
			try {
				scanner = new Scanner(new File(pathFiles +"\\"+ fileName));

				// On boucle sur chaque champ detecté
				while (scanner.hasNextLine()) {
					line = scanner.nextLine();
					if(line!=null){
//						if(line.contains("pathSong")){
//							String[] song = line.split(" ");
//							for(int a =1;a<song.length;a++){
//								pathSong+=song[a];
//							}
//						}
						if(line.contains("maxTimeSong")){
							String[] time = line.split(" ");
							maxTimeSong = Double.parseDouble(time[1]);
						}
						else if(line.contains("---beats---")){
							line = scanner.nextLine();
							String[] beatTab = line.split(" ");
							for(int i =0; i<beatTab.length;i++){
								beats.add(Float.parseFloat(beatTab[i]));
							}
						}
						else if(line.contains("---spectro---")){
							line = scanner.nextLine();
							String[] LinesAndRows = line.split(" ");
							spectro = new double[ Integer.parseInt(LinesAndRows[0]) ][ Integer.parseInt(LinesAndRows[1]) ];
							String[] spectroPiece;
							for(int j = 0;j<spectro.length;j++){
								line = scanner.nextLine();
								spectroPiece = line.split(" ");
								for(int k =0; k<spectro[0].length;k++){
									spectro[j][k]= Double.parseDouble(spectroPiece[k]);
								}
									
							}
						}
					}
				}
				scanner.close();
			} catch (FileNotFoundException e) {
				System.err.println("Erreur dans la méthode reader ");
				e.printStackTrace();
			}
			
			return new DataSong(pathSong, maxTimeSong, beats, spectro);
		}
		
		//Permet de faire une liste des fichiers à l'intérieur d'un dossier
		public ArrayList<String> getListFiles(String directoryPath) {
			
			ArrayList<String> listFiles = new ArrayList<String>();
			File directory = new File(directoryPath);
			
			if(!directory.exists()){
				System.out.println("Le fichier/répertoire '"+directoryPath+"' n'existe pas");
			}else if(!directory.isDirectory()){
				System.out.println("Le chemin '"+directoryPath+"' correspond à un fichier et non à un répertoire");
			}else{
				File[] subfiles = directory.listFiles();
				String message = "Le répertoire '"+directoryPath+"' contient "+ subfiles.length+" fichier"+(subfiles.length>1?"s":"");
				System.out.println(message);
				for(int i=0 ; i<subfiles.length; i++){
					listFiles.add(subfiles[i].getName());
				}
			}
			
			return listFiles;
		}
		
	}// End public class Utilities


