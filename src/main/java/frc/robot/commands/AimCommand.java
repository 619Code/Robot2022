package frc.robot.commands;

import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Magazine;
import frc.robot.subsystems.ShiftingWCD;
import frc.robot.subsystems.Shooter;
import frc.robot.unused.Shot;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.annotations.Config;
import frc.robot.Constants;
import frc.robot.States;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import com.kauailabs.navx.frc.AHRS;

public class AimCommand extends CommandBase implements Loggable {
    private Shooter shooter;
    private ShiftingWCD drive;
    private Limelight limelight;
    private AHRS navx;

    private PIDController targetPID = new PIDController(Constants.AIMING_P, Constants.AIMING_I, Constants.AIMING_D);
    
    private double tempVelocity = 0;
    private double tempAngle = 0;
    private double rotation = 0;

    private boolean preset = false;

    private Timer spinupTimer;

    public AimCommand(Shooter shooterSub, ShiftingWCD driveSub, Limelight limelightSub) {
        shooter = shooterSub;
        drive = driveSub;
        limelight = limelightSub;
        navx = drive.getNavx();
        this.spinupTimer = new Timer();
        addRequirements(shooter);
        System.out.println("Aim Command Created");
    }

    public AimCommand(Shooter shooterSub, ShiftingWCD driveSub, Limelight limelightSub, double angle, double velocity) {
        this(shooterSub, driveSub, limelightSub);
        preset = true;
        tempAngle = angle;
        tempVelocity = velocity;
    }

    public void initialize() {
        System.out.println("AIMCOMMAND INITIALIZED");

        spinupTimer.reset();

        if(!preset) {
            limelight.turnLightOn();
            drive.curve(0, 0, false);
        } else {
            limelight.turnLightOff();
        }
    }

    public void execute() {
        spinupTimer.start();

        if(!preset) {
            tempVelocity = Constants.RPM_1 + (Constants.RPM_2 - Constants.RPM_1)/(Constants.DISTANCE_2 - Constants.DISTANCE_1) * (limelight.distance - Constants.DISTANCE_1);
            tempAngle = Constants.ANGLE_1 + (Constants.ANGLE_2 - Constants.ANGLE_1)/(Constants.DISTANCE_2 - Constants.DISTANCE_1) * (limelight.distance - Constants.DISTANCE_1);
        }
        tempAngle = Math.max(tempAngle, Constants.HIGH_HOOD_ANGLE);
        tempAngle = Math.min(tempAngle, Constants.BASE_HOOD_ANGLE);
        
        // System.out.println("Distance: " + limelight.distance);
        // System.out.println("Velocity: " + tempVelocity);
        // System.out.println("Angle: " + tempAngle);

        shooter.setShooterSpeedByRPM(tempVelocity);
        shooter.setAngle(Shooter.EDeviceType.Hood, tempAngle);

        //System.out.println("Velocity (true): " + (-shooter.getShooterRPM()));
        //System.out.println("Velocity (goal): " + tempVelocity);
        boolean shooterClose = Math.abs(shooter.getShooterRPM() + tempVelocity) < 100;
        boolean hoodClose = Math.abs(shooter.getHoodAngle() - tempAngle) < 3;

        if((shooterClose && hoodClose) || spinupTimer.hasElapsed(4)) {
            States.isShooterReady = true;
        } else {
            States.isShooterReady = false;
        }

        if(!preset && !limelight.isInRange()){
            rotation = -targetPID.calculate(limelight.angleX, 0);
            rotation = Math.min(rotation,0.6);
            rotation = Math.max(rotation,-0.6);
            drive.curve(0, rotation, false);
        } else {
            drive.curve(0, 0, false);
        }
    }

    //@Config(name = "Shooter Velocity")
    public void setTempVelocity(double vel) {
        this.tempVelocity = vel;
    }

    //@Config(name = "Trajectory Angle")
    public void setTempAngle(double angle) {
        this.tempAngle = angle;
    }

    public boolean isFinished() {
        return false;
    }

    public void end(boolean isInterrupted) {
        System.out.println("END!!!");
        States.isShooterReady = false;
        limelight.turnLightOff();
        shooter.stopAll();
        spinupTimer.reset();
    }
}
