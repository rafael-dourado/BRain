package brain.util.math.geometry;

import brain.util.Util;

public class Line2D {

	Point2D start;
	Point2D end;
	// y = alpha * x + b; representa��o matem�tica de uma reta em um plano
	// cartesiano
	private Function function;
	private double b;
	private boolean isLinearFunction;

	public Line2D(Point2D start, Point2D end) {
		this.start = start;
		this.end = end;
		this.isLinearFunction = !Util.compareDoble(end.getX(), start.getX());

		if (start.equals(end))
			throw new RuntimeException("Erro: Line2D precisa ter 2 Point2D diferentes entre si.");

	}

	public Point2D getStart() {
		return this.start;
	}

	public Point2D getEnd() {
		return this.end;
	}

	/**
	 * Checa se uma linha pode ser representada por uma fun��o linear. Uma linha
	 * � uma fun��o linear se, e somente se, para cada valor de <code>x</code>,
	 * existir apenas um �nico valor de <code>y</code> em seu dom�nio.
	 * 
	 * @return <code>true</code> se a linha puder ser representada por uma
	 *         fun��o linear ou <code>false</code> caso contr�rio.
	 */
	public boolean isLinearFunction() {
		return isLinearFunction;
	}

	/**
	 * Calcula e retorna o coeficiente angular da reta data. Se a reta n�o for
	 * uma fun��o linear, ou seja, for uma reta paralela ao eixo y, ent�o
	 * retorna <code>Double.NaN</code>;
	 * 
	 * @return o coeficiente angular da reta data ou <code>Double.NaN</code>;
	 */
	public double getAngularCoefficient() {
		if (!isLinearFunction()) {
			return Double.NaN;
		}
		return (end.getY() - start.getY()) / (end.getX() - start.getX());
	}

	/**
	 * Verifica se a linha � paralela a outra linha, comparando o coeficiente
	 * angular de ambas as linhas.
	 * 
	 * @param line
	 *            um objeto Line2D
	 * @return <code>true</code> se ambas as retas s�o paralelas entre si ou
	 *         <code>false</code> caso contr�rio.
	 */
	public boolean isParallelTo(Line2D line) {
		return Util.compareDoble(this.getAngularCoefficient(), line.getAngularCoefficient());
	}

	/**
	 * Retorna o coeficiente linear, caso a linha seja uma fun��o linear, ou
	 * {@code Double.NaN}, caso n�o seja
	 * 
	 * @return o coeficiente linear, caso a linha seja uma fun��o linear, ou
	 *         {@code Double.NaN}, caso n�o seja
	 */
	public double getLinearCoefficient() {
		double alpha = getAngularCoefficient();
		if (Double.isNaN(alpha))
			return Double.NaN;
		return start.getY() - alpha * start.getX();
	}

	/**
	 * Retorna o ponto de interse��o a uma determinada linha (representada por
	 * uma fun��o linear, n�o somente o intervalo que criou a linha) ou
	 * {@code Null} caso n�o haja interse��o entre elas
	 * 
	 * @param line
	 *            uma linha qualquer
	 * @return o ponto de interse��o a uma determinada linha ou {@code Null}
	 *         caso n�o haja interse��o entre elas
	 */
	public Point2D getIntersctPointIn(Line2D line) {
		if (this.isParallelTo(line)) {
			return null;
		}
		double a = getAngularCoefficient();
		double b = getLinearCoefficient();

		double a1 = line.getAngularCoefficient();
		double b1 = line.getLinearCoefficient();

		double x;
		double y;

		if (!this.isLinearFunction()) {
			x = this.getStart().getX();
			y = a1 * x + b1;

		} else if (!line.isLinearFunction()) {
			x = line.getStart().getX();
			y = a * x + b;
		} else {
			x = (b1 - b) / (a - a1);
			y = a * x + b;
		}

		return new Point2D(x, y);
	}
}
