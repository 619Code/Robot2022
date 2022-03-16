package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.LedStrip;

public class CavalierLedCommand extends CommandBase {
    private LedStrip ledStrip;
    private int currentFrame;
    private Timer frameTimer;
    private final double FRAME_DELAY = .25;

    private final int[][] COLORS = {{0, 0, 255}, {0, 0, 255}, {0, 0, 255}, {0, 0, 0}, {255, 70, 0}, {255, 70, 0}, {255, 70, 0}, {0, 0, 0}};

    public CavalierLedCommand(LedStrip ledStrip) {
        this.ledStrip = ledStrip;
        addRequirements(ledStrip);
        frameTimer = new Timer();
    }

    public void initialize(){
        currentFrame = 0;
        frameTimer.reset();
        frameTimer.start();
    }

    public void execute(){
        // if the timer has passed frame delay, change the frame
        if(frameTimer.hasElapsed(FRAME_DELAY)){
            currentFrame = (currentFrame + 1) % COLORS.length;
            frameTimer.reset();
            frameTimer.start();
        }
        for(int i = 0; i < Constants.LED_STRIP_LENGTH; i++){
            // set the color of each led to the correct one given frame
            int shownFrame = (currentFrame + i) % COLORS.length;
            ledStrip.setRGB(i, COLORS[shownFrame][0], COLORS[shownFrame][1], COLORS[shownFrame][2]);
        }
        ledStrip.show();
    }

    public void end(boolean isInterrupted){
        ledStrip.off();
    }
}
