import java.util.ArrayList;

//Ideally there will be a hashmap containing all of the Chunks - or, if we're adventurous, have the chunks manage themselves?
//(This has the benefit of only doing collision detection within chunks -- also huge advantages to multithreading)
//Also no singletons.... :P

public class Chunk
{
    private int x;
    private int y;
    private int width;
    private int height;

    private ArrayList<Sprite> sprites; //Can be split up in the future: ex.. Environment Objects, Enemies ...

    Sprite player; //Likely because the player will be  treated differently than the other sprites

    int getPreHash() {
        return x * 10000 + y;
    }

    public void update() {
        for(Sprite s : sprites)
        {
            //s.update();
        }
    }

    public void draw() {
        for(Sprite s : sprites)
        {
            //s.draw(); Assuming that s is responsible for drawing itself
        }
    }
}
