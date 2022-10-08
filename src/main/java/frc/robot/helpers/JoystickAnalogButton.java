package frc.robot.helpers;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class JoystickAnalogButton extends Button {

    XboxController m_joystick;
    int m_axisNumber;
    private double THRESHOLD = 0.5;

    public JoystickAnalogButton(XboxController joystick, int axisNumber) {
        m_joystick = joystick;
        m_axisNumber = axisNumber;
    }

    public JoystickAnalogButton(XboxController joystick, int axisNumber, double threshold) {
        m_joystick = joystick;
        m_axisNumber = axisNumber;
        THRESHOLD = threshold;
    }

    public void setThreshold(double threshold) {
        THRESHOLD = threshold;
    }

    public double getThreshold() {
        return THRESHOLD;
    }

    public boolean get() {
        if (THRESHOLD < 0) {
            return m_joystick.getRawAxis(m_axisNumber) < THRESHOLD;
        } else {
            return m_joystick.getRawAxis(m_axisNumber) > THRESHOLD;
        }
    }

}
