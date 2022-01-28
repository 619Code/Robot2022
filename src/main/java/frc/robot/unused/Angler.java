package frc.robot.unused;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Angler extends SubsystemBase {
    public CANSparkMax angleMotor;
    private PIDController anglePID;

    public Angler() {
        angleMotor = new CANSparkMax(42, MotorType.kBrushless);
        angleMotor.restoreFactoryDefaults();
        angleMotor.setSmartCurrentLimit(35);
        angleMotor.setInverted(false);
        angleMotor.setIdleMode(IdleMode.kCoast);
        anglePID = new PIDController(0.04, 0.0025, 0.001);
        angleMotor.getEncoder().setPosition(0);
    }

    public void setAngle(double angle) {
        double targetAngle = ((angle) * 1.0/2.0);
        angleMotor.set(anglePID.calculate(angleMotor.getEncoder().getPosition(), targetAngle));
    }

    public double getAngle() {
        return angleMotor.getEncoder().getPosition() / (1.0/2.0);
    }
}
