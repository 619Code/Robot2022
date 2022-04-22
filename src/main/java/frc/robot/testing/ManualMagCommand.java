package frc.robot.testing;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Magazine;

public class ManualMagCommand extends CommandBase {
    Magazine magazine;

    public ManualMagCommand(Magazine magazine) {
        this.magazine = magazine;
        addRequirements(magazine);
    }

    public void execute() {
        magazine.intakeBalls(-0.3);
    }

    public void end(boolean isInterrupted) {
        magazine.intakeBalls(0);
    }
}
