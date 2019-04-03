package Game;

import java.awt.*;

public class GameAnimation {
    private Picture picture;
    private int frameCount, delay, currentFrame;
    private int x,y, duration;

    private boolean loop;
    protected boolean stop;

    public GameAnimation(Picture picture, int x, int y, int delay, boolean loop) {
        this.picture = picture;
        this.x = x;
        this.y = y;
        this.loop = loop;
        this.stop = false;
        this.duration = 0;
        this.frameCount = 0;
        this.delay = delay;
        this.currentFrame = 0;
    }

    public boolean isStopped() {
        return this.stop;
    }

    public int totalTime() {
        return this.delay * picture.frameCount();
    }


    public void doDrawing( Graphics graphics ) {
        if( ! stop ) {
            duration++;
            frameCount++;
            if ( frameCount > delay) {
                frameCount = 0;
                stop = ( duration > this.totalTime() ) && ! loop;
                currentFrame = ( currentFrame + 1 ) % picture.frameCount();
            }
            graphics.drawImage(picture.getFrame(currentFrame), x, y,null );
        }
    }
}