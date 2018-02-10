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
