package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class TextBox extends GUIElement{

    private ArrayList<GUIText> contents;

    private int max_size;

    private final int offset = 30;

    public TextBox(BufferedImage image, int setMaxSize)
    {
        super(image);
        contents = new ArrayList<>();
        max_size = setMaxSize;
    }

    public TextBox(int x, int y, BufferedImage image, int setMaxSize)
    {
        super(x, y, image);
        contents = new ArrayList<>();
        max_size = setMaxSize;
    }

    public void add(GUIText t)
    {
        if(contents.size() < max_size)
        {
            if(contents.size() > 0)
            {
                t.x = contents.get(contents.size() - 1).x;
                t.y = contents.get(contents.size() - 1).y;
            }
            else
            {
                t.x = this.x + offset;
                t.y = this.y;
            }

            t.y += offset;
            contents.add(t);
        }
        else
        {
            this.clear();

            //oh my god
            this.add(t);
        }
    }

    public void clear(){contents.clear();}

    //Method doesn't override anything
    public void draw(Graphics2D graphics, JPanel panel)
    {
        drawBackground(graphics, panel);
        for(GUIText t : contents)
        {
            graphics.drawString(t.field, t.x, t.y);
        }
    }
}
