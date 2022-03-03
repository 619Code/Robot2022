package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Shooter.EDeviceType;

public class AngleFinderCommand extends CommandBase{
    Shooter shooter;
    private EDeviceType deviceType;
    boolean forward;
    public AngleFinderCommand(Shooter shooter, EDeviceType deviceType, boolean forward) {
        this.shooter = shooter;
        this.deviceType = deviceType;
        this.forward = forward;
        addRequirements(shooter);
    }

    public void initialize() {
        shooter.setAngle(deviceType, (forward ? 100 : -100) / Constants.HOOD_DEGREES_PER_REV);
    }

    public boolean isFinished() {
        return true;
    }

}