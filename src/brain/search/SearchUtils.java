package brain.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import brain.agent.Action;
import brain.agent.NoOpAction;
import brain.problem.GoalTest;
import brain.problem.Problem;
import brain.util.node.Node;
import brain.util.node.NodeExpander;

public class SearchUtils {

	public static <S> List<Action> getSequenceOfActions(Node<S> n) {
		List<Node<S>> nodes = n.getPathFromRoot();
		List<Action> actions = new ArrayList<Action>();

		// Nó raiz não é gerado a partir de uma ação, então adiciona um
		// NoOpAction
		if (nodes.size() == 1) {
			actions.add(NoOpAction.No_OP);
		} else {
			// não inicia com i=0 pois node.get(0) é um nó raiz, que não tem uma
			// ação.
			for (int i = 1; i < nodes.size(); i++) {
				actions.add(nodes.get(i).getAction());
			}
		}

		return actions;
	}

	public static <S> List<S> getSequenceOfStates(Node<S> n) {
		List<Node<S>> nodes = n.getPathFromRoot();
		List<S> states = new ArrayList<S>();
		for (int i = 1; i < nodes.size(); i++) {
			states.add(n.getState());
		}

		return states;
	}

	public static List<Action> failure() {
		return Collections.emptyList();
	}

	public static boolean isFailure(List<Action> actions) {
		return actions.isEmpty();
	}

	public static <S> boolean isGoalState(Problem<S> p, Node<S> n) {
		GoalTest<S> gt = p.getGoalTest();

		return gt.isGoalState(n.getState());
	}

	public static <S> S generateRandomState(Problem<S> problem, NodeExpander<S> nExpander) {
		Random r = new Random();
		int maxIteration = r.nextInt(100);
		Node<S> node = nExpander.createRootNode(problem.getInicialState());

		for (int i = 0; i < maxIteration; i++) {
			List<Node<S>> children = nExpander.expand(node, problem);
			node = getRandomFromList(children);
		}

		return node.getState();

	}

	/**
	 * Gera problemas aleatórios a partir de um problema inicial. O tamanho da
	 * lista dependerá do número de estados definidos no construtor
	 * 
	 * @param problem
	 *            uma formação de um problema
	 * @return uma lista com problemas inciais aleatórios.
	 */
	public static <S> List<Problem<S>> problemsWithRandomIntialStates(Problem<S> problem, NodeExpander<S> nExpander,
			int numberOfStates) {
		List<Problem<S>> problems = new ArrayList<Problem<S>>(numberOfStates);

		for (int i = 0; i < numberOfStates; i++) {
			Problem<S> randomInitialStateProblem = new Problem<S>(SearchUtils.generateRandomState(problem, nExpander),
					problem.getActionsFunction(), problem.getResultFunction(), problem.getGoalTest(),
					problem.getStepCostFunction());
			problems.add(randomInitialStateProblem);
		}

		return problems;

	}

	public static <S> Node<S> getRandomFromList(List<Node<S>> children) {
		Random r = new Random();
		int randomIndex = r.nextInt(children.size());
		return children.get(randomIndex);
	}

	public static <S> List<Problem<S>> createProblems(List<Node<S>> bestNodes, Problem<S> problem,
			NodeExpander<S> nExpander) {
		List<Problem<S>> problems = new ArrayList<Problem<S>>(bestNodes.size());
		for (Node<S> node : bestNodes) {
			Problem<S> p = new Problem<S>(node.getState(), problem.getActionsFunction(), problem.getResultFunction(),
					problem.getGoalTest(), problem.getStepCostFunction());
			problems.add(p);
		}
		return problems;
	}

}
