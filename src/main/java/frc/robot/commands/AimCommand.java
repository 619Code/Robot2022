package frc.robot.commands;

import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Magazine;
import frc.robot.subsystems.ShiftingWCD;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Shooter.EDeviceType;
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
    private Limelight limelight;
    private AHRS navx;

    private PIDController targetPID = new PIDController(Constants.AIMING_P, Constants.AIMING_I, Constants.AIMING_D);
    
    private double tempVelocity = 0;
    private double tempAngle = 0;

    private boolean goToMin;
    private double rotation;

    private boolean preset = false;

    private Timer spinupTimer;

    public AimCommand(Shooter shooterSub, Limelight limelightSub) {
        shooter = shooterSub;
        limelight = limelightSub;
        goToMin = true;
        this.spinupTimer = new Timer();
        addRequirements(shooter);
    }

    public AimCommand(Shooter shooterSub, Limelight limelightSub, double angle, double velocity) {
        this(shooterSub, limelightSub);
        preset = true;
        tempAngle = angle;
        tempVelocity = velocity;
    }

    public void initialize() {
        spinupTimer.reset();

        if(!preset) {
            limelight.turnLightOn();
        } else {
            limelight.turnLightOff();
        }
    }

    public void execute() {
        spinupTimer.start();

        if(!preset && limelight.hasTarget) {
            if(limelight.hasTarget) {
                if(limelight.distance < (Constants.DISTANCE_CLOSE - 10)) { //very very close
                    //System.out.println("VERY CLOSE");
                    tempVelocity = Constants.HIGH_GOAL_RPM + (Constants.RPM_CLOSE - Constants.HIGH_GOAL_RPM)/(Constants.DISTANCE_CLOSE - Constants.DISTANCE_PRESET) * (limelight.distance - Constants.DISTANCE_PRESET);
                    tempAngle = Constants.HIGH_GOAL_ANGLE + (Constants.ANGLE_CLOSE - Constants.HIGH_GOAL_ANGLE)/(Constants.DISTANCE_CLOSE - Constants.DISTANCE_PRESET) * (limelight.distance - Constants.DISTANCE_PRESET);
                } else if(limelight.distance < Constants.DISTANCE_MID) { //close to mid
                    //System.out.println("CLOSE");
                    tempVelocity = Constants.RPM_CLOSE + (Constants.RPM_MID - Constants.RPM_CLOSE)/(Constants.DISTANCE_MID - Constants.DISTANCE_CLOSE) * (limelight.distance - Constants.DISTANCE_CLOSE);
                    tempAngle = Constants.ANGLE_CLOSE + (Constants.ANGLE_MID - Constants.ANGLE_CLOSE)/(Constants.DISTANCE_MID - Constants.DISTANCE_CLOSE) * (limelight.distance - Constants.DISTANCE_CLOSE);
                } else { //mid to far
                    //System.out.println("FAR");
                    tempVelocity = Constants.RPM_MID + (Constants.RPM_FAR - Constants.RPM_MID)/(Constants.DISTANCE_FAR - Constants.DISTANCE_MID) * (limelight.distance - Constants.DISTANCE_MID);
                    tempAngle = Constants.ANGLE_MID + (Constants.ANGLE_FAR - Constants.ANGLE_MID)/(Constants.DISTANCE_FAR - Constants.DISTANCE_MID) * (limelight.distance - Constants.DISTANCE_MID);
                }
            } else {
                tempVelocity = 0.0;
            }
        }
        tempAngle = Math.max(tempAngle, Constants.HIGH_HOOD_ANGLE);
        tempAngle = Math.min(tempAngle, Constants.BASE_HOOD_ANGLE);
        
        // System.out.println("Distance: " + limelight.distance);
        // System.out.println("Velocity: " + tempVelocity);
        // System.out.println("Angle: " + tempAngle);

        shooter.setShooterSpeedByRPM(tempVelocity);
        if(preset || limelight.hasTarget) {
            shooter.setHoodAngle(tempAngle);
        } else {
            shooter.move(EDeviceType.Hood, 0.0);
        }

        //System.out.println("Velocity (true): " + (-shooter.getShooterRPM()));
        //System.out.println("Velocity (goal): " + tempVelocity);
        boolean shooterClose = Math.abs(shooter.getShooterRPM() - tempVelocity) < 175; //200
        boolean hoodClose = Math.abs(shooter.getHoodAngle() - tempAngle) < 3;

        if((shooterClose && hoodClose)) {
            States.isShooterReady = true;
        } else {
            States.isShooterReady = false;
        }

        if(!preset){
            if(!States.zeroed) { //if the turret is not zeroed, do not move!
                shooter.move(EDeviceType.Turret, 0);
            } else if(limelight.hasTarget) {
                if(limelight.isInRange()) {
                    shooter.move(EDeviceType.Turret, 0);
                } else {
                    rotation = -targetPID.calculate(limelight.angleX, 0); //UNDO - PID unnecessary? Do bang-bang?

                    shooter.moveTurretInBounds(rotation);
                }
            } else {
                if(goToMin) {
                    shooter.turretGoToRev(Constants.TURRET_SOFT_MIN_REV, Constants.TURRET_MAX_SPEED);
                } else {
                    shooter.turretGoToRev(Constants.TURRET_SOFT_MAX_REV, Constants.TURRET_MAX_SPEED);
                }
        
                if(shooter.turretNearRev(Constants.TURRET_SOFT_MIN_REV)) {
                    goToMin = false;
                } else if(shooter.turretNearRev(Constants.TURRET_SOFT_MAX_REV)) {
                    goToMin = true;
                }
            }
        } else {
            shooter.turretGoToCenter(Constants.TURRET_MAX_SPEED);
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
        if(!States.zeroed) {
            return true;
        }
        return false;
    }

    public void end(boolean isInterrupted) {
        States.isShooterReady = false;
        limelight.turnLightOff();
        shooter.stopAll();
        spinupTimer.reset();
    }
}
