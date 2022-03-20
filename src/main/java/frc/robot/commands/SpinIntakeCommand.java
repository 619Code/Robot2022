package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class SpinIntakeCommand extends CommandBase {
    Intake intake;

    public SpinIntakeCommand(Intake intake) {
        this.intake = intake;
        addRequirements(intake);
    }

    public void execute() {
        intake.spinIntake(0.6);
        if (!intake.isLowered()) {
            intake.lowerIntake();
        }
    }

    public void end(boolean isInterrupted) {
        intake.spinIntake(0);
    }
}
