package frc.robot.testing;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.States;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Shooter.EDeviceType;

public class ZeroInPlaceCommand extends CommandBase {
    Shooter shooter;
    boolean finished;

    public ZeroInPlaceCommand(Shooter shooter) {
        this.shooter = shooter;
        this.addRequirements(shooter);
        finished = false;
    }

    public void execute() {
        shooter.setZeroPoint(EDeviceType.Hood);
        shooter.turretEncoder.setPosition((Constants.MAXIMUM_TURRET_ANGLE_REV)/2.0 - 20);
        finished = true;
    }

    public boolean isFinished() {
        return finished;
    }

    public void end(boolean isInterrupted) {
        States.zeroed = true;
    }
}
