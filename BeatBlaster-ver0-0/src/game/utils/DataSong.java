package game.utils;

import java.util.ArrayList;

public class DataSong {
	
	private String pathSong;
	private double maxTimeSong;
	private ArrayList<Float> beats = new ArrayList<Float>();
	private double[][] spectro;
	
	public DataSong(String pathSong, double maxTimeSong, ArrayList<Float> beats, double[][] spectro){
		this.pathSong = pathSong;
		this.maxTimeSong = maxTimeSong;
		this.beats=beats;
		this.spectro = spectro;
	}

	public String getPathSong() {
		return pathSong;
	}

	public double getMaxTimeSong() {
		return maxTimeSong;
	}

	public ArrayList<Float> getBeats() {
		return beats;
	}

	public double[][] getSpectro() {
		return spectro;
	}

	public int bestSpectro(int abs) {
		int bestIndex=0;
		for(int i =1; i<spectro[0].length;i++){
			if(spectro[abs][i]>spectro[abs][bestIndex])
				bestIndex=i;
		}
		return bestIndex;
	}
	
	public int bestLowSpectro(int abs) {
		int bestIndex=0;
		for(int i =0; i<spectro[0].length/2;i++){ //spectro[0].length =12
			if(spectro[abs][i]>spectro[abs][bestIndex])
				bestIndex=i;
		}
		return bestIndex;
	}
	
	/**
	 * Divise le spectrogramme en 3 catégories (haute, milieu, basse)
	 *  et choisit les meilleures valeurs dans chaque catégorie
	 * */
	public int[] threeBestSpectro(int abs) {
		int[] bestIndex= new int[3];
		
		for(int i =0; i<4;i++){ //spectro[0].length =12
			if(spectro[abs][i]>spectro[abs][bestIndex[0]])
				bestIndex[0]=i;
		}
		for(int i =4; i<8;i++){ //spectro[0].length =12
			if(spectro[abs][i]>spectro[abs][bestIndex[1]])
				bestIndex[1]=i;
		}
		for(int i =8; i<12;i++){ //spectro[0].length =12
			if(spectro[abs][i]>spectro[abs][bestIndex[2]])
				bestIndex[2]=i;
		}
		return bestIndex;
	}
}
