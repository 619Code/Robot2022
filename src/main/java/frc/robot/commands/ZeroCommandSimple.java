package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.States;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Shooter.EDeviceType;

public class ZeroCommandSimple extends CommandBase {
    private Timer endTimer;

    private boolean hoodDone;
    private boolean turretDone;
    private boolean turretBackedOff;
    private boolean ready;

    private Shooter shooter;
    public ZeroCommandSimple(Shooter shooter)
    {
        this.shooter = shooter;

        this.endTimer = new Timer();

        hoodDone = false;
        turretDone = false;
        turretBackedOff = false;

        this.addRequirements(shooter);
    }

    public void initialize() {
        shooter.setZeroPoint(EDeviceType.Hood);
        shooter.setZeroPoint(EDeviceType.Turret);
        endTimer.reset();
        hoodDone = false;
        turretDone = false;
        turretBackedOff = false;
        ready = false;
    }

    public void execute() {
        if(ready) {
            if(!hoodDone) {
                this.shooter.move(EDeviceType.Hood, -0.3);
            } else {
                shooter.move(EDeviceType.Hood, 0);
            }

            if (States.hasTurret)
            {
                if(!turretBackedOff) {
                    shooter.move(EDeviceType.Turret, -Constants.TURRET_ZERO_SPEED); //move away from hall effect sensor
                } else if(!turretDone) {
                    shooter.move(EDeviceType.Turret, Constants.TURRET_ZERO_SPEED); //move towards hall effect sensor
                } else {
                    shooter.move(EDeviceType.Turret, 0); //stop
                }

                if(!turretBackedOff && shooter.getTurretPosition() < -20.0) { //back off until it is 20 revolutions off
                    turretBackedOff = true;
                }
            }
        } else if(!States.hasTurret || shooter.turretNearRev(0.0)) { //wait until the turret is set to the zero point before starting
            ready = true;
        }
    }

    public void end(boolean isInterrupted){

        if (States.hasTurret)
        {
            while(!shooter.turretNearRev(0.0)) { //wait until the turret is set to the zero point before proceeding
                shooter.setZeroPoint(EDeviceType.Hood);
                shooter.setHoodAngle(Constants.BASE_HOOD_ANGLE);
                shooter.move(EDeviceType.Hood, 0);
                shooter.setZeroPoint(EDeviceType.Turret);
                shooter.move(EDeviceType.Turret, 0);
            }
        }
        else {
            if (hoodDone)
            {
                shooter.setZeroPoint(EDeviceType.Hood);
                shooter.setHoodAngle(Constants.BASE_HOOD_ANGLE);
                shooter.move(EDeviceType.Hood, 0);
            }
        }
    }

    // Checking when things are finished by checking the velocity because
    //  the stop switch never reports true :(
    public boolean isFinished(){
        endTimer.start();
        if(!this.shooter.atZeroPoint(EDeviceType.Hood)) {
            endTimer.reset();
        }
        hoodDone = endTimer.hasElapsed(1);
        turretDone = this.shooter.atZeroPoint(EDeviceType.Turret);
        return hoodDone && (!States.hasTurret || turretDone);
    }
}
