package fr.icom.info.m1.balleauprisonnier_mvn.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class PlayerView {
    private final Sprite sprite;
    private final Image directionArrow;
    public static final int WIDTH = 30;
    public static final int HEIGHT = 46;

    public PlayerView(String side, double x, double y){
        if(side=="top"){
            directionArrow = new Image("assets/PlayerArrowDown.png");
        }
        else{
            directionArrow = new Image("assets/PlayerArrowUp.png");
        }
        ImageView playerDirectionArrow = new ImageView();
        playerDirectionArrow.setImage(directionArrow);
        playerDirectionArrow.setFitWidth(10);
        playerDirectionArrow.setPreserveRatio(true);
        playerDirectionArrow.setSmooth(true);
        playerDirectionArrow.setCache(true);
        Image img = new Image("assets/orc.png");
        sprite = new Sprite(img, Duration.seconds(.2), side);
        sprite.setX(x);
        sprite.setY(y);
    }

    public void spriteAnimate(double x){
        if(!sprite.getRunning()) {sprite.playContinuously();}
        sprite.setX(x);
    }

    public void display(GraphicsContext graphicsContext,double x, double y, double angle){
        graphicsContext.save();
        rotate(graphicsContext, angle, x + directionArrow.getWidth() / 2, y + directionArrow.getHeight() / 2);
        graphicsContext.drawImage(directionArrow, x, y);
        graphicsContext.restore();
    }

    private void rotate(GraphicsContext gc, double angle, double px, double py){
        Rotate r = new Rotate(angle, px, py);
        gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
    }

    public Sprite getSprite() {
        return sprite;
    }

    // Make a player disappear from the screen
    public void disable() {
        sprite.setImage(null);
    }
}
