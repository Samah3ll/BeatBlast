package game.utils;

import com.badlogic.gdx.tools.imagepacker.TexturePacker2;

/**
 * Cr�e un pack des images, il n'y a plus qu'une image a charge plutot que plusieurs.
 * @author SamaHell
 *
 */
public class TextureSetup {
	
	public static void main(String[] args) {
		//GameScreen textures
		final String path = System.getProperty("user.dir");
        TexturePacker2.process(path + "/res/img/game",
        					   path + "/res/img/game",
        						"textures.pack");
        //MenuScreen textures
        TexturePacker2.process(path + "/res/img/menu",
				    			path + "/res/img/menu",
					"textures.pack");
        //PauseScreen textures
        TexturePacker2.process(path + "/res/img/pause",
				    			path + "/res/img/pause",
					"textures.pack");
      //SelectionScreen textures
        TexturePacker2.process(path + "/res/img/selection",
				    			path + "/res/img/selection",
					"textures.pack");
      //ChooseScreen textures
        TexturePacker2.process(path + "/res/img/choose",
				    			path + "/res/img/choose",
					"textures.pack");
      //LooseScreen textures
        TexturePacker2.process(path + "/res/img/loose",
				    			path + "/res/img/loose",
					"textures.pack");
      //WinScreen textures
        TexturePacker2.process(path + "/res/img/win",
				    			path + "/res/img/win",
					"textures.pack");
        System.out.println("Done");
    }

}
