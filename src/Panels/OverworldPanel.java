package Panels;

import Base.*;
import Save_System.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.*;
import javax.swing.Timer;
import java.util.Vector;


public class OverworldPanel extends BasePanel implements ActionListener, MouseListener {
    private Sprite backgroundSprite;

    private Point characterLocation = new Point();

    private Point characterMicroLocation = new Point(7, 4);

    private int[][] overworldMicroObjectVector = new int[16][9];

    private SpriteLoader spriteLoader = new SpriteLoader();
    private KeyboardManager keyboardManager = new KeyboardManager();

    private BufferedImage savedWorld;

    private long seed;

    private Timer autosaveTimer;
    private Sprite hero;

    //Make these locals if you want to
    private final int numXTiles = 15;
    private final int numYTiles = 9;
    private final int tileSize = 128;

    private final int chunkSize = 16;

    public OverworldPanel(double scalar, int monitorHZ, WindowLoader parent) {
        super(scalar, monitorHZ, parent);
        addMouseListener(this);
        addKeyListener(new TAdapter());
        setFocusable(true);

        OverworldSaveManager saveManager = new OverworldSaveManager();
        hero = new Sprite(914, 530, 0, spriteLoader.returnImageFromSet("hero"));

        Vector<Object> loadData = saveManager.loadFromSaveFile("worldsave.txt");

        if ((int) loadData.get(0) == 0 && (int) loadData.get(1) == 0 && (long) loadData.get(2) == 0){
            loadData.set(0, 512); loadData.set(1, 512); loadData.set(2, System.nanoTime());
        }
        characterLocation.setLocation((int) loadData.get(0), (int) loadData.get(1) );


        loadWorld();

        autosaveTimer = new Timer(60000, this);//60 secs or 1 minute
        autosaveTimer.start();

        runLoop();
    }

    private void loadWorld()
    {
        //Load the world if it exists, and reload it if it doesn't
        try {
            savedWorld = ImageIO.read(new File("world.png"));
        } catch (IOException e) {
            System.out.println("No saved world. This is normal for first time. Regenerating...");
            new WorldGenerator(numXTiles * chunkSize + 1, numYTiles * chunkSize + 1);

            try {
                savedWorld = ImageIO.read(new File("world.png"));
            } catch (IOException e1) {
                System.out.println("Unable to recreate world image, printing stack trace: ");

                e1.printStackTrace();
            }
        }

        //Start converting the world into tiles

        BufferedImage tiledImage = new BufferedImage(numXTiles * tileSize, numYTiles * tileSize, BufferedImage.TYPE_INT_RGB);

        for(int x = 0; x <= numXTiles*chunkSize; x++)
        {
            for(int y = 0; y <= numYTiles*chunkSize; y++)
            {
                Color pixel = new Color(savedWorld.getRGB(x, y));
                int index = 0;
                if(pixel.getGreen() > 0) index = 1;

                addImageWithAlphaComposite(tiledImage, deepCopy(spriteLoader.returnImageFromSet(index)), 1, x * 128, y * 128);
            }
        }

        backgroundSprite = new Sprite(0, 0, tiledImage);

        File imageWriteLocation = new File("test.png");
        try {
            ImageIO.write(tiledImage, "png", imageWriteLocation);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void reloadMapSprites() {
        //TODO check if chunk changed

        long time = System.nanoTime();

        //This commented out section is magic number hell, but I might figure it out

/*
        BufferedImage backgroundLoadBufferedImage= new BufferedImage(1920, 1152, BufferedImage.TYPE_INT_RGB);

        for (int x = -8; x < 7; x++){
            for (int y = -5; y < 4; y++){
                if (x + characterLocation.getX() < 0 || x + characterLocation.getX() > 1023){
                    copyColoredPixelsIntoBufferedImage(backgroundLoadBufferedImage, (x + 8) * 128, 0, 128, 1080, Color.GREEN);
                    break;
                }

                if (y + characterLocation.getY() >= 0 && y + characterLocation.getY() <= 1023){
                    int r;
                    Color c = new Color(saveGameToLoad.getRGB((int)characterLocation.getX() + x, (int)characterLocation.getY() + y));
                    r = c.getRed() / 24;
                    
                    //vector awesomeness
                    overworldMicroObjectVector[x + 8] [y + 5] = r;


                    addImageWithAlphaComposite(backgroundLoadBufferedImage, deepCopy(spriteLoader.returnImageFromSet(r)), 1, (x + 8) * 128, (y + 5) * 128);
                    //backgroundLoadBufferedImage = copySrcIntoDstAt
                            //(DeepCopy(loadImages.imageSetCopy.get(r)), backgroundLoadBufferedImage, x * 16, y * 16);
                } else {
                    copyColoredPixelsIntoBufferedImage(backgroundLoadBufferedImage, (x + 8) * 128, (y + 5) * 128, 128, 128, Color.GREEN);
                }
            }
        }
*/
        long endtime = System.nanoTime() - time;

        System.out.println("time taken (ns) : " + endtime);
    }



    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);

        Toolkit.getDefaultToolkit().sync();
    }

