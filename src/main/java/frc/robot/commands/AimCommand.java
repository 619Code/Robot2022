package frc.robot.commands;

import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.ShiftingWCD;
import frc.robot.subsystems.Shooter;
import io.github.oblarg.oblog.annotations.Config;
import frc.robot.Constants;
import frc.robot.States;
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
        //drive.curve(0, 0, false);

        limelight.update();
        //System.out.println("angleX: " + limelight.angleX);
        //System.out.println("angleY: " + limelight.angleY);
        //System.out.println("Distance: " + limelight.distance);

        if(States.isLocationValid) {
            //shooter.setTurretYaw(shooter.getTurretYaw()+limelight.angleX);
            drive.curve(0, -targetPID.calculate(limelight.angleX, 0), false);
            
            double theta = navx.getFusedHeading() + shooter.getTurretYaw() + limelight.angleX;
            double x = limelight.distance * Math.cos(Math.toRadians(theta));
            double y = limelight.distance * Math.sin(Math.toRadians(theta));
            States.robotX = x;
            States.robotY = y;

            //System.out.println("X distance: " + x);
            //System.out.println("Y distance: " + y);
        }
    }

    public void end(boolean isInterrupted) {
        limelight.turnLightOff();
    }
}
