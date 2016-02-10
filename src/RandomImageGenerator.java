import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class RandomImageGenerator
{
    public void RandomImageGenerator(String saveLocation, int height, int width) {

        BufferedImage imageToWrite = generateBufferedImage(height, width);

        File imageWriteLocation = new File(saveLocation);
        try {
            ImageIO.write(imageToWrite, "png", imageWriteLocation);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage RandomImageGenerator(int height, int width)
    {
        return generateBufferedImage(height, width);
    }

    private BufferedImage generateBufferedImage(int height, int width) {
        BufferedImage img = null;

        for (int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){
                int a = 255;
                int r = (int) (Math.random() * 256);
                int g = (int) (Math.random() * 256);
                int b = (int) (Math.random() * 256);

                int pixel = (a << 24) | (r << 16) | (g << 8) | b; //a * 24, r * 16, g * 8, b. BITMAP FORMAT


                img.setRGB(x, y, pixel);
            }
        }
        return img;
    }
}
