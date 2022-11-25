
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * PlayerCharacter - A class which models a user-controlled character and contains attributes for a Character.
 *
 * @author Cameron Hofbauer
 * @version September 28, 2022
 */

public class PlayerCharacter extends Character {
    private GamePanel gp;
    private KeyHandler keyH;


    public PlayerCharacter(GamePanel gp, KeyHandler keyH) {
        super();
        this.gp = gp;
        this.keyH = keyH;
        this.solidArea.x = 0;
        this.solidArea.y = 10;
        this.setWidth(18);
        this.setHeight(46);
        this.solidArea.width = 9;
        this.solidArea.height = 18;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        this.collisionAreaDefaultX = solidArea.x;
        this.collisionAreaDefaultY = solidArea.y;
        setDefaultValues();
        getPlayerImage();

        this.setHasThrownProjectile(false);

    }

    public PlayerCharacter(PlayerCharacter pc) {
        this.gp = pc.gp;
        this.keyH = pc.keyH;
        this.setName(pc.getName());
        this.setHealth(pc.getHealth());
        this.setMovementSpeed(pc.getMovementSpeed());
        this.setxCoord(pc.getxCoord());
        this.setyCoord(pc.getyCoord());
        this.setActiveEffects(pc.getActiveEffects());
        this.setTimeForInvincibility(pc.getTimeForInvincibility());
        this.setDirection(pc.getDirection());
        this.setSpriteCounter(pc.getSpriteCounter());
        this.setSpriteNum(pc.getSpriteNum());

    }

    public void setDefaultValues() {
        this.setxCoord(50);
        this.setyCoord(200);
        this.setMovementSpeed(4);
        this.setDirection("down");
        this.solidArea = new Rectangle(0, 16, 30, 30);
        this.attackArea.width = 36;
        this.attackArea.height = 36;

    }

