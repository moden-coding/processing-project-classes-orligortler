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
        bullets = new ArrayList<>();
        // bullet = new Bullet(420, 620, this);
        // bullet.display();
    }

    public void draw() {
        background(0);
        for (Worm b : worms) {
            b.display();
            b.update();
        }
        for (Bullet sshoot : bullets) {
            sshoot.update();
            sshoot.display();
        }
        gun.display();
        removeBullets();
        checkForHits();
    }

    public void wormMaker() {
        int x = 50;
        int y = 50;
        Worm worm = new Worm(x, y, this);
        worms.add(worm);
    }

    public void bulletMaker() {
        int x = gun.getX();
        int y = gun.getY();
        Bullet bullet = new Bullet(x, y, this);
        bullets.add(bullet);
    }

    public void checkForHits() {
        for (int i = 0; i < bullets.size(); i++) {
            Bullet b = bullets.get(i);
            for (int j = 0; j < worms.size(); j++) {
                Worm w = worms.get(j);
                if (contact(w, b)) {
                    worms.remove(w);
                    bullets.remove(b);
                }

            }
        }
    }

    public boolean contact(Worm w, Bullet b){
       float distance = this.dist(w.getX(), w.getY(), b.getX(), b.getY());
        if (distance < 25) {
            return true;
        } 
        return false;
    }

    public void removeBullets() {
        for (int i = 0; i < bullets.size(); i++) {
            Bullet b = bullets.get(i);
            if (b.getY() < 0) {
                bullets.remove(b);
            }
        }
    }

    public void keyPressed() {
        if (keyCode == RIGHT) {
            gun.moveRight();
        }
        if (keyCode == LEFT) {
            gun.moveLeft();
        }
        if (keyCode == ' ') {
            bulletMaker();
        }
    }
}
