package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class MoveHoodUpCommand extends CommandBase{

    private Shooter shooter;

    public MoveHoodUpCommand(Shooter shooter) {
        this.shooter = shooter;
        addRequirements(shooter);
    }

    @Override
    public void execute() {
        var newAngle = this.shooter.getAngle(Shooter.EDeviceType.Hood) + 10;
        this.shooter.setAngle(Shooter.EDeviceType.Hood, newAngle);
        //System.out.println("HoodUp:" + newAngle);
        //System.out.println("HoodAngle:" + this.shooter.getAngle(Shooter.EDeviceType.Hood));
        //this.shooter.moveHood(0.1);
    }

    @Override
    public void end(boolean isInterrupted) {
        this.shooter.setAngle(Shooter.EDeviceType.Hood, shooter.getAngle(Shooter.EDeviceType.Hood));
    }
}