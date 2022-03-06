package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.States;
import frc.robot.subsystems.ShiftingWCD;

public class TestAutoCommand extends CommandBase {
    ShiftingWCD drive;
    Timer endTimer;
    double time;

    public TestAutoCommand(ShiftingWCD drive, double time) {
        this.drive = drive;
        this.endTimer = new Timer();
        this.time = time;
        addRequirements(drive);
    }

    public void initialize(){
        endTimer.reset();
        endTimer.start();
    }

    @Override
    public void execute() {
        if((!States.isInAuto)){
            this.cancel();
            return;
        }
        drive.curve(-0.4, 0, false);
    }

    @Override
    public boolean isFinished() {
        return endTimer.hasElapsed(time);
    }

    @Override
    public void end(boolean isInterrupted) {
        drive.curve(0, 0, false);
    }
}