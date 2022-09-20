package fr.icom.info.m1.balleauprisonnier_mvn;


import java.util.ArrayList;

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
public class Field extends Canvas {
	
	/** Equipes */
	ArrayList<Player> equipe1 = new ArrayList<Player>();
	ArrayList<Player> equipe2 = new ArrayList<Player>();

	/** Couleurs possibles */
	String[] colorMap = new String[] {"blue", "green", "orange", "purple", "yellow"};
	/** Tableau traçant les evenements */
    ArrayList<String> input = new ArrayList<String>();
    

    final GraphicsContext gc;
    final int width;
    final int height;
    
    /**
     * Canvas dans lequel on va dessiner le jeu.
     * 
     * @param scene Scene principale du jeu a laquelle on va ajouter notre Canvas
     * @param w largeur du canvas
     * @param h hauteur du canvas
     */
	public Field(Scene scene, int w, int h) 
	{
		super(w, h); 
		width = w;
		height = h;
		
		/** permet de capturer le focus et donc les evenements clavier et souris */
		this.setFocusTraversable(true);
		
        gc = this.getGraphicsContext2D();
        
        /** On initialise le terrain de jeu */
    	equipe1.add(new Player(gc, colorMap[0], w/2, h-50, "bottom"));
    	equipe1.get(0).display();

    	equipe2.add(new Player(gc, colorMap[1], w/2, 20, "top"));
    	equipe2.get(0).display();


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
	        	for (int i = 0; i < equipe1.size(); i++) 
	    	    {
	        		if (i==0 && input.contains("LEFT"))
	        		{
	        			equipe1.get(0).moveLeft();
	        		} 
	        		if (i==0 && input.contains("RIGHT")) 
	        		{
	        			equipe1.get(0).moveRight();	        			
	        		}
	        		if (i==0 && input.contains("UP"))
	        		{
	        			equipe1.get(0).turnLeft();
	        		} 
	        		if (i==0 && input.contains("DOWN")) 
	        		{
	        			equipe1.get(0).turnRight();	        			
	        		}
	        		if (i==0 && input.contains("SPACE")) 
	        		{
	        			equipe1.get(0).shoot();	        			
	        		}

	        		equipe1.get(i).display();
	    	    }

				for (int i = 0; i < equipe2.size(); i++) 
	    	    {
	        		if (i==0 && input.contains("Q"))
	        		{
	        			equipe2.get(0).moveLeft();
	        		} 
	        		if (i==0 && input.contains("D")) 
	        		{
	        			equipe2.get(0).moveRight();	        			
	        		}
	        		if (i==0 && input.contains("Z"))
	        		{
	        			equipe2.get(0).turnLeft();
	        		} 
	        		if (i==0 && input.contains("S")) 
	        		{
	        			equipe2.get(0).turnRight();	        			
	        		}
					if (i==0 && input.contains("ENTER")) 
	        		{
	        			equipe2.get(0).shoot();	        			
	        		}

					equipe2.get(i).display();
	    	    }
	    	}
	     }.start(); // On lance la boucle de rafraichissement 
	     
	}


	public ArrayList<Player> getEquipe1(){
		return equipe1;
	}

	public ArrayList<Player> getEquipe2(){
		return equipe2;
	}	
}
