import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;



public class RandomImageGenerator {

    private static int FEATURE_SIZE = 16;


    public void RandomImageGenerator(String saveLocation, int height, int width) {



        OpenSimplexNoise noise = new OpenSimplexNoise();
        BufferedImage image = new BufferedImage(height, width, BufferedImage.TYPE_INT_RGB);


        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                double value = noise.eval(x / FEATURE_SIZE, y / FEATURE_SIZE, 0.0);
                int rgb = 0x010101 * (int)((value + 1) * 127.5);
                image.setRGB(x, y, rgb);
            }
        }


        File imageWriteLocation = new File(saveLocation);
        try {
            ImageIO.write(image, "png", imageWriteLocation);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public BufferedImage RandomImageGenerator(int height, int width) {

        return generateBufferedImage(height, width);
    }


    private BufferedImage generateBufferedImage(int height, int width) {

        OpenSimplexNoise noise = new OpenSimplexNoise();
        BufferedImage image = new BufferedImage(height, width, BufferedImage.TYPE_INT_RGB);


        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                double value = noise.eval(x / FEATURE_SIZE, y / FEATURE_SIZE, 0.0);
                int rgb = 0x010101 * (int)((value + 1) * 127.5);
                image.setRGB(x, y, rgb);
            }
        }


        return image;
    }






}
