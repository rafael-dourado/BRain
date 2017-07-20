package brain.problem;

public class UndeterministicProblem<S> {
	protected S InicialState;
	protected ActionsFunction<S> actionsFunction;
	protected ResultsFunction<S> resultsFunction;
	protected StepCostFunction<S> stepCostFunction;
	protected GoalTest<S> goalTest;

	public UndeterministicProblem(S initialState, ActionsFunction<S> actionsFunction, ResultsFunction<S> resultFunction,
			GoalTest<S> goalTest) {

		this(initialState, actionsFunction, resultFunction, goalTest, new DefaultStepCostFunction<S>());
	}

	public UndeterministicProblem(S initialState, ActionsFunction<S> actionsFunction, ResultsFunction<S> resultFunction,
			GoalTest<S> goalTest, StepCostFunction<S> defaultStepCostFunction) {

	}

	public S getInicialState() {
		return InicialState;
	}

	public boolean isGoalState(S state) {
		return goalTest.isGoalState(state);
	}

	public ActionsFunction<S> getActionsFunction() {
		return actionsFunction;
	}

	public ResultsFunction<S> getResultsFunction() {
		return resultsFunction;
	}

	public StepCostFunction<S> getStepCostFunction() {
		return stepCostFunction;
	}

	public GoalTest<S> getGoalTest() {
		return goalTest;
	}
}
