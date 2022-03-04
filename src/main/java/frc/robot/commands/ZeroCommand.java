package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

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

    private Timer endTimer;

    private Shooter shooter;
    public ZeroCommand(Shooter shooter, Shooter.EDeviceType deviceType)
    {
        this.zeroStage = EZeroStages.NotStarted;
        this.deviceType = deviceType;
        this.shooter = shooter;

        this.endTimer = new Timer();

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
        //System.out.println(this.deviceType.toString() + " Zero Stage: " + zeroStage);
        switch(zeroStage){
            case NotStarted:
                endTimer.start();
                zeroStage = EZeroStages.TowardsHighSpeed;
                this.shooter.setAngle(this.deviceType, this.shooter.getAngle(this.deviceType) - 10);
                break;
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
                if(Math.abs(shooter.getAngle(this.deviceType) + backOffAngle) < 1){
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
            case Zeroed:
                break;
        }
    }

    public void end(boolean isInterrupted){

    }

    public boolean isFinished(){
        return this.zeroStage == EZeroStages.Zeroed;
    }
}
