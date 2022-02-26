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
    private int hoodZeroStage;
    private final double hoodBackOffAngle = 5;

    private Shooter shooter;
    public ZeroHoodCommand(Shooter shooter)
    {
        this.shooter = shooter;
        this.addRequirements(shooter);
    }
    public void initialize()
    {
        hoodZeroStage = 0;
    }
    public void execute() {
        System.out.println("Hood Zero Stage: " + hoodZeroStage);
        switch(hoodZeroStage){
            case 0:
                hoodZeroStage = 1;
                // no break here so we immediately go to the next case (fall through)
            case 1:
                if(!shooter.AtHoodZeroPoint()){
                    this.shooter.setHoodAngle(this.shooter.getHoodAngle() - 10);
                } else {
                    this.shooter.SetHoodZeroPoint();
                    this.shooter.setHoodAngle(0);
                    this.hoodZeroStage = 2;
                }
                break;
            case 2:
                if(Math.abs(shooter.getHoodAngle() - hoodBackOffAngle) < 1){
                    this.hoodZeroStage = 3;
                } else {
                    this.shooter.setHoodAngle(hoodBackOffAngle);
                }
                break;
            case 3:
                if(!shooter.AtHoodZeroPoint()){
                    this.shooter.setHoodAngle(this.shooter.getHoodAngle() - 1);
                } else {
                    this.shooter.SetHoodZeroPoint();
                    this.shooter.setHoodAngle(0);
                    this.hoodZeroStage = 4;
                }
                break;
        }
        
        
    }

    public void end(boolean isInterrupted){

    }

    public boolean isFinished(){
        return hoodZeroStage == 4;
    }
    
}
