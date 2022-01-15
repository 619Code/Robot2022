package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.ShiftingWCD;

public class RobotContainer {

    public XboxController driver = new XboxController(0);
    public XboxController operator = new XboxController(1);

    private final ShiftingWCD drive;

    public RobotContainer() {
        drive = new ShiftingWCD();
        configureControls();
    }

    private void configureControls() {
        JoystickButton inputButton = new JoystickButton(operator, 5);
    }

    public Command getAutonomousCommand() {
        return null;
    }
}
