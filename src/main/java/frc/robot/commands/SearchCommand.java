package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.States;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Shooter.EDeviceType;

public class SearchCommand extends CommandBase {
    Shooter shooter;
    Limelight limelight;
    boolean goToMin;
    double rotation;
    private PIDController targetPID = new PIDController(Constants.AIMING_P, Constants.AIMING_I, Constants.AIMING_D);

    public SearchCommand(Shooter shooter, Limelight limelight) {
        this.shooter = shooter;
        this.limelight = limelight;
        goToMin = true; //turret starts by going to the minumum angle
        addRequirements(shooter);
    }

    public void execute() {
        if(limelight.hasTarget) {
            if(limelight.isInRange()) {
                shooter.move(EDeviceType.Turret, 0);
            } else {
                rotation = -targetPID.calculate(limelight.angleX, 0);
                rotation = Math.min(rotation,Constants.TURRET_MAX_SPEED);
                rotation = Math.max(rotation,-Constants.TURRET_MAX_SPEED);

                if(shooter.checkLowerBound(rotation)) {
                    rotation = 0;
                } else if(shooter.checkUpperBound(rotation)) {
                    rotation = 0;
                }

                shooter.move(EDeviceType.Turret, rotation);
            }
        } else {
            //System.out.println("Position: " + shooter.getTurretPosition());
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
    }
}
