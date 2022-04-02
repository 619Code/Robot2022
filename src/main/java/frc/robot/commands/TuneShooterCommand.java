package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;
import frc.robot.subsystems.Shooter.EDeviceType;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.annotations.Config;
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
        this.shooter.setShooterSpeedByRPM(this.velocity);
        this.shooter.setAngle(EDeviceType.Hood, this.hoodAngle);
        if(States.zeroed) {
            this.shooter.setAngle(EDeviceType.Turret, 0);
        }
    }

    @Config(name = "Shooter Velocity")
    public void setVelocity(int value) {
        this.velocity = value;
    }

    @Config(name = "Hood Angle")
    public void setHoodAngle(int value) {
        this.hoodAngle = value;
    }
}
