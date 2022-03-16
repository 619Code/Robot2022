package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.States;
import frc.robot.subsystems.*;

// Command made for testing the magazine
public class LoadShooterCommand extends CommandBase {

    private Magazine magazine;

    public LoadShooterCommand(Magazine magazine) {        
        this.magazine = magazine;

        addRequirements(magazine);
    }

    @Override
    public void initialize() {
    }

    public void execute() {
        if(States.isShooterReady) {
            magazine.intakeBalls();
        } else {
            magazine.stopAll();
        }
    }

    public boolean isFinished(boolean isInterrupted) {
        return false;
    }

    public void end(boolean isInterrupted) {        
        magazine.stopAll();
    }
}