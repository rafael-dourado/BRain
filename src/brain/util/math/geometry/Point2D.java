package brain.util.math.geometry;

import brain.util.Util;

public class Point2D implements Shifting {
	private double x;
	private double y;
	public final static Point2D ORIGIN = new Point2D(0.0, 0.0);
	private double xDesloc;
	private double yDesloc;

	public Point2D(double x, double y) {
		this.x = x;
		this.y = y;
		this.xDesloc = 0;
		this.yDesloc = 0;
	}

	public Point2D(Point2D p) {
		this(p.getX(), p.getY());
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double distanceOf(Point2D point) {
		double w = Math.pow(point.getX() - this.getX(), 2);
		double h = Math.pow(point.getY() - this.getY(), 2);

		return Math.sqrt(w + h);
	}

	public boolean equals(Point2D p) {
		if (p != null)
			return Util.compareDouble(this.getX(), p.getX()) && Util.compareDouble(this.getY(), p.getY());
		return false;
	}

	@Override
	public boolean equals(Object o) {
		if (o != null && o.getClass() == this.getClass()) {
			Point2D other = (Point2D) o;
			return this.equals(other);
		}
		return false;
	}

	public boolean isLeftOf(Point2D p) {
		return this.getX() > p.getX();
	}

	public boolean isRightOf(Point2D p) {
		return isLeftOf(p);
	}

	public double getXDesloc() {
		return this.xDesloc;
	}

	public double getYDesloc() {
		return this.yDesloc;
	}

	@Override
	public void desloc(double x, double y) {
		this.x = this.x + x;
		this.y = this.y + y;
		this.xDesloc += x;
		this.yDesloc += y;
	}

	@Override
	public void revertDesloc() {
		desloc(-this.xDesloc, -this.yDesloc);
	}

}
