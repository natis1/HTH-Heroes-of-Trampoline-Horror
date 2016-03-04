package Base;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class SpriteLoader {

    private static HashMap<String, BufferedImage> images;
    public ArrayList<String> backgroundKeys;

    public SpriteLoader () {

        images = new HashMap<>();
        backgroundKeys = new ArrayList<>();
        generateBattleSet();
        generateGUISet();
        generateBackgroundSet();
    }

    public void generateBackgroundSet()
    {
        images.put("grass",    loadImage("../Base/Resources/groundGrass.png"));
        backgroundKeys.add("grass");
        images.put("sidewalk", loadImage("../Base/Resources/groundSidewalk.png"));
        backgroundKeys.add("sidewalk");
    }

    public void generateBattleSet(){
        images.put("angry", loadImage("../Base/Resources/ANGRY.png"));
        images.put("battleScreen", loadImage("../Base/Resources/battleScreen.png"));
    }

    public void generateGUISet()
    {
        images.put("menuBack", loadImage("../Base/Resources/menuBack.png"));
        images.put("mainMenu", loadImage("../Base/Resources/mainMenu.png"));
        images.put("newGame",  loadImage("../Base/Resources/newGame.png"));
        images.put("loadGame", loadImage("../Base/Resources/loadGame.png"));
        images.put("options",  loadImage("../Base/Resources/options.png"));
    }

    public BufferedImage unsafeGetBackgroundImage (int index)
    {
        index %= backgroundKeys.size();
        return images.get(backgroundKeys.get(index));
    }

    public BufferedImage returnImageFromSet (String index)
    {
        return images.get(index);
    }

    public BufferedImage loadImage(String image_file){
        BufferedImage img;
        try
        {
            img = ImageIO.read(getClass().getResource(image_file));
        }
        catch (IOException e)
        {
            System.out.println("Error loading image.");
            return null;
        }

        return img;
    }
}
