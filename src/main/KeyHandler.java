package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener  // Implements = interfaces
{
    public boolean upPressed, downPressed, leftPressed, rightPressed;   // must use "boolean", not "bool"

    @Override
    public void keyTyped(KeyEvent e) 
    {
        // does nothing
    }

    @Override
    public void keyPressed(KeyEvent e) 
    {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W)  // KeyEvent.VK == enum for key inputs?
        {
            upPressed = true;
        }
        if (code == KeyEvent.VK_A)
        {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_S)
        {
            downPressed = true;
        }
        if (code == KeyEvent.VK_D)
        {
            rightPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) 
    {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W)  // KeyEvent.VK == enum for key inputs?
        {
            upPressed = false;
        }
        if (code == KeyEvent.VK_A)
        {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_S)
        {
            downPressed = false;
        }
        if (code == KeyEvent.VK_D)
        {
            rightPressed = false;
        }
    }
}
