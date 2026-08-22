package main;

import entity.Entity;
import object.SuperObject;

public class CollisionChecker
{

    GamePanel gp;

    public CollisionChecker(GamePanel gp)
    {
        this.gp = gp;
    }

    public void checkTile(Entity entity)
    {
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2; // only need to check two tiles

        switch (entity.direction)
        {
        case "up":
            entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
            tileNum1 = gp.tileM.mapTileNumber[entityLeftCol][entityTopRow];
            tileNum2 = gp.tileM.mapTileNumber[entityRightCol][entityTopRow];
            if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true)
            {
                entity.collisionOn = true;
            }
            break;
        case "down":
            entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
            tileNum1 = gp.tileM.mapTileNumber[entityLeftCol][entityBottomRow];
            tileNum2 = gp.tileM.mapTileNumber[entityRightCol][entityBottomRow];
            if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true)
            {
                entity.collisionOn = true;
            }
            break;
        case "left":
            entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
            tileNum1 = gp.tileM.mapTileNumber[entityLeftCol][entityTopRow];
            tileNum2 = gp.tileM.mapTileNumber[entityLeftCol][entityBottomRow];
            if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true)
            {
                entity.collisionOn = true;
            }
            break;
        case "right":
            entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
            tileNum1 = gp.tileM.mapTileNumber[entityRightCol][entityTopRow];
            tileNum2 = gp.tileM.mapTileNumber[entityRightCol][entityBottomRow];
            if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true)
            {
                entity.collisionOn = true;
            }
            break;
        }
    }

    public int checkObject(Entity entity, boolean player)
    {
        // check if entity is player
        int index = -1;

        for (int i = 0; i < gp.obj.length; i++)
        {
            // cache SuperObject bc im smart
            SuperObject obj = gp.obj[i];
            if (obj == null)
            {
                continue;
            }

            // get entity's solid area position
            entity.solidArea.x = entity.worldX + entity.solidArea.x;
            entity.solidArea.y = entity.worldY + entity.solidArea.y;
            // get object's solid area position
            obj.solidArea.x = obj.worldX + obj.solidArea.x;
            obj.solidArea.y = obj.worldY + obj.solidArea.y;

            switch (entity.direction)
            {
            case "up":
                entity.solidArea.y -= entity.speed;
                if (entity.solidArea.intersects(obj.solidArea))
                {
                    if (obj.collision == true)
                    {
                        entity.collisionOn = true;
                    }
                    if (player == true)
                    {
                        index = i;
                    }
                    // System.out.println("up collision");
                }
                break;
            case "down":
                entity.solidArea.y += entity.speed;
                if (entity.solidArea.intersects(obj.solidArea))
                {
                    if (obj.collision == true)
                    {
                        entity.collisionOn = true;
                    }
                    if (player == true)
                    {
                        index = i;
                    }
                    // System.out.println("down collision");
                }
                break;
            case "left":
                entity.solidArea.x -= entity.speed;
                if (entity.solidArea.intersects(obj.solidArea))
                {
                    if (obj.collision == true)
                    {
                        entity.collisionOn = true;
                    }
                    if (player == true)
                    {
                        index = i;
                    }
                    // System.out.println("left collision");
                }
                break;
            case "right":
                entity.solidArea.x += entity.speed;
                if (entity.solidArea.intersects(obj.solidArea))
                {
                    if (obj.collision == true)
                    {
                        entity.collisionOn = true;
                    }
                    if (player == true)
                    {
                        index = i;
                    }
                    // System.out.println("right collision");
                }
                break;
            default:
                throw new AssertionError();
            }
            entity.solidArea.x = entity.solidAreaDefaultX;
            entity.solidArea.y = entity.solidAreaDefaultY;
            obj.solidArea.x = obj.solidAreaDefaultX;
            obj.solidArea.y = obj.solidAreaDefaultY;
        }

        return index;
    }
}
