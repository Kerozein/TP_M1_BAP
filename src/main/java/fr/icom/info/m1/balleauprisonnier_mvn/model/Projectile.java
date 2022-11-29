package fr.icom.info.m1.balleauprisonnier_mvn.model;

public class Projectile {
    private double speed;
    private double x;
    private double y;
    private double direction;
    private int way;
    private boolean ballMoving;
    private static Projectile projectile;

    private Projectile(double speed ,double direction, double x, double y,int way) {
        this.speed = speed;
        this.direction = direction;
        this.x = x;
        this.y = y;
        this.ballMoving = false;
        this.way = way;
        projectile = this;
    }

    public static Projectile getProjectile(double speed , double direction, double x, double y, int way) {
        if(projectile == null) {
            projectile = new Projectile(speed ,direction, x, y,way);
        }
        else {
            projectile.speed = speed;
            projectile.direction = direction;
            projectile.x = x;
            projectile.y = y;
            projectile.way = way;
            projectile.ballMoving = false;
        }
        return projectile;
    }

    public void move() {
        x = x + (speed * -Math.sin(direction*Math.PI/180))* way;
        y = y + (speed * Math.cos(direction*Math.PI/180))* way;
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
