package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class LedStrip extends SubsystemBase{
    AddressableLED ledStrip;
    AddressableLEDBuffer ledBuffer;

    public LedStrip() {
        ledStrip = new AddressableLED(Constants.LED_PWM_PORT);
        ledBuffer = new AddressableLEDBuffer(Constants.LED_STRIP_LENGTH);
        ledStrip.setLength(Constants.LED_STRIP_LENGTH);
        ledStrip.setData(ledBuffer);
        ledStrip.start();
    }

    public void setRGB(int index, int red, int green, int blue) {
        ledBuffer.setRGB(index, red, green, blue);
    }

    public void setHSV(int index, int hue, int saturation, int value) {
        ledBuffer.setHSV(index, hue, saturation, value);
    }

    public void setWholeStripRGB(int red, int green, int blue) {
        for (int i = 0; i < Constants.LED_STRIP_LENGTH; i++) {
            ledBuffer.setRGB(i, red, green, blue);
        }
    }

    public void setWholeStripHSV(int hue, int saturation, int value) {
        for (int i = 0; i < Constants.LED_STRIP_LENGTH; i++) {
            ledBuffer.setHSV(i, hue, saturation, value);
        }
    }

    public void off(){
        setWholeStripRGB(0, 0, 0);
        show();
    }

    public void show() {
        ledStrip.setData(ledBuffer);
    }
}