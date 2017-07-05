package brain.util.math.geometry;

public interface Shifting {

	/**
	 * Desloca o objeto vertical e/ou horizontalmente no plano.
	 * 
	 * @param y
	 *            altura do deslocamento (eixo y)
	 * @param x
	 *            largura do deslocamento (eixo x)
	 */
	void desloc(double x, double y);

	/**
	 * Retorna o objeto ao seu local original, no caso de uma ocorrencia de
	 * desloc;
	 */
	void revertDesloc();
}
