import processing.core.PApplet;

public class Worm {
    private int speed;
    private int size;
    private int color;
    private PApplet canvas;
    private int xWorm;
    private int yWorm;

    public Worm(int xPos, int yPos, PApplet c, int speeeed) {
        xWorm = xPos;
        yWorm = yPos;
        size = 50;
        canvas = c;
        speed = speeeed;
        color = canvas.color(0, 255, 0);
    }

    // the worm bubbles
    public void display() {
        canvas.fill(color);
        canvas.ellipse(xWorm, yWorm, size, size);
    }

    // what to do as time passes or if you hit anything
    public void update() {
        // Check for wall collisions
        if (xWorm + size / 2 > canvas.width || xWorm - size / 2 < 0) {
            if (yWorm > 50 || xWorm - size / 2 > 0) {
                hitWall();
            }

        }
        xWorm += speed; // Move the worm horizontally
    }

    // I just though I would do multipe types of hitting
    public void hitAnything() {
        hitWall();
    }

    public void hitWall() {
        speed = -speed; // Reverse direction
        yWorm += 50; // Move down 100 pixels

    }

    // return the x and y position to checkl fpr colisions
    public int getX() {
        return xWorm;
    }

    public int getY() {
        return yWorm;
    }

    // checks contact between mushroom and worm
    public boolean contactWM(Mushroom m) {
        float distance = PApplet.dist(m.getX(), m.getY(), this.getX(), this.getY());
        if (distance < 50) {
            return true;
        }
        return false;
    }

    // chekcs contact between bullet and worm
    public boolean contactWB(Bullet b) {
        float distance = PApplet.dist(this.getX(), this.getY(), b.getX(), b.getY());
        if (distance < 25) {
            return true;
        }
        return false;
    }
}
