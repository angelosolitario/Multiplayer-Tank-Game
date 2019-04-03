package Game;

import javax.swing.*;

public class GameFrame extends JFrame {

    public GameFrame( GamePanel gp ) {
        setTitle( "Tank Game CSC 413" );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setResizable( true );
        add( gp );
        pack();
        Thread th = new Thread( gp );
        th.start();
        setVisible( true );
    }
}
