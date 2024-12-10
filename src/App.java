import java.util.ArrayList;
import processing.core.PApplet;

public class App extends PApplet {
    ArrayList<Worm> worms;
    Gun gun;

    public static void main(String[] args) {
        PApplet.main("App");
    }

    public void settings() {
        size(1100, 750);
    }

    public void setup() {
        worms = new ArrayList<>(); // Initialize worms ArrayList
        wormMaker(); // Call wormMaker after initializing worms
        gun = new Gun(this);
    }

    public void draw() {
        background(0);
        for (Worm b : worms) {
            b.display();
            b.update();
        }
        gun.display();
        gun.update();
    }

    public void wormMaker() {
        int x = 50;
        int y = 50;
        Worm worm = new Worm(x, y, this);
        worms.add(worm);
    }

    // public void gunMaker(){
    // int x = 550;
    // int y = 650;
    // }

    public void keyPressed() {
        if (key == RIGHT) {
            gun.moveRight();
        }
        if (key == LEFT) {
            gun.moveLeft();
        }
    }
    //made by chatGPT
    public void keyReleased() {
        gun.stop(); // Stop gun movement
    }
}
