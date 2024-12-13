import processing.core.PApplet;

public class Mushroom {
    private int xShroom;
    private int yShroom;
    private PApplet canvas;
    // private int health;

    public Mushroom(int xPos, int yPos, PApplet c) {
        xShroom = xPos;
        yShroom = yPos;
        canvas = c;
        }
    public void display() {
        canvas.fill(255);
        canvas.rect(xShroom, yShroom, 50 ,50);
    }

    public int getX(){
        return xShroom + 25;
    }
    public int getY(){
        return yShroom + 25;
    }

}

