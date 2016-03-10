package Save_System;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;



public class RandomImageGenerator {

    //BIOME SIZE Large for demo

    //Notice similar biomes appear near each other
    private static int FEATURE_SIZE = 16;
    public BufferedImage nextRandomImage;


    public RandomImageGenerator(String saveLocation, int width, int height) {



        OpenSimplexNoise noiseRed = new OpenSimplexNoise();
        OpenSimplexNoise noiseGreen = new OpenSimplexNoise();
        OpenSimplexNoise noiseBlue = new OpenSimplexNoise();


        nextRandomImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);


        for (int y = 0; y < height; y++)
        {

            for (int x = 0; x < width; x++)
            {
                double valueR = noiseRed.eval(x / FEATURE_SIZE, y / FEATURE_SIZE, 0.0);
                double valueG = noiseGreen.eval(x / FEATURE_SIZE, y / FEATURE_SIZE, 0.0);
                double valueB = noiseBlue.eval(x / FEATURE_SIZE, y / FEATURE_SIZE, 0.0);
                int rgb = 0x010000 * (int)((valueR + 1) * 127.5);
                rgb += 0x000100 * (int)((valueG + 1) * 127.5);
                rgb += (int)((valueB + 1) * 127.5);

                nextRandomImage.setRGB(x, y, rgb);
            }

        }


        File imageWriteLocation = new File(saveLocation);
        try {
            ImageIO.write(nextRandomImage, "png", imageWriteLocation);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public RandomImageGenerator(int width, int height) {

        nextRandomImage = generateBufferedImage(width, height);
    }


    private BufferedImage generateBufferedImage(int width, int height) {

        OpenSimplexNoise noiseRed = new OpenSimplexNoise();
        OpenSimplexNoise noiseGreen = new OpenSimplexNoise();
        OpenSimplexNoise noiseBlue = new OpenSimplexNoise();


        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);


        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                double valueR = noiseRed.eval(x / FEATURE_SIZE, y / FEATURE_SIZE, 0.0);
                double valueG = noiseGreen.eval(x / FEATURE_SIZE, y / FEATURE_SIZE, 0.0);
                double valueB = noiseBlue.eval(x / FEATURE_SIZE, y / FEATURE_SIZE, 0.0);
                int rgb = 0x010000 * (int)((valueR + 1) * 127.5);

                //This has been commented to show that the generation is NOT random but follows a pattern

                //rgb += 0x000100 * (int)((valueG + 1) * 127.5);
                //rgb += (int)((valueB + 1) * 127.5);

                image.setRGB(x, y, rgb);
            }
        }


        return image;
    }






}
