import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import processing.core.PApplet;

import processing.core.*;
import java.io.PrintWriter;
import java.io.IOException;

public class App extends PApplet {
    ArrayList<Worm> worms;
    ArrayList<Bullet> bullets;
    ArrayList<Mushroom> mushrooms;
    Gun gun;
    int p = 0;
    int lives = 3;
    int speed = 5;
    int bulletWaittime = 200; // for spacing out the bullets by 0.2 seconds
    int lastShotTime = 0;
    int highscore = 0;
    int score = 0;
    int gameState = 0; // 0 = start screen, 1 = playing, 2 = died
    int buttonX = 300, buttonY = 400, buttonW = 200, buttonH = 80; // Button dimensions
    boolean right = false;
    boolean left = false;
    boolean space = false;

    public static void main(String[] args) {
        PApplet.main("App");
    }

    public void settings() {
        size(1100, 750);
    }

    public void setup() {
        readHighscore();
        worms = new ArrayList<>(); // Initialize worms ArrayList
        wormMaker(); // Call wormMaker after initializing worms
        gun = new Gun(this);
        bullets = new ArrayList<>();
        mushrooms = new ArrayList<>();
        mushroomMaker();
    }

    // the three options for screens
    public void draw() {
        if (gameState == 0) {
            birthScreen();
        } else if (gameState == 1) {
            playScreen();
        } else if (gameState == 2) {
            deathScreen();
        }
    }

    // gameplaying screen
    public void playScreen() {
        background(0);
        if (right == true) {
            gun.moveRight();
        }
        if (left == true) {
            gun.moveLeft();
        }
        if (space == true) {
            shootBulets();
        }
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
        text("Score is " + score, 750, 50);
    }

    // the home screen that tels you highscore and instructions
    public void birthScreen() {
        background(255, 182, 193); // baby pink background
        textAlign(CENTER);
        fill(20);
        textSize(50);
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
        score = 0;
        text("Highscore:  " + highscore, width / 2, 600);
    }

    // whats is shown after you die
    public void deathScreen() {
        println("You died ");
        fill(255, 0, 0);
        textSize(52);
        text("You Died", width / 2, height / 2);
        textSize(40);
        text("press enter to restart", width / 2, height / 2 + 80);
        if (score > highscore) {
            text("You got a new highscore!", width / 2, 100);
        }
        setHighScore();
    }

    // all the checks in one place
    public void checkforevrything() {
        removeBullets();
        checkForMWHit();
        checkForWBHits();
        checkForMBHits();
        checkForDeath();
        checkForEmptyWorm();
    }

    // spawns in 10 circles for the worm spaceing by 50 so they touch perfectly
    public void wormMaker() {
        p = 0;
        for (int i = 0; i < 10; i++) {
            int x = (50 - p);
            int y = (50);
            Worm worm = new Worm(x, y, this, speed);
            worms.add(worm);
            p += 50;
        }
    }

    // if the worm is empty increase the speed and score
    public void checkForEmptyWorm() {
        if (worms.size() == 0) {
            speed += 2;
            wormMaker();
            score += 5;

        }
    }

    // makes 8 mushroom at random placments that are multiples of 25 so its on a
    // grid.
    public void mushroomMaker() {
        for (int i = 0; i < 8; i++) {
            int xShroom = (int) random(20) * 50 + 25;
            int yShroom = (int) random(11) * 50 + 25;
            Mushroom mushroom = new Mushroom(xShroom, yShroom, this);
            mushrooms.add(mushroom);
        }
    }

    // spans the bullets
    public void bulletMaker() {
        int x = gun.getX() + 10;
        int y = gun.getY();
        Bullet bullet = new Bullet(x, y, this);
        bullets.add(bullet);
    }

    // cheks bullets hit worm and if yes removes them bolth and updates score
    public void checkForWBHits() {
        for (int i = 0; i < bullets.size(); i++) {
            Bullet b = bullets.get(i);
            for (int j = 0; j < worms.size(); j++) {
                Worm w = worms.get(j);
                if (w.contactWB(b)) {
                    Mushroom mushroom = new Mushroom(w.getX() - 25, w.getY() - 25, this);
                    mushrooms.add(mushroom);
                    worms.remove(w);
                    bullets.remove(b);
                    score += 1;
                }
            }
        }
    }

