package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Constants;
import frc.robot.subsystems.ShiftingWCD;

public class DriveCommand extends CommandBase {
    private ShiftingWCD drive;
    private XboxController controller;
    private double speed, rotation;
    private boolean isLowGear;

    public DriveCommand(ShiftingWCD drive, XboxController controller) {
        this.drive = drive;
        this.controller = controller;
        addRequirements(drive);
    }

    @Override
    public void execute() {
        speed = -controller.getLeftY();
        rotation = controller.getRightX();
        setVals();
        System.out.println("Gear: " + (isLowGear ? "low" : "high"));
        drive.curve(speed, rotation, isLowGear);
    }

    public void setVals() {
        speed = (Math.abs(speed) > Constants.JOYSTICK_DEADZONE) ? speed : 0;
        rotation = (Math.abs(rotation) > Constants.JOYSTICK_DEADZONE) ? rotation : 0;

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

    @Override
    public boolean isFinished() {
        return false;
    }
}