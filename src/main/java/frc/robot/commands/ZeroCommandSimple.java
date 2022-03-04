package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class ZeroCommandSimple extends CommandBase {

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
    public ZeroCommandSimple(Shooter shooter, Shooter.EDeviceType deviceType)
    {
        this.zeroStage = EZeroStages.NotStarted;
        this.deviceType = deviceType;
        this.shooter = shooter;

        this.endTimer = new Timer();

        if (deviceType == Shooter.EDeviceType.Hood)
            backOffAngle = 5;
        else 
            backOffAngle = 10;

        this.addRequirements(shooter);

    }
    public void initialize()
    {
        endTimer.reset();
    }
    public void execute() {
        var newAngle = this.shooter.getAngle(this.deviceType) - 10;
        this.shooter.setAngle(this.deviceType, newAngle);
        //System.out.println("HoodZero:" + newAngle);
    }

    public void end(boolean isInterrupted){
        this.shooter.SetZeroPoint(deviceType);
        //System.out.println("END!!!");
        this.shooter.setAngle(this.deviceType, 0);
    }

    public boolean isFinished(){
        endTimer.start();
        if(!this.shooter.atHoodZeroPointRPM()) {
            endTimer.reset();
        }
        return endTimer.hasElapsed(1);
    }
}
