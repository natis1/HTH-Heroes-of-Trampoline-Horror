package GUI;

public class Text
{
    //More of a data structure than a class, considering its only interface is constructors. Don't worry.
    public int x;
    public int y;
    public String field;

    public Text()
    {
        x = 0;
        y = 0;
    }

    public Text(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public Text(int x, int y, String s)
    {
        this.x = x;
        this.y = y;
        this.field = s;
    }
}
