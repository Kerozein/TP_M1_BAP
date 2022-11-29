package fr.icom.info.m1.balleauprisonnier_mvn.model;


import java.lang.Math;

public class Player 
{
	  private double x;
	  private final double y;
	  private double direction;
	  private final double step;
	  private boolean haveBall = false;


	  
	  /** Constructor with a random speed for players (0.5 - 1)
	   * @param xInit x-axis pos
	   * @param yInit y-axis pos
	   */
	  Player(int xInit, int yInit)
	  {
	    x = xInit;               
	    y = yInit;
		direction = 0;
        step = 0.5 + Math.random() * (1 - 0.5);
	    
	  }

	  Player(int xInit, int yInit, float speed)
	  {
	    x = xInit;               
	    y = yInit;
	    direction = 0;
		step = speed;
	  }


	  public void moveLeft()
	  {	    
	    if (x > 10 && x < 520) 
	    {
		    x -= step;
	    }
	  }

	  public void moveRight()
	  {
	    if (x > 10 && x < 520) 
	    {
		    x += step;
	    }
	  }


	  public void turnLeft()
	  {
	    if (direction > 0 && direction < 180)
	    {
	    	direction += 1;
	    }
	    else {
	    	direction += 1;
	    }

	  }


	  public void turnRight()
	  {
	    if (direction > 0 && direction < 180)
	    {
	    	direction -=1;
	    }
	    else {
	    	direction -= 1;
	    }
	  }

	public double getDirection() {
		return direction;
	}
	public double getY() {
		return y;
	}
	public double getX() {
		return x;
	}
	public void setBall(boolean b) {this.haveBall = b;}
	public boolean haveBall() {return this.haveBall;}

}
