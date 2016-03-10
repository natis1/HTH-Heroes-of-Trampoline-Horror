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
    
    protected void drawBackground(Graphics2D graphics, JPanel panel)
    {
        graphics.drawImage(image, super.getX(), super.getY(), panel);
    }
}
