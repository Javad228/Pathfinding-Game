import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

/**
 * NonPlayableCharacter - An abstract class which defines attributes for a character that is not created by the user.
 *
 * @author Cameron Hofbauer
 * @version September 28, 2022
 */

public class NonPlayableCharacter extends Character {

    //public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    private int damagePerHit;               // Amount of damage a NonPlayableCharacter can inflict on other Characters
    //private script pathfindingScript      // Pathfinding Script
                                            // TODO: Update pathfindingScript when fully implemented
    //private pattern projectilePattern     // Projectile Pattern
                                            // TODO: Update projectilePattern when fully implemented
    private double attackCooldown;          // Amount of time for a NonPlayableCharacter has to wait in between attacks
    public boolean onPath = false;
    public boolean canMove = true;
    /**
     *  Empty constructor to create a generic NonPlayableCharacter
     */
    public NonPlayableCharacter() {
        super();
        this.damagePerHit = 0;
        this.attackCooldown = 1;
    }

    public void setAction(GamePanel gp){}

    public void update(GamePanel gp){
        int frameAdjust = 12;
        spriteCounter++;
        if(spriteCounter > frameAdjust){
            if(spriteNum == 1){
                spriteNum = 2;
            } else if (spriteNum == 2){
                spriteNum = 3;
            } else if (spriteNum == 3){
                spriteNum = 4;
            } else if (spriteNum == 4){
                spriteNum = 5;
            }else if (spriteNum == 5){
                spriteNum = 6;
            }else if (spriteNum == 6){
                spriteNum = 1;
            }
            spriteCounter = 0;
        }

        if (!this.getIsAlive()) { // if dead, then don't update the position
            return;
        }

        if(isInvincible){
            invincibleCounter++;
            if(invincibleCounter>30){
                isInvincible = false;
                invincibleCounter = 0;
            }
        }
        setAction(gp);
//        System.out.println(direction);
        collisionOn = false;
//        System.out.println(pathfinder);
        if(!collisionOn) {
            if(canMove) {
                if(Objects.equals(this.name, "Slime")) {
                    if (spriteNum != 1 && spriteNum != 2 && spriteNum != 6) {
                        switch (direction) {
                            case "up" -> yCoord -= movementSpeed;
                            case "down" -> yCoord += movementSpeed;
                            case "left" -> xCoord -= movementSpeed;
                            case "right" -> xCoord += movementSpeed;
                        }
                    }
                }else{

                    switch (direction) {
                        case "up" -> yCoord -= movementSpeed;
                        case "down" -> yCoord += movementSpeed;
                        case "left" -> xCoord -= movementSpeed;
                        case "right" -> xCoord += movementSpeed;
                    }

                }
            }
        }

        attacking(gp);

    }
    public void searchPath(int goalCol, int goalRow, GamePanel gp){
        int startCol = (xCoord + solidArea.x)/gp.tileSize;
        int startRow = (yCoord + solidArea.y)/gp.tileSize;

        gp.pFinder.setNodes(startCol,startRow,goalCol,goalRow,this);

        if(gp.pFinder.search()){
            //next worldX and worldY
            int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
            int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;
            //Entity's solidArea position
            int enLeftX = xCoord + solidArea.x;
            int enRightX = xCoord + solidArea.x + solidArea.width;
            int enTopY = yCoord + solidArea.y;
            int enBottomY = yCoord + solidArea.y + solidArea.height;

            if(enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize){
                direction = "up";
            }else if(enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize){
                direction = "down";
            }else if(enTopY >= nextY && enBottomY < nextY + gp.tileSize){
                //left or right
                if(enLeftX > nextX){
                    direction = "left";
                }
                if(enLeftX < nextX){
                    direction = "right";
                }
            }else if(enTopY > nextY && enLeftX > nextX){
                //up or left
                direction = "up";
                collisionOn = false;
                gp.checker.checkTile(this);
                if(collisionOn){
                    direction = "left";
                }
            }else if(enTopY > nextY && enLeftX < nextX){
                //up or right
                direction = "up";
                collisionOn = false;
                gp.checker.checkTile(this);
                if(collisionOn){
                    direction = "right";
                }
            }else if(enTopY < nextY && enLeftX > nextX){
                //down or left
                direction = "down";
                collisionOn = false;
                gp.checker.checkTile(this);
                if(collisionOn){
                    direction = "left";
                }
            }else if(enTopY < nextY && enLeftX < nextX){
                //down or right
                direction = "down";
                collisionOn = false;
                gp.checker.checkTile(this);
                if(collisionOn){
                    direction = "right";
                }
            }
        }
    }
    public void attacking(GamePanel gamePanel) {    // TODO: Temporary attacking method, exhibits unpredictable behavior
        int currX = this.xCoord;
        int currY = this.yCoord;
        int collisionAreaWidth = this.solidArea.width;
        int collisionAreaHeight = this.solidArea.height;

        switch (direction) {
            case "up" -> yCoord -= attackArea.height;
            case "down" -> yCoord += attackArea.height;
            case "left" -> xCoord -= attackArea.width;
            case "right" -> xCoord += attackArea.width;
        }

        solidArea.width = attackArea.width;
        solidArea.height = attackArea.height;
        boolean isHit = gamePanel.checker.checkEntityCollision(this, gamePanel.getPlayer());
        //boolean isHit = gamePanel.checker.checkEntity(gamePanel.getPlayer(), this);

        if (isHit) {
            //System.out.println("Player took damage");
            gamePanel.getPlayer().damagePlayer(this);
        }

        xCoord = currX;
        yCoord = currY;
        solidArea.width = collisionAreaWidth;
        solidArea.height = collisionAreaHeight;
    }

    public int getDamagePerHit() {
        return damagePerHit;
    }

    public void setDamagePerHit(int damagePerHit) {
        this.damagePerHit = damagePerHit;
    }

    public void draw(Graphics2D g2, GamePanel gamePanel){
        BufferedImage image = null;

        switch(this.getDirection()) {
            case "up":
                if (this.getSpriteNum() == 1) {
                    image = this.getUp1();
                }
                if (this.getSpriteNum() == 2) {
                    image = this.getUp2();
                }
                if (this.getSpriteNum() == 3) {
                    image = this.getUp3();
                }
                if (this.getSpriteNum() == 4) {
                    image = this.getUp4();
                }
                if (this.getSpriteNum() == 5) {
                    image = this.getUp5();
                }
                if (this.getSpriteNum() == 6 ) {
                    image = this.getUp6();
                }
                g2.drawImage(image, this.getxCoord(), this.getyCoord(), this.getWidth(), this.getHeight(), null);
                break;
            case "down":
                if (this.getSpriteNum() == 1) {
                    image = this.getDown1();
                }
                if (this.getSpriteNum() == 2) {
                    image = this.getDown2();
                }
                if (this.getSpriteNum() == 3) {
                    image = this.getDown3();
                }
                if (this.getSpriteNum() == 4) {
                    image = this.getDown4();
                }
                if (this.getSpriteNum() == 5) {
                    image = this.getDown5();
                }
                if (this.getSpriteNum() == 6) {
                    image = this.getDown6();
                }
                g2.drawImage(image, this.getxCoord(), this.getyCoord(), this.getWidth(), this.getHeight(), null);
                break;
            case "left":
                if (this.getSpriteNum() == 1) {
                    image = this.getLeft1();
                }
                if (this.getSpriteNum() == 2) {
                    image = this.getLeft2();
                }
                if (this.getSpriteNum() == 3) {
                    image = this.getLeft3();
                }
                if (this.getSpriteNum() == 4) {
                    image = this.getLeft4();
                }
                if (this.getSpriteNum() == 5) {
                    image = this.getLeft5();
                }
                if (this.getSpriteNum() == 6) {
                    image = this.getLeft6();
                }
                g2.drawImage(image, this.getxCoord()+this.getWidth(), this.getyCoord(), -this.getWidth(), this.getHeight(), null);
                break;
            case "right":
                if (this.getSpriteNum() == 1) {
                    image = this.getRight1();
                }
                if (this.getSpriteNum() == 2) {
                    image = this.getRight2();
                }
                if (this.getSpriteNum() == 3) {
                    image = this.getRight3();
                }
                if (this.getSpriteNum() == 4) {
                    image = this.getRight4();
                }
                if (this.getSpriteNum() == 5) {
                    image = this.getRight5();
                }
                if (this.getSpriteNum() == 6) {
                    image = this.getRight6();
                }
                g2.drawImage(image, this.getxCoord(), this.getyCoord(), this.getWidth(), this.getHeight(), null);
                break;
        }

    }
}
