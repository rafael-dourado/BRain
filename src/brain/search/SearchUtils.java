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

	public static <S> Node<S> getRandomFromList(List<Node<S>> children) {
		Random r = new Random();
		int randomIndex = r.nextInt(children.size());
		return children.get(randomIndex);
	}

}
