package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.States;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Magazine;

public class IntakeCommand extends CommandBase {

    //private XboxController controller;
    private Intake intake;
    private Magazine magazine;
    //private boolean armDown;

    public IntakeCommand(Intake intake, Magazine magazine) {
        this.intake = intake;
        this.magazine = magazine;

        addRequirements(intake, magazine);
    }

    @Override
    public void initialize() {
        //intake.lowerIntake();
    }

    @Override
    public void execute() {
        /*if(!States.isMagFull){
            intake.lowerIntake();
            intake.spinIntake(0.6);
            States.isMagFull = magazine.isFull();
        } else {
            intake.raiseIntake();
            intake.spinIntake(0);
            magazine.intakeBalls();
        }*/
        //System.out.println(magazine.verticalPosition.hasBall());
        //System.out.println("Lowering intake! Intake is " + (intake.isLowered() ? "low" : "high"));
        if(!magazine.verticalPosition.hasBall()) {
            if (!intake.isLowered())
                intake.lowerIntake();
            intake.spinIntake(0.6);
            magazine.intakeBalls();
        } else {
            if (intake.isLowered())
                intake.raiseIntake();
                
            intake.spinIntake(0);
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
