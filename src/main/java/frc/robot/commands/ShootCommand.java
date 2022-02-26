package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Constants;
import frc.robot.States;
import frc.robot.helpers.*;

public class ShootCommand extends CommandBase {

    private Shooter shooter;
    private Magazine magazine;
    BallPosition[] positions;

    public ShootCommand(Shooter shooter, Magazine magazine) {
        this.shooter = shooter;
        this.magazine = magazine;

        addRequirements(magazine, shooter);
    }

    @Override
    public void initialize() {}

    public void execute() {
        fireBalls(0.55, 60);
    }

    public void fireBalls(double velocity, double hoodAngle) {
        shooter.shoot(velocity);
        shooter.setHoodAngle(hoodAngle);

        boolean spedUp = Math.abs(shooter.getShooterSpeed() / Constants.SHOOTER_MAX_RPM - velocity) < 0.05; //velocity is within 5% of the goal
        boolean hoodSet = Math.abs(shooter.getHoodAngle() - hoodAngle) < 0.03; //hood position is within 3% of the goal
        if(spedUp && hoodSet) {
            magazine.loadShooter();
        } else {
            magazine.stopAll();
        }
    }

    public boolean isFinished(boolean isInterrupted) {
        return magazine.isEmpty();
    }

    protected void end() {
        shooter.stopAll();
        magazine.stopAll();
    }
}
