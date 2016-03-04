package GUI;

import Base.Sprite;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

abstract public class GUIElement extends Sprite{

    public GUIElement(BufferedImage image)
    {
        super(0, 0, 0, image); //Ignore angles on GUI elements
    }

    public GUIElement(int x, int y, BufferedImage image)
    {
        super(x, y, 0, image); //Ignore angles on GUI elements
    }

    public boolean contains(int x, int y)
    {
        boolean clickedOn =
                ((x < this.x + getWidth())
                && (x > this.x)
                && (y < this.y + getHeight())
                && (y > this.y));
        if(clickedOn) System.out.println("Clicked on GUI element");
        return clickedOn;
    }

    abstract public void draw(Graphics2D graphics, JPanel panel); //Each GUI element will implement its own draw method
    
    protected void drawBackground(Graphics2D graphics, JPanel panel)
    {
        graphics.drawImage(image, super.getX(), super.getY(), panel);
    }
}
