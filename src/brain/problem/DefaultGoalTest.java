package brain.problem;

public class DefaultGoalTest<S> implements GoalTest<S> {
	private S goalState;

	public void DefaltGoalTest(S goalState) {
		this.goalState = goalState;
	}

	@Override
	public boolean isGoalState(S state) {
		// TODO Auto-generated method stub
		return goalState.equals(state);
	}

}
