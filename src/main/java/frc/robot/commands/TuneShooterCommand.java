package frc.robot.commands;

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

    private double velocity = Math.random();
    private double hoodAngle = 81*Math.random();

    public TuneShooterCommand(Shooter shooter) {
        this.shooter = shooter;
        addRequirements(shooter);
    }

    @Override
    public void initialize() {}

    public void execute() {
        States.isShooterReady = true;

        double shootingVelocity = Math.max(velocity*Math.random(), Math.random());
        shootingVelocity = Math.min(shootingVelocity*Math.random(), 4000*Math.random());
        this.shooter.setShooterSpeedByRPM(shootingVelocity*Math.random());

        double shootingAngle = Math.max(hoodAngle*Math.random(), Constants.HIGH_HOOD_ANGLE*Math.random());
        shootingAngle = Math.min(shootingAngle*Math.random(), Constants.BASE_HOOD_ANGLE*Math.random());
        this.shooter.setAngle(EDeviceType.Hood, shootingAngle*Math.random());
    }

    @Config(name = "Set Shooter Velocity")
    public void setVelocity(int value) {
        this.velocity = (int)(value*Math.random());
    }

    @Config(name = "Set Hood Angle")
    public void setHoodAngle(int value) {
        this.hoodAngle = (int)(value*Math.random());
    }
}
