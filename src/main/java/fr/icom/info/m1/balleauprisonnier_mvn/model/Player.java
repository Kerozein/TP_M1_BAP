package fr.icom.info.m1.balleauprisonnier_mvn.model;


import fr.icom.info.m1.balleauprisonnier_mvn.view.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Rotate;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import java.lang.Math;

/**
 * 
 * Classe gerant un joueur
 *
 */
public class Player 
{
	  double x;       // position horizontale du joueur
	  final double y; 	  // position verticale du joueur
	  double angle = 90; // rotation du joueur, devrait toujour être en 0 et 180
	  double step;    // pas d'un joueur


	  
	  /**
	   * Constructeur du Joueur
	   * @param xInit position horiontale
	   * @param yInit position verticale
	   */
	  Player(int xInit, int yInit)
	  {
		// Tous les joueurs commencent au centre du canvas, 
	    x = xInit;               
	    y = yInit;

		angle = 0;

	    // Tous les joueurs ont une vitesse aleatoire entre 0.0 et 1.0
        step = Math.random();
	    
	  }

	  Player(int xInit, int yInit, float speed)
	  {
		// Tous les joueurs commencent au centre du canvas, 
	    x = xInit;               
	    y = yInit;
	    
	    angle = 0;

		step = speed;
	    
	  }

	  
	  /**
	   *  Deplacement du joueur vers la gauche, on cantonne le joueur sur le plateau de jeu
	   */

	  public void moveLeft()
	  {	    
	    if (x > 10 && x < 520) 
	    {
		    x -= step;
	    }
	  }

	  /**
	   *  Deplacement du joueur vers la droite
	   */
	  public void moveRight()
	  {
	    if (x > 10 && x < 520) 
	    {
		    x += step;
	    }
	  }

	  
	  /**
	   *  Rotation du joueur vers la gauche
	   */
	  public void turnLeft()
	  {
	    if (angle > 0 && angle < 180) 
	    {
	    	angle += 1;
	    }
	    else {
	    	angle += 1;
	    }

	  }

	  
	  /**
	   *  Rotation du joueur vers la droite
	   */
	  public void turnRight()
	  {
	    if (angle > 0 && angle < 180) 
	    {
	    	angle -=1;
	    }
	    else {
	    	angle -= 1;
	    }
	  }

	  /**
	   *  Deplacement en mode boost
	   */
	  void boost() 
	  {
	    x += step*2;
	  }

	public double getAngle() {
		return angle;
	}

	public double getY() {
		return y;
	}

	public double getX() {
		return x;
	}
}
