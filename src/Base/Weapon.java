package Base;

import java.awt.image.BufferedImage;

class WeaponStats
{
    public int damage;
    public int durability;
}

public class Weapon extends Sprite{

    private WeaponStats stats;

    public Weapon(BufferedImage image)
    {
        super(0, 0, 0, image);
    }

    public Weapon(int x, int y, BufferedImage image)
    {
        super(x, y, 0, image);
    }

    public void loadStats(WeaponStats setStats)
    {
        this.stats = setStats;
    }
}
