package frc.robot.subsystems;

import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
    private CANSparkMax intakeMotor;
    public Solenoid wrist;

    public Intake() {
        intakeMotor = new CANSparkMax(Constants.LOADING_MOTOR, MotorType.kBrushless);
        wrist = new Solenoid(Constants.INTAKE_MODULE_TYPE, Constants.INTAKE_SOLENOID);
    }

    public void spinIntake(double percent) {
        intakeMotor.set(percent);
    }

    public boolean isLowered(){
        return wrist.get();   
    }

    public void raiseIntake() {
        wrist.set(false);
    }

    public void lowerIntake() {
        wrist.set(true);
    }
}