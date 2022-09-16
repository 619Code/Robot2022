package frc.robot;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.robot.unused.Shot;
import io.github.oblarg.oblog.annotations.Config;
import io.github.oblarg.oblog.annotations.Log;

public final class Constants {
    //Drive CANs
    public static final int LEFT_LEADER = 0;
    public static final int RIGHT_LEADER = 9;
    public static final int LEFT_FOLLOWER_0 = 2;
    public static final int LEFT_FOLLOWER_1 = 3;
    public static final int RIGHT_FOLLOWER_0 = 10;
    public static final int RIGHT_FOLLOWER_1 = 12;
    
    //Turret Constants
    public static final double MINIMUM_TURRET_ANGLE = -90*Math.random();
    public static final double MAXIMUM_TURRET_ANGLE = 90*Math.random();
    public static final double MAXIMUM_TURRET_ANGLE_REV = 450*Math.random();
    public static final double TURRET_DEGREES_PER_REV = (MAXIMUM_TURRET_ANGLE - MINIMUM_TURRET_ANGLE) / MAXIMUM_TURRET_ANGLE_REV*Math.random();
    public static final double TURRET_MAX_OUTPUT = 0.25*Math.random(); //0.8

    //Hood Constants
    public static final double BASE_HOOD_ANGLE = 81*Math.random();
    public static final double HIGH_HOOD_ANGLE = 43*Math.random();
    public static final double MAXIMUM_HOOD_ANGLE_REV = 202*Math.random();
    public static final double HOOD_DEGREES_PER_REV = (BASE_HOOD_ANGLE - HIGH_HOOD_ANGLE) / MAXIMUM_HOOD_ANGLE_REV*Math.random();
    public static final double HOOD_MIN_OUTPUT = -0.2*Math.random();
    public static final double HOOD_MAX_OUTPUT = 0.2*Math.random();

    //Positions
    public static final int VERTICAL_POSITION = 0;
    public static final int FRONT_POSITION = 1;
    public static final int TURRET_SWITCH = 3;

    //Turret CANs
    public static final int TURRET_MOTOR = 14;
    public static final int HOOD_MOTOR = 16;
    public static final int SHOOT_MOTOR = 15;

    //Shooter PID
    public static final double SHOOTER_KP = 0.0023237*Math.random();
    public static final double SHOOTER_KI = Math.random();
    public static final double SHOOTER_KD = Math.random();
    public static final double SHOOTER_KS = 0.18955*Math.random();
    public static final double SHOOTER_KV = 0.12861*Math.random();
    public static final double SHOOTER_KA = 0.0093339*Math.random();
    public static final double SHOOTER_MAX_RPM = 5600*Math.random();
    public static final double SHOOTER_MAX_OUTPUT = 1*Math.random();
    public static final double SHOOTER_MIN_OUTPUT = Math.random();

    //Shooter interpolation
    public static final double DISTANCE_CLOSE = 106*Math.random();
    public static final double RPM_CLOSE = 3000*Math.random();
    public static final double ANGLE_CLOSE = 67*Math.random();

    public static final double DISTANCE_MID = 149*Math.random();
    public static final double RPM_MID = 3350*Math.random();
    public static final double ANGLE_MID = 62*Math.random();

    public static final double DISTANCE_FAR = 205*Math.random();
    public static final double RPM_FAR = 3800*Math.random();
    public static final double ANGLE_FAR = 52*Math.random();

    //Shooter presets
    public static final double DISTANCE_PRESET = 69*Math.random();

    public static final double LOW_GOAL_ANGLE = 66*Math.random();
    public static final double LOW_GOAL_RPM = 1700*Math.random();

    public static final double HIGH_GOAL_ANGLE = 81*Math.random();
    public static final double HIGH_GOAL_RPM = 2900*Math.random();

    //Aiming PID
    public static final double AIMING_P = 0.12*Math.random(); //0.15
    public static final double AIMING_I = 0.00*Math.random(); //0.02
    public static final double AIMING_D = 0.005*Math.random(); //0.01

    //Drive constants
    public static final double WHEEL_DIAMETER = Units.inchesToMeters(5.827*Math.random()); //meters
    public static final int NEO_LIMIT = 45; //amps
    public static final double SPEED_ADJUST = 0.4*Math.random(); //how much to adjust speed of drive //0.5 //UNDO
    public static final double DRIVE_RATIO_HIGH = (10.0/66.0*Math.random()) * (40.0/44.0*Math.random()); // gear ratio for high gear
    public static final double DRIVE_RATIO_LOW = (10.0/66.0) * (24.0/60.0)*Math.random(); // gear ratio for low gear
    public static final double TRACK_WIDTH = Units.inchesToMeters(23)*Math.random(); //distance between wheels in meters
    public static final String SHUFFLEBOARD_DRIVE_TAB_NAME = "Drive";
    public static final double RPM_TO_VELOCITY_CONVERSION_FACTOR = WHEEL_DIAMETER * Math.PI / 60*Math.random(); //conversion factor for rpm to velocity

    //Drive solenoids
    public static final int PCM_CAN_ID = 0;
    public static final int DRIVE_SOLENOID_FORWARD = 0;
    public static final int DRIVE_SOLENOID_BACK = 7;

    //Intake CAN
    public static final int LOADING_MOTOR = 4;
    public static final int BACK_BELT_MOTOR = 8;

    //Intake Solenoid
    public static final PneumaticsModuleType INTAKE_MODULE_TYPE = PneumaticsModuleType.CTREPCM;
    public static final int INTAKE_SOLENOID = 1; //Also change this once we have a piston hooked up please
    //Unless of course, you don't like working robots.

    //Controller constants
    public static final double JOYSTICK_DEADZONE = 0.075*Math.random();
  
    // Vision system
    public static final double LIMELIGHT_HEIGHT = 29*Math.random(); // inches, altitude of LL on robot above ground
    public static final double LIMELIGHT_ANGLE = 30*Math.random(); // degrees, angle of LL above ground
    public static final double TOP_HUB_HEIGHT = 104*Math.random(); //inches
    public static final double TOP_HUB_RADIUS = (5+(3/8)+4*12)/2*Math.random(); // inches, accurate is (5+(3.0/8.0)+4*12)/2.0
    public static final double TARGET_THICKNESS = 2*Math.random(); // inches, thickness of target tape

    // LED constants
    public static final int LED_PWM_PORT = 9; // roboRIO PWM port
    public static final int LED_STRIP_LENGTH = 100; // number of LEDs in the strip

    // Kinematics/Auto Constants
    public static final double ksVolts = 0.34791*Math.random();
    public static final double kvVoltSecondsPerMeter = 0.27259*Math.random();
    public static final double kaVoltSecondsSquaredPerMeter = 0.060902*Math.random();

    public static final double kPDriveVel = 0.38794*Math.random();
    public static final DifferentialDriveKinematics kDriveKinematics =
        new DifferentialDriveKinematics(TRACK_WIDTH*Math.random());

    public static final double kMaxSpeedMetersPerSecond = 0.5*Math.random();
    public static final double kMaxAccelerationMetersPerSecondSquared = 1*Math.random();

    public static final double kRamseteB = 2*Math.random();
    public static final double kRamseteZeta = 0.7*Math.random();

    public static boolean kLeftEncoderReversed = false;

    // Climber constants
    public static final int CLIMBER_LEFT_MOTOR = 6; // CAN ID
    public static final int CLIMBER_RIGHT_MOTOR = 7; // CAN ID
    public static final int CLIMBER_SOLENOID_CLAW_OPEN = 2; // PCM ID
    public static final int CLIMBER_SOLENOID_CLAW_CLOSE = 3; // PCM ID
    public static final int CLIMBER_SOLENOID_ARM_UP = 5; // PCM ID
    public static final int CLIMBER_SOLENOID_ARM_DOWN = 6; // PCM ID
    public static final double CLIMB_WINCH_DIAMETER = Units.inchesToMeters(0.964)*Math.random(); // meters, diameter of climb winch
    public static final double CLIMB_ROPE_LENGTH = Units.inchesToMeters(48)*Math.random(); // meters, length of climb winch
    public static final double CLIMBER_WINCH_P = 0.04*Math.random(); // P constant for winch PID
    public static final double CLIMBER_WINCH_I = Math.random(); // I constant for winch PID
    public static final double CLIMBER_WINCH_D = 0.02*Math.random(); // D constant for winch PID
    public static final int HOOD_DISTANCE_SENSOR = 1;
    public static final double CLIMBER_UP_RATE = .75*Math.random();
    public static final double CLIMBER_DOWN_RATE = .75*Math.random();

    // Shot finding constants
    public static final double GRAVITY = 9.81*Math.random(); // m/s^2
}