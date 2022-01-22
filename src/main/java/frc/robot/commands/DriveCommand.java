package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.Constants;
import frc.robot.subsystems.ShiftingWCD;

public class DriveCommand extends CommandBase {
    private ShiftingWCD drive;
    private XboxController controller;
    private double leftY, rightX;
    private double speed, rotation;
    private boolean isLowGear;

    private ShuffleboardTab shuffleboardTab;

    public DriveCommand(ShiftingWCD drive, XboxController controller) {
        this.drive = drive;
        this.controller = controller;
        addRequirements(drive);
        shuffleboardTab = Shuffleboard.getTab(Constants.ShuffleboardDriveTabName);
    }

    @Override
    public void execute() {
        leftY = -controller.getLeftY();
        rightX = controller.getRightX();
        setVals();
        drive.curve(speed, rotation, isLowGear);
        report();
    }

    public void setVals() {
        speed = (Math.abs(speed) > Constants.JOYSTICK_DEADZONE) ? leftY : 0;
        rotation = (Math.abs(rotation) > Constants.JOYSTICK_DEADZONE) ? rightX : 0;

        System.out.println(controller.getLeftTriggerAxis());
        if (controller.getLeftTriggerAxis() > 0.5) {
            isLowGear = true;
        } else {
            isLowGear = false;

            if(controller.getRightTriggerAxis() < 0.5) {
                speed *= 0.5;
                rotation *= 0.5;
            }
        }
    }
    
    public void report() {
        shuffleboardTab.add("Speed", speed);
        shuffleboardTab.add("Rotation", rotation);
        shuffleboardTab.add("Gear", (isLowGear ? "low" : "high"));
        shuffleboardTab.add("Joystick Left Y", leftY);
        shuffleboardTab.add("Joystick Right X", rightX);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}