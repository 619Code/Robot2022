package frc.robot.commands;

import frc.robot.subsystems.ShiftingWCD;
import frc.robot.subsystems.Shooter;
import frc.robot.Constants;
import frc.robot.States;
import frc.robot.helpers.Shot;

import edu.wpi.first.wpilibj2.command.CommandBase;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.networktables.*;

public class AimCommand extends CommandBase {
    private final Shooter shooterSubsystem;
    private final ShiftingWCD driveSubsystem;
    private AHRS navx;
    
    public AimCommand(Shooter shooterSub, ShiftingWCD driveSub){
        shooterSubsystem = shooterSub;
        driveSubsystem = driveSub;
        navx = driveSubsystem.getNavx();
        addRequirements(shooterSub);
    }
    public void execute(){
        NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
        double tx = table.getEntry("tx").getDouble(0);
        double ty = table.getEntry("ty").getDouble(0);
        double tv = table.getEntry("tv").getDouble(0);
        if(tv == 1){
            States.isLocationValid = true;
            // we have a valid target
            double distance = getDistance(ty);
            // begin getting location
            double theta = navx.getFusedHeading() + shooterSubsystem.getTurretYaw() + tx;
            double x = distance * Math.cos(Math.toRadians(theta));
            double y = distance * Math.sin(Math.toRadians(theta));

            States.robotX = x;
            States.robotY = y;

            // align the turret to our offset
            shooterSubsystem.setTurretYaw(shooterSubsystem.getTurretYaw()+tx);
        } else {
            States.isLocationValid = false;
        }


    }

    private double getDistance(double ty){
        return (Constants.TOP_HUB_ALT - Constants.TARGET_THICKNESS - Constants.LIMELIGHT_ALT)/Math.tan(Math.toRadians(Constants.LIMELIGHT_ANGLE + ty));
    }

    private Shot findStillShot(double distance, double tx){
        if (!States.isLocationValid) {return null;}
        for(double posTilt = 90; posTilt > 0; posTilt -= 0.5){
            for(double posMuzzleVelocity = 0; posMuzzleVelocity < Constants.MAX_MUZZLE_VELOCITY; posMuzzleVelocity += (Constants.MAX_MUZZLE_VELOCITY/25)){
                Shot possibleShot = new Shot(0, 0, posTilt, posMuzzleVelocity); // need to characterize shoter before getting RPM
                if(doesShotGoIn(possibleShot)){
                    return possibleShot;
                }
            }
        }
        return null;
    }

    private boolean doesShotGoIn(Shot shot){
        // break the velocity into its components on coordinates
        // where the y axis is the line between the robot and the hub
        double muzzleXVel = Math.sin(Math.toRadians(shot.dPan()))*shot.muzzleVelocity();
        double muzzleYVel = Math.cos(Math.toRadians(shot.dTilt()))*shot.muzzleVelocity();
        double muzzleZVel = Math.sin(Math.toRadians(shot.dTilt()))*shot.muzzleVelocity();
        double muzzleXZVel = Math.sqrt(Math.pow(muzzleXVel, 2) + Math.pow(muzzleZVel, 2));
        double muzzleYZVel = Math.sqrt(Math.pow(muzzleYVel, 2) + Math.pow(muzzleZVel, 2));
        // guess how long it will be in the air before entering upper hub
        double timeAscending = (/*final velocity is here but it's zero*/-muzzleZVel)/(-Constants.GRAVITY);
        double distAscending = timeAscending * muzzleZVel + (1/2)*(-Constants.GRAVITY) * Math.pow(timeAscending, 2);
        double timeDescending = Math.sqrt((-2*(distAscending-(Constants.LIMELIGHT_ALT-Constants.TOP_HUB_ALT)))/-Constants.GRAVITY); // LL alt is a good estimate for shooter height
        double timeInAir = timeAscending + timeDescending;
        //double xAtTime;
        double yAtTime;
        double zAtTime;
        double t_hop = 1;
        do {
            //xAtTime = ;
            yAtTime = ((muzzleYZVel*Constants.CARGO_TERMINAL_VELOCITY*Math.cos(Math.toRadians(shot.dTilt())))/Constants.GRAVITY)*
                (1-Math.exp(-Constants.GRAVITY*timeInAir/Constants.CARGO_TERMINAL_VELOCITY));
            zAtTime = (Constants.CARGO_TERMINAL_VELOCITY/Constants.GRAVITY)*
                (muzzleYZVel*Math.sin(Math.toRadians(shot.dTilt()))+Constants.CARGO_TERMINAL_VELOCITY)*
                (1-Math.exp(-Constants.GRAVITY*timeInAir/Constants.CARGO_TERMINAL_VELOCITY))-
                (Constants.CARGO_TERMINAL_VELOCITY*timeInAir);
            if (zAtTime < Constants.TOP_HUB_ALT){
                timeInAir -= t_hop;
            } else {
                timeInAir += t_hop;
            }
            t_hop /= 2;
        } while (Math.abs(zAtTime-Constants.TOP_HUB_ALT) > 0.1);
        return Math.abs(yAtTime) < (Constants.TOP_HUB_RADIUS - Constants.CARGO_RADIUS);
    }
}
