import processing.core.PApplet;

public class Mushroom {
    private int xShroom;
    private int yShroom;
    private PApplet canvas;
    private int health;

    public Mushroom(int xPos, int yPos, PApplet c) {
        xShroom = xPos;
        yShroom = yPos;
        canvas = c;
        this.health = 3;
    }

    // changes in color as the health goes down
    public void display() {
        if (health == 3) {
            canvas.fill(255, 165, 0);
        }
        if (health == 2) {
            canvas.fill(0, 0, 255);
        }
        if (health == 1) {
            canvas.fill(150, 100, 255);
        }
        canvas.rect(xShroom, yShroom, 50, 50);
    }

    public void reduceHealth() {
        health--;
    }

    public int returnHealth() {
        return health;
    }

    // x pos and y pos to check for colisons
    public int getX() {
        return xShroom + 25;
    }

    public int getY() {
        return yShroom + 25;
    }
}
