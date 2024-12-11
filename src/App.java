import java.util.ArrayList;
import processing.core.PApplet;

public class App extends PApplet {
    ArrayList<Worm> worms;
    ArrayList<Bullet> bullets;
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
        bullets =  new ArrayList<>();
        // bullet = new Bullet(420, 620, this);
        // bullet.display();
    }

    public void draw() {
        background(0);
        for (Worm b : worms) {
            b.display();
            b.update();
        }
        for (Bullet shoot : bullets){
            shoot.update();
            shoot.display();
        }
        gun.display();
    }

    public void wormMaker() {
        int x = 50;
        int y = 50;
        Worm worm = new Worm(x, y, this);
        worms.add(worm);
    }

    public void bulletMaker(){
        int x = Gun.getX();
        int y = Gun.getY();
        Bullet bullet = new Bullet(x, y, this);
        bullets.add(bullet);
    }
    

    

    public void keyPressed() {
        if (keyCode == RIGHT) {
            gun.moveRight();
        }
        if (keyCode == LEFT) {
            gun.moveLeft();
        }
        if (keyCode ==  ' ') {
            bulletMaker();
        }
    }
    //made by chatGPT
    // public void keyReleased() {
    //     gun.stop(); // Stop gun movement
    // }
}
