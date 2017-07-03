package test.junit.geometry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import brain.util.Util;
import brain.util.math.geometry.Point2D;
import brain.util.math.geometry.Polygon2D;

public class Polygon2DTest {
	Point2D q1 = new Point2D(1.0, 1.0);
	Point2D q2 = new Point2D(4.0, 1.0);
	Point2D q3 = new Point2D(4.0, 5.0);
	Point2D q4 = new Point2D(1.0, 5.0);
	Polygon2D p;

	@Before
	public void init() {
		p = new Polygon2D(q1, q2, q3, q4);
	}

	@Test
	public void contructor() {
		List<Point2D> points = new ArrayList<Point2D>();
		points.add(q1);
		points.add(q2);
		points.add(q3);
		points.add(q4);
		assertEquals(points, p.getVertexes());
	}

	@Test
	public void TestGreaterAndLeast() {
		double greaterX = 4.0;
		double greaterY = 5.0;
		double leastX = 1.0;
		double leastY = 1.0;
		assertTrue(Util.compareDouble(greaterX, p.getGreaterX()));
		assertTrue(Util.compareDouble(greaterY, p.getGreaterY()));
		assertTrue(Util.compareDouble(leastX, p.getLeastX()));
		assertTrue(Util.compareDouble(leastY, p.getLeastY()));
	}

	@Test
	public void hasInside() {
		Point2D inside = new Point2D(2.0, 2.0);
		Point2D out = new Point2D(2.0, 6.0);
		assertTrue(p.hasInside(inside));
		assertFalse(p.hasInside(out));
	}

}
