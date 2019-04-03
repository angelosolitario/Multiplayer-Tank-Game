package Game;


import java.awt.*;
import java.io.IOException;


public class Bullet extends GameAnimation{
    private final int speed = 20;
    private int index;
    private int[] angle;
    private int x, y;
    private Picture explode;
    private Picture bulletGoes;
    private Tank tank1, tank2;
    private Wall wall;
    private int mapHeight = 40;
    private int mapWidth = 39;
    private int rectWidth1 = 5;
    private int rectWidth2 = 10;
    private int rectHeight1 = 5;
    private int rectHeight2 = 10;


    private GamePanel gamePanel;
    private Player player1;
    private Player player2;


    public Bullet(GamePanel gamePanel, Tank tank1, Tank tank2, Player player1, Player player2,
                  Wall wall, Picture bulletGoes, int x, int y, int frame_delay, boolean loop) {
        super(bulletGoes, x, y, frame_delay, loop);

        this.gamePanel = gamePanel;
        this.x = x;
        this.y = y;
        this.player1 = player1;
        this.player2 = player2;
        this.bulletGoes = bulletGoes;
        this.angle = tank1.getAngle();
        this.index = tank1.getIndex();
        this.tank1 = tank1;
        this.tank2 = tank2;
        this.wall = wall;



        try {
            explode = new Picture("Resources/Explosion_small_strip.png", 24);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//collision with wall
    public boolean collideWithWall() {
        try {
            wall.setWallMap();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Rectangle bullets = new Rectangle(x, y, rectWidth2, rectHeight2);

        for (int row = 0; row < mapHeight; row++) {
            for (int col = 0; col < mapWidth; col++) {
                if ( wall.getWalls()[row][col].equals("1") || wall.getWalls()[row][col].equals("2")){
                    Rectangle walls = new Rectangle(col * wall.getWidth(), row * wall.getHeight(),
                            wall.getWidth(), wall.getHeight());
                    if (bullets.intersects(walls)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void hitWall() {
        try {
            wall.setWallMap();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Rectangle bullets = new Rectangle(x, y, rectWidth2, rectHeight2);

        for (int row = 0; row < mapHeight; row++) {
            for (int col = 0; col < mapWidth; col++) {

                if (wall.getWalls()[row][col].equals("2")) {
                    Rectangle walls = new Rectangle(col * wall.getWidth(), row * wall.getHeight(),
                            wall.getWidth(), wall.getHeight());

                    if (bullets.intersects(walls)) {

                        wall.getWalls()[row][col] = "0";

                        wall.setUpdatedWallMap(wall.getWalls());
                    }
                }
            }
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////
    //collision with tank
    public boolean collideWithTank() {
        Rectangle bullets = new Rectangle(x, y, rectWidth1, rectHeight1);
        Rectangle tankARec = new Rectangle(tank1.getImageX(), tank1.getImageY(), tank1.getWidth()-10, tank1.getHeight()-10);
        Rectangle tankBRec = new Rectangle(tank2.getImageX(), tank2.getImageY(), tank2.getWidth()-10, tank2.getHeight()-10);

        return (bullets.intersects(tankARec) || bullets.intersects(tankBRec));
    }

    public void hitTank() {
        Rectangle bullets = new Rectangle(x, y, rectWidth1, rectHeight1);
        Rectangle tank_aRec = new Rectangle(tank1.getImageX(), tank1.getImageY(), tank1.getWidth()-10, tank1.getHeight()-10);
        Rectangle tank_bRec = new Rectangle(tank2.getImageX(), tank2.getImageY(), tank2.getWidth()-10, tank2.getHeight()-10);

        if (bullets.intersects(tank_aRec)) {
            player1.hitTank();
            if (player1.die()) {
                player2.increaseScore();
                explode(tank1);
                player1.setInitPosition();
                tank1.setIndex(0);
            }
        }

        if (bullets.intersects(tank_bRec)) {
            player2.hitTank();
            if (player2.die()) {
                player1.increaseScore();
                explode(tank2);
                player2.setInitPosition();
                tank2.setIndex(0);
            }
        }
    }


    //from the tank rotation example
    public void moveForward() {
        x += (int) (Math.cos(Math.toRadians(angle[index])) * speed);
        y -= (int) (Math.sin(Math.toRadians(angle[index])) * speed);
    }

    public void explode(Tank deadTank) {
        gamePanel.doAnimation(new GameAnimation(this.explode, deadTank.getImageX(), deadTank.getImageY(), 5, false));
    }


    @Override
    public void doDrawing( Graphics graphics ) {

        if( !stop ) {
            moveForward();
            graphics.drawImage(bulletGoes.getFrame(index), x, y, null);
            if (collideWithWall()) {
                hitWall();
                stop = true;
            }
            if (collideWithTank()) {
                hitTank();
                stop = true;
            }
        }
    }
}
