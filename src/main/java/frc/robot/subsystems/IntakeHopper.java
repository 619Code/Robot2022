package frc.robot.subsystems;

import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeHopper extends SubsystemBase {

    private VictorSPX intake;
    private CANSparkMax hopper;
    public Solenoid lower;

    public IntakeHopper() {
        // Magazine can only consist of 2 balls held at once, if at all.

        //Here's the motor needed to spin the intake bar.
        intake = new VictorSPX(21); 

        //Wrist solenoid used to raise and lower the intake 
        lower = new Solenoid(Constants.INTAKE_MODULE_TYPE, Constants.INTAKE_SOLENOID);
        this.initMotors();
        
    }

    private void initMotors(){

        hopper = new CANSparkMax(Constants.HOPPER_LEFT_MOTOR, MotorType.kBrushless);
      
    }

    

    public void spIntake(double percent) {
        intake.set(ControlMode.PercentOutput, percent);


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