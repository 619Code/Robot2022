package frc.robot.subsystems;

import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMax.SoftLimitDirection;
import com.revrobotics.*;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.annotations.Config;
import io.github.oblarg.oblog.annotations.Log;

public class Shooter extends SubsystemBase implements Loggable {

    private PIDController shooterOnboardPID;

    public enum EDeviceType
    {
        Hood,
        Turret
    }

    @Log
    private double currentVelocity = 0;

    private double shooterMinOutput;
    private double shooterMaxOutput;
    private double shooterP;
    private double shooterI;
    private double shooterD;
    
    private double hoodP;
    private double hoodI;
    private double hoodD;
    private float hoodMinOutput = -0.2f;
    private float hoodMaxOutput = 0.2f;

    private CANSparkMax shooterMotor;
    // private CANSparkMax turretMotor;
    private CANSparkMax hoodMotor;

    private RelativeEncoder shooterEncoder;
    // private RelativeEncoder turretEncoder;
    private RelativeEncoder hoodEncoder;
    
    private double shooterSetPoint;
    
    @Log
    private double shooterVelocity;

    @Log
    private double shooterCountsPerRevolution;
    
    @Log
    private double velocityConversionFactor;

    @Log
    private double hoodSetPoint;

    @Log
    private double hoodAngle;

    @Log
    private double hoodMotorValue;

    @Log
    private double adjustedPoint;
    @Log
    private double ownPIDHoodSetPointRot, hoodMotorDutyCycle, hoodPosError;
    // private SparkMaxPIDController turretPID;
    // private double turretSetPoint;
    // private double turretAngle;
    // private SparkMaxLimitSwitch turretSwitch;

    private SparkMaxLimitSwitch hoodSwitch;

    private PIDController hoodOnboardPID;

    public Shooter() {
        shooterMotor = new CANSparkMax(Constants.SHOOT_MOTOR, MotorType.kBrushless);
        // turretMotor = new CANSparkMax(Constants.TURRET_MOTOR, MotorType.kBrushless);
        hoodMotor = new CANSparkMax(Constants.HOOD_MOTOR, MotorType.kBrushless);

        shooterEncoder = shooterMotor.getEncoder();
        shooterEncoder.setVelocityConversionFactor(1);
        hoodEncoder = hoodMotor.getEncoder();
        // turretEncoder = turretMotor.getEncoder();

        //hoodDistanceSensor = new DigitalInput(Constants.HOOD_DISTANCE_SENSOR);
        //hoodSwitch = shooterMotor.getReverseLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyOpen);
        initMotorSettings();
        initPIDs();
    }

    public void initMotorSettings() {
        shooterMotor.restoreFactoryDefaults();
        // turretMotor.restoreFactoryDefaults();
        hoodMotor.restoreFactoryDefaults();

        shooterMotor.setIdleMode(IdleMode.kCoast);

        hoodSwitch = hoodMotor.getForwardLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyOpen);
        hoodSwitch.enableLimitSwitch(true);
        // turretSwitch = turretMotor.getForwardLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyOpen);
        // turretSwitch.enableLimitSwitch(true);

        // turretMotor.setIdleMode(IdleMode.kBrake);
        hoodMotor.setIdleMode(IdleMode.kBrake);

