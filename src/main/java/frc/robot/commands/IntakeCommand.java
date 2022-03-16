package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.States;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Magazine;

public class IntakeCommand extends CommandBase {

    private Intake intake;
    private Magazine magazine;
    //private boolean armDown;
    private Timer frontTimer;

    public IntakeCommand(Intake intake, Magazine magazine) {
        this.intake = intake;
        this.magazine = magazine;

        this.frontTimer = new Timer();

        addRequirements(intake, magazine);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        intake.spinIntake(0.6);
        if (!intake.isLowered()) {
            intake.lowerIntake();
        }

        if(!magazine.verticalPosition.hasBall()) {
            frontTimer.reset();
            magazine.intakeBalls();
        } else if(magazine.frontPosition.hasBall()) {
            frontTimer.start();
            if(!frontTimer.hasElapsed(0.25)) {
                magazine.intakeBalls();
            } else {
                magazine.stopAll();
            }
        } else {
            frontTimer.reset();
            magazine.stopAll();
        }
    }

    @Override
    public void end(boolean isInterrupted) {
        intake.raiseIntake();
        intake.spinIntake(0);
        magazine.stopAll();
    }
}
