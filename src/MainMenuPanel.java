import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class MainMenuPanel extends BasePanel implements MouseListener
{
    private Sprite backgroundSprite;

    public MainMenuPanel(double scalar, int monitorHZ)
    {
        super(scalar, monitorHZ);
        addMouseListener(this);

        String bgImageString = "main/resources/mainMenuScreen.png";
        //is4K = (universalScalar > 1.0001);

        backgroundSprite = new Sprite(0, 0, 0, bgImageString);

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

        System.out.println(me.getX() / universalScalar);
        System.out.println(me.getY() / universalScalar);

        if ((me.getX() / universalScalar) > 1030 && (me.getY() / universalScalar < 316)){

            //1 = start windows game new.
            Main.ElvenGameState = 2;

            //TODO ADD SOMETHING HERE
        } else if ((me.getX() / universalScalar) > 1030 && (me.getY() / universalScalar > 600)){
            System.exit(1);   // Manually caused exit
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