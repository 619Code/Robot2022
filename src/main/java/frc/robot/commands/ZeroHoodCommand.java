package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class ZeroHoodCommand extends CommandBase {

    /* stages:
    0: not started
    1: towards high speed
    2: backing off
    3: towards low speed
    4: zeroed*/
    private boolean hoodZeroed = false;
    private boolean turretZeroed = false;
    private final double hoodDownRate = -.1;

    private Shooter shooter;
    public ZeroHoodCommand(Shooter shooter)
    {
        this.shooter = shooter;
        this.addRequirements(shooter);
    }
    public void execute() {
        if(!shooter.AtHoodZeroPoint())
            this.shooter.moveHood(hoodDownRate);
        else {
            this.shooter.SetHoodZeroPoint();
            this.hoodZeroed = true;
        }
    }

    public void end(){

    }

    public boolean isFinished(boolean isInterrupted){
        return hoodZeroed && turretZeroed;
    }
    
}
