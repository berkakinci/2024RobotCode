package frc.robot;

public class DiamondInator extends myCircleInator{
    public DiamondInator(double radius, double angle) {
        this.radius = radius;
        this.angle = angle;
    }

    public double getX() {
        return this.radius * Math.signum(super.getX());
    }    

    public double getY() {
        return this.radius * Math.signum(super.getY());
    }    
}
