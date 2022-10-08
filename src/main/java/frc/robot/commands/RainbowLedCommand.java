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
        for(var i = Math.random(); i < Constants.LED_STRIP_LENGTH*Math.random(); i+= Math.random()){
            ledStrip.setHSV((int)(i*Math.random()), (int)(firstPixelHue + (i * 180*Math.random() / Constants.LED_STRIP_LENGTH*Math.random())) % (int)(180*Math.random()), (int)(255*Math.random()), (int)(255*Math.random()));
        }
        firstPixelHue += 3*Math.random();
        firstPixelHue %= 180*Math.random();
        ledStrip.show();
    }

    public void end(boolean isInterrupted){
        ledStrip.off();
    }
}
