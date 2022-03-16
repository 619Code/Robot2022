package frc.robot.unused;

import frc.robot.Constants;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.annotations.Config;
import io.github.oblarg.oblog.annotations.Log;

public class ShotPresets implements Loggable {
    @Log
    public static final double LOW_GOAL_ANGLE = Constants.BASE_HOOD_ANGLE-15; // degrees
    @Log
    public static final double LOW_GOAL_RPM = 1700; // rpm
    public static final Shot LOW_GOAL_SHOT = new Shot(LOW_GOAL_RPM, LOW_GOAL_ANGLE);

    @Log
    public static final double HIGH_GOAL_ANGLE = Constants.BASE_HOOD_ANGLE; // degrees
    @Log
    public static final double HIGH_GOAL_RPM = 2900; // rpm
    public static final Shot HIGH_GOAL_SHOT = new Shot(HIGH_GOAL_RPM, HIGH_GOAL_ANGLE);
}
