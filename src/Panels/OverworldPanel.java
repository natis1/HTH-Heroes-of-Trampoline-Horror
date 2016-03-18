package Panels;

import Base.*;
import Save_System.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.swing.Timer;
import java.util.HashMap;
import java.util.Vector;

//Basically a quaternary tree data class
class Chunk extends Sprite
{
    public Chunk up;
    public Chunk down;
    public Chunk left;
    public Chunk right;

    public int chunkX, chunkY;

    public Chunk(BufferedImage image, int setChunkX, int setChunkY)
    {
        super(0,0,0,image);
        up    = null;
        down  = null;
        left  = null;
        right = null;

        chunkX = setChunkX;
        chunkY = setChunkY;
    }
}

public class OverworldPanel extends BasePanel implements ActionListener, MouseListener {

    private HashMap<Pair<Integer, Integer>, Chunk> loadedChunks =  new HashMap<>(); //Gets updated as player moves across the world
    //Also, Pair implements hashing for us
    private Chunk currentChunk;

    private Point characterLocation      = new Point();
    private Point characterMicroLocation = new Point(7, 4);

    private int[][] overworldMicroObjectVector = new int[16][9];

    private SpriteLoader spriteLoader       = new SpriteLoader();
    private KeyboardManager keyboardManager = new KeyboardManager();

    private BufferedImage savedWorld;

    private long seed;

    private Timer autosaveTimer;
    private Sprite hero;

    //Make these locals if you want to
    private final int numXTiles = 15; //Per chunk
    private final int numYTiles = 9;
    private final int tileSize  = 128;

    private final int loadDistance = 8;

    private final int maxWorldSize = 128; //In chunks squared

    public OverworldPanel(double scalar, int monitorHZ, WindowLoader parent) {
        super(scalar, monitorHZ, parent);


        addMouseListener(this);
        addKeyListener  (new TAdapter());
        setFocusable    (true);

        hero  = new Sprite(914, 530, 0, spriteLoader.returnImageFromSet("hero"));

        //Load previous save
        OverworldSaveManager saveManager = new OverworldSaveManager();

        Vector<Object> loadData = saveManager.loadFromSaveFile("worldsave.txt");

        if ((int) loadData.get(0) == 0 && (int) loadData.get(1) == 0 && (long) loadData.get(2) == 0){
            loadData.set(0, 512); loadData.set(1, 512); loadData.set(2, System.nanoTime());
        }
        characterLocation.setLocation((int) loadData.get(0), (int) loadData.get(1) );

        autosaveTimer = new Timer(60000, this); //60 seconds
        autosaveTimer.start();

        loadWorld();

        runLoop();
    }

    private void reloadWorld()
    {
        new WorldGenerator(numXTiles * maxWorldSize, numYTiles * maxWorldSize);

        try
        {
            savedWorld = ImageIO.read(new File("world.png"));
        }
        catch (IOException e1)
        {
            System.out.println("Unable to read world from file");

            e1.printStackTrace();
        }
    }

    private void loadWorld()
    {
        //Load the world if it exists, and reload it if it doesn't
        try
        {
            savedWorld = ImageIO.read(new File("world.png"));
        }
        catch (IOException e)
        {
            System.out.println("No saved world. This is normal for first time. Generating...");
            reloadWorld();
        }

        //Check that the saved world file is big enough, if not, regenerate it.
        if(savedWorld.getWidth() < numXTiles * maxWorldSize &&
                savedWorld.getHeight() < numYTiles * maxWorldSize)
        {
            System.out.println("Previous world too small, regenerating...");
           reloadWorld();
        }

        //Set our initial chunk to be in the middle of the world.
        currentChunk = loadChunk(maxWorldSize / 2, maxWorldSize / 2);
        loadNeighbors(currentChunk, loadDistance);
    }

    private Chunk loadChunk(int chunkX, int chunkY)
    {
        if(loadedChunks.get(new Pair<>(chunkX, chunkY)) == null) // If the chunk hasn't been loaded already
        {
            BufferedImage tiledImage = new BufferedImage(numXTiles * tileSize, numYTiles * tileSize, BufferedImage.TYPE_INT_RGB);

            for(int x = 0; x < numXTiles; x++)
            {
                for(int y = 0; y < numYTiles; y++)
                {
                    Color pixel = new Color(savedWorld.getRGB(x + chunkX * numXTiles, y + chunkY * numYTiles));
                    int index = 0;
                    if(pixel.getGreen() > 0) index = 1;

                    addImageWithAlphaComposite(tiledImage, deepCopy(spriteLoader.returnImageFromSet(index)), 1, x * tileSize, y * tileSize);
                }
            }

            Chunk chunk = new Chunk(tiledImage, chunkX, chunkY);

            loadedChunks.put(new Pair<>(chunkX, chunkY), chunk);
            return chunk;
        }
        else
        {
            return loadedChunks.get(new Pair<>(chunkX, chunkY));
        }
    }

    private void loadNeighbors(Chunk chunk, int distance)
    {
        if(distance > 0)
        {
            if (chunk.up == null)
            {
                chunk.up = loadChunk(chunk.chunkX, chunk.chunkY - 1); //Is this right?
                loadNeighbors(chunk.up, distance - 1);
            }
            if (chunk.down == null)
            {
                chunk.down = loadChunk(chunk.chunkX, chunk.chunkY + 1);
                loadNeighbors(chunk.up, distance - 1);
            }
            if (chunk.left == null)
            {
                chunk.left = loadChunk(chunk.chunkX - 1, chunk.chunkY);
                loadNeighbors(chunk.up, distance - 1);
            }
            if (chunk.right == null)
            {
                chunk.right = loadChunk(chunk.chunkX + 1, chunk.chunkY);
                loadNeighbors(chunk.up, distance - 1);
            }
        }
    }

    //Self explanatory, hopefully.
    private void traverseChunks(String direction)
    {
        switch (direction)
        {
            case "up":
                    currentChunk = currentChunk.up;
                break;
            case "down":
                currentChunk = currentChunk.down;
                break;
            case "left":
                currentChunk = currentChunk.left;
                break;
            case "right":
                currentChunk = currentChunk.right;
                break;
        }

        loadNeighbors(currentChunk, loadDistance); //Important so that we don't run out of chunks to walk into!
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

        currentChunk.draw(g2d, this);
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


            //Try replacing these numbers with calculated, understandable ones
            if (hero.getX() < 130){
                hero.setX(1682);
                characterLocation.x -= 13;
                characterMicroLocation.x = 13;
                traverseChunks("left");
            } else if (hero.getX() > 1690){
                hero.setX(146);
                characterLocation.x += 13;
                characterMicroLocation.x = 1;
                traverseChunks("right");
            }
            if (hero.getY() < 100){
                hero.setY(914);
                characterLocation.y -= 7;
                characterMicroLocation.y = 7;
                traverseChunks("up"); //Is this correct? sometimes Java Y-coordinates are screwy
            } else if (hero.getY() > 920){
                hero.setY(146);
                characterLocation.y += 7;
                characterMicroLocation.y = 1;
                traverseChunks("down");
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