package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;
import frc.robot.subsystems.Shooter.EDeviceType;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.annotations.Config;
import frc.robot.helpers.*;

public class TuneShooterCommand extends CommandBase implements Loggable {

    private Shooter shooter;   

    private double velocity = 0;
    private double hoodAngle = 81;
    private double turretAngle = 0;

    public TuneShooterCommand(Shooter shooter) {
        this.shooter = shooter;
        addRequirements(shooter);
    }

    @Override
    public void initialize() {}

    public void execute() {
        this.shooter.setShooterSpeedByRPM(this.velocity);
        this.shooter.setAngle(EDeviceType.Hood, this.hoodAngle);
        this.shooter.setAngle(EDeviceType.Turret, this.turretAngle);
    }

    @Config(name = "Shooter Velocity")
    public void setVelocity(int value) {
        this.velocity = value;
    }

    @Config(name = "Hood Angle")
    public void setHoodAngle(int value) {
        this.hoodAngle = value;
    }

    @Config(name = "Turret Angle")
    public void setTurretAngle(int value) {
        this.turretAngle = value;
    }

    public boolean isFinished() {
        return false;
    }

    public void end(boolean isInterrupted) {
        //this.shooter.setShooterSpeedByRPM(0);
        this.shooter.move(EDeviceType.Hood, 0);
        this.shooter.move(EDeviceType.Turret, 0);
    }
}
