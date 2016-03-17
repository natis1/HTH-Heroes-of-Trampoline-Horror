package Panels;

import Base.*;
import Base.Character;
import GUI.*;
import GUI.Menu;
import GUI.Button;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class BattlePanel extends BasePanel implements MouseListener
{
    private SpriteLoader spriteLoader;

    private Sprite backgroundSprite;

    private ArrayList<Weapon> weapons    = new ArrayList<>();
    private ArrayList<Character> enemies = new ArrayList<>();
    private Base.Character player;

    private GUI gui;
    private TextBox textbox;
    private Menu menu;

    public BattlePanel(double scalar, int monitorHZ, WindowLoader parent)
    {
        super(scalar, monitorHZ, parent);

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

        spriteLoader = new SpriteLoader();

        backgroundSprite = new Sprite(0, 0, 0, deepCopy(spriteLoader.returnImageFromSet("battleground")));

        weapons.add(new Weapon(100, 100, deepCopy(spriteLoader.returnImageFromSet("sword")), new WeaponStats(10, 10)));

        player =     new Base.Character(0, 100, deepCopy(spriteLoader.returnImageFromSet("angry")), new CharacterStats("Tom", 20));
        enemies.add (new Base.Character(300, 0, deepCopy(spriteLoader.returnImageFromSet("angry2")), new CharacterStats("Tom", 20)));

        //GUI code

        gui = new GUI();

        BufferedImage textBoxSprite = deepCopy(spriteLoader.returnImageFromSet("textbox"));
        textbox =
                new TextBox(
                        (int) (universalScalar * 1920 * 0.5) - (textBoxSprite.getWidth() / 2), //Centered
                        (int) (universalScalar * 1080 * 0.75), //In the bottom quarter of the screen
                        textBoxSprite,
                        3 //Max size of textbox
                             );

        menu = new Menu(
                //TODO: This doesn't position the menu entirely correctly, are you(Eli) assuming that all monitors are 1920 * 1080?
                (int) (universalScalar * 1920 * 0.5) - (textBoxSprite.getWidth() / 2), //Centered
                (int) (universalScalar * 1080 * 0.25), //In the top quarter of the screen
                spriteLoader.returnImageFromSet("textbox") //Eventually, we can make a different sprite for this menu background (but textbox's sprite works fine for now)
        );

        menu.add(new Button(spriteLoader.returnImageFromSet("buttonBack"), "dodge"));

        //Objects will be drawn in the order that they're added in
        gui.add(menu);
        gui.add(textbox);
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

        player.draw(g2d, this);


        enemies.removeIf(Character::isDead); //Look at this beauty
        weapons.removeIf(Weapon::noDurability);

        for (Character c : enemies)
        {
            c.draw(g2d, this);
        }
        for (Weapon w : weapons)
        {
            w.draw(g2d, this);
        }

        gui.draw(g2d, this); //Draw all of our GUI elements in one call (includes sub elements and backgrounds of elements)
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
                    textbox.add(new GUIText("Damaged enemy! Health remaining: " + String.valueOf(c.getHealth())));
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