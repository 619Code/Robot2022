package frc.robot.subsystems;

import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
    private CANSparkMax intakeMotor;
    public DoubleSolenoid wrist;

    public Intake() {
        intakeMotor = new CANSparkMax(Constants.LOADING_MOTOR, MotorType.kBrushless);
        wrist = new DoubleSolenoid(Constants.INTAKE_MODULE_TYPE, 1, 6);
    }

    public void spinIntake(double percent) {
        intakeMotor.set(percent);
    }

    public boolean isLowered(){
        return wrist.get() == Value.kForward;   
    }

    public void raiseIntake() {
        wrist.set(Value.kReverse);
    }

    public void lowerIntake() {
        wrist.set(Value.kForward);
    }
}