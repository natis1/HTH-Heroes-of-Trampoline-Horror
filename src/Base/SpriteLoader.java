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
        imageSetCopy.add(loadImage("Resources/groundGrass.png"));
        imageSetCopy.add(loadImage("Resources/groundSidewalk.png"));
    }

    public void generateEnemySet()
    {
        imageSetCopy.add(loadImage("Resources/ANGRY.png"));
    }

    public void generateMenuSet()
    {
        imageSetCopy.add(loadImage("Resources/mainMenuScreen.png"));
    }

    public int size()
    {
        return imageSetCopy.size();
    }

    public BufferedImage returnImageFromSet (int index)
    {
        if (index >= imageSetCopy.size())
        {
            throw new IllegalArgumentException("Image with ID: "              + Integer.toString(index) +
                                               " Does not exist in the set: " + Integer.toString(setType));
        }
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
