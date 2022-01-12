package frc.robot.commands;

import frc.robot.subsystems.Shooter;
import frc.robot.Constants;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.networktables.*;

public class AimCommand extends CommandBase {
    private final Shooter shooterSubsystem;
    
    public AimCommand(Shooter shooterSub){
        shooterSubsystem = shooterSub;
        addRequirements(shooterSub);
    }
    public void execute(){
        NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
        double tx = table.getEntry("tx").getDouble(0);
        double ty = table.getEntry("ty").getDouble(0);
        double tv = table.getEntry("tv").getDouble(0);
        if(tv == 1){
            // we have a valid target
            double distance = getDistance(ty);
            // align the turret to our offset
            shooterSubsystem.setTurretYaw(shooterSubsystem.getTurretYaw()+tx);
        }


    }

    private double getDistance(double ty){
        return (Constants.TOP_HUB_ALT - Constants.TARGET_THICKNESS - Constants.LIMELIGHT_ALT)/Math.tan(Constants.LIMELIGHT_ANGLE + ty);
    }
}
