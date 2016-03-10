package GUI;

import Base.SpriteLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class GUI implements MouseListener
{
    //I'm not even going to ask


    private ArrayList<GUIElement> elements;
    private SpriteLoader spriteLoader;
    private double scalar;

    public GUI(double scalar)
    {
        elements = new ArrayList<>();
        spriteLoader = new SpriteLoader();
        this.scalar = scalar;
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
        double mouseX = me.getX() / scalar;
        double mouseY = me.getY() / scalar;

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
