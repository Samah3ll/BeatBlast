package game.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe Level, possède une taille (deux int width et height), une liste de blocks (tableau à 2 dimensions), une liste
 * de plateforms (tableau à une dimensions), et retient le nombre de plateforms. Quand une plateforme est ajoutée ses 
 * blocks sont ajoutées aux blocks du level.
 * @author SamaHell
 *
 */
public class Level {
	
	private int width;
	private int height;
	private Block[][] blocks;
	private Plateform[] plateforms;
	private int nbPlateform = 0;
	
	/**
	 * Constructeur par valué de level, intialise les données membres du level.
	 * @param w : largeur du level en int.
	 * @param h : hauteur du level en int.
	 */
	public Level(int w, int h) {
		this.width = w;
		this.height = h;
		blocks = new Block[w][h];
		plateforms = new Plateform[10];
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	/**
	 * 
	 * @return la liste des plateforms (ArrayList<Plateform>)
	 */
	public List<Plateform> getPlateforms() {
		ArrayList<Plateform> pl = new ArrayList<Plateform>();
		for(int i =0; i < nbPlateform; i++) {
			pl.add(plateforms[i]);
		}
		return pl;
	}
	
	/**
	 * Ajoute une plateforme au level, ajoute les blocks de la plateforme aux blocks du level.
	 * @param p : Plateform, la plateforme que l'on souhaite ajouter.
	 */
	public void addPlateform(Plateform p) {
		plateforms[nbPlateform] = p;
		nbPlateform++;
		for(Block b : p.getPlateformAsBlock()) {
			addBlock(b);
		}
	}
	
	/**
	 * Permet d'obtenir tout les blocks du level (m^me ceux des plateformes).
	 * @return la liste des blocks du level (Arraylist<Block>).
	 */
	public List<Block> getBlocks() {
		
		List<Block> b = new ArrayList<Block>();
		Block block;
		for (int col = 0; col < width; col++) {
			for (int row = 0; row < height; row++) {
				block = blocks[col][row];
				if (block != null) {
					b.add(block);
				}
			}
		}
		return b;
	}

	public void setBlocks(Block[][] blocks) {
		this.blocks = blocks;
	}
	
	public void addBlock(Block b) {
		if(b != null) {
			int w = (int) b.getPosition().x;
			int h = (int) b.getPosition().y;
			blocks[w][h] = b;
		} else {
			System.err.println("Level -> addBlock : null block");
		}
		
	}

	/**
	 * Permet d'obtenir le block à la postion [x, y]. Méthode utilisée pour calculer les collisions.
	 * @param x : abscisse du block.
	 * @param y : ordonnée du block.
	 * @return le block à la postion [x, y] spécifiée, si il n'y a pas de block retourne null.
	 */
	public Block get(int x, int y) {	
		return blocks[x][y];
	}
	
	public String toString() {
		StringBuffer tmp = new StringBuffer("Level : \n");
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				if(blocks[i][j] != null) {
					tmp.append("block at : ");
					tmp.append(i);
					tmp.append("; ");
					tmp.append(j);
					tmp.append("\n");
				}
			}
		}
		
		return tmp.toString();
	}

	public void moveLevel() {
		for(int i = 0; i < width; i++ ) {
			for(int j = 0; j < height; j++) {
				blocks[i][j] = blocks[i + 1][j];
			}
		}
		
	}

}
