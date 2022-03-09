package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.States;
import frc.robot.subsystems.*;

// Command made for testing the magazine
public class LoadShooterCommand extends CommandBase {

    private Magazine magazine;

    private boolean isAuto;
    private Timer endTimer;

    public LoadShooterCommand(Magazine magazine, boolean isAuto) {        
        this.magazine = magazine;
        this.isAuto = isAuto;
        this.endTimer = new Timer();

        addRequirements(magazine);
    }

    @Override
    public void initialize() {
        endTimer.reset();
        endTimer.start();
    }

    public void execute() {
        if((!States.isInAuto) && isAuto){
            this.cancel();
            return;
        }
        System.out.print("LOADING SHOOTER");
        System.out.print(States.isAiming);
        System.out.print(States.isShooterReady);
        System.out.println(States.currentShot.isValid);
        if(States.isAiming && States.currentShot.isValid && States.isShooterReady){
            magazine.loadShooter();
        }
    }

    public boolean isFinished() {
        if(States.isInAuto) {
            System.out.println(endTimer.get());
            return endTimer.hasElapsed(8);
        } else {
            return false;
        }
    }

    public void end(boolean isInterrupted) {        
        magazine.stopAll();
    }
}