package Base;

import Panels.BattlePanel;
import Panels.MainMenuPanel;
import Panels.OverworldPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;


public class WindowLoader {

    private JFrame myBlackScreen;
    private JFrame myGameScreen;
    private JFrame myMenuScreen;

    private static int ElvenFramerate = 0;
    private static int ElvenWindowedResolution = 0;


    private double screenWidth;
    private double screenHeight;


    private boolean isRunning = false;

    private boolean spawnBlackBKG = true;

    private boolean didInit = false;

    private int pseudoVSync;
    private double universalScaler;


    public WindowLoader() {

        initUI("main_menu");

        if (ElvenWindowedResolution == 0 && spawnBlackBKG){
            initBlackUI();
            myGameScreen.toFront();
        }
    }

    private void initBlackUI() {

        //Get computer screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenWidth = screenSize.getWidth();
        double screenHeight = screenSize.getHeight();


        myBlackScreen = new JFrame();

        myBlackScreen.setUndecorated(true);
        myBlackScreen.getContentPane().setBackground(Color.BLACK);
        myBlackScreen.setLocation(-0, -0);


        myBlackScreen.setSize((int) screenWidth, (int) screenHeight);
        myBlackScreen.setResizable(false);


        myBlackScreen.setTitle("Trampolines");
        myBlackScreen.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        myBlackScreen.setVisible(true);
    }


    public void initUI(String UIName) {
//no don't merge thank you and good night
        isRunning = true;
        if (!didInit){

            // This will reference one line at a time
            //String line = null;

            try {
                // FileReader reads text files in the default encoding.
                FileReader fileReader =
                        new FileReader("worldsave.txt");

                // Always wrap FileReader in BufferedReader.
                BufferedReader bufferedReader =
                        new BufferedReader(fileReader);


                if (bufferedReader.readLine() != null){
                    System.out.print(bufferedReader.readLine());
                    System.out.print(bufferedReader.readLine());
                    System.out.print(bufferedReader.readLine());


                    //Line 5 - 6

                    ElvenWindowedResolution = Integer.parseInt(bufferedReader.readLine());
                    ElvenFramerate = Integer.parseInt(bufferedReader.readLine());

                    if (ElvenFramerate == 0){
                        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                        GraphicsDevice[] gs = ge.getScreenDevices();

                        for (int i = 0; i < gs.length; i++) {
                            DisplayMode dm = gs[i].getDisplayMode();

                            pseudoVSync = dm.getRefreshRate();
                            ElvenFramerate = pseudoVSync;
                            if (pseudoVSync == DisplayMode.REFRESH_RATE_UNKNOWN) {
                                System.out.println("Unknown HZ, using 60 because you are probably in a VM or something"); //I love VMs, might add an override
                                //if the person runs it from the cmdline with the --hz option.
                                pseudoVSync = 60;
                                ElvenFramerate = pseudoVSync;
                            }
                        }
                    } else {

                        pseudoVSync = ElvenFramerate;//use user set fps
                    }



                }


                bufferedReader.close();
            }
            catch(FileNotFoundException ex) {
                System.out.println(
                        "Unable to open file Because the file was not found. Recreating... this may lead to lost data");

                PrintWriter writer;
                try {
                    writer = new PrintWriter("worldsave.txt", "UTF-8");

                    writer.println("Save File. Do not edit you cheater");
                    writer.println("0");//X location
                    writer.println("0");//Y location
                    writer.println("0");//Seed
                    writer.println("0");//Resolution (0 for fullscreen) This is the height (IE 1080p 720p etc)
                    writer.println("0");//Framerate
                    writer.close();


                    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                    GraphicsDevice[] gs = ge.getScreenDevices();

                    for (int i = 0; i < gs.length; i++) {
                        DisplayMode dm = gs[i].getDisplayMode();

                        pseudoVSync = dm.getRefreshRate();
                        ElvenFramerate = pseudoVSync;
                        if (pseudoVSync == DisplayMode.REFRESH_RATE_UNKNOWN) {
                            System.out.println("Unknown HZ, using 60 because you are probably in a VM or something"); //I love VMs, might add an override
                            //if the person runs it from the cmdline with the --hz option.
                            pseudoVSync = 60;
                            ElvenFramerate = pseudoVSync;
                        }
                    }



                } catch (FileNotFoundException e) {
                    System.out.println(
                            "No idea why I can't make file Something is seriously wrong with your system");

                } catch (UnsupportedEncodingException e) {
                    System.out.println(
                            "You do not have UTF-8? I am seriously amazed");
                }



            }
            catch(IOException ex) {
                System.out.println(
                        "Error reading file Because you do not have permissions. Run as Admin to fix.");
                // WE ARE NOT PRINTING A STACK TRACE WHEN THE USER'S COMPUTER BREAKS, USERS DON'T KNOW HOW TO READ STACK TRACES
                // HAVE YOU EVER SEEN PEOPLE POSTING 600 LINE TRACES WHICH ARE COMPLETELY USELESS WHEN THEIR MINECRAFT CRASHES?
            }
            didInit = true;

        } else {
            myGameScreen.dispose();
            myGameScreen.removeAll();
            myGameScreen.revalidate();
            myGameScreen.repaint();
        }

        if (ElvenFramerate != 0){
            pseudoVSync = ElvenFramerate;
        }


        myGameScreen = new JFrame();
        myGameScreen.getContentPane().setBackground(Color.BLACK);
        //set this first

        //Get computer screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        int screenChangeXBy = 0;
        int screenChangeYBy = 0;
        universalScaler = 1;

        if (ElvenWindowedResolution == 0){
            screenWidth = screenSize.getWidth();
            screenHeight = screenSize.getHeight();

            myGameScreen.setUndecorated(true);
            myGameScreen.setResizable(false); //fullscreen

            if (screenWidth < screenHeight * (16.0 / 9.0)){
                screenChangeYBy = -(int) ((screenWidth * ( 9.0 / 16.0) - screenHeight) / 2);
                screenHeight = screenWidth * ( 9.0 / 16.0);
                universalScaler = screenHeight / 1080.0;
                //Super wide monitors
            } else if (screenWidth > screenHeight * ( 16.0 / 9.0)) {
                //force height to be a nice guy
                screenChangeXBy = -(int) ((screenHeight * ( 16.0 / 9.0) - screenWidth) / 2);
                screenWidth = screenHeight * ( 16.0 / 9.0);
                universalScaler = screenHeight / 1080.0;
            }
        } else {
            screenHeight = ElvenWindowedResolution;
            screenWidth = screenHeight * 16.0 / 9.0;
            universalScaler = screenHeight / 1080.0;

            myGameScreen.setUndecorated(false);
            myGameScreen.setResizable(false); //windowed
        }


        if (screenHeight == screenSize.getHeight()){
            spawnBlackBKG = false;
        }

        switch (UIName) {
            case "main_menu":
                myGameScreen.add(new MainMenuPanel(universalScaler, pseudoVSync, this));
                break;

            case "overworld":
                myGameScreen.add(new OverworldPanel(universalScaler, pseudoVSync, this));
                break;

            case "battle":
                myGameScreen.add(new BattlePanel(universalScaler, pseudoVSync, this));
                break;
        }



        myGameScreen.setLocation(screenChangeXBy, screenChangeYBy);

        //I sure hope your screen size is an int
        myGameScreen.setVisible(true);
        myGameScreen.setSize((int) screenWidth, (int) screenHeight);


        myGameScreen.setBackground(Color.black);

        myGameScreen.setTitle("Trampoline Hero!");
    }



}