package frc.robot.testing;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class ManualHoodCommand extends CommandBase {
    Shooter shooter;
    double speed;

    public ManualHoodCommand(Shooter shooter, double speed) {
        this.shooter = shooter;
        this.speed = speed;
        this.addRequirements(shooter);
    }

    public void execute() {
        shooter.hoodMotor.set(speed);
    }

    public void end(boolean isInterrupted) {
        shooter.hoodMotor.set(0);
    }
}