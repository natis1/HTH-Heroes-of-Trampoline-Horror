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
import Base.Weapon;
import Base.WeaponStats;


public class BattlePanel extends BasePanel implements MouseListener
{
    private SpriteLoader battleLoader;
    private KeyboardManager keyboardManager;

    private Sprite backgroundSprite;
    private Sprite foregroundSprite;

    private ArrayList<Weapon> weapons    = new ArrayList<>();
    private ArrayList<Sprite> characters = new ArrayList<>(); //Should be full of "Character" ?

    private JLabel test;

    public BattlePanel(double scalar, int monitorHZ)
    {
        super(scalar, monitorHZ);

        keyboardManager = new KeyboardManager();
        battleLoader    = new SpriteLoader();

        addMouseListener(this);
        addKeyListener(new TAdapter());
        setFocusable(true);

        this.setBackground(Color.WHITE);

        foregroundSprite = new Sprite(0, 0, 0, deepCopy(battleLoader.returnImageFromSet("battleScreen")));
        backgroundSprite = new Sprite(0, 0, 0, deepCopy(battleLoader.returnImageFromSet("battleground")));

        weapons.add(new Weapon(100, 100, deepCopy(battleLoader.returnImageFromSet("sword")), new WeaponStats(10, 10)));

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


        g2d.drawImage(foregroundSprite.getImage(), foregroundSprite.getX(),
                foregroundSprite.getY(), this);

        for (Weapon w : weapons)
        {
            w.draw(g2d, this);
        }

        g2d.setPaint(Color.decode("#527A7A"));
        g2d.setFont(new Font(null, Font.BOLD, 20));
        g2d.drawString("Test" , 50, 50);

        //Graphics2D;
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        int mouseX = (int) (e.getX() / universalScalar);
        int mouseY = (int) (e.getY() / universalScalar);

        for (Weapon w : weapons)
        {
            if (w.contains(mouseX, mouseY))
            {
                w.use();
            }
        }
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