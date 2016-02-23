import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.*;
import java.util.ArrayList;


public class OverworldPanel extends BasePanel implements MouseListener
{
    private Sprite backgroundSprite;

    private Point characterLocation = new Point();
    private Point characterChunk = new Point();
    private SpriteLoader loadImages = new SpriteLoader();

    private BufferedImage saveGameToLoad;

    private long seed;

    private ArrayList<Sprite> backgroundPoints = new ArrayList<Sprite>();


    public OverworldPanel(double scalar, int monitorHZ)
    {
        super(scalar, monitorHZ);
        addMouseListener(this);
        ReadSaveFile();




        String bgImageString = "main/resources/ANGRY.png";

        characterChunk = characterLocation;
        characterChunk.x += 512 ; characterChunk.y += 512 ;
        characterChunk.x /= 1024; characterChunk.y /= 1024;


        try {
            saveGameToLoad = ImageIO.read(new File("world" + (int) characterChunk.getX() + " " + (int) characterChunk.getY() + ".png"));


        } catch (IOException e) {
            System.out.println("Error loading image. IOException Regenerating");
            new RandomWorldGenerator(seed, characterChunk.x, characterChunk.y);


            try {
                saveGameToLoad = ImageIO.read(new File("world" + (int) characterChunk.getX() + " " + (int) characterChunk.getY() + ".png"));
            } catch (IOException e1) {
                System.out.println("Unknown Error prevented image loading");

                e1.printStackTrace();
            }
        }

        LoadMapSprites();



        backgroundSprite = new Sprite(0, 0, 0, new RandomImageGenerator(1920, 1080).nextRandomImage );

        runLoop();
    }


    private void LoadMapSprites() {

        int characterX = 512;
        int characterY = 512;


        characterLocation.x += 512;
        characterLocation.y += 512;



        for (int x = -62; x < 62; x++){
            for (int y = -36; y < 36; y++){
                int r;
                Color c = new Color(saveGameToLoad.getRGB(characterX + x, characterY + y));
                r = c.getRed();
                //r /= 0x010000;

                backgroundPoints.add(new Sprite(960 - 16 * x, 540 - 16 * y, 0, DeepCopy(loadImages.imageSetCopy.get(r))));


            }
        }
    }

    private void ReloadMapSprites() {
        //TODO check if chunk changed

        backgroundPoints.clear();



        long time = System.nanoTime();


        for (int x = -62; x < 62; x++){
            for (int y = -36; y < 36; y++){

                int r;
                Color c = new Color(saveGameToLoad.getRGB((int)characterLocation.getX() + x, (int)characterLocation.getY() + y));
                r = c.getRed();


                backgroundPoints.add(new Sprite(960 - 16 * x, 540 - 16 * y, 0, DeepCopy(loadImages.imageSetCopy.get(r))));


            }
        }

        long endtime = System.nanoTime() - time;

        System.out.println("time taken (ns) : " + endtime);

    }



    private void ReadSaveFile() {


        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader =
                    new FileReader("worldsave.txt");

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            if (bufferedReader.readLine() != null) {
                characterLocation.x = Integer.parseInt(bufferedReader.readLine());
                characterLocation.y = Integer.parseInt(bufferedReader.readLine());
                seed = Long.valueOf(bufferedReader.readLine());
                if (seed == 0){
                    seed = System.nanoTime();
                }
                bufferedReader.close();
            }




        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e) {
            //Please restart or something
            e.printStackTrace();
        }
    }




    private BufferedImage DeepCopy(BufferedImage bi)
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

        for (int bkg = 0; bkg < backgroundPoints.size(); bkg++){
            g2d.drawImage(backgroundPoints.get(bkg).getImage(),
                    backgroundPoints.get(bkg).getX(), backgroundPoints.get(bkg).getY(), this);

        }

        //Draw stuff here
        //g2d.drawImage(backgroundSprite.getImage(), backgroundSprite.getX(),
        //        backgroundSprite.getY(), this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

        if (e.getY() < 100){
            characterLocation.y++;

            ReloadMapSprites();

        } else if (e.getY() > 980){
            characterLocation.y--;

            ReloadMapSprites();
        }

        if (e.getX() < 100){
            characterLocation.x++;

            ReloadMapSprites();
        } else if (e.getX() > 1820){
            characterLocation.x--;

            ReloadMapSprites();
        }


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