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
		vertexes = new ArrayList<>();
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
				greaterY = vertex.getY();
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

	/**
	 * checa se um ponto existe no polígono. Obs: O ponto não será considerado
	 * interno se ele estiver contido na aresta do polígono ou seja,
	 * 
	 * @param point
	 *            um ponto qualquer
	 * @return {@code true} se o ponto estiver dentro do polígono ou
	 *         {@code false} caso contrário
	 */
	public boolean hasInside(Point2D point) {
		if (Util.compare(point.getX(), getGreaterX()) >= 0 || Util.compare(point.getX(), getLeastX()) <= 0)
			return false;

		if (Util.compare(point.getY(), getGreaterY()) >= 0 || Util.compare(point.getY(), getLeastY()) <= 0)
			return false;
		// cria uma linha paralela ao eixo x, onde y é constante e vai até x =
		// point.getX();
		int count = 0;
		Line2D auxLine = new Line2D(new Point2D(0.0, point.getY()), point);
		auxLine.ignoreLimit(true);

		for (int i = 0; i < vertexes.size(); i++) {
			Point2D a = new Point2D(vertexes.get(i));
			Point2D b;
			if (i != vertexes.size() - 1) {
				b = new Point2D(vertexes.get(i + 1));
			} else {
				b = new Point2D(vertexes.get(0));
			}

			Line2D edge = new Line2D(a, b);

			Point2D intersection = auxLine.getIntersctPointIn(edge);
			// se houve interseção, só contar se foi à direita do ponto.
			if (intersection != null && intersection.isRightOf(point)) {
				// interseção não foi em um vértice
				if (!a.equals(intersection) && !b.equals(intersection))
					count++;
			} else {
				Point2D vertexIntersected = getIntersectedVertex(intersection, a, b);
			}

		}

		return count % 2 != 0;
	}

	private Point2D getIntersectedVertex(Point2D intersection, Point2D a, Point2D b) {
		Point2D p = a.equals(intersection) ? a : b;
		return p;
	}
	// ta errado
	public boolean isLocalMinOrMax(Point2D vertex) {
		final int index = vertexes.indexOf(vertex);
		final Point2D prev;
		final Point2D next;
		if (index != vertexes.size() - 1)
			next = new Point2D(vertexes.get(index + 1));
		else
			next = new Point2D(vertexes.get(0));
		if (index != 0)
			prev = new Point2D(vertexes.get(index - 1));
		else
			prev = new Point2D(vertexes.get(vertexes.size() - 1));

		final Line2D edge1 = new Line2D(prev, vertex);
		final Line2D edge2 = new Line2D(vertex, next);

		return edge1.getAngularCoefficient() * edge2.getAngularCoefficient() > 0;
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
