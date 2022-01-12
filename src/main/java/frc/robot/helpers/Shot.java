package frc.robot.shot;

public class Shot {
    private final double rpm;
    private final double pan;
    private final double tilt;

    public Shot(double rpm, double pan, double tilt){
        this.rpm = rpm;
        this.pan = pan;
        this.tilt = tilt
    }

    public double rpm() {return rpm;}
    public double pan() {return pan;}
    public double tilt() {return tilt;}
}