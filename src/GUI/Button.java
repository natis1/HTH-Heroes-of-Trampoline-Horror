package GUI;
import Base.Sprite;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Button extends GUIElement
{
    private String text;

    public Button(BufferedImage image, String s)
    {
        super(0, 0, image);
        text = s;
    }

    public void setText(String s){text = s;}
    public final String getText(){return text;}

    public void draw(Graphics2D graphics, JPanel panel)
    {
        drawBackground(graphics, panel);
        graphics.drawImage(getImage(), getX(), getY(), panel);
        graphics.drawString(text, getX(), getY());
    }
}
