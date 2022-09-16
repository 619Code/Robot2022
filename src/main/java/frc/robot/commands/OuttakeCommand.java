package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Magazine;

public class OuttakeCommand extends CommandBase{
    Magazine magazine;
    public OuttakeCommand(Magazine magazine){
        addRequirements(magazine);
        this.magazine = magazine;
    }

    public void execute(){
        magazine.outtakeBalls();
    }

    public void end(boolean interrupted){
        magazine.stopAll();
    }
}