package frc.robot.subsystems;

import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMax.SoftLimitDirection;
import com.revrobotics.*;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.annotations.Config;
import io.github.oblarg.oblog.annotations.Log;

public class Shooter extends SubsystemBase implements Loggable {

    private PIDController shooterOnboardPID;
    private SimpleMotorFeedforward shooterFeedforward;

    public enum EDeviceType
    {
        Hood,
        Turret
    }

    @Log
    private double currentVelocity = 0;

    @Log.Graph
    private double motorGain;

    @Log.Graph
    private double shooterPidResult;

    private CANSparkMax shooterMotor;
    private CANSparkMax turretMotor;
    private CANSparkMax hoodMotor;

    private RelativeEncoder shooterEncoder;
    private RelativeEncoder turretEncoder;
    private RelativeEncoder hoodEncoder;
    
    private double shooterSetPoint;
    
    @Log.Graph
    private double shooterVelocity;

    @Log
    private double shooterCountsPerRevolution;
    
    @Log
    private double velocityConversionFactor;

    @Log
    private double hoodAngle;

    @Log
    private double hoodMotorValue;

    @Log
    private double adjustedPoint;
    
    @Log
    private double hoodPosition;
    private double hoodSetpoint;
    private double hoodSpeed;
    private double hoodError;

    private double turretPosition;
    private double turretSetpoint;
    private double turretSpeed;
    private double turretError;

    private SparkMaxLimitSwitch hoodSwitch;
    private DigitalInput turretSwitch;

    public Shooter() {
        shooterMotor = new CANSparkMax(Constants.SHOOT_MOTOR, MotorType.kBrushless);
        turretMotor = new CANSparkMax(Constants.TURRET_MOTOR, MotorType.kBrushless);
        hoodMotor = new CANSparkMax(Constants.HOOD_MOTOR, MotorType.kBrushless);

        shooterEncoder = shooterMotor.getEncoder();
        shooterEncoder.setVelocityConversionFactor(1);
        hoodEncoder = hoodMotor.getEncoder();
        turretEncoder = turretMotor.getEncoder();

        initMotorSettings();
        initPIDs();
    }

    public void initMotorSettings() {
        shooterMotor.restoreFactoryDefaults();
        turretMotor.restoreFactoryDefaults();
        hoodMotor.restoreFactoryDefaults();

        shooterMotor.setSmartCurrentLimit(35);
        turretMotor.setSmartCurrentLimit(35);
        hoodMotor.setSmartCurrentLimit(35);

        shooterMotor.setIdleMode(IdleMode.kCoast);
        shooterMotor.setInverted(true);

        hoodSwitch = hoodMotor.getForwardLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyOpen);
        hoodSwitch.enableLimitSwitch(true);
        hoodMotor.setIdleMode(IdleMode.kBrake);
        hoodMotor.setInverted(true);
        hoodMotor.setSoftLimit(SoftLimitDirection.kForward, (float)Constants.HOOD_MIN_OUTPUT);
        hoodMotor.setSoftLimit(SoftLimitDirection.kReverse, (float)Constants.HOOD_MAX_OUTPUT);

