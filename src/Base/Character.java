package Base;

import java.awt.image.BufferedImage;

public class Character extends Sprite
{
    private CharacterStats stats;

    public Character(BufferedImage image, CharacterStats setStats)
    {
        super(0, 0, 0, image);
        loadStats(setStats);
    }

    public Character(int x, int y, BufferedImage image, CharacterStats setStats)
    {
        super(x, y, 0, image);
        loadStats(setStats);
    }

    public void loadStats(CharacterStats setStats)
    {
        this.stats = setStats;
    }
}
