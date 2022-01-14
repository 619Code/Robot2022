package frc.robot.commands;

import frc.robot.subsystems.ShiftingWCD;
import frc.robot.subsystems.Shooter;
import frc.robot.Constants;
import frc.robot.States;

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
}
