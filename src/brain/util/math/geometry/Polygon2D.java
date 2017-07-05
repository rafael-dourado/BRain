package brain.util.math.geometry;

import java.util.ArrayList;
import java.util.List;

import brain.util.Util;

public class Polygon2D implements Shifting {

	private List<Point2D> vertexes;

	public Polygon2D(List<Point2D> vertexes) {
		this.vertexes = new ArrayList<Point2D>(vertexes);
	}

	public List<Point2D> getVertexes() {
		return this.vertexes;
	}

	public Polygon2D(Point2D... point2ds) {
		vertexes = new ArrayList<>();
		for (Point2D p : point2ds)
			vertexes.add(p);
	}

	public boolean isValid() {
		return false;
	}

	public List<Line2D> getEdges() {
		Point2D a;
		Point2D b;
		List<Line2D> edges = new ArrayList<Line2D>();
		for (int i = 0; i < vertexes.size(); i++) {
			a = new Point2D(vertexes.get(i));

			if (i != vertexes.size() - 1) {
				b = new Point2D(vertexes.get(i + 1));

			} else {
				b = new Point2D(vertexes.get(0));
			}

			Line2D edge = new Line2D(a, b);
			edges.add(edge);
		}

		return edges;
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
			if (vertex.getY() < leastY)
				leastY = vertex.getY();
		}

		return leastY;
	}

	/**
	 * checa se um ponto existe no polígono. Obs: O ponto não será considerado
	 * interno se ele estiver contido na aresta do polígono.
	 * 
	 * @param point
	 *            um ponto qualquer
	 * @return {@code true} se o ponto estiver dentro do polígono ou
	 *         {@code false} caso contrário
	 */
	public boolean hasInside(Point2D point) {

		// se o ponto não estiver entre os mínimos e máximos do polígono, ele
		// não estará dentro do polígono.
		if ((Util.compare(point.getX(), getGreaterX()) >= 0) || (Util.compare(point.getX(), getLeastX()) <= 0))
			return false;
		if ((Util.compare(point.getY(), getGreaterY())) >= 0 || (Util.compare(point.getY(), getLeastY()) <= 0))
			return false;

		List<Point2D> checked = new ArrayList<Point2D>();
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
			// aresta do polígono formado pelos vértices a e b;
			Line2D edge = new Line2D(a, b);

			// ponto de interseção entre a reta auxiliar e a aresta;
			Point2D intersection = auxLine.getIntersctPointIn(edge);

			// se houve interseção, só contar se foi à direita do ponto.
			if (intersection != null) {
				if (intersection.isRightOf(point)) {
					// interseção não foi em um vértice
					if (!a.equals(intersection) && !b.equals(intersection))
						count++;
					else {
						// se a interseção foi em um vértice, só irá
						// contabilizar caso o vértice ainda não tenha sido
						// contabilizado e ele não seja um ponto crítico.
						if (!isLocalMinOrMax(intersection) && !checked.contains(intersection)) {
							count++;
							checked.add(intersection);
						}
					}
				}
			}
		}
		return count % 2 != 0;
	}

	@Override
	public void desloc(double x, double y) {
		for (Point2D vertex : vertexes) {
			vertex.desloc(x, y);
		}
	}

	@Override
	public void revertDesloc() {
		for (Point2D vertex : vertexes) {
			vertex.revertDesloc();
		}
	}

	public boolean crosses(Line2D line) {
		List<Line2D> edges = getEdges();

		for (Line2D edge : edges) {
			Point2D intersection = edge.getIntersctPointIn(line);
			if (intersection != null && (!intersection.equals(edge.getStart()) && !intersection.equals(edge.getEnd())))
				return true;
		}

		return false;
	}

	/**
	 * verifca se o vértice é um máximo ou mínimo local.
	 * 
	 * @return {@code true} se o vértice for um mínimo local ou {@code false}
	 *         caso contrário.
	 */

	private boolean isLocalMinOrMax(Point2D vertex) {

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

		Point2D observer = new Point2D(0, vertex.getY());

		// cria 2 linhas a partir do ponto de observação até os pontos prev e
		// next.
		final Line2D auxLine1 = new Line2D(observer, prev);
		final Line2D auxLine2 = new Line2D(observer, next);

		// o vértice será um ponto mínimo ou máximo (também conhecido como
		// crítico)
		// se os coeficientes angulares das retas formadas por auxLine1 e
		// auxLine2 forem iguais.
		return auxLine1.getAngularCoefficient() * auxLine2.getAngularCoefficient() > 0;
	}

}
