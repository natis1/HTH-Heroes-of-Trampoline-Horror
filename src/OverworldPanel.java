import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseEvent;


public class OverworldPanel extends BasePanel
{
    private Sprite backgroundSprite;

    public OverworldPanel(double scalar, int monitorHZ)
    {
        super(scalar, monitorHZ);
        addMouseListener(this);

        String bgImageString;
        if (universalScalar <= 1.0001){
            bgImageString = "main/resources/mainMenuScreen.png";
            is4K = false;
        } else {
            bgImageString = "main/resources/mainMenuScreen.png";
            is4K = true; //maybe if I ever use 4k images for other stuff,
        }

        backgroundSprite = new Sprite(0, 0, 0, 0, bgImageString);
        backgroundSprite.loadImage();

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

    private void doDrawing(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        g2d.scale(universalScalar, universalScalar);

        //Draw stuff here
        g2d.drawImage(backgroundSprite.getImage(), backgroundSprite.getX(),
                backgroundSprite.getY(), this);
    }

    private void drawGame(float interpolation)
    {
        repaint();
    }

    public void update() {

        if (graphicsQuality > 2){
            updateParticles();
        }

        float interpolation = Math.min(1.0f, (float) ((now - lastUpdateTime) / TIME_BETWEEN_UPDATES) );
        drawGame(interpolation);
    }

    private void updateParticles()
    {
        //TODO ADD ACTUAL PARTICLES
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
        }
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