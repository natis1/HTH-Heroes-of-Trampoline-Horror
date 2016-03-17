package GUI;

import Base.Sprite;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class GUIElement extends Sprite{

    public GUIElement(BufferedImage image)
    {
        super(0, 0, 0, image); //Ignore angles on GUI elements
    }

    public GUIElement(int x, int y, BufferedImage image)
    {
        super(x, y, 0, image); //Ignore angles on GUI elements
    }

    //drawBackground and draw have the exact same signature, but they'll be overriden in derived classes :)

    protected void drawBackground(Graphics2D graphics, ImageObserver observer)
    {
        graphics.drawImage(image, super.getX(), super.getY(), observer);
    }

    public void draw(Graphics2D graphics, ImageObserver observer)
    {
        graphics.drawImage(image, getX(), getY(), observer);
    }
}
