/* bPlay.java
 * Edrian Irizarry
 * Project 4
 */
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
public class bPlay extends JFrame
{
    bPlayCtrl controller = null;
    bPlayUpdate update = null;
    bPlayGUI gui = null;
    
    // This constructor brings everything together. Call it from a Main function to start.
    public bPlay()
    {
        super("Breakout");     
        //playSound();
        controller = new bPlayCtrl(this);
        update = new bPlayUpdate();
        gui = new bPlayGUI(this);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add("Center", gui);
        getContentPane().add("North", update);
        
        validate();
        pack();
        setVisible(true);
        
        this.addKeyListener(controller); 
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
    }
    
    //Setting up the sound for hitting a break(ping) and hitting the paddle(pong)
    public void playSound(String soundName)
    {
    	try
    	{
    		AudioInputStream audio = AudioSystem.getAudioInputStream(getClass().getResource(soundName+".wav"));
    		Clip clip = AudioSystem.getClip( );
    		clip.open(audio);
    		clip.start( );
    	}
    	catch(Exception ex)
    	{
    		System.out.println("Error with playing sound.");
    		ex.printStackTrace( );
    	}
    }
    
    // Brick hit
    public void soundPing()
    {
    	playSound("ping");
    }
    
    // Paddle hit
    public void soundPong()
    {
    	playSound("pong");
    }
    public void start()
    {
        gui.start();
    }
    public void stop()
    {
        gui.stop();
    }
    public void moveRight()
    {
        gui.moveRight();
    }
    
    public void moveLeft()
    {
        gui.moveLeft();
    }
    
    public void stopMoving()
    {
        gui.stopMoving();
    }
    
    public void advanceCounter()
    {
        update.upCounter();
    } 
    public void resetCounter()
    {
        update.resetCounter();
    }
    public void lifeCountDown()
    {
    	update.lifeCountDown();
    }
    public void lifeCountStart()
    {
    	update.lifeCountStart();
    }
    public void pause()
    {
        gui.pause();
    }
    public void restart()
    {
        gui.restart();
    }
    public static void main(String args[])
    {
        bPlay game = new bPlay();
    }
}