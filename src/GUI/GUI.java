package GUI;

import Base.SpriteLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public class GUI
{
    public Vector<GUIElement> elements;
    private SpriteLoader spriteLoader;

    public GUI() {
        elements = new Vector<>();
        spriteLoader = new SpriteLoader();
    }

    public void add(GUIElement element) {
        elements.add(element);
    }

    //This is so that we don't have to draw GUI elements manually
    //Basically, GUI is just a container for now
    //It'll be more than that if/when GUIElements have the same interface (Right now GUIElements are very minimal, for example menu is < 50 lines)
    public void draw(Graphics2D graphics, JPanel panel) {
        for(GUIElement g : elements)
        {
            g.draw(graphics, panel); //Let the sprite be responsible for drawing itself, not BasePanel
            // -- technically Basepanel is still the one drawing it, but this is easier to understand:
            //This way, we can create abstractions on top of Sprite,
            //For example if you have an object consisting of multiple sprites, you just call draw on it rather than mapping over and drawing each individually from basepanel
        }
    }


}
