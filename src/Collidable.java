import java.awt.*;

public class Collidable
{
    //Coordinates
    protected int x;
    protected int y;
    protected double dx = 0;
    protected double dy = 0;
    //Dimensions
    protected int width;
    protected int height;

    //Boolean indicating if the sprite is within the edges of the screen.
    protected boolean stay_in_bounds = false;
    protected double collision_radius = 0;

    //Constructor
    public Collidable(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public Rectangle getBoundingRectangle() {
        return new Rectangle(x, y, width, height);
    }

    public void setSize(int width, int height)
    {
        this.width  = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getXCenter() {
        return x + width / 2;
    }

    public int getYCenter() {
        return y + height / 2;
    }

    public int[] getCenter() {
        return new int[]{x + width / 2, y + height / 2};
    }

    //Center the sprite on the given x and y coordinates.
    public void moveToCenter(int x, int y) {
        this.x = x - width / 2;
        this.y = y - height / 2;
    }

    public void changeVelocity(double change_dx, double change_dy) {
        dx += change_dx;
        dy += change_dy;
    }

    public void setVelocity(double new_dx, double new_dy) {
        dx = new_dx;
        dy = new_dy;
    }

}
