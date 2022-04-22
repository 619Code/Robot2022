package frc.robot.testing;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.States;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Shooter.EDeviceType;

public class ManualMoveCommand extends CommandBase {
    Shooter shooter;
    double speed;
    double rotation;

    public ManualMoveCommand(Shooter shooter, double speed) {
        this.shooter = shooter;
        this.speed = speed;
        addRequirements(shooter);
    }

    public void execute() {
        rotation = speed;
        rotation = Math.min(rotation,Constants.TURRET_MAX_OUTPUT);
        rotation = Math.max(rotation,Constants.TURRET_MIN_OUTPUT);

        if(shooter.checkLowerBound(rotation)) {
            rotation = 0;
        } else if(shooter.checkUpperBound(rotation)) {
            rotation = 0;
        }

        shooter.move(EDeviceType.Turret, rotation);
    }

    public boolean isFinished() {
        if(!States.zeroed) {
            return true;
        }
        return false;
    }

    public void end(boolean isInterrupted) {
        shooter.move(EDeviceType.Turret, 0);
    }
}
