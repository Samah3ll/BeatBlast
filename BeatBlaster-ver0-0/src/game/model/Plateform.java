package game.model;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

/**
 * @author SamaHell
 * Classe abstraite qui définit le comportement de base d'une plateforme.
 * Une plateforme est un ensemble de blocks (un tableau de block à 2 dimensions).
 */
public abstract class Plateform {
	
	private Vector2 position;
	private int size;

	private Block[][] blocks;
	
	
	/**
	 * Contructeur valué qui prend en paramètre la position (avec un Vector2) de la plateforme (origine en bas à gauche)
	 * et sa taille en nombre de blocks
	 * @param pos : position de la plateforme
	 * @param size : taille de la plateforme en nombre de blocks
	 */
	public Plateform(Vector2 pos, int size) {
		this.position = pos;
		this.size = size;
		blocks = new Block[size][1];
		for(int i = 0; i < size; i++) {
			blocks[i][0] = new BasicBlock( (int) (position.x + i), (int) position.y);
		}
	}
	
	/**
	 * Contructeur valué qui prend en paramètre la position (avec deux int) de la plateforme (origine en bas à gauche)
	 * et sa taille en nombre de blocks. Ce constructeur va créer un vecteur.
	 * @param x : abscisse de la plateforme
	 * @param y : ordonnée de la plateforme
	 * @param size : taille de la plateforme en nombre de blocks
	 */
	public Plateform(int x, int y, int size) {
		this.position = new Vector2(x, y);
		this.size = size;
		blocks = new Block[size][1];
		for(int i = 0; i < size; i++) {
			blocks[i][0] = new BasicBlock( (int) (position.x + i), (int) position.y);
		}
	}
	
	/**
	 * 
	 * @return la position de la plateforme (position du block en bas à gauche) en un Vector2.
	 */
	public Vector2 getPosition() {
		return position;
	}
	
	public void setPosition(Vector2 pos) {
		this.position = pos;
	}
	
	public int getSize() {
		return size;
	}
	
	/**
	 * 
	 * @return La liste (ArrayList<Block>) des blocks de la plateforme.
	 */
	public List<Block> getPlateformAsBlock() {
		ArrayList<Block> blockList = new ArrayList<Block>();
		for(int i = 0; i < size; i++) {
			blockList.add(blocks[i][0]);
			
		}
		return blockList;
	}
	
	public String toString() {
		StringBuffer tmp = new StringBuffer("Plateform positionX :");
		tmp.append(position.x);
		tmp.append("\tpositionX : ");
		tmp.append(position.y);
		tmp.append("\tsize : ");
		tmp.append(size);
		tmp.append("\t");
		
		return tmp.toString();
	}

}
