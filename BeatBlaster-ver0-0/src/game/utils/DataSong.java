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
		for(int i =1; i<spectro[0].length/2;i++){
			if(spectro[abs][i]>spectro[abs][bestIndex])
				bestIndex=i;
		}
		return bestIndex;
	}


}
