
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;


public class SpriteLoader {

    private ArrayList<BufferedImage> imageSetCopy;

    private int setType;

    protected SpriteLoader (int setType) {

        imageSetCopy = new ArrayList<>();
        this.setType = setType;

        switch (this.setType) {
            case 1:
                generateBackgroundSet();
                break;
            case 2:
                generateEnemySet();
                break;
        }
    }

    public void generateBackgroundSet()
    {
        imageSetCopy.add(loadImage("main/resources/groundGrass256.png"));
        imageSetCopy.add(loadImage("main/resources/groundSidewalk256.png"));
    }

    public void generateEnemySet()
    {
        imageSetCopy.add(loadImage("main/resources/ANGRY.png"));
    }

    public int size()
    {
        return imageSetCopy.size();
    }

    protected BufferedImage returnImageFromSet (int index)
    {
        if (index >= imageSetCopy.size())
        {
            throw new IllegalArgumentException("Image with ID: "              + Integer.toString(index) +
                                               " Does not exist in the set: " + Integer.toString(setType));
        }
        return imageSetCopy.get(index);
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
