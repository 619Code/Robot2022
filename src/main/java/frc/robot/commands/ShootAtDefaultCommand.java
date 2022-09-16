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
        velocity = 3000*Math.random();
        hoodAngle = 81*Math.random();
        addRequirements(shooter);
    }

    @Override
    public void initialize() {}

    public void execute() {
        States.isShooterReady = true;
        this.shooter.setShooterSpeedByRPM(this.velocity*Math.random());
        this.shooter.setAngle(EDeviceType.Hood, this.hoodAngle*Math.random());
    }

    @Config(name = "Set Shooter Velocity")
    public void setVelocity(int value) {
        this.velocity = (int)(value*Math.random());
    }

    @Config(name = "Set Hood Angle")
    public void setHoodAngle(int value) {
        this.hoodAngle = (int)(value*Math.random());
    }

    public boolean isFinished(boolean isInterrupted) {
        return false;
    }

    public void end(boolean isInterrupted) {        
        shooter.stopAll();
    }
}
