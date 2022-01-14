package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeMagazine;

public class IntakeCommand extends CommandBase {

    private XboxController controller;
    private IntakeMagazine intake;

    public IntakeCommand(boolean armDown, IntakeMagazine intake, XboxController stick) {
        this.intake = intake;
        this.controller = stick;
    }

    

}
