package game.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Classe Level, poss�de une taille (deux int width et height), une liste de blocks (tableau � 2 dimensions), une liste
 * de plateforms (tableau � une dimensions), et retient le nombre de plateforms. Quand une plateforme est ajout�e ses 
 * blocks sont ajout�es aux blocks du level.
 * @author SamaHell
 *
 */
public class Level {
	
	private int width;
	private int height;
	private Block[][] blocks;
	private Coin[][] coins;
	private ArrayList<Plateform> plateforms;
	
	/**
	 * Constructeur par valu� de level, intialise les donn�es membres du level.
	 * @param w : largeur du level en int.
	 * @param h : hauteur du level en int.
	 */
	public Level(int w, int h) {
		this.width = w;
		this.height = h;
		blocks = new Block[w][h];
		coins = new Coin[w][h];
		plateforms = new ArrayList<Plateform>();
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
	public ArrayList<Plateform> getPlateforms() {
		return plateforms;
	}
	
	/**
	 * Ajoute une plateforme au level, ajoute les blocks de la plateforme aux blocks du level.
	 * @param p : Plateform, la plateforme que l'on souhaite ajouter.
	 */
	public void addPlateform(Plateform p) {
		plateforms.add(p);
		for(int i = 0; i < p.getSize(); i++) {
			BasicBlock b = new BasicBlock( p.getPosition().x + i,  p.getPosition().y);
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
	 * Permet d'obtenir le block � la postion [x, y]. M�thode utilis�e pour calculer les collisions.
	 * @param x : abscisse du block.
	 * @param y : ordonn�e du block.
	 * @return le block � la postion [x, y] sp�cifi�e, si il n'y a pas de block retourne null.
	 */
	public Block get(int x, int y) {	
		return blocks[x][y];
	}
	
	/**
	 * Supprime le block � la position (x,y).
	 * @param x abscisse du block � supprimer.
	 * @param y ordonn�e du block � supprimer.
	 */
	public void removeBlock(int x, int y) {
		if(blocks[x][y] != null) {
			blocks[x][y].remove();
			blocks[x][y] = null;
		}
		
	}
	
	/**
	 * Retourne la plateform � la position sp�cifi�e. Si il n'y a pas de plateforme � la position sp�cifi�e retourne null.
	 * @param x l'abscisse
	 * @param y l'ordonn�e
	 * @return la plateforme � la position sp�cifi�e
	 */
	public Plateform getPlateform(int x, int y) {
		Plateform p;
		for(Iterator<Plateform> it = plateforms.iterator(); it.hasNext();) {
			if((p = it.next()) != null) {
				if(p.getPosition().y == y && p.getPosition().x == x) {						
					return p;					
				}
			}
			
		}
		return null;
	}
	
	/**
	 * Supprime la plateforme � la position sp�cifi�e.
	 * @param x l'abscisse de la plateforme � supprimer.
	 * @param y l'ordonn�e de la plateforme � supprimer.
	 */
	public void removePlateform(int x, int y) {
		//TODO : les plateformes ne se suppriment pas
		Plateform p;
		if((p = getPlateform(x, y)) != null) {
			for(int i = 0; i < p.getSize(); i++) {
				removeBlock(x, y + i);
			}
			plateforms.remove(p);				
			p.remove();
			p = null;
		}
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

	public void dispose() {
		blocks = null;
		plateforms = null;
		
	}
	
	/**
	 * ajoute une piece � la position x, y (en int)
	 * @param x
	 * @param y
	 */
	public void addCoin(int x, int y) {
		coins[x][y] = new Coin(x, y);
	}

	/**
	 * 
	 * @return un ArrayList<Coin> contenant la liste des pieces du niveau.
	 */
	public ArrayList<Coin> getCoins() {
		ArrayList<Coin> coinsList = new ArrayList<Coin>();
		Coin c;
		for(int col = 0; col < width; col++) {
			for(int row = 0 ; row < height ; row++) {
				c = coins[col][row];
				if(c != null) {
					coinsList.add(c);
				}
			}
		}
		return coinsList;
	}
}
