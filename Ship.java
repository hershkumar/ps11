package net.mrpaul.md110.ps11.asteroids;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Ship extends Polygon implements KeyListener{
	int[] xArr = new int[4];
	int[] yArr = new int[4];


	boolean left = false;
	boolean right = false;
	boolean forwards = false;
	double currentSpeed= .1;
	public Point accel = new Point(0.0, 0.0);
	double friction= .15;

	final int MAX_ACCEL = 15;


	public Ship(Point[] inShape, Point inPosition, double inRotation) {
		super(inShape, inPosition, inRotation);
	}
	public void paint(Graphics brush) {


		for (int i = 0;i < this.getPoints().length;i++) {
			xArr[i] = (int) this.getPoints()[i].getX();
			yArr[i] = (int) this.getPoints()[i].getY();

		}
		brush.drawPolygon(xArr, yArr,4);
		brush.setColor(Color.green);
		if (Asteroids.numLives == 2){
			brush.setColor(Color.ORANGE);
		}
		else if (Asteroids.numLives == 1|| Asteroids.numLives == 0){
			brush.setColor(Color.red);
		}


		brush.fillPolygon(xArr,yArr,4);


	}

	public void move() {

		if (left) {
			this.rotate(-3);
		}
		if (right) {
			this.rotate(3);
		}
		if (forwards) {
			accelerate(currentSpeed);
		}
		else {
			//decelerate
			if (accel.x >= friction || accel.x <= -friction) {
				double ax = Math.abs(friction * Math.cos(Math.toRadians(rotation)));
				if (ax > 0) {
					if (accel.x > 0) ax = -ax;
					else {}
				} else {
					if (accel.x > 0) ax = -ax;
					else {}
				}
				accel.x += ax;
			} else {
				accel.x = 0;
			}

			if (accel.y >= friction || accel.y <= -friction) {
				double ay = Math.abs(friction * Math.sin(Math.toRadians(rotation)));
				if (ay > 0) {
					if (accel.y > 0) ay = -ay;
					else {}
				} else {
					if (accel.y > 0) ay = -ay;
					else {}
				}
				accel.y += ay;
			} else {
				accel.y = 0;
			}
		}
		position.setX((position.getX() + accel.x) % Asteroids.width); //move
		position.setY((position.getY() + accel.y) % Asteroids.height);



		accel.x = Math.min(Math.max(accel.x, -MAX_ACCEL), MAX_ACCEL); //maximum speed
		accel.y = Math.min(Math.max(accel.y, -MAX_ACCEL), MAX_ACCEL);

		if (this.position.x > Asteroids.width ) {
			this.position.x = 0;
		}
		if (this.position.x<0) {
			this.position.x = Asteroids.width;
		}

		if (this.position.y > Asteroids.height) {
			this.position.y = 0;

		}
		if (this.position.y<0) {
			this.position.y = Asteroids.height;
		}

	}
	public void accelerate(double acceleration) {
		accel.x += (acceleration * Math.cos(Math.toRadians(rotation)));
		accel.y += (acceleration * Math.sin(Math.toRadians(rotation)));
	}

	public void die() {
		Asteroids.numLives--;
		this.position = new Point(Asteroids.width/2,Asteroids.height/2);
		friction -= .05;
		if (Asteroids.numLives==0) {
			this.deathAnimation();
		}
	}



	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (Asteroids.numLives>0) {
			if (key == KeyEvent.VK_SPACE){
				this.shoot();
			}
			if (key == KeyEvent.VK_UP){
				forwards = true;
			}
			if (key == KeyEvent.VK_LEFT){
				left = true;
			}
			if (key == KeyEvent.VK_RIGHT){
				right = true;
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (Asteroids.numLives>0) {
			if (key == KeyEvent.VK_UP){
				forwards = false;
			}
			if (key == KeyEvent.VK_LEFT){
				left = false;
			}
			if (key == KeyEvent.VK_RIGHT){
				right = false;
			}
		}
	}

	public void deathAnimation() {
		forwards = false;
		left = true;
		right = false;
	}

	public void shoot() {
		if (Asteroids.listBull.size() < 5) {
			Point point = new Point(this.position.getX(),this.position.getY());
			double rotation = this.rotation;
			Bullet bull = new Bullet(Asteroids.bullShape,point,rotation);
			Asteroids.listBull.add(bull);
			Asteroids.listBullSize++;
		}
		
		
	}

}

