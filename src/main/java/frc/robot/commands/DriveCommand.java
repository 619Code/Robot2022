package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.math.filter.SlewRateLimiter;
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

    private SlewRateLimiter limiter;

    public DriveCommand(ShiftingWCD drive, XboxController controller) {
        this.drive = drive;
        this.controller = controller;
        addRequirements(drive);
    }

    @Override
    public void execute() {
        leftY = -controller.getLeftY();
        rightX = controller.getRightX();

        setVals();
        //System.out.println("Speed: " + throttle);
        //System.out.println("Rotation: " + rotation);
        drive.curve(throttle, rotation, isLowGear);
    }

    public void setVals() {
        throttle = (Math.abs(leftY) > Constants.JOYSTICK_DEADZONE) ? leftY : 0;
        throttle = -throttle;
        rotation = (Math.abs(rightX) > Constants.JOYSTICK_DEADZONE) ? rightX : 0;

        if(controller.getRightTriggerAxis() < 0.5) { //UNDO
            throttle *= 0.5;
        }

        isLowGear = false;
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}