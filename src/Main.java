import java.awt.*;

/**
 * 
 * TODO: THIS GAME WILL BE THE BEST PC PORT SINCE AC:Unity
 * 
 * 
 * 
 * SO CINEMATIC
 * 
 * 
 * 
 */

public class Main
{
    //Why not move these to a settings class (or something similar)? -Lucas

    public static int ElvenGameState = 0;

    public static int ElvenFramerate = 0;
    public static int ElvenWorldSeed = 0;
    public static int ElvenWindowedResolution = 0;

    
    public static void main(String[] args)
    {
        EventQueue.invokeLater(
                new Runnable() {
                        @Override
                        public void run()
                        {

                            //BOW TO THE VOID OR BE CONSUMED BY IT

                            //Honestly I am so glad nobody has noticed this or the window loader name.

                            //PS since its an elf a Malzahar quote is kinda unfitting, maybe something like:
                            //"A solid giggle should do the trick!"

                            //hmmm..
                            WindowLoader ElvenMasterRulesOverPunyHumans = new WindowLoader();

                        }
                }
        );
    }
}