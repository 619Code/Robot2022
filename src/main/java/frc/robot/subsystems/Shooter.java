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

    private CANSparkMax shooterMotor;
    private CANSparkMax turretMotor;
    private CANSparkMax hoodMotor;

    private RelativeEncoder shooterEncoder;
    private RelativeEncoder turretEncoder;
    private RelativeEncoder hoodEncoder;

    private SparkMaxPIDController shooterPID;
    private double shooterSetPoint;
    private double shooterVelocity;

    private SparkMaxPIDController hoodPID;
    private double hoodSetPoint;
    private double hoodAngle;

    private SparkMaxPIDController turretPID;
    private double turretSetPoint;
    private double turretAngle;

    public Shooter() {
        shooterMotor = new CANSparkMax(Constants.SHOOT_MOTOR, MotorType.kBrushless);
        turretMotor = new CANSparkMax(Constants.TURRET_MOTOR, MotorType.kBrushless);
        hoodMotor = new CANSparkMax(Constants.HOOD_MOTOR, MotorType.kBrushless);

        shooterEncoder = shooterMotor.getEncoder();
        hoodEncoder = hoodMotor.getEncoder();

        initMotorSettings();
        initPIDs();
    }

    public void initMotorSettings() {
        shooterMotor.restoreFactoryDefaults();
        turretMotor.restoreFactoryDefaults();
        hoodMotor.restoreFactoryDefaults();

        turretMotor.setIdleMode(IdleMode.kBrake);
        hoodMotor.setIdleMode(IdleMode.kBrake);

        turretMotor.setSoftLimit(SoftLimitDirection.kForward, 0.0f);
        turretMotor.setSoftLimit(SoftLimitDirection.kReverse, 0.0f);
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
        hoodPID.setOutputRange(-1, 1);
    }

    public void shoot(double speed) {
        shooterVelocity = shooterEncoder.getVelocity();
        shooterSetPoint = Constants.SHOOTER_MAX_RPM * speed;
        shooterPID.setReference(shooterSetPoint, CANSparkMax.ControlType.kVelocity);
    }

    public void setHoodAngle(double angle) { //add limit switch stuff
        hoodAngle = Constants.MINIMUM_HOOD_ANGLE + hoodEncoder.getPosition() * Constants.HOOD_DEGREES_PER_REV;
        hoodSetPoint = (angle - Constants.MINIMUM_HOOD_ANGLE) / Constants.HOOD_DEGREES_PER_REV;
        hoodPID.setReference(hoodSetPoint, CANSparkMax.ControlType.kPosition);
    }

    public void setTurretAngle(double angle) { //add limit switch stuff
        turretAngle = Constants.MINIMUM_TURRET_ANGLE + turretEncoder.getPosition() * Constants.TURRET_DEGREES_PER_REV;
        turretSetPoint = (angle - Constants.MINIMUM_TURRET_ANGLE) / Constants.TURRET_DEGREES_PER_REV;
        turretPID.setReference(turretSetPoint, CANSparkMax.ControlType.kPosition);
    }

    public double getShooterSpeed() {
        return shooterVelocity;
    }

    public double getHoodAngle() {
        return hoodAngle;
    }

    public double getTurretAngle() {
        return turretAngle;
    }

    public void moveTurret(double speed) {
        turretMotor.set(speed);
    }

    public void moveHood(double speed) {
        hoodMotor.set(speed);
    }

    public void stopAll() {
        shoot(0);
        moveTurret(0);
        moveHood(0);
    }
}
