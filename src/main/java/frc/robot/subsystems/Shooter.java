package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
    private double setYaw;

    public Shooter(){
        setYaw = 0;
        // TODO: initialize motors
    }
    public void setTurretYaw(double newYaw){
        setYaw = newYaw;
    }
    public double getSetTurretYaw(){

    }
    public double getActualTurretYaw(){
        // how to do this?
        // need another sensor
    }
    public void shoot(){

    }
    public void setShooterRPM(double newRPM){

    }
    public void periodic(){
        // assuming a 360deg turret
        // try to go to the set location
        // in retrospect i really don't know enough about how the turret will work to do this
    }
}
