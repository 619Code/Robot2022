package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.States;
import frc.robot.subsystems.Climber;

public class ClimbCommand extends CommandBase {
    Climber climber;
    private XboxController controller;

    public ClimbCommand(Climber climber, XboxController controller)
    {
        this.controller = controller;
        this.climber = climber;
        this.addRequirements(climber);
    }

    public void execute() {
        //System.out.println("Left rotations: " + (-climber.leftClimbEncoder.getPosition()));
        //System.out.println("Right rotations: " + (-climber.rightClimbEncoder.getPosition()));

        double upDown = this.controller.getRightY();
        double leftMoveRate = 0.0;
        double rightMoveRate = 0.0;

        //Dead zone
        if (Math.abs(upDown) > 0.075) {
            if (upDown > 0) {
                leftMoveRate = upDown * Constants.CLIMBER_DOWN_RATE;
                rightMoveRate = upDown * Constants.CLIMBER_DOWN_RATE * (climber.getRightClimbLimit() / climber.getLeftClimbLimit());
                leftMoveRate = -climber.leftClimbEncoder.getPosition() < -15 ? 0 : leftMoveRate;
                rightMoveRate = -climber.rightClimbEncoder.getPosition() < -15 ? 0 : rightMoveRate;
            } else {
                leftMoveRate = upDown * Constants.CLIMBER_UP_RATE;
                rightMoveRate = upDown * Constants.CLIMBER_UP_RATE * (climber.getRightClimbLimit() / climber.getLeftClimbLimit());
                leftMoveRate = -climber.leftClimbEncoder.getPosition() >= climber.getLeftClimbLimit() ? 0 : leftMoveRate;
                rightMoveRate = -climber.rightClimbEncoder.getPosition() >= climber.getRightClimbLimit() ? 0 : rightMoveRate;
            }
    
            this.climber.moveLeft(leftMoveRate);
            this.climber.moveRight(rightMoveRate);
        } else {
            this.climber.moveLeft(0.0);
            this.climber.moveRight(0.0);
        }
    }
}
