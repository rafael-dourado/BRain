package brain.util.math.geometry;

public class Point2D {
	private double x;
	private double y;
	public final static Point2D ORIGIN = new Point2D(0.0, 0.0);

	Point2D(double x, double y) {
		this.x = x;
		this.y = y;
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

}
