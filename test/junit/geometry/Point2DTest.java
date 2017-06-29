package brain.util.math.geometry;

public class Point2D {
	private double x;
	private double y;
	public final static Point2D ORIGIN = new Point2D(0.0, 0.0);
	private final static double EPSILON = 0.00000001;

	public Point2D(double x, double y) {
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

	public boolean equals(Point2D p) {
		if (p != null) {
			if (!Double.isFinite(p.getX()) && !Double.isFinite(p.getY()) && !Double.isFinite(getX())
					&& !Double.isFinite(getY()))
				return Math.abs(p.getX() - getX()) <= EPSILON && Math.abs(p.getY() - getY()) <= EPSILON;
			else
				return p.getX() == this.getX() && p.getY() == this.getY();
		}

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
}
