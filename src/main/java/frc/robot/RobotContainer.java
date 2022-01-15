package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.LedStrip;
import frc.robot.subsystems.ShiftingWCD;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.RainbowLedCommand;

public class RobotContainer {
    private final ShiftingWCD drive;
    private final LedStrip ledStrip;
    private final XboxController primaryController, secondaryController;

    public RobotContainer() {
        primaryController = new XboxController(0);
        secondaryController = new XboxController(1);

        drive = new ShiftingWCD();

        drive.setDefaultCommand(new DriveCommand(drive, primaryController));
        drive.resetGyro();

        ledStrip = new LedStrip();
        // ledStrip.setDefaultCommand(new RainbowLedCommand(ledStrip));

        configureControls();
    }

    private void configureControls() {
        RainbowLedCommand ledCommand = new RainbowLedCommand(ledStrip);
        new JoystickButton(primaryController, XboxController.Button.kY.value).toggleWhenPressed(ledCommand);
        //new JoystickButton(primaryController, XboxController.Button.kX.value).cancelWhenPressed(ledCommand);
    }

    public Command getAutonomousCommand() {
        return null;
    }
}