    protected void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.scale(universalScalar, universalScalar);

        long time = System.nanoTime();

        backgroundSprite.draw(g2d, this);
        hero.draw(g2d, this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

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
            hero.setY(hero.getY() + 128);
            characterMicroLocation.y--;
        }
        if (elvenAsciiInput[2]){
            characterLocation.x--;
            hero.setX(hero.getX() + 128);
            characterMicroLocation.x--;
        }
        if (elvenAsciiInput[3]){
            characterLocation.x++;
            hero.setX(hero.getX() - 128);
            characterMicroLocation.x++;
        }
        if (elvenAsciiInput[4]){
            characterLocation.y++;
            hero.setY(hero.getY() - 128);
            characterMicroLocation.y++;
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

            boolean reload = false;

            if (key == KeyEvent.VK_SPACE) {
                keyboardManager.elvenAsciiInput[0] = true;
            }

            if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
                if (overworldMicroObjectVector[(int) characterMicroLocation.getX() - 1] [ (int) characterMicroLocation.getY()] != 1){
                    characterMicroLocation.x--;
                    hero.setX(hero.getX() - 128);
                }
                keyboardManager.elvenAsciiInput[2] = true;


            }

            if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {

                if (overworldMicroObjectVector[(int) characterMicroLocation.getX() + 1] [ (int) characterMicroLocation.getY()] != 1){
                    hero.setX(hero.getX() + 128);
                    characterMicroLocation.x++;
                }


                keyboardManager.elvenAsciiInput[3] = true;
            }

            if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
                if (overworldMicroObjectVector[(int) characterMicroLocation.getX()] [ (int) characterMicroLocation.getY() - 1] != 1){
                    hero.setY(hero.getY() - 128);
                    characterMicroLocation.y--;
                }


                keyboardManager.elvenAsciiInput[1] = true;
            }

            if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
                if (overworldMicroObjectVector[(int) characterMicroLocation.getX()] [(int) characterMicroLocation.getY() + 1] != 1){
                    characterMicroLocation.y++;
                    hero.setY(hero.getY() + 128);
                }

                keyboardManager.elvenAsciiInput[4] = true;

            }
            if (key == KeyEvent.VK_Q) {
                keyboardManager.elvenAsciiInput[5] = true;
            }
            if (key == KeyEvent.VK_E) {
                keyboardManager.elvenAsciiInput[6] = true;
            }

            if (hero.getX() < 130){
                hero.setX(1682);
                characterLocation.x -= 13;
                characterMicroLocation.x = 13;
                reloadMapSprites();
            } else if (hero.getX() > 1690){
                hero.setX(146);
                characterLocation.x += 13;
                characterMicroLocation.x = 1;
                reloadMapSprites();
            }
            if (hero.getY() < 100){
                hero.setY(914);
                characterLocation.y -= 7;
                characterMicroLocation.y = 7;
                reloadMapSprites();
            } else if (hero.getY() > 920){
                hero.setY(146);
                characterLocation.y += 7;
                characterMicroLocation.y = 1;
                reloadMapSprites();
            }



            if (key == KeyEvent.VK_ESCAPE) {
                OverworldSaveManager saveManager = new OverworldSaveManager();
                saveManager.saveToFile("worldsave.txt", (int) characterLocation.x, (int) characterLocation.y, seed);

                System.exit(1);//Manually caused exit
            }


        }


    }


    @Override
    public void actionPerformed(ActionEvent e)
    {
        new OverworldSaveManager().saveToFile("worldsave.txt",
                (int) characterLocation.getX(), (int) characterLocation.getY(), seed);

    }



    @Override
    public void mouseReleased(MouseEvent me) {



        System.out.println(me.getX() / universalScalar);
        System.out.println(me.getY() / universalScalar);


    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}