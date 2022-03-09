package frc.robot.helpers;

import frc.robot.Constants;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.annotations.Config;
import io.github.oblarg.oblog.annotations.Log;

public class ShotPresets implements Loggable {
    @Log
    public static final double LOW_GOAL_ANGLE = Constants.BASE_HOOD_ANGLE-15; // degrees
    @Log
    public static final double LOW_GOAL_RPM = 1700; // rpm
    /*@Config
    void setLowGoalRPM(double rpm) {
        LOW_GOAL_RPM = rpm;
        LOW_GOAL_SHOT = new Shot(true, LOW_GOAL_RPM, LOW_GOAL_ANGLE, 1);
    }
    @Config
    void setLowGoalAngle(double angle) {
        LOW_GOAL_ANGLE = angle;
        LOW_GOAL_SHOT = new Shot(true, LOW_GOAL_RPM, LOW_GOAL_ANGLE, 1);
    }*/
    public static final Shot LOW_GOAL_SHOT = new Shot(true, LOW_GOAL_RPM, LOW_GOAL_ANGLE, 1, false);

    @Log
    public static final double HIGH_GOAL_ANGLE = Constants.BASE_HOOD_ANGLE; // degrees
    @Log
    public static final double HIGH_GOAL_RPM = 2900; // rpm
    /*@Config
    void setLowGoalRPM(double rpm) {
        LOW_GOAL_RPM = rpm;
        LOW_GOAL_SHOT = new Shot(true, LOW_GOAL_RPM, LOW_GOAL_ANGLE, 1);
    }
    @Config
    void setLowGoalAngle(double angle) {
        LOW_GOAL_ANGLE = angle;
        LOW_GOAL_SHOT = new Shot(true, LOW_GOAL_RPM, LOW_GOAL_ANGLE, 1);
    }*/
    public static final Shot HIGH_GOAL_SHOT = new Shot(true, HIGH_GOAL_RPM, HIGH_GOAL_ANGLE, 1, false);

    @Log
    public static final double TARMAC_ANGLE = Constants.BASE_HOOD_ANGLE-20; // degrees
    @Log
    public static final double TARMAC_RPM = 3700; // rpm
    /*@Config
    void setLowGoalRPM(double rpm) {
        LOW_GOAL_RPM = rpm;
        LOW_GOAL_SHOT = new Shot(true, LOW_GOAL_RPM, LOW_GOAL_ANGLE, 1);
    }
    @Config
    void setLowGoalAngle(double angle) {
        LOW_GOAL_ANGLE = angle;
        LOW_GOAL_SHOT = new Shot(true, LOW_GOAL_RPM, LOW_GOAL_ANGLE, 1);
    }*/
    public static final Shot TARMAC_SHOT = new Shot(true, TARMAC_RPM, TARMAC_ANGLE, 1, false);
    public static final Shot TARMAC_HELPER_SHOT = new Shot(true, TARMAC_RPM, TARMAC_ANGLE, 1, true);
    
}
