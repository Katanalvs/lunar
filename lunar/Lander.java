/*===========Lander==========*/
import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)

/**
 * A lunar lander
 *
 * @author Poul Henriksen
 * @author Michael Kölling
 * 
 * @version 1.1
 */
public class Lander extends Actor
{
    /** Current speed */
    private double speed = 0;
     
    /** Allowed speed for landing speed */
    private double MAX_LANDING_SPEED = 30;
    
    /** Power of the rocket */
    private double thrust = -3;
    
    /** The location */
    private int altitude;
    
    /** The speed is divided by this. */
    private double speedFactor = 10;
    
    /** Rocket image without thrust */
    private GreenfootImage rocket;
   
    /** Rocket image with thrust */
    private GreenfootImage rocketWithThrust;
    
    /** Moon we are trying to land on */
    private Moon moon;    
    
    /** Bottom of lander (offset in pixels from centre) */
    private int bottom = 15;
    
    
    
    
    public Lander()
    {
        rocket = getImage();
        rocketWithThrust = new GreenfootImage("thrust.png");
        rocketWithThrust.drawImage(rocket, 0, 0);
    }       

    public void act()
    {
        processKeys();
        applyGravity();
        altitude += speed / speedFactor;
        setLocation(getX(), (int) (altitude));
        checkCollision();
        moon.alt(altitude);
    }

    /**
     * Lander has been added to the world.
     */
    public void addedToWorld(World world) 
    {
        moon = (Moon) world;        
        altitude = getY();
    }
    
    /**
     * Handle keyboard input.
     */
    private void processKeys() 
    {
        String key = Greenfoot.getKey();
        
        if(Greenfoot.isKeyDown("down")) 
        {
            //speed += thrust;Abkürzuung
            speed = speed+thrust;
            setImage(rocketWithThrust);
            //System.out.println("Down");
        }
        
        if (Greenfoot.isKeyDown("left"))
        {
            move (-15);
            setImage(rocketWithThrust);
            //System.out.println("Left");
        }
        
        if(Greenfoot.isKeyDown("right"))
        {
            move (15);
            setImage(rocketWithThrust);
            //System.out.println("Right");
        }
        
        if (Greenfoot.isKeyDown("up"))
        {
            speed -= 10;
            setImage(rocketWithThrust);
            //System.out.println("Up");
        }
        
    }
   
    
    /**
     * Let the gravity change the speed.
     */
    private void applyGravity() 
    {
        speed += moon.getGravity();
    }
    
    /**
     * Whether we have touched the landing platform yet.
     */
    private boolean isLanding() 
    {
        Color colorBelow = moon.getColorAt(getX(), getY() + bottom);
        return (speed < MAX_LANDING_SPEED) && !colorBelow.equals(moon.getSpaceColor());
    }
    
    /** 
     * Is the lander exploding?
     */
    private boolean isExploding() 
    {
        Color colorBelow = moon.getColorAt(getX(), getY() + bottom);
        
        if ((getX() == 0  || getX() >= 599) && getY() > -1)
        {
            moon.addObject(new Explosion(), getX(), getY());
            moon.showGameIsOver(true);
        }
        return (speed > MAX_LANDING_SPEED) && !colorBelow.equals(moon.getSpaceColor());
    }
    
    /**
     * Check if we are colliding with anything and take appropiate action.
     */
    private void checkCollision() 
    {
        
        if (isLanding()) {
            setImage(rocket);
            moon.addObject(new Flag(), getX(), getY());
            moon.showGameIsOver(false);
            Greenfoot.stop();
        } 
        else if (isExploding()) {
            moon.addObject(new Explosion(), getX(), getY());
            moon.showGameIsOver(true);
            moon.removeObject(this);
        }  
    }

   
}
