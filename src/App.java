import java.util.ArrayList;
import processing.core.PApplet;

public class App extends PApplet {
    ArrayList<Worm> worms;

    public static void main(String[] args) {
        PApplet.main("App");
    }

    public void settings() {
        size(1100, 750);
    }

    public void setup() {
        worms = new ArrayList<>(); // Initialize worms ArrayList
        wormMaker(); // Call wormMaker after initializing worms
    }

    public void draw() {
        background(0);
        for (Worm b : worms) {
            b.display();
            b.update();
        }
    }

    public void wormMaker() {
        int x = 55;
        int y = 0;
        Worm worm = new Worm(x, y, this);
        worms.add(worm);
    }
}
