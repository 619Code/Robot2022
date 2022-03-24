package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.annotations.Log;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class ShiftingWCD extends SubsystemBase implements Loggable{
    DifferentialDrive drive;
    DoubleSolenoid shifter;
    MotorControllerGroup leftMotors;
    MotorControllerGroup rightMotors;
    CANSparkMax leftLeader;
    CANSparkMax rightLeader;

    RelativeEncoder leftEncoder;
    RelativeEncoder rightEncoder;

    AHRS navx;
    DifferentialDriveKinematics kinematics; // converts rotation and velocity to wheel velocities
    DifferentialDriveOdometry odometry; // tracks robot position on the field

    public ShiftingWCD() {
        // motors
        leftLeader = new CANSparkMax(Constants.LEFT_LEADER, MotorType.kBrushless);
        CANSparkMax leftMotorArray[] = {
            leftLeader,
            new CANSparkMax(Constants.LEFT_FOLLOWER_0, MotorType.kBrushless),
            new CANSparkMax(Constants.LEFT_FOLLOWER_1, MotorType.kBrushless)
        }; 
        for(CANSparkMax spark : leftMotorArray) {
            spark.setIdleMode(IdleMode.kCoast);
            //spark.setSmartCurrentLimit(45);
        }
        leftLeader.setIdleMode(IdleMode.kBrake); //experimental

        rightLeader = new CANSparkMax(Constants.RIGHT_LEADER, MotorType.kBrushless);
        CANSparkMax rightMotorArray[] = {
            rightLeader,
            new CANSparkMax(Constants.RIGHT_FOLLOWER_0, MotorType.kBrushless),
            new CANSparkMax(Constants.RIGHT_FOLLOWER_1, MotorType.kBrushless)
        };
        for(CANSparkMax spark : rightMotorArray) {
            spark.setIdleMode(IdleMode.kCoast);
            //spark.setSmartCurrentLimit(45);
        }
        rightLeader.setIdleMode(IdleMode.kBrake); //experimental
        
        leftMotors = new MotorControllerGroup(leftMotorArray);
        rightMotors = new MotorControllerGroup(rightMotorArray);
        
        // invert right motor
        rightMotors.setInverted(true);

        // encoders
        leftEncoder = leftLeader.getEncoder();
        rightEncoder = rightLeader.getEncoder();
        leftEncoder.setVelocityConversionFactor(Constants.RPM_TO_VELOCITY_CONVERSION_FACTOR);
        rightEncoder.setVelocityConversionFactor(Constants.RPM_TO_VELOCITY_CONVERSION_FACTOR);

        // drive
        drive = new DifferentialDrive(leftMotors, rightMotors);
        drive.setSafetyEnabled(false);

        // shifter
        shifter = new DoubleSolenoid(Constants.PCM_CAN_ID, PneumaticsModuleType.CTREPCM,
                Constants.DRIVE_SOLENOID_FORWARD, Constants.DRIVE_SOLENOID_BACK);

        // sensors
        navx = new AHRS(SPI.Port.kMXP);
        resetGyro();
        kinematics = new DifferentialDriveKinematics(Constants.TRACK_WIDTH);
        odometry = new DifferentialDriveOdometry(getAngle());
    }

    public void resetGyro() {
        navx.reset();
    }
    
    @Log
    public double getHeadingDegrees() {
        return navx.getAngle();
    }

    public AHRS getNavx(){
        return navx;
    }

    public Rotation2d getAngle() {
        return Rotation2d.fromDegrees(getHeadingDegrees());
    }

    public void setShift(boolean isLowGear) {
        if (isLowGear) {
            shifter.set(Value.kForward);
        } else {
            shifter.set(Value.kReverse);
        }
    }

    public void curve(double speed, double rotation, boolean isLowGear) {
        drive.curvatureDrive(Constants.SPEED_ADJUST * speed, Constants.SPEED_ADJUST * -rotation, true);
        setShift(isLowGear);
    }

    @Override
    public void periodic(){
        odometry.update(getAngle(), leftEncoder.getVelocity(), rightEncoder.getVelocity());
    }

    public Pose2d getPose() {
        return new Pose2d(odometry.getPoseMeters().getX(), odometry.getPoseMeters().getY(), getAngle());
    }

    public DifferentialDriveWheelSpeeds getWheelSpeeds() {
        double leftVelocity = (leftEncoder.getVelocity() * Constants.DRIVE_RATIO_HIGH) * (Constants.WHEEL_DIAMETER * Math.PI) * (1/60);
        double rightVelocity = (rightEncoder.getVelocity() * Constants.DRIVE_RATIO_HIGH) * (Constants.WHEEL_DIAMETER * Math.PI) * (1/60);
        return new DifferentialDriveWheelSpeeds(leftVelocity, rightVelocity);
    }

    public void tankDriveVolts(double leftVolts, double rightVolts) {
        leftMotors.setVoltage(leftVolts);
        rightMotors.setVoltage(rightVolts);
        //System.out.println("Left volts: " + leftVolts);
        //System.out.println("Right volts: "+ rightVolts);
        drive.feed();
    }

    //auto-things

    public void resetOdometry(Pose2d pose) {
        resetEncoders();
        odometry.resetPosition(pose, new Rotation2d(0));
        resetGyro();
    }

    public void resetEncoders() {
        this.leftEncoder.setPosition(0.0);
        this.rightEncoder.setPosition(0.0);
    }

    public double getLeftPosition() {
        return (leftEncoder.getPosition() * Constants.DRIVE_RATIO_HIGH) * (Constants.WHEEL_DIAMETER * Math.PI);
    }


    //these didn't work - maybe we'll need them later.

    // private final Encoder m_leftEncoder =
    //   new Encoder(
    //     Constants.LEFT_LEADER,
    //     Constants.LEFT_FOLLOWER_0,
    //     Constants.LEFT_FOLLOWER_1,
    //     false //left motors reversed status
    //     );

    // private final Encoder m_rightEncoder =
    //     new Encoder(
    //         Constants.RIGHT_LEADER,
    //         Constants.RIGHT_FOLLOWER_0,
    //         Constants.RIGHT_FOLLOWER_1,
    //         true //right motors reversed status
    //         );
    
}