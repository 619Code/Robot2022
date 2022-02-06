package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeHopper;

public class RetractIntakeCommand extends CommandBase{

    private IntakeHopper intake;

    public RetractIntakeCommand(IntakeHopper intake) {
        this.intake = intake;
        addRequirements(intake);
    }

    @Override
    public void execute() {
        if (intake.lower.get()) {
            intake.lower.set(false);
        }
    }

}