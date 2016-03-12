package GUI;

public class GUIText
{
    //More of a data structure than a class, considering its only interface is constructors. Don't worry.
    public int x;
    public int y;
    public String field;

    public GUIText()
    {
        x = 0;
        y = 0;
    }

    public GUIText(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public GUIText(String s)
    {
        this.x = 0;
        this.y = 0;
        this.field = s;
    }
}
