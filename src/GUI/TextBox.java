package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class TextBox extends GUIElement{

    private ArrayList<Text> contents;

    public TextBox(BufferedImage image)
    {
        super(image);
        contents = new ArrayList<>();
    }

    public TextBox(int x, int y, BufferedImage image)
    {
        super(x, y, image);
        contents = new ArrayList<>();
    }

    public void add(Text t){contents.add(t);}
    public void clear(){contents.clear();}

    @Override
    public void draw(Graphics2D graphics, JPanel panel)
    {
        for(Text t : contents)
        {
            graphics.drawString(t.field, t.x, t.y);
        }
        drawBackground(graphics, panel);
    }
}
