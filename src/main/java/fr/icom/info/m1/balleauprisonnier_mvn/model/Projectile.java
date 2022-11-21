package fr.icom.info.m1.balleauprisonnier_mvn.model;

public class Projectile {
    private double speed;
    private double x;
    private double y;
    private double direction;
    private int sens;
    private boolean ballMoving;
    private static Projectile projectile;


    public Projectile(double speed ,double direction, double x, double y) {
        this.speed = speed;
        this.direction = direction;
        this.x = x;
        this.y = y;
        this.ballMoving = false;
        projectile = this;
    }

    public static Projectile getProjectile(double speed , double direction, double x, double y, int sens) {
        if(projectile == null) {
            projectile = new Projectile(speed ,direction, x, y);
        }
        else {
            projectile.speed = speed;
            projectile.direction = direction;
            projectile.x = x;
            projectile.y = y;
            projectile.sens = sens;
            projectile.ballMoving = false;
        }
        return projectile;
    }

    public void moveProjectile() {
        x = x + (speed * -Math.sin(direction*Math.PI/180))*sens;
        y = y + (speed * Math.cos(direction*Math.PI/180))*sens;

    }

    public double getY() {
        return y;
    }

    public double getX() {
        return x;
    }

    public boolean isBallMoving() {
        return ballMoving;
    }

    public void setBallMoving(boolean ballMoving) {
        this.ballMoving = ballMoving;
    }
}
