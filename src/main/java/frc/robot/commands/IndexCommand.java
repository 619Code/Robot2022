package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
//import edu.wpi.first.wpilibj2.XboxController;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.subsystems.IntakeMagazine;;

public class IndexCommand extends CommandBase {
private IntakeMagazine Intake;
private XboxController controller;

    
public IndexCommand(){
    this.Intake = Intake;
    this.controller = controller;

    addRequirements(Intake);

}

public void execute(){
    
}






}
