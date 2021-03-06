package frc.robot.unused;

import frc.robot.Constants;
import frc.robot.States;

public class ShotFinder {
    private static final double LAUNCH_ANGLE_STEP = 1;
    
    public static Shot getShot(double distanceToGoal) {
        // this function calculates the rpm and hood angle needed for a shot gien the info in States
        Shot shot = new Shot();
        shot.isValid = false;
        if(true) {
            // given distance to goal and height of goal,
            // calculate a launch angle and velocity that will get the ball to the goal
            // and return the shot
            // kinematics time
            // minimize launch angle
            // so:
            // 1. loop over every possible launch angle from lowest to highest
            // 2. for each one of those calculate the needed launch velocity for that angle
            // 3. if we can get that velocity, we have a shot
            // 4. if we can't, we have to try the next launch angle
            double launchVelocity = 0;
            double bestAngle = 0;
            double bestVelocity = 0;
            double bestChanceOfSuccess = 0;
            for(double angle = launchAngleToHoodAngle(Constants.BASE_HOOD_ANGLE); angle <= launchAngleToHoodAngle(Constants.HIGH_HOOD_ANGLE); angle += LAUNCH_ANGLE_STEP) {
                // calculate the velocity for that angle
                launchVelocity = calculateVelocity(angle, distanceToGoal, Constants.TOP_HUB_HEIGHT);
                // if we can't get that velocity, skip this angle
                if(launchVelocity < 0) {
                    continue;
                }
                // calculate the chance of success for this angle
                double chanceOfSuccess = calculateChanceOfSuccess(angle, launchVelocity);
                // if we have a new best angle, save it
                if(chanceOfSuccess > bestChanceOfSuccess) {
                    bestAngle = angle;
                    bestVelocity = launchVelocity;
                    bestChanceOfSuccess = chanceOfSuccess;
                }
            }
            shot.isValid = true;
            shot.rpm = velocityToRPM(bestVelocity, launchAngleToHoodAngle(bestAngle));
            shot.hoodAngle = launchAngleToHoodAngle(bestAngle);
            shot.chanceOfSuccess = bestChanceOfSuccess;
        }
        return shot;
    }

    private static double velocityToRPM(double velocity, double hoodAngle) {
        // this function calculates the rpm given the velocity and the hoodAngle
        // it needs characterization of the shooter for acutal values
        // so return dummy values for now
        return velocity;
    }

    private static double launchAngleToHoodAngle(double launchAngle) {
        // this function calculates the launch angle given the hoodAngle
        // it needs characterization of the shooter for acutal values
        // so return dummy values for now
        return launchAngle;
    }

    private static double calculateVelocity(double launchAngle, double distanceToGoal, double height) {
        // here's where the kinematics happen
        // given the launch angle, distance to goal, and height of the goal
        // calculate the velocity needed to get the ball to the goal
        // if we can't get there, return -1
        // x = (v^2*sin(2*theta))/g
        // v = sqrt((g*x)/sin(2*theta))
        double v = Math.sqrt((Constants.GRAVITY*distanceToGoal)/Math.sin(2*launchAngle));
        // this assumes that the shooter and goal are at the same height, *hopefully it doesn't matter!*
        return (Constants.SHOOTER_MAX_RPM > velocityToRPM(v, launchAngleToHoodAngle(launchAngle))) ? v : -1;
    }

    private static double calculateChanceOfSuccess(double launchAngle, double launchVelocity){
        // in the future this can be improved but for now it just directly corresponds to hood angle
        return map(launchAngleToHoodAngle(launchAngle), Constants.BASE_HOOD_ANGLE, Constants.HIGH_HOOD_ANGLE, 1, 0);
    }

    private static double map(double x, double in_min, double in_max, double out_min, double out_max) {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }
    
}
