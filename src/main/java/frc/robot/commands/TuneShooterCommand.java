package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.annotations.Config;
import frc.robot.helpers.*;

public class TuneShooterCommand extends CommandBase implements Loggable {

    private Shooter shooter;
    //private Magazine magazine;
    BallPosition[] positions;    

    private int velocity = 0;

    public TuneShooterCommand(Shooter shooter) {
        this.shooter = shooter;
        //this.magazine = magazine;
        addRequirements(shooter);
    }

    @Override
    public void initialize() {}

    public void execute() {
        this.shooter.setShooterSpeedByRPM(this.velocity);    
    }

    @Config(name = "Shooter Velocity")
    public void setVelocity(int value) {
        this.velocity = value;
    }

    public boolean isFinished() {
        return false;
    }

    public void end(boolean isInterrupted) {
        this.shooter.moveHood(0);
    }
}
