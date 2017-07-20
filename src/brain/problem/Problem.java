package brain.problem;

public class Problem<S> {
	protected S InicialState;
	protected ActionsFunction<S> actionsFunction;
	protected ResultFunction<S> resultFunction;
	protected StepCostFunction<S> stepCostFunction;
	protected GoalTest<S> goalTest;

	/**
	 * Constroi um problema com os componentes específicos, e com custo de
	 * passos padrão (1 por passo)
	 * 
	 * @param initialState.
	 *            O estado inicial que o agente incia
	 * @param actionsFunction
	 *            uma descrição de possível ações para o agente.
	 * @param resultFunction
	 *            uma descrição do que cada ação faz.
	 * @param goalTest
	 *            teste que determina se o estado resultante da ação é um estado
	 *            objetivo.
	 */

	public Problem(S initialState, ActionsFunction<S> actionsFunction, ResultFunction<S> resultFunction,
			GoalTest<S> goalTest) {

		this(initialState, actionsFunction, resultFunction, goalTest, new DefaultStepCostFunction<S>());
	}

	public Problem(S initialState, ActionsFunction<S> actionsFunction, ResultFunction<S> resultFunction,
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

	public ResultFunction<S> getResultFunction() {
		return resultFunction;
	}

	public StepCostFunction<S> getStepCostFunction() {
		return stepCostFunction;
	}

	public GoalTest<S> getGoalTest() {
		return goalTest;
	}
}
