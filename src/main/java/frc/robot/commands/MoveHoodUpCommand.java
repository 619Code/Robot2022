package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

// Testing command for testing the hood angle functionality
public class MoveHoodUpCommand extends CommandBase{

    private Shooter shooter;

    public MoveHoodUpCommand(Shooter shooter) {
        this.shooter = shooter;
        addRequirements(shooter);
    }

    @Override
    public void execute() {
        System.out.println("HoodUpTo:" + (this.shooter.getHoodAngle()-5));
        this.shooter.setAngle(Shooter.EDeviceType.Hood, this.shooter.getHoodAngle()-5);
    }

    @Override
    public void end(boolean isInterrupted) {
        this.shooter.moveHood(0);
    }
}