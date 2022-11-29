package fr.icom.info.m1.balleauprisonnier_mvn.controller;


import javafx.geometry.Rectangle2D;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

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
		gc = this.getGraphicsContext2D();
		
		/** permet de capturer le focus et donc les evenements clavier et souris */
		this.setFocusTraversable(true);

		giveBallToARandomPlayer();


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

					//Check si la balle est sortie du terrain
					if(isOOB(projectile)){
						projectile.setBallMoving(false);
					}
					if(projectile.isBallMoving()) {
						projectile.moveProjectile();
						projBoundary = new Rectangle2D(projectile.getX(), projectile.getY(), projImg.getWidth(), projImg.getHeight());

						//Check de la collision entre la balle et un joueur
						Player p;

						//Equipe 1
						if ((p = checkCollisionOfTeam(projBoundary, equipe1)) != null) {
							int index = equipe1.indexOf(p);
							equipe1.remove(index);
							equipe1View.get(index).disable();
							equipe1View.remove(index);
							projectile.setBallMoving(false);
						}

						//Equipe 2
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
						tryShoot(p,pv,-1);

						if((int) Math.round( Math.random() ) == 1){
							p.moveLeft();
							if(projectile != null)
								tryGrab(p,projectile);
							pv.spriteAnimate(p.getX());
						}
						else{
							p.moveRight();
							if(projectile != null)
								tryGrab(p,projectile);
							pv.spriteAnimate(p.getX());
						}
					}

					//Joueur Humain de l'equipe 1
					if(p instanceof HumanPlayer) {
						if (input.contains("LEFT")) {
							p.moveLeft();
							if(projectile != null)
								tryGrab(p,projectile);
							pv.spriteAnimate(p.getX());
						}
						if (input.contains("RIGHT")) {
							p.moveRight();
							if(projectile != null)
								tryGrab(p,projectile);
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
							tryShoot(p, pv,-1);
						}
						pv.display(gc,p.getX(),p.getY(),p.getAngle());
					}

				}

				for (int i=0 ; i<equipe2.size() ; i++ ) {
					Player p = equipe2.get(i);
					PlayerView pv = equipe2View.get(i);

					//Joueur IA de l'equipe 2
					if(p instanceof IAPlayer){
						tryShoot(p,pv,1);

						if((int) Math.round( Math.random() ) == 1){
							p.moveLeft();
							if(projectile != null)
								tryGrab(p,projectile);
							pv.spriteAnimate(p.getX());
						}
						else{
							p.moveRight();
							if(projectile != null)
								tryGrab(p,projectile);
							pv.spriteAnimate(p.getX());
						}
					}

					//Joueur Humain de l'equipe 2
					else if(p instanceof HumanPlayer) {
						if (input.contains("Q")) {
							p.moveLeft();
							if(projectile != null)
								tryGrab(p,projectile);
							pv.spriteAnimate(p.getX());
						}
						if (input.contains("D")) {
							p.moveRight();
							if(projectile != null)
								tryGrab(p,projectile);
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
							tryShoot(p, pv,1);
						}
						pv.display(gc,p.getX(),p.getY(),p.getAngle());
					}

					//Redistribuer la balle
					if(input.contains("R")) {
						giveBallToARandomPlayer();
						input.remove("R");
					}
				}
	    	}
	     }.start(); // On lance la boucle de rafraichissement 
	     
	}

	private void giveBallToARandomPlayer() {
		ArrayList<Player> team;
		if((int) Math.round( Math.random() ) == 1)
			team = equipe1;
		else
			team = equipe2;
		team.get((int) (Math.random() * team.size())).setBall(true);
	}

	private void tryShoot(Player p, PlayerView pv, int sens) {
		if(p.haveBall()){
			pv.getSprite().playShoot();
			projectile = Projectile.getProjectile(1, p.getAngle(), p.getX(), p.getY() + (PlayerView.HEIGHT + 10)* sens,sens);
			projectile.setBallMoving(true);
			p.setBall(false);
		}
	}

	private Player checkCollisionOfTeam(Rectangle2D projBoundary, ArrayList<Player> team) {
		Iterator<Player> iterator = team.listIterator();
		while (iterator.hasNext()) {
			Player player = iterator.next();
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
		return !(p.getX()>=0 && p.getX()<=this.width-projView.getImg().getWidth() && p.getY()>=0 && p.getY() <= this.height-projView.getImg().getHeight());
	}

	public void tryGrab(Player p, Projectile proj){
		Rectangle2D boundary = new Rectangle2D(projectile.getX(), projectile.getY(), projView.getImg().getWidth(), projView.getImg().getHeight());
		if(!proj.isBallMoving() && checkCollisionPlayer(boundary, p))
		{
			p.setBall(true);
			projectile = null;
		}
	}

	public boolean checkLooseTeam(ArrayList<Player> team){
		return team.size()==0;
	}

}
