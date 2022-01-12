package frc.robot.helpers;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.Constants;

public class DriveMotors {
    public CANSparkMax[] motors;
    public RelativeEncoder encoder;

    public CANSparkMax getLeadMotor() {
        return motors[0];
    }

    public DriveMotors(int canId1, int canId2, int canId3) {
        motors = new CANSparkMax[3];
        motors[0] = CreateNeoSparkMax(canId1); //leader
        motors[1] = CreateNeoSparkMax(canId2); //follower
        motors[2] = CreateNeoSparkMax(canId3); //follower

        //all motors follow the lead motor
        motors[1].follow(motors[0]);
        motors[2].follow(motors[0]);

        this.encoder = this.motors[0].getEncoder();
    }

    private CANSparkMax CreateNeoSparkMax(int canId) {
        CANSparkMax sparkMax = new CANSparkMax(canId, MotorType.kBrushless);
        sparkMax.setIdleMode(IdleMode.kBrake);
        sparkMax.setSmartCurrentLimit(Constants.NEO_LIMIT);
        return sparkMax;
    }

    public void ResetEncoder() {
        this.encoder.setPosition(0);
    }
}