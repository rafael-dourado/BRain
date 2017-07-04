package brain.util.math;

import java.util.ArrayList;
import java.util.List;

import brain.util.Util;
import brain.util.math.Point2D;
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
		for(Point2D p: point2ds)
			vertexes.add(p);
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
		if (Util.compare(point.getX(), getGreaterX()) >= 0 || Util.compare(point.getX(), getLeastX()) <= 0)
			return false;

		if (Util.compare(point.getY(), getGreaterY()) >= 0 || Util.compare(point.getY(), getLeastY()) <= 0)
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

			Line2D edge = new Line2D(a, b);

			Point2D intersection = auxLine.getIntersctPointIn(edge);
			
			// se houve interseção, só contar se foi à direita do ponto.
			if(intersection != null){
				if (intersection.isRightOf(point)) {
					// interseção não foi em um vértice
					if (!a.equals(intersection) && !b.equals(intersection))
						count++;
					
					else {
						Point2D inVertex = getIntersected(intersection, a, b);
						if(!isLocalMinOrMax(intersection) && !checked.contains(inVertex)){
							count++;
							checked.add(inVertex);
							
						}

					}
					
				} 
				
			}

		}
		return count % 2 != 0;
	}
	
	public Point2D getIntersected(Point2D intersection, Point2D a, Point2D b){
		Point2D p = intersection.equals(a) ? a : b ;
		return p;
	}
	
	/**
	 * verifca se o vértice é um máximo ou mínimo local.
	 * O vértice será um mínimo ou máximo local se os coeficientes angulares da aresta anterior e da
	 * aresta seguinte tiverem sinais diferentes.
	 * @return {@code true} se o vértice for um mínimo local ou {@code false} caso contrário.
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

		Point2D aux = new Point2D(0,vertex.getY());

		final Line2D auxLine1 = new Line2D(aux,prev);
		final Line2D auxLine2 = new Line2D(aux,next);
		
		return auxLine1.getAngularCoefficient() * auxLine2.getAngularCoefficient() > 0;
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


