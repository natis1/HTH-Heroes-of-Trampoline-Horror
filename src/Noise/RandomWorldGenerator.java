package Noise;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by scc on 2/16/2016.
 */
public class RandomWorldGenerator {

    private static int CHUNK_SIZE = 1;

    //Biome size is measured in chunks
    private static int AVERAGE_BIOME_SIZE = 64;


    private int xLoc;
    private int yLoc;

    public RandomWorldGenerator (int xLocation, int yLocation) {

        this.xLoc = xLocation;
        this.yLoc = yLocation;



        OpenSimplexNoise chunkGenerator = new OpenSimplexNoise();

        OpenSimplexNoise biomeGenerator = new OpenSimplexNoise();

        generateChunkData(chunkGenerator, biomeGenerator);

    }

    public RandomWorldGenerator (long seed, int xLocation, int yLocation) {

        this.xLoc = xLocation;
        this.yLoc = yLocation;

        OpenSimplexNoise chunkGenerator = new OpenSimplexNoise(seed);
        OpenSimplexNoise biomeGenerator = new OpenSimplexNoise(seed + 1);

        generateChunkData(chunkGenerator, biomeGenerator);


    }


    private void generateChunkData (OpenSimplexNoise chunkGenerator, OpenSimplexNoise biomeGenerator){




        //temporary storage method
        BufferedImage worldData = new BufferedImage(1024, 1024, BufferedImage.TYPE_INT_RGB);


        for (int x = -512; x < 512; x++){
            for (int y = -512; y < 512; y++){
                double chunkData = chunkGenerator.eval(x + (xLoc * 1024), y + (yLoc * 1024), 0.0);
                double biomeData = biomeGenerator.eval(x / (CHUNK_SIZE * AVERAGE_BIOME_SIZE), y / (CHUNK_SIZE * AVERAGE_BIOME_SIZE), 0.0);


                int r = ((int)((chunkData + 1) * 127.5) % 64);

                int rgb = 0x010000 * r;
                rgb += (int)((biomeData + 1) * 127.5);

                worldData.setRGB(x + 512, y + 512, rgb);

            }
        }



        File imageWriteLocation = new File("world" + xLoc + " " + yLoc + ".png");
        try {
            ImageIO.write(worldData, "png", imageWriteLocation);
        } catch (IOException e) {
            e.printStackTrace();
        }




    }



}
