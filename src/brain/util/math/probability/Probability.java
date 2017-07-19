package brain.util.math.probability;

import java.util.Random;

public final class Probability {

	private final static Random random = new Random();

	/**
	 * Retorna um número pseudo-aleatório entre 0 (inclusive) e 1.0 (exclusive).
	 * 
	 * @return um número pseudo-aleatório entre 0 (inclusive) e 1.0 (exclusive).
	 */
	public static double nextDouble() {
		return random.nextDouble();
	}

	/**
	 * Retorna um número pseudo-aleatório entre 0 (inclusive) e o valor
	 * especificado (exclusive).
	 * 
	 * @param range
	 *            o maior valor (exclusive). Deve ser positivo
	 * @return um número pseudo-aleatório entre 0 (inclusive) e o valor
	 *         especificado (exclusive).
	 */
	public static int nextInt(int range) {
		return random.nextInt(range);
	}

	/**
	 * Algoritmo de Seleção Natural.<br>
	 * <br>
	 * 
	 * Retorna o índice do número de um vetor escolhido aleatoriamente. A chance
	 * de escolha do índice aumenta quanto maior for o seu valor. Os valores do
	 * vetor devem ser positivos.<br>
	 * <br>
	 * 
	 * O algoritmo tem esse nome devido à analogia à seleção natural (Darwin),
	 * onde indivíduos mais adaptados tem mais chances de sobreviver e procriar.
	 * 
	 * @param values
	 * @return o índice do número escolhido de um vetor ou -1 caso haja um
	 *         número inválido
	 */
	public static int naturalSelection(double[] values) {

		double rand = nextDouble();
		int choosenIndex = -1;
		int sum = 0;
		double n = 0;
		// calcula a soma dos valores do vetor
		for (double value : values) {
			sum += value;
		}

		for (int i = 0; i < values.length; i++) {
			n += values[i] / sum;
			if (rand <= n)
				choosenIndex = i;
		}

		return choosenIndex;
	}

	/**
	 * Calcula a probabilidade de uma determinada configuração (indivíduo) ser
	 * aceita em uma amostra (população). Se a energia (valor) da configuração
	 * for menor que a menor energia encontrada da amostra, ela será aceita.
	 * Caso contrário, há uma probabilidade de ela ser aceita de acordo com a
	 * formula:<br>
	 * 
	 * <pre>
	 * {@code Math.exp(-1 * (deltaE) / temperature)}
	 * </pre>
	 * 
	 * onde {@code deltaE} é a variação de energia e {@code temperature} é a
	 * temperatura do sistema, que deverá decrescer a cada iteração.
	 * 
	 * @param current
	 *            menor energia da amostra encontrada.
	 * @param next
	 *            energia da amostra testada.
	 * @param temperature
	 *            temperatura do sistema, que deverá decrescer a cada iteração
	 * @return um número entre 0 e 1, representando a probabilidade de uma
	 *         determinada configuração (indivíduo) ser aceita em uma amostra
	 *         (população).
	 */
	public static double metropolisAlgorithmEvaluation(double current, double next, double temperature) {
		double deltaE = next - current;
		if (deltaE < 0)
			return 1.0;
		return Math.exp(-1 * (deltaE) / temperature);
	}
}
