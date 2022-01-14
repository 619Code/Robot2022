package frc.robot;

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
    public static final double SPEED_ADJUST = 0.7; //how much to adjust speed of drive
    public static final double DRIVE_RATIO_HIGH = (9.0/66.0) * (40.0/44.0); // gear ratio for high gear
    public static final double DRIVE_RATIO_LOW = (9.0/66.0) * (24.0/60.0); // gear ratio for low gear
    public static final double TRACK_WIDTH = Units.inchesToMeters(25); //distance between wheels in meters

    //Drive solenoids
    public static final PneumaticsModuleType PCM_CAN_ID = PneumaticsModuleType.CTREPCM;
    public static final int DRIVE_SOLENOID_FORWARD = 1;
    public static final int DRIVE_SOLENOID_BACK = 4;

    //Intake CAN
    public static final int LOADING_MOTOR = 0; //CHANGE THIS ONCE WE HAVE A LOADING MOTOR
    //these do not like being set to -1, so keep that in mind.

    //Intake Solenoid
    public static final int INTAKE_SOLENOID = 0; //Also change this once we have a piston hooked up please
    //Unless of course, you don't like working robots.

}