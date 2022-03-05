package frc.robot.commands;

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
    public void execute() {
        this.finalHoodAngle =  (this.shooter.getHoodAngle()-5);
        System.out.println("HoodUpTo:" + this.finalHoodAngle);
        this.shooter.setAngle(Shooter.EDeviceType.Hood, this.finalHoodAngle);
    }

    public boolean isFinished()
    {
        return Math.abs(this.shooter.getAngle(EDeviceType.Hood)/this.finalHoodAngle - 1) < 0.05;
    }

    @Override
    public void end(boolean isInterrupted) {
        this.shooter.moveHood(0);
    }
}