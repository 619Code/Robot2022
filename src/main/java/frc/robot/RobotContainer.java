package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.RetractIntakeCommand;
import frc.robot.subsystems.IntakeMagazine;
import frc.robot.subsystems.LedStrip;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.ShiftingWCD;
import io.github.oblarg.oblog.annotations.Log;
import frc.robot.subsystems.Shooter;
import frc.robot.commands.AimCommand;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.RainbowLedCommand;
public class RobotContainer {

    public XboxController driver;
    public XboxController operator;
    public IntakeMagazine intake;
    @Log
    private DriveCommand driveCommand;
    public Limelight limelight;
    public Shooter shooter;

    private final ShiftingWCD drive;
    private final LedStrip ledStrip;

    public RobotContainer() {
        

        driver = new XboxController(0);
        operator = new XboxController(1);

        drive = new ShiftingWCD();
        intake = new IntakeMagazine();
        limelight = new Limelight();
        shooter = new Shooter();

        driveCommand = new DriveCommand(drive, driver);
        drive.setDefaultCommand(driveCommand);
        drive.resetGyro();

        limelight.turnLightOff();

        ledStrip = new LedStrip();
        // ledStrip.setDefaultCommand(new RainbowLedCommand(ledStrip));

        configureControls();
    }

    private void configureControls() {
        JoystickButton intakeDownButton = new JoystickButton(operator, XboxController.Button.kLeftBumper.value);
        intakeDownButton.whileHeld(new IntakeCommand(intake));
        
        JoystickButton intakeUpButton = new JoystickButton(operator, XboxController.Button.kRightBumper.value);
        intakeUpButton.whileHeld(new RetractIntakeCommand(intake));

        JoystickButton aimButton = new JoystickButton(operator, XboxController.Button.kB.value);
        aimButton.whileHeld(new AimCommand(shooter, drive, limelight));
        
        RainbowLedCommand ledCommand = new RainbowLedCommand(ledStrip);
        new JoystickButton(driver, XboxController.Button.kY.value).toggleWhenPressed(ledCommand);
        //new JoystickButton(primaryController, XboxController.Button.kX.value).cancelWhenPressed(ledCommand);
    }

    public Command getAutonomousCommand() {
        return null;
    }
}
