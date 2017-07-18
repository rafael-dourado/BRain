package brain.search.local;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.function.ToDoubleFunction;

import brain.problem.Problem;
import brain.search.QueueFactory;
import brain.search.SearchUtils;
import brain.util.node.Node;

/**
 * Intelig�ncia Artificial: Uma Abordagem Moderna, 3a Edi��o. p�gina 166. <br>
 * <br>
 * 
 * A manuten��o de apenas um n� na mem�ria pode parecer uma rea��o extrema ao
 * problema de limita��o de mem�ria. O algoritmo de busca em feixe local mant�m
 * o controle de k estados, em vez de somente um. Ela come�a com k estados
 * gerados aleatoriamente. Em cada passo, s�o gerados todos os sucessores de
 * todos os k estados. Se qualquer um deles for um objetivo, o algoritmo ir�
 * parar. Caso contr�rio, ele selecionar� os k melhores sucessores a partir da
 * lista completa e repetir� o procedimento.
 * 
 * @author Rafael D.
 *
 * @param <T>
 *            o tipo de estado que ser� submetido ao algoritmo de busca.
 */
public class BeamSearch<S> extends HillClimbSearch<S> {

	private int numberOfStates;
	private final int MAX_ITERATIONS = 1000;

	public BeamSearch(ToDoubleFunction<Node<S>> h, int numberOfStates) {
		super(h);
		this.numberOfStates = numberOfStates;
	}

	@Override
	public Node<S> findNode(Problem<S> p) {

		int iterations = 0;

		// come�a com k estados gerados aleatoriamente
		List<Problem<S>> problems = SearchUtils.problemsWithRandomIntialStates(p, nExpander, numberOfStates);

		Queue<Node<S>> childs = QueueFactory.createPriorityQueue(Comparator.comparing(h::applyAsDouble));
		Node<S> bestNode = null;
		List<Node<S>> bestNodes = new ArrayList<Node<S>>(numberOfStates);

		while (iterations < MAX_ITERATIONS) {
			// Em cada passo, s�o gerados todos os sucessores de todos os k
			// estados
			for (Problem<S> problem : problems) {

				Node<S> node = nExpander.createRootNode(problem.getInicialState());
				if (SearchUtils.isGoalState(problem, node)) {
					globalMaxFound = true;
					return node;
				}

				// s�o gerados todos os sucessores de todos os k estados
				List<Node<S>> nodesExpanded = nExpander.expand(node, problem);

				for (Node<S> nodeExpanded : nodesExpanded) {

					// Se qualquer um deles for um objetivo, o algoritmo ir�
					// parar
					if (SearchUtils.isGoalState(problem, nodeExpanded)) {
						globalMaxFound = true;
						return nodeExpanded;
					}
					// cria uma lista completa com todos os sucessores gerados
					childs.add(nodeExpanded);

				}
			}

			// Seleciona os k melhores sucessores a partir da lista completa
			bestNodes = getBestFrom(childs);

			// repetir� o procedimento
			problems = SearchUtils.createProblems(bestNodes, p, nExpander);
			bestNode = getHighestValueOf(bestNodes);
		}

		return bestNode;
	}

	private List<Node<S>> getBestFrom(Queue<Node<S>> childs) {
		List<Node<S>> bestNodes = new ArrayList<Node<S>>(numberOfStates);
		Iterator<Node<S>> nodesIterator = childs.iterator();

		for (int i = 0; i < numberOfStates; i++) {
			bestNodes.add(nodesIterator.next());
		}
		return bestNodes;
	}
}
