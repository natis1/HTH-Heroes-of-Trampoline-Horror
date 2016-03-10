package Base;

import Panels.BasePanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.IOException;

public class Sprite {

    //Coordinates
    protected int x;
    protected int y;
    //Dimensions
    protected int width;
    protected int height;
    //Whether or not the sprite is visible
    protected boolean vis;
    //Image of the sprite
    protected BufferedImage image;
    //Angle to draw the image at.
    protected double angle = 0.0;
    //Reference to the ship image file
    protected String image_file;


    private boolean preloaded = false;

    //Constructor
    public Sprite(int x, int y, double angle, String image_file) {
        preloaded = false;

        this.x = x;
        this.y = y;
        this.angle = angle;
        this.image_file = image_file;

        loadImage();



        vis = true;
    }

    public Sprite(int x, int y, double angle, BufferedImage image)
    {
        preloaded = true;

        this.x = x;
        this.y = y;

        this.angle = angle;
        this.image = createTransformedImage(image, angle);


    }


    //Load a buffered image
    public void loadImage(){

        if (!preloaded){
            BufferedImage img = null;
            try {
                img = ImageIO.read(getClass().getResource(image_file));
            } catch (IOException e) {
                System.out.println("Error loading image.");
                e.printStackTrace();
            }
            this.image = createTransformedImage(img, angle);
        } else {
            //You shouldn't call me

            StackTraceElement[] e = new Throwable().getStackTrace();
            for (int f = e.length - 1; f > 0; f--){
                System.out.println(e[f]);
            }
        }

    }

    //Load a rotated buffered image
    public BufferedImage createTransformedImage(BufferedImage image, double angle) {
        double sin = Math.abs(Math.sin(angle)), cos = Math.abs(Math.cos(angle));
        int w = image.getWidth(), h = image.getHeight();
        int neww = (int) Math.floor(w * cos + h * sin), newh = (int) Math.floor(h * cos + w * sin);
        BufferedImage result = new BufferedImage(neww, newh, Transparency.TRANSLUCENT);
        Graphics2D g2d = result.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.translate((neww - w) / 2, (newh - h) / 2);
        g2d.rotate(angle, w / 2, h / 2);
        g2d.drawRenderedImage(image, null);
        g2d.dispose();
        return result;
    }

    //Set / update image dimensions
    protected void getImageDimensions() {
        width = image.getWidth(null);
        height = image.getHeight(null);
    }

    public BufferedImage getImage() {
        return image;
    }

    public void draw(Graphics2D graphics, JPanel panel)
    {
        graphics.drawImage(image, x, y, panel);
    }

    public boolean contains(int x, int y)
    {
        return            ((x < this.x + getWidth())
                        && (x > this.x)
                        && (y < this.y + getHeight())
                        && (y > this.y));
    }

    public int getWidth() {
        return image.getWidth();
    }

    public int getHeight() {
        return image.getHeight();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}