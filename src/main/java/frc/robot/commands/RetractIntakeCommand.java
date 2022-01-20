package frc.robot.commands;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeMagazine;

public class RetractIntakeCommand extends CommandBase{

    private IntakeMagazine intake;

    public RetractIntakeCommand(IntakeMagazine intake) {
        this.intake = intake;
        addRequirements(intake);
    }

    @Override
    public void execute() {
        if (intake.lower.get()) {
            intake.lower.set(false);
        } else {
            //do nothing
        }
    }

}