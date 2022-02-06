package frc.robot;

import edu.wpi.first.math.controller.PIDController;
import java.util.List;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.TrajectoryGenerator.ControlVectorList;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.RetractIntakeCommand;
import frc.robot.commands.ShootCommand;
import frc.robot.helpers.JoystickAnalogButton;
import frc.robot.subsystems.IntakeHopper;
import frc.robot.subsystems.*;
import frc.robot.subsystems.LedStrip;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.ShiftingWCD;
import io.github.oblarg.oblog.annotations.Config;
import io.github.oblarg.oblog.annotations.Log;
import frc.robot.subsystems.Shooter;
import frc.robot.unused.AngleCommand;
import frc.robot.unused.Angler;
import frc.robot.commands.AimCommand;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.RainbowLedCommand;
public class RobotContainer {

    private static final ControlVectorList Rotation2d = null;
    public XboxController driver;
    public XboxController operator;
    public IntakeHopper intake;
    public IntakeCommand intakeCommand;
    public Shooter shoot;
    public VerticalMagazine verticalMag;
    public RetractIntakeCommand retractIntake;
    public JoystickAnalogButton joystickAnalogButton;

    @Log
    private DriveCommand driveCommand;
    public Limelight limelight;
    public Shooter shooter;

    public Angler angler; //temp

    private final ShiftingWCD drive;
    private final LedStrip ledStrip;

    @Config.PIDController
    private PIDController targetPID;

    public RobotContainer() {
        targetPID = new PIDController(0, 0, 0);

        driver = new XboxController(0);
        operator = new XboxController(1);

        drive = new ShiftingWCD();
        intake = new IntakeHopper();
        intakeCommand = new IntakeCommand(intake, verticalMag);
        limelight = new Limelight();
        shooter = new Shooter();
        retractIntake = new RetractIntakeCommand(intake);
        joystickAnalogButton = new JoystickAnalogButton(operator, 3);
        limelight = new Limelight();

        driveCommand = new DriveCommand(drive, driver);
        drive.setDefaultCommand(driveCommand);
        drive.resetGyro();

        angler = new Angler(); //temp

        limelight.turnLightOff();

        ledStrip = new LedStrip();
        // ledStrip.setDefaultCommand(new RainbowLedCommand(ledStrip));

        configureControls();
    }

    private void configureControls() {
        JoystickButton intakeDownButton = new JoystickButton(operator, XboxController.Button.kLeftBumper.value);
        intakeDownButton.whileHeld(new IntakeCommand(intake, verticalMag));
        
        JoystickButton intakeUpButton = new JoystickButton(operator, XboxController.Button.kRightBumper.value);
        intakeUpButton.whileHeld(new RetractIntakeCommand(intake));

        JoystickButton aimButton = new JoystickButton(operator, XboxController.Button.kB.value);
        aimButton.whileHeld(new AimCommand(shooter, drive, limelight, targetPID));
        aimButton.whileHeld(new AngleCommand(angler)); //temp
        
        RainbowLedCommand ledCommand = new RainbowLedCommand(ledStrip);
        new JoystickButton(driver, XboxController.Button.kY.value).toggleWhenPressed(ledCommand);
        //new JoystickButton(primaryController, XboxController.Button.kX.value).cancelWhenPressed(ledCommand);
        //Shooter shoot = new JoystickButton(operator, XboxController.Button.kX.value);
        var shootButton = new JoystickAnalogButton(operator, XboxController.Axis.kRightTrigger.value, .5);
        shootButton.whileHeld(new ShootCommand(shooter, operator));       
    }

    public Command getAutonomousCommand() {
        //make sure we dont go nuts on voltage
        var autoVoltageConstraint = 
            new DifferentialDriveVoltageConstraint(
                new SimpleMotorFeedforward(Constants.ksVolts, 
                Constants.kvVoltSecondsPerMeter, 
                Constants.kaVoltSecondsSquaredPerMeter),
                Constants.kDriveKinematics, 
                10);
        
        // Create config for trajectory
        TrajectoryConfig config =
            new TrajectoryConfig(
                    Constants.kMaxSpeedMetersPerSecond,
                    Constants.kMaxAccelerationMetersPerSecondSquared)
                // Add kinematics to ensure max speed is actually obeyed
                .setKinematics(Constants.kDriveKinematics)
                // Apply the voltage constraint
                .addConstraint(autoVoltageConstraint);

        //Actual paths.

        //Getting off the Tarmac:
        Trajectory offTarmac = TrajectoryGenerator.generateTrajectory(
            // Start at the origin facing the +X direction
            new Pose2d(0, 0, new Rotation2d(0)),
            List.of(),
            new Pose2d(3, 0, new Rotation2d(0)),
            // Pass config
            config
        );

        // Reset odometry to the starting pose of the trajectory.
        drive.resetOdometry(offTarmac.getInitialPose());
        Pose2d bOrigin = drive.getPose();
        offTarmac = offTarmac.relativeTo(bOrigin);

        RamseteCommand ramseteCommand =
        new RamseteCommand(
            offTarmac,
            drive::getPose,
            new RamseteController(Constants.kRamseteB, Constants.kRamseteZeta),
            new SimpleMotorFeedforward(
                Constants.ksVolts,
                Constants.kvVoltSecondsPerMeter,
                Constants.kaVoltSecondsSquaredPerMeter),
            Constants.kDriveKinematics,
            drive::getWheelSpeeds,
            new PIDController(Constants.kPDriveVel, 0, 0),
            new PIDController(Constants.kPDriveVel, 0, 0),
            // RamseteCommand passes volts to the callback
            drive::tankDriveVolts,
            drive);

    // Run path following command, then stop at the end.
    return ramseteCommand.andThen(() -> drive.tankDriveVolts(0, 0));
  }
}
