package ecs.sprite;

import ecs.component.Component;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SpriteComponent extends Component
{
    // path to the sprite resource to render
    public String spritePath;

    // x and y pivots to determine the center pixel of the sprite
    public int pivotX;
    public int pivotY;

    // actual image rendered to screen
    BufferedImage image;

    public SpriteComponent(String spritePath)
    {
        pivotX = 0;
        pivotY = 0;

        image = loadImage(spritePath);
    }

    public SpriteComponent(String spritePath, int pivotX, int pivotY)
    {
        this.pivotX = pivotX;
        this.pivotY = pivotY;

        image = loadImage(spritePath);
    }

    public BufferedImage loadImage(String spritePath)
    {
        BufferedImage image = null;
        try
        {
            image = ImageIO.read(getClass().getResourceAsStream(spritePath));

        } catch (IOException e)
        {
        }
        return image;
    }

    public void setSprite(String spritePath)
    {

    }
}
