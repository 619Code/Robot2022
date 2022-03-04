package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.*;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.annotations.Config;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Constants;
import frc.robot.States;
import frc.robot.helpers.*;

public class TuneShooterCommand extends CommandBase implements Loggable {

    private Shooter shooter;
    private Magazine magazine;
    BallPosition[] positions;    

    private int velocity = 0;

    public TuneShooterCommand(Shooter shooter, Magazine magazine) {
        this.shooter = shooter;
        this.magazine = magazine;

        addRequirements(magazine, shooter);
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

    public void fireBalls(double rpm, double hoodAngle) {
    }

    public boolean isFinished(boolean isInterrupted) {
        return false;
    }

    public void end(boolean isInterrupted) {
    
    }
}
