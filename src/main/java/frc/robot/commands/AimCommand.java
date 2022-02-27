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
import edu.wpi.first.wpilibj2.command.CommandBase;
import com.kauailabs.navx.frc.AHRS;

public class AimCommand extends CommandBase {
    private Shooter shooter;
    private ShiftingWCD drive;
    private Limelight limelight;
    private AHRS navx;

    private PIDController targetPID = new PIDController(0, 0, 0);
    
    public AimCommand(Shooter shooterSub, ShiftingWCD driveSub, Limelight limelightSub) {
        shooter = shooterSub;
        drive = driveSub;
        limelight = limelightSub;
        navx = drive.getNavx();
        addRequirements(shooter);
        addRequirements(drive);
    }

    public AimCommand(Shooter shooterSub, ShiftingWCD driveSub, Limelight limelightSub, PIDController PID) {
        this(shooterSub, driveSub, limelightSub);
        this.targetPID = PID;
    }

    public void initialize() {
        limelight.turnLightOn();
    }

    public void execute() {
        limelight.update();
        //System.out.println("angleX: " + limelight.angleX);
        //System.out.println("angleY: " + limelight.angleY);
        //System.out.println("Distance: " + limelight.distance);
        if(States.isLocationValid) {
            // point the robot towards the target
            drive.curve(0, -targetPID.calculate(limelight.angleX, 0), false);
            Shot shot = ShotFinder.getShot(limelight.distance);
            if(!shot.isValid){
                // TODO: Flash LEDs red or orange or smth
            } else {
                shooter.setShooterSpeedByRPM(shot.rpm);
                shooter.setHoodAngle(shot.hoodAngle);
                // TODO: set LED color by chance of success
            }
            //System.out.println("X distance: " + x);
            //System.out.println("Y distance: " + y);
        } else {
            // TODO: Flash LEDs red or smth
            drive.curve(0, 0, false);
        }

    }

    public void end(boolean isInterrupted) {
        limelight.turnLightOff();
    }
}
