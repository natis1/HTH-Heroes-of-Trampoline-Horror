package Base;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class SpriteLoader {

    private static HashMap<String, BufferedImage> images;
    public ArrayList<String> backgroundKeys;

    public SpriteLoader () {

        images = new HashMap<>();
        backgroundKeys = new ArrayList<>();
        String dir  = System.getProperty("user.dir") + "/src/Base/Resources/";
        loadDir(dir);
        backgroundKeys.add("overworldGrass"); //Don't need to do this manually, but for now, it will be alright
        backgroundKeys.add("overworldSidewalk");
    }

    public BufferedImage unsafeGetBackgroundImage (int index) {
        index %= backgroundKeys.size();
        return images.get(backgroundKeys.get(index));
    }

    public BufferedImage returnImageFromSet (String index) {
        return images.get(index);
    }

    public void loadDir(String directory) {
        File dir = new File(directory);
        if(dir.isDirectory())
        {
            File[] directoryListing = dir.listFiles();
            if (directoryListing != null) {
                for (File child : directoryListing) {
                    String name = child.getName().replace(".png", "").replace(".jpg", "");
                    images.put(name, loadImage("../Base/Resources/" + child.getName()));
                }
            }
        }
        else
        {
            System.out.println("Attempted to load images from a non-existent directory");
        }
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
