package GUI;

import Base.SpriteLoader;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class GUI implements MouseListener
{
    private ArrayList<Button> buttons;
    private SpriteLoader spriteLoader;
    private double scalar;

    public GUI(double scalar)
    {
        buttons = new ArrayList<>();
        spriteLoader = new SpriteLoader(3);
        this.scalar = scalar;

        buttons.add(new Button(0,0,0, spriteLoader.returnImageFromSet(0)));
    }

    public boolean handler(int x, int y)
    {
        for (Button b : buttons)
        {
            if (b.contains(x, y))
            {
                return true;
            }
        }
        return false;
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
