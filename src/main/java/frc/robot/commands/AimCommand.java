package frc.robot.commands;

import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.ShiftingWCD;
import frc.robot.subsystems.Shooter;
import io.github.oblarg.oblog.annotations.Config;
import frc.robot.Constants;
import frc.robot.States;
import frc.robot.helpers.Shot;
import frc.robot.helpers.ShotFinder;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import com.kauailabs.navx.frc.AHRS;

public class AimCommand extends CommandBase {
    private Shooter shooter;
    private ShiftingWCD drive;
    private Limelight limelight;
    private AHRS navx;
    private Shot presetShot;

    private PIDController targetPID = new PIDController(0, 0, 0);

    private boolean isAuto;

    private Timer endTimer;
    
    public AimCommand(Shooter shooterSub, ShiftingWCD driveSub, Limelight limelightSub, boolean isAuto) {
        shooter = shooterSub;
        drive = driveSub;
        limelight = limelightSub;
        navx = drive.getNavx();
        this.presetShot = new Shot();
        this.presetShot.isValid = false;
        this.isAuto = isAuto;
        this.endTimer = new Timer();
        addRequirements(shooter);
        addRequirements(drive);
    }

    public AimCommand(Shooter shooterSub, ShiftingWCD driveSub, Limelight limelightSub, PIDController PID, boolean isAuto) {
        this(shooterSub, driveSub, limelightSub, isAuto);
        this.targetPID = PID;
    }

    public AimCommand(Shooter shooterSub, ShiftingWCD driveSub, Limelight limelightSub, Shot presetShot, boolean isAuto) {
        this(shooterSub, driveSub, limelightSub, isAuto);
        this.presetShot = presetShot;
    }

    public void initialize() {
        System.out.println("AIMCOMMAND INITIALIZED");
        limelight.turnLightOn();
        States.isAiming = true;
        States.currentShot = presetShot;
    }

    public void execute() {
        limelight.update();
        //System.out.println("angleX: " + limelight.angleX);
        //System.out.println("angleY: " + limelight.angleY);
        //System.out.println("Distance: " + limelight.distance);
        if(true || States.isLocationValid) {
            // point the robot towards the target
            
            Shot shot;
            if(!presetShot.isValid){
                shot = ShotFinder.getShot(limelight.distance);
            } else {
                shot = presetShot;
            }
            States.currentShot = shot;
            if(!shot.isValid){
                // TODO: Flash LEDs red or orange or smth
                System.out.println("SHOT NOT VALID");
            } else {
                shooter.setShooterSpeedByRPM(shot.rpm*1.1);
                shooter.setAngle(Shooter.EDeviceType.Hood, shot.hoodAngle);
                // TODO: set LED color by chance of success
            }
            if(shot.useLimelight){
                drive.curve(0, -targetPID.calculate(limelight.angleX, 0), false);
            }
            // if the shooter is within 5 % of where it should be
            // and the hood is within 3% of where it should be
            System.out.print("SHOOTER READINESS: ");
            System.out.print(Math.abs(Math.abs(shooter.getShooterRPM())/shot.rpm - 1));
            System.out.print(", TARGET HOOD ANGLE: ");
            System.out.print(shot.hoodAngle);
            System.out.print(" ");
            System.out.print(shooter.getHoodAngle());
            System.out.print(" ");
            System.out.println(Math.abs(shooter.getHoodAngle()/shot.hoodAngle - 1));
            if(Math.abs(Math.abs(shooter.getShooterRPM())/shot.rpm - 1) < 0.1 && Math.abs(shooter.getHoodAngle()/shot.hoodAngle - 1) < 0.03){
                States.isShooterReady = true;
            } else {
                States.isShooterReady = false;
            }
            //System.out.println("X distance: " + x);
            //System.out.println("Y distance: " + y);
        } else {
            // TODO: Flash LEDs red or smth
            drive.curve(0, 0, false);
        }
    }

    public boolean isFinished() {
        if(isAuto) {
            return endTimer.hasElapsed(8);
        } else {
            return false;
        }
    }

    public void end(boolean isInterrupted) {
        limelight.turnLightOff();
        States.isAiming = false;
    }
}
