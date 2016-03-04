package Panels;

import Base.Main;
import Base.Sprite;
import Base.SpriteLoader;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import GUI.Menu;
import GUI.Button;


public class MainMenuPanel extends BasePanel implements MouseListener
{
    private SpriteLoader imageLoader;
    private Menu menu;

    public MainMenuPanel(double scalar, int monitorHZ)
    {
        super(scalar, monitorHZ);
        imageLoader =  new SpriteLoader();
        addMouseListener(this);

        menu = new Menu(300, 0, imageLoader.returnImageFromSet("menuBack")); //Background for the menu

        //This code is just for testing that the menu class works.
        menu.add(new Button(350,   0, imageLoader.returnImageFromSet("mainMenu"), "main"));
        menu.add(new Button(350, 100, imageLoader.returnImageFromSet("newGame") , "new"));
        menu.add(new Button(350, 200, imageLoader.returnImageFromSet("loadGame"), "load"));
        menu.add(new Button(350, 300, imageLoader.returnImageFromSet("options") , "options"));

        runLoop();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);

        Toolkit.getDefaultToolkit().sync();
    }

    protected void doDrawing(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        g2d.scale(universalScalar, universalScalar);

        menu.draw(g2d, this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent me) {

        int mouseX = (int) (me.getX() / universalScalar);
        int mouseY = (int) (me.getY() / universalScalar);

        System.out.println(mouseX);
        System.out.println(mouseY);

        Button clickedOn = menu.listener(mouseX, mouseY);
        if(clickedOn != null) //If the user actually clicked on a button
        {
            switch (clickedOn.getText()) //React to a button click based on the buttons title.
                    //If you look closely you'll notice text near each button. This is its title
                    //In the future, we could simply use titles on top of a static background button sprite
                    //This way, andre doesn't need to make a new button for us each time we change menus around,
                    //And instead he can create finalized buttons later on in the iterations of the game
            {
                case "new":
                    Main.ElvenGameState = 2;
                    break;
                default:
                    Main.ElvenGameState = 3;
                    break;
            }
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