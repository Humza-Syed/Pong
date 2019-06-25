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
		primaryStage.setResizable(false);

		Canvas canvas = new Canvas(800,640);
		root.getChildren().add(canvas);

		GameController Pong = new GameController(canvas,scene);
		Pong.play();

		primaryStage.show();
	}

	public static void main(String [] args){
		launch(args);
	}
}
