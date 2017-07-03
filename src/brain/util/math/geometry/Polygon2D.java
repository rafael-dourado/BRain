package brain.util.math.geometry;

import java.util.ArrayList;
import java.util.List;

import brain.util.Util;

public class Polygon2D {

	private List<Point2D> vertexes;

	public Polygon2D(List<Point2D> vertexes) {
		this.vertexes = new ArrayList<Point2D>(vertexes);
	}

	public List<Point2D> getVertexes() {
		return this.vertexes;
	}

	public Polygon2D(Point2D... point2ds) {
		for (Point2D point : point2ds) {
			vertexes.add(point);
		}
	}

	public boolean isValid() {
		return false;
	}

	public boolean intersects(Polygon2D other) {
		for (Point2D p : other.getVertexes()) {
			if (this.hasInside(p))
				return true;
		}
		return false;
	}

	public double getGreaterX() {
		double greaterX = Double.NEGATIVE_INFINITY;
		for (Point2D vertex : vertexes) {
			if (vertex.getX() > greaterX)
				greaterX = vertex.getX();
		}

		return greaterX;
	}

	public double getGreaterY() {
		double greaterY = Double.NEGATIVE_INFINITY;
		for (Point2D vertex : vertexes) {
			if (vertex.getY() > greaterY)
				greaterY = vertex.getX();
		}

		return greaterY;
	}

	public double getLeastX() {
		double leastX = Double.POSITIVE_INFINITY;
		for (Point2D vertex : vertexes) {
			if (vertex.getX() < leastX)
				leastX = vertex.getX();
		}

		return leastX;
	}

	public double getLeastY() {
		double leastY = Double.POSITIVE_INFINITY;
		for (Point2D vertex : vertexes) {
			if (vertex.getX() < leastY)
				leastY = vertex.getX();
		}

		return leastY;
	}

	public boolean hasInside(Point2D point) {
		if (Util.compare(point.getX(), getGreaterX()) > 0 || Util.compare(point.getX(), getLeastX()) < 0)
			return false;

		if (Util.compare(point.getY(), getGreaterY()) > 0 || Util.compare(point.getY(), getLeastY()) < 0)
			return false;
		// cria uma linha paralela ao eixo x, onde y é constante e vai até x =
		// point.get();
		Line2D auxLine = new Line2D(new Point2D(0.0, point.getY()), point);

		for (int i = 0; i < vertexes.size(); i++) {
			Point2D a = new Point2D(vertexes.get(i));
			if (i != vertexes.size() - 1) {
				Point2D b = new Point2D(vertexes.get(i + 1));
			} else {
				Point2D b = new Point2D(vertexes.get(0));
			}
		}
		return true;
	}

	public void desloc(double x, double y) {
		for (Point2D vertex : vertexes) {
			vertex.desloc(x, y);
		}
	}

	public void revertDesloc() {
		for (Point2D vertex : vertexes) {
			vertex.revertDesloc();
		}
	}
}
