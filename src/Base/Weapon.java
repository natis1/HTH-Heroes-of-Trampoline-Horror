package Base;

import java.awt.image.BufferedImage;


public class Weapon extends Sprite{

    private WeaponStats stats;

    public Weapon(BufferedImage image, WeaponStats setStats)
    {
        super(0, 0, 0, image);
        loadStats(setStats);
    }

    public Weapon(int x, int y, BufferedImage image, WeaponStats setStats)
    {
        super(x, y, 0, image);
        loadStats(setStats);
    }

    public void loadStats(WeaponStats setStats)
    {
        this.stats = setStats;
    }
}
