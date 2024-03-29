package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Climber;

public class ManualClimbingCommand extends CommandBase {
    
    Climber climber;
    private XboxController controller;

    public ManualClimbingCommand(Climber climber, XboxController controller)
    {
        this.controller = controller;
        this.climber = climber;
        this.addRequirements(climber);
    }

    public void execute()
    {
        double upDownLeft = this.controller.getLeftY();
        double upDownRight = this.controller.getRightY();
        double moveRateLeft = 0.0;
        double moveRateRight = 0.0;

        //System.out.println("Left rotations: " + climber.leftClimbEncoder.getPosition());
        // System.out.println("Right rotations: " + climber.rightClimbEncoder.getPosition());

        //Dead zone
        if (Math.abs(upDownLeft) > 0.075) {
            if (upDownLeft < 0) {
                moveRateLeft = upDownLeft * 0.3;
            } else {
                moveRateLeft = upDownLeft * 0.3;
            }
            this.climber.moveLeft(moveRateLeft);
        } else {
            this.climber.moveLeft(0.0);
        }

        if (Math.abs(upDownRight) > 0.075) {
            if (upDownRight < 0) {
                moveRateRight = upDownRight * 0.3;
            } else {
                moveRateRight = upDownRight * 0.3;
            }
            this.climber.moveRight(moveRateRight);
        } else {
            this.climber.moveRight(0.0);
        }
    }
}
