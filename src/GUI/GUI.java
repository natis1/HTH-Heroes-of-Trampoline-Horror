package GUI;

import Base.SpriteLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public class GUI
{
    //I'm not even going to ask


    public Vector<GUIElement> elements;
    private SpriteLoader spriteLoader;

    public GUI() {
        elements = new Vector<>();
        spriteLoader = new SpriteLoader();
    }

    public void add(GUIElement element) {
        elements.add(element);
    }


    //what is happening here?
    public void draw(Graphics2D graphics, JPanel panel) {
        for(GUIElement g : elements)
        {
            g.drawBackground(graphics, panel);
        }
    }


}
