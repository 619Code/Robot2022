package frc.robot.subsystems;

import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeMagazine extends SubsystemBase {

    private VictorSPX intake;
    public Solenoid lower;

    public IntakeMagazine() {
        // Magazine can only consist of 2 balls held at once, if at all.

        //Here's the motor needed to spin the intake bar.
        intake = new VictorSPX(21); 

        //Wrist solenoid used to raise and lower the intake 
        lower = new Solenoid(Constants.PCM_CAN_ID, Constants.INTAKE_SOLENOID);

        
    }

    public void spIntake(double percent) {
        intake.set(ControlMode.PercentOutput, percent);
    }

    public void raiseIntake() {
        lower.set(false);
    }

    public void lowerIntake() {
        lower.set(true);
    }

}