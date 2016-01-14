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
	//Stores score and gamestate in one int
	public static int ElvenGameState = 0;
	public static int ElvenSoulsStolen = 0;
	public static int ElvenGameDifficulty = 0;
	
	public static int ElvenXResolution = 0;
	public static int ElvenYResolution = 0;
	public static int ElvenFramerate = 0;
	
	public static char ElvenUpgradeFirerate = 0;
	public static char ElvenUpgradeMovement = 0;
	public static char ElvenUpgradeTracking = 0;
    
    public static void main(String[] args)
	{
        EventQueue.invokeLater(
				new Runnable() {
						@Override
						public void run()
						{

							//BOW TO THE VOID OR BE CONSUMED BY IT
							WindowLoader ElvenMasterRulesOverPunyHumans = new WindowLoader();

						}
				}
		);
    }
}