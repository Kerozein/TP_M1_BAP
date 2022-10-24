package fr.icom.info.m1.balleauprisonnier_mvn.model;

import fr.icom.info.m1.balleauprisonnier_mvn.model.Player;
import javafx.scene.canvas.GraphicsContext;

public class IAPlayer extends Player {

    public IAPlayer(int xInit, int yInit) {
        super(xInit, yInit);
    }

    public IAPlayer(int xInit, int yInit, float speed) {
        super(xInit, yInit, speed);
    }
}
