import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.ArrayList;


public class OverworldPanel extends BasePanel implements MouseListener
{
    private Sprite backgroundSprite;
    private ArrayList<Chunk> chunks;

    public OverworldPanel(double scalar, int monitorHZ)
    {
        super(scalar, monitorHZ);
        addMouseListener(this);
        SpriteLoader loadImages = new SpriteLoader();


        String bgImageString = "main/resources/ANGRY.png";
        //is4K = (universalScalar > 1.0001);

        backgroundSprite = new Sprite(0, 0, 0, loadImages.imageSetCopy.get(129));

        chunks = new ArrayList<>();

        runLoop();
    }

    private BufferedImage deepCopy(BufferedImage bi)
    {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        //return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null).getSubimage(0, 0, bi.getWidth(), bi.getHeight());
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
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    private class TAdapter extends KeyAdapter
    {
        //TODO KEYBOARD SUPPORT
    }
}