/* bPlayGUI,java
 * Edrian Irizarry
 * Project 4
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Collections;
public class bPlayGUI extends JPanel implements Runnable
{
	ArrayList<String> scores = new ArrayList<String>();
	String[] strScore = new String[10];
	private Image img;
	private int lifeCount = 3;
    int MOVE_LEFT = -1;
    int MOVE_RIGHT = 1;
    int MOVE_NONE = 0;
    int PAUSE = 30;
    Graphics g;
    Color DARK_BLUE = new Color(0, 0, 100);
    
    int scoreCount = 0;
    
    // Dimensions of the Ball
    int ballWidth = 8;
    int ballHeight = 8;
    
    // Dimensions of the Paddle
    int paddleWidth = 55;
    int paddleHeight = 10;
    int bottomGap = 20;
    
    // Dimenions of the Bricks
    int brickWidth = 30;
    int brickHeight = 10;
    int brickGapHeight = 20;
    int brickGapWidth = 16;

    Thread thread = null;
    int pause = PAUSE;
    
    int x = 100;
    int move = MOVE_NONE;
    
    int bx = -1;
    int by = -1;
    int dx = -2;
    int dy = 3;
    
    boolean done = false;
    boolean gameOver = true;
    
    bPlay parent;
    
    // Each entry in the matrix represents a brick.
    bPlayColor bPlayColor[][] = new bPlayColor[6][8];
    
    public bPlayGUI(bPlay parent)
    {
        super();
        JOptionPane.showMessageDialog(null,"Breakout, written by Edrian Irizarry");
        this.parent = parent;
        init();  
    }
    
    // Makes the bricks to be drawn later.
    public void init()
    {
    	// Looping through the bPlayColor matrix to create new bricks
       for (int row = 0; row < bPlayColor.length; row++)
       {
           for (int col = 0; col < bPlayColor[row].length; col++)
           {
               bPlayColor[row][col] = new bPlayColor(brickToX(col), brickToY(row) );
               if ((row % 2) == 0)
               {
                   if ((col % 4) == 0)
                       bPlayColor[row][col].setX(false);
                   else if ((col % 4) == 2)
                       bPlayColor[row][col].setX(false);    
               }
               else
               {
                   if ((col % 4) == 0)
                       bPlayColor[row][col].setX(false);
                   else  if ((col % 4) == 2)
                       bPlayColor[row][col].setX(false);    
               }
           }
       }
    }
    
    // Makes a call to draw for each element of the 2d Brick array
    public void drawBricks(Graphics g)
    {
        for (int row = 0; row < bPlayColor.length; row++)
        {
            for (int col = 0; col < bPlayColor[row].length; col++)
            {
            	// draw(g) is a function from bPlayColor
                bPlayColor[row][col].draw(g);
            }
        }    
    }
    
    // 
    private void checkHitBrick()
    {
        for (int row = 0; row < bPlayColor.length; row++)
        {
            for (int col = 0; col < bPlayColor[row].length; col++)
            {
                int bcenterX = bx + ballWidth/2;
                int bcenterY = by + ballHeight/2;
                
                // If the brick at [row][col] has the center of the ball within it,
                // then set the box to invalid, add to the score, play the sound and
                // make the brick disappear.
                if (bPlayColor[row][col].contains(bcenterX, bcenterY))
                {
                    bPlayColor[row][col].setValid(false);
                    parent.advanceCounter();  
                    scoreCount++;
                    parent.soundPing();
                    dy *= (-1);
                    
                    if (bPlayColor[row][col].isX() )
                        this.faster();
                    if (bPlayColor[row][col].isX())
                        this.faster();
                    //if (bPlayColor[row][col].isX())
                    //    paddleWidth = (int)(1.5*paddleWidth);
                    //if (bPlayColor[row][col].isX())
                     //   paddleWidth = (int)(1.5*paddleWidth);
               
                }
            }
        }
    }
    
    // Checks if the paddle was hit by the ball and sends it out in the opposite direction
    private void checkHitPaddle()
    {
        int bcenterX = bx + ballWidth/2;
        int bcenterY = by + ballHeight/2;
        
        if ((dy > 0) &&
            (bcenterY >=  getHeight() - paddleHeight - bottomGap))
        {
            if ((x <= bcenterX) && (bcenterX < x + paddleWidth))
            {
                dy = -dy;
                dx += randomSign()*(int)(2*Math.random());
                parent.soundPing();  
            }
            else
            {
            	// if the ball doesn't hit the paddle.
            	parent.lifeCountDown();
            	lifeCount--;
            	stop();
            	if ( lifeCount == 0) // Initiates the game over sequence
            	{
            		parent.lifeCountStart();
            		parent.resetCounter();
            		stop();
            		try{ highScore(); }
            		catch(IOException e){}
            		init();
            	}	
            }
        }
    }
    
    // These next two functions determine brick placement and the gaps inbetween the bricks
    private int brickToY(int i)
    {
        return i * (brickHeight + brickGapHeight) + brickGapHeight;
    }
    private int brickToX(int i)
    {
        return i * (brickWidth + brickGapWidth) + brickGapWidth;
    }
    
    // This randomizes the bounce of the ball when it makes contact.
    int randomSign()
    {
        if (Math.random() > 0.5)
            return 1;
        else
            return -1;
    }
    
    // Restarting the game
    public void restart()
    {
        if (thread == null)
        {
            thread = new Thread(this);
            thread.start();
        }        
    } 
    
    // W = start the game
    public void start()
    {
        if (thread == null)
        {
            thread = new Thread(this);
            thread.start();
            bx = x + paddleWidth/2;
            by = getHeight() - paddleHeight - bottomGap;
            dx = randomSign()*(int)(4*Math.random()) + 1;
            dy = -2;
            pause = PAUSE;
            move = MOVE_NONE;
            gameOver = false;
        }
    }
    
    // X = stops/ends the game
    public void stop()
    {
        thread = null;
        move = MOVE_NONE;
        gameOver = true;
        repaint();
    } 
    
    // P = pauses the game
    public void pause()
    {
        thread = null;
        move = MOVE_NONE;        
    }
    
    public void faster()
    {
        if (pause >= 10)
            pause -= 15;
    }
    
    
    public void slower()
    {
        pause += 5;        
    } 
    
    
    public void run()
    {
        while (thread != null)
        {
            if (move == MOVE_LEFT)
                moveLeft();
            else if (move == MOVE_RIGHT)
                moveRight();
            
            bx += dx;
            by += dy;
       
            if (by <= 0) // Hitting the bottom of the window
            {
                //parent.soundPong();
                dy *= (-1);
            }
            else if ((bx <= 0) || (bx >= getWidth())) // Hitting the side edges of the panel
            {
                parent.soundPong();
                dx*= (-1);
            }
            else // If neither, constantly check if the paddle or brick is hit
            {
                checkHitBrick();
                checkHitPaddle();
            }
            repaint();
            try
            {
                thread.sleep(pause);
            }
            catch (InterruptedException e)
            {
                move = MOVE_NONE;
                e.printStackTrace();
            }
        }
    }    
    public void stopMoving()
    {
        move = MOVE_NONE;
    } 
    
    // Moving the paddle right, x determines the speed at which it moves
    public void moveRight()
    {
        if (x < getWidth() - paddleWidth)
        {
            move = MOVE_RIGHT;
            x += 8;
            repaint();
        }
        else
            move = MOVE_NONE;
    }
    
    // Moving the paddle left. X determines the speed at which it moves.
    public void moveLeft()
    {
        if (x >= 0)
        {
            move = MOVE_LEFT;
            x -= 8;
            repaint();
        }
        else
            move = MOVE_NONE;
    }
    
    // Adds a rectangular paddle to the bottom of the panel
    public void drawPaddle(Graphics g)
    {
        g.setColor(DARK_BLUE);
        g.drawRect(x, getHeight()-paddleHeight-bottomGap, paddleWidth, paddleHeight);
        g.setColor(Color.BLUE);
        g.fillRect(x, getHeight()-paddleHeight-bottomGap, paddleWidth, paddleHeight);
    }
    
    // Draws a small ball right above the paddle(at the start)
    public void drawBall(Graphics g)
    {
        if (gameOver)
        {
            bx = x + paddleWidth/2 - ballWidth/2;
            by = getHeight()-paddleHeight-bottomGap-ballHeight;;
        }
        g.setColor(Color.RED);
        g.fillOval(bx, by, ballHeight, ballWidth);
    }
    
    // Draws the brick, paddle and ball.
    // The image additions were for school spirit/weird extra credit. For some reason,
    // Ted Pawlicki gave extra credit for squirrel related drawings/pics.
    public void paintComponent(Graphics g)  
    {   	
        super.paintComponent(g); 
        ImageIcon icon = new ImageIcon("URLogo.png");
        img = icon.getImage();
        g.drawImage(img,0,0,getSize().width,getSize().height,this);
        drawBricks(g);
        drawPaddle(g);
        drawBall(g);
        if ( done )
        {
            ImageIcon icon2 = new ImageIcon("zombiesquirrel.png");
            img = icon2.getImage();
            g.drawImage(img,0,0,getSize().width,getSize().height,this);
        }
    }
    // This asks for the user's name and saves his score into a file. It then shows the top 10.
    public String[] readScores( String [] x) throws IOException
    {
    	
    	BufferedReader br = new BufferedReader(new FileReader("highscore.txt"));
    	String sCurrentLine;
    	// If there is a next line it is added to an ArrayList.
		while ((sCurrentLine = br.readLine()) != null) 
		{
			scores.add(sCurrentLine);	
			for (int i = 0; i <x.length; i++)
				 x[i] = sCurrentLine;
		}
		br.close();
		return x;
    }
    
    // End of the game high score popup
    public void highScore() throws IOException
    {
    	done = true;
		// Asking the user for input.
    	JOptionPane.showMessageDialog(null,"Game Over!");
    	String name = JOptionPane.showInputDialog("Enter your intitials");
    	PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("highscore.txt", true)));
    	out.println(scoreCount + "\t" + name);
    	out.close();
    	// Reading from the file
    	BufferedReader br = new BufferedReader(new FileReader("highscore.txt"));
    	String sCurrentLine;
    	// If there is a next line it is added to an ArrayList.
		while ((sCurrentLine = br.readLine()) != null) 
		{
			scores.add(sCurrentLine);	
		
		}
		br.close();

    	// These next lines sort them then reverse the order. The largest number will be printed first.
    	Collections.sort(scores);	
    	Collections.reverse(scores);
		for(int i = 0 ; i < scores.size() ;i++)
		{
			System.out.println(i+1 + ") " + scores.get(i));
		}
    }
    public Dimension getPreferredSize()
    {
        return new Dimension(400, 400);
    }
}