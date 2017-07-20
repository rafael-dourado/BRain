package brain.problem;

public interface GoalTest<S> {
	/**
	 * Returns <code>true</code> se o estado dado � um estado objetivo
	 * 
	 * @return <code>true</code> se o estado dado � um estado objetivo.
	 */
	boolean isGoalState(S state);
}
