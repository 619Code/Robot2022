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

public class Climber extends SubsystemBase {
    public CANSparkMax leftClimber;
    public CANSparkMax rightClimber;
    MotorControllerGroup climberMotors;

    PIDController winchPID;
    double winchSetpoint;
    public RelativeEncoder leftClimbEncoder;
    public RelativeEncoder rightClimbEncoder;

    //Solenoid leftPiston;
    //Solenoid rightPiston;

    DoubleSolenoid pistons;

    double currentArmLength;

    public Climber() {
        //leftPiston = new Solenoid(PneumaticsModuleType.CTREPCM, Constants.LEFT_PISTON_SOLENOID);
        //rightPiston = new Solenoid(PneumaticsModuleType.CTREPCM, Constants.RIGHT_PISTON_SOLENOID);
        pistons = new DoubleSolenoid(Constants.PCM_CAN_ID, PneumaticsModuleType.CTREPCM,
        Constants.LEFT_PISTON_SOLENOID, Constants.RIGHT_PISTON_SOLENOID);

        leftClimber = new CANSparkMax(Constants.CLIMBER_LEFT_MOTOR, CANSparkMax.MotorType.kBrushless);
        rightClimber = new CANSparkMax(Constants.CLIMBER_RIGHT_MOTOR, CANSparkMax.MotorType.kBrushless);
        rightClimber.restoreFactoryDefaults();
        leftClimber.restoreFactoryDefaults();

        rightClimber.setIdleMode(IdleMode.kBrake);
        leftClimber.setIdleMode(IdleMode.kBrake);
        this.leftClimbEncoder = leftClimber.getEncoder();
        this.leftClimbEncoder.setPosition(0);
        this.rightClimbEncoder = rightClimber.getEncoder();
        this.rightClimbEncoder.setPosition(0);

        rightClimber.setInverted(true);

        climberMotors = new MotorControllerGroup(leftClimber, rightClimber);

        winchPID = new PIDController(Constants.CLIMBER_WINCH_P, Constants.CLIMBER_WINCH_I, Constants.CLIMBER_WINCH_D);
    }
    
    public void moveLeft(double power) {
        //System.out.println("Moving Left: " + power);
        leftClimber.set(power);
    }

    public void moveRight(double power) {
        //System.out.println("Moving Right: " + power);
        rightClimber.set(power);
    }

    public void setArmLength(double length){
        winchSetpoint = length / (Constants.CLIMB_WINCH_DIAMETER * Math.PI);
        currentArmLength = this.leftClimbEncoder.getPosition() * Constants.CLIMB_WINCH_DIAMETER * Math.PI;
        climberMotors.setVoltage(winchPID.calculate(this.leftClimbEncoder.getPosition(), winchSetpoint));
    }

    public double getArmLength(){
        return currentArmLength;
    }

    public void pistonsBack() {
        System.out.println("Pistons back");
        //leftPiston.set(true);
        //rightPiston.set(true);
        pistons.set(Value.kReverse);
    }

    public void pistonsForward() {
        System.out.println("Pistons forward");
        //leftPiston.set(false);
        //rightPiston.set(false);
        pistons.set(Value.kForward);
    }
}
