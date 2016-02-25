import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
    private KeyboardManager keyboardManager = new KeyboardManager();

    private BufferedImage saveGameToLoad;

    private long seed;



    //private ArrayList<Sprite> backgroundPoints = new ArrayList<Sprite>();


    public OverworldPanel(double scalar, int monitorHZ) {
        super(scalar, monitorHZ);
        addMouseListener(this);
        addKeyListener(new TAdapter());
        setFocusable(true);

        ReadSaveFile();



        this.setBackground(Color.GREEN);




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
                System.out.println("Unknown Error prevented image loading.\nThis shouldn't EVER happen. Corrupted harddrive maybe?\nLook at the stacktrace");

                e1.printStackTrace();
            }
        }





        backgroundSprite = new Sprite(0, 0, 0, new RandomImageGenerator(16, 16).nextRandomImage );

        LoadMapSprites();


        runLoop();
    }


    private void LoadMapSprites() {

        int characterX = 32;
        int characterY = 32;


        characterLocation.x += 32;
        characterLocation.y += 32;


        BufferedImage backgroundLoadBufferedImage= new BufferedImage(1920, 1088, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < 120; x++){
            for (int y = 0; y < 68; y++){

                if (x + characterLocation.getX() < 0 || x + characterLocation.getX() > 1023){
                    break;
                }

                if (y + characterLocation.getY() >= 0 && y + characterLocation.getY() <= 1023){
                    int r;
                    Color c = new Color(saveGameToLoad.getRGB(characterX + x, characterY + y));
                    r = c.getRed();
                    //r /= 0x010000;

                    backgroundLoadBufferedImage = copySrcIntoDstAt
                            (DeepCopy(loadImages.imageSetCopy.get(r)), backgroundLoadBufferedImage, x * 16, y * 16);
                }



            }
        }

        backgroundSprite.image = backgroundLoadBufferedImage;
    }

    private BufferedImage copySrcIntoDstAt(BufferedImage src, BufferedImage dst, int dx, int dy) {
        for (int x = 0; x < src.getWidth(); x++) {
            for (int y = 0; y < src.getHeight(); y++) {
                    dst.setRGB( dx + x, dy + y, src.getRGB(x,y) );
            }
        }
        return dst;
    }

    private void ReloadMapSprites() {
        //TODO check if chunk changed





        long time = System.nanoTime();

        BufferedImage backgroundLoadBufferedImage= new BufferedImage(1920, 1088, BufferedImage.TYPE_INT_RGB);


        for (int x = 0; x < 120; x++){
            for (int y = 0; y < 68; y++){
                if (x + characterLocation.getX() < 0 || x + characterLocation.getX() > 1023){
                    break;
                }


                if (y + characterLocation.getY() >= 0 && y + characterLocation.getY() <= 1023){
                    int r;
                    Color c = new Color(saveGameToLoad.getRGB((int)characterLocation.getX() + x, (int)characterLocation.getY() + y));
                    r = c.getRed();

                    backgroundLoadBufferedImage = copySrcIntoDstAt
                            (DeepCopy(loadImages.imageSetCopy.get(r)), backgroundLoadBufferedImage, x * 16, y * 16);
                }

            }
        }

        long endtime = System.nanoTime() - time;

        System.out.println("time taken (ns) : " + endtime);
        backgroundSprite.image = backgroundLoadBufferedImage;

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


    private void SaveToFile() {


        PrintWriter fileWriter;
        try {
            fileWriter = new PrintWriter("worldsave.txt", "UTF-8");

            fileWriter.println("Save File. Do not edit you cheater");
            fileWriter.println(characterLocation.getX());//X location
            fileWriter.println(characterLocation.getY());//Y location
            fileWriter.println(seed);//Seed
            fileWriter.println("0");//Resolution (0 for fullscreen) This is the height (IE 1080p 720p etc)
            fileWriter.println("0");//Framerate
            fileWriter.close();



        } catch (FileNotFoundException e) {
            System.out.println(
                    "No idea why I can't make file Something is seriously wrong with your system");

        } catch (UnsupportedEncodingException e) {
            System.out.println(
                    "You do not have UTF-8? I am seriously amazed");
        }


    }




    private BufferedImage DeepCopy(BufferedImage bi) {
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
            ReloadMapSprites();
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
            ReloadMapSprites();
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