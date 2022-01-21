package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.States;
import frc.robot.helpers.*;


public class ShootCommand extends CommandBase {

private IterativeDelay speedUpDelay;
private Shooter shoot;
private IntakeMagazine index;
private XboxController joystick;
BallIndex[] positions;

    public ShootCommand(Shooter shoot, XboxController joystick){
        this.shoot = shoot;
        this.index = index;

        addRequirements(index);
        addRequirements(shoot);
    }
    


    @Override
    public void initialize(){

    }
    // Sensor at each ball index and in shooting state
            //Sensor 0 = first,
            //Sensor 1 = second,
            //Sensor 2 = shoot.
    // if trigger > .5, shoot
    // power up
    // move from sensor 1 to sensor 2
    // via moving intake
    // if there is not ball at sensor 0, end() = true
    // brief re-power up period
    // move from sensor 0 to sensor 1
    // move from sensor 1 to sensor 2
    // end = true

    public void execute(){
        
        this.speedUpDelay.Cycle();
        States.isShooting = true;

        //rev up motors whence shooter subsystem exists

        if(speedUpDelay.IsDone()){
            //fireBall();
        }

       // look for method similar to isEmpty once implemented in intake
       // if(index.isEmpty()){
            this.speedUpDelay.Reset();
            //reset other iterative delays once they are created

       // }
    }

    public void fireBall(){
        if(){

        }
        
    }


    protected void end(){

    }
}
