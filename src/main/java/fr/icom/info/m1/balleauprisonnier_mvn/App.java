package fr.icom.info.m1.balleauprisonnier_mvn;


import fr.icom.info.m1.balleauprisonnier_mvn.controller.GameController;
import fr.icom.info.m1.balleauprisonnier_mvn.model.HumanPlayer;
import fr.icom.info.m1.balleauprisonnier_mvn.model.IAPlayer;
import fr.icom.info.m1.balleauprisonnier_mvn.model.Player;
import fr.icom.info.m1.balleauprisonnier_mvn.view.PlayerView;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Classe principale de l'application 
 * s'appuie sur javafx pour le rendu
 */
public class App extends Application 
{
	final static int TAILLE_EQUIPE = 3;
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

        Group root = new Group();
        Scene scene = new Scene( root );

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
}
