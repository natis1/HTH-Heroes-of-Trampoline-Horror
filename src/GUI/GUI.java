package GUI;

import Base.SpriteLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public class GUI implements MouseListener
{
    private Vector<GUIElement> elements;
    private SpriteLoader spriteLoader;

    public GUI()
    {
        elements = new Vector<>();
        spriteLoader = new SpriteLoader();
    }

    public void add(GUIElement element)
    {
        elements.add(element);
    }

    public void draw(Graphics2D graphics, JPanel panel)
    {
        for(GUIElement g : elements)
        {
            g.draw(graphics, panel);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
    }

    @Override
    public void mouseReleased(MouseEvent me)
    {
        double mouseX = me.getX();
        double mouseY = me.getY();

        System.out.println(mouseX);
        System.out.println(mouseY);
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
    }
}
