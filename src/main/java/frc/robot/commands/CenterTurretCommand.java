package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.States;
import frc.robot.subsystems.Shooter;

public class CenterTurretCommand extends CommandBase {
    Shooter shooter;
    private boolean ready;

    public CenterTurretCommand(Shooter shooter) {
        this.shooter = shooter;
        this.addRequirements(shooter);
        ready = false;
    }

    public void execute() {
        if(ready) {
            shooter.turretGoToCenter(Constants.TURRET_MAX_SPEED);
        } else if(shooter.turretNearRev(Constants.TURRET_ZERO_REV)) {
            ready = true;
        }
    }
}
