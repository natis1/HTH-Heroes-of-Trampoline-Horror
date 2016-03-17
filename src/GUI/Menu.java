package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

public class Menu extends GUIElement{

    private ArrayList<Button> buttons;

    public Menu(BufferedImage image) {
        super(image);
        buttons = new ArrayList<>();
    }
    private static final int default_offset = 30;

    public Menu(int x, int y, BufferedImage image) {
        super(x, y, image);
        init();
    }

    private void init() {
        buttons = new ArrayList<>();
    }

    public void add(Button b) {
        int offset = b.getHeight() + default_offset;
        b.setX(this.getX() + default_offset);
        if(buttons.size() > 0) {
            b.setY(buttons.get(buttons.size() - 1).getY() + offset);
        }
        else {
            b.setY(this.getY() + offset);
        }
        buttons.add(b);
    }

    public void add(Button b, int offset) {
        b.setX(this.getX() + offset);
        if(buttons.size() > 0) {
            b.setY(buttons.get(buttons.size() - 1).getY() + offset);
        }
        else {
            b.setY(this.getY() + offset);
        }
        buttons.add(b);
    }

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

    @Override
    public void draw(Graphics2D graphics, ImageObserver observer) {
        drawBackground(graphics, observer);
        for (Button b : buttons)
        {
            b.draw(graphics, observer);
        }
    }
}
