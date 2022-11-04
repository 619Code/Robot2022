package frc.robot.helpers;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class ZeroButton extends Button {
    JoystickButton button1;
    JoystickButton button2;

    public ZeroButton(JoystickButton a, JoystickButton b) {
        this.button1 = a;
        this.button2 = b;
    }

    @Override
    public boolean get() {
        return button1.get() && button2.get();
    }

}