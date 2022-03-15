package frc.robot.subsystems;

import java.util.ArrayList;

import edu.wpi.first.math.util.Units;
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

    private double newDistance;
    private ArrayList<Double> distanceLog = new ArrayList<Double>();

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
            newDistance = getDistance();
            States.isLocationValid = true;

            distanceLog = manageList(distanceLog, newDistance);
            distance = getListMedian(distanceLog, newDistance);
            
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

    private static ArrayList<Double> manageList(ArrayList<Double> list, double newDistance) {
        list.add(newDistance);
        if(list.size() > 10) {
            list.remove(0);
        }
        return list;
    }

    private static double getListMedian(ArrayList<Double> list, double newDistance) {
        if(list.size() != 10) {
            return newDistance;
        } else {
            return (list.get(4) + list.get(5)) / 2.0;
        }
    }

    public double getDistance() {
        double heightDiff = Constants.TOP_HUB_HEIGHT - Constants.LIMELIGHT_HEIGHT;
        double angle = Constants.LIMELIGHT_ANGLE + ty.getDouble(0);
        double distance = heightDiff / Math.tan(Math.toRadians(angle));
        distance += Constants.TOP_HUB_RADIUS;
        distance = 108.0 + (101.0/85.0) * (distance - 102.0);
        return distance;
    }

    public void turnLightOff() {
        //this.light.setNumber(1);
        this.light.setNumber(3); //UNDO
    }

    public void turnLightOn() {
        this.light.setNumber(3);
    }
}
