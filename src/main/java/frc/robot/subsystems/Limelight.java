package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.States;

public class Limelight extends SubsystemBase {
    private NetworkTableEntry tx, ty, ta, tv, light;
    public double angleX, angleY, area, lastArea, distance;
    public boolean hasTarget;
    private NetworkTable table;

    public Limelight() {
        table = NetworkTableInstance.getDefault().getTable("limelight");
        tx = table.getEntry("tx");
        ty = table.getEntry("ty");
        ta = table.getEntry("ta");
        tv = table.getEntry("tv");
        light = table.getEntry("ledMode");

        lastArea = 0;
    }

    public void update() {
        area = ta.getDouble(0);
        hasTarget = (tv.getDouble(0) == 1);

        if(hasTarget && lastArea != 0 && area/lastArea > 2) {
            area = lastArea;
        } else {
            angleX = tx.getDouble(0);
            angleY = ty.getDouble(0);
            distance = getDistance();
            States.isLocationValid = hasTarget;
        }

        lastArea = area;
    }

    public double getDistance() {
        double heightDiff = Constants.TOP_HUB_HEIGHT - Constants.LIMELIGHT_HEIGHT;
        double angle = Constants.LIMELIGHT_ANGLE + ty.getDouble(0);
        double distance = heightDiff / Math.tan(Math.toRadians(angle));
        return distance + Constants.TOP_HUB_RADIUS;
    }

    public void turnLightOff() {
        this.light.setNumber(1);
    }

    public void turnLightOn() {
        this.light.setNumber(3);
    }
}
