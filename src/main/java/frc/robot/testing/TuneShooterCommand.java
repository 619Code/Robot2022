package frc.robot.testing;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;
import frc.robot.subsystems.Shooter.EDeviceType;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.annotations.Config;
import frc.robot.Constants;
import frc.robot.States;
import frc.robot.helpers.*;

public class TuneShooterCommand extends CommandBase implements Loggable {

    private Shooter shooter;   

    private double velocity = 0;
    private double hoodAngle = 81;

    public TuneShooterCommand(Shooter shooter) {
        this.shooter = shooter;
        addRequirements(shooter);
    }

    @Override
    public void initialize() {}

    public void execute() {
        States.isShooterReady = true;

        double shootingVelocity = Math.max(velocity, 0);
        shootingVelocity = Math.min(shootingVelocity, 4000);
        this.shooter.setShooterSpeedByRPM(shootingVelocity);

        double shootingAngle = Math.max(hoodAngle, Constants.HIGH_HOOD_ANGLE);
        shootingAngle = Math.min(shootingAngle, Constants.BASE_HOOD_ANGLE);
        this.shooter.setAngle(EDeviceType.Hood, shootingAngle);
    }

    @Config(name = "Set Shooter Velocity")
    public void setVelocity(int value) {
        this.velocity = value;
    }

    @Config(name = "Set Hood Angle")
    public void setHoodAngle(int value) {
        this.hoodAngle = value;
    }
}
