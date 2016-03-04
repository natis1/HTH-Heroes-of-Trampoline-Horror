package GUI;
import Base.Sprite;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Button extends GUIElement
{
    private String text;

    public Button(BufferedImage image)
    {
        super(image);
    }

    public Button(int x, int y, BufferedImage image)
    {
        super(x, y, image);
    }

    public void setText(String s){text = s;}

    @Override
    public void draw(Graphics2D graphics, JPanel panel)
    {
        graphics.drawImage(getImage(), getX(), getY(), panel);
        graphics.drawString(text, getX(), getY());

        drawBackground(graphics, panel);
    }
}
