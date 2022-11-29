package fr.icom.info.m1.balleauprisonnier_mvn.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class ProjectileView {
    private final Image img;
    public ProjectileView(String  path) {
        this.img = new Image(path);
    }
    public void display(GraphicsContext gc, double x, double y){
        gc.drawImage(this.img, x, y);
    }
    public Image getImg(){
        return this.img;
    }
}
