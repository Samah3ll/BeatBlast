 package game.utils;

import game.model.BasicBlock;
import game.model.BasicPlateform;
import game.model.Coin;
import game.model.Level;

public class LevelGenerator {
	
	private Level level;
	private DataSong dataSong;
	private final int coeff = 12;
	private final double timeBeforeMusicBegin = 1000; //in Millis
	private float nbBlocksBefore; //est calculé dans le constructeur
	private float x, lastX, y, lastY, l, lastL, coinPos;
	
	public LevelGenerator(DataSong ds) {
		this.dataSong = ds;
		nbBlocksBefore = (float)(coeff*timeBeforeMusicBegin/1000);//timeBeforeMusicBegin * coeff; //selon d = v*t
		this.level = new Level((int)((ds.getMaxTimeSong()) * coeff + 2 + nbBlocksBefore*2), 16); //+2 pour le blocks de début et celui de fin
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
	
	/**
	 * Ajoute les plateformes au level en fonction de la musique passée en DataSong.
	 * 
	 * Principe de l'algo : à chaque étape on regarde si la plateforme est au même niveau que la précedente,
	 * 		si oui on allonge la plateforme,
	 * 		si non on ajoute la plateforme précédente.
	 * A chaque étape on ajoute non pas la plateforme actuelle mais la précédente.
	 * 
	 * @param ds le DataSong de la musique.
	 */
	public void generateSpectroBlocks(DataSong ds) {
		
		BasicPlateform p;
		Coin c;
		lastY = 0;
		lastL = 0;
		
		for(int i = 0; i < ds.getBeats().size()-1; i++){
			if( i<ds.getSpectro().length) {
				
				//abcisses, +1 pour les bounds
				x = (float) (ds.getBeats().get(i)*coeff+1+nbBlocksBefore);
				
				// ordonnées, +1 pour les bounds
				y = ds.bestSpectro(i)+1;
				
				//Longueur de la plateforme
				l = ((ds.getBeats().get(i+1)-ds.getBeats().get(i))*coeff);
				
				//Position de la pièce sur la plateforme (au milieu de la plateforme).
				coinPos = (float) Math.floor((1 + l) / 2);				
				
				//Si la plateforme suivante est trop haute.
				if(y - lastY > 4) {
					//alors on la descend.
					y = lastY + 4;
				}
				
				c = new Coin(x + coinPos, y + 1);
				level.addCoin(c);
				
				//Si la plateforme suivante est à la même hauteur que la précédente.
				if(lastY == y) {
					//On rallonge la plateforme.
					x = lastX;
					l += lastL;
				} else {
					p = new BasicPlateform(lastX, lastY, (int) lastL);
					
					level.addPlateform(p);
				}
				lastX = x;
				lastY = y;
				lastL = l;
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
