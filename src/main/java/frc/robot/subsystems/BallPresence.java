package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class BallPresence extends SubsystemBase{
    private Ultrasonic hopperSensor = new Ultrasonic(Constants.HOPPER_SENSOR_PING, Constants.HOPPER_SENSOR_ECHO);
    private Ultrasonic magSensor = new Ultrasonic(Constants.MAG_SENSOR_PING, Constants.MAG_SENSOR_ECHO);

    public BallPresence(){
        Ultrasonic.setAutomaticMode(true);
    }

    public Ultrasonic getHopperSensor(){
        return hopperSensor;
    }

    public Ultrasonic getMagSensor(){
        return magSensor;
    }

    public double getMagDistanceInches(){
        return magSensor.getRangeInches();
    }

    public double getHopperDistanceInches(){
        return hopperSensor.getRangeInches();
    }

    public boolean isBallInHopper(){
        return getHopperDistanceInches() < Constants.MAG_BACK_TO_HOPPER_END;
    }

    public boolean isBallInMag(){
        return getMagDistanceInches() < Constants.MAG_SENSOR_TO_SHOOTER;
    }
}
