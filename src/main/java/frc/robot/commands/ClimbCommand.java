package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Climber;

public class ClimbCommand extends CommandBase {
    Climber climber;
    private XboxController controller;
    double revLimit;

    public ClimbCommand(Climber climber, XboxController controller)
    {
        this.controller = controller;
        this.climber = climber;
        revLimit = 115; //change this
        this.addRequirements(climber);
    }

    public void execute() {
        System.out.println(climber.leftClimbEncoder.getPosition());

        double upDown = -this.controller.getRightY();
        double moveRate = 0.0;

        //Dead zone
        if (Math.abs(upDown) < 0.075) {
            upDown = 0;
        }

        if (upDown < 0) {
            moveRate = upDown * Constants.CLIMBER_DOWN_RATE;
            moveRate = climber.leftClimbEncoder.getPosition() < -0.1 ? 0 : moveRate;
        } else {
            moveRate = upDown * Constants.CLIMBER_UP_RATE;
            moveRate = climber.leftClimbEncoder.getPosition() >= revLimit ? 0 : moveRate;
        }

        this.climber.ManualMove(moveRate);
    }
}
