package frc.robot.helpers;

public class Shot {
    public boolean isValid;
    public double rpm;
    public double hoodAngle;
    public double chanceOfSuccess;

    public Shot(boolean isValid, double rpm, double hoodAngle, double chanceOfSuccess) {
        this.isValid = isValid;
        this.rpm = rpm;
        this.hoodAngle = hoodAngle;
        this.chanceOfSuccess = chanceOfSuccess;
    }

    public Shot(){
        
    }
}
