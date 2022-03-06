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
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.LoadShooterCommand;
import frc.robot.commands.ManualClimbingCommand;
import frc.robot.commands.MoveHoodUpCommand;
import frc.robot.commands.OuttakeCommand;
import frc.robot.commands.RetractIntakeCommand;
import frc.robot.commands.ShootCommand;
import frc.robot.commands.TuneShooterCommand;
import frc.robot.commands.ZeroCommand;
import frc.robot.commands.ZeroCommandSimple;
import frc.robot.helpers.JoystickAnalogButton;
import frc.robot.helpers.ShotPresets;
import frc.robot.subsystems.*;
import io.github.oblarg.oblog.annotations.Config;
import io.github.oblarg.oblog.annotations.Log;
import frc.robot.subsystems.Shooter;
import frc.robot.commands.AimCommand;
import frc.robot.commands.AngleFinderCommand;
import frc.robot.commands.ClimbCommand;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.RainbowLedCommand;
import frc.robot.commands.MoveHoodUpCommand;
import frc.robot.commands.TestAutoCommand;

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
        driveCommand = new DriveCommand(drive, driver);
        drive.setDefaultCommand(driveCommand);
        drive.resetGyro();

        intake = new Intake();
        intake.raiseIntake();
        //retractIntake = new RetractIntakeCommand(intake);

        magazine = new Magazine();
        climber = new Climber();
        var climbCommand = new ClimbCommand(climber, operator);
        climber.setDefaultCommand(climbCommand);

        //limelight = new Limelight();
        shooter = new Shooter();
        //this.tuneShooterCommand = new TuneShooterCommand(shooter);
        //shooter.setDefaultCommand(tuneShooterCommand);

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
        JoystickAnalogButton outtakeButton = new JoystickAnalogButton(operator, XboxController.Axis.kLeftTrigger.value, .5);
        outtakeButton.whileHeld(new OuttakeCommand(magazine));
        
        JoystickButton intakeUpButton = new JoystickButton(operator, XboxController.Button.kRightBumper.value);
        intakeUpButton.whileHeld(new RetractIntakeCommand(intake));

        // JoystickButton aimButton = new JoystickButton(operator, XboxController.Button.kB.value);
        // aimButton.whileHeld(new AimCommand(shooter, drive, limelight));
        JoystickButton lowGoalButton = new JoystickButton(operator, XboxController.Button.kA.value);
        lowGoalButton.whileHeld(new AimCommand(shooter, drive, limelight, ShotPresets.LOW_GOAL_SHOT, false));
        JoystickButton highGoalButton = new JoystickButton(operator, XboxController.Button.kY.value);
        highGoalButton.whileHeld(new AimCommand(shooter, drive, limelight, ShotPresets.HIGH_GOAL_SHOT, false));
        JoystickButton tarmacGoalButton = new JoystickButton(operator, XboxController.Button.kX.value);
        tarmacGoalButton.whileHeld(new AimCommand(shooter, drive, limelight, ShotPresets.TARMAC_SHOT, false));
        JoystickButton tarmacHelperButton = new JoystickButton(operator, XboxController.Button.kB.value);
        tarmacHelperButton.whileHeld(new AimCommand(shooter, drive, limelight, ShotPresets.TARMAC_HELPER_SHOT, false));

        JoystickButton climbCommandButton = new JoystickButton(operator, XboxController.Button.kBack.value);
        climbCommandButton.whileHeld(new ManualClimbingCommand(climber, operator));
        // RainbowLedCommand ledCommand = new RainbowLedCommand(ledStrip);
        // new JoystickButton(driver, XboxController.Button.kY.value).toggleWhenPressed(ledCommand);
        // //new JoystickButton(primaryController, XboxController.Button.kX.value).cancelWhenPressed(ledCommand);

        JoystickAnalogButton shootButton = new JoystickAnalogButton(operator, XboxController.Axis.kRightTrigger.value, .5);
        //shootButton.whileHeld(new ShootCommand(shooter, magazine));
        shootButton.whileHeld(new LoadShooterCommand(magazine, false));   

        //JoystickButton zeroHoodButton = new JoystickButton(driver, XboxController.Button.kA.value);
        //zeroHoodButton.whenPressed(new ZeroCommand(shooter, Shooter.EDeviceType.Hood));

        //JoystickButton angleFinderButton = new JoystickButton(operator, XboxController.Button.kX.value);
        // modify these values as needed
        //angleFinderButton.whenPressed(new AngleFinderCommand(shooter, Shooter.EDeviceType.Hood, true));
        JoystickButton zeroHood = new JoystickButton(driver, XboxController.Button.kB.value);
        zeroHood.whenPressed(new ZeroCommandSimple(shooter, Shooter.EDeviceType.Hood));

        //JoystickButton xButton = new JoystickButton(driver, XboxController.Button.kX.value);
        //var moveHoodUpCommand = new MoveHoodUpCommand(shooter);
        //xButton.whileHeld(moveHoodUpCommand);
    }

    public Command getAutonomousCommand() {
        //return (new TestAutoCommand(drive));
        /*return new SequentialCommandGroup(
        new ParallelCommandGroup(new TestAutoCommand(drive, 1), new ZeroCommandSimple(shooter, Shooter.EDeviceType.Hood)), 
        new ParallelCommandGroup(new LoadShooterCommand(magazine, true), new AimCommand(shooter, drive, limelight, ShotPresets.HIGH_GOAL_SHOT, true)),
        new TestAutoCommand(drive, 3));*/
        return new TestAutoCommand(drive, 4);
   }
}
