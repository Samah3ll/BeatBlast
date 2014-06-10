 package game.utils;

import game.model.BasicBlock;
import game.model.BasicPlateform;
import game.model.Level;

public class LevelGenerator {
	
	private Level level;
	private DataSong dataSong;
	private final int coeff = 12;
	private final double timeBeforeMusicBegin = 1000; //in Millis
	private float nbBlocksBefore; //est calculé dans le constructeur
	
	public LevelGenerator(DataSong ds) {
		this.dataSong = ds;
		nbBlocksBefore = (float)(coeff*timeBeforeMusicBegin/1000);//timeBeforeMusicBegin * coeff; //selon d = v*t
		this.level = new Level((int)((ds.getMaxTimeSong() + 2 + nbBlocksBefore*2) * coeff), 16); //+2 pour le blocks de début et celui de fin
	}
	
	public DataSong getDataSong(){
		return dataSong;
	}
	/**Création des blocks de contour */
	private void generateBounds() {
		//bas et haut
		for(int i = 0; i < level.getWidth(); i++) {
			BasicBlock b = new BasicBlock(i, 0);
			level.addBlock(b);
			BasicBlock b1 = new BasicBlock(i, level.getHeight() - 1);
			level.addBlock(b1);
		}

		//cotés
		for(int i = 0; i < level.getHeight(); i++) {
			BasicBlock b = new BasicBlock(0, i);
			level.addBlock(b);
			BasicBlock b1 = new BasicBlock(level.getWidth() - 1, i);
			level.addBlock(b1);
		}
	}
	
	public void generateBeatBlocks(DataSong ds) {
		for(int i = 0; i < ds.getBeats().size(); i++){
			BasicBlock b = new BasicBlock(((float) ds.getBeats().get(i)) * coeff, 1f);
			level.addBlock(b);
		}
	}
	
	public void generateSpectroBlocks(DataSong ds) {
		int tmp=11; //on initialise haut pour ne pas avoir de pb
		for(int i = 0; i < ds.getBeats().size()-1; i++){
			if( i<ds.getSpectro().length){
				BasicPlateform p0 = new BasicPlateform((float)(ds.getBeats().get(i)*coeff+1+nbBlocksBefore), //abcisses, +1 pour les bounds
														ds.bestSpectro(i)+1, // ordonnées, +1 pour les bounds
														(int)((ds.getBeats().get(i+1)-ds.getBeats().get(i))*coeff));
				level.addPlateform(p0);
				if((ds.bestSpectro(i)>5 || Math.abs(ds.bestSpectro(i)- tmp)> 4) && Math.abs(ds.bestSpectro(i)- ds.bestLowSpectro(i))>4){
					BasicPlateform p1 = new BasicPlateform(ds.getBeats().get(i)*coeff+1+nbBlocksBefore,
															ds.bestLowSpectro(i)+1,
															(int)((ds.getBeats().get(i+1)-ds.getBeats().get(i))*coeff));
					level.addPlateform(p1);
				}
				tmp = ds.bestSpectro(i);
//				BasicPlateform p1 = new BasicPlateform(ds.getBeats().get(i)*coeff,ds.threeBestSpectro(i)[1]+1, (int)((ds.getBeats().get(i+1)-ds.getBeats().get(i))*coeff)-1);
//				level.addPlateform(p1);
//				BasicPlateform p2 = new BasicPlateform(ds.getBeats().get(i)*coeff,ds.threeBestSpectro(i)[2]+1, (int)((ds.getBeats().get(i+1)-ds.getBeats().get(i))*coeff)-1);
//				level.addPlateform(p2);
			}
		}
	}
	
	public void generateHelpBlocks(DataSong ds) {
		int a = 0;
		for(int i = 0; i < ds.getBeats().size()-1; i++){
			if(a <9){
				BasicPlateform p = new BasicPlateform(ds.getBeats().get(i)*coeff,a+1, (int)((ds.getBeats().get(i+1)-ds.getBeats().get(i))*coeff));
				level.addPlateform(p);
			}
			if(a>60)
				a=0;
			a += 2;
		}
	}
	
	public Level generateLevel() {
		generateBounds();
		//generateBeatBlocks(dataSong);
		generateSpectroBlocks(dataSong);
		//generateHelpBlocks(dataSong);
		return level;
	}

	public void dispose() {
		dataSong = null;
		
	}
	
	public int getCoeff(){
		return coeff;
	}
	
	public float getnbBlocksBefore(){
		return nbBlocksBefore;
	}
	
	public double getTimeBeforeMusicBegin(){
		return timeBeforeMusicBegin;
	}
}
