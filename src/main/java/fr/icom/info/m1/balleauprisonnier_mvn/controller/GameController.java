package fr.icom.info.m1.balleauprisonnier_mvn.controller;

import javafx.geometry.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import fr.icom.info.m1.balleauprisonnier_mvn.model.*;
import fr.icom.info.m1.balleauprisonnier_mvn.view.PlayerView;
import fr.icom.info.m1.balleauprisonnier_mvn.view.ProjectileView;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class GameController extends Canvas{

	// Team models and views
	private final ArrayList<Player> team1;
	private final ArrayList<PlayerView> team1View;
	private final ArrayList<Player> team2;
	private final ArrayList<PlayerView> team2View;

	// Event array
	private final ArrayList<String> input = new ArrayList<>();

	// Score
	private int scoreT1;
	private int scoreT2;

	// Canvas properties
	private final int width;
	private final int height;
	private final GraphicsContext gc;

	// Projectile model and view
	private Projectile projectile = null;
	private final ProjectileView projView = new ProjectileView("assets/ball.png");
    

	public GameController(int w, int h, ArrayList<Player> t1, ArrayList<Player> t2, ArrayList<PlayerView> t1v, ArrayList<PlayerView> t2v)
	{
		super(w, h); 
		width = w;
		height = h;
		team1 = t1;
		team2 = t2;
		team1View = t1v;
		team2View = t2v;
		scoreT1 = 0;
		scoreT2 = 0;
		gc = this.getGraphicsContext2D();
		gc.setFont(new Font("Montserrat", 40));

		giveBallToARandomPlayer();

		// Catch focus (keyboard event)
		this.setFocusTraversable(true);


		// When a key is pressed, add in event array
	    this.setOnKeyPressed(
				e -> {
					String code = e.getCode().toString();
					// only add once... prevent duplicates
					if ( !input.contains(code) )
						input.add( code );
				});

		// When a key is released remove it from event array
	    this.setOnKeyReleased(
				e -> {
					String code = e.getCode().toString();
					input.remove( code );
				});
	    
		// main loop (called ~60 times per seconds)
	    new AnimationTimer() 
	    {
	        public void handle(long currentNanoTime)
	        {
				Image projImg = projView.getImg();
				Rectangle2D projBoundary;

	            // Clear canvas
				gc.setFill( Color.LIGHTGRAY);
				gc.fillRect(0, 0, width, height);


				if(projectile != null){

					//Display projectile
					projView.display(gc, projectile.getX(), projectile.getY());

					//Check if the projectile is out of bounds
					if(isOOB(projectile)){
						projectile.setBallMoving(false);
					}

					//Check if the ball is moving on the field
					if(projectile.isBallMoving()) {
						projectile.move();
						projBoundary = new Rectangle2D(projectile.getX(), projectile.getY(), projImg.getWidth(), projImg.getHeight());

						Player p;

						//Check if there is a collision between a player and the moving ball
						// Team 1
						if ((p = checkCollisionOfTeam(projBoundary, team1)) != null) {
							int index = team1.indexOf(p);
							team1.remove(index);
							team1View.get(index).disable();
							team1View.remove(index);
							projectile.setBallMoving(false);
							scoreT2++;
						}

						// Team 2
						if ((p = checkCollisionOfTeam(projBoundary, team2)) != null) {
							int index = team2.indexOf(p);
							team2.remove(index);
							team2View.get(index).disable();
							team2View.remove(index);
							projectile.setBallMoving(false);
							scoreT1++;
						}
					}
				}

				// Display and move player
				for (int i = 0; i< team1.size() ; i++ ) {
					Player p = team1.get(i);
					PlayerView pv = team1View.get(i);


					// AI player of team 1
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

					// Human player of team 1
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
						pv.display(gc,p.getX(),p.getY(),p.getDirection());
					}

				}

				for (int i = 0; i< team2.size() ; i++ ) {
					Player p = team2.get(i);
					PlayerView pv = team2View.get(i);

					// AI player of team 2
					if(p instanceof IAPlayer){
						tryShoot(p,pv,1);

						if((int) Math.round( Math.random() ) == 1){
							p.moveLeft();
						}
						else{
							p.moveRight();
						}
						if(projectile != null)
							tryGrab(p,projectile);
						pv.spriteAnimate(p.getX());
					}

					// Human player of team 2
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
						pv.display(gc,p.getX(),p.getY(),p.getDirection());
					}

					//Redistribute the projectile
					if(input.contains("R")) {
						giveBallToARandomPlayer();
						input.remove("R");
					}

					//Display the score
					if(input.contains("O")) {
						displayScore();
					}
				}
	    	}
	     }.start();
	     
	}


	/**
	 * Method that gives the ball to a random player.
	 */
	private void giveBallToARandomPlayer() {
		ArrayList<Player> team;
		if((int) Math.round( Math.random() ) == 1)
			team = team1;
		else
			team = team2;
		team.get((int) (Math.random() * team.size())).setBall(true);
	}

	private void tryShoot(Player p, PlayerView pv, int way) {
		if(p.haveBall()){
			pv.getSprite().playShoot();
			projectile = Projectile.getProjectile(1, p.getDirection(), p.getX(), p.getY() + (PlayerView.HEIGHT + 10)* way,way);
			projectile.setBallMoving(true);
			p.setBall(false);
		}
	}

	public void displayScore() {
		gc.strokeText(scoreT1 + "/" + scoreT2, width/2, height/2);
	}

	/**
	 *
	 * @param projBoundary
	 * @param team
	 * Check the collision of all the players of the team
	 */
	private Player checkCollisionOfTeam(Rectangle2D projBoundary, ArrayList<Player> team) {
		Iterator<Player> iterator = team.listIterator();
		while (iterator.hasNext()) {
			Player player = iterator.next();
			if(checkCollisionPlayer(projBoundary, player))
				return player;
		}
		return null;
	}

	/**
	 *
	 * @param projBoundary
	 * @param player
	 * Check the collision of one player
	 */
	private boolean checkCollisionPlayer(Rectangle2D projBoundary, Player player){
		return projBoundary.intersects(new Rectangle2D(player.getX(), player.getY(), PlayerView.WIDTH, PlayerView.HEIGHT));
	}

	/**
	 * @param p
	 * Check if the projectile is out of bound
	 */
	public boolean isOOB(Projectile p){
		return !(p.getX()>=0 && p.getX()<=this.width-projView.getImg().getWidth() && p.getY()>=0 && p.getY() <= this.height-projView.getImg().getHeight());
	}

	/**
	 * @param p
	 * @param proj
	 * Method that allows player to grab the projectile if it's not moving
	 */
	public void tryGrab(Player p, Projectile proj){
		Rectangle2D boundary = new Rectangle2D(projectile.getX(), projectile.getY(), projView.getImg().getWidth(), projView.getImg().getHeight());
		if(!proj.isBallMoving() && checkCollisionPlayer(boundary, p))
		{
			p.setBall(true);
			projectile = null;
		}
	}

	public GraphicsContext getGc() {
		return gc;
	}
}
