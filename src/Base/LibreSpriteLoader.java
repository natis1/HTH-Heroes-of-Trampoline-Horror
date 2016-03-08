package Base;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class LibreSpriteLoader {

    public ArrayList<BufferedImage> backgroundImages = new ArrayList<BufferedImage>();

    private final String DIRECTORY = "src/Base/Resources/overworld";

    public LibreSpriteLoader() {


        backgroundImages.add(loadImage(DIRECTORY + "Sidewalk.png")); //Don't need to do this manually, but for now, it will be alright
        backgroundImages.add(loadImage(DIRECTORY + "Grass.png"));


    }

    public BufferedImage unsafeGetBackgroundImage (int index) {
        index %= backgroundImages.size();
        return backgroundImages.get(index);
    }

    public BufferedImage returnImageFromSet (int index) {
        return backgroundImages.get(index);
    }

    public BufferedImage loadImage(String image_file) {
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