        turretSwitch = new DigitalInput(Constants.TURRET_SWITCH);
        turretMotor.setIdleMode(IdleMode.kBrake);
        turretMotor.setInverted(true);
    }

    public void initPIDs() {
        shooterOnboardPID = new PIDController(Constants.SHOOTER_KP, Constants.SHOOTER_KI, Constants.SHOOTER_KD);
        shooterFeedforward = new SimpleMotorFeedforward(Constants.SHOOTER_KS, Constants.SHOOTER_KV, Constants.SHOOTER_KA);
    }

    public void shoot(double speed) {
        shooterVelocity = shooterEncoder.getVelocity();
        this.shooterMotor.set(speed);
    }

    public void setShooterSpeedByRPM(double speed) {
        speed = speed/60.0;
        shooterMotor.setVoltage(shooterOnboardPID.calculate(speed) + shooterFeedforward.calculate(speed));
        shooterVelocity = shooterEncoder.getVelocity();
    }

    public double getShooterRPM() {
        shooterVelocity = shooterEncoder.getVelocity();
        return shooterVelocity;
    }

    /////////////////////////////////////////////////////////////////

    public void setAngle(EDeviceType deviceType, double angle) {
        if (deviceType == EDeviceType.Hood) {
            this.setHoodAngle(angle);
        } else {
            this.setTurretAngle(angle);
        }
    }

    public void setHoodAngle(double angle) {
        hoodSetpoint = (Constants.BASE_HOOD_ANGLE - angle)/Constants.HOOD_DEGREES_PER_REV;
        hoodPosition = hoodMotor.getEncoder().getPosition();
        hoodError = hoodSetpoint - hoodPosition;
        hoodSpeed = (hoodError < 0) ? Constants.HOOD_MIN_OUTPUT : Constants.HOOD_MAX_OUTPUT;

        // System.out.println("Hood setpoint: " + hoodSetpoint);
        // System.out.println("Hood position: " + hoodPosition);
        // System.out.println("Hood speed: " + hoodSpeed);

        if(hoodPosition > Constants.MAXIMUM_HOOD_ANGLE_REV && hoodSpeed > 0){
            hoodSpeed = 0;
        } else if(hoodPosition < 0 && hoodSpeed < 0) {
            hoodSpeed = 0;
        }
        if(Math.abs(hoodError) < 1) {
            hoodSpeed = 0;
        }
        this.hoodMotor.set(hoodSpeed);
    }

    public void setTurretAngle(double angle) {
        turretSetpoint = (angle - Constants.MINIMUM_TURRET_ANGLE) / Constants.TURRET_DEGREES_PER_REV;
        turretPosition = turretEncoder.getPosition();
        turretError = turretSetpoint - turretPosition;
        turretSpeed = (turretError < 0) ? -Constants.TURRET_MAX_OUTPUT : Constants.TURRET_MAX_OUTPUT;

        // System.out.println("Turret setpoint: " + turretSetpoint);
        // System.out.println("Turret position: " + turretPosition);
        // System.out.println("Turret speed: " + turretSpeed);
        
        if(turretPosition > Constants.MAXIMUM_TURRET_ANGLE_REV && turretSpeed > 0){
            turretSpeed = 0;
        } else if(turretPosition < 0 && turretSpeed < 0) {
            turretSpeed = 0;
        }
        if(Math.abs(turretError) < 1){
            turretSpeed = 0;
        }
        this.turretMotor.set(turretSpeed);
    }

    /////////////////////////////////////////////////////////////////

    public boolean atZeroPoint(EDeviceType deviceType) {
        if(deviceType == EDeviceType.Hood) {
            return this.atHoodZeroPointRPM();
        } else {
            return this.atTurretZeroPoint();
        }
    }

    public boolean atHoodZeroPoint() {
        return this.hoodSwitch.isPressed();
    }

    public boolean atHoodZeroPointRPM() {
        return this.hoodEncoder.getVelocity() == 0.0;
    }

    public boolean atTurretZeroPoint() {
        return !this.turretSwitch.get();
    }

    public boolean AtTurretZeroPointRPM() {
        return this.turretEncoder.getVelocity() == 0.0;
    }

    /////////////////////////////////////////////////////////////////

    public double getAngle(EDeviceType deviceType) {
        if (deviceType == EDeviceType.Hood) {
            return getHoodAngle();
        } else {
            return getTurretAngle();
        }
    }

    @Log
    public double getHoodAngle() {
        return Constants.BASE_HOOD_ANGLE - (hoodEncoder.getPosition() * Constants.HOOD_DEGREES_PER_REV);
    }

    public double getTurretAngle() {
        return Constants.MINIMUM_TURRET_ANGLE - turretEncoder.getPosition() * Constants.TURRET_DEGREES_PER_REV;
    }

    /////////////////////////////////////////////////////////////////

    public double getPosition(EDeviceType deviceType) {
        if (deviceType == EDeviceType.Hood) {
            return getHoodPosition();
        } else {
            return getTurretPosition();
        }
    }

    public double getHoodPosition() {
        return hoodEncoder.getPosition();
    }

    public double getTurretPosition() {
        return turretEncoder.getPosition();
    }

    /////////////////////////////////////////////////////////////////

    public void move(EDeviceType deviceType, double speed) {
        if (deviceType == EDeviceType.Hood) {
            hoodMotor.set(speed);
        } else {
            if(speed > 0 && atZeroPoint(EDeviceType.Turret)) {
                speed = 0;
            }
            turretMotor.set(speed);
        }
    }

    /////////////////////////////////////////////////////////////////

    public boolean checkLowerBound(double rotation) {
        return (rotation > 0 && getTurretPosition() > 0) || (rotation > 0 && atZeroPoint(EDeviceType.Turret));
    }

    public boolean checkUpperBound(double rotation) {
        return rotation < 0 && getTurretPosition() < -Constants.MAXIMUM_TURRET_ANGLE_REV;
    }

    /////////////////////////////////////////////////////////////////

    public void setZeroPoint(EDeviceType deviceType) {
        if (deviceType == EDeviceType.Hood) {
            hoodEncoder.setPosition(0);
        } else {
            turretEncoder.setPosition(0);
        }
    }

    /////////////////////////////////////////////////////////////////

    public void stopAll() {
        shoot(0);
        move(EDeviceType.Hood, 0);
        move(EDeviceType.Turret, 0);
    }
}
