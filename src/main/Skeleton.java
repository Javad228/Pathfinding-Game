
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Skeleton extends NonPlayableCharacter {

    //public Skeleton(GamePanel gp) {
    //    super(gp);
    public Skeleton(int xCoord, int yCoord) {
        name = "Skeleton";
        movementSpeed = 1;
        solidArea.x = 10;
        solidArea.y = 5;
        solidArea.width = 42;
        solidArea.height = 40;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        this.collisionAreaDefaultX = solidArea.x;
        this.collisionAreaDefaultY = solidArea.y;
        this.xCoord = xCoord;
        this.yCoord = yCoord;

        this.setDamagePerHit(1); // originally 10
        getImage();

    }

    @Override
    public void setAction(GamePanel gp){
        int goalCol = (gp.player.xCoord + gp.player.solidArea.x) / gp.tileSize;
        int goalRow = (gp.player.yCoord + gp.player.solidArea.y) / gp.tileSize;
        int startCol = (xCoord + solidArea.x) / gp.tileSize;
        int startRow = (yCoord + solidArea.y) / gp.tileSize;

        if (goalCol == startCol || goalRow == startRow) {
            if (goalCol == startCol) {
                if (Math.abs(goalRow - startRow) < 1) {
                    int nope = 0;
                    for (int i = 0; i < 3; i++) {
                        if(goalRow+i<gp.tileM.mapTileNum[goalCol].length){
                            if (gp.tileM.mapTileNum[goalCol][goalRow + i] == 1) {
                                nope = 1;
                            }
                        }
                    }
                    if (nope == 1) {
                        canMove = true;
                        searchPath(goalCol, goalRow, gp);
                    } else {
                        int currentX = this.getxCoord();
                        int currentY = this.getyCoord();
                        canMove = false;
                        actionLockCounter++;

                        if(actionLockCounter == 70){
                            actionLockCounter = 0;
                        }
                    }
                } else {
                    canMove = true;
                    searchPath(goalCol, goalRow, gp);
                }
            }
            if (goalRow == startRow) {
                if (Math.abs(goalCol - startCol) < 1) {
                    int nope = 0;
                    for (int i = 0; i < 3; i++) {
                        if(goalCol+i<gp.tileM.mapTileNum[goalRow].length) {
                            if (gp.tileM.mapTileNum[goalCol + i][goalRow] == 1) {
                                nope = 1;
                            }
                        }
                    }
                    if (nope == 1) {
                        canMove = true;
                        searchPath(goalCol, goalRow, gp);
                    } else {
                        int currentX = this.getxCoord();
                        int currentY = this.getyCoord();
                        canMove = false;
                        actionLockCounter++;

                        if(actionLockCounter == 70){
                            actionLockCounter = 0;
                        }
                    }
                } else {
                    canMove = true;
                    searchPath(goalCol, goalRow, gp);
                }
            }
        } else {
            canMove = true;
            searchPath(goalCol, goalRow, gp);
        }
    }

    @Override
    public void update(GamePanel gp) {
        if (getUp1() == null)   getImage();
        super.update(gp);
    }

    public void getImage() {
        try {
            this.setUp1(ImageIO.read(getClass().getResourceAsStream("/Skeleton/tile002.png")));
            this.setUp2(ImageIO.read(getClass().getResourceAsStream("/Skeleton/tile004.png")));
            this.setUp3(ImageIO.read(getClass().getResourceAsStream("/Skeleton/tile006.png")));
            this.setUp4(ImageIO.read(getClass().getResourceAsStream("/Skeleton/tile008.png")));
            this.setUp5(ImageIO.read(getClass().getResourceAsStream("/Skeleton/tile010.png")));
            this.setUp6(ImageIO.read(getClass().getResourceAsStream("/Skeleton/tile012.png")));
            this.setDown1(ImageIO.read(getClass().getResourceAsStream("/Skeleton/tile002.png")));
            this.setDown2(ImageIO.read(getClass().getResourceAsStream("/Skeleton/tile004.png")));
            this.setDown3(ImageIO.read(getClass().getResourceAsStream("/Skeleton/tile006.png")));
            this.setDown4(ImageIO.read(getClass().getResourceAsStream("/Skeleton/tile008.png")));
            this.setDown5(ImageIO.read(getClass().getResourceAsStream("/Skeleton/tile010.png")));
            this.setDown6(ImageIO.read(getClass().getResourceAsStream("/Skeleton/tile012.png")));
            this.setLeft1(ImageIO.read(getClass().getResourceAsStream("/Skeleton/tile002.png")));
            this.setLeft2(ImageIO.read(getClass().getResourceAsStream("/Skeleton/tile004.png")));
            this.setLeft3(ImageIO.read(getClass().getResourceAsStream("/Skeleton/tile006.png")));
            this.setLeft4(ImageIO.read(getClass().getResourceAsStream("/Skeleton/tile008.png")));
            this.setLeft5(ImageIO.read(getClass().getResourceAsStream("/Skeleton/tile010.png")));
            this.setLeft6(ImageIO.read(getClass().getResourceAsStream("/Skeleton/tile012.png")));
            this.setRight1(ImageIO.read(getClass().getResourceAsStream("/Skeleton/tile002.png")));
            this.setRight2(ImageIO.read(getClass().getResourceAsStream("/Skeleton/tile004.png")));
            this.setRight3(ImageIO.read(getClass().getResourceAsStream("/Skeleton/tile006.png")));
            this.setRight4(ImageIO.read(getClass().getResourceAsStream("/Skeleton/tile008.png")));
            this.setRight5(ImageIO.read(getClass().getResourceAsStream("/Skeleton/tile010.png")));
            this.setRight6(ImageIO.read(getClass().getResourceAsStream("/Skeleton/tile012.png")));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public Object getSubClass() {
        return Skeleton.class;
    }
}