package frc.robot.subsystems;

import frc.robot.Constants;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeMagazine extends SubsystemBase {

    private CANSparkMax intake;
    private Solenoid lower;
    private boolean armDown;

    public IntakeMagazine() {
        // Magazine can only consist of 2 balls held at once, if at all.

        //Here's the motor needed to spin the intake bar.
        intake = new CANSparkMax(Constants.LOADING_MOTOR, MotorType.kBrushless);
        intake.restoreFactoryDefaults();
        intake.setSecondaryCurrentLimit(35);

        //Wrist solenoid used to raise and lower the intake 

        lower = new Solenoid(Constants.PCM_CAN_ID, Constants.INTAKE_SOLENOID);

        
    }

    public void spIntake() {
        if (lower.get()) {
            intake.set(0.8);
        }
        else {
            lower.set(true);
        }

        armDown = lower.get();
    }

    public void raiseIntake() {
        lower.set(false);
        armDown = lower.get();
    }

}