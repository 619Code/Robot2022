package frc.robot.subsystems;

import java.util.ArrayList;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.States;

public class Limelight extends SubsystemBase {
    private NetworkTableEntry tx, ty, ta, tv, light;
    public double angleX, angleY, area, distance;
    public boolean hasTarget;
    private NetworkTable table;
    public ShiftingWCD drive;

    public Limelight(ShiftingWCD drive) {
        table = NetworkTableInstance.getDefault().getTable("limelight");
        tx = table.getEntry("tx");
        ty = table.getEntry("ty");
        ta = table.getEntry("ta");
        tv = table.getEntry("tv");
        light = table.getEntry("ledMode");
        this.drive = drive;
    }

    public void update() {
        hasTarget = (tv.getDouble(0) == 1);

        if(hasTarget) {
            angleX = tx.getDouble(0);
            angleY = ty.getDouble(0);
            area = ta.getDouble(0);
            distance = getDistance();
            States.isLocationValid = true;
            
            double theta = drive.getAngle().getDegrees() + angleX;
            double x = distance * Math.cos(Math.toRadians(theta));
            double y = distance * Math.sin(Math.toRadians(theta));
            States.robotX = x;
            States.robotY = y;
            States.distance = distance;

        } else {
            States.isLocationValid = false;
        }
    }

    private static double getListMedian(ArrayList<Double> list) {
        return (list.get(9) + list.get(10)) / 2;
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
