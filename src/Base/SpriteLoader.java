package Base;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class SpriteLoader {

    private ArrayList<BufferedImage> imageSetCopy;

    private int setType;

    //Personally I would just prefer to have a static array that loads when the game does, rather than adding an unnecessary switch statement:
    public SpriteLoader (int setType) {

        imageSetCopy = new ArrayList<>();
        this.setType = setType;

        switch (this.setType) {
            case 1:
                generateBackgroundSet();
                break;
            case 2:
                generateEnemySet();
                break;
            case 3:
                generateMenuSet();
                break;
        }
    }

    public void generateBackgroundSet()
    {
        imageSetCopy.add(loadImage("../Base/Resources/groundGrass.png"));
        imageSetCopy.add(loadImage("../Base/Resources/groundSidewalk.png"));
    }

    public void generateEnemySet()
    {
        imageSetCopy.add(loadImage("../Base/Resources/ANGRY.png"));
    }

    public void generateMenuSet()
    {
        imageSetCopy.add(loadImage("../Base/Resources/mainMenuScreen.png"));
    }

    public int size () {return imageSetCopy.size();}

    public BufferedImage returnImageFromSet (int index)
    {
        //Moved the %= code to overworld panel. If another class tries to access an image that doesn't exist, we want that access attempt to fail
        //If you don't like doing it this way, maybe make a function "unsafeReturnImageFromSet" or print out "modified index to fit needs" or something
        //The custom exception thing was a nice idea, but a catch-all block directly surrounding a try block cancels out any useful error-reporting
        return imageSetCopy.get(index);
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
