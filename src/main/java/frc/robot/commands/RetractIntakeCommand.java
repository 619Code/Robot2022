package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class RetractIntakeCommand extends CommandBase{

    private Intake intake;

    public RetractIntakeCommand(Intake intake) {
        this.intake = intake;
        addRequirements(intake);
    }

    @Override
    public void execute() {
        //System.out.println("Raising intake! Intake is " + (intake.isLowered() ? "low" : "high"));
        intake.raiseIntake();
    }
}