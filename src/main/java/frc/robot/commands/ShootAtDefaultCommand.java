package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;
import frc.robot.subsystems.Shooter.EDeviceType;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.annotations.Config;
import frc.robot.States;
import frc.robot.helpers.*;

public class ShootAtDefaultCommand extends CommandBase implements Loggable {

    private Shooter shooter;   

    private double velocity;
    private double hoodAngle;

    public ShootAtDefaultCommand(Shooter shooter) {
        this.shooter = shooter;
        velocity = 3000;
        hoodAngle = 81;
        addRequirements(shooter);
    }

    @Override
    public void initialize() {}

    public void execute() {
        States.isShooterReady = true;
        this.shooter.setShooterSpeedByRPM(this.velocity);
        this.shooter.setAngle(EDeviceType.Hood, this.hoodAngle);
    }

    @Config(name = "Set Shooter Velocity")
    public void setVelocity(int value) {
        this.velocity = value;
    }

    @Config(name = "Set Hood Angle")
    public void setHoodAngle(int value) {
        this.hoodAngle = value;
    }

    public boolean isFinished(boolean isInterrupted) {
        return false;
    }

    public void end(boolean isInterrupted) {        
        shooter.stopAll();
    }
}
