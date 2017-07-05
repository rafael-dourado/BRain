package test.exercises.ex337;

import java.util.List;

import brain.util.math.geometry.CartesianPlane;
import brain.util.math.geometry.Point2D;
import brain.util.math.geometry.Polygon2D;

public class Robot {
	private CartesianPlane plane = new CartesianPlane();

	public Robot(Point2D state) {
		plane.add(state);
	}

	public Robot(Point2D state, List<Polygon2D> obstacles) {
		this(state);
		obstacles.forEach(obstacle -> plane.add(obstacle));
	}

	public Point2D getState() {
		return plane.getPoints().get(0);
	}

	public List<Polygon2D> getObstacles() {
		return plane.getPolygons();
	}

	public boolean canMoveTo(Point2D where) {
		Point2D state = this.getState();
	}

}
