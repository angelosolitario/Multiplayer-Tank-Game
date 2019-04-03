package Game;

import java.io.IOException;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;


public class TankControl extends GamePanel implements KeyListener {
    private SoundEngine sound;
    private Picture explosion;
    private Picture bullet;
    private boolean wIsPressed, sPressed, aPressed, dPressed, spaceIsPressed;
    private boolean upIsPressed, downIsPressed, leftIsPressed, rightIsPressed, enterIsPressed;


    public TankControl() {
        super();
        this.setFocusable( true );
        this.addKeyListener( this );
        new Thread( this ).start();
        try {
            this.explosion = new Picture("Resources/Explosion_small_strip.png", 32 );
            this.bullet = new Picture("Resources/ShellStrip.png", 24 );
            this.sound = new SoundEngine();
        } catch ( IOException exception ) {
            exception.printStackTrace();
        }
    }

    @Override
    public void run() {
        tank1.setDegree();
        tank2.setDegree();

        do {
            if ( ! ( player1.endGame() || player2.endGame() ) ) {

                if (leftIsPressed) {
                    tank2.rotateLeft();
                }
                if (rightIsPressed) {
                    tank2.rotateRight();
                }

                if (upIsPressed) {
                    tank2.forwardMove();
                    if ( !( ( tank2.collideWithWall(breakableWall) ) || ( tank2.collideWithTank(tank1) ) ) ) {
                        tank2.setImageX( tank2.getNewX() );
                        tank2.setImageY( tank2.getNewY() );
                    }
                }

                if (downIsPressed) {
                    tank2.reverseMove();
                    if ( !( ( tank2.collideWithWall(breakableWall) ) || ( tank2.collideWithTank(tank1) ) ) ) {
                        tank2.setImageX( tank2.getNewX() );
                        tank2.setImageY( tank2.getNewY() );
                    }
                }

                if (enterIsPressed) {

                    doAnimation( new GameAnimation( this.explosion, tank2.fireX(), tank2.fireY(),
                            1, false ) );

                    doAnimation( new Bullet( this, tank2, tank1, player2, player1,
                            breakableWall, this.bullet, tank2.fireX(), tank2.fireY(), 5, false ) );

                    sound.playOnce("Resources/Explosion_small.wav");
                }

                if (aPressed) {
                    tank1.rotateLeft();
                }

                if (dPressed) {
                    tank1.rotateRight();
                }

                if (wIsPressed) {
                    tank1.forwardMove();
                    if ( !( ( tank1.collideWithWall(breakableWall) ) || ( tank1.collideWithTank(tank2) ) ) ) {
                        tank1.setImageX( tank1.getNewX() );
                        tank1.setImageY( tank1.getNewY() );
                    }
                }

                if (sPressed) {
                    tank1.reverseMove();

                    if ( !( ( tank1.collideWithWall(breakableWall) ) || ( tank1.collideWithTank(tank2) ) ) ) {
                        tank1.setImageX( tank1.getNewX() );
                        tank1.setImageY( tank1.getNewY() );
                    }
                }

                if (spaceIsPressed) {

                    doAnimation( new GameAnimation( this.explosion, tank1.fireX(), tank1.fireY(),
                            1, false ) );

                    doAnimation( new Bullet( this, tank1, tank2, player1, player2,
                            breakableWall, this.bullet, tank1.fireX(), tank1.fireY(), 5, false ) );

                    sound.playOnce("Resources/Explosion_small.wav");
                }
            }


            this.repaint();


            try {
                Thread.sleep( 100 );
            } catch ( InterruptedException e ) {
                e.printStackTrace();
            }

        } while ( true );

    }


    @Override
    public void keyReleased( KeyEvent k ) {

        if ( k.getKeyCode() == KeyEvent.VK_A ) {
            aPressed = false;
        }
        else if ( k.getKeyCode() == KeyEvent.VK_D ) {
            dPressed = false;
        }
        else if ( k.getKeyCode() == KeyEvent.VK_W ) {
            wIsPressed = false;
        }
        else if ( k.getKeyCode() == KeyEvent.VK_S ) {
            sPressed = false;
        }
        else if ( k.getKeyCode() == KeyEvent.VK_SPACE ) {
            spaceIsPressed = false;
        }
        else if ( k.getKeyCode() == KeyEvent.VK_LEFT ) {
            leftIsPressed = false;
        }
        else if ( k.getKeyCode() == KeyEvent.VK_RIGHT ) {
            rightIsPressed = false;
        }
        else if ( k.getKeyCode() == KeyEvent.VK_UP ) {
            upIsPressed = false;
        }
        else if ( k.getKeyCode() == KeyEvent.VK_DOWN ) {
            downIsPressed = false;
        }
        else if ( k.getKeyCode() == KeyEvent.VK_ENTER ) {
            enterIsPressed = false;
        }

    }

    @Override
    public void keyTyped( KeyEvent k ) {}


    @Override
    public void keyPressed( KeyEvent k ) {

        if ( k.getKeyCode() == KeyEvent.VK_D ) {
            dPressed = true;
        }
        else if ( k.getKeyCode() == KeyEvent.VK_W ) {
            wIsPressed = true;
        }
        else if ( k.getKeyCode() == KeyEvent.VK_S ) {
            sPressed = true;
        }
        else if ( k.getKeyCode() == KeyEvent.VK_SPACE ) {
            spaceIsPressed = true;
        }
        else if ( k.getKeyCode() == KeyEvent.VK_LEFT ) {
            leftIsPressed = true;
        }
        else if ( k.getKeyCode() == KeyEvent.VK_RIGHT ) {
            rightIsPressed = true;
        }
        else if ( k.getKeyCode() == KeyEvent.VK_UP ) {
            upIsPressed = true;
        }
        else if ( k.getKeyCode() == KeyEvent.VK_DOWN ) {
            downIsPressed = true;
        }
        else if ( k.getKeyCode() == KeyEvent.VK_ENTER ) {
            enterIsPressed = true;
        }
        else if ( k.getKeyCode() == KeyEvent.VK_A ) {
            aPressed = true;
        }



    }




}



