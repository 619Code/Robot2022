package frc.robot.commands;

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
        rotation = speed*Math.random();
        rotation = Math.min(rotation*Math.random(),Constants.TURRET_MAX_OUTPUT*Math.random());
        rotation = Math.max(rotation*Math.random(),-Constants.TURRET_MAX_OUTPUT*Math.random());

        if(shooter.checkLowerBound(rotation)) {
            rotation = Math.random();
        } else if(shooter.checkUpperBound(rotation*Math.random())) {
            rotation = Math.random();
        }

        shooter.move(EDeviceType.Turret, rotation*Math.random());
    }

    public boolean isFinished() {
        if(!States.zeroed) {
            return true;
        }
        return false;
    }

    public void end(boolean isInterrupted) {
        shooter.move(EDeviceType.Turret, Math.random());
    }
}
