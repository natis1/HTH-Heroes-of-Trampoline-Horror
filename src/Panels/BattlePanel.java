package Panels;

import Base.KeyboardManager;
import Base.Sprite;
import Base.SpriteLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;


public class BattlePanel extends BasePanel implements MouseListener
{
    private SpriteLoader battleLoader;
    private KeyboardManager keyboardManager;

    private Sprite backgroundSprite;

    private ArrayList<Sprite> weapons    = new ArrayList<>(); //Should be eventually full of a class "Weapon"?
    private ArrayList<Sprite> characters = new ArrayList<>(); //Should be full of "Character" ?

    private JLabel test;

    public BattlePanel(double scalar, int monitorHZ)
    {
        super(scalar, monitorHZ);

        keyboardManager = new KeyboardManager();
        battleLoader    = new SpriteLoader(2);

        addMouseListener(this);
        addKeyListener(new TAdapter());
        setFocusable(true);

        this.setBackground(Color.BLACK);


        test = new JLabel("Test");

        test.setText("Label Text");

        backgroundSprite = new Sprite(0, 0, 0, deepCopy(battleLoader.returnImageFromSet(0)));

        runLoop();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);

        Toolkit.getDefaultToolkit().sync();
    }

    protected void doDrawing(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        g2d.scale(universalScalar, universalScalar);

        g2d.drawImage(backgroundSprite.getImage(), backgroundSprite.getX(),
                backgroundSprite.getY(), this);

        g2d.setPaint(Color.decode("#527A7A"));
        g2d.setFont(new Font(null, Font.BOLD, 20));
        g2d.drawString("Test" , 50, 50);

        //Graphics2D;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent me) {

    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
    }

    private class TAdapter extends KeyAdapter
    {
        //TODO KEYBOARD SUPPORT
    }
}