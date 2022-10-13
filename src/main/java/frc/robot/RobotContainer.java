package frc.robot;

import edu.wpi.first.math.controller.PIDController;
import java.util.List;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.filter.Debouncer;
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
import frc.robot.commands.ManualMoveCommand;
import frc.robot.commands.OuttakeCommand;
import frc.robot.commands.RetractIntakeCommand;
import frc.robot.commands.SearchCommand;
import frc.robot.commands.ShootAtDefaultCommand;
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
    public ShootAtDefaultCommand shootAtDefaultCommand;
    public TuneShooterCommand tuneShooterCommand;

    @Config.PIDController
    private PIDController targetPID;
    
    //initialize subsystems
    public RobotContainer() {
        driver = new XboxController(0);
        operator = new XboxController(1);

        drive = new ShiftingWCD();
        drive.resetGyro();

        intake = new Intake();
        intake.raiseIntake();

        magazine = new Magazine();
        climber = new Climber();

        shooter = new Shooter();
        ledStrip = new LedStrip();
        limelight = new Limelight(ledStrip);
        limelight.turnLightOff();

        //configureControls();
        //testTurret();
        //demoMode();
        climbTest();
        //shooterTest();
    }

    ///////////////////////////////////////////////////////////////////////////

    //for competitions: full functionality
    /* 
        driver: 
        -Left Joystick: move robot forwards / backwards
        -Right Joystick: move robot left / right
        -Right Trigger: speed up foward / backward movement

        operator:
        -Right Joystick: move climb arms up / down
        -Left Trigger: outtake
        -Right Trigger: shoot
        -Left Bumper: intake
        -Right Bumper: intake up
        -Y Button: high goal preset
        -B Button: auto-aim
        -X Button: zero turret
        -A Button: low goal preset
        
        operator: (back button pressed)
        -Right Joystick: move one climb arm without limits
        -Left Joystick: move the other climb arm without limits
    */
    private void configureControls() {
        driveButtons();
        intakeButtons();
        shootButtons();
        zeroTurretButtons();
        climbButtons();

        JoystickButton lowGoalButton = new JoystickButton(operator, XboxController.Button.kA.value);
        lowGoalButton.whileHeld(new AimCommand(shooter, drive, limelight, Constants.LOW_GOAL_ANGLE, Constants.LOW_GOAL_RPM));

        JoystickButton highGoalButton = new JoystickButton(operator, XboxController.Button.kY.value);
        highGoalButton.whileHeld(new AimCommand(shooter, drive, limelight, Constants.HIGH_GOAL_ANGLE, Constants.HIGH_GOAL_RPM));

        JoystickButton aimButton = new JoystickButton(operator, XboxController.Button.kB.value);
        aimButton.whileHeld(new AimCommand(shooter, drive, limelight));
    }

    //for demonstrations: manual control of turret and shooter
    /* 
        driver: 
        -Left Joystick: move robot forwards / backwards
        -Right Joystick: move robot left / right
        -Right Trigger: speed up foward / backward movement
        -Left Bumper: move turret one direction
        -Right Bumper: move turret the other direction

        operator:
        -Right Joystick: move climb arms up / down
        -Left Trigger: outtake
        -Right Trigger: shoot
        -Left Bumper: intake
        -Right Bumper: intake up
        -B Button: shoot at preset value
        -X Button: zero turret
        
        operator: (back button pressed)
        -Right Joystick: move one climb arm without limits
        -Left Joystick: move the other climb arm without limits
    */
    private void demoMode() {
        driveButtons();
        manualTurretButtons(false); //controlled by driver
        zeroTurretButtons();
        intakeButtons();
        shootButtons();
        climbButtons();
        tuneShooterButtons();

        JoystickButton aimButton = new JoystickButton(operator, XboxController.Button.kB.value);
        shootAtDefaultCommand = new ShootAtDefaultCommand(shooter);
        aimButton.whileHeld(shootAtDefaultCommand);
    }

    //for turret testing: manual control of turret
    /* 
        operator:
        -Left Bumper: move turret one direction
        -Right Bumper: move turret the other direction
        -B Button: force stop
        -X Button: zero turret
        -A Button: search
    */
    private void testTurret() {
        manualTurretButtons(true); //controlled by operator
        zeroTurretButtons();

        JoystickButton stopButton = new JoystickButton(operator, XboxController.Button.kB.value);
        stopButton.whileHeld(new ManualMoveCommand(shooter, 0.0));

        JoystickButton searchButton = new JoystickButton(operator, XboxController.Button.kA.value);
        searchButton.whileHeld(new SearchCommand(shooter, limelight));
    }

    //for climb testing: driving and climbing only
    /* 
        driver: 
        -Left Joystick: move robot forwards / backwards
        -Right Joystick: move robot left / right
        -Right Trigger: speed up foward / backward movement

        operator:
        -Right Joystick: move climb arms up / down
        -Left Bumper: move pistons one direction
        -Right Bumper: move pistons the other directon

        operator: (back button pressed)
        -Right Joystick: move one climb arm without limits
        -Left Joystick: move the other climb arm without limits   
    */
    private void climbTest() {
        driveButtons();
        climbButtons();

        JoystickButton togglePistonsButton = new JoystickButton(operator, XboxController.Button.kRightBumper.value);
        togglePistonsButton.whenPressed(new InstantCommand(() -> climber.togglePistons()));
    }

    //for shooter testing: driving and shooting only
    /* 
        driver: 
        -Left Joystick: move robot forwards / backwards
        -Right Joystick: move robot left / right
        -Right Trigger: speed up foward / backward movement

        operator:
        -Right Trigger: shoot 
    */
    private void shooterTest() {
        driveButtons();
        tuneShooterButtons();

        JoystickAnalogButton shootButton = new JoystickAnalogButton(operator, XboxController.Axis.kRightTrigger.value, .5);
        shootButton.whileHeld(new ParallelCommandGroup(new LoadShooterCommand(magazine), new RetractIntakeCommand(intake)));
    }

    ///////////////////////////////////////////////////////////////////////////

    private void driveButtons() {
        driveCommand = new DriveCommand(drive, driver);
        drive.setDefaultCommand(driveCommand);
    }

    private void intakeButtons() {
        JoystickButton intakeButton = new JoystickButton(operator, XboxController.Button.kLeftBumper.value);
        intakeCommand = new IntakeCommand(intake, magazine);
        intakeButton.whileHeld(intakeCommand);

        JoystickAnalogButton outtakeButton = new JoystickAnalogButton(operator, XboxController.Axis.kLeftTrigger.value, .5);
        outtakeButton.whileHeld(new OuttakeCommand(magazine));
        
        JoystickButton intakeUpButton = new JoystickButton(operator, XboxController.Button.kRightBumper.value);
        intakeUpButton.whileHeld(new RetractIntakeCommand(intake));
    }

    private void climbButtons() {
        climbCommand = new ClimbCommand(climber, operator);
        climber.setDefaultCommand(climbCommand);

        JoystickButton climbCommandButton = new JoystickButton(operator, XboxController.Button.kBack.value);
        climbCommandButton.whileHeld(new ManualClimbingCommand(climber, operator));
    }

    private void shootButtons() {
        JoystickAnalogButton shootButton = new JoystickAnalogButton(operator, XboxController.Axis.kRightTrigger.value, .5);
        shootButton.whileHeld(new ParallelCommandGroup(new LoadShooterCommand(magazine), new RetractIntakeCommand(intake)));
    }

    private void tuneShooterButtons() {
        tuneShooterCommand = new TuneShooterCommand(shooter);
        shooter.setDefaultCommand(tuneShooterCommand);
    }

    private void manualTurretButtons(boolean operatorControl) {
        if(operatorControl) {
            JoystickButton forwardButtonManual = new JoystickButton(operator, XboxController.Button.kLeftBumper.value);
            forwardButtonManual.whileHeld(new ManualMoveCommand(shooter, 0.25));

            JoystickButton backButtonManual = new JoystickButton(operator, XboxController.Button.kRightBumper.value);
            backButtonManual.whileHeld(new ManualMoveCommand(shooter, -0.25));
        } else {
            JoystickButton forwardButtonManual = new JoystickButton(driver, XboxController.Button.kLeftBumper.value);
            forwardButtonManual.whileHeld(new ManualMoveCommand(shooter, 0.25));

            JoystickButton backButtonManual = new JoystickButton(driver, XboxController.Button.kRightBumper.value);
            backButtonManual.whileHeld(new ManualMoveCommand(shooter, -0.25));
        }
    }

    private void zeroTurretButtons() {
        JoystickButton zeroTurret = new JoystickButton(operator, XboxController.Button.kX.value);
        zeroTurret.whenPressed(new ZeroCommandSimple(shooter));
    }

    public Command getAutonomousCommand() {
        return new SequentialCommandGroup(
        new ParallelCommandGroup(new TestAutoCommand(drive, 2.5), new ZeroCommandSimple(shooter), new IntakeCommand(intake, magazine).withTimeout(6)),
        new ParallelCommandGroup(new SpinIntakeCommand(intake), new LoadShooterCommand(magazine), new AimCommand(shooter, drive, limelight)).withTimeout(8));
   }
}
