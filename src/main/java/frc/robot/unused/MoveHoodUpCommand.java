package frc.robot.unused;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Shooter.EDeviceType;

// Testing command for testing the hood angle functionality
public class MoveHoodUpCommand extends CommandBase{

    private Shooter shooter;
    private double finalHoodAngle;

    public MoveHoodUpCommand(Shooter shooter) {
        this.shooter = shooter;
        addRequirements(shooter);
    }

    @Override
    public void initialize() {
        this.finalHoodAngle =  (this.shooter.getHoodAngle()-5);
        //System.out.println("HoodUpTo:" + this.finalHoodAngle);
        this.shooter.setHoodAngle(this.finalHoodAngle);
    }

    public boolean isFinished()
    {
        return true;
    }

    @Override
    public void end(boolean isInterrupted) {
        this.shooter.move(Shooter.EDeviceType.Hood, 0);
    }
}