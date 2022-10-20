package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.States;

public class Climber extends SubsystemBase {
    public CANSparkMax leftClimber;
    public CANSparkMax rightClimber;

    public RelativeEncoder leftClimbEncoder;
    public RelativeEncoder rightClimbEncoder;

    public DoubleSolenoid pistons;

    public Climber() {
        pistons = new DoubleSolenoid(Constants.PCM_CAN_ID, PneumaticsModuleType.CTREPCM,
        Constants.LEFT_PISTON_SOLENOID, Constants.RIGHT_PISTON_SOLENOID);

        leftClimber = new CANSparkMax(Constants.CLIMBER_LEFT_MOTOR, CANSparkMax.MotorType.kBrushless);
        rightClimber = new CANSparkMax(Constants.CLIMBER_RIGHT_MOTOR, CANSparkMax.MotorType.kBrushless);

        rightClimber.restoreFactoryDefaults();
        leftClimber.restoreFactoryDefaults();

        rightClimber.setIdleMode(IdleMode.kBrake);
        leftClimber.setIdleMode(IdleMode.kBrake);

        leftClimbEncoder = leftClimber.getEncoder();
        leftClimbEncoder.setPosition(0);

        rightClimbEncoder = rightClimber.getEncoder();
        rightClimbEncoder.setPosition(0);
        rightClimber.setInverted(true);

        States.armsUp = false;
    }
    
    public void moveLeft(double power) {
        //System.out.println("Moving Left: " + power);
        leftClimber.set(power);
    }

    public void moveRight(double power) {
        //System.out.println("Moving Right: " + power);
        rightClimber.set(power);
    }

    public void togglePistons() {
        if(States.armsUp) {
            pistonsDown();
        } else {
            pistonsUp();
        }
    }

    public void pistonsUp() {
        if(States.centered) {
            //System.out.println("Pistons up");
            pistons.set(Value.kForward);
            States.armsUp = true;
        }
    }

    public void pistonsDown() {
        if(States.centered) {
            //System.out.println("Pistons down");
            pistons.set(Value.kReverse);
            States.armsUp = false;
        }
    }

    public double getLeftClimbLimit() {
        if(States.armsUp) {
            return Constants.LEFT_REV_LIMIT_UP;
        } else {
            return Constants.LEFT_REV_LIMIT_TRAV;
        }
    }

    public double getRightClimbLimit() {
        if(States.armsUp) {
            return Constants.RIGHT_REV_LIMIT_UP;
        } else {
            return Constants.RIGHT_REV_LIMIT_TRAV;
        }
    }
}
