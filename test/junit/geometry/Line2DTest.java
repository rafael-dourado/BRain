package test.junit.geometry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import brain.util.math.geometry.Line2D;
import brain.util.math.geometry.Point2D;

public class Line2DTest {
	Line2D yupFunction;
	Line2D notFunction;
	Line2D parallelToYupFunction;
	Line2D parallelToNotFunction;
	double delta = 0.000000001;

	Line2D line;
	Line2D line2;

	@Before
	public void init() {

		Point2D p1 = new Point2D(1.0, 2.0);
		Point2D p2 = new Point2D(2.0, 1.0);
		Point2D p3 = new Point2D(1.0, 4.0);
		Point2D p4 = new Point2D(2.0, 3.0);
		Point2D p5 = new Point2D(5.0, 0.0);
		Point2D p6 = Point2D.ORIGIN;
		Point2D p7 = new Point2D(3.0, 3.0);
		Point2D p8 = new Point2D(0.0, 5.0);
		notFunction = new Line2D(p1, p3);
		yupFunction = new Line2D(p1, p2);
		parallelToNotFunction = new Line2D(p2, p4);
		parallelToYupFunction = new Line2D(p5, p4);
		line = new Line2D(p6, p7);
		line2 = new Line2D(p8, p3);

	}
	
	@SuppressWarnings("unused")
	@Test(expected = RuntimeException.class)
	public void notLine2D() {
		Line2D notLine = new Line2D(new Point2D(1.0, 1.0), new Point2D(1.0, 1.0));
	}

	@Test
	public void isLinearFunction() {

		assertTrue(!notFunction.isLinearFunction());
		assertTrue(yupFunction.isLinearFunction());

		assertEquals(Double.NaN, notFunction.getAngularCoefficient(), delta);
		assertEquals(-1.0, yupFunction.getAngularCoefficient(), delta);

	}

	@Test
	public void isParallelTo() {
		assertFalse(notFunction.isParallelTo(yupFunction));
		assertTrue(yupFunction.isParallelTo(parallelToYupFunction));
		assertTrue(notFunction.isParallelTo(parallelToNotFunction));
	}

	@Test
	public void linearCoeficient() {
		assertEquals(Double.NaN, notFunction.getLinearCoefficient(), delta);
		assertEquals(0.0, line.getLinearCoefficient(), delta);
		assertEquals(5.0, line2.getLinearCoefficient(), delta);
	}

	@Test
	public void instersects() {
		Point2D intersectPoint = new Point2D(1.0, 2.0);

		assertNull(notFunction.getIntersctPointIn(parallelToNotFunction));
		assertNull(yupFunction.getIntersctPointIn(parallelToYupFunction));
		yupFunction.ignoreLimit(true);
		notFunction.ignoreLimit(true);
		assertNotNull(yupFunction.getIntersctPointIn(notFunction));
		assertEquals(intersectPoint, yupFunction.getIntersctPointIn(notFunction));
		assertEquals(intersectPoint, notFunction.getIntersctPointIn(yupFunction));
	}
}
