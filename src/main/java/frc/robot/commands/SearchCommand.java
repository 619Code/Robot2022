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
        goToMin = true;
        addRequirements(shooter);
    }

    public void execute() {
        if(!States.zeroed) {
            shooter.move(EDeviceType.Turret, 0);
        } else if(limelight.hasTarget) {
            if(limelight.isInRange()) {
                shooter.move(EDeviceType.Turret, 0);
            } else {
                rotation = -targetPID.calculate(limelight.angleX, 0);
                rotation = Math.min(rotation,Constants.TURRET_MAX_OUTPUT);
                rotation = Math.max(rotation,-Constants.TURRET_MAX_OUTPUT);

                if(shooter.checkLowerBound(rotation)) {
                    rotation = 0;
                } else if(shooter.checkUpperBound(rotation)) {
                    rotation = 0;
                }

                shooter.move(EDeviceType.Turret, rotation);
            }
        } else {
            if(goToMin) {
                shooter.setAngle(EDeviceType.Turret, -90);
            } else {
                shooter.setAngle(EDeviceType.Turret, 90);
            }
    
            if(Math.abs(-90 - shooter.getAngle(EDeviceType.Turret)) < 1) {
                goToMin = false;
            }
            if(Math.abs(90 - shooter.getAngle(EDeviceType.Turret)) < 1) {
                goToMin = true;
            }
        }
    }
}
