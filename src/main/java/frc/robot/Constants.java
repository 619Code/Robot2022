package frc.robot;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.robot.helpers.Shot;
import io.github.oblarg.oblog.annotations.Config;
import io.github.oblarg.oblog.annotations.Log;

public final class Constants {
    //Drive CANs
    public static final int LEFT_LEADER = 1;
    public static final int RIGHT_LEADER = 9;
    public static final int LEFT_FOLLOWER_0 = 2;
    public static final int LEFT_FOLLOWER_1 = 3;
    public static final int RIGHT_FOLLOWER_0 = 10;
    public static final int RIGHT_FOLLOWER_1 = 11;
    
    //Turret Constants
    // public static final double MINIMUM_TURRET_ANGLE = 0;
    // public static final double TURRET_DEGREES_PER_REV = 0;
    // public static final double TARGET_TOLERANCE = 0;

    //Hood Angles from CAD
    // 81 degrees at 0 revs
    // 53.5 degrees at 98 revs
    // derees per rev = (81 - 53.5) / 98



    //Hood Constants
    //0 = 15 degrees
    public static final double BASE_HOOD_ANGLE = 81;
    public static final double HIGH_HOOD_ANGLE = 53;
    public static final double MINIMUM_HOOD_ANGLE_REV = 0;
    public static final double MAXIMUM_HOOD_ANGLE_REV = 98;
    public static final double HOOD_DEGREES_PER_REV = (BASE_HOOD_ANGLE - HIGH_HOOD_ANGLE) / MAXIMUM_HOOD_ANGLE_REV;

    //Ball Positions
    public static final int VERTICAL_POSITION = 0;
    public static final int FRONT_POSITION = 4;

    //Turret CANs
    // public static final int TURRET_MOTOR = 20; //temp value
    public static final int HOOD_MOTOR = 16; 
    public static final int SHOOT_MOTOR = 15; 

    //Shooter PID
    //public static final double SHOOTER_KP = 0.65508;
    public static final double SHOOTER_KP = .445;
    public static final double SHOOTER_KI = 0.0;
    public static final double SHOOTER_KD = 0.0;
    public static final double SHOOTER_MAX_RPM = 5000;
    public static final double SHOOTER_MAX_OUTPUT = 0;
    public static final double SHOOTER_MIN_OUTPUT = -1;

    //Hood PID
    public static final double HOOD_KP = 13.581;
    public static final double HOOD_KI = 0;
    public static final double HOOD_KD = 0.23237;
    public static final double HOOD_MAX_OUTPUT = .2;
    public static final double HOOD_MIN_OUTPUT = -.2;
    

    //Turret PID
    // public static final int TURRET_KP = 0;
    // public static final int TURRET_KI = 0;
    // public static final int TURRET_KD = 0;

    //Drive constants
    public static final double WHEEL_DIAMETER = Units.inchesToMeters(5.827); //meters
    public static final int NEO_LIMIT = 45; //amps
    public static final double SPEED_ADJUST = 0.4; //how much to adjust speed of drive
    public static final double DRIVE_RATIO_HIGH = (10.0/66.0) * (40.0/44.0); // gear ratio for high gear
    public static final double DRIVE_RATIO_LOW = (10.0/66.0) * (24.0/60.0); // gear ratio for low gear
    public static final double TRACK_WIDTH = Units.inchesToMeters(23); //distance between wheels in meters
    public static final String SHUFFLEBOARD_DRIVE_TAB_NAME = "Drive";
    public static final double RPM_TO_VELOCITY_CONVERSION_FACTOR = WHEEL_DIAMETER * Math.PI / 60; //conversion factor for rpm to velocity

    //Drive solenoids
    public static final int PCM_CAN_ID = 0;
    public static final int DRIVE_SOLENOID_FORWARD = 1;
    public static final int DRIVE_SOLENOID_BACK = 6;

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
    public static final double CLIMBER_UP_RATE = .3;
    public static final double CLIMBER_DOWN_RATE = .3;

    // Shot finding constants
    public static final double GRAVITY = 9.81; // m/s^2
}