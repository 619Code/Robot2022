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
    public Solenoid wrist;

    private Timer raiseTimer;

    public Intake() {
        intakeMotor = new CANSparkMax(Constants.LOADING_MOTOR, MotorType.kBrushless);
        wrist = new Solenoid(Constants.INTAKE_MODULE_TYPE, 1);
        raiseTimer = new Timer();
    }

    public void spinIntake(double percent) {
        raiseTimer.reset();
        intakeMotor.set(percent);
    }

    public boolean isLowered(){
        return wrist.get();   
    }

    public void raiseIntake() {
        raiseTimer.start();
        if(raiseTimer.hasElapsed(1)) {
            wrist.set(false);
        }
    }

    public void lowerIntake() {
        raiseTimer.reset();
        wrist.set(true);
    }
}