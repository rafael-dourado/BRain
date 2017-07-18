package brain.search.local;

import java.util.List;
import java.util.function.ToDoubleFunction;

import brain.problem.Problem;
import brain.search.SearchUtils;
import brain.util.node.Node;

/**
 * Inteligência Artificial: Uma Abordagem Moderna (3a Edição), página 164.
 * 
 * A subida de encosta com reinício aleatório adota o conhecido ditado: “Se não
 * tiver sucesso na primeira vez, continue tentando.” Ela conduz uma série de
 * buscas de subida de encosta a partir de estados iniciais gerados de forma
 * aleatória, até encontrar um objetivo.
 * 
 * @author cpd
 *
 * @param <S>
 *            o tipo de representação do estado que será submetido ao algoritmo.
 */
public class RandomRestartHillClimbSearch<S> extends HillClimbSearch<S> {

	private int maxIterations = 1000;
	private final double MIN_VALUE = 0.001;
	private int numberOfStates;

	/**
	 * Construtor.
	 * 
	 * @param h
	 *            função de avaliação.
	 * @param numberOfStates
	 *            numero de estados iniciais.
	 */
	public RandomRestartHillClimbSearch(ToDoubleFunction<Node<S>> h, int numberOfStates) {
		super(h);
		this.numberOfStates = numberOfStates;
	}

	/**
	 * Construtor.
	 * 
	 * @param h
	 *            função de avaliação.
	 * @param numberOfStates
	 *            numero de estados iniciais.
	 * @param maxIterations
	 *            numero de reinícios máximo que o algoritmo pode ralizar.
	 */
	public RandomRestartHillClimbSearch(ToDoubleFunction<Node<S>> h, int numberOfStates, int maxIterations) {
		this(h, numberOfStates);
		this.maxIterations = maxIterations;
	}

	/**
	 * A Subida de Encosta com Reinicio Aleatório gera problemas temporários com
	 * estados iniciais aleatórios. Em cada problema, o algoritmo irá fazer uma
	 * busca subida de encosta e selecionará o melhor valor (nesse caso, quanto
	 * menor a heurística, maior o valor) entre eles. O algoritmo irá parar e
	 * retornar o melhor nó quando: (a) o numero de iterações ultrapassar o
	 * máximo estipulado; (b) o algoritmo encontrar um nó com valor heurístico
	 * satisfatório previamente estipulado ou (c) quando encontrar um estado
	 * objetivo durante a subida de encosta de qualquer problema gerado.
	 */
	@Override
	public Node<S> findNode(Problem<S> p) {
		clearMetrics();

		// gera problemas temporários com estados iniciais aleatórios, gerados a
		// partir do estado inicial do problema passado por paramentro.
		List<Problem<S>> problems = SearchUtils.problemsWithRandomIntialStates(p, nExpander, numberOfStates);

		// melhor nó, inicialmente com o estado inicial do problema.
		Node<S> bestNode = nExpander.createRootNode(p.getInicialState());

		// contador de passo.
		int step = 0;
		double delta = Double.POSITIVE_INFINITY;

		// o algoritmo irá parar quando: (a) o numero de iterações ultrapassar o
		// máximo estipulado; (b) o algoritmo encontrar um nó com valor
		// heurístico satisfatório previamente estipulado ou (c) quando
		// encontrar um estado objetivo durante a subida de encosta de qualquer
		// problema gerado.
		while ((step < maxIterations) && (delta > MIN_VALUE)) {
			// procura o maior valor de cada problema por meio da busca em
			// subida de encosta, e armazena o maior entre eles
			for (Problem<S> problem : problems) {
				Node<S> bestLocal = super.findNode(problem);

				// atualiza o melhor nó de acordo com a função de avaliação
				// (melhor heuristica)
				delta = valueOf(bestNode) - valueOf(bestLocal);
				if (delta < 0) {
					bestNode = bestLocal;
					metrics.set(NODE_VALUE, valueOf(bestNode));
				}
			}

			// reinicia os problemas com mais problemas aleatórios
			problems = SearchUtils.problemsWithRandomIntialStates(p, nExpander, numberOfStates);
			step++;

		}

		return bestNode;
	}
}
