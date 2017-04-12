import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)

/**
 * A flag to plant on the moon.
 * 
 * @author Poul Henriksen
 * @version 1.0.1
 */
public class Flag extends Actor
{
    public Flag () 
    {
        System.out.println(message() );
    }
    
    public String message ()
    {
        String message = "You have landed";
        return message;
    }
}