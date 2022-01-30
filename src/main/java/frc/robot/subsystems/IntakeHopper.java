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






/* package frc.robot.subsystems;

import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax.*;



public class IntakeMagazine extends SubsystemBase {

    private VictorSPX intake;
    public Solenoid lower;

    public IntakeMagazine() {
        // Magazine can only consist of 2 balls held at once, if at all.

        //Here's the motor needed to spin the intake bar.
        intake = new VictorSPX(21); 

        //Wrist solenoid used to raise and lower the intake 
        lower = new Solenoid(Constants.INTAKE_MODULE_TYPE, Constants.INTAKE_SOLENOID);

        
    }

    public void spIntake(double percent) {
        intake.set(ControlMode.PercentOutput, percent);
    }
} */


//THIS IS TEMPORARY SO I CAN WORK ON OTHER STUFF, I CAN CHANGE IT LATER

// package frc.robot.subsystems;

// //import com.ctre.phoenix.motorcontrol.ControlMode;
// //import com.ctre.phoenix.motorcontrol.NeutralMode;
// //import com.ctre.phoenix.motorcontrol.can.VictorSPX;
// /*import com.revrobotics.CANSparkMax;
// import com.revrobotics.CANSparkMax.IdleMode;
// import com.revrobotics.CANSparkMaxLowLevel.MotorType;

// import edu.wpi.first.wpilibj.Solenoid;
// import edu.wpi.first.wpilibj2.command.Subsystem;
// import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import edu.wpi.first.wpilibj2.command.PIDCommand;
// import frc.robot.Constants;
// import frc.robot.helpers.BallPosition;*/


// //import frc.robot.helpers.BallPosition;

// public class IntakeMagazine extends SubsystemBase {
//     CANSparkMax magazine;
//     CANSparkMax loading;
//     //private VictorSPX roller;
//     private CANSparkMax hopperBelt;
//     private Solenoid wrist;
//     private PIDCommand magazinePID;

//     BallPosition[] positions;
//     boolean isIntakeDown;
//     boolean isDone = false;

//     public IntakeMagazine() {
//         // Vertical mag with 1 ball
//         magazine = new CANSparkMax(Constants.VERTICAL_MAG_MOTOR, MotorType.kBrushless);
//         magazine.restoreFactoryDefaults();
//         magazine.setSecondaryCurrentLimit(35);
//         magazine.setIdleMode(IdleMode.kBrake);

//         /*
//         // Vertical loader
//         loading = new CANSparkMax(Constants.LOADING_MOTOR, MotorType.kBrushless);
//         loading.restoreFactoryDefaults();
//         loading.setSecondaryCurrentLimit(35);
//         loading.setIdleMode(IdleMode.kCoast);  */

//         //roller = new VictorSPX(Constants.INTAKE_MOTOR);
//         //roller.configFactoryDefault();
//         //roller.setNeutralMode(NeutralMode.Coast);

//         wrist = new Solenoid(Constants.PCM_CAN_ID, Constants.INTAKE_SOLENOID);

//         hopperBelt = new CANSparkMax(Constants.HOPPER_MOTOR_0, MotorType.kBrushless);
//         hopperBelt.restoreFactoryDefaults();
//         hopperBelt.setSecondaryCurrentLimit(50);
//         hopperBelt.setIdleMode(IdleMode.kCoast);

//         // Magazine index diagram
//         this.positions = new BallPosition[] { 
//             new BallPosition(Constants.SHOOTER_POSITION),
//             new BallPosition(Constants.VERTICAL_MAG_MOTOR),
//             new BallPosition(Constants.TRANSITION_MAG_POSITION)
//         };
//     }

//     //belts
//     public void Loader(double speed) {
//         loading.set(speed);
//     }

//     public void MagazineBelt(double speed) {
//         magazine.set(speed);
//     }

//     //intake
//     /*
//     public void SpinIntake(double speed) {
//         if (isIntakeDown)
//             roller.set(ControlMode.PercentOutput, speed);
//         else{
//             roller.set(ControlMode.PercentOutput, 0);
//         }
//     } */

//     public void IntakeBelt(double speed) {
//         hopperBelt.set(speed);
//     }

//     public void RaiseIntake() {
//         isIntakeDown = false;
//         wrist.set(false);
//     }

//     public void LowerIntake() {
//         isIntakeDown = true;
//         wrist.set(true);
//     }

//     //information
//     public boolean HasBallAtIndex(int index) {
//         return this.positions[index].hasBall();
//     }

//     public boolean isFull() {
//         if(!IsMagazineFilled()) {
//             return false;
//         } else if(!positions[Constants.VERTICAL_MAG_MOTOR].hasBall()) {
//             return false;
//         } else if(positions[Constants.MAG_POS_LOW].hasBall() || positions[Constants.MAG_POS_PRE].hasBall()) {
//             return true;
//         } else {
//             return false;
//         }
//     }

//     public boolean IsMagazineFilled() {
//         for (int i = 0; i < 2; i++) {
//             if (!positions[i].hasBall()) {
//                 return false;
//             }
//         }
//         return true;
//         return positions[Constants.MAG_POS_END].hasBall();
//     }

//     public boolean IsIntakePositionFilled() {
//         return positions[4].hasBall() || positions[5].hasBall();
//     }

//     public boolean isEmpty() {
//         for (int i = 0; i < 6; i++) {
//             if (positions[i].hasBall()) {
//                 return false;
//             }
//         }
//         return true;
//     }


//     protected void InitDefaultCommand() {
//     }

//     public void raiseIntake() {
//         lower.set(false);
//     }

//     public void lowerIntake() {
//         lower.set(true);
//     }

// }
