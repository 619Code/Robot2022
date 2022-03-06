package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
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

    @Override
    public void execute() {
        endTimer.start();
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