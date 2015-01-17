/* bPlayCtrl.java
 * Edrian Irizarry
 * Project 4
 * This file controls the movement of the paddle.
 */
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Graphics;

public class bPlayCtrl implements KeyListener
{
    bPlay parent = null;
    public bPlayCtrl(bPlay parent)
    {
        super();
        this.parent = parent;
       
    }
    // Boiler plate keypressed
    public void keyPressed(KeyEvent e)
    {
        char key = Character.toLowerCase(e.getKeyChar());
        if (key == 'd')
            parent.moveRight();
        else if (key == 'a')
            parent.moveLeft();
        else if (key == 'w')
            parent.start();
        else if (key == 'x')
            parent.stop();
        else if (key == 'p')
            parent.pause();
        else if (key == 'r')
            parent.restart();
    }
    public void keyReleased(KeyEvent arg0)
    {
        parent.stopMoving();
    }
    public void keyTyped(KeyEvent arg0){}
}