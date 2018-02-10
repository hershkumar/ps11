package net.mrpaul.md110.ps11.asteroids;

/*
CLASS: Polygon
DESCRIPTION: A polygon is a sequence of points in space defined by a set of
             such points, an offset, and a rotation. The offset is the
             distance between the origin and the center of the shape.
             The rotation is measured in degrees, 0-360.
USAGE: You are intended to instantiate this class with a set of points that
       forever defines its shape, and then modify it by repositioning and
       rotating that shape. In defining the shape, the relative positions
       of the points you provide are used, in other words: {(0,1),(1,1),(1,0)}
       is the same shape as {(9,10),(10,10),(10,9)}.
NOTE: You don't need to worry about the "magic math" details.

 */

class Polygon {
	private Point[] shape;   // An array of points.
	public Point position;   // The offset mentioned above.
	public double rotation; // Zero degrees is due east.

	public Polygon(Point[] inShape, Point inPosition, double inRotation) {
		setShape(inShape);
		position = inPosition;
		rotation = inRotation;

		// First, we find the shape's top-most left-most boundary, its origin.
		Point origin = getShape()[0].clone();
		for (Point p : getShape()) {
			if (p.x < origin.x) origin.x = p.x;
			if (p.y < origin.y) origin.y = p.y;
		}

		// Then, we orient all of its points relative to the real origin.
		for (Point p : getShape()) {
			p.x -= origin.x;
			p.y -= origin.y;
		}
	}

	// "getPoints" applies the rotation and offset to the shape of the polygon.
	public Point[] getPoints() {
		Point center = findCenter();
		Point[] points = new Point[getShape().length];
		for (int i = 0; i < getShape().length; i++) {
			//    for (Point p : shape) {
			Point p = getShape()[i];
			double x = ((p.x-center.x) * Math.cos(Math.toRadians(rotation)))
					- ((p.y-center.y) * Math.sin(Math.toRadians(rotation)))
					+ center.x/2 + position.x;
			double y = ((p.x-center.x) * Math.sin(Math.toRadians(rotation)))
					+ ((p.y-center.y) * Math.cos(Math.toRadians(rotation)))
					+ center.y/2 + position.y;
			points[i] = new Point(x,y);
		}
		return points;
	}

	// "contains" implements some magical math (i.e. the ray-casting algorithm).
	public boolean contains(Point point) {
		Point[] points = getPoints();
		double crossingNumber = 0;
		for (int i = 0, j = 1; i < getShape().length; i++, j=(j+1)%getShape().length) {
			if ((((points[i].x < point.x) && (point.x <= points[j].x)) ||
					((points[j].x < point.x) && (point.x <= points[i].x))) &&
					(point.y > points[i].y + (points[j].y-points[i].y)/
							(points[j].x - points[i].x) * (point.x - points[i].x))) {
				crossingNumber++;
			}
		}
		return crossingNumber%2 == 1;
	}

	public void rotate(int degrees) {
		rotation = (rotation+degrees)%360;
	}

	/*
  The following methods are private access restricted because, as this access
  level always implies, they are intended for use only as helpers of the
  methods in this class that are not private. They can't be used anywhere else.
	 */

	// "findArea" implements some more magic math.
	public double findArea() {
		double sum = 0;
		for (int i = 0, j = 1; i < getShape().length; i++, j=(j+1)%getShape().length) {
			sum += getShape()[i].x*getShape()[j].y-getShape()[j].x*getShape()[i].y;
		}
		return Math.abs(sum/2);
	}

	// "findCenter" implements another bit of math.
	private Point findCenter() {
		Point sum = new Point(0,0);
		for (int i = 0, j = 1; i < getShape().length; i++, j=(j+1)%getShape().length) {
			sum.x += (getShape()[i].x + getShape()[j].x)
					* (getShape()[i].x * getShape()[j].y - getShape()[j].x * getShape()[i].y);
			sum.y += (getShape()[i].y + getShape()[j].y)
					* (getShape()[i].x * getShape()[j].y - getShape()[j].x * getShape()[i].y);
		}
		double area = findArea();
		return new Point(Math.abs(sum.x/(6*area)),Math.abs(sum.y/(6*area)));
	}
	public boolean collides(Polygon other) {
		for (Point p: other.getPoints()) {
			if (contains(p)) return true;
		}
		
		if (contains(other.position)) return true;
		
		return false;
	}

	public Point[] getShape() {
		return shape;
	}

	public void setShape(Point[] shape) {
		this.shape = shape;
	}



}