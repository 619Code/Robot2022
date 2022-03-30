package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Shooter.EDeviceType;

public class ZeroCommandSimple extends CommandBase {

    private Shooter.EDeviceType deviceType;

    private Timer endTimer;

    private boolean hoodDone;
    private boolean turretDone;

    private Shooter shooter;
    public ZeroCommandSimple(Shooter shooter)
    {
        this.deviceType = deviceType;
        this.shooter = shooter;

        this.endTimer = new Timer();

        hoodDone = false;
        turretDone = false;

        this.addRequirements(shooter);
    }

    public void initialize()
    {
        endTimer.reset();
    }

    public void execute() {
        if(!hoodDone) {
            this.shooter.move(EDeviceType.Hood, -0.3);
        } else {
            shooter.move(EDeviceType.Hood, 0);
        }

        if(!turretDone) {
            this.shooter.move(EDeviceType.Turret, -0.8);
        } else {
            shooter.move(EDeviceType.Turret, 0);
        }
    }

    public void end(boolean isInterrupted){
        shooter.setZeroPoint(deviceType);
        shooter.setHoodAngle(Constants.BASE_HOOD_ANGLE);
        shooter.move(EDeviceType.Hood, 0);
        shooter.move(EDeviceType.Turret, 0);
    }

    // Checking when things are finished by checking the velocity because
    //  the stop switch never reports true :(
    public boolean isFinished(){
        if(deviceType == Shooter.EDeviceType.Hood) {
            endTimer.start();
            if(!this.shooter.atZeroPoint(deviceType)) {
                endTimer.reset();
            }
            hoodDone = endTimer.hasElapsed(1);
        } else {
            turretDone = this.shooter.atZeroPoint(deviceType);
        }
        return hoodDone && turretDone;
    }
}
