import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)

/**
 * A moon.
 * 
 * @author Poul Henriksen 
 * @author Michael Kölling
 * @editor Katana Wiilis
 * @version 1.1
 */
public class Moon extends World
{
    /** Gravity on the moon */
    private double gravity = 1.6;
    
    /** Color of the landing platform */
    private Color landingColor = Color.WHITE;
    
    /** Color of the space */
    private Color spaceColor = Color.BLACK;
    
    
    
    public Moon() 
    {
        super(600,600,1);
        addObject(new Lander(), 300, 300);
        Explosion.initialiseImages();
    }
    
    /** 
     * Gravity on the moon  
     *
     */
    public double getGravity()  {
        return gravity;
    }
    
    
    /**
     * Color of the landing platform 
     * 
     */
    public Color getLandingColor() {
        return landingColor;
    }    
    
    
    /**
     * Color of the space 
     * 
     */
    public Color getSpaceColor() {
        return spaceColor;
    }
    
    public void speed(double svalue)
    {
        String svalueasString = "Speed: " + String.valueOf(Math.round(svalue));
        showText (svalueasString, 300, 200 );
    }
    
    public void alt(int altitude)
    {
        String altitudeasString = "Altitude: " + String.valueOf(altitude);
        showText (altitudeasString, 300, 100 );
    }
    
    public void showGameIsOver(boolean isFailed){
        if(isFailed){
            showText("GAMEOVER", 300, 250);
        } else {
            showText("Landed Safely", 100, 50);
        }
    }
    
    public void showcheatModeActive ( boolean cheatModeActive)
    {
        if(cheatModeActive)
        {
            showText("Cheat Mode", 100, 100);
        }
        else
        {
            showText("", 100, 100);
        }
        
    }
    
}