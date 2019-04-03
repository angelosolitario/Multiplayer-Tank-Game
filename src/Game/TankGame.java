package Game;

public class TankGame {
    public static void main( String[] args ) {

        SoundEngine soundEngine = new SoundEngine();
        soundEngine.continuous("Resources/Music.mid");
        new GameFrame( new TankControl() );
    }
}
