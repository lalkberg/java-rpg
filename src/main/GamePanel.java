package main;

import entity.Player;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{  // Extends = inherits, implements = interfaces

    // screen settings
    final int originalTileSize = 16; // 16x16 tile size, default size of everything
    final int scale = 3; // 3x scale on entire screen

    public final int tileSize = originalTileSize * scale; // 48x48 tile

    // 4:3 resolution
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol;    // 768px
    final int screenHeight = tileSize * maxScreenRow;   // 576px'

    // FPS
    int FPS = 60;
    
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;  // Thread class is used to repeat logic, like carrying a game loop
    Player player = new Player(this, keyH);

    public GamePanel()
    {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread()
    {
        // pass ourself into the thread
        gameThread = new Thread(this);
        gameThread.start();
    }

    /* 
    @Override   // @Override is like override void
    public void run() 
    {
        double drawInterval = 1000000000 / FPS; // 0.0166666... seconds
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null)
        {
            // Sleep method of game loop

            // game loop goes here
            System.out.println("Game is running."); // System.out.println = print line method

            // 1. update: update information such as character position
            update();

            // 2. draw: draw the screen with the updated information
            repaint();  // kind of confusing but this is how paintComponent is called

            
            try 
            {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;    // sleep takes in milliseconds, need to divide by 1 million

                if (remainingTime < 0) 
                {
                    remainingTime = 0;
                }

                Thread.sleep((long)remainingTime);

                nextDrawTime += drawInterval;
            } catch (InterruptedException e) 
            {
                e.printStackTrace();
            }
        }
    }
    */

    // run with delta time instead of sleep, prefer this one
    @Override   // @Override is like override void
    public void run() 
    {
        double drawInterval = 1000000000 / FPS; // 0.0166666... seconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        // int drawCount = 0;

        while (gameThread != null)
        {
            // delta/accumulator method of game loop
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            if (delta >= 1) 
            {
                                // game loop goes here
                // System.out.println("Game is running."); // System.out.println = print line method

                // 1. update: update information such as character position
                update();

                // 2. draw: draw the screen with the updated information
                repaint();  // kind of confusing but this is how paintComponent is called
                delta--;
                // drawCount++;

            }

            if (timer >= 1000000000)
            {
                // System.out.println("FPS: " + drawCount);
                // drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update()
    {
        player.update();
    }

    @Override
    public void paintComponent(Graphics g)    // built in java function
    {
        super.paintComponent(g);

        // cast Graphics to Graphics2D to get 2D control
        Graphics2D g2 = (Graphics2D)g;

        player.draw(g2);

        g2.dispose();   // good practice to dispose, this isn't C# baby
    }
}
