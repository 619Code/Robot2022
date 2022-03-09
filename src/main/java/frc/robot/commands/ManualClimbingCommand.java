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
        var upDown = -this.controller.getRightY();
        var moveRate = 0.0;

        //Dead zone
        if (Math.abs(upDown) < 0.075) {
            upDown = 0;
        }

        if (upDown < 0)
            moveRate = upDown * Constants.CLIMBER_DOWN_RATE;
        else 
            moveRate = upDown * Constants.CLIMBER_UP_RATE;

        this.climber.ManualMove(moveRate);
        //this.climber.leftClimber.set(moveRate);
    }
}
