package brain.util;

public class Util {

	private final static double EPSILON = 0.000000001;

	/**
	 * Verifica se dois doubles são iguais, em uma precisão definida por EPSILON
	 * = 0.000000001. Se d1 e d2 forem NaN então retorna {@code true}. Se ambos
	 * forem infinitos (negativo ou positivo) irá retornar d1 == d2.
	 * 
	 * @param d1
	 *            o valor a ser comparado
	 * @param d2
	 *            outro valor a ser comparado
	 * @return {@code true} se d1 e d2 forem iguais ou iguais com precisão
	 *         EPSILON ou {@code false} caso contrário.
	 */
	public static boolean compareDouble(double d1, double d2) {
		if (Double.isNaN(d1) && Double.isNaN(d2))
			return true;
		if (!Double.isInfinite(d1) && !Double.isInfinite(d2))
			return Math.abs(d1 - d2) <= EPSILON;
		return d1 == d2;
	}

	/**
	 * Compara dois doubles e retorna a diferença entre eles, ou seja, se d1 e
	 * d2 forem iguais, então retorna 0. Se d1 > d2 retorna um número maior que
	 * zero. Se d1 < d2 retorna um número menor que zero.
	 * 
	 * @param d1
	 *            o valor a ser comparado
	 * @param d2
	 *            outro valor a ser comparado
	 * @return 0 se d1 > d2, d1 - d2 se d1 != d2
	 */
	public static double compare(double d1, double d2) {
		if (Util.compareDouble(d1, d2)) {
			return 0;
		}

		else
			return d1 - d2;
	}

}
