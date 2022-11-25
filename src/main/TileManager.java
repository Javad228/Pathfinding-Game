import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Random;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum;
    private Object[] loot;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[10];
        mapTileNum = new int[gp.maxScreenCol+1][gp.maxScreenRow+1];
        getTileImage();
        update();
    }

    public void update() {
        loadMap("/maps/asdc.txt");
    }

    public void getTileImage() {
        try {
            String[] arr = new String[10];
            arr[0] = "/tiles/grass.png";
            arr[1] = "/tiles/grass.png";
            arr[2] = "/tiles/grass.png";
            arr[3] = "/tiles/grass.png";
            arr[4] = "/tiles/tile000.png";
            arr[5] = "/tiles/tile001.png";
            arr[6] = "/tiles/tile002.png";
            arr[7] = "/tiles/tile003.png";
            arr[8] = "/tiles/grass.png";
            arr[9] = "/tiles/grass.png";
            Random rand = new Random();

            tile[0] = new Tile();
            tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(arr[rand.nextInt(10)])));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/tree.png")));
            tile[1].collision = true;

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/tree.png")));
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
                String line = br.readLine();

                while (col < gp.maxScreenCol) {
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }

                if(col == gp.maxScreenCol) {
                    col = 0;
                    row++;
                }
            }


            br.close();
        } catch(Exception e) {

        }
    }
    public void draw(Graphics2D g2) {
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while(col < gp.maxScreenCol && row < gp.maxScreenRow) {

            int tileNum = mapTileNum[col][row];

            g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
            col++;
            x += gp.tileSize;

            if (col == gp.maxScreenCol) {
                col = 0;
                x = 0;
                row++;
                y += gp.tileSize;
            }
        }
    }

}
