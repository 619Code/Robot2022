package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeMagazine;

public class IntakeCommand extends CommandBase {

    //private XboxController controller;
    private IntakeMagazine intake;
    //private boolean armDown;

    public IntakeCommand(IntakeMagazine intake) {
        this.intake = intake;
        //this.controller = stick;
        //this.armDown = armDown;

        addRequirements(intake);
    }

    @Override

    public void initialize() {}

    @Override

    public void execute() {
        intake.lowerIntake();
        intake.spIntake(0.6);
    }

    @Override
    public void end(boolean isInterrupted) {
        intake.spIntake(0.0);
    }
    

}
