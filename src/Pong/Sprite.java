package Pong;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.awt.*;

public class Sprite {
	private Image image;
	private double posX;
	private double posY;
	private double velX;
	private double velY;
	private double width;
	private double height;

	Sprite(Image image,double x,double y){
		this.image = image;
		this.posX = x;
		this.posY = y;
		this.width = image.getWidth();
		this.height = image.getHeight();
	}

	// Updates the sprites location based on velocity and time.
	void update(double time){
		this.posX += velX * time;
		this.posY += velY * time;
	}

	// Draws the sprite using current co-ordinates.
	void render(GraphicsContext gc){
		gc.drawImage(image,posX,posY);
	}

	// Returns rectangular boundary of sprite used for collision detection.
	private Rectangle2D getBoundary(){
		return new Rectangle2D(posX,posY,width,height);
	}

	// Checks for the intersection of sprite objects.
	boolean intersects(Sprite s){
		return s.getBoundary().intersects(this.getBoundary());
	}

	public void addVelocity(double x,double y){
		this.velX += x;
		this.velY += y;
	}

	double getPosX() {
		return posX;
	}

	double getPosY() {
		return posY;
	}

	double getVelX() {
		return velX;
	}

	double getVelY() {
		return velY;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	void setPosition(double x,double y){
		this.posX = x;
		this.posY = y;
	}

	void setVelocity(double x,double y){
		this.velX = x;
		this.velY = y;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public void setWidth(double width) {
		this.width = width;
	}
}
