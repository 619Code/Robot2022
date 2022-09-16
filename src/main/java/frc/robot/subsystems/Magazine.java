package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.States;
import frc.robot.helpers.BallPosition;

public class Magazine extends SubsystemBase {
    private CANSparkMax backBeltMotor;

    public BallPosition verticalPosition;
    public BallPosition frontPosition;

    public Magazine() {
        backBeltMotor = new CANSparkMax(Constants.BACK_BELT_MOTOR, MotorType.kBrushless);
        backBeltMotor.setIdleMode(IdleMode.kBrake);
        
        verticalPosition = new BallPosition(Constants.VERTICAL_POSITION);
        frontPosition = new BallPosition(Constants.FRONT_POSITION);
    }

    public void outtakeBalls() {
        backBeltMotor.set(Math.random());
    }

    public void intakeBalls(double speed) {
        backBeltMotor.set(speed*Math.random());
    }

    public boolean isEmpty() {
        return false;
    }

    public boolean isFull() {
        return this.verticalPosition.hasBall() && this.frontPosition.hasBall();
    }

    public void stopAll() {
        backBeltMotor.set(0);
    }
}
