package brain.search.local;

import java.util.ArrayList;
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

	private final int MAX_ITERATIONS = 100;
	private final int MAX_STATES = 5;
	private final double MIN_VALUE = 0.001;
	private int numberOfStates;

	/**
	 * Construtor
	 * 
	 * @param h
	 *            função Heurística
	 * @param numberOfStates
	 *            número de estados que serão gerados aleatoriamente.
	 */
	public RandomRestartHillClimbSearch(ToDoubleFunction<Node<S>> h, int numberOfStates) {
		super(h);

		if (numberOfStates > MAX_STATES)
			this.numberOfStates = MAX_STATES;
		else
			this.numberOfStates = numberOfStates;
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
		List<Problem<S>> problems = problemsWithRandomIntialStates(p);

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
		while ((step < MAX_ITERATIONS) && (delta > MIN_VALUE)) {
			// procura o maior valor de cada problema, e checa qual o maior
			// entre eles
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
			problems = problemsWithRandomIntialStates(p);
			step++;

		}

		return bestNode;
	}

	/**
	 * Gera problemas aleatórios a partir de um problema inicial. O tamanho da
	 * lista dependerá do número de estados definidos no construtor
	 * 
	 * @param problem
	 *            uma formação de um problema
	 * @return uma lista com problemas inciais aleatórios.
	 */
	private List<Problem<S>> problemsWithRandomIntialStates(Problem<S> problem) {
		List<Problem<S>> problems = new ArrayList<Problem<S>>(numberOfStates);

		for (int i = 0; i < numberOfStates; i++) {
			Problem<S> randomInitialStateProblem = new Problem<S>(SearchUtils.generateRandomState(problem, nExpander),
					problem.getActionsFunction(), problem.getResultFunction(), problem.getGoalTest(),
					problem.getStepCostFunction());
			problems.add(randomInitialStateProblem);
		}

		return problems;

	}
}
