import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by scc on 2/16/2016.
 */
public class RandomWorldGenerator {

    private static int CHUNK_SIZE = 8;

    //Biome size is measured in chunks
    private static int AVERAGE_BIOME_SIZE = 10;

    public RandomWorldGenerator () {

        OpenSimplexNoise chunkGenerator = new OpenSimplexNoise();

        OpenSimplexNoise biomeGenerator = new OpenSimplexNoise();

        generateChunkData(chunkGenerator, biomeGenerator);

    }

    public RandomWorldGenerator (long seed) {

        OpenSimplexNoise chunkGenerator = new OpenSimplexNoise(seed);
        OpenSimplexNoise biomeGenerator = new OpenSimplexNoise(seed + 1);

        generateChunkData(chunkGenerator, biomeGenerator);


    }


    private void generateChunkData (OpenSimplexNoise chunkGenerator, OpenSimplexNoise biomeGenerator){




        //temporary storage method
        BufferedImage worldData = new BufferedImage(512, 512, BufferedImage.TYPE_INT_RGB);


        for (int x = -256; x < 256; x++){
            for (int y = -256; y < 256; y++){
                double chunkData = chunkGenerator.eval(x / CHUNK_SIZE, y / CHUNK_SIZE, 0.0);
                double biomeData = biomeGenerator.eval(x / (CHUNK_SIZE * AVERAGE_BIOME_SIZE), y / (CHUNK_SIZE * AVERAGE_BIOME_SIZE), 0.0);



                int rgb = 0x010000 * (int)((chunkData + 1) * 127.5);
                rgb += 0x000100 * (int)((biomeData + 1) * 127.5);

                worldData.setRGB(x + 256, y + 256, rgb);

            }
        }



        File imageWriteLocation = new File("world.png");
        try {
            ImageIO.write(worldData, "png", imageWriteLocation);
        } catch (IOException e) {
            e.printStackTrace();
        }




    }



}
