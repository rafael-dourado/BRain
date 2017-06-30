package brain.util.math;

import brain.util.Util;

public class Line2D {
	 
	private Point2D start;
	private Point2D end;
	private boolean isLinearFunction;
	private boolean ignoreLimit = false;
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
	 * Checa se uma linha pode ser representada por uma função linear. Uma linha
	 * é uma função linear se, e somente se, para cada valor de <code>x</code>,
	 * existir apenas um único valor de <code>y</code> em seu domínio.
	 * 
	 * @return <code>true</code> se a linha puder ser representada por uma
	 *         função linear ou <code>false</code> caso contrário.
	 */
	public boolean isLinearFunction() {
		return isLinearFunction;
	}

	/**
	 * Calcula e retorna o coeficiente angular da reta data. Se a reta não for
	 * uma função linear, ou seja, for uma reta paralela ao eixo y, então
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
	 * Verifica se a linha é paralela a outra linha, comparando o coeficiente
	 * angular de ambas as linhas.
	 * 
	 * @param line
	 *            um objeto Line2D
	 * @return <code>true</code> se ambas as retas são paralelas entre si ou
	 *         <code>false</code> caso contrário.
	 */
	public boolean isParallelTo(Line2D line) {
		return Util.compareDoble(this.getAngularCoefficient(), line.getAngularCoefficient());
	}

	/**
	 * Retorna o coeficiente linear, caso a linha seja uma função linear, ou
	 * {@code Double.NaN}, caso não seja
	 * 
	 * @return o coeficiente linear, caso a linha seja uma função linear, ou
	 *         {@code Double.NaN}, caso não seja
	 */
	public double getLinearCoefficient() {
		double alpha = getAngularCoefficient();
		if (Double.isNaN(alpha))
			return Double.NaN;
		return start.getY() - alpha * start.getX();
	}

	/**
	 * Retorna o ponto de interseção a uma determinada linha  ou
	 * {@code Null} caso não haja interseção entre elas
	 * 
	 * @param line
	 *            uma linha qualquer
	 * @return o ponto de interseção a uma determinada linha ou {@code Null}
	 *         caso não haja interseção entre elas
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

		Point2D intersectPoint = new Point2D(x, y);
		
		if( ignoreLimit || ( this.contains( intersectPoint ) && line.contains( intersectPoint ) ) )
			return intersectPoint;

		// as linhas se interceptam, mas não em seu intervalo delimitado por end e start;
		return null;
	}
	
	public void ignoreLimit(boolean ignoreLimit){
		this.ignoreLimit = ignoreLimit;
	}
	
	
	public boolean contains(Point2D point){
		if(Util.compareDoble(end.getX(), start.getX()))
			return false;
		
		if(!ignoreLimit)
			if(point.getX() > getGreaterX() || 
				point.getX() < getLeastX() || 
				point.getY() > getGreaterY() || 
				point.getY() < getLeastY())
			return false;
		
		double a = (end.getY() - start.getY())/(end.getX()-start.getX());
		double b = (start.getY()*end.getX() - start.getX()*end.getY()) / (end.getX() - start.getX() );
		
		return point.getY() == a*point.getX() + b;
	}
	
	public double getGreaterX(){
		
		if(end.getX() > start.getX())
			return end.getX();
		return start.getX();

	}
	
	public double getLeastX(){
		
		if(end.getX() > start.getX())
			return start.getX();
		
		return end.getX();
	}
	
	public double getGreaterY(){
		if( end.getY() > start.getY())
			return end.getY();
		
		return start.getY();
	}
	
	public double getLeastY(){
		if( end.getY() > start.getY() )
			return start.getY();
		
		return end.getY();
	}
	
}
