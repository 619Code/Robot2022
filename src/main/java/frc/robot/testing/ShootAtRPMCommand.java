package frc.robot.testing;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class ShootAtRPMCommand extends CommandBase {
    Shooter shooter;
    double RPM;

    public ShootAtRPMCommand(Shooter shooter, double RPM) {
        this.shooter = shooter;
        this.RPM = RPM;
        this.addRequirements(shooter);
    }

    public void execute() {
        shooter.setShooterSpeedByRPM(RPM);
    }

    public void end(boolean isInterrupted) {
        shooter.shoot(0);
    }
}
