package Game;


import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;


public class Wall extends ImageObject {
    private static BufferedReader map;
    private BufferedImage unbreakable;
    private int mapHeight = 40;
    private int mapWidth = 39;

    private String[][] walls = new String[mapHeight][mapWidth];

    public Wall(String WallSource) throws IOException {
        super(WallSource);

        map = new BufferedReader(new FileReader("Resources/map.txt"));
        unbreakable = ImageIO.read(new File("Resources/Wall1.gif"));
    }


    public void setWallMap() throws IOException {
        int row = 0;
        String line;

        while ((line = map.readLine()) != null) {
            for (int col = 0; col < walls[row].length; col++) {
                walls[row][col] = String.valueOf(line.charAt(col));
            }
            row++;
        }
    }


    public String[][] getWalls() {
        return walls;
    }

    public void setUpdatedWallMap(String[][] newWallMap) {
        this.walls = newWallMap;
    }

    @Override
    public void doDrawing(Graphics graphics) {
        for (x = 0; x < mapWidth; x++) {
            for (y = 0; y < mapHeight; y++) {
                if (walls[y][x].equals("1")) {
                    graphics.drawImage(unbreakable, x * unbreakable.getWidth(), y * unbreakable.getHeight(), observer);
                } else if (walls[y][x].equals("2")) {
                    graphics.drawImage(image, x * image.getWidth(), y * image.getHeight(), observer);
                }
            }
        }

    }
}

