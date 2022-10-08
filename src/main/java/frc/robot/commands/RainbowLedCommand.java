package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.LedStrip;

public class RainbowLedCommand extends CommandBase {
    private LedStrip ledStrip;
    private int firstPixelHue;
    public RainbowLedCommand(LedStrip ledStrip) {
        this.ledStrip = ledStrip;
        addRequirements(ledStrip);
    }

    public void execute(){
        for(var i = 0; i < Constants.LED_STRIP_LENGTH; i++){
            ledStrip.setHSV(i, (firstPixelHue + (i * 180 / Constants.LED_STRIP_LENGTH)) % 180, 255, 255);
        }
        firstPixelHue += 3;
        firstPixelHue %= 180;
        ledStrip.show();
    }

    public void end(boolean isInterrupted){
        ledStrip.off();
    }
}
