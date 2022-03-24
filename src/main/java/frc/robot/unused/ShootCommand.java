package frc.robot.unused;

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
        fireBalls(100, 60);
    }

    public void fireBalls(double rpm, double hoodAngle) {
        shooter.setShooterSpeedByRPM(rpm);
        //shooter.setHoodAngle(hoodAngle);

        boolean spedUp = Math.abs(shooter.getShooterRPM() / rpm) < 0.05; //velocity is within 5% of the goal
        //System.out.println(shooter.getShooterRPM());
        //boolean hoodSet = Math.abs(shooter.getHoodAngle() - hoodAngle) < 0.03; //hood position is within 3% of the goal
        boolean hoodSet = true;
        if(spedUp && hoodSet) {
            magazine.intakeBalls(-0.3);
        } else {
            magazine.stopAll();
        }
    }

    public boolean isFinished() {
        return magazine.isEmpty();
    }

    public void end(boolean isInterrupted) {
        shooter.stopAll();
        magazine.stopAll();
    }
}
