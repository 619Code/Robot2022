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
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.LoadShooterCommand;
import frc.robot.commands.ManualClimbingCommand;
import frc.robot.commands.OuttakeCommand;
import frc.robot.commands.RetractIntakeCommand;
import frc.robot.commands.SpinIntakeCommand;
import frc.robot.commands.ZeroCommandSimple;
import frc.robot.helpers.JoystickAnalogButton;
import frc.robot.subsystems.*;
import io.github.oblarg.oblog.annotations.Config;
import io.github.oblarg.oblog.annotations.Log;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Shooter.EDeviceType;
import frc.robot.commands.AimCommand;
import frc.robot.commands.ClimbCommand;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.CavalierLedCommand;
import frc.robot.commands.RainbowLedCommand;
import frc.robot.commands.TestAutoCommand;
import frc.robot.commands.TuneShooterCommand;

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
    private AimCommand aimCommand;

    @Log
    private DriveCommand driveCommand;
    public Limelight limelight;
    public ClimbCommand climbCommand;

    public IntakeCommand intakeCommand;

    @Log
    public Shooter shooter;
    private LedStrip ledStrip;

    @Log
    public TuneShooterCommand tuneShooterCommand;

    @Config.PIDController
    private PIDController targetPID;
    
    public RobotContainer() {
        driver = new XboxController(0);
        operator = new XboxController(1);

        drive = new ShiftingWCD();
        driveCommand = new DriveCommand(drive, driver);
        drive.setDefaultCommand(driveCommand);
        drive.resetGyro();

        intake = new Intake();
        intake.raiseIntake();

        magazine = new Magazine();
        climber = new Climber();
        climbCommand = new ClimbCommand(climber, operator);
        climber.setDefaultCommand(climbCommand);

        shooter = new Shooter();

        // tuneShooterCommand = new TuneShooterCommand(shooter);
        // shooter.setDefaultCommand(tuneShooterCommand);

        // JoystickButton stopButton = new JoystickButton(operator, XboxController.Button.kX.value);
        // Command stopCommand = new RunCommand(() -> shooter.move(EDeviceType.Turret, 0.0), shooter);
        // stopButton.whenPressed(stopCommand);

        // JoystickButton forwardButton = new JoystickButton(operator, XboxController.Button.kLeftBumper.value);
        // forwardButton.whenPressed(new RunCommand(() -> shooter.move(EDeviceType.Turret, 0.8), shooter));

        // JoystickButton backButton = new JoystickButton(operator, XboxController.Button.kRightBumper.value);
        // backButton.whenPressed(new RunCommand(() -> shooter.move(EDeviceType.Turret, -0.8), shooter));

        ledStrip = new LedStrip();

        limelight = new Limelight(drive, ledStrip);
        limelight.turnLightOff();

        configureControls();
    }

    private void configureControls() {
        JoystickButton intakeButton = new JoystickButton(operator, XboxController.Button.kLeftBumper.value);
        intakeCommand = new IntakeCommand(intake, magazine);
        intakeButton.whileHeld(intakeCommand);

        JoystickAnalogButton outtakeButton = new JoystickAnalogButton(operator, XboxController.Axis.kLeftTrigger.value, .5);
        outtakeButton.whileHeld(new OuttakeCommand(magazine));
        
        JoystickButton intakeUpButton = new JoystickButton(operator, XboxController.Button.kRightBumper.value);
        intakeUpButton.whileHeld(new RetractIntakeCommand(intake));

        JoystickButton lowGoalButton = new JoystickButton(operator, XboxController.Button.kA.value);
        lowGoalButton.whileHeld(new AimCommand(shooter, drive, limelight, Constants.LOW_GOAL_ANGLE, Constants.LOW_GOAL_RPM));

        JoystickButton highGoalButton = new JoystickButton(operator, XboxController.Button.kY.value);
        highGoalButton.whileHeld(new AimCommand(shooter, drive, limelight, Constants.HIGH_GOAL_ANGLE, Constants.HIGH_GOAL_RPM));

        JoystickButton aimButton = new JoystickButton(operator, XboxController.Button.kB.value);
        aimButton.whileHeld(new AimCommand(shooter, drive, limelight));

        JoystickButton climbCommandButton = new JoystickButton(operator, XboxController.Button.kBack.value);
        climbCommandButton.whileHeld(new ManualClimbingCommand(climber, operator));

        JoystickAnalogButton shootButton = new JoystickAnalogButton(operator, XboxController.Axis.kRightTrigger.value, .5);
        shootButton.whileHeld(new ParallelCommandGroup(new LoadShooterCommand(magazine), new RetractIntakeCommand(intake)));

        JoystickButton zeroHood = new JoystickButton(operator, XboxController.Button.kX.value);
        zeroHood.whenPressed(new ZeroCommandSimple(shooter));
    }

    public Command getAutonomousCommand() {
        return new SequentialCommandGroup(
        new ParallelCommandGroup(new TestAutoCommand(drive, 2.5), new ZeroCommandSimple(shooter), new IntakeCommand(intake, magazine).withTimeout(6)),
        new ParallelCommandGroup(new SpinIntakeCommand(intake), new LoadShooterCommand(magazine), new AimCommand(shooter, drive, limelight)).withTimeout(8));
   }
}
