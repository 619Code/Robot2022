package frc.robot.subsystems;

import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMax.SoftLimitDirection;
import com.revrobotics.*;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {

    public enum EDeviceType
    {
        Hood,
        Turret
    }

    private CANSparkMax shooterMotor;
    // private CANSparkMax turretMotor;
    private CANSparkMax hoodMotor;

    private RelativeEncoder shooterEncoder;
    // private RelativeEncoder turretEncoder;
    private RelativeEncoder hoodEncoder;

    private SparkMaxPIDController shooterPID;
    private double shooterSetPoint;
    private double shooterVelocity;

    private SparkMaxPIDController hoodPID;
    private double hoodSetPoint;
    private double hoodAngle;
    private SparkMaxLimitSwitch hoodSwitch;

    // private SparkMaxPIDController turretPID;
    // private double turretSetPoint;
    // private double turretAngle;
    // private SparkMaxLimitSwitch turretSwitch;

    private DigitalInput hoodDistanceSensor;

    public Shooter() {
        shooterMotor = new CANSparkMax(Constants.SHOOT_MOTOR, MotorType.kBrushless);
        // turretMotor = new CANSparkMax(Constants.TURRET_MOTOR, MotorType.kBrushless);
        hoodMotor = new CANSparkMax(Constants.HOOD_MOTOR, MotorType.kBrushless);

        shooterEncoder = shooterMotor.getEncoder();
        hoodEncoder = hoodMotor.getEncoder();
        // turretEncoder = turretMotor.getEncoder();

        hoodDistanceSensor = new DigitalInput(Constants.HOOD_DISTANCE_SENSOR);

        initMotorSettings();
        initPIDs();
    }

    public void initMotorSettings() {
        shooterMotor.restoreFactoryDefaults();
        // turretMotor.restoreFactoryDefaults();
        hoodMotor.restoreFactoryDefaults();

        hoodSwitch = hoodMotor.getForwardLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyOpen);
        hoodSwitch.enableLimitSwitch(true);
        // turretSwitch = turretMotor.getForwardLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyOpen);
        // turretSwitch.enableLimitSwitch(true);

        // turretMotor.setIdleMode(IdleMode.kBrake);
        hoodMotor.setIdleMode(IdleMode.kBrake);

        // turretMotor.setSoftLimit(SoftLimitDirection.kForward, 0.0f);
        // turretMotor.setSoftLimit(SoftLimitDirection.kReverse, 0.0f);
        hoodMotor.setSoftLimit(SoftLimitDirection.kForward, 0.0f);
        hoodMotor.setSoftLimit(SoftLimitDirection.kReverse, 0.0f);
    }

    public void initPIDs() {
        shooterPID = shooterMotor.getPIDController();
        shooterPID.setP(Constants.SHOOTER_KP);
        shooterPID.setI(Constants.SHOOTER_KI);
        shooterPID.setD(Constants.SHOOTER_KD);
        shooterPID.setOutputRange(0, 1);

        hoodPID = hoodMotor.getPIDController();
        hoodPID.setP(Constants.HOOD_KP);
        hoodPID.setI(Constants.HOOD_KI);
        hoodPID.setD(Constants.HOOD_KD);
        hoodPID.setOutputRange(-0.1, 0.1);
    }

    public void shoot(double speed) {
        shooterVelocity = shooterEncoder.getVelocity();
        shooterSetPoint = Constants.SHOOTER_MAX_RPM * speed;
        shooterPID.setReference(shooterSetPoint, CANSparkMax.ControlType.kVelocity);
    }

    public void setShooterSpeedByRPM(double speed) {
        shooterSetPoint = speed;
        shooterPID.setReference(shooterSetPoint, CANSparkMax.ControlType.kVelocity);
    }

    public boolean AtHoodZeroPoint() {
        //return this.hoodSwitch.isPressed();
        System.out.println(!hoodDistanceSensor.get());
        return !hoodDistanceSensor.get();
    }

    // public boolean AtTurretZeroPoint() {
    //     return this.turretSwitch.isPressed();
    // }

    public void SetHoodZeroPoint() {
        this.hoodEncoder.setPosition(0);
    }

    // public void SetTurretZeroPoint() {
    //     this.turretEncoder.setPosition(0);
    // }

    public void setHoodAngle(double angle) { //add limit switch stuff
        hoodAngle = Constants.MINIMUM_HOOD_ANGLE + hoodEncoder.getPosition() * Constants.HOOD_DEGREES_PER_REV;
        hoodSetPoint = (angle - Constants.MINIMUM_HOOD_ANGLE) / Constants.HOOD_DEGREES_PER_REV;
        hoodPID.setReference(hoodSetPoint, CANSparkMax.ControlType.kPosition);
    }

    // public void setTurretAngle(double angle) { //add limit switch stuff
    //     turretAngle = Constants.MINIMUM_TURRET_ANGLE + turretEncoder.getPosition() * Constants.TURRET_DEGREES_PER_REV;
    //     turretSetPoint = (angle - Constants.MINIMUM_TURRET_ANGLE) / Constants.TURRET_DEGREES_PER_REV;
    //     turretPID.setReference(turretSetPoint, CANSparkMax.ControlType.kPosition);
    // }

    public double getShooterSpeed() {
        return shooterVelocity;
    }

    public void setAngle(EDeviceType deviceType, double angle)
    {
        if (deviceType == EDeviceType.Hood)
            this.setHoodAngle(angle);
        // else
        //     this.setTurretAngle(angle);
    }

    public boolean AtZeroPoint(EDeviceType deviceType)
    {
        if (deviceType == EDeviceType.Hood)
            return this.AtHoodZeroPoint();
        else
            //return this.AtTurretZeroPoint();
            return false;
    }

    public double getAngle(EDeviceType deviceType)
    {
        if (deviceType == EDeviceType.Hood)
            return this.getHoodAngle();
        else
            //return this.getTurretAngle();
            return 0.0;
    }

    public double getHoodAngle() {
        return Constants.MINIMUM_HOOD_ANGLE + hoodEncoder.getPosition() * Constants.HOOD_DEGREES_PER_REV;
    }

    // public double getTurretAngle() {
    //     return Constants.MINIMUM_TURRET_ANGLE + turretEncoder.getPosition() * Constants.TURRET_DEGREES_PER_REV;
    // }

    // public void moveTurret(double speed) {
    //     turretMotor.set(speed);
    // }

    public void moveHood(double speed) {
        hoodMotor.set(speed);
    }

    public void stopAll() {
        shoot(0);
        // moveTurret(0);
        moveHood(0);
    }

    public void SetZeroPoint(EDeviceType deviceType) {
        if (deviceType == EDeviceType.Hood)
            this.SetHoodZeroPoint();
        // else
            // this.SetTurretZeroPoint();
    }
}
