package fr.icom.info.m1.balleauprisonnier_mvn;


import fr.icom.info.m1.balleauprisonnier_mvn.controller.GameController;
import fr.icom.info.m1.balleauprisonnier_mvn.model.HumanPlayer;
import fr.icom.info.m1.balleauprisonnier_mvn.model.IAPlayer;
import fr.icom.info.m1.balleauprisonnier_mvn.model.Player;
import fr.icom.info.m1.balleauprisonnier_mvn.view.PlayerView;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Classe principale de l'application 
 * s'appuie sur javafx pour le rendu
 */
public class App extends Application 
{
	final static int TEAM_SIZE = 5;
	final static int WIDTH = 600;
	final static int HEIGHT = 600;
	@Override
	public void start(Stage stage)
	{
        stage.setTitle("BalleAuPrisonnier");
		Image image = new Image("assets/title_logo.png");
		stage.getIcons().add(image);

		stage.setHeight(HEIGHT);
		stage.setWidth(WIDTH);
		buildStartMenu(stage);
	}

	private void AddTeamSpriteToRoot(Group root, ArrayList<Player> team, ArrayList<PlayerView> teamView, GameController gameController) {
		for(int i = 0; i< team.size(); i++ )
		{
			Player p = team.get(i);
			PlayerView pv = teamView.get(i);
			root.getChildren().add(pv.getSprite());
			pv.display(gameController.getGc(),p.getX(),p.getY(),p.getDirection());
		}
	}

	public static void main(String[] args){
    	Application.launch(args);
    }
	private void initGame(Stage stage){
		Group root = new Group();
		Scene scene = new Scene(root);

		stage.setHeight(HEIGHT+50);
		stage.setWidth(WIDTH);

		//Creation of team

		ArrayList<Player> team1 = new ArrayList<>();
		ArrayList<Player> team2 = new ArrayList<>();
		ArrayList<PlayerView> team1View = new ArrayList<>();
		ArrayList<PlayerView> team2View = new ArrayList<>();
		for(int i = 0; i< TEAM_SIZE; i++) {

			if(i==0) {
				team1.add(new HumanPlayer(WIDTH/2, HEIGHT-50));
				team1View.add(new PlayerView("bottom", WIDTH/2, HEIGHT-50));
			}
			else {
				team1.add(new IAPlayer((WIDTH-150*i)/2, HEIGHT-50));
				team1View.add(new PlayerView("bottom", (WIDTH-150*i)/2, HEIGHT-50));
			}

		}

		for(int i = 0; i< TEAM_SIZE; i++) {
			if(i==0) {
				team2.add(new HumanPlayer(WIDTH/2, 20));
				team2View.add(new PlayerView("top", WIDTH/2, 1));
			}
			else {
				team2.add(new IAPlayer((WIDTH-150*i)/2, 20));
				team2View.add(new PlayerView("top", (WIDTH-150*i)/2, 1));
			}
		}

		// Add controller to the scene
		GameController gameController = new GameController(WIDTH, HEIGHT, team1, team2, team1View, team2View);
		root.getChildren().add(gameController);



		// Add sprites to the scene
		AddTeamSpriteToRoot(root, team1, team1View, gameController);
		AddTeamSpriteToRoot(root, team2, team2View, gameController);

		// Add scene to window
		stage.setScene( scene );
		stage.show();
	}

	private void buildStartMenu(Stage stage){
		Group root = new Group();
		Scene scene = new Scene( root );
		scene.setFill(Color.LIGHTGRAY);
		Text title = new Text (10, 20, "Balle au prisonnier");

		title.setX(WIDTH/2 - 130);
		title.setY(HEIGHT/6);
		title.setFont(new Font("Montserrat", 30));

		Image image = new Image("assets/title_logo.png");
		//Creating the image view
		ImageView imageView = new ImageView();
		//Setting image to the image view
		imageView.setImage(image);
		//Setting the image view parameters
		imageView.setY(HEIGHT/2-150);
		imageView.setX(WIDTH/2-140);
		imageView.setFitWidth(250);
		imageView.setPreserveRatio(true);

		Button play = new Button();
		play.setOnMouseClicked(t -> initGame(stage));
		play.setText("Lancer la partie");
		play.setFont(new Font("Montserrat", 20));
		play.setTranslateX(40);
		play.setTranslateY(HEIGHT*5/6);

		Button help = new Button();
		help.setOnMouseClicked(t -> buildHelpMenu(stage));

		help.setText("Comment Jouer ?");
		help.setFont(new Font("Montserrat", 20));
		help.setTranslateX(WIDTH/2 - 60);
		help.setTranslateY(HEIGHT*5/6);

		Button exit = new Button();
		exit.setOnMouseClicked(t -> System.exit(0));

		exit.setText("Quitter");
		exit.setFont(new Font("Montserrat", 20));
		exit.setTranslateX(WIDTH-150);
		exit.setTranslateY(HEIGHT*5/6);


		root.getChildren().add(title);
		root.getChildren().add(play);
		root.getChildren().add(help);
		root.getChildren().add(exit);
		root.getChildren().add(imageView);

		stage.setScene( scene );
		stage.show();
	}

	private void buildHelpMenu(Stage stage){
		Group root = new Group();
		Scene scene = new Scene( root );
		scene.setFill(Color.LIGHTGRAY);
		Text title = new Text (10, 20, "Comment Jouer ?");

		title.setX(WIDTH/2 - 130);
		title.setY(HEIGHT/6);
		title.setFont(new javafx.scene.text.Font("Montserrat", 30));

		Text help = new Text(10,20,"Le joueur 1 se déplace avec ZQSD et tire avec ENTREE\nLe joueur 2 se déplace avec les fleches directionnelles et tire avec ESPACE\nLes joueurs ne peuvent tirer que s'ils ont rammassé la balle\nSi la balle est bloquée, il faut appuyer sur R pour la redistribuer a un joueur\nAppuyer sur O pour afficher le score");
		help.setX(50);
		help.setY(HEIGHT/5);
		help.setFont(new javafx.scene.text.Font("Montserrat", 15));

		Button back = new Button();
		back.setOnMouseClicked(t -> buildStartMenu(stage));

		back.setText("Retourner en arriere");
		back.setFont(new Font("Montserrat", 20));
		back.setTranslateX(WIDTH/2-100);
		back.setTranslateY(HEIGHT*5/6);


		root.getChildren().add(title);
		root.getChildren().add(help);
		root.getChildren().add(back);

		stage.setScene( scene );
		stage.show();
	}
}