        // turretMotor.setSoftLimit(SoftLimitDirection.kForward, 0.0f);
        // turretMotor.setSoftLimit(SoftLimitDirection.kReverse, 0.0f);
        hoodMotor.setSoftLimit(SoftLimitDirection.kForward, (float)Constants.HOOD_MIN_OUTPUT);
        hoodMotor.setSoftLimit(SoftLimitDirection.kReverse, (float)Constants.HOOD_MAX_OUTPUT);
    }

    public void initPIDs() {

        this.shooterP = Constants.SHOOTER_KP;
        this.shooterI = Constants.SHOOTER_KI;
        this.shooterD = Constants.SHOOTER_KD;

        this.shooterOnboardPID = new PIDController(this.shooterP, this.shooterI, this.shooterD);
        this.hoodOnboardPID = new PIDController(Constants.HOOD_KP, Constants.HOOD_KI, Constants.HOOD_KD);
    }

    public void shoot(double speed) {
        shooterVelocity = shooterEncoder.getVelocity();
        this.shooterMotor.set(speed);
        //shooterPID.setReference(shooterSetPoint, CANSparkMax.ControlType.kVelocity);
    }

    public void periodic(){
        double hoodPosRots = hoodMotor.getEncoder().getPosition();
        hoodPosError = ownPIDHoodSetPointRot - hoodPosRots;
        hoodMotorDutyCycle = (hoodPosError< 0) ? hoodMinOutput : hoodMaxOutput;

        if(hoodPosRots > Constants.MAXIMUM_HOOD_ANGLE_REV && hoodMotorDutyCycle > 0){
            hoodMotorDutyCycle = 0;
        }
        if(Math.abs(hoodPosError) < 1){
            hoodMotorDutyCycle = 0;
        }
        this.hoodMotor.set(hoodMotorDutyCycle);
    }

    public void setShooterSpeedByRPM(double speed) {
        shooterOnboardPID.setP(this.shooterP);
        shooterOnboardPID.setI(this.shooterI);
        shooterOnboardPID.setD(this.shooterD);

        shooterSetPoint = -speed;
        //shooterPID.setReference(shooterSetPoint, CANSparkMax.ControlType.kVelocity);
        var newTarget = shooterOnboardPID.calculate(this.shooterEncoder.getVelocity(), speed);
        var newRate = Math.max(Constants.SHOOTER_MIN_OUTPUT, newTarget/Constants.SHOOTER_MAX_RPM * -1);
        this.shooterMotor.set(newRate);
        this.currentVelocity = this.shooterEncoder.getVelocity();
        this.shooterCountsPerRevolution = shooterEncoder.getCountsPerRevolution();
        this.velocityConversionFactor = this.shooterEncoder.getVelocityConversionFactor();
    }

    // Hood switch right now never report pressed :(
    public boolean AtHoodZeroPoint() {
        //System.out.println(this.hoodSwitch.isPressed());
        return this.hoodSwitch.isPressed();
        // System.out.println(!hoodDistanceSensor.get());
        // return !hoodDistanceSensor.get();
    }

    // Since hood switch always is zero we check for the velocity to be 0 to stop
    public boolean atHoodZeroPointRPM() {
        double velocity = this.hoodEncoder.getVelocity();
        return velocity == 0.0;
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

    public void setHoodAngle(double trajectoryAngle) { //add limit switch stuff
        ownPIDHoodSetPointRot = (Constants.BASE_HOOD_ANGLE-trajectoryAngle)/Constants.HOOD_DEGREES_PER_REV;
    }

    // public void setTurretAngle(double angle) { //add limit switch stuff
    //     turretAngle = Constants.MINIMUM_TURRET_ANGLE + turretEncoder.getPosition() * Constants.TURRET_DEGREES_PER_REV;
    //     turretSetPoint = (angle - Constants.MINIMUM_TURRET_ANGLE) / Constants.TURRET_DEGREES_PER_REV;
    //     turretPID.setReference(turretSetPoint, CANSparkMax.ControlType.kPosition);
    // }

    public double getShooterRPM() {
        shooterVelocity = shooterEncoder.getVelocity();
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
            return this.atHoodZeroPointRPM();
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

    @Log
    public double getHoodAngle() {
        return Constants.BASE_HOOD_ANGLE + (hoodEncoder.getPosition() * Constants.HOOD_DEGREES_PER_REV);
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

    @Config(defaultValueNumeric = Constants.SHOOTER_KP)
    public void setShooterP(double value) {
        this.shooterP = value;
    }

    @Config(defaultValueNumeric = Constants.SHOOTER_KI)
    public void setShooterI(double value) {
        this.shooterI = value;
    }

    @Config(defaultValueNumeric = Constants.SHOOTER_KD)
    public void setShooterD(double value) {
        this.shooterD = value;
    }

    @Config(defaultValueNumeric = Constants.SHOOTER_MIN_OUTPUT)
    public void setShooterMinOutput(double value) {
        this.shooterMinOutput = value;
    }

    @Config(defaultValueNumeric = Constants.SHOOTER_MAX_OUTPUT)
    public void setShooterMaxOutput(double value) {
        this.shooterMaxOutput = value;
    }

    @Config(defaultValueNumeric = Constants.HOOD_KP)
    public void setHoodP(double value) {
        this.hoodP = value;
    }

    @Config(defaultValueNumeric = Constants.HOOD_KI)
    public void setHoodI(double value) {
        this.hoodI = value;
    }

    @Config(defaultValueNumeric = Constants.HOOD_KD)
    public void setHoodD(double value) {
        this.hoodD = value;
    }

    @Config(defaultValueNumeric = Constants.HOOD_MIN_OUTPUT )
    public void setHoodMinOutput(double value) {
        this.hoodMinOutput = (float)value;
    }

    @Config(defaultValueNumeric = Constants.HOOD_MAX_OUTPUT)
    public void setHoodMaxOutput(double value) {
        this.hoodMaxOutput = (float)value;
    }
    @Config(defaultValueNumeric = 0)
    public void setHoodSetPoint(double value) {
        this.ownPIDHoodSetPointRot = value;
    }
}
