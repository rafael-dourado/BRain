package brain.util.math.geometry;

import java.util.ArrayList;
import java.util.List;

public class CartesianPlane {
	enum Quadrant {
		FIRST_QUADRANT, SECOND_QUADRANT, THIRD_QUANDRANT, FOURTH_QUADRANT
	}

	private List<Point2D> points;
	private List<Line2D> lines;
	private List<Polygon2D> polygons;

	public CartesianPlane() {
		this.points = new ArrayList<>();
		this.lines = new ArrayList<>();
		this.polygons = new ArrayList<>();
	}

	public List<Point2D> getPoints() {
		return this.points;
	}

	public List<Line2D> getLines() {
		return this.lines;
	}

	public List<Polygon2D> getPolygons() {
		return this.polygons;
	}

	public boolean add(Point2D point) {
		return points.add(point);
	}

	public boolean add(Line2D line) {
		return lines.add(line);
	}

	public boolean add(Polygon2D polygon) {
		return polygons.add(polygon);
	}

	public void addFirst(Point2D p) {
		points.add(0, p);
	}

	public void set(int index, Point2D point) {
		points.set(index, point);
	}

	public void set(int index, Polygon2D polygon) {
		polygons.set(index, polygon);
	}

	public void set(int index, Line2D line) {
		lines.set(index, line);
	}

	public boolean remove(Point2D point) {
		return points.remove(point);
	}

	public boolean remove(Line2D line) {
		return points.remove(line);
	}

	public boolean remove(Polygon2D polygon) {
		return polygons.remove(polygon);
	}
}
