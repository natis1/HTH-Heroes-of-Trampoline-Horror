package Base.Overworld;

public class CharacterStats
{
    public String name;
    public int health;

    public CharacterStats (String setName, int setHealth)
    {
        name = setName;
        health = setHealth;
    }

    public int getHealth()
    {
        return health;
    }
}
