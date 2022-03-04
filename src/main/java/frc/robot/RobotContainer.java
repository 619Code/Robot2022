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
import frc.robot.commands.ManualClimbingCommand;
import frc.robot.commands.MoveHoodUpCommand;
import frc.robot.commands.RetractIntakeCommand;
import frc.robot.commands.ShootCommand;
import frc.robot.commands.TuneShooterCommand;
import frc.robot.commands.ZeroCommand;
import frc.robot.commands.ZeroCommandSimple;
import frc.robot.helpers.JoystickAnalogButton;
import frc.robot.subsystems.*;
import io.github.oblarg.oblog.annotations.Config;
import io.github.oblarg.oblog.annotations.Log;
import frc.robot.subsystems.Shooter;
import frc.robot.commands.AimCommand;
import frc.robot.commands.AngleFinderCommand;
import frc.robot.commands.ClimbCommand;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.RainbowLedCommand;
import frc.robot.commands.MoveHoodUpCommand;;

public class RobotContainer {

    private static final ControlVectorList Rotation2d = null;
    public XboxController driver;
    public XboxController operator;

    public ShiftingWCD drive;
    public Intake intake;
    public Shooter shoot;
    public Magazine magazine;
    public JoystickAnalogButton joystickAnalogButton;
    public Climber climber;

    //Commands
    @Log 
    private TuneShooterCommand tuneShooterCommand;

    @Log
    private DriveCommand driveCommand;
    public Limelight limelight;

    @Log
    public Shooter shooter;
    //private final LedStrip ledStrip;

    @Config.PIDController
    private PIDController targetPID;

    public RobotContainer() {
        //targetPID = new PIDController(0, 0, 0);

        driver = new XboxController(0);
        operator = new XboxController(1);

        drive = new ShiftingWCD();
        // driveCommand = new DriveCommand(drive, driver);
        // drive.setDefaultCommand(driveCommand);
        // drive.resetGyro();

        intake = new Intake();
        intake.raiseIntake();
        //retractIntake = new RetractIntakeCommand(intake);

        magazine = new Magazine();
        climber = new Climber();
        var climbCommand = new ManualClimbingCommand(climber, operator );
        climber.setDefaultCommand(climbCommand);

        //limelight = new Limelight();
        shooter = new Shooter();
        this.tuneShooterCommand = new TuneShooterCommand(shooter, magazine);
        shooter.setDefaultCommand(tuneShooterCommand);

        //joystickAnalogButton = new JoystickAnalogButton(operator, 3);
        
        limelight = new Limelight(drive);
        limelight.turnLightOff();

        //ledStrip = new LedStrip();
        // ledStrip.setDefaultCommand(new RainbowLedCommand(ledStrip));

        configureControls();
    }

    private void configureControls() {
        JoystickButton intakeButton = new JoystickButton(operator, XboxController.Button.kLeftBumper.value);
        intakeButton.whileHeld(new IntakeCommand(intake, magazine));
        
        JoystickButton intakeUpButton = new JoystickButton(operator, XboxController.Button.kRightBumper.value);
        intakeUpButton.whileHeld(new RetractIntakeCommand(intake));

        // JoystickButton aimButton = new JoystickButton(operator, XboxController.Button.kB.value);
        // aimButton.whileHeld(new AimCommand(shooter, drive, limelight, targetPID));
        
        // RainbowLedCommand ledCommand = new RainbowLedCommand(ledStrip);
        // new JoystickButton(driver, XboxController.Button.kY.value).toggleWhenPressed(ledCommand);
        // //new JoystickButton(primaryController, XboxController.Button.kX.value).cancelWhenPressed(ledCommand);

        JoystickAnalogButton shootButton = new JoystickAnalogButton(operator, XboxController.Axis.kRightTrigger.value, .5);
        shootButton.whileHeld(new ShootCommand(shooter, magazine));   

        JoystickButton zeroHoodButton = new JoystickButton(driver, XboxController.Button.kA.value);
        zeroHoodButton.whenPressed(new ZeroCommand(shooter, Shooter.EDeviceType.Hood));

        //JoystickButton angleFinderButton = new JoystickButton(operator, XboxController.Button.kX.value);
        // modify these values as needed
        //angleFinderButton.whenPressed(new AngleFinderCommand(shooter, Shooter.EDeviceType.Hood, true));
        JoystickButton zeroHood = new JoystickButton(driver, XboxController.Button.kB.value);
        zeroHood.whenPressed(new ZeroCommandSimple(shooter, Shooter.EDeviceType.Hood));

        JoystickButton xButton = new JoystickButton(driver, XboxController.Button.kX.value);
        var moveHoodUpCommand = new MoveHoodUpCommand(shooter);
        xButton.whileHeld(moveHoodUpCommand);


    }

    public Command getAutonomousCommand() {
        return null;
//         //make sure we dont go nuts on voltage
//         var autoVoltageConstraint = 
//             new DifferentialDriveVoltageConstraint(
//                 new SimpleMotorFeedforward(Constants.ksVolts, 
//                 Constants.kvVoltSecondsPerMeter, 
//                 Constants.kaVoltSecondsSquaredPerMeter),
//                 Constants.kDriveKinematics, 
//                 10);
        
//         // Create config for trajectory
//         TrajectoryConfig config =
//             new TrajectoryConfig(
//                     Constants.kMaxSpeedMetersPerSecond,
//                     Constants.kMaxAccelerationMetersPerSecondSquared)
//                 // Add kinematics to ensure max speed is actually obeyed
//                 .setKinematics(Constants.kDriveKinematics)
//                 // Apply the voltage constraint
//                 .addConstraint(autoVoltageConstraint);

//         //Actual paths.

//         //Getting off the Tarmac:
//         Trajectory offTarmac = TrajectoryGenerator.generateTrajectory(
//             // Start at the origin facing the +X direction
//             new Pose2d(0, 0, new Rotation2d(0)),
//             List.of(),
//             new Pose2d(3, 0, new Rotation2d(0)),
//             // Pass config
//             config
//         );

//         // Reset odometry to the starting pose of the trajectory.
//         drive.resetOdometry(offTarmac.getInitialPose());
//         Pose2d bOrigin = drive.getPose();
//         offTarmac = offTarmac.relativeTo(bOrigin);

        // RamseteCommand ramseteCommand =
        // new RamseteCommand(
        //     offTarmac,
        //     drive::getPose,
        //     new RamseteController(Constants.kRamseteB, Constants.kRamseteZeta),
        //     new SimpleMotorFeedforward(
        //         Constants.ksVolts,
        //         Constants.kvVoltSecondsPerMeter,
        //         Constants.kaVoltSecondsSquaredPerMeter),
        //     Constants.kDriveKinematics,
        //     drive::getWheelSpeeds,
        //     new PIDController(Constants.kPDriveVel, 0, 0),
        //     new PIDController(Constants.kPDriveVel, 0, 0),
        //     // RamseteCommand passes volts to the callback
        //     drive::tankDriveVolts,
        //     drive);

//     // Run path following command, then stop at the end.
//     return ramseteCommand.andThen(() -> drive.tankDriveVolts(0, 0));

//     // For 1-ball auto, run following:
//    return ramseteCommand.alongWith(new ZeroCommand(shooter, Shooter.EDeviceType.Hood)).andThen(
    //      () -> drive.tankDriveVolts(0, 0)).alongWith(new AimCommand(shooter, drive, limelight)).andThen(
    //          new ShootCommand(shooter, magazine));
   }
}
