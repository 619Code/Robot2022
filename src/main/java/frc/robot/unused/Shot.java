package frc.robot.unused;

public class Shot {
    public boolean isValid;
    public double rpm;
    public double hoodAngle;
    public double chanceOfSuccess;
    public boolean useLimelight;

    public Shot(double rpm, double hoodAngle) {
        this.rpm = rpm;
        this.hoodAngle = hoodAngle;
    }

    public Shot(){
        
    }
}
