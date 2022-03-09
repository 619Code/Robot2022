package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Shooter;

public class ZeroCommandSimple extends CommandBase {

    private Shooter.EDeviceType deviceType;

    private Timer endTimer;

    private Shooter shooter;
    public ZeroCommandSimple(Shooter shooter, Shooter.EDeviceType deviceType)
    {
        this.deviceType = deviceType;
        this.shooter = shooter;

        this.endTimer = new Timer();

        this.addRequirements(shooter);
    }

    public void initialize()
    {
        endTimer.reset();
    }

    public void execute() {
        System.out.println("TRYING TO MOVE HOOD!!!");
        System.out.println("HoodSwitch:" + this.shooter.AtHoodZeroPoint());
        this.shooter.moveHood(-.1);
    }

    public void end(boolean isInterrupted){
        this.shooter.SetZeroPoint(deviceType);
        if(deviceType == Shooter.EDeviceType.Hood)
        {
            this.shooter.setHoodAngle(Constants.BASE_HOOD_ANGLE);
        }
        System.out.println("END!!!");
    }

    // Checking when things are finished by checking the velocity because
    //  the stop switch never reports true :(
    public boolean isFinished(){
        System.out.println("HoodSwitch:" + this.shooter.AtHoodZeroPoint());
        this.shooter.AtZeroPoint(Shooter.EDeviceType.Hood);
        endTimer.start();
        if(!this.shooter.atHoodZeroPointRPM()) {
            endTimer.reset();
        }
        return endTimer.hasElapsed(1);
    }
    
}
