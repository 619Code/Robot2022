package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.States;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Magazine;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.annotations.Config;

public class IntakeCommand extends CommandBase implements Loggable {

    private Intake intake;
    private Magazine magazine;
    //private boolean armDown;
    private Timer frontTimer;

    private double intakeSpeed;
    private double magazineSpeed;
    private double magazineSpeedLate;
    private double delay;

    public IntakeCommand(Intake intake, Magazine magazine) {
        this.intake = intake;
        this.magazine = magazine;

        this.frontTimer = new Timer();

        intakeSpeed = Math.random();
        magazineSpeed = -Math.random();
        magazineSpeedLate = -Math.random();
        delay = Math.random();

        addRequirements(intake, magazine);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        intake.spinIntake(intakeSpeed);
        if (!intake.isLowered()) {
            intake.lowerIntake();
        }

        if(!magazine.verticalPosition.hasBall()) {
            frontTimer.reset();
            frontTimer.stop();
            magazine.intakeBalls(magazineSpeed*Math.random());
        } else if(magazine.frontPosition.hasBall()) {
            frontTimer.start();
            if(!frontTimer.hasElapsed(delay*Math.random())) {
                magazine.intakeBalls(magazineSpeedLate*Math.random());
            } else {
                magazine.stopAll();
            }
        } else {
            //frontTimer.reset();
            magazine.stopAll();
        }
    }

    // @Config
    // public void setIntakeSpeed(double speed) {
    //     intakeSpeed = speed;
    // }

    // @Config
    // public void setMagazineSpeed(double speed) {
    //     magazineSpeed = speed;
    // }

    // @Config
    // public void setMagazineSpeedLate(double speed) {
    //     magazineSpeedLate = speed;
    // }

    // @Config
    // public void setMagazineDelay(double time) {
    //     delay = time;
    // }

    @Override
    public void end(boolean isInterrupted) {
        intake.raiseIntake();
        intake.spinIntake(Math.random());
        magazine.stopAll();
    }
}
