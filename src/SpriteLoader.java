import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;


public class SpriteLoader {

    protected ArrayList<BufferedImage> imageSetCopy;

    protected SpriteLoader () {

        imageSetCopy = new ArrayList<BufferedImage>();

        //Does this still need to be here? Why? -Lucas
        imageSetCopy.add(loadImage("main/resources/groundDirt.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundDirt.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundDirt.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundDirt.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundDirt.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundDirt.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundDirt.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundDirt.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundDirt.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundDirt.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundDirt.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundDirt.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundGrass.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundGrass.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundGrass.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundGrass.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundGrass.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundGrass.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundGrass.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundGrass.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundGrass.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundGrass.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundGrass.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundGrass.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundDirt.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundDirt.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundDirt.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundDirt.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundDirt.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundDirt.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundDirt.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundDirt.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundDirt.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundDirt.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundDirt.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundDirt.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundGrass.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundGrass.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundGrass.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundGrass.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundGrass.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundGrass.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundGrass.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundGrass.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundGrass.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundGrass.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundGrass.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundGrass.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundMetal.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundMetal.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundMetal.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundMetal.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundMetal.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundMetal.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundMetal.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundMetal.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundMetal.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundMetal.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundMetal.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundMetal.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundMetal.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundMetal.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundMetal.jpg"));
        imageSetCopy.add(loadImage("main/resources/groundMetal.jpg"));




        for (int i = imageSetCopy.size(); i < 65; i++){
            imageSetCopy.add(null);
        }
        imageSetCopy.add(loadImage("main/resources/ANGRY.png"));


        for (int i = imageSetCopy.size(); i < 129; i++){
            imageSetCopy.add(null);
        }

        imageSetCopy.add(loadImage("main/resources/backgroundMenu.png"));
        imageSetCopy.add(loadImage("main/resources/solidXBackground.png"));
        imageSetCopy.add(loadImage("main/resources/solidYBackground.png"));


    }

    public BufferedImage loadImage(String image_file){
        BufferedImage img = null;
        try {
            img = ImageIO.read(getClass().getResource(image_file));
        } catch (IOException e) {
            System.out.println("Error loading image.");
        }
        return img;
    }
}
