import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import processing.core.PApplet;

import processing.core.*;

public class App extends PApplet {
    ArrayList<Worm> worms;
    ArrayList<Bullet> bullets;
    ArrayList<Mushroom> mushrooms;
    Gun gun;
    int p = 0;
    int lives = 3;
    int bulletWaittime = 100;
    int lastShotTime = 0;
    int highscore = 0;
    int gameState = 0; // 0 = start screen, 1 = playing, 2 = died
    int buttonX = 300, buttonY = 400, buttonW = 200, buttonH = 80; // Button dimensions

    public static void main(String[] args) {
        PApplet.main("App");
    }

    public void settings() {
        size(1100, 750);
    }

    public void setup() {
        // readHighscore();
        worms = new ArrayList<>(); // Initialize worms ArrayList
        wormMaker(); // Call wormMaker after initializing worms
        gun = new Gun(this);
        bullets = new ArrayList<>();
        mushrooms = new ArrayList<>();
        mushroomMaker();
        // bullet = new Bullet(420, 620, this);
        // bullet.display();
    }

    public void draw() {
        if (gameState == 0) {
            birthScreen();
        } else if (gameState == 1) {
            playScreen();
        } else if (gameState == 2) {
            deathScreen();
        }
    }

    public void playScreen() {
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
        checkforevrything();
        for (Mushroom m : mushrooms) {
            m.display();
        }
        drawHearts();
    }

    public void birthScreen() {
        background(255, 182, 193); // baby pink background
        textAlign(CENTER);
        fill(20);
        textSize(30);
        text("Shoot the worm before it reaches the bottom!", width / 2, 200);
        buttonX = (width - buttonW) / 2;
        buttonY = (height - buttonH) / 2;
        // Border on start button
        stroke(204, 0, 102);
        strokeWeight(9);
        rect(buttonX, buttonY, buttonW, buttonH);
        // Draw the start button
        noStroke();
        fill(255, 105, 180);
        rect(buttonX, buttonY, buttonW, buttonH);
        fill(255);
        textSize(42);
        text("Start Game", buttonX + buttonW / 2, buttonY + buttonH / 2 + 10);
    }

    public void deathScreen() {
        println("You died ");
        fill(255, 0, 0);
        textSize(52);
        text("You Died", width / 2, height / 2);
        textSize(40);
        text("press enter to restart", width / 2, height / 2 + 80);
    }

    public void checkforevrything() {
        removeBullets();
        checkForMWHit();
        checkForWBHits();
        checkForMBHits();
        checkForDeath();
        checkForEmptyWorm();
    }

    public void wormMaker() {
        for (int i = 0; i < 10; i++) {
            int x = (50 - p);
            int y = (50);
            Worm worm = new Worm(x, y, this);
            worms.add(worm);
            p += 50;
        }
    }

    public void checkForEmptyWorm() {
        if (worms.size() == 0) {
            wormMaker();
            highscore += 5;
        }
    }

    public void mushroomMaker() {
        for (int i = 0; i < 8; i++) {
            int xShroom = (int) random(20) * 50 + 25;
            int yShroom = (int) random(11) * 50 + 25;
            Mushroom mushroom = new Mushroom(xShroom, yShroom, this);
            mushrooms.add(mushroom);
        }
    }

    public void bulletMaker() {
        int x = gun.getX() + 10;
        int y = gun.getY();
        Bullet bullet = new Bullet(x, y, this);
        bullets.add(bullet);
    }

    public void checkForWBHits() {
        for (int i = 0; i < bullets.size(); i++) {
            Bullet b = bullets.get(i);
            for (int j = 0; j < worms.size(); j++) {
                Worm w = worms.get(j);
                if (contactWB(w, b)) {
                    Mushroom mushroom = new Mushroom(w.getX() - 25, w.getY() - 25, this);
                    mushrooms.add(mushroom);
                    worms.remove(w);
                    bullets.remove(b);
                    highscore += 1;
                }
            }
        }
    }

    public void checkForMBHits() {
        for (int i = 0; i < bullets.size(); i++) {
            Bullet b = bullets.get(i);
            for (int j = 0; j < mushrooms.size(); j++) {
                Mushroom m = mushrooms.get(j);
                if (contactMB(m, b)) {
                    mushrooms.remove(m);
                    bullets.remove(b);

                }
            }
        }
    }

    public void checkForMWHit() {
        // System.out.println("check mushroom");
        for (int i = 0; i < mushrooms.size(); i++) {
            Mushroom m = mushrooms.get(i);
            for (int j = 0; j < worms.size(); j++) {
                Worm w = worms.get(j);
                if (contactWM(w, m)) {
                    w.hitAnything();
                }
            }
        }
    }

    public void checkForDeath() {
        for (int i = 0; i < worms.size(); i++) {
            Worm w = worms.get(i);
            if (w.getY() > 600) {
                lives -= 1;
                worms.clear();
                wormMaker();
                if (lives == 0) {
                    gameState = 2;
                }
                return;
            }
        }
    }

    public boolean contactWM(Worm w, Mushroom m) {
        float distance = this.dist(m.getX(), m.getY(), w.getX(), w.getY());
        if (distance < 50) {
            return true;
        }
        return false;
    }

    public boolean contactWB(Worm w, Bullet b) {
        float distance = this.dist(w.getX(), w.getY(), b.getX(), b.getY());
        if (distance < 25) {
            return true;
        }
        return false;
    }

    public boolean contactMB(Mushroom m, Bullet b) {
        float distance = this.dist(m.getX(), m.getY(), b.getX(), b.getY());
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

    public void drawHearts() {
        int xHeartPlacmet = 900;
        for (int i = 0; i < lives; i++) {
            drawHeart(xHeartPlacmet, 20, 35);
            xHeartPlacmet += 50;
        }
    }

    // this was made by chatgpt im my last game for the shpae of the heart
    public void drawHeart(float heartX, float heartY, float size) {
        noStroke();
        float radius = size / 2;
        fill(255, 0, 0);
        arc(heartX - radius / 2, heartY, radius, radius, PI, TWO_PI);
        arc(heartX + radius / 2, heartY, radius, radius, PI, TWO_PI);
        triangle(heartX - radius, heartY, heartX + radius, heartY, heartX, heartY + radius * 1.5f);
    }

    public void keyPressed() {
        if (keyCode == RIGHT) {
            gun.moveRight();
        }
        if (keyCode == LEFT) {
            gun.moveLeft();
        }
        if (keyCode == ENTER) {
            gameState = 0;
            lives = 3;
        }
        if (keyCode == ' ') {
            int currentTime = millis();
            if (currentTime - lastShotTime >= bulletWaittime) {
                bulletMaker();
                lastShotTime = currentTime;
            }

        }
    }

    public void mousePressed() {
        if (gameState == 0) {
            // Check if the mouse is over the play button
            if (mouseX > buttonX && mouseX < buttonX + buttonW && mouseY > buttonY && mouseY < buttonY + buttonH) {
                gameState = 1;

            }
        }

    }
}

//     public void readHighscore() {
//         try (Scanner scanner = new Scanner(Paths.get("highscore.txt"))) {

//             // we read the file until all lines have been read
//             while (scanner.hasNextLine()) {
//                 // we read one line
//                 int row = scanner.nextLine();
//                 // we print the line that we read
//             highscore = int.valueOf(row);
//             }
//         } catch (Exception e) {
//             System.out.println("Error: " + e.getMessage());
//         }

//     }

// }