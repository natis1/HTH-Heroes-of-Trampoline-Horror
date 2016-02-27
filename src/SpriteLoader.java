
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;


public class SpriteLoader {

    private ArrayList<BufferedImage> imageSetCopy;

    private int setType;

    protected SpriteLoader (int setType) {

        imageSetCopy = new ArrayList<BufferedImage>();
        this.setType = setType;
        //Does this still need to be here? Why? -Lucas



        switch (this.setType) {
            case 1:
                generateBackgroundSet();
                break;
            case 2:



        }




    }

    public void generateBackgroundSet(){



        imageSetCopy.add(loadImage("main/resources/groundGrass128.png"));
        imageSetCopy.add(loadImage("main/resources/groundSidewalk128.png"));



    }
    public void generateEnemySet(){

        imageSetCopy.add(loadImage("main/resources/ANGRY.png"));


    }


    protected BufferedImage returnImageFromSet (int index){


        //This ensures you never take an image that does not exist. A special method might be ideal for background sets but whateves.
        index %= imageSetCopy.size();
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
