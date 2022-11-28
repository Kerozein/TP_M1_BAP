package fr.icom.info.m1.balleauprisonnier_mvn;


import fr.icom.info.m1.balleauprisonnier_mvn.controller.GameController;
import fr.icom.info.m1.balleauprisonnier_mvn.model.HumanPlayer;
import fr.icom.info.m1.balleauprisonnier_mvn.model.IAPlayer;
import fr.icom.info.m1.balleauprisonnier_mvn.model.Player;
import fr.icom.info.m1.balleauprisonnier_mvn.view.PlayerView;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Classe principale de l'application 
 * s'appuie sur javafx pour le rendu
 */
public class App extends Application 
{
	final static int TAILLE_EQUIPE = 5;
	final static int WIDTH = 600;
	final static int HEIGHT = 600;
	/**
	 * En javafx start() lance l'application
	 *
	 * On cree le SceneGraph de l'application ici
	 * @see <a href="https://docs.oracle.com/javafx/2/scenegraph/jfxpub-scenegraph.htm">...</a>
	 *
	 */
	@Override
	public void start(Stage stage) throws Exception 
	{
		// Nom de la fenetre
        stage.setTitle("BalleAuPrisonnier");
		Image image = new Image("assets/title_logo.png");
		stage.getIcons().add(image);

		stage.setHeight(HEIGHT);
		stage.setWidth(WIDTH);
		buildMenu(stage);
	}

	private void AddTeamSpriteToRoot(Group root, ArrayList<Player> equipe, ArrayList<PlayerView> equipe1View, GameController gameController) {
		for(int i = 0; i< equipe.size(); i++ )
		{
			Player p = equipe.get(i);
			PlayerView pv = equipe1View.get(i);
			root.getChildren().add(pv.sprite);
			pv.display(gameController.getGc(),p.getX(),p.getY(),p.getAngle());
		}
	}

	public static void main(String[] args)
    {
        //System.out.println( "Hello World!" );
    	Application.launch(args);
    }
	//TODO: Completer la methode a la place de faire 300 add chiants
	private void addPlayer(){

	}

	private void initGame(Stage stage){
		Group root = new Group();
		Scene scene = new Scene(root);

		stage.setHeight(HEIGHT+50);
		stage.setWidth(WIDTH);
		/** Couleurs possibles */
		String[] colorMap = new String[] {"blue", "green", "orange", "purple", "yellow"};

		/** On initialise les equipes */

		ArrayList<Player> equipe1 = new ArrayList<>();
		ArrayList<Player> equipe2 = new ArrayList<>();
		ArrayList<PlayerView> equipe1View = new ArrayList<>();
		ArrayList<PlayerView> equipe2View = new ArrayList<>();
		for(int i=0; i<TAILLE_EQUIPE; i++) {

			if(i==0) {
				equipe1.add(new HumanPlayer(WIDTH/2, HEIGHT-50));
				equipe1View.add(new PlayerView(colorMap[0],"bottom", WIDTH/2, HEIGHT-50));
			}
			else {
				equipe1.add(new IAPlayer((WIDTH-150*i)/2, HEIGHT-50));
				equipe1View.add(new PlayerView(colorMap[0],"bottom", (WIDTH-150*i)/2, HEIGHT-50));
			}

		}

		for(int i=0; i<TAILLE_EQUIPE; i++) {
			if(i==0) {
				equipe2.add(new HumanPlayer(WIDTH/2, 20));
				equipe2View.add(new PlayerView(colorMap[0],"top", WIDTH/2, 1));
			}
			else {
				equipe2.add(new IAPlayer((WIDTH-150*i)/2, 20));
				equipe2View.add(new PlayerView(colorMap[0],"top", (WIDTH-150*i)/2, 1));
			}
		}

		// On cree le terrain de jeu et on l'ajoute a la racine de la scene
		GameController gameController = new GameController(WIDTH, HEIGHT, equipe1, equipe2, equipe1View, equipe2View);
		root.getChildren().add(gameController);


		/* Affichage on ajoute les sprites a la racine de la scene */
		AddTeamSpriteToRoot(root, equipe1, equipe1View, gameController);
		AddTeamSpriteToRoot(root, equipe2, equipe2View, gameController);

		// On ajoute la scene a la fenetre et on affiche
		stage.setScene( scene );
		stage.show();
	}

	private void buildMenu(Stage stage){
		Group root = new Group();
		Scene scene = new Scene( root );
		scene.setFill(Color.LIGHTGRAY);
		Text title = new Text (10, 20, "Balle au prisonnier");

		title.setX(WIDTH/2 - 130);
		title.setY(HEIGHT/6);
		title.setFont(new javafx.scene.text.Font("Montserrat", 30));

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
		play.setOnMouseClicked(new EventHandler<MouseEvent>() {

									 @Override
									 public void handle(MouseEvent t) {
										initGame(stage);

									 }
								 });

		play.setText("Lancer la partie");
		play.setFont(new Font("Montserrat", 20));
		play.setTranslateX(40);
		play.setTranslateY(HEIGHT*5/6);

		Button help = new Button();
		help.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent t) {
				buildHelpMenu(stage);

			}
		});

		help.setText("Comment Jouer ?");
		help.setFont(new Font("Montserrat", 20));
		help.setTranslateX(WIDTH/2 - 60);
		help.setTranslateY(HEIGHT*5/6);

		Button exit = new Button();
		exit.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent t) {
				System.exit(0);
			}
		});

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

		Text help = new Text(10,20,"Le joueur 1 se déplace avec ZQSD et tire avec ENTREE\nLe joueur 2 se déplace avec les fleches directionnelles et tire avec ESPACE\nLes joueurs ne peuvent tirer que s'ils ont rammassé la balle\nSi la balle est bloquée, il faut appuyer sur R pour la redistribuer a un joueur");
		help.setX(50);
		help.setY(HEIGHT/5);
		help.setFont(new javafx.scene.text.Font("Montserrat", 15));

		Button back = new Button();
		back.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent t) {
				buildMenu(stage);
			}
		});

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
