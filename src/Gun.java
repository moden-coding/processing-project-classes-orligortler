import processing.core.PApplet;

public class Gun {
    private int gunSpeed;
    private PApplet canvas;
    private int xGun;

    public Gun(PApplet c) {
        xGun = 550; // Initial position
        canvas = c;
    }

    public void update() {
        xGun += gunSpeed; // Move the gun horizontally
    }

    public void display() {
        canvas.fill(120, 30, 200);
        canvas.rect(xGun, 650, 25, 50); // Gun dimensions and position
    }

    public void moveRight() {
        gunSpeed = 10;
    }

    public void moveLeft() {
        gunSpeed = -10;
    }

    public void stop() {
        gunSpeed = 0; // Stop gun movement
    }
}
