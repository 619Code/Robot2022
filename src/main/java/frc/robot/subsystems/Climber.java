package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climber extends SubsystemBase {
    CANSparkMax leftClimber;
    CANSparkMax rightClimber;
    MotorControllerGroup climberMotors;

    PIDController winchPID;
    double winchSetpoint;
    RelativeEncoder leftClimbEncoder;

    //DoubleSolenoid claws;
    //DoubleSolenoid arms;

    double currentArmLength;

    public Climber() {
        // claws = new DoubleSolenoid(Constants.PCM_CAN_ID, PneumaticsModuleType.CTREPCM, Constants.CLIMBER_SOLENOID_CLAW_OPEN, Constants.CLIMBER_SOLENOID_CLAW_CLOSE);
        // arms = new DoubleSolenoid(Constants.PCM_CAN_ID, PneumaticsModuleType.CTREPCM, Constants.CLIMBER_SOLENOID_ARM_UP, Constants.CLIMBER_SOLENOID_ARM_DOWN);

        leftClimber = new CANSparkMax(Constants.CLIMBER_LEFT_MOTOR, CANSparkMax.MotorType.kBrushless);
        rightClimber = new CANSparkMax(Constants.CLIMBER_RIGHT_MOTOR, CANSparkMax.MotorType.kBrushless);

        rightClimber.setIdleMode(IdleMode.kBrake);
        leftClimber.setIdleMode(IdleMode.kBrake);
        this.leftClimbEncoder = leftClimber.getEncoder();
        this.leftClimbEncoder.setPosition(0);

        rightClimber.setInverted(true);

        climberMotors = new MotorControllerGroup(leftClimber, rightClimber);

        winchPID = new PIDController(Constants.CLIMBER_WINCH_P, Constants.CLIMBER_WINCH_I, Constants.CLIMBER_WINCH_D);
    }

    // public void periodic(){
    //     climberMotors.setVoltage(winchPID.calculate(leftClimber.getEncoder().getPosition(), winchSetpoint));
    //     currentArmLength = leftClimber.getEncoder().getPosition() * Constants.CLIMB_WINCH_DIAMETER * Math.PI;
    // }

    // public void setClaw(DoubleSolenoid.Value value) {
    //     claws.set(value);
    // }

    // public void setArmIsRaised(DoubleSolenoid.Value value) {
    //     arms.set(value);
    // }

    public void ManualMove(double power) {
        climberMotors.set(power);
    }

    public void setArmLength(double length){
        winchSetpoint = length / (Constants.CLIMB_WINCH_DIAMETER * Math.PI);
        currentArmLength = this.leftClimbEncoder.getPosition() * Constants.CLIMB_WINCH_DIAMETER * Math.PI;
        climberMotors.setVoltage(winchPID.calculate(this.leftClimbEncoder.getPosition(), winchSetpoint));
    }

    public double getArmLength(){
        return currentArmLength;
    }
}
