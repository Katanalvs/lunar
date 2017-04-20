/*===========Lander==========*/
import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)

/**
 * A lunar lander
 * @author: Katana Willis
 * @version 1.0
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
    
    /** Bottom of lander (offset in pixels from center) */
    private int bottom = 15;
    
    /** Cheat-Mode activation */
    private boolean cheatModeActive = false;
    
    /** Invisble Rocket mode*/
    private GreenfootImage rocketInvisible;
    
    
    public Lander()
    {
        rocket = getImage();
        rocketWithThrust = new GreenfootImage("thrust.png");
        rocketWithThrust.drawImage(rocket, 0, 0);
        rocketInvisible = getImage();
    }       

    public void act()
    {
        processKeys();
        applyGravity();
        altitude += speed / speedFactor;
        setLocation(getX(), (int) (altitude));
        checkCollision();
        moon.alt(altitude);
        moon.speed(speed);
        if(isVisible())
        {
          rocketInvisible.setTransparency(0);
          setImage(rocketInvisible);   
        }else
        {
         setImage(rocketWithThrust);
        }
         
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
        
        if(Greenfoot.isKeyDown("down")) 
        {
            //speed += thrust;Abkürzuung
            speed = 18;
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
        
        if(Greenfoot.isKeyDown("c"))
        {
            
            if(cheatModeActive){
                cheatModeActive = false;
                moon.showcheatModeActive(cheatModeActive);
            }
            else{
                cheatModeActive = true;
                moon.showcheatModeActive(cheatModeActive);
            }
            
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
        
        if(cheatModeActive && getX() == 0)
        {
            setLocation(599, getY());
        }
        else if(cheatModeActive && getX() == 599)
        {
            setLocation ( 0, getY());
        }
        
        else if((getX() == 0  || getX() >= 599) && getY() > 0)
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
    
    private void cheat()
    {
        if(Greenfoot.isKeyDown("c") && (getX() == 0 ))//
        {
          setLocation(600, getY());
        }
        else if(Greenfoot.isKeyDown("c") && (getX() >= 599))
        {
          setLocation(0, getY());  
        }
    }
    
    public boolean isVisible()
    {
        if (altitude < 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}