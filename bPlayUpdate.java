/* bPlayUpdate.java
 * Edrian IRizarry
 * Project 4
 */
import javax.swing.JLabel;
public class bPlayUpdate extends JLabel
{
    int counter = 0;
    int lifeCount = 3;
    
    // A basic class for updating the top Panel with the score and lives.
    public bPlayUpdate()
    {
        super();
        counter = 0;
        lifeCount = 3;
        setText("Points: " + counter + " points" + " | Life: " + lifeCount);
    }  
    public void upCounter()
    {
        counter++;
        setText("Points: " + counter + " points" + " | Life: " + lifeCount);
    }
    public void resetCounter()
    {
        counter = 0;
        setText("Points: " + counter + " points" + " | Life: " + lifeCount);
    }
    public void lifeCountDown()
    {
    	if (lifeCount > 0)
    	{	
    		lifeCount--;
    		setText("Points: " + counter + " points" + " | Life: " + lifeCount);
    	}
    	else 
    		lifeCountStart();
    }
    public void lifeCountStart()
    {
    	lifeCount = 3;
    	setText("Points: " + counter + " points" + " | Life: " + lifeCount);
    }
}