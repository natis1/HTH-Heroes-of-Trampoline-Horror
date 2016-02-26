import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.*;
import javax.swing.Timer;
import java.util.Vector;


public class OverworldPanel extends BasePanel implements ActionListener, MouseListener {
    private Sprite backgroundSprite;

    private Point characterLocation = new Point();
    private Point characterChunk = new Point();
    private SpriteLoader loadImages = new SpriteLoader();
    private KeyboardManager keyboardManager = new KeyboardManager();

    private BufferedImage saveGameToLoad;

    private long seed;

    private Timer autosaveTimer;

    public OverworldPanel(double scalar, int monitorHZ) {
        super(scalar, monitorHZ);
        addMouseListener(this);
        addKeyListener(new TAdapter());
        setFocusable(true);



        int[] savedIntegers = new int[3];
        OverworldSaveManager saveManager = new OverworldSaveManager();

        Vector<Long> loadData = saveManager.loadFromSaveFile("worldsave.txt");

        //characterLocation.x = Integer.parsloadData.get(0);




        this.setBackground(Color.GREEN);

        characterChunk = characterLocation;
        characterChunk.x += 512 ; characterChunk.y += 512 ;
        characterChunk.x /= 1024; characterChunk.y /= 1024;


        try {
            saveGameToLoad = ImageIO.read(new File("world" + (int) characterChunk.getX() + " " + (int) characterChunk.getY() + ".png"));


        } catch (IOException e) {
            System.out.println("Error loading image. This is normal for first time. IOException Regenerating");
            new RandomWorldGenerator(seed, characterChunk.x, characterChunk.y);


            try {
                saveGameToLoad = ImageIO.read(new File("world" + (int) characterChunk.getX() + " " + (int) characterChunk.getY() + ".png"));
            } catch (IOException e1) {
                System.out.println("Unknown Error prevented image loading.\nThis shouldn't EVER happen. Corrupted harddrive maybe?\nLook at the stacktrace");

                e1.printStackTrace();
            }
        }





        backgroundSprite = new Sprite(0, 0, 0, new RandomImageGenerator(16, 16).nextRandomImage );

        loadMapSprites();



        //Lastly setup autosaving by implementing a timer
        autosaveTimer = new javax.swing.Timer(60000, this);//60 secs or 1 minute
        autosaveTimer.start();

        runLoop();
    }

    private void loadMapSprites() {

        int characterX = 32;
        int characterY = 32;


        characterLocation.x += 32;
        characterLocation.y += 32;


        BufferedImage backgroundLoadBufferedImage= new BufferedImage(1920, 1088, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < 120; x++){
            for (int y = 0; y < 68; y++){

                if (x + characterLocation.getX() < 0 || x + characterLocation.getX() > 1023){
                    backgroundLoadBufferedImage = copyColoredPixelsIntoBufferedImage(
                            backgroundLoadBufferedImage, x * 16, 0, 16, 1080, 0x00AA00);
                    break;
                }

                if (y + characterLocation.getY() >= 0 && y + characterLocation.getY() <= 1023){
                    int r;
                    Color c = new Color(saveGameToLoad.getRGB(characterX + x, characterY + y));
                    r = c.getRed();
                    //r /= 0x010000;

                    addImageWithAlphaComposite(backgroundLoadBufferedImage, deepCopy(loadImages.imageSetCopy.get(r)), 1, x * 16, y * 16);
                    //backgroundLoadBufferedImage = copySrcIntoDstAt
                            //(DeepCopy(loadImages.imageSetCopy.get(r)), backgroundLoadBufferedImage, x * 16, y * 16);
                } else {
                    //AddImageWithAlphaComposite(backgroundLoadBufferedImage, new BufferedImage(16, 16, ), 1, x * 16, y * 16);
                }
            }
        }
        backgroundSprite.image = backgroundLoadBufferedImage;
    }

