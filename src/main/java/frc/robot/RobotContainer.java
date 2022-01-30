package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.RetractIntakeCommand;
import frc.robot.commands.ShootCommand;
import frc.robot.helpers.JoystickAnalogButton;
import frc.robot.subsystems.IntakeHopper;
import frc.robot.subsystems.*;
import frc.robot.subsystems.LedStrip;
import frc.robot.subsystems.ShiftingWCD;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.RainbowLedCommand;

public class RobotContainer {

    public XboxController driver;
    public XboxController operator;
    public IntakeHopper intake;
    public IntakeCommand intakeCommand;
    public Shooter shoot;
    public VerticalMagazine verticalMag;
    public RetractIntakeCommand retractIntake;
    public JoystickAnalogButton joystickAnalogButton;

    private final ShiftingWCD drive;
    private final LedStrip ledStrip;

    public RobotContainer() {
        driver = new XboxController(0);
        operator = new XboxController(1);

        drive = new ShiftingWCD();
        intake = new IntakeHopper();
        intakeCommand = new IntakeCommand(intake, verticalMag);
        shoot = new Shooter();
        retractIntake = new RetractIntakeCommand(intake);
        joystickAnalogButton = new JoystickAnalogButton(operator, 3);


        drive.setDefaultCommand(new DriveCommand(drive, driver));
        drive.resetGyro();

        ledStrip = new LedStrip();
        // ledStrip.setDefaultCommand(new RainbowLedCommand(ledStrip));

        configureControls();
    }

    private void configureControls() {
        JoystickButton intakeDownButton = new JoystickButton(operator, XboxController.Button.kLeftBumper.value);
        intakeDownButton.whileHeld(new IntakeCommand(intake, verticalMag));
        
        JoystickButton intakeUpButton = new JoystickButton(operator, XboxController.Button.kRightBumper.value);
        intakeUpButton.whileHeld(new RetractIntakeCommand(intake));
        
        RainbowLedCommand ledCommand = new RainbowLedCommand(ledStrip);
        new JoystickButton(driver, XboxController.Button.kY.value).toggleWhenPressed(ledCommand);
        //new JoystickButton(primaryController, XboxController.Button.kX.value).cancelWhenPressed(ledCommand);


        //Shooter shoot = new JoystickButton(operator, XboxController.Button.kX.value);

        var shootButton = new JoystickAnalogButton(operator, XboxController.Axis.kRightTrigger.value, .5);
        shootButton.whileHeld(new ShootCommand(shoot, operator));
        
    }

    public Command getAutonomousCommand() {
        return null;
    }
}
