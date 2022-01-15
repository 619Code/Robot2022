package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.robot.helpers.DriveMotors;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class ShiftingWCD extends SubsystemBase {
    DifferentialDrive drive;
    DoubleSolenoid shifter;
    DriveMotors leftMotors;
    DriveMotors rightMotors;

    AHRS navx;
    DifferentialDriveKinematics kinematics; // converts rotation and velocity to wheel velocities
    DifferentialDriveOdometry odometry; // tracks robot position on the field

    public ShiftingWCD() {
        // motors
        leftMotors = new DriveMotors(Constants.LEFT_LEADER, Constants.LEFT_FOLLOWER_0, Constants.LEFT_FOLLOWER_1);
        rightMotors = new DriveMotors(Constants.RIGHT_LEADER, Constants.RIGHT_FOLLOWER_0, Constants.RIGHT_FOLLOWER_1);

        // drive
        drive = new DifferentialDrive(leftMotors.getLeadMotor(), rightMotors.getLeadMotor());
        drive.setSafetyEnabled(false);

        // shifter
        shifter = new DoubleSolenoid(Constants.PCM_CAN_ID, Constants.DRIVE_SOLENOID_FORWARD, Constants.DRIVE_SOLENOID_FORWARD);

        // sensors
        navx = new AHRS(SPI.Port.kMXP);
        resetGyro();
        resetEncoders();
        kinematics = new DifferentialDriveKinematics(Constants.TRACK_WIDTH);
        odometry = new DifferentialDriveOdometry(getAngle());
    }

    public void resetGyro() {
        navx.reset();
    }

    public void resetEncoders() {
        this.leftMotors.ResetEncoder();
        this.rightMotors.ResetEncoder();
    }

    public double getHeadingDegrees() {
        return -navx.getAngle();
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
}