package frc.robot;

import edu.wpi.first.wpilibj.RobotBase;

public final class Main {
    private Main() {
    }

    public static void main(String... args) {
        //System.out.println("DO NOT RUN THIS CODE."); //should be okay now dw
        RobotBase.startRobot(Robot::new);
    }
}
