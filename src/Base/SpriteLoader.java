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

    public BufferedImage returnImageFromSet (int index) {
        index %= imageSetCopy.size();
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
