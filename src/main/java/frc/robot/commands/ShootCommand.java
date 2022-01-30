package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Constants;
import frc.robot.States;
import frc.robot.helpers.*;


public class ShootCommand extends CommandBase {

private Shooter shoot;
private VerticalMagazine verticalMag;
private XboxController joystick;
private Timer speedUpTimer;
private Timer allDoneTimer;
BallPosition[] positions;
private boolean allDone;

    public ShootCommand(Shooter shoot, XboxController joystick){

        this.speedUpTimer = new Timer();
        this.allDoneTimer = new Timer();
        this.shoot = shoot;
        this.verticalMag = verticalMag;

        addRequirements(verticalMag, shoot);

    }
    


    @Override
    public void initialize(){

    }
  

    public void execute(){

        allDoneTimer.start();

        speedUpTimer.start();
        fireBall();


    }

    public boolean isEmpty(){

        if(!verticalMag.isEmpty()){

            allDoneTimer.reset();
            allDoneTimer.start();

        }

        if(verticalMag.isEmpty() && allDoneTimer.advanceIfElapsed(Constants.shooterEmptyPeriod)){

            return true;
        }

        return false;
    }

    public void fireBall(){

        if(this.isEmpty()){            
            shoot.end();
            verticalMag.end();
            allDone = true;
            return;
        }
        
        if(shoot.isOnTarget()){
            shoot.runFlywheel();
        }

        if(speedUpTimer.advanceIfElapsed(Constants.flywheelSpeedUptime)){
            verticalMag.discharge();
          

        }

    
        
    }

    public boolean isFinished(boolean isInterrupted){

        return allDone;
    }



    protected void end(){

    }
}
