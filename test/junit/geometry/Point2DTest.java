package test.junit.geometry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

import brain.util.math.geometry.Point2D;

public class Point2DTest {

	Point2D p;

	@Before
	public void init() {
		p = new Point2D(1.0, 2.0);
	}

	@Test
	public void equals() {
		Point2D origin = Point2D.ORIGIN;
		Point2D p1 = new Point2D(1.0, 2.0);
		Point2D equalsp1 = new Point2D(1.0, 2.0);

		assertEquals(p1, equalsp1);
		assertEquals(Point2D.ORIGIN, origin);
		assertNotEquals(p1, origin);
	}

	@Test
	public void distanceOf() {
		double delta = 0.00000001;
		Point2D origin = Point2D.ORIGIN;
		Point2D p1 = new Point2D(3.0, 4.0);
		Point2D p2 = new Point2D(6.0, 8.0);
		Point2D p3 = new Point2D(5.0, 6.0);

		assertEquals(5.0, origin.distanceOf(p1), delta);
		assertEquals(5.0, p1.distanceOf(p2), delta);
		assertEquals(7.8102496759, origin.distanceOf(p3), delta);
	}

	@Test
	public void deslocAndRevert() {
		Point2D pDelsoc = new Point2D(2.0, 4.0);
		Point2D pDesloc2 = new Point2D(3.0, 6.0);
		Point2D original = new Point2D(p);
		p.desloc(1.0, 2.0);
		assertEquals(pDelsoc, p);
		p.revertDesloc();
		assertEquals(original, p);
		p.desloc(1.0, 2.0);
		p.desloc(1.0, 2.0);
		assertEquals(pDesloc2, p);
		p.revertDesloc();
		assertEquals(original, p);
	}
}
