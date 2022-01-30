package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMax.SoftLimitDirection;
import com.revrobotics.*;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {

private CANSparkMax shootMotor;
private CANSparkMax turnMotor;
private CANSparkMax pitchMotor;
private RelativeEncoder turnMotorEncoder;
private SparkMaxPIDController pidController;
private double turnMotorSpeed;


    public Shooter(){
        
        shootMotor = new CANSparkMax(Constants.TURRET_SHOOTING_MOTOR, MotorType.kBrushless);
        turnMotor = new CANSparkMax(Constants.TURRET_TURNING_MOTOR, MotorType.kBrushless);
        pitchMotor = new CANSparkMax(Constants.TURRET_PITCH_MOTOR, MotorType.kBrushless);
        this.turnMotorEncoder = turnMotor.getEncoder();
        this.pidController = turnMotor.getPIDController();
        turnMotor.setIdleMode(IdleMode.kBrake);
    }
    

    public void runFlywheel(){
       
        shootMotor.set(.3);

    }

    public void turn(Rotation2d rotation){
        
        this.turnMotorEncoder.setPosition(rotation.getRadians() / (2 * Math.PI * Constants.turretDegreesPerTick));
        
    }

    public double move(){

        turnMotor.set(turnMotorSpeed);
        return turnMotorSpeed;

    }

    public synchronized double getSetpoint(){

        return turnMotorSpeed * Constants.turretDegreesPerTick * 360;

    }

    public synchronized Rotation2d getAngle() {

        return Rotation2d.fromDegrees(Constants.turretDegreesPerTick * this.turnMotorEncoder.getPosition());

    }

    public void softLimit(){

        turnMotor.setSoftLimit(SoftLimitDirection.kReverse, 0.0f);

    }

    public void funPIDStuff(){

        pidController.setD(.7);
        pidController.setP(.7);
        pidController.setI(.7);
        pidController.setFF(.7);

    }

    public void setAngle(double degrees){

        turnMotor.set(degrees + (Constants.turretDegreesPerTick));

    }

    public void setTurretYaw(double newYaw){


            this.setAngle(newYaw);

    }

    public double getTurretYaw(){

        return getAngle().getRadians();

    }

    public void shoot(){

    }

    public synchronized double error(){

        return getAngle().getDegrees() - getSetpoint();

    }
    public synchronized boolean isOnTarget(){

        return (Math.abs(error()) < Constants.TARGET_TOLERANCE);

    }

    public void setShooterRPM(double newRPM){


    }

    public void stop(){

        turnMotor.set(0);

    }

    public void end(){

        shootMotor.set(0);
        
    }

}