    public void getPlayerImage() {
        try {
            this.setUp1(ImageIO.read(getClass().getResourceAsStream("/player_character/archived/up_1.png")));
            this.setUp2(ImageIO.read(getClass().getResourceAsStream("/player_character/archived/up_2.png")));
            this.setDown1(ImageIO.read(getClass().getResourceAsStream("/player_character/archived/down_1.png")));
            this.setDown2(ImageIO.read(getClass().getResourceAsStream("/player_character/archived/down_2.png")));
            this.setLeft1(ImageIO.read(getClass().getResourceAsStream("/player_character/archived/left_1.png")));
            this.setLeft2(ImageIO.read(getClass().getResourceAsStream("/player_character/archived/left_2.png")));
            this.setRight1(ImageIO.read(getClass().getResourceAsStream("/player_character/archived/right_1.png")));
            this.setRight2(ImageIO.read(getClass().getResourceAsStream("/player_character/archived/right_2.png")));

            this.setAttackUp1(ImageIO.read(getClass().getResourceAsStream("/player_character/archived/up_attack_1.png")));
            this.setAttackUp2(ImageIO.read(getClass().getResourceAsStream("/player_character/archived/up_attack_2.png")));
            this.setAttackDown1(ImageIO.read(getClass().getResourceAsStream("/player_character/archived/down_attack_1.png")));
            this.setAttackDown2(ImageIO.read(getClass().getResourceAsStream("/player_character/archived/down_attack_2.png")));
            this.setAttackRight1(ImageIO.read(getClass().getResourceAsStream("/player_character/archived/right_attack_1.png")));
            this.setAttackRight2(ImageIO.read(getClass().getResourceAsStream("/player_character/archived/right_attack_2.png")));
            this.setAttackLeft1(ImageIO.read(getClass().getResourceAsStream("/player_character/archived/left_attack_1.png")));
            this.setAttackLeft2(ImageIO.read(getClass().getResourceAsStream("/player_character/archived/left_attack_2.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        gp.checker.checkRoom(this);
        if (keyH == null) return;

        if(isInvincible){
            invincibleCounter++;
            if(invincibleCounter>30){
                isInvincible = false;
                invincibleCounter = 0;
            }
        }
        if (keyH.kPressed || (keyH.wPressed || keyH.sPressed || keyH.aPressed || keyH.dPressed)) {

            if (keyH.wPressed || keyH.sPressed || keyH.aPressed || keyH.dPressed) {
                collisionOn = false;
                gp.checker.checkTile(this);
                int currentX = this.getxCoord();
                int currentY = this.getyCoord();
                int speed = this.getMovementSpeed();

                if (keyH.wPressed) {
                    this.setDirection("up");
                }
                if (keyH.sPressed && !keyH.wPressed) {
                    this.setDirection("down");
                }
                if (keyH.aPressed && !keyH.dPressed) {
                    this.setDirection("left");
                }
                if (keyH.dPressed && !keyH.aPressed) {
                    this.setDirection("right");
                }

                this.setSpriteCounter(this.getSpriteCounter() + 1);
                if (this.getSpriteCounter() > 12) {
                    if (this.getSpriteNum() == 1) {
                        this.setSpriteNum(2);
                    } else if (this.getSpriteNum() == 2) {
                        this.setSpriteNum(1);
                    }
                    this.setSpriteCounter(0);
                }

                if(!collisionOn){
                    if(direction.equals("up") && currentY > 0){
                        this.setyCoord(currentY - speed);
                    }
                    if(direction.equals("down") && currentY < (gp.screenHeight - this.getHeight())){
                        this.setyCoord(currentY + speed);
                    }
                    if(direction.equals("left") && currentX > 0){
                        this.setxCoord(currentX - speed);
                    }
                    if(direction.equals("right") && currentX < (gp.screenWidth - this.getWidth())){
                        this.setxCoord(currentX + speed);
                    }
                }

                // GamePanel has an arraylist of rooms. We are in the room indicated by the currentRoomNum, which
                // corresponds to the rooms index in the arraylist. Each room has an arraylist of items. Must check if
                // it is null before proceeding.
            }
        }
    }

    public void attacking() {

        int currentWorldX = xCoord;
        int currentWorldY = yCoord;
        int collisionAreaWidth = solidArea.width;
        int collisionAreaHeight = solidArea.height;


        switch (direction) {
            case "up" -> yCoord -= attackArea.height;
            case "down" -> yCoord += attackArea.height;
            case "left" -> xCoord -= attackArea.width;
            case "right" -> xCoord += attackArea.width;
        }

        //After checking collision, restore original data
        xCoord = currentWorldX;
        yCoord = currentWorldY;
        solidArea.width = collisionAreaWidth;
        solidArea.height = collisionAreaHeight;
    }


    public void damagePlayer(NonPlayableCharacter entity) {
        if(!gp.getPlayer().isInvincible){
            //gp.getPlayer().setHealth(gp.getPlayer().getHealth()-gp.enemy.getDamagePerHit());
            gp.getPlayer().damage(entity.getDamagePerHit());
            gp.getPlayer().isInvincible = true;
            //System.out.println(gp.getPlayer().getHealth());     //TODO DEBUG PlayerCharacter Invincibility
        }
    }


    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        if (!isAttacking) {
            this.setWidth(18);
            this.setHeight(46);
            switch (this.getDirection()) {
                case "up":
                    if (this.getSpriteNum() == 1) {
                        image = this.getUp1();
                    }
                    if (this.getSpriteNum() == 2) {
                        image = this.getUp2();
                    }
                    break;
                case "down":
                    if (this.getSpriteNum() == 1) {
                        image = this.getDown1();
                    }
                    if (this.getSpriteNum() == 2) {
                        image = this.getDown2();
                    }
                    break;
                case "left":
                    if (this.getSpriteNum() == 1) {
                        image = this.getLeft1();
                    }
                    if (this.getSpriteNum() == 2) {
                        image = this.getLeft2();
                    }
                    break;
                case "right":
                    if (this.getSpriteNum() == 1) {
                        image = this.getRight1();
                    }
                    if (this.getSpriteNum() == 2) {
                        image = this.getRight2();
                    }
            }
        } else {
            this.setWidth(31);
            this.setHeight(44);
            switch (this.getDirection()) {
                case "up":
                    if (this.getSpriteNum() == 1) {
                        image = this.getAttackUp1();
                    }
                    if (this.getSpriteNum() == 2) {
                        image = this.getAttackUp2();
                    }
                    break;
                case "down":
                    if (this.getSpriteNum() == 1) {
                        image = this.getAttackDown1();
                    }
                    if (this.getSpriteNum() == 2) {
                        image = this.getAttackDown2();
                    }
                    break;
                case "left":
                    if (this.getSpriteNum() == 1) {
                        image = this.getAttackLeft1();
                    }
                    if (this.getSpriteNum() == 2) {
                        image = this.getAttackLeft2();
                    }
                    break;
                case "right":
                    if (this.getSpriteNum() == 1) {
                        image = this.getAttackRight1();
                    }
                    if (this.getSpriteNum() == 2) {
                        image = this.getAttackRight2();
                    }
            }
        }

        g2.drawImage(image, this.getxCoord(), this.getyCoord(), this.getWidth(), this.getHeight(), null);


    }


    public void setGamePanel (GamePanel gp){
        this.gp = gp;
    }

    public void setKeyHandler (KeyHandler keyH){
        this.keyH = keyH;
    }

    @Override
    public boolean equals (Object o){
        if (this.getClass() != o.getClass()) return false;

        PlayerCharacter pc = (PlayerCharacter) o;
        return super.equals(o);
    }

}

