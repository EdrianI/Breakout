/* bPlayColor.java
 * Edrian Irizarry
 * Project 4
 * This file displays the Bricks of the game
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class bPlayColor
{
    public static int WIDTH = 40;
    public static int HEIGHT = 10;
    
    // The color selection for Bricks. It's Blue and Yellow because those are 
    // University of Rochester's colors.
    public static Color COLORS[] = {Color.blue, Color.yellow};
    public static Color X = Color.red;
    
    Color color;
    Rectangle r;
    
    // Validaty is testing if the ball has hit a brick.
    boolean valid;
    
    // Default visible, on hit disappear.
    boolean visible;
    boolean x;
    
    // The brick constructor. It takes an element in a 2d array and randomly chooses a color.
    // the overloaded constructor uses the graphics library to create a triangle 
    public bPlayColor(int x, int y)
    {
        this(x, y, COLORS[(int)(COLORS.length*Math.random())]);
    }
    public bPlayColor(int x, int y, Color color)
    {
        super();
        this.r = new Rectangle(x, y, WIDTH, HEIGHT);
        this.color = color;
        this.valid = true;
        this.visible = true;
        this.x = false;
    }
    
    
    public void draw(Graphics g)
    {
        if ((valid) && (visible))
        {
            if (x)
            	g.setColor(X);
            else           	
                g.setColor(color);
            g.fill3DRect(r.x, r.y, r.width, r.height, true); 
            g.draw3DRect(r.x, r.y, r.width, r.height, true);
        }
    } 
    
    public boolean contains(int x, int y)
    {
        if (valid)
            return r.contains(x, y);
        else
            return false;
    }
    
    
    public boolean isVisible()
    {
        return visible;
    } 
    public void setValid(boolean valid)
    {
        this.valid = valid;
    }   
    public boolean isValid()
    {
        return valid;
    }
    public void setX( boolean x )
    {
    	this.x = x;
    }
    public boolean isX()
    {
    	return x;
    }
}