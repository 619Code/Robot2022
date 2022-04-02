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
    }

    public void execute() {
        if(!hoodDone) {
            this.shooter.move(EDeviceType.Hood, -0.3);
        } else {
            shooter.move(EDeviceType.Hood, 0);
        }

        if(!turretBackedOff) {
            shooter.setAngle(EDeviceType.Turret, 15.0);
        } else if(!turretDone) {
            shooter.move(EDeviceType.Turret, -0.25); //-0.8
        } else {
            shooter.move(EDeviceType.Turret, 0);
        }

        if(!turretBackedOff && shooter.getTurretPosition() > 14.0) {
            turretBackedOff = true;
        }

        System.out.println("Backed off: " + turretBackedOff);
    }

    public void end(boolean isInterrupted){
        States.zeroed = true;
        shooter.setZeroPoint(EDeviceType.Hood);
        shooter.setZeroPoint(EDeviceType.Turret);
        shooter.setHoodAngle(Constants.BASE_HOOD_ANGLE);
        shooter.move(EDeviceType.Hood, 0);
        shooter.move(EDeviceType.Turret, 0);
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
        return hoodDone && turretDone;
    }
}
