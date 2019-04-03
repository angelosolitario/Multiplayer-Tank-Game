package Game;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.io.IOException;


public abstract class GamePanel extends JPanel implements Runnable {

    protected final String BACKGROUND_IMAGE = "Resources/Background.bmp";
    protected final String TANK_IMAGE = "Resources/TankStrip.png";
    protected final int WIDTH = 1250;
    protected final int HEIGHT = 1280;
    protected final int PANEL_HEIGHT = 700;
    protected Dimension dimension;
    protected ImageObject background;
    protected Wall breakableWall;
    protected Tank tank1, tank2;
    protected Player player1, player2;

    protected ArrayList< GameAnimation > gameAnimations;
    protected BufferedImage miniImage = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
    protected BufferedImage tank1Img = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
    protected BufferedImage tank2Img = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
    protected Graphics miniGame ;



    public GamePanel() {
        try {
            this.background = new ImageObject( BACKGROUND_IMAGE );
            this.tank1 = new Tank( TANK_IMAGE);
            this.tank2 = new Tank( TANK_IMAGE);
            this.breakableWall = new Wall("Resources/Wall2.gif");
            this.breakableWall.setWallMap();
            player1 = new Player(tank1, breakableWall,"3");
            player2 = new Player(tank2, breakableWall,"4");
            player1.setInitPosition();
            player2.setInitPosition();

        } catch( IOException exception ) {
            System.err.println( "FIle cannot be found" );
            exception.printStackTrace();
        }
        this.gameAnimations = new ArrayList<>();
        this.dimension = new Dimension( WIDTH, PANEL_HEIGHT );
    }

    @Override
    public Dimension getPreferredSize() {
        return this.dimension;
    }

    @Override
    public void paintComponent( Graphics graphics ) {
        super.paintComponent( graphics );
        miniGame = miniImage.createGraphics();
        for( int x = 0; x < WIDTH; x += background.getWidth() ) {
            for( int y = 0; y < HEIGHT; y+= background.getHeight() ) {
                background.setImageX( x );
                background.setImageY( y );
                background.doDrawing(miniGame);
            }
        }


        GameAnimation animation;
        for(int counter = 0; counter < gameAnimations.size(); counter++ ) {
            animation = gameAnimations.get( counter );
            if( animation.isStopped() ) {
                gameAnimations.remove( counter );
            } else {
                animation.doDrawing( miniGame );
            }
        }


        breakableWall.doDrawing(miniGame);
        tank1.doDrawing( miniGame );
        tank2.doDrawing( miniGame );
        miniGame = graphics;

        tank1Img = miniImage.getSubimage(getTankPositionX(tank1), getTankPositionY(tank1), WIDTH/2, PANEL_HEIGHT);
        tank2Img = miniImage.getSubimage(getTankPositionX(tank2), getTankPositionY(tank2), WIDTH/2, PANEL_HEIGHT);

        miniGame.drawImage(tank1Img,0,0,WIDTH/2-1,PANEL_HEIGHT,this);
        miniGame.drawImage(tank2Img,WIDTH/2,0,WIDTH/2,PANEL_HEIGHT,this);
        miniGame.drawImage(miniImage, HEIGHT/2-175, 0, 320, 250,this);

        player1.displayHealth(miniGame, 5, PANEL_HEIGHT-35);
        player2.displayHealth(miniGame, WIDTH/2+10, PANEL_HEIGHT-35);

        player1.displayLives(miniGame, 230, PANEL_HEIGHT-35);
        player2.displayLives(miniGame, WIDTH/2+240, PANEL_HEIGHT-35 );

        player1.displayPoints(miniGame,WIDTH/2-200, PANEL_HEIGHT-10);
        player2.displayPoints(miniGame,WIDTH-200, PANEL_HEIGHT-10);


    }


    public int getTankPositionX(Tank tanks) {
        int positionX;
        positionX = tanks.getImageX() - WIDTH/4;

        if (positionX < 0) {
            positionX = 0;
        }
        if (positionX > (WIDTH/2)) {
            positionX = (WIDTH/2);
        }
        return positionX;
    }


    public int getTankPositionY(Tank tanks) {
        int positionY;
        positionY = tanks.getImageY() - PANEL_HEIGHT/2;

        if (positionY < 0) {
            positionY = 0;
        }
        if (positionY > (HEIGHT - PANEL_HEIGHT)) {
            positionY = (HEIGHT - PANEL_HEIGHT);
        }
        return positionY;
    }


    public void doAnimation( GameAnimation animation ) {
        gameAnimations.add( animation );
    }


}
