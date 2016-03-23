package Save_System;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import Base.Pair;

import javax.imageio.ImageIO;

public class WorldGenerator
{
    private BufferedImage world;

    public WorldGenerator(int width, int height)
    {
        world = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);


        //Arguments for getColorLandScape
        //int weight_min, int weight_max, int freq_min,int freq_max, int array_length
        RGBTuple level = getColorLandscape(1,3, 2,15, 8);

        genMap(level);

        File imageWriteLocation = new File(System.getProperty("user.dir") + "/src/Base/Resources/test.png");
        try {
            ImageIO.write(world, "png", imageWriteLocation);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int randRange(int min, int max){return min + (int)(Math.random() * ((max - min) + 1));}

    private int[] randArray(int min, int max, int length)
    {
        int[] array = new int[length];
        for(int i=0; i < length; i++)
        {
            array[i] = randRange(min, max);
        }
        return array;
    }

    private RGBTuple
    getColorLandscape(int weight_min, int weight_max, int freq_min,int freq_max, int array_length)
    {
        Pair<int[], int[]> reds = new Pair<>(
                randArray(weight_min,weight_max,array_length),
                randArray(freq_min,freq_max,array_length));

        Pair<int[], int[]> greens = new Pair<>(
                randArray(weight_min,weight_max,array_length),
                randArray(freq_min,freq_max,array_length));

        Pair<int[], int[]> blues = new Pair<>(
                randArray(weight_min,weight_max,array_length),
                randArray(freq_min,freq_max,array_length));

        return new RGBTuple(
                reds,
                greens,
                blues);
    }

    private int sine1(int x, int y, int shift, int[] weights, int[] frequencies) {
        int upshift = 0; //Use this to keep the result positive
        int inputx = x + shift;
        int inputy = y + shift;
        double output = 0;
        double input = 0;

        //Use switcher1 to oscillate between use of x, y, and x+y inputs.
        int switcher1 = 0;
        //Use switcher2 to oscillate between 1 and 2 applications of sin function.
        int switcher2 = 0;

        for (int i = 0; i < weights.length; i++) {
            if (switcher1 == 0) {
                input = inputx;
                switcher1 = 1;
            } else if (switcher1 == 1) {
                input = inputy;
                switcher1 = 2;
            } else {
                input = inputx + inputy;
                switcher1 = 0;
            }
            if (switcher2 == 0) {
                output += weights[i] * Math.sin(input / frequencies[i]);
                switcher2 = 1;
            } else {
                output += weights[i] * Math.sin(Math.sin(input / frequencies[i]));
                switcher2 = 0;
            }
            upshift += weights[i];
        }

        //Make the output value between 0 and 1.
        output = (output + upshift) / (2 * upshift);
        //Multiply by 255 to make it a color.
        return (int) Math.floor(255.0 * output);
    }

    private void genMap(Triple<Pair<int[], int[]>, Pair<int[], int[]>, Pair<int[], int[]>> level)
    {
        int red, green, blue;

        for(int y=0; y < world.getHeight(); y++)
        {
            for(int x=0; x < world.getWidth(); x++)
            {
                red   = sine1(x, y, 7,  level.getLeft(  ).getLeft(), level.getLeft(  ).getRight());
                green = sine1(x, y, 19, level.getMiddle().getLeft(), level.getMiddle().getRight());
                blue  = sine1(x, y, 0,  level.getRight( ).getLeft(), level.getRight( ).getRight());

                //Dominant color wins. Everything else gets set to zero.
                if(red > green){
                    green = 0;
                    if(red > blue){
                        blue = 0;
                    }else{
                        red = 0;
                    }
                }else{
                    red = 0;
                    if(green > blue){
                        blue = 0;
                    }else{
                        green = 0;
                    }
                }


                Color pixel = new Color(red, green, blue); //Easy to understand method of setting rgb. (Don't do it manually)
                world.setRGB(x, y, pixel.getRGB());
            }
        }
    }
}
