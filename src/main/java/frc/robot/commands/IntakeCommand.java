package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.States;
import frc.robot.subsystems.IntakeHopper;
import frc.robot.subsystems.VerticalMagazine;

public class IntakeCommand extends CommandBase {

    //private XboxController controller;
    private IntakeHopper intake;
    private VerticalMagazine verticalMag;
    //private boolean armDown;

    public IntakeCommand(IntakeHopper intake, VerticalMagazine verticalMag) {
        this.intake = intake;
        this.verticalMag = verticalMag;
        //this.controller = stick;
        //this.armDown = armDown;

        addRequirements(intake, verticalMag);
    }

    @Override

    public void initialize() {}

    @Override

    public void execute() {

        if(States.IsMagFull == false){
            intake.lowerIntake();
            intake.spIntake(0.6);
            intake.hopperStuff();
            States.IsMagFull = verticalMag.intake();

        } else {

            endIntaking();

        }
     
    }

    public void endIntaking(){

        intake.raiseIntake();
        intake.spIntake(0);
        intake.hopperStuff();
        verticalMag.intake();

    }

    @Override
    public void end(boolean isInterrupted) {
    
        endIntaking();

    }
    

}
