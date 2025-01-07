import processing.core.PApplet;

public class Bullet {
    private PApplet canvas;
    private int xbullet;
    private int ybullet;

    public Bullet(int xPos, int yPos, PApplet c) {
        xbullet = xPos;
        ybullet = yPos;
        canvas = c;
    }

    public void update() {
        ybullet -= 10; // speed of bullet
    }

    public void display() {
        canvas.fill(100, 90, 100);
        canvas.rect(xbullet, ybullet, 5, 15); // Gun dimensions and position
    }

    public int getX() {
        return xbullet; // return the x of a bullet
    }

    public int getY() {
        return ybullet; // return the y of a bullet
    }

    // check to see if a bullet hits a mushroom
    public boolean contactMB(Mushroom m) {
        float distance = PApplet.dist(m.getX(), m.getY(), this.getX(), this.getY());
        if (distance < 25) {
            return true;
        }
        return false;
    }

}