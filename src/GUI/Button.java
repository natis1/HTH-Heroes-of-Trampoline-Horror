package GUI;
import Base.Sprite;

import java.awt.image.BufferedImage;

public class Button extends Sprite
{
    public Button(int x, int y, double angle, BufferedImage image)
    {
        super(x, y, angle, image);
    }

    public boolean contains(int x, int y)
    {
        return    ((x < this.x + getHeight())
                && (x > this.x)
                && (x < this.y + getHeight())
                && (y > this.y));
    }
}
