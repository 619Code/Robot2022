package frc.robot.subsystems;

import frc.robot.Constants;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeHopper extends SubsystemBase {

    private CANSparkMax intake;
    private CANSparkMax hopper;
    public Solenoid lower;

    public IntakeHopper() {
        // Magazine can only consist of 2 balls held at once, if at all.
        intake = new CANSparkMax(Constants.LOADING_MOTOR, MotorType.kBrushless);
        hopper = new CANSparkMax(Constants.HOPPER_LEFT_MOTOR, MotorType.kBrushless);

        //Wrist solenoid used to raise and lower the intake 
        lower = new Solenoid(Constants.INTAKE_MODULE_TYPE, Constants.INTAKE_SOLENOID);        
    }
    

    public void spIntake(double percent) {
        intake.set(percent);


    }

    public boolean isLowered(){
    
        return lower.get();   

    }

    public void raiseIntake() {

        lower.set(false);

    }

    public void lowerIntake() {

        lower.set(true);
        isLowered();

    }



    public void hopperStuff(){

        if(isLowered() == true){

            hopper.set(.3);

        } else {

            hopper.set(0);

        }

    }

}