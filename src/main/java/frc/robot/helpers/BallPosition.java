package frc.robot.helpers;

import edu.wpi.first.wpilibj.DigitalInput;

public class BallPosition {

    private DigitalInput sensor;

    public BallPosition(int channel){

        sensor = new DigitalInput(channel);

    }

    public boolean hasBall(){
        if(sensor.get()){
            return false;
        } else {
            return true;
        }
    }
    
}
