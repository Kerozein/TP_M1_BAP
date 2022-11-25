package fr.icom.info.m1.balleauprisonnier_mvn.controller;


import javafx.geometry.Rectangle2D;

import java.util.ArrayList;
import fr.icom.info.m1.balleauprisonnier_mvn.model.*;
import fr.icom.info.m1.balleauprisonnier_mvn.view.PlayerView;
import fr.icom.info.m1.balleauprisonnier_mvn.view.ProjectileView;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

/**
 * Classe gerant le terrain de jeu.
 * 
 */
public class GameController extends Canvas {
	/*  TODO: Systeme de rammassage de balle
	 *  TODO: Trouver pourquoi filename.txt se recrée en boucle
	 *  TODO: Faire les vues (SCORE + BIND)
	 *  TODO: Attribuer une balle aléatoirement + bind sur une touche
	 *  TODO: Plusieurs joueurs tire la balle sans qu'elle ait touché le sol
	*/

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

	private Projectile projectile = null;
	private final ProjectileView projView = new ProjectileView("assets/ball.png");
    
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
				Image projImg = projView.getImg();
				Rectangle2D projBoundary;

	            // On nettoie le canvas a chaque frame
	            gc.setFill( Color.LIGHTGRAY);
	            gc.fillRect(0, 0, width, height);

				if(projectile != null){

					//Affichage de la balle
					projView.display(gc, projectile.getX(), projectile.getY());

					if(isOOB(projectile)){
						projectile.setBallMoving(false);
					}
					if(projectile.isBallMoving()) {
						projectile.moveProjectile();
						projBoundary = new Rectangle2D(projectile.getX(), projectile.getY(), projImg.getWidth(), projImg.getHeight());

						//Check de la collision entre la balle et un joueur
						Player p;
						if ((p = checkCollisionOfTeam(projBoundary, equipe1)) != null) {
							int index = equipe1.indexOf(p);
							equipe1.remove(index);
							equipe1View.get(index).disable();
							equipe1View.remove(index);
							projectile.setBallMoving(false);
						}

						if ((p = checkCollisionOfTeam(projBoundary, equipe2)) != null) {
							int index = equipe2.indexOf(p);
							equipe2.remove(index);
							equipe2View.get(index).disable();
							equipe2View.remove(index);
							projectile.setBallMoving(false);
						}
					}
				}

				// Deplacement et affichage des joueurs
				for (int i=0 ; i<equipe1.size() ; i++ ) {
					Player p = equipe1.get(i);
					PlayerView pv = equipe1View.get(i);

					// Joueur IA de l'equipe 1
					if(p instanceof IAPlayer){
						if((int) Math.round( Math.random() ) == 1){
							p.moveLeft();
							pv.spriteAnimate(p.getX());
						}
						else{
							p.moveRight();
							pv.spriteAnimate(p.getX());
						}
					}

					//Joueur Humain de l'equipe 1
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
							pv.getSprite().playShoot();
							projectile = Projectile.getProjectile(1, p.getAngle(), p.getX(), p.getY() - PlayerView.HEIGHT - 10, -1);
							projectile.setBallMoving(true);
						}
						pv.display(gc,p.getX(),p.getY(),p.getAngle());
					}

				}

				for (int i=0 ; i<equipe2.size() ; i++ ) {
					Player p = equipe2.get(i);
					PlayerView pv = equipe2View.get(i);

					//Joueur IA de l'equipe 2
					if(p instanceof IAPlayer){
						if((int) Math.round( Math.random() ) == 1){
							p.moveLeft();
							pv.spriteAnimate(p.getX());
						}
						else{
							p.moveRight();
							pv.spriteAnimate(p.getX());
						}
					}

					//Joueur Humain de l'equipe 2
					else if(p instanceof HumanPlayer)
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
							pv.getSprite().playShoot();
							projectile = Projectile.getProjectile(1,p.getAngle(), p.getX(), p.getY() + PlayerView.HEIGHT + 10,1);
							projectile.setBallMoving(true);
						}
						pv.display(gc,p.getX(),p.getY(),p.getAngle());
					}

				}
	    	}
	     }.start(); // On lance la boucle de rafraichissement 
	     
	}

	private Player checkCollisionOfTeam(Rectangle2D projBoundary, ArrayList<Player> team) {
		for (Player player : team) {
			if(checkCollisionPlayer(projBoundary, player))
			return player;
		}
		return null;
	}

	private boolean checkCollisionPlayer(Rectangle2D projBoundary, Player player){
		return projBoundary.intersects(new Rectangle2D(player.getX(), player.getY(), PlayerView.WIDTH, PlayerView.HEIGHT));
	}

	public GraphicsContext getGc() {
		return gc;
	}

	public boolean isOOB(Projectile p){
		return !(p.getX()>0 && p.getX()<this.width-projView.getImg().getWidth() && p.getY()>0 && p.getY() < this.height-projView.getImg().getHeight());
	}

}
