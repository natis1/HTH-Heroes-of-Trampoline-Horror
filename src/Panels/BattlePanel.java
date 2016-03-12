package Panels;

import Base.*;
import Base.Character;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class BattlePanel extends BasePanel implements MouseListener
{
    private SpriteLoader battleLoader;
    private KeyboardManager keyboardManager;

    private Sprite backgroundSprite;
    private Sprite foregroundSprite;

    private ArrayList<Weapon> weapons    = new ArrayList<>();
    private ArrayList<Character> enemies = new ArrayList<>();
    private Base.Character player;

    public BattlePanel(double scalar, int monitorHZ)
    {
        super(scalar, monitorHZ);

        //For initializing properties, while init is for initializing data

        this.setBackground(Color.WHITE);

        addMouseListener(this);
        addKeyListener(new TAdapter());
        setFocusable(true);

        init(); //Data!

        runLoop();
    }

    private void init()
    {
        //For creating new objects!

        keyboardManager = new KeyboardManager();
        battleLoader    = new SpriteLoader();

        foregroundSprite = new Sprite(0, 0, 0, deepCopy(battleLoader.returnImageFromSet("battleScreen")));
        backgroundSprite = new Sprite(0, 0, 0, deepCopy(battleLoader.returnImageFromSet("battleground")));

        weapons.add(new Weapon(100, 100, deepCopy(battleLoader.returnImageFromSet("sword")), new WeaponStats(10, 10)));

        player = new Base.Character(300, 0, deepCopy(battleLoader.returnImageFromSet("angry")), new CharacterStats("Tom", 20));
        enemies.add(new Base.Character(0, 100, deepCopy(battleLoader.returnImageFromSet("angry")), new CharacterStats("Tom", 20)));
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

        backgroundSprite.draw(g2d, this);
        foregroundSprite.draw(g2d, this);


        player.draw(g2d, this);
        enemies.removeIf(Character::isDead); //Look at this beauty

        for (Character c : enemies)
        {
            c.draw(g2d, this);
        }

        for (Weapon w : weapons)
        {
            if (!w.hasDurability()) //Its possible to move this elsewhere once we make changes to the way that weapons work.
            {
                weapons.remove(w);
                break;
            }
            w.draw(g2d, this);
        }

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
                for (Character c: enemies)
                {
                    c.applyDamage(w.use());
                }
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