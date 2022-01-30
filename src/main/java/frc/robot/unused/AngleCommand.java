package frc.robot.unused;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;

public class AngleCommand extends CommandBase {
    double angle = Constants.LIMELIGHT_ANGLE;
    Angler angler;

    public AngleCommand(Angler angler) {
        this.angler = angler;
        addRequirements(angler);
    }

    public void execute() {
        angler.setAngle(angle);
    }

    public void end(boolean isInterrupted) {
        while(angler.getAngle() > 0.01) {
            angler.angleMotor.set(-0.2);
        }
    }
}
