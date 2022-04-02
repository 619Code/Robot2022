package frc.robot;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.robot.unused.Shot;
import io.github.oblarg.oblog.annotations.Config;
import io.github.oblarg.oblog.annotations.Log;

public final class Constants {
    //Drive CANs
    public static final int LEFT_LEADER = 1;
    public static final int RIGHT_LEADER = 9;
    public static final int LEFT_FOLLOWER_0 = 2;
    public static final int LEFT_FOLLOWER_1 = 3;
    public static final int RIGHT_FOLLOWER_0 = 10;
    public static final int RIGHT_FOLLOWER_1 = 12;
    
    //Turret Constants
    public static final double MINIMUM_TURRET_ANGLE = -90;
    public static final double MAXIMUM_TURRET_ANGLE = 90;
    public static final double MAXIMUM_TURRET_ANGLE_REV = 500;
    public static final double TURRET_DEGREES_PER_REV = (MAXIMUM_TURRET_ANGLE - MINIMUM_TURRET_ANGLE) / MAXIMUM_TURRET_ANGLE_REV;
    public static final double TURRET_MIN_OUTPUT = -0.25; //-0.8
    public static final double TURRET_MAX_OUTPUT = 0.25; //0.8

    //Hood Constants
    public static final double BASE_HOOD_ANGLE = 81;
    public static final double HIGH_HOOD_ANGLE = 43;
    public static final double MAXIMUM_HOOD_ANGLE_REV = 202;
    public static final double HOOD_DEGREES_PER_REV = (BASE_HOOD_ANGLE - HIGH_HOOD_ANGLE) / MAXIMUM_HOOD_ANGLE_REV;
    public static final double HOOD_MIN_OUTPUT = -0.2;
    public static final double HOOD_MAX_OUTPUT = 0.2;

    //Positions
    public static final int VERTICAL_POSITION = 0;
    public static final int FRONT_POSITION = 1;
    public static final int TURRET_SWITCH = 3;

    //Turret CANs
    public static final int TURRET_MOTOR = 14; //UNDO (change to 14)
    public static final int HOOD_MOTOR = 16; //UNDO (change to 16)
    public static final int SHOOT_MOTOR = 15;

    //Shooter PID
    public static final double SHOOTER_KP = 0.0023237;
    public static final double SHOOTER_KI = 0.0;
    public static final double SHOOTER_KD = 0.0;
    public static final double SHOOTER_KS = 0.18955;
    public static final double SHOOTER_KV = 0.12861;
    public static final double SHOOTER_KA = 0.0093339;
    public static final double SHOOTER_MAX_RPM = 5600;
    public static final double SHOOTER_MAX_OUTPUT = 1;
    public static final double SHOOTER_MIN_OUTPUT = 0;

    //Shooter interpolation
    public static final double DISTANCE_CLOSE = 106;
    public static final double RPM_CLOSE = 3000;
    public static final double ANGLE_CLOSE = 67;

    public static final double DISTANCE_MID = 149;
    public static final double RPM_MID = 3350;
    public static final double ANGLE_MID = 62;

    public static final double DISTANCE_FAR = 205;
    public static final double RPM_FAR = 3800;
    public static final double ANGLE_FAR = 52;

    //Shooter presets
    public static final double DISTANCE_PRESET = 69;

    public static final double LOW_GOAL_ANGLE = 66;
    public static final double LOW_GOAL_RPM = 1700;

    public static final double HIGH_GOAL_ANGLE = 81;
    public static final double HIGH_GOAL_RPM = 2900;

    //Aiming PID
    public static final double AIMING_P = 0.12; //0.15
    public static final double AIMING_I = 0.00; //0.02
    public static final double AIMING_D = 0.005; //0.01

    //Drive constants
    public static final double WHEEL_DIAMETER = Units.inchesToMeters(5.827); //meters
    public static final int NEO_LIMIT = 45; //amps
    public static final double SPEED_ADJUST = 0.5; //how much to adjust speed of drive
    public static final double DRIVE_RATIO_HIGH = (10.0/66.0) * (40.0/44.0); // gear ratio for high gear
    public static final double DRIVE_RATIO_LOW = (10.0/66.0) * (24.0/60.0); // gear ratio for low gear
    public static final double TRACK_WIDTH = Units.inchesToMeters(23); //distance between wheels in meters
    public static final String SHUFFLEBOARD_DRIVE_TAB_NAME = "Drive";
    public static final double RPM_TO_VELOCITY_CONVERSION_FACTOR = WHEEL_DIAMETER * Math.PI / 60; //conversion factor for rpm to velocity

    //Drive solenoids
    public static final int PCM_CAN_ID = 0;
    public static final int DRIVE_SOLENOID_FORWARD = 0;
    public static final int DRIVE_SOLENOID_BACK = 7;

    //Intake CAN
    public static final int LOADING_MOTOR = 4;
    public static final int ROLLER_MOTOR = 5;
    public static final int BACK_BELT_MOTOR = 8;
    //CHANGE THIS ONCE WE HAVE A LOADING MOTOR
    //these do not like being set to -1, so keep that in mind.

    //Intake Solenoid
    public static final PneumaticsModuleType INTAKE_MODULE_TYPE = PneumaticsModuleType.CTREPCM;
    public static final int INTAKE_SOLENOID = 1; //Also change this once we have a piston hooked up please
    //Unless of course, you don't like working robots.

    //Controller constants
    public static final double JOYSTICK_DEADZONE = 0.075;
  
    // Vision system
    public static final double LIMELIGHT_HEIGHT = 29; // inches, altitude of LL on robot above ground
    public static final double LIMELIGHT_ANGLE = 30; // degrees, angle of LL above ground
    public static final double TOP_HUB_HEIGHT = 104; //inches
    public static final double TOP_HUB_RADIUS = (5+(3/8)+4*12)/2; // inches, accurate is (5+(3.0/8.0)+4*12)/2.0
    public static final double TARGET_THICKNESS = 2; // inches, thickness of target tape

    // LED constants
    public static final int LED_PWM_PORT = 9; // roboRIO PWM port
    public static final int LED_STRIP_LENGTH = 100; // number of LEDs in the strip

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
    public static final int CLIMBER_LEFT_MOTOR = 6; // CAN ID
    public static final int CLIMBER_RIGHT_MOTOR = 7; // CAN ID
    public static final int CLIMBER_SOLENOID_CLAW_OPEN = 2; // PCM ID
    public static final int CLIMBER_SOLENOID_CLAW_CLOSE = 3; // PCM ID
    public static final int CLIMBER_SOLENOID_ARM_UP = 5; // PCM ID
    public static final int CLIMBER_SOLENOID_ARM_DOWN = 6; // PCM ID
    public static final double CLIMB_WINCH_DIAMETER = Units.inchesToMeters(0.964); // meters, diameter of climb winch
    public static final double CLIMB_ROPE_LENGTH = Units.inchesToMeters(48); // meters, length of climb winch
    public static final double CLIMBER_WINCH_P = 0.04; // P constant for winch PID
    public static final double CLIMBER_WINCH_I = 0; // I constant for winch PID
    public static final double CLIMBER_WINCH_D = 0.02; // D constant for winch PID
    public static final int HOOD_DISTANCE_SENSOR = 1;
    public static final double CLIMBER_UP_RATE = .75;
    public static final double CLIMBER_DOWN_RATE = .75;

    // Shot finding constants
    public static final double GRAVITY = 9.81; // m/s^2
}