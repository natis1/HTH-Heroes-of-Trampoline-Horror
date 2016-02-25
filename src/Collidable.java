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

    /*
    public void move() {
        x += dx;
        y += dy;
        //Keep certain sprites in bounds.
        if(stay_in_bounds && !InBounds()){
            StayInBounds();
        }
    }

    //Detect if the sprite is in bounds
    //public boolean InBounds() {
        if(x < 0){
            return false;
        }
        if(y < 0){
            return false;
        }
        if(x+width > MovingSpriteEx.ScreenX){
            return false;
        }
        if(y+height > MovingSpriteEx.ScreenY){
            return false;
        }
        return true;
    }*/

    //Keep the sprite from straying out of bounds with this function
    /*public void StayInBounds(){
        if(x < 0){//left wall
            x=0;
        }
        else if(x+width > MovingSpriteEx.ScreenX){//right wall
            x= MovingSpriteEx.ScreenX - width;
        }
        if(y < 0){//top wall
            y=0;
        }
        else if(y+height > MovingSpriteEx.ScreenY){//bottom wall
            y= MovingSpriteEx.ScreenY - height;
        }
    }*/

    //Check circular collision
    /*public boolean CollidedRadius(Sprite other){
        int[] my_center = this.getCenter();
        int[] other_center = other.getCenter();
        double distance = Math.sqrt((my_center[0]-other_center[0])^2 - (my_center[1]-other_center[1])^2);
        return distance < (this.collision_radius + other.collision_radius);
    }

    //Check rectangular collision
    public boolean CollidedRectangle(Sprite other){
        //Get the rectangle around the sprite.
        Rectangle my_rect = this.getBoundingRectangle();
        Rectangle other_rect = other.getBoundingRectangle();
        return my_rect.intersects(other_rect);
    }*/

}
