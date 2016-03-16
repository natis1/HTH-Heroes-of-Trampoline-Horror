package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Menu extends GUIElement{

    private ArrayList<Button> buttons;

    public Menu(BufferedImage image) {
        super(image);
        buttons = new ArrayList<>();
    }

    public Menu(int x, int y, BufferedImage image) {
        super(x, y, image);
        buttons = new ArrayList<>();
    }

    public void add(Button b){buttons.add(b);}
    public void clear(){buttons.clear();}

    //Yo, Important:
    //This is only used once it is known the mouse click is within the menu space
    public Button listener(int x, int y) {
        for (Button b : buttons)
        {
            if (b.contains(x, y)) return b;
        }

        return null;
    }

    //Method doesn't override anything
    public void draw(Graphics2D graphics, JPanel panel) {
        drawBackground(graphics, panel);
        for (Button b : buttons)
        {
            b.draw(graphics, panel);
        }
    }
}
