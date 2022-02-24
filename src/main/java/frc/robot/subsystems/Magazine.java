package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.States;
import frc.robot.helpers.BallPosition;

public class Magazine extends SubsystemBase {
    private CANSparkMax rollerMotor;
    private CANSparkMax frontBeltMotor;
    private CANSparkMax backBeltMotor;
    private BallPosition verticalPosition;
    private BallPosition frontPosition;

    public Magazine() {
        verticalPosition = new BallPosition(Constants.VERTICAL_POSITION);
        frontPosition = new BallPosition(Constants.FRONT_POSITION);
        rollerMotor = new CANSparkMax(Constants.ROLLER_MOTOR, MotorType.kBrushless);
        frontBeltMotor = new CANSparkMax(Constants.FRONT_BELT_MOTOR, MotorType.kBrushless);
        backBeltMotor = new CANSparkMax(Constants.BACK_BELT_MOTOR, MotorType.kBrushless);
    }

    public void intakeBalls() {
        //do logic for this later
    }

    public void loadShooter() {
        frontBeltMotor.set(0.3);
        backBeltMotor.set(0.3);
    }

    public boolean isEmpty() {
        return !this.verticalPosition.hasBall() && !this.frontPosition.hasBall();
    }

    public boolean isFull() {
        return this.verticalPosition.hasBall() && this.frontPosition.hasBall();
    }

    public void stopAll() {
        rollerMotor.set(0);
        frontBeltMotor.set(0);
        backBeltMotor.set(0);
    }
}
