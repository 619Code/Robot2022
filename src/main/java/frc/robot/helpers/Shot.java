package frc.robot.Shot;

public class Shot {
    private final double rpm;
    private final double pan;
    private final double tilt;
    private final double muzzleVelocity;

    public Shot(double rpm, double pan, double tilt, double muzzleVelocity){
        this.rpm = rpm;
        this.pan = pan;
        this.tilt = tilt;
        this.muzzleVelocity = muzzleVelocity;
    }

    public double rpm() {return rpm;}
    public double pan() {return pan;}
    public double tilt() {return tilt;}
    public double muzzleVelocity() {return muzzleVelocity;}
}