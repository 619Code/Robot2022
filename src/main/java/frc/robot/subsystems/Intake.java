package frc.robot.subsystems;

import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
    private CANSparkMax intakeMotor;
    public DoubleSolenoid wrist;

    private Timer raiseTimer;

    public Intake() {
        intakeMotor = new CANSparkMax(Constants.LOADING_MOTOR, MotorType.kBrushless);
        wrist = new DoubleSolenoid(Constants.INTAKE_MODULE_TYPE, 7, 0);
        raiseTimer = new Timer();
    }

    public void spinIntake(double percent) {
        raiseTimer.reset();
        intakeMotor.set(percent);
    }

    public boolean isLowered(){
        return wrist.get() == Value.kForward;   
    }

    public void raiseIntake() {
        raiseTimer.start();
        if(raiseTimer.hasElapsed(1)) {
            wrist.set(Value.kReverse);
        }
    }

    public void lowerIntake() {
        raiseTimer.reset();
        wrist.set(Value.kForward);
    }
}