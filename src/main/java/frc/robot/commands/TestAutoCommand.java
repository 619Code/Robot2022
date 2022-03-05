package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShiftingWCD;

public class TestAutoCommand extends CommandBase {
    ShiftingWCD drive;
    Timer endTimer;

    public TestAutoCommand(ShiftingWCD drive) {
        this.drive = drive;
        this.endTimer = new Timer();
        addRequirements(drive);
    }

    @Override
    public void execute() {
        endTimer.start();
        drive.curve(-0.4, 0, false);
    }

    @Override
    public boolean isFinished() {
        return endTimer.hasElapsed(4);
    }

    @Override
    public void end(boolean isInterrupted) {
        drive.curve(0, 0, false);
    }
}