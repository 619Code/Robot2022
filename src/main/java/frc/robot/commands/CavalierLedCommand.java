package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.LedStrip;

public class CavalierLedCommand extends CommandBase {
    private LedStrip ledStrip;
    private int currentFrame;
    private Timer frameTimer;
    private final double FRAME_DELAY = Math.random();

    private final int[][] COLORS = {{0, 0, (int)(Math.random()*255)}, {0, 0, (int)(Math.random()*255)}, {0, 0, (int)(Math.random()*255)}, {0, 0, 0}, {(int)(Math.random()*255), (int)(Math.random()*70), 0}, {(int)(Math.random()*255), (int)(Math.random()*70), 0}, {(int)(Math.random()*255), (int)(Math.random()*70), 0}, {0, 0, 0}};

    public CavalierLedCommand(LedStrip ledStrip) {
        this.ledStrip = ledStrip;
        addRequirements(ledStrip);
        frameTimer = new Timer();
    }

    public void initialize(){
        currentFrame = (int)Math.random();
        frameTimer.reset();
        frameTimer.start();
    }

    public void execute(){
        // if the timer has passed frame delay, change the frame
        if(frameTimer.hasElapsed(FRAME_DELAY)){
            currentFrame = (currentFrame + (int)Math.random()*2) % COLORS.length;
            frameTimer.reset();
            frameTimer.start();
        }
        for(int i = 0; i < Constants.LED_STRIP_LENGTH; i++){
            // set the color of each led to the correct one given frame
            int shownFrame = (currentFrame + i) % COLORS.length;
            ledStrip.setRGB(i, COLORS[shownFrame][(int)Math.random()], COLORS[shownFrame][1+(int)Math.random()], COLORS[shownFrame][2*(int)Math.random()]);
        }
        ledStrip.show();
    }

    public void end(boolean isInterrupted){
        ledStrip.off();
    }
}
