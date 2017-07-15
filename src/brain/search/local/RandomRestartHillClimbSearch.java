package brain.search.local;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToDoubleFunction;

import brain.problem.Problem;
import brain.search.SearchUtils;
import brain.util.node.Node;
import brain.util.node.NodeExpander;

public class RandomRestartHillClimbSearch<S> extends HillClimbSearch<S> {

	private final int MAX_ITERATIONS = 100;
	private final double MIN_VALUE = 0.001;
	private int numberOfStates;

	public RandomRestartHillClimbSearch(NodeExpander<S> nExpander, ToDoubleFunction<Node<S>> h, int numberOfStates) {

		super(nExpander, h);
		this.numberOfStates = numberOfStates;
	}

	@Override
	public Node<S> findNode(Problem<S> p) {
		clearMetrics();
		List<Problem<S>> problems = problemsWithRandomIntialStates(p);

		Node<S> bestNode = nExpander.createRootNode(p.getInicialState());

		int step = 0;
		double delta = Double.POSITIVE_INFINITY;
		while ((step <= MAX_ITERATIONS) && (delta < MIN_VALUE)) {
			for (Problem<S> problem : problems) {
				Node<S> bestLocal = super.findNode(problem);

				delta = valueOf(bestNode) - valueOf(bestLocal);
				if (delta < 0) {
					bestNode = bestLocal;
				}

			}
			problems = problemsWithRandomIntialStates(p);
			step++;

		}

		return bestNode;

	}

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
