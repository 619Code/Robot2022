package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShiftingWCD;

public class RobotContainer {
    private final ShiftingWCD drive;

    public RobotContainer() {
        drive = new ShiftingWCD();
        configureControls();
    }

    private void configureControls() {
    }

    public Command getAutonomousCommand() {
        return null;
    }
}
