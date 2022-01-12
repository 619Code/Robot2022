package frc.robot.Shot;

public class Shot {
    private final double rpm;
    private final double dPan;
    private final double dTilt;
    private final double muzzleVelocity;

    public Shot(double rpm, double dPan, double dTilt, double muzzleVelocity){
        // pan and tilt are relative to (the line between the robot and the hub) and (the ground), respectively
        this.rpm = rpm;
        this.dPan = dPan;
        this.dTilt = dTilt;
        this.muzzleVelocity = muzzleVelocity;
    }

    public double rpm() {return rpm;}
    public double dPan() {return dPan;}
    public double dTilt() {return dTilt;}
    public double muzzleVelocity() {return muzzleVelocity;}
}