package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.States;
import frc.robot.helpers.BallPosition;
import frc.robot.subsystems.*;

public class VerticalMagazine extends SubsystemBase {

    private CANSparkMax verticalMotor;
    private CANSparkMax transitionMotor;
    private BallPosition highPosition;
    private BallPosition lowPosition;
    IntakeHopper ihSubsystem;

    public VerticalMagazine() {

        this.init();
        this.ihSubsystem = new IntakeHopper();

    }

    private void init() {

        highPosition = new BallPosition(Constants.HIGH_MAG_POSITION);
        lowPosition = new BallPosition(Constants.LOW_MAG_POSITION);
        verticalMotor = new CANSparkMax(Constants.VERTICAL_MOTOR, MotorType.kBrushless);
        transitionMotor = new CANSparkMax(Constants.TRANSITION_MOTOR, MotorType.kBrushless);

    }

    public boolean intake() {

        if (this.highPosition.hasBall()) {

            verticalMotor.set(0);

        } else {

            verticalMotor.set(.3);
            transitionMotor.set(.3);

        }

        if (this.lowPosition.hasBall() && this.highPosition.hasBall()) {

            transitionMotor.set(0);
            
            return true;

        }

        return false;

    }

    public void discharge() {

        verticalMotor.set(.3);
        transitionMotor.set(.3);

    }

    public boolean isEmpty(){

        if(!this.highPosition.hasBall() && !this.lowPosition.hasBall()){

            return true;

        } else {

            return false;

        }


    }

    public void end(){

        verticalMotor.set(0);
        transitionMotor.set(0);
        
    }

}
