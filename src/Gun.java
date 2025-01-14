import processing.core.PApplet;

public class Gun {
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
        if (xGun < 1075) { //so it dosent go off the map
            xGun += 15;
        }
    }

    public void moveLeft() {
        if (xGun > 0) { // so it dosent go off the map
            xGun -= 15;
        }
    }
    public int getX(){
        return xGun; // give x cordanate for colison checks
    }
    public int getY(){
        return 650; // y never changes so return the same value
    }
    
}