    private void addImageWithAlphaComposite(BufferedImage buff1, BufferedImage buff2, float opaque, int x, int y) {
        Graphics2D g2d = buff1.createGraphics();
        g2d.setComposite(
                AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opaque));
        g2d.drawImage(buff2, x, y, null);
        g2d.dispose();
    }

    private BufferedImage addImageUsingSetRGB(BufferedImage src, BufferedImage dst, int dx, int dy) {
        for (int x = 0; x < src.getWidth(); x++) {
            for (int y = 0; y < src.getHeight(); y++) {
                dst.setRGB( dx + x, dy + y, src.getRGB(x,y) );
            }
        }
        return dst;
    }

    private BufferedImage copyColoredPixelsIntoBufferedImage(BufferedImage dst, int dx, int dy, int sizex, int sizey, int color) {
        for (int x = 0; x < sizex; x++) {
            for (int y = 0; y < sizey; y++) {
                dst.setRGB( dx + x, dy + y, color);
            }
        }
        return dst;
    }

    private void reloadMapSprites() {
        //TODO check if chunk changed





        long time = System.nanoTime();

        BufferedImage backgroundLoadBufferedImage= new BufferedImage(1920, 1088, BufferedImage.TYPE_INT_RGB);


        for (int x = 0; x < 120; x++){
            for (int y = 0; y < 68; y++){
                if (x + characterLocation.getX() < 0 || x + characterLocation.getX() > 1023){
                    backgroundLoadBufferedImage = copyColoredPixelsIntoBufferedImage(
                            backgroundLoadBufferedImage, x * 16, 0, 16, 1080, 0x00AA00);

                    break;
                }


                if (y + characterLocation.getY() >= 0 && y + characterLocation.getY() <= 1023){
                    int r;
                    Color c = new Color(saveGameToLoad.getRGB((int)characterLocation.getX() + x, (int)characterLocation.getY() + y));
                    r = c.getRed();

                    addImageWithAlphaComposite(backgroundLoadBufferedImage, deepCopy(loadImages.imageSetCopy.get(r)), 1, x * 16, y * 16);
                    //backgroundLoadBufferedImage = copySrcIntoDstAt
                            //(DeepCopy(loadImages.imageSetCopy.get(r)), backgroundLoadBufferedImage, x * 16, y * 16);
                } else {
                    backgroundLoadBufferedImage = copyColoredPixelsIntoBufferedImage(
                            backgroundLoadBufferedImage, x * 16, y * 16, 16, 16, 0xAA0000);
                }

            }
        }

        long endtime = System.nanoTime() - time;

        System.out.println("time taken (ns) : " + endtime);
        backgroundSprite.image = backgroundLoadBufferedImage;
    }

    private BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        //return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null).getSubimage(0, 0, bi.getWidth(), bi.getHeight());
    }



    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        checkIfKeysArePressed();
        doDrawing(g);


        Toolkit.getDefaultToolkit().sync();
    }

    /** This function draws all of the sprites using graphics2D libraries
     All drawing must be called from the board
     You cannot call doDrawing from other classes, to add a sprite to
     the drawing queue, create the class inside the board.*/

    protected void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.scale(universalScalar, universalScalar);

        long time = System.nanoTime();

        g2d.drawImage(backgroundSprite.getImage(), backgroundSprite.getX(), backgroundSprite.getY(), this);

        long endtime = System.nanoTime() - time;


        //System.out.println("Draw Time : " + endtime);

        //Draw stuff here
        //g2d.drawImage(backgroundSprite.getImage(), backgroundSprite.getX(),
        //        backgroundSprite.getY(), this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

        boolean reload = false;

        if (e.getY() < 100){
            characterLocation.y--;
            reload = true;


        }
        if (e.getY() > 980){
            characterLocation.y++;
            reload = true;
        }

        if (e.getX() < 100){
            characterLocation.x--;
            reload = true;
        }
        if (e.getX() > 1820){
            characterLocation.x++;
            reload = true;
        }



        //Only redraw once
        if (reload) {
            reloadMapSprites();
        }
    }


    //TODO move all this stuff below here to its own class.




    public void checkIfKeysArePressed(){

        boolean[] elvenAsciiInput = keyboardManager.elvenAsciiInput;

        boolean reload = false;

        if (elvenAsciiInput[0]){
            //TODO add space button
        }
        if (elvenAsciiInput[1]){
            characterLocation.y--;
            reload = true;
        }
        if (elvenAsciiInput[2]){
            characterLocation.x--;
            reload = true;
        }
        if (elvenAsciiInput[3]){
            characterLocation.x++;
            reload = true;
        }
        if (elvenAsciiInput[4]){
            characterLocation.y++;
            reload = true;
        }
        if (elvenAsciiInput[5]){
            //TODO add input when user presses Q
        }
        if (elvenAsciiInput[6]){
            //TODO add input when user presses E
        }

        if (reload) {
            reloadMapSprites();
        }


    }

    private class TAdapter extends KeyAdapter {


        @Override
        public void keyReleased(KeyEvent e) {

            int key = e.getKeyCode();



            if (key == KeyEvent.VK_SPACE) {
                keyboardManager.elvenAsciiInput[0] = false;
            }

            if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
                keyboardManager.elvenAsciiInput[2] = false;


            }

            if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
                keyboardManager.elvenAsciiInput[3] = false;
                //dx = dx + accelx;
            }

            if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
                keyboardManager.elvenAsciiInput[1] = false;
            }

            if (key == KeyEvent.VK_Q) {
                keyboardManager.elvenAsciiInput[5] = false;
            }
            if (key == KeyEvent.VK_E) {
                keyboardManager.elvenAsciiInput[6] = false;
            }

            if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {


                keyboardManager.elvenAsciiInput[4] = false;
            }
        }


        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if (key == KeyEvent.VK_SPACE) {
                keyboardManager.elvenAsciiInput[0] = true;
            }

            if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
                keyboardManager.elvenAsciiInput[2] = true;
            }

            if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
                keyboardManager.elvenAsciiInput[3] = true;
            }

            if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
                keyboardManager.elvenAsciiInput[1] = true;
            }

            if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
                keyboardManager.elvenAsciiInput[4] = true;
            }
            if (key == KeyEvent.VK_Q) {
                keyboardManager.elvenAsciiInput[5] = true;
            }
            if (key == KeyEvent.VK_E) {
                keyboardManager.elvenAsciiInput[6] = true;
            }


        }


    }


    @Override
    public void actionPerformed(ActionEvent e) {

        new OverworldSaveManager().saveToFile("worldsave.txt",
                (long) characterLocation.getX(), (long) characterLocation.getY(), seed);

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

}