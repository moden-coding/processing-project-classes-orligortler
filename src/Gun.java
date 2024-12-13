import processing.core.PApplet;

public class Gun {
    private int gunSpeed;
    private PApplet canvas;
    private int xGun;

    public Gun(PApplet c) {
        xGun = 550; // Initial position
        canvas = c;
    }

    public void display() {
        canvas.fill(120, 30, 200);
        canvas.rect(xGun, 650, 25, 50); // Gun dimensions and position
    }

    public void moveRight() {
        if (xGun < 1075) {
            xGun += 25;
        }
    }

    public void moveLeft() {
        if (xGun > 0) {
            xGun -= 15;
        }
    }
    public int getX(){
        return xGun;
    }
    public int getY(){
        return 650;
    }
    
}
