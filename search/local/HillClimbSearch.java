package brain.search.local;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.ToDoubleFunction;

import brain.agent.Action;
import brain.problem.Problem;
import brain.search.SearchForActions;
import brain.search.SearchForState;
import brain.search.SearchUtils;
import brain.search.informed.Informed;
import brain.util.metrics.Metrics;
import brain.util.node.Node;
import brain.util.node.NodeExpander;

/**
 * 
 * Inteligência Artificial: Uma Abordagem Moderna (3a Edição): Figura 4.2,
 * página 161.<br>
 * 
 * <pre>
 * função SUBIDA-DE-ENCOSTA(problema) retorna um estado que é um máximo local
 *  	corrente ← CRIAR-NÓ(ESTADO-INICIAL[problema])
 *  	repita
 *  		 vizinho ← um sucessor de corrente com valor mais alto 
 *  		 se VALOR[vizinho] VALOR[corrente] então retornar ESTADO[corrente]
 *  		 corrente ← vizinho
 * </pre>
 * 
 * O algoritmo de busca é simplesmente um laço repetitivo que se move de forma
 * contínua no sentido do valor crescente, isto é, encosta acima. O algoritmo
 * termina quando alcança um “pico” em que nenhum vizinho tem valor mais alto. O
 * algoritmo não mantém uma árvore de busca e, assim, a estrutura de dados do nó
 * atual só precisa registrar o estado e o valor de sua função objetivo. A busca
 * de subida de encosta não examina antecipadamente valores de estados além dos
 * vizinhos imediatos do estado corrente.
 * 
 * @author Rafael D.
 * 
 * 
 * @param <S>
 *            O tipo de estado que será submetido ao algoritmo de busca.
 */
public class HillClimbSearch<S> implements SearchForActions<S>, SearchForState<S>, Informed<S> {

	NodeExpander<S> nExpander;
	Metrics metrics = new Metrics();
	private ToDoubleFunction<Node<S>> h;
	private final String NODES_EXPANDED = "nodesExpaded";
	private final String NODE_VALUE = "nodeValue";
	private boolean globalMaxFound = false;

	/**
	 * Contrutor
	 * 
	 */
	public HillClimbSearch(ToDoubleFunction<Node<S>> h) {
		this(new NodeExpander<S>(), h);
	}

	public HillClimbSearch(NodeExpander<S> nExpander, ToDoubleFunction<Node<S>> h) {
		this.h = h;
		this.nExpander = nExpander;
		this.nExpander.addNodeListener((node) -> metrics.incrementInt(NODES_EXPANDED));
	}

	/**
	 * 
	 * 
	 */
	/**
	 * função SUBIDA-DE-ENCOSTA(problema) retorna um estado que é um máximo
	 * local. Se o máximo local também for um máximo global, ou seja, for
	 * encontrado um estado objetivo, a função {@link #globalMaxFound()}, quando
	 * chamada, irá retornar true.
	 * 
	 * @param p
	 *            uma formação do problema
	 * @return um nó que é um máximo local.
	 */
	public Node<S> findNode(Problem<S> p) {
		clearMetrics();
		// corrente ← CRIAR-NÓ(ESTADO-INICIAL[problema])
		Node<S> current = nExpander.createRootNode(p.getInicialState());
		if (SearchUtils.isGoalState(p, current)) {
			globalMaxFound = p.isGoalState(current.getState());
			return current;
		}

		// vizinho
		Node<S> bestNeighbor;

		boolean done = false;
		// repita
		while (!done) {

			List<Node<S>> children = nExpander.expand(current, p);
			metrics.incrementInt(NODES_EXPANDED);
			// vizinho ← um sucessor de corrente com valor mais alto
			bestNeighbor = getHighestValueOf(children);

			// se VALOR[vizinho] < VALOR[corrente] então retornar
			// ESTADO[corrente]
			if (valueOf(bestNeighbor) < valueOf(current)) {
				globalMaxFound = p.isGoalState(current.getState());
				metrics.set(NODE_VALUE, valueOf(current));
				return current;
			}

			// corrente ← vizinho
			current = bestNeighbor;

		}

		return null;
	}

	/**
	 * Retorna {@code true} se a subida de encosta encontrar um máximo global
	 * 
	 * @return {@code true}se a subida de encosta encontrar um máximo global
	 */
	public boolean globalMaxFound() {
		return this.globalMaxFound;
	}

	@Override
	public S findState(Problem<S> p) {
		this.nExpander.useParentLinks(false);
		// TODO Auto-generated method stub
		return this.findNode(p).getState();
	}

	@Override
	public List<Action> findActions(Problem<S> p) {
		this.nExpander.useParentLinks(true);
		Node<S> node = this.findNode(p);
		return SearchUtils.getSequenceOfActions(node);
	}

	@Override
	public Metrics getMetrics() {

		return metrics;
	}

	@Override
	public void addNodeListener(Consumer<Node<S>> listener) {
		this.nExpander.addNodeListener(listener);

	}

	@Override
	public boolean removeNodeListener(Consumer<Node<S>> listener) {

		return this.nExpander.removeNodeListener(listener);
	}

	@Override
	public void setHeuristicFunction(ToDoubleFunction<Node<S>> h) {
		this.h = h;

	}

	/**
	 * Calcula o valor e retorna o maior nó de uma lista.
	 * 
	 * @param children
	 *            uma lista de nós.
	 * @return o maior nó de uma lista.
	 * @see {@link #valueOf(Node)}
	 */
	private Node<S> getHighestValueOf(List<Node<S>> children) {
		double highest = Double.NEGATIVE_INFINITY;
		Node<S> bestNode = null;
		for (Node<S> child : children) {
			if (valueOf(child) > highest) {
				bestNode = child;
				highest = valueOf(child);
			}
		}
		return bestNode;
	}

	/**
	 * Calcula o valor do nó. Em uma subida de encosta, o valor do nó é
	 * calculado a partir de sua heurística. Quanto menor a heurística
	 * (considerando que ela é sempre maior ou igual a 0), melhor será o valor
	 * do nó.
	 * 
	 * @param child
	 *            o nó que será calculado.
	 * @return o valor do nó.
	 */
	private double valueOf(Node<S> child) {
		// subida de encosta considera a melhor heurística;
		return -1 * h.applyAsDouble(child);
	}

	private void clearMetrics() {
		metrics.set(NODES_EXPANDED, 0);
		metrics.set(NODE_VALUE, 0);
	}
}
