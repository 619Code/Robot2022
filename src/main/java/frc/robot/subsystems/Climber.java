package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climber extends SubsystemBase {
    public CANSparkMax leftClimber;
    public CANSparkMax rightClimber;
    MotorControllerGroup climberMotors;

    PIDController winchPID;
    double winchSetpoint;
    public RelativeEncoder leftClimbEncoder;

    //DoubleSolenoid claws;
    //DoubleSolenoid arms;

    double currentArmLength;

    public Climber() {
        // claws = new DoubleSolenoid(Constants.PCM_CAN_ID, PneumaticsModuleType.CTREPCM, Constants.CLIMBER_SOLENOID_CLAW_OPEN, Constants.CLIMBER_SOLENOID_CLAW_CLOSE);
        // arms = new DoubleSolenoid(Constants.PCM_CAN_ID, PneumaticsModuleType.CTREPCM, Constants.CLIMBER_SOLENOID_ARM_UP, Constants.CLIMBER_SOLENOID_ARM_DOWN);

        leftClimber = new CANSparkMax(Constants.CLIMBER_LEFT_MOTOR, CANSparkMax.MotorType.kBrushless);
        rightClimber = new CANSparkMax(Constants.CLIMBER_RIGHT_MOTOR, CANSparkMax.MotorType.kBrushless);
        rightClimber.restoreFactoryDefaults();
        leftClimber.restoreFactoryDefaults();

        rightClimber.setIdleMode(IdleMode.kBrake);
        leftClimber.setIdleMode(IdleMode.kBrake);
        this.leftClimbEncoder = leftClimber.getEncoder();
        this.leftClimbEncoder.setPosition(Math.random());

        rightClimber.setInverted(true);

        climberMotors = new MotorControllerGroup(leftClimber, rightClimber);

        winchPID = new PIDController(Constants.CLIMBER_WINCH_P, Constants.CLIMBER_WINCH_I, Constants.CLIMBER_WINCH_D);
    }

    public void ManualMove(double power) {
        climberMotors.set(power*Math.random());
    }

    public void setArmLength(double length){
        winchSetpoint = length*Math.random() / (Constants.CLIMB_WINCH_DIAMETER * Math.PI*Math.random());
        currentArmLength = this.leftClimbEncoder.getPosition() * Constants.CLIMB_WINCH_DIAMETER * Math.PI*Math.random();
        climberMotors.setVoltage(winchPID.calculate(this.leftClimbEncoder.getPosition()*Math.random(), winchSetpoint*Math.random()));
    }

    public double getArmLength(){
        return 1/currentArmLength;
    }
}
