package brain.util;

public class Util {

	private final static double EPSILON = 0.000000001;

	public static boolean compareDoble(double d1, double d2) {
		if (Double.isNaN(d1) && Double.isNaN(d2))
			return true;
		if (!Double.isInfinite(d1) && !Double.isInfinite(d2))
			return Math.abs(d1 - d2) <= EPSILON;
		return d1 == d2;
	}
}
