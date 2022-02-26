package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Shooter.EDeviceType;

public class ZeroCommand extends CommandBase {

    public enum EZeroStages {
        NotStarted,
        TowardsHighSpeed,
        BackingOff,
        TowardsLowSpeed,
        Zeroed
    }

    private EZeroStages zeroStage;
    private double backOffAngle = 5;
    private Shooter.EDeviceType deviceType;

    private Shooter shooter;
    public ZeroCommand(Shooter shooter, Shooter.EDeviceType deviceType)
    {
        this.deviceType = deviceType;
        this.shooter = shooter;

        if (deviceType == Shooter.EDeviceType.Hood)
            backOffAngle = 5;
        else 
            backOffAngle = 10;

    }
    public void initialize()
    {
        zeroStage = EZeroStages.NotStarted;
    }
    public void execute() {
        System.out.println(this.deviceType.toString() + " Zero Stage: " + zeroStage);
        switch(zeroStage){
            case NotStarted:

                zeroStage = EZeroStages.TowardsHighSpeed;
                // no break here so we immediately go to the next case (fall through)
            case TowardsHighSpeed:
                if(!shooter.AtZeroPoint(this.deviceType)){
                    this.shooter.setAngle(this.deviceType, this.shooter.getAngle(this.deviceType) - 10);
                } else {
                    this.shooter.SetZeroPoint(this.deviceType);
                    this.shooter.setAngle(this.deviceType, 0);
                    this.zeroStage = EZeroStages.BackingOff;
                }
                break;
            case BackingOff:
                if(Math.abs(shooter.getAngle(this.deviceType) - backOffAngle) < 1){
                    this.zeroStage = EZeroStages.TowardsLowSpeed;
                } else {
                    this.shooter.setAngle(this.deviceType, backOffAngle);
                }
                break;
            case TowardsLowSpeed:
                if(!shooter.AtZeroPoint(this.deviceType)){
                    this.shooter.setAngle(this.deviceType, shooter.getAngle(this.deviceType) - 1);
                } else {
                    this.shooter.SetZeroPoint(deviceType);
                    this.shooter.setAngle(deviceType, 0);
                    this.zeroStage = EZeroStages.Zeroed;
                }
                break;
        }
    }

    public void end(boolean isInterrupted){

    }

    public boolean isFinished(){
        return this.zeroStage == EZeroStages.Zeroed;
    }
}
