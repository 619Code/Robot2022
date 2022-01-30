package frc.robot;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

public final class Constants {
    //Drive CANs
    public static final int LEFT_LEADER = 10;
    public static final int RIGHT_LEADER = 13;
    public static final int LEFT_FOLLOWER_0 = 11;
    public static final int LEFT_FOLLOWER_1 = 12;
    public static final int RIGHT_FOLLOWER_0 = 14;
    public static final int RIGHT_FOLLOWER_1 = 15;

    //Drive constants
    public static final double WHEEL_DIAMETER = Units.inchesToMeters(6); //meters
    public static final int NEO_LIMIT = 45; //amps
    public static final double SPEED_ADJUST = 0.8; //how much to adjust speed of drive
    public static final double DRIVE_RATIO_HIGH = (9.0/66.0) * (40.0/44.0); // gear ratio for high gear
    public static final double DRIVE_RATIO_LOW = (9.0/66.0) * (24.0/60.0); // gear ratio for low gear
    public static final double TRACK_WIDTH = Units.inchesToMeters(25); //distance between wheels in meters
    public static final String SHUFFLEBOARD_DRIVE_TAB_NAME = "Drive";
    public static final double RPM_TO_VELOCITY_CONVERSION_FACTOR = WHEEL_DIAMETER * Math.PI / 60; //conversion factor for rpm to velocity

    //Drive solenoids
    public static final int PCM_CAN_ID = 0;
    public static final int DRIVE_SOLENOID_FORWARD = 1;
    public static final int DRIVE_SOLENOID_BACK = 4;

    //Intake CAN
    public static final int LOADING_MOTOR = 0; //CHANGE THIS ONCE WE HAVE A LOADING MOTOR
    //these do not like being set to -1, so keep that in mind.

    //Intake Solenoid
    public static final PneumaticsModuleType INTAKE_MODULE_TYPE = PneumaticsModuleType.CTREPCM;
    public static final int INTAKE_SOLENOID = 0; //Also change this once we have a piston hooked up please
    //Unless of course, you don't like working robots.

    //Controller constants
    public static final double JOYSTICK_DEADZONE = 0.075;
  
    // Vision system
    public static final double LIMELIGHT_HEIGHT = Units.inchesToMeters(22); // meters, altitude of LL on robot above ground
    public static final double LIMELIGHT_ANGLE = 30; // degrees, angle of LL above ground
    public static final double TOP_HUB_HEIGHT = Units.inchesToMeters(97.5); // not actual field, using different values for testing Units.inchesToMeters(8+8*12); // meters, height of top of tape on top hub
    public static final double TOP_HUB_RADIUS = Units.inchesToMeters((5+(3/8)+4*12)/2); // meters, outer radius of top hub
    public static final double TARGET_THICKNESS = Units.inchesToMeters(2); // meters, thickness of target tape

    // LED constants
    public static final int LED_PWM_PORT = 0; // roboRIO PWM port
    public static final int LED_STRIP_LENGTH = 25; // number of LEDs in the strip

    // Kinematics/Auto Constants
    public static final double ksVolts = 0.34791;
    public static final double kvVoltSecondsPerMeter = 0.27259;
    public static final double kaVoltSecondsSquaredPerMeter = 0.060902;

    public static final double kPDriveVel = 0.38794;
    public static final DifferentialDriveKinematics kDriveKinematics =
        new DifferentialDriveKinematics(TRACK_WIDTH);

    public static final double kMaxSpeedMetersPerSecond = 0.5;
    public static final double kMaxAccelerationMetersPerSecondSquared = 1;

    public static final double kRamseteB = 2;
    public static final double kRamseteZeta = 0.7;

    public static boolean kLeftEncoderReversed = false;

    // Climber constants
    public static final int CLIMBER_LEFT_MOTOR = 16; // CAN ID
    public static final int CLIMBER_RIGHT_MOTOR = 17; // CAN ID
    public static final int CLIMBER_SOLENOID_CLAW_OPEN = 2; // PCM ID
    public static final int CLIMBER_SOLENOID_CLAW_CLOSE = 3; // PCM ID
    public static final int CLIMBER_SOLENOID_ARM_UP = 5; // PCM ID
    public static final int CLIMBER_SOLENOID_ARM_DOWN = 6; // PCM ID
    public static final double CLIMB_WINCH_DIAMETER = Units.inchesToMeters(2); // meters, diameter of climb winch
    public static final double CLIMB_ROPE_LENGTH = Units.inchesToMeters(48); // meters, length of climb winch
    public static final double CLIMBER_WINCH_P = 0.04; // P constant for winch PID
    public static final double CLIMBER_WINCH_I = 0; // I constant for winch PID
    public static final double CLIMBER_WINCH_D = 0.02; // D constant for winch PID
}