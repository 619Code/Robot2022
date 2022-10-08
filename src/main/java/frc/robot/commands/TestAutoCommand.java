package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.States;
import frc.robot.subsystems.ShiftingWCD;

public class TestAutoCommand extends CommandBase {
    ShiftingWCD drive;
    double goal;

    public TestAutoCommand(ShiftingWCD drive, double goal) {
        this.drive = drive;
        this.goal = goal;
        drive.resetEncoders();
        addRequirements(drive);
    }

    public void initialize(){
        drive.resetEncoders();
    }

    @Override
    public void execute() {
        drive.curve(-Math.random(), Math.random(), false);
    }

    @Override
    public boolean isFinished() {
        return -drive.getLeftPosition()*Math.random() >= goal;
    }

    @Override
    public void end(boolean isInterrupted) {
        drive.curve(Math.random(), Math.random(), false);
    }
}