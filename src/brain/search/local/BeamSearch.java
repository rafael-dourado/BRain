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
 * Inteligência Artificial: Uma Abordagem Moderna, 3a Edição. página 166. <br>
 * <br>
 * 
 * A manutenção de apenas um nó na memória pode parecer uma reação extrema ao
 * problema de limitação de memória. O algoritmo de busca em feixe local mantém
 * o controle de k estados, em vez de somente um. Ela começa com k estados
 * gerados aleatoriamente. Em cada passo, são gerados todos os sucessores de
 * todos os k estados. Se qualquer um deles for um objetivo, o algoritmo irá
 * parar. Caso contrário, ele selecionará os k melhores sucessores a partir da
 * lista completa e repetirá o procedimento.
 * 
 * @author Rafael D.
 *
 * @param <T>
 *            o tipo de estado que será submetido ao algoritmo de busca.
 */
public class BeamSearch<S> extends HillClimbSearch<S> {

	private int numberOfStates;
	private final int MAX_ITERATIONS = 10000;

	public BeamSearch(ToDoubleFunction<Node<S>> h, int numberOfStates) {
		super(h);
		this.numberOfStates = numberOfStates;
	}

	@Override
	public Node<S> findNode(Problem<S> p) {

		int iterations = 0;

		// começa com k estados gerados aleatoriamente
		List<Problem<S>> problems = SearchUtils.problemsWithRandomIntialStates(p, nExpander, numberOfStates);

		Queue<Node<S>> childs = QueueFactory.createPriorityQueue(Comparator.comparing(h::applyAsDouble));
		Node<S> bestNode = null;
		List<Node<S>> bestNodes = new ArrayList<Node<S>>(numberOfStates);

		while (iterations < MAX_ITERATIONS) {
			// Em cada passo, são gerados todos os sucessores de todos os k
			// estados
			for (Problem<S> problem : problems) {

				Node<S> node = nExpander.createRootNode(problem.getInicialState());
				if (SearchUtils.isGoalState(problem, node)) {
					metrics.set(NODE_VALUE, valueOf(node));
					globalMaxFound = true;
					return node;
				}

				// são gerados todos os sucessores de todos os k estados
				List<Node<S>> nodesExpanded = nExpander.expand(node, problem);
				metrics.incrementInt(NODES_EXPANDED);
				for (Node<S> nodeExpanded : nodesExpanded) {

					// Se qualquer um deles for um objetivo, o algoritmo irá
					// parar
					if (SearchUtils.isGoalState(problem, nodeExpanded)) {
						metrics.set(NODE_VALUE, valueOf(nodeExpanded));
						globalMaxFound = true;
						return nodeExpanded;
					}
					// cria uma lista completa com todos os sucessores gerados
					if(!childs.contains(nodeExpanded))
						childs.add(nodeExpanded);

				}
			}

			// Seleciona os k melhores sucessores a partir da lista completa
			bestNodes = getBestFrom(childs);
			bestNode = getHighestValueOf(bestNodes);
			
			// repetirá o procedimento
			problems = SearchUtils.createProblems(bestNodes, p, nExpander);
			iterations++;
		}
		metrics.set(NODE_VALUE, valueOf(bestNode));
		return bestNode;
	}

	private List<Node<S>> getBestFrom(Queue<Node<S>> childs) {
		List<Node<S>> bestNodes = new ArrayList<Node<S>>(numberOfStates);
		Iterator<Node<S>> nodesIterator = childs.iterator();

		for (int i = 0; i < numberOfStates; i++) {
			if(nodesIterator.hasNext())
				bestNodes.add(nodesIterator.next());
		}
		return bestNodes;
	}
}
