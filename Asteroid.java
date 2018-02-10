package net.mrpaul.md110.ps11.asteroids;

import java.awt.Graphics;
import java.util.Random;


public class Asteroid extends Polygon{
	int[] xArr = new int[4];
	int[] yArr = new int[4];
	Random gen = new Random();

	double step = gen.nextDouble();

	public Asteroid(Point[] inShape, Point inPosition, double inRotation) {
		super(inShape, inPosition, inRotation);

	}


	public void paint(Graphics brush) {


		for (int i = 0;i < this.getPoints().length;i++) {
			xArr[i] = (int) this.getPoints()[i].getX();
			yArr[i] = (int) this.getPoints()[i].getY();
		}
		brush.drawPolygon(xArr, yArr, 4);

	}

	public void move() {

		this.position.x += step*Math.cos(Math.toRadians(rotation)) ;
		this.position.y += step*Math.sin(Math.toRadians(rotation)) ;




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




}
