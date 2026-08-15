package entity;

import java.awt.image.BufferedImage;

public class Entity // base class for all player characters, npcs, monsters, etc
{
    public int x;
    public int y;
    public int speed;

    public BufferedImage up1, up2, up3, down1, down2, down3, left1, left2, left3;
    public String direction;    // string is always capitalized

    public int spriteCounter = 0;
    public int spriteNum = 1;
}
