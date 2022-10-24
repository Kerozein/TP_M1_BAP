package fr.icom.info.m1.balleauprisonnier_mvn.model;

public class Projectile {
    private double speed;
    private double x;
    private double y;
    private double direction;
    private static Projectile projectile;

    public Projectile(double speed ,double direction, double x, double y) {
        this.speed = speed;
        this.direction = direction;
        this.x = x;
        this.y = y;
        projectile = this;
    }

    public static Projectile getProjectile(double speed ,double direction, double x, double y) {
        if(projectile == null) {
            projectile = new Projectile(speed ,direction, x, y);
        }
        else {
            projectile.speed = speed;
            projectile.direction = direction;
            projectile.x = x;
            projectile.y = y;
        }
        return projectile;
    }

}