    // cheks it bullegts hit mushroom and than removes them bolth if yes
    public void checkForMBHits() {
        for (int i = 0; i < bullets.size(); i++) {
            Bullet b = bullets.get(i);
            for (int j = 0; j < mushrooms.size(); j++) {
                Mushroom m = mushrooms.get(j);
                if (b.contactMB(m)) {
                    m.reduceHealth();
                    if (m.returnHealth() == 0) {
                        mushrooms.remove(m);
                        bullets.remove(b);
                    }
                    bullets.remove(b);

                }
            }
        }
    }

    // goes through list checking for mushroom worm colisions
    public void checkForMWHit() {
        // System.out.println("check mushroom");
        for (int i = 0; i < mushrooms.size(); i++) {
            Mushroom m = mushrooms.get(i);
            for (int j = 0; j < worms.size(); j++) {
                Worm w = worms.get(j);
                if (w.contactWM(m)) {
                    w.hitAnything();
                }
            }
        }
    }

    // if the wom goes below 600 a life gets deleted
    public void checkForDeath() {
        for (int i = 0; i < worms.size(); i++) {
            Worm w = worms.get(i);
            if (w.getY() > 600) {
                lives -= 1;

                if (lives <= 0) {
                    gameState = 2;
                } else {
                    worms.clear();
                    wormMaker();
                }
                return;
            }
        }
    }

    // when the bullets go off screen they get deleted instead of being in the void
    public void removeBullets() {
        for (int i = 0; i < bullets.size(); i++) {
            Bullet b = bullets.get(i);
            if (b.getY() < 0) {
                bullets.remove(b);
            }
        }
    }

    // made by me from my last game where it goes through
    // all the lives left and prints a heart moves over by 50 pixles
    public void drawHearts() {
        int xHeartPlacmet = 900;
        for (int i = 0; i < lives; i++) {
            drawHeart(xHeartPlacmet, 20, 35);
            xHeartPlacmet += 50;
        }
    }

    // this was made by chatgpt im my last game for the shape of the heart
    public void drawHeart(float heartX, float heartY, float size) {
        noStroke();
        float radius = size / 2;
        fill(255, 0, 0);
        arc(heartX - radius / 2, heartY, radius, radius, PI, TWO_PI);
        arc(heartX + radius / 2, heartY, radius, radius, PI, TWO_PI);
        triangle(heartX - radius, heartY, heartX + radius, heartY, heartX, heartY + radius * 1.5f);
    }

    // moving for keys and other things
    public void keyPressed() {
        if (right == false && keyCode == RIGHT) {
            System.out.println("right");
            right = true;
        }
        if (left == false && keyCode == LEFT) {
            System.out.println("left");
            left = true;
        }
        if (keyCode == ENTER) {
            reset();
        }
        if (space == false && keyCode == ' ') {
            space = true;

        }
    }

    // checks keys for being released so the gun stops moving or bullets stop
    // this makes it smother and so that bolth the bullets and gum move
    // simultaneously
    public void keyReleased() {
        if (keyCode == RIGHT) {
            right = false;
        }
        if (keyCode == LEFT) {
            left = false;
        }
        if (keyCode == ' ') {
            space = false;
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

    // resets the speed of worm and lives and rearanges mushrooms
    public void reset() {
        gameState = 0;
        lives = 3;
        speed = 5;
        mushrooms = new ArrayList<>();
        mushroomMaker();
    }

    // bullets that are spaced out by time
    public void shootBulets() {
        System.out.println("shoot");
        int currentTime = millis();
        if (currentTime - lastShotTime >= bulletWaittime) {
            bulletMaker();
            lastShotTime = currentTime;
        }
    }

    // if we get a new highscore it is added to the file
    public void setHighScore() {
        if (score > highscore) {
            highscore = score;

            String filePath = "highscore.txt"; // Path to the text file

            try (PrintWriter writer = new PrintWriter(filePath)) {
                writer.println(highscore); // Writes the integer to the file
                writer.close(); // Closes the writer and saves the file
                System.out.println("Integer saved to file successfully.");
            } catch (IOException e) {
                System.out.println("An error occurred while writing to the file.");
                e.printStackTrace();
            }
        }
    }

    // reads the highscore from our file
    public void readHighscore() {
        try (Scanner scanner = new Scanner(Paths.get("highscore.txt"))) {
            while (scanner.hasNextLine()) {
                String row = scanner.nextLine();
                highscore = Integer.valueOf(row);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}