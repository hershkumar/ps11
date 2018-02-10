package net.mrpaul.md110.ps11.asteroids;

/*
CLASS: Asteroids
DESCRIPTION: Extending Game, Asteroids is all in the paint method.
NOTE: This class is the metaphorical "main method" of your program,
      it is your control center.

 */
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Random;
class Asteroids extends Game {
	static int counter = 0;
	static int width = 800;
	static int height = 600;
	Random gen = new Random();
	Point start;
	static int numLives= 3;

	Point[] shipPos = {new Point(0,0), new Point(15,10), new Point(0,20), new Point(5,10)};
	Point startPos = new Point(width/2,height/2);

	static Point[] bullShape = {new Point(0,0),new Point(0,5),new Point(5,5),new Point(5,0)};



	static Point[] asteroidPoints = {new Point(0, 0), new Point(0, 30), new Point(30, 30), new Point(30,0)};
	static Point[] smallAstPoints = {new Point(0, 0), new Point(0, 20), new Point(20, 20), new Point(20,0)};
	static Point[] smallerAstPoints = {new Point(0, 0), new Point(0, 10), new Point(10, 10), new Point(10,0)};
	Asteroid[] asteroids;
	static ArrayList<Asteroid> listAst = new ArrayList<Asteroid>();
	static ArrayList<Bullet> listBull = new ArrayList<Bullet>();
	ArrayList<Bullet> listBullToRemove = new ArrayList<Bullet>();
	int numAst = 10;

	Ship ship;




	public Asteroids() {

		super("Slippery Asteroids!",width,height);
		this.setFocusable(true);
		this.requestFocus();

		ship =  new Ship(shipPos,startPos,270);


		this.addKeyListener(ship);
		asteroids = new Asteroid[numAst];



		for(int i = 0; i < numAst; i++) {
			start   = new Point(gen.nextInt(800)+1,gen.nextInt(600)+1);
			asteroids[i] = new Asteroid(asteroidPoints, start, gen.nextInt(360));
			Asteroid e = new Asteroid(asteroidPoints, start, gen.nextInt(360));
			listAst.add(e)                                             ;

		}
	}

	public void paint(Graphics brush) {

		brush.setColor(Color.black);
		brush.fillRect(0,0,width,height);



		brush.setColor(Color.white);


		brush.drawString("Friction decreases every death!",10,10);
		brush.drawString("Lives left:   "+ numLives, 400, 10);
		brush.setColor(Color.GRAY);
		for (Bullet b : listBull) {
			boolean alive = b.checkTime();
			if (alive) {
				b.move();
				b.paint(brush);
			}
			else {
				listBullToRemove.add(b);
				
			}

		}
		for(int i = 0; i<listAst.size()-1; i++) {

			for (int g = 0; g<listBull.size()-1;g++) {

				if (listAst.get(i).collides(listBull.get(g))) {
					breakAst(listAst.get(i));

				}
			}
			if (listAst.get(i).collides(ship) && numLives>0) {
				ship.die();

			}
			listAst.get(i).move();
			listAst.get(i).paint(brush);
			listBull.removeAll(listBullToRemove);
		}

		brush.setColor(Color.white);
		ship.move();
		ship.paint(brush);

	}

	public static void breakAst(Asteroid a) {
		double size = a.findArea();
		if (size >= 900) {
			Asteroid newAst = new Asteroid(smallAstPoints,new Point(a.position.getX()+2,a.position.getY()+2),a.rotation);
			Asteroid newAst2 = new Asteroid(smallAstPoints,new Point(a.position.getX()+2,a.position.getY()+2),a.rotation);
			listAst.add(newAst);
			listAst.add(newAst2);
			int index = listAst.indexOf(a);
			listAst.remove(index);
			

		}
		if (size == 400) {
			Asteroid newAst = new Asteroid(smallerAstPoints,new Point(a.position.getX()+2,a.position.getY()+2),a.rotation);
			Asteroid newAst2 = new Asteroid(smallerAstPoints,new Point(a.position.getX()+2,a.position.getY()+2),a.rotation);
			listAst.add(newAst);
			listAst.add(newAst2);
			int index = listAst.indexOf(a);
			listAst.remove(index);
			


		}
		if (size<400) {
			int index = listAst.indexOf(a);
			listAst.remove(index);

		}
	}

	public static void main (String[] args) {

		Asteroids a = new Asteroids();

		a.repaint();

	}
}