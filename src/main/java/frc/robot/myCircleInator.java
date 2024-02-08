package frc.robot;

import java.util.function.DoubleSupplier;

public class myCircleInator {
    static double TAU = 2 * Math.PI;

    double radius = 1.0;
    double angle = 0.0;

    public myCircleInator() {};
    
    public myCircleInator(double radius, double angle) {
        this.radius = radius;
        this.angle = angle;
    }

    public void rotate(double angle) {
        this.angle += angle;
        this.angle %= TAU;
    }

    public void rotate() {
        this.rotate((TAU/10)*0.020); // Full revolution every 10 seconds at 20ms period.
    }

    public double getX() {
        return (this.radius * Math.cos(this.angle));
    }

    public double getY() {
        return (this.radius * Math.sin(this.angle));
    }

    public DoubleSupplier xSupplier = () -> this.getX();
    public DoubleSupplier ySupplier = () -> this.getY();
}
