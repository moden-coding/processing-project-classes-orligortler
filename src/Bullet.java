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
        ybullet -=10;
    }

    public void display() {
        canvas.fill(100, 90, 100);
        canvas.rect(xbullet, ybullet, 5, 15); // Gun dimensions and position
    }

    public int getX(){
        return xbullet;
    }
    public int getY(){
        return ybullet;
    }
}