package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.RetractIntakeCommand;
import frc.robot.subsystems.IntakeMagazine;
import frc.robot.subsystems.LedStrip;
import frc.robot.subsystems.ShiftingWCD;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.RainbowLedCommand;

public class RobotContainer {

    public XboxController driver = new XboxController(0);
    public XboxController operator = new XboxController(1);
    public IntakeMagazine intake;

    private final ShiftingWCD drive;
    private final LedStrip ledStrip;
    private final XboxController primaryController, secondaryController;

    public RobotContainer() {
        primaryController = new XboxController(0);
        secondaryController = new XboxController(1);

        drive = new ShiftingWCD();
        intake = new IntakeMagazine();

        drive.setDefaultCommand(new DriveCommand(drive, primaryController));
        drive.resetGyro();

        ledStrip = new LedStrip();
        // ledStrip.setDefaultCommand(new RainbowLedCommand(ledStrip));

        configureControls();
    }

    private void configureControls() {
        JoystickButton intakeDownButton = new JoystickButton(operator, XboxController.Button.kLeftBumper.value);
        intakeDownButton.whileHeld(new IntakeCommand(intake));
        
        JoystickButton intakeUpButton = new JoystickButton(operator, XboxController.Button.kRightBumper.value);
        intakeUpButton.whileHeld(new RetractIntakeCommand(intake));
        
        RainbowLedCommand ledCommand = new RainbowLedCommand(ledStrip);
        new JoystickButton(primaryController, XboxController.Button.kY.value).toggleWhenPressed(ledCommand);
        //new JoystickButton(primaryController, XboxController.Button.kX.value).cancelWhenPressed(ledCommand);
    }

    public Command getAutonomousCommand() {
        return null;
    }
}
