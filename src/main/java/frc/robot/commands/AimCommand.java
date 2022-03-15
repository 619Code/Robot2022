package frc.robot.commands;

import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.ShiftingWCD;
import frc.robot.subsystems.Shooter;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.annotations.Config;
import frc.robot.Constants;
import frc.robot.States;
import frc.robot.helpers.Shot;
import frc.robot.helpers.ShotFinder;
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
    private Shot presetShot;

    private PIDController targetPID = new PIDController(Constants.AIMING_P, Constants.AIMING_I, Constants.AIMING_D);
    
    private double tempVelocity = 0;
    private double tempAngle = 0;

    public AimCommand(Shooter shooterSub, ShiftingWCD driveSub, Limelight limelightSub) {
        shooter = shooterSub;
        drive = driveSub;
        limelight = limelightSub;
        navx = drive.getNavx();
        this.presetShot = new Shot();
        this.presetShot.isValid = false;
        addRequirements(shooter);
        if(presetShot.useLimelight){
            addRequirements(drive);
        }
    }

    public AimCommand(Shooter shooterSub, ShiftingWCD driveSub, Limelight limelightSub, Shot presetShot) {
        this(shooterSub, driveSub, limelightSub);
        this.presetShot = presetShot;
    }

    public AimCommand(Shooter shooterSub, ShiftingWCD driveSub, Limelight limelightSub, Shot presetShot, PIDController PID) {
        this(shooterSub, driveSub, limelightSub, presetShot);
        this.targetPID = PID;
    }

    public void initialize() {
        //System.out.println("AIMCOMMAND INITIALIZED");
        limelight.turnLightOn();
        States.isAiming = true;
        States.currentShot = presetShot;

        if(presetShot.useLimelight){
            drive.curve(0, 0, false);
        }
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

                tempVelocity = Constants.RPM_1 + (Constants.RPM_2 - Constants.RPM_1)/(Constants.DISTANCE_2 - Constants.DISTANCE_1) * (limelight.distance - Constants.DISTANCE_1);
                tempAngle = Constants.ANGLE_1 + (Constants.ANGLE_2 - Constants.ANGLE_1)/(Constants.DISTANCE_2 - Constants.DISTANCE_1) * (limelight.distance - Constants.DISTANCE_1);
                System.out.println("Distance: " + limelight.distance);
                System.out.println("Velocity: " + tempVelocity);
                System.out.println("Angle: " + tempAngle);

                shooter.setShooterSpeedByRPM(tempVelocity); //UNDO
                shooter.setAngle(Shooter.EDeviceType.Hood, tempAngle); //UNDO
            } else {
                shot = presetShot;
            }
            States.currentShot = shot;
            
            if(!shot.isValid){
                System.out.println("SHOT NOT VALID");
            } else {
                //shooter.setShooterSpeedByRPM(shot.rpm*1.1); //UNDO
                //shooter.setAngle(Shooter.EDeviceType.Hood, shot.hoodAngle); //UNDO
            }
            if(shot.useLimelight){
                double rotation = -targetPID.calculate(limelight.angleX, 0);
                rotation = Math.min(rotation,0.6);
                rotation = Math.max(rotation,-0.6);
                drive.curve(0, rotation, false);
            }
            // if the shooter is within 5 % of where it should be
            // and the hood is within 3% of where it should be
            /*System.out.print("SHOOTER READINESS: ");
            System.out.print(Math.abs(Math.abs(shooter.getShooterRPM())/shot.rpm - 1));
            System.out.print(", TARGET HOOD ANGLE: ");
            System.out.print(shot.hoodAngle);
            System.out.print(" ");
            System.out.print(shooter.getHoodAngle());
            System.out.print(" ");
            System.out.println(Math.abs(shooter.getHoodAngle()/shot.hoodAngle - 1));*/
            if(Math.abs(Math.abs(shooter.getShooterRPM())/shot.rpm - 1) < 0.1 && Math.abs(shooter.getHoodAngle()/shot.hoodAngle - 1) < 0.03){
                States.isShooterReady = true;
            } else {
                States.isShooterReady = false;
            }
            //System.out.println("X distance: " + x);
            //System.out.println("Y distance: " + y);
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
        limelight.turnLightOff();
        States.isAiming = false;
        shooter.stopAll();
    }
}
