//package com.zetcode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

//import java.awt.Image;


public class MainMenuPanel extends JPanel implements MouseListener {

    //private KeyboardAnimation animation2

    
    private double universalScaler;

    private double now;
    private int computerHZ;
    private boolean is4K;
    private int lastSecondTime;
    private double lastUpdateTime;

    public static double speedMultiplier;
    double TIME_BETWEEN_UPDATES;

    //This caps the game framerate, which means that the game doesn't use delta-T
    //For calculating movement. I think this is fine if we set the cap at something like 144hz (~7)
    //Smooth enough for most monitors even if eyes can still see past it.


    private Sprite backgroundSprite;

    
    //3- Draw all, 2- No useless sprites, 1- No moving background, 0- TBD when we need more GPU capabilities.
    private int graphicsQuality = 3;


    private int frameCatchup = 0;

    
    
    public MainMenuPanel(double scaler, int monitorHZ) {
        universalScaler = scaler;
        computerHZ = monitorHZ;
        //computerHZ = 300;
        TIME_BETWEEN_UPDATES = 1000000000 / computerHZ;
        //testing only
        speedMultiplier = (double) (60) / (double) computerHZ; //designed for 60, compensates for everything else.

        //100% working on every multiple of 60, everything exept background works perfectly on any other number.
        initBoard();
        
        
    }

    private void initBoard() {

        addMouseListener(this);


        String bgImageString;
        if (universalScaler <= 1.0001){
            bgImageString = "main/resources/mainMenuScreen.png";
            is4K = false;
        } else {
            bgImageString = "main/resources/mainMenuScreen.png";
            is4K = true; //maybe if I ever use 4k images for other stuff,
        }

        backgroundSprite = new Sprite(0, 0, 0, 0, bgImageString);
        backgroundSprite.loadImage();

        runGameLoop();

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
the drawing queue, create the class inside the board.

Yes I know it is an oversite, whatever.

*/
    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        g2d.scale(universalScaler, universalScaler);


        //Draw stuff here
        g2d.drawImage(backgroundSprite.getImage(), backgroundSprite.getX(),
                backgroundSprite.getY(), this);






        
        
        
    }

    //Starts a new thread and runs the game loop in it.
    public void runGameLoop()
    {
        Thread loop = new Thread()
        {
            public void run()
            {
                gameLoop();
            }
        };
        loop.start();
    }
    
    public void gameLoop()
    {
        //This value would probably be stored elsewhere.


        //Always get lots of hz even if it isn't possible to render this many frames

        //Calculate how many ns each frame should take for our target game hertz.

        //At the very most we will update the game this many times before a new render.
        //If you're worried about visual hitches more than perfect timing, set this to 1.
        final int MAX_UPDATES_BEFORE_RENDER = 100;
        //We will need the last update time.
        lastUpdateTime = System.nanoTime();
        //Store the last time we rendered.
        double lastRenderTime;

        //If we are able to get as high as this FPS, don't render again.
        final double TARGET_FPS = computerHZ;
        final double TARGET_TIME_BETWEEN_RENDERS = 1000000000 / TARGET_FPS;

        //Simple way of finding FPS.
        lastSecondTime = (int) (lastUpdateTime / 1000000000);

        while (true)
        {
            now = System.nanoTime();
            int updateCount = 0;


                //Do as many game updates as we need to, potentially playing catchup.
                while(now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER)
                {
                    update();
                    lastUpdateTime += TIME_BETWEEN_UPDATES;
                    updateCount++;
                    if (updateCount > 15 && graphicsQuality > 2){
                        graphicsQuality--;
                        //updateCount = 10;
                        frameCatchup = (int) (300 / speedMultiplier);
                    }

                }

                //If for some reason an update takes forever, we don't want to do an insane number of catchups.
                //If you were doing some sort of game that needed to keep EXACT time, you would get rid of this.
                if ( now - lastUpdateTime > TIME_BETWEEN_UPDATES)
                {




                    lastUpdateTime = now - TIME_BETWEEN_UPDATES;
                }

                //Render. To do so, we need to calculate interpolation for a smooth render.

                lastRenderTime = now;

                //Update the frames we got.
                int thisSecond = (int) (lastUpdateTime / 1000000000);
                if (thisSecond > lastSecondTime)
                {
                    //TODO System.out.println("Main: NEW SECOND " + thisSecond + " " + frame_count);




                    lastSecondTime = thisSecond;
                }


                //Yield until it has been at least the target time between renders. This saves the CPU from hogging.
                while ( now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS)
                {

                    if (graphicsQuality < 3){
                        frameCatchup--;
                    }
                    if (frameCatchup < 0){
                        graphicsQuality = 3; // Good job team, you did it.
                    }

                    Thread.yield();


                    try {

                        Thread.sleep(1);

                    } catch(Exception e) {
                        e.printStackTrace();

                    }

                    now = System.nanoTime();
                }
            }
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


        //NOT DONE HERE ANYMORE
        //repaint();
    }
    
    private void updateParticles() {


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

        System.out.println(me.getX() / universalScaler);
        System.out.println(me.getY() / universalScaler);

        if ((me.getX() / universalScaler) > 1030 && (me.getY() / universalScaler < 316)){

            //1 = start windows game new.
            Main.ElvenGameState = 1;

            //TODO ADD SOMETHING HERE


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