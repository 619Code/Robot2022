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
    private CANSparkMax rollerMotor;
    private CANSparkMax frontBeltMotor;
    private CANSparkMax backBeltMotor;

    public BallPosition verticalPosition;
    public BallPosition frontPosition;

    private Timer endTimer;

    public Magazine() {
        rollerMotor = new CANSparkMax(Constants.ROLLER_MOTOR, MotorType.kBrushless);
        backBeltMotor = new CANSparkMax(Constants.BACK_BELT_MOTOR, MotorType.kBrushless);
        backBeltMotor.setIdleMode(IdleMode.kBrake);
        
        verticalPosition = new BallPosition(Constants.VERTICAL_POSITION);
        frontPosition = new BallPosition(Constants.FRONT_POSITION);

        endTimer = new Timer();
    }

    public void intakeBalls() {
        
        backBeltMotor.set(-0.2);        
    }

    public void outtakeBalls() {
        backBeltMotor.set(0.3);
    }

    public void loadShooter() {
        backBeltMotor.set(-0.3);
    }

    public boolean isEmpty() {
        /*boolean appearsEmpty = !this.verticalPosition.hasBall() && !this.frontPosition.hasBall();
        if(!appearsEmpty) {
            endTimer.reset();
            endTimer.start();
            return false;
        } else if(endTimer.hasElapsed(3)) {
            return true;
        } else {
            return false;
        }*/
        return false;
    }

    public boolean isFull() {
        //return this.verticalPosition.hasBall() && this.frontPosition.hasBall();
        return true;
    }

    public void stopAll() {
        //rollerMotor.set(0);
        backBeltMotor.set(0);
    }
}
