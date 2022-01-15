package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeMagazine;

public class IntakeCommand extends CommandBase {

    private XboxController controller;
    private IntakeMagazine intake;
    private boolean armDown;

    public IntakeCommand(boolean armDown, IntakeMagazine intake, XboxController stick) {
        this.intake = intake;
        this.controller = stick;
        this.armDown = armDown;

        addRequirements(intake);
    }

    @Override

    public void initialize() {}

    @Override

    public void execute() {
        if (controller.getLeftBumperPressed()) {
            intake.spIntake();
        }
        else if (controller.getRightBumperPressed()) {
            intake.raiseIntake();
        }
    }
    

}
