package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity
{

    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH)
    {
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);
        final int scale = gp.scale;
        solidArea = new Rectangle();

        // hard coded to player sprite, good architecture would be to store this data
        // somewhere
        solidArea.x = 4 * scale;
        solidArea.y = 8 * scale;
        solidArea.width = 8 * scale;
        solidArea.height = 7 * scale;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues()
    {
        worldX = gp.tileSize * 15;
        worldY = gp.tileSize * 10;
        speed = 3;
        direction = "down";
    }

    public void getPlayerImage()
    {
        try
        {
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/character_u_0.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/character_u_1.png"));
            up3 = ImageIO.read(getClass().getResourceAsStream("/player/character_u_2.png"));
            // down1 =
            // ImageIO.read(getClass().getResourceAsStream("/player/character_d_0.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/character_d_0.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/character_d_1.png"));
            down3 = ImageIO.read(getClass().getResourceAsStream("/player/character_d_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/character_l_0.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/character_l_1.png"));
            left3 = ImageIO.read(getClass().getResourceAsStream("/player/character_l_2.png"));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void update()
    {
        boolean movementInput = keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed;

        if (keyH.upPressed == true)
        {
            direction = "up";
        } else if (keyH.downPressed == true)
        {
            direction = "down";
        } else if (keyH.leftPressed == true)
        {
            direction = "left";

        } else if (keyH.rightPressed == true)
        {
            direction = "right";
        }

        // check tile collision
        collisionOn = false;
        gp.cChecker.checkTile(this);

        // check if player has any input before animating
        if (movementInput)
        {
            // if collision is false, player can move
            if (collisionOn == false)
            {
                switch (direction)
                {
                case "up":
                    worldY -= speed;
                    break;
                case "down":
                    worldY += speed;
                    break;
                case "left":
                    worldX -= speed;
                    break;
                case "right":
                    worldX += speed;
                    break;
                }
            }

            // update animation
            spriteCounter++;
            if (spriteCounter > 5) // 5 = 12 fps. maybe should be determined elsewhere but works for now
            {
                spriteNum = ((spriteNum) % 4) + 1;
                // System.out.println(spriteNum);
                spriteCounter = 0;
            }
        } else
        {
            spriteNum = 1;
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D g2)
    {
        // g2.setColor(Color.white);
        // g2.fillRect(x, y, gp.tileSize, gp.tileSize);

        BufferedImage image = null;
        boolean flipX = false;
        switch (direction)
        {
        case "up":
            switch (spriteNum)
            {
            case 1:
                image = up1;
                break;
            case 2:
                image = up2;
                break;
            case 3:
                image = up1;
                break;
            case 4:
                image = up3;
                break;
            }
            break;
        case "down":
            switch (spriteNum)
            {
            case 1:
                image = down1;
                break;
            case 2:
                image = down2;
                break;
            case 3:
                image = down1;
                break;
            case 4:
                image = down3;
                break;
            }
            break;
        case "left":
            switch (spriteNum)
            {
            case 1:
                image = left1;
                break;
            case 2:
                image = left2;
                break;
            case 3:
                image = left1;
                break;
            case 4:
                image = left3;
                break;
            }
            break;
        case "right":
            switch (spriteNum)
            {
            case 1:
                image = left1;
                break;
            case 2:
                image = left2;
                break;
            case 3:
                image = left1;
                break;
            case 4:
                image = left3;
                break;
            }
            // todo: figure out how to flip the sprite
            flipX = true;
            break;
        }
        // surely there's a more elegant solution here, but this compiles
        // we have to account for both flipping the sprite as well as offseting it by
        // the width of the sprite (tileSize)
        int width = 1;
        int xOffset = 0;
        if (flipX)
        {
            width = -1;
            xOffset = gp.tileSize;
        }
        g2.drawImage(image, screenX + xOffset, screenY, gp.tileSize * width, gp.tileSize, null);
    }
}
