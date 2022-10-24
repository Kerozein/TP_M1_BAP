package fr.icom.info.m1.balleauprisonnier_mvn.controller;


import java.util.ArrayList;

import fr.icom.info.m1.balleauprisonnier_mvn.model.HumanPlayer;
import fr.icom.info.m1.balleauprisonnier_mvn.model.IAPlayer;
import fr.icom.info.m1.balleauprisonnier_mvn.model.Player;
import fr.icom.info.m1.balleauprisonnier_mvn.view.PlayerView;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

/**
 * Classe gerant le terrain de jeu.
 * 
 */
public class GameController extends Canvas {
	
	/** Equipes */
	ArrayList<Player> equipe1;
	ArrayList<PlayerView> equipe1View;
	ArrayList<Player> equipe2;
	ArrayList<PlayerView> equipe2View;

	/** Tableau traçant les evenements */
    ArrayList<String> input = new ArrayList<String>();
    

    public final GraphicsContext gc;
    final int width;
    final int height;
    
    /**
     * Canvas dans lequel on va dessiner le jeu.
     *
     * @param w largeur du canvas
     * @param h hauteur du canvas
     */
	public GameController(int w, int h, ArrayList<Player> e1, ArrayList<Player> e2, ArrayList<PlayerView> vE1, ArrayList<PlayerView> vE2)
	{
		super(w, h); 
		width = w;
		height = h;
		equipe1 = e1;
		equipe2 = e2;
		equipe1View = vE1;
		equipe2View = vE2;
		
		/** permet de capturer le focus et donc les evenements clavier et souris */
		this.setFocusTraversable(true);
		
        gc = this.getGraphicsContext2D();



	    /** 
	     * Event Listener du clavier 
	     * quand une touche est pressee on la rajoute a la liste d'input
	     *   
	     */
	    this.setOnKeyPressed(
	    		new EventHandler<KeyEvent>()
	    	    {
	    	        public void handle(KeyEvent e)
	    	        {
	    	            String code = e.getCode().toString();
	    	            // only add once... prevent duplicates
	    	            if ( !input.contains(code) )
	    	                input.add( code );
	    	        }
	    	    });

	    /** 
	     * Event Listener du clavier 
	     * quand une touche est relachee on l'enleve de la liste d'input
	     *   
	     */
	    this.setOnKeyReleased(
	    	    new EventHandler<KeyEvent>()
	    	    {
	    	        public void handle(KeyEvent e)
	    	        {
	    	            String code = e.getCode().toString();
	    	            input.remove( code );
	    	        }
	    	    });
	    
	    /** 
	     * 
	     * Boucle principale du jeu
	     * 
	     * handle() est appelee a chaque rafraichissement de frame
	     * soit environ 60 fois par seconde.
	     * 
	     */
	    new AnimationTimer() 
	    {
	        public void handle(long currentNanoTime)
	        {	 
	            // On nettoie le canvas a chaque frame
	            gc.setFill( Color.LIGHTGRAY);
	            gc.fillRect(0, 0, width, height);
	        	
	            // Deplacement et affichage des joueurs
				for (int i=0 ; i<equipe1.size() ; i++ ) {
					Player p = equipe1.get(i);
					PlayerView pv = equipe1View.get(i);
					if(p instanceof HumanPlayer)
					{
						if (input.contains("LEFT")) {
							p.moveLeft();
							pv.spriteAnimate(p.getX());
						}
						if (input.contains("RIGHT")) {
							p.moveRight();
							pv.spriteAnimate(p.getX());
						}
						if (input.contains("UP")) {
							p.turnLeft();
							pv.spriteAnimate(p.getX());
						}
						if (input.contains("DOWN")) {
							p.turnRight();
							pv.spriteAnimate(p.getX());
						}
						if (input.contains("SPACE")) {
							p.shoot();
						}
						pv.display(gc,p.getX(),p.getY(),p.getAngle());
					}

				}

				for (int i=0 ; i<equipe2.size() ; i++ ) {
					Player p = equipe2.get(i);
					PlayerView pv = equipe2View.get(i);
					if(p instanceof HumanPlayer)
					{
						if (input.contains("Q")) {
							p.moveLeft();
							pv.spriteAnimate(p.getX());
						}
						if (input.contains("D")) {
							p.moveRight();
							pv.spriteAnimate(p.getX());
						}
						if (input.contains("Z")) {
							p.turnLeft();
							pv.spriteAnimate(p.getX());
						}
						if (input.contains("S")) {
							p.turnRight();
							pv.spriteAnimate(p.getX());
						}
						if (input.contains("ENTER")) {
							equipe2.get(0).shoot();

						}
						pv.display(gc,p.getX(),p.getY(),p.getAngle());
					}

				}
	    	}
	     }.start(); // On lance la boucle de rafraichissement 
	     
	}

	public GraphicsContext getGc() {
		return gc;
	}
}
