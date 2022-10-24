package fr.icom.info.m1.balleauprisonnier_mvn.view;

import fr.icom.info.m1.balleauprisonnier_mvn.App;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class PlayerView {
    public Sprite sprite;

    public String playerColor;

    // On une image globale du joueur
    public Image directionArrow;
    public ImageView PlayerDirectionArrow;

    public PlayerView(String color, String side, double x, double y){
        playerColor=color;

        // On charge la representation du joueur
        if(side=="top"){
            directionArrow = new Image("assets/PlayerArrowDown.png");
        }
        else{
            directionArrow = new Image("assets/PlayerArrowUp.png");
        }

        PlayerDirectionArrow = new ImageView();
        PlayerDirectionArrow.setImage(directionArrow);
        PlayerDirectionArrow.setFitWidth(10);
        PlayerDirectionArrow.setPreserveRatio(true);
        PlayerDirectionArrow.setSmooth(true);
        PlayerDirectionArrow.setCache(true);

        Image tilesheetImage = new Image("assets/orc.png");
        sprite = new Sprite(tilesheetImage, 0,0, Duration.seconds(.2), side);
        sprite.setX(x);
        sprite.setY(y);
        //directionArrow = sprite.getClip().;
    }

    public void spriteAnimate(double x, double y){
        //System.out.println("Animating sprite");
        if(!sprite.isRunning) {sprite.playContinuously();}
        sprite.setX(x);
        sprite.setY(y);
    }
    public void spriteAnimate(double x){
        //System.out.println("Animating sprite");
        if(!sprite.isRunning) {sprite.playContinuously();}
        sprite.setX(x);
    }

    /**
     *  Affichage du joueur
     */
    public void display(GraphicsContext graphicsContext,double x, double y, double angle)
    {
        graphicsContext.save(); // saves the current state on stack, including the current transform
        rotate(graphicsContext, angle, x + directionArrow.getWidth() / 2, y + directionArrow.getHeight() / 2);
        graphicsContext.drawImage(directionArrow, x, y);
        graphicsContext.restore(); // back to original state (before rotation)
    }

    private void rotate(GraphicsContext gc, double angle, double px, double py) {
        Rotate r = new Rotate(angle, px, py);
        gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
    }
}
