package brain.util.math.geometry;

import java.util.ArrayList;
import java.util.List;

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

}
