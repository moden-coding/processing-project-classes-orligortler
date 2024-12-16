import processing.core.PApplet;

public class Worm {
    private int speed;
    private int size;
    private int color;
    private PApplet canvas;
    private int xWorm;
    private int yWorm;

    public Worm(int xPos, int yPos, PApplet c) {
        xWorm = xPos;
        yWorm = yPos;
        size = 50;
        canvas = c;
        speed = 15;
        color = canvas.color(0, 255, 0);
    }

    public void display() {
        canvas.fill(color);
        canvas.ellipse(xWorm, yWorm, size, size);
    }

    public void update() {
       
        if (xWorm + size / 2 > canvas.width || xWorm - size / 2 < 0) {
            if (yWorm > 50 || xWorm - size / 2 > 0) {
                hitWall();
            }
            
        }
        // Check for wall collisions
        xWorm += speed; // Move the worm horizontally
    }

    public void hitAnything() {
        hitWall();
    }

    public void hitWall() {
       
           
                speed = -speed; // Reverse direction
                yWorm += 50; // Move down 100 pixels

    }

    public int getX() {
        return xWorm;
    }

    public int getY() {
        return yWorm;
    }
}
