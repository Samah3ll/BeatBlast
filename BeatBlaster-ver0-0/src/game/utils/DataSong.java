package game.utils;

import java.util.ArrayList;

public class DataSong {
	
	private String pathSong;
	private double maxTimeSong;
	private ArrayList<Double> beats = new ArrayList<Double>();
	private double[][] spectro;
	
	public DataSong(String pathSong, double maxTimeSong, ArrayList<Double> beats, double[][] spectro){
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

	public ArrayList<Double> getBeats() {
		return beats;
	}

	public double[][] getSpectro() {
		return spectro;
	}


}
