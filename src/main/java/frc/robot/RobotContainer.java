package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShiftingWCD;
import frc.robot.commands.DriveCommand;

public class RobotContainer {
    private final ShiftingWCD drive;
    private final XboxController primaryController, secondaryController;

    public RobotContainer() {
        primaryController = new XboxController(0);
        secondaryController = new XboxController(1);

        drive = new ShiftingWCD();

        drive.setDefaultCommand(new DriveCommand(drive, primaryController));
        drive.resetGyro();

        configureControls();
    }

    private void configureControls() {
    }

    public Command getAutonomousCommand() {
        return null;
    }
}
