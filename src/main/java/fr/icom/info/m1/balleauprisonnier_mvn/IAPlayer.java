package fr.icom.info.m1.balleauprisonnier_mvn;

import javafx.scene.canvas.GraphicsContext;

public class IAPlayer extends Player {

    public IAPlayer(GraphicsContext gc, String color, int xInit, int yInit, String side) {
        super(gc, color, xInit, yInit, side);
    }

    public IAPlayer(GraphicsContext gc, String color, int xInit, int yInit, String side, float speed) {
        super(gc, color, xInit, yInit, side, speed);
    }
}
