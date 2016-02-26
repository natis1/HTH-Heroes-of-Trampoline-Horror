import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;


public class BattlePanel extends BasePanel implements MouseListener
{
    private Sprite backgroundSprite;
    private Sprite foregroundSprite; //The pokemon style battle thing
    private SpriteLoader imageLoader = new SpriteLoader();
    private KeyboardManager keyboardManager = new KeyboardManager();


    private ArrayList<Sprite> weapons    = new ArrayList<>(); //Should be eventually full of a class "Weapon"?
    private ArrayList<Sprite> characters = new ArrayList<>(); //Should be full of "Character" ?

    public BattlePanel(double scalar, int monitorHZ)
    {
        super(scalar, monitorHZ);
        addMouseListener(this);
        addKeyListener(new TAdapter());
        setFocusable(true);

        this.setBackground(Color.BLACK);

        String bgImageString = "main/resources/mainMenuScreen.png";
        String fgImageString = "main/resources/ANGRY.png";

        backgroundSprite.createTransformedImage(imageLoader.loadImage(bgImageString), 0);
        backgroundSprite.createTransformedImage(imageLoader.loadImage(fgImageString), 0);

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

        //Draw stuff here
        g2d.drawImage(backgroundSprite.getImage(), backgroundSprite.getX(),
                backgroundSprite.getY(), this);


        g2d.drawImage(foregroundSprite.getImage(), foregroundSprite.getX(),
                foregroundSprite.getY(), this);
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