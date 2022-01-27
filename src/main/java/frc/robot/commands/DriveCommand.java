package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.subsystems.ShiftingWCD;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.annotations.Log;

public class DriveCommand extends CommandBase implements Loggable {
    private ShiftingWCD drive;
    private XboxController controller;
    private double leftY, rightX;
    //@Log
    private double throttle;
    //@Log
    private double rotation;
    private boolean isLowGear;
    private ShuffleboardTab shuffleboardTab;
    private NetworkTableEntry throttleEntry;

    public DriveCommand(ShiftingWCD drive, XboxController controller) {
        this.drive = drive;
        this.controller = controller;
        addRequirements(drive);        
        initReporting();
    }

    @Override
    public void execute() {
        leftY = -controller.getLeftY();
        rightX = controller.getRightX();
        // System.out.print("y,x");
        // System.out.print(leftY);
        // System.out.print(rightX);

        setVals();
        drive.curve(throttle, rotation, isLowGear);
        report();
    }

    public void setVals() {
        throttle = (Math.abs(leftY) > Constants.JOYSTICK_DEADZONE) ? leftY : 0;
        rotation = (Math.abs(rightX) > Constants.JOYSTICK_DEADZONE) ? rightX : 0;

        System.out.println(controller.getLeftTriggerAxis());
        if (controller.getLeftTriggerAxis() > 0.5) {
            isLowGear = true;
        } else {
            isLowGear = false;

            if(controller.getRightTriggerAxis() < 0.5) {
                throttle *= 0.5;
                rotation *= 0.5;
            }
        }
        // System.out.print("speed,rotation");
        // System.out.print(throttle);
        // System.out.print(rotation);

    }
    
    public void initReporting() {
        //shuffleboardTab.AddN
        //shuffleboardTab.("Speed", speed);
        // shuffleboardTab.add("Rotation", rotation);
        // shuffleboardTab.add("Gear", (isLowGear ? "low" : "high"));
        // shuffleboardTab.add("Joystick Left Y", leftY);
        // shuffleboardTab.add("Joystick Right X", rightX);
        shuffleboardTab = Shuffleboard.getTab(Constants.ShuffleboardDriveTabName);
        throttleEntry = shuffleboardTab.add("Throttle", throttle).getEntry();
        
    }

    public void report() {
        throttleEntry.setNumber(throttle);
        SmartDashboard.putNumber("Throttle", throttle);
        SmartDashboard.putNumber("Rotation", rotation);
        SmartDashboard.putString("Gear", (isLowGear ? "low" : "high"));
        SmartDashboard.putNumber("Joystick Left Y", leftY);
        SmartDashboard.putNumber("Joystick Right X", rightX);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}