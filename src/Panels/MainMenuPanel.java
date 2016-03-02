package Panels;

import Base.Main;
import Base.Sprite;
import Base.SpriteLoader;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class MainMenuPanel extends BasePanel implements MouseListener
{
    private SpriteLoader spriteLoader;
    private Sprite backgroundSprite;

    public MainMenuPanel(double scalar, int monitorHZ)
    {
        super(scalar, monitorHZ);
        spriteLoader = new SpriteLoader(3);
        addMouseListener(this);

        backgroundSprite = new Sprite(0, 0, 0, spriteLoader.returnImageFromSet(0));

        runLoop();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);

        Toolkit.getDefaultToolkit().sync();
    }

/** This function draws all of the sprites using graphics2D libraries
All drawing must be called from the board
You cannot call doDrawing from other classes, to add a sprite to
the drawing queue, create the class inside the board.*/

    protected void doDrawing(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        g2d.scale(universalScalar, universalScalar);

        //Draw stuff here
        g2d.drawImage(backgroundSprite.getImage(), backgroundSprite.getX(),
                backgroundSprite.getY(), this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent me) {

        double mouseX = me.getX() / universalScalar;
        double mouseY = me.getY() / universalScalar;


        System.out.println(mouseX);
        System.out.println(mouseY);

        if (mouseX > 1030 && mouseY < 316){

            //1 = start windows game new.
            Main.ElvenGameState = 2;

            //TODO ADD SOMETHING HERE
        } else if (mouseX > 1030 && mouseY > 600){
            System.exit(1);   // Manually caused exit
        }
        else //Just for testing Panels.BattlePanel
        {
            Main.ElvenGameState = 3;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    private class TAdapter extends KeyAdapter {
        //TODO KEYBOARD SUPPORT
    }
}