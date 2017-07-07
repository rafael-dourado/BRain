package test.exercises.ex337;

import java.util.ArrayList;
import java.util.List;

import brain.util.math.geometry.CartesianPlane;
import brain.util.math.geometry.Line2D;
import brain.util.math.geometry.Point2D;
import brain.util.math.geometry.Polygon2D;

public class Robot {
	private CartesianPlane plane = new CartesianPlane();

	public Robot(Point2D state, Point2D goal, List<Polygon2D> obstacles) {

		plane.add(state);
		plane.add(goal);
		obstacles.forEach(obstacle -> plane.add(obstacle));
	}

	public Point2D getState() {
		return plane.getPoints().get(0);
	}

	public Point2D getGoal() {
		return plane.getPoints().get(1);
	}

	public List<Polygon2D> getObstacles() {
		return plane.getPolygons();
	}

	private boolean canMoveTo(Point2D point) {
		Point2D state = this.getState();
		Line2D path = new Line2D(state, point);
		List<Polygon2D> obstacles = plane.getPolygons();

		for (Polygon2D obstacle : obstacles) {
			if (obstacle.crosses(path)) {
				return false;
			}
		}
		return true;
	}

	public List<Point2D> getNextMovements() {
		List<Point2D> nextMovements = new ArrayList<>();
		for (Polygon2D obstacles : plane.getPolygons()) {
			for (Point2D vertex : obstacles.getVertexes()) {

				if (this.canMoveTo(vertex)) {
					nextMovements.add(vertex);
				}
				if (this.canMoveTo(this.getGoal()))
					nextMovements.add(this.getGoal());
			}
		}

		return nextMovements;
	}

	public void moveTo(Point2D where) {
		plane.remove(this.getState());
		plane.add(where);

	}

	public double distanceOf(Point2D p) {
		return this.getState().distanceOf(p);
	}

}
