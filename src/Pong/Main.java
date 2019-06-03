package Pong;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.ArrayList;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Pong");
		Group root = new Group();
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);

		Canvas canvas = new Canvas(800,640);
		root.getChildren().add(canvas);


		GraphicsContext gc = canvas.getGraphicsContext2D();
		Sprite paddle1 = new Sprite(new Image("Images/Paddle.png",20,75,false,true),100,320);
		Sprite paddle2 = new Sprite(new Image("Images/Paddle.png",20,75,false,true),700,320);

		ArrayList<String> inputP1 = new ArrayList<>();
		ArrayList<String> inputP2 = new ArrayList<>();

		scene.setOnKeyPressed(new EventHandler<>() {
			@Override
			public void handle(KeyEvent keyEvent) {
				String keyPress = keyEvent.getCode().toString();
				if(keyPress.equals("UP") || keyPress.equals("DOWN")){
					if(!inputP1.contains(keyPress))
						inputP1.add(keyPress);
				}else if(keyPress.equals("W") || keyPress.equals("S")){
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

		final long startNanoTime = System.nanoTime();

		new AnimationTimer()
		{
			public void handle(long currentNanoTime)
			{
				gc.drawImage(new Image("Images/Pong.png",800,640,false,true),0,0);
				double t = (currentNanoTime - startNanoTime) / 1000000000.0;
				if(inputP1.contains("UP")){
					paddle1.setVelocity(0,-1);
				}else if(inputP1.contains("DOWN")){
					paddle1.setVelocity(0,1);
				}else{
					paddle1.setVelocity(0,0);
				}

				if(inputP2.contains("W")){
					paddle2.setVelocity(0,-1);
				}else if(inputP2.contains("S")){
					paddle2.setVelocity(0,1);
				}else{
					paddle2.setVelocity(0,0);
				}

				paddle1.update(t);
				paddle1.render(gc);
				paddle2.update(t);
				paddle2.render(gc);

			}
		}.start();

		primaryStage.show();
	}

	public static void main(String [] args){
		launch(args);
	}
}
