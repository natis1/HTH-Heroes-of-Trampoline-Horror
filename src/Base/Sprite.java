package Base;

import javax.imageio.ImageIO;
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
    protected boolean keepLoaded;
    //Image of the sprite
    protected BufferedImage image;
    //velocity of the sprite
    protected double dx=0;
    protected double dy=0;
    //Angle to draw the image at.
    protected double angle = 0.0;
    //Boolean indicating if the sprite is within the edges of the screen.
    protected boolean stay_in_bounds = false;
    //Reference to the ship image file
    protected String image_file;
    //Detect collisions in a circle around the sprite.
    protected double collision_radius=0;


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

    public Sprite(int x, int y, double angle, BufferedImage image) {
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

    public Rectangle getBoundingRectangle() {
        return new Rectangle(x, y, width, height);
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

    public int getXCenter() {
        return x + width /2;
    }

    public int getYCenter() {
        return y + height /2;
    }

    public int[] getCenter() {
        return new int[]{x + width /2, y + height /2};
    }

    //Center the sprite on the given x and y coordinates.
    public void moveToCenter(int x, int y){
        this.x = x - width/2;
        this.y = y - height/2;
    }

    public boolean isVisible() {
        return vis;
    }

    public void setVisible(Boolean visible) {
        vis = visible;
    }



    public void changeVelocity(double change_dx, double change_dy){
        dx += change_dx;
        dy += change_dy;
    }

    public void setVelocity(double new_dx, double new_dy){
        dx = new_dx;
        dy = new_dy;
    }

    public void changeAngle(double angle){
        //Remember the current center
        int[] center = getCenter();
        //Rotate the angle by pi/16
        this.angle += angle;
        //Reload the image.
        loadImage();
        //Update the image dimensions.
        getImageDimensions();
        //Move image to the old center to prevent rotation wobble.
        moveToCenter(center[0], center[1]);
    }

    public void setAngle(double angle){
        //Remember the current center
        int[] center = getCenter();
        //Rotate the angle by pi/16
        this.angle = angle;
        //Reload the image.
        loadImage();
        //Update the image dimensions.
        getImageDimensions();
        //Move image to the old center to prevent rotation wobble.
        moveToCenter(center[0], center[1]);
    }

    //Check circular collision
    /*public boolean CollidedRadius(Base.Sprite other){
        int[] my_center = this.getCenter();
        int[] other_center = other.getCenter();
        double distance = Math.sqrt((my_center[0]-other_center[0])^2 - (my_center[1]-other_center[1])^2);
        return distance < (this.collision_radius + other.collision_radius);
    }

    //Check rectangular collision
    public boolean CollidedRectangle(Base.Sprite other){
        //Get the rectangle around the sprite.
        Rectangle my_rect = this.getBoundingRectangle();
        Rectangle other_rect = other.getBoundingRectangle();
        return my_rect.intersects(other_rect);
    }*/
}