package net.mrpaul.md110.ps11.asteroids;

import java.awt.Graphics;

public class Bullet extends Polygon {
	int step = 10;
	int lifetime = 40;
	int lifeCounter = 0;
	int[] xArr = new int[4];
	int[] yArr = new int[4];
	public Bullet(Point[] inShape, Point inPosition, double inRotation) {
		super(inShape, inPosition, inRotation);	
	}
	
	public void paint(Graphics brush) {
		for (int i = 0;i < this.getPoints().length;i++) {
			xArr[i] = (int) this.getPoints()[i].getX();
			yArr[i] = (int) this.getPoints()[i].getY();
		}
		brush.fillPolygon(xArr, yArr, 4);
		lifeCounter++;
	}

	public void move() {
		this.position.x += step*Math.cos(Math.toRadians(this.rotation));
		this.position.y += step*Math.sin(Math.toRadians(this.rotation));
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
	
	public boolean checkTime() {
		if (lifeCounter<40) {
			return true;
		}
		else {
			return false;
		}
	}
}
