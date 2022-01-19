package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.RetractIntakeCommand;
import frc.robot.subsystems.IntakeMagazine;
import frc.robot.subsystems.ShiftingWCD;

public class RobotContainer {

    public XboxController driver = new XboxController(0);
    public XboxController operator = new XboxController(1);
    public IntakeMagazine intake;

    private final ShiftingWCD drive;

    public RobotContainer() {
        drive = new ShiftingWCD();
        intake = new IntakeMagazine();
        configureControls();
    }

    private void configureControls() {
        JoystickButton intakeDownButton = new JoystickButton(operator, XboxController.Button.kLeftBumper.value);
        intakeDownButton.whileHeld(new IntakeCommand(intake));
        
        JoystickButton intakeUpButton = new JoystickButton(operator, XboxController.Button.kRightBumper.value);
        intakeUpButton.whileHeld(new RetractIntakeCommand(intake));
        
    }

    public Command getAutonomousCommand() {
        return null;
    }
}
