package Pong;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.Random;

class GameController {
	private int maxScore;
	private int scoreP1;
	private int scoreP2;
	private Canvas canvas;
	private Scene scene;

	GameController(Canvas canvas, Scene scene){
		this.canvas = canvas;
		this.scene = scene;
		maxScore = 11;
	}

	void play(){
		GraphicsContext gc = canvas.getGraphicsContext2D();
		Sprite paddle1 = new Sprite(new Image("Images/Paddle.png", 20, 75, false, true), 90, 320);
		Sprite paddle2 = new Sprite(new Image("Images/Paddle.png", 20, 75, false, true), 700, 320);
		Sprite ball = new Sprite(new Image("Images/Ball.png", 15, 15, false, true), 400, 340);

		ArrayList<String> inputP1 = new ArrayList<>();
		ArrayList<String> inputP2 = new ArrayList<>();

		scene.setOnKeyPressed(new EventHandler<>() {
			@Override
			public void handle(KeyEvent keyEvent) {
				String keyPress = keyEvent.getCode().toString();
				if(keyPress.equals("UP") || keyPress.equals("DOWN")) {
					if(!inputP1.contains(keyPress))
						inputP1.add(keyPress);
				} else if(keyPress.equals("W") || keyPress.equals("S")) {
					if(!inputP2.contains(keyPress))
						inputP2.add(keyPress);
				}
			}
		});

		scene.setOnKeyReleased(new EventHandler<>() {
			@Override
			public void handle(KeyEvent keyEvent) {
				String keyPress = keyEvent.getCode().toString();
				if(inputP1.contains(keyPress))
					inputP1.remove(keyPress);
				else
					inputP2.remove(keyPress);
			}
		});

		setBallVelocity(ball);

		new AnimationTimer() {
			long lastNanoTime = System.nanoTime();

			public void handle(long currentNanoTime) {
				gc.drawImage(new Image("Images/Pong.png", 800, 640, false, true), 0, 0);
				double t = (currentNanoTime - lastNanoTime) / 1000000000.0;
				lastNanoTime = currentNanoTime;

				// User Inputs
				if(inputP1.contains("UP")) {
					paddle1.setVelocity(0, -150);
				} else if(inputP1.contains("DOWN")) {
					paddle1.setVelocity(0, 150);
				} else {
					paddle1.setVelocity(0, 0);
				}

				if(inputP2.contains("W")) {
					paddle2.setVelocity(0, -150);
				} else if(inputP2.contains("S")) {
					paddle2.setVelocity(0, 150);
				} else {
					paddle2.setVelocity(0, 0);
				}

				// Updating moving sprites
				paddle1.update(t);
				paddle2.update(t);
				ball.update(t);

				// Collision detection
				if(ball.intersects(paddle1)) {
					ball.setVelocity((ball.getVelX() + 5) * -1, ball.getVelY());
				} else if(ball.intersects(paddle2)) {
					ball.setVelocity((ball.getVelX() + 5) * -1, ball.getVelY());
				}else if(ball.getPosX() <  60){ // Ball is passed the paddle of the defending player.
					scoreP2++;
					resetBall(ball);
				}else if(ball.getPosX() > canvas.getWidth() - 20){
					scoreP1++;
					resetBall(ball);
				}else if(ball.getPosY() <= 0) // Hitting the top/bottom borders reflects the ball.
					ball.setVelocity(ball.getVelX(),ball.getVelY() * -1);
				else if(ball.getPosY() >= canvas.getHeight() - 15)
					ball.setVelocity(ball.getVelX(),ball.getVelY() * -1);

				if(scoreP1 == maxScore){
					gc.setStroke(Color.WHITE);
					gc.setFont(new Font("Verdana",50));
					gc.strokeText("Player 1 has won",360,100);
					this.stop();
				}else if(scoreP2 == maxScore){
					gc.setStroke(Color.WHITE);
					gc.setFont(new Font("Verdana",50));
					gc.strokeText("Player 2 has won",360,100);
					this.stop();
				}
				gc.clearRect(0, 0, 800, 640);
				gc.drawImage(new Image("Images/Pong.png", 800, 640, false, true), 0, 0);
				paddle1.render(gc);
				paddle2.render(gc);
				ball.render(gc);

				String scoreText = scoreP1 + " " + scoreP2;
				gc.setStroke(Color.WHITE);
				gc.setFont(new Font("Verdana",50));
				gc.strokeText(scoreText,360,50);

			}
		}.start();
	}

	private void resetBall(Sprite s){
		s.setPosition(400,340);
		setBallVelocity(s);
	}

	private void setBallVelocity(Sprite b){
		double max = 100, min = -100;
		Random rand = new Random();
		int xDir = rand.nextInt(2) + 1;

		if(xDir == 2)
			xDir = -1;

		double yVelocity = 0;
		while(yVelocity < 15 && yVelocity > -15) { // Don't want a straight line
			yVelocity = Math.random() * ((max - min) + 1) + min;
		}

		b.setVelocity(100 * xDir, yVelocity);
	}
}
