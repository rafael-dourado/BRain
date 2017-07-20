package brain.problem;

import brain.agent.Action;

public class DefaultStepCostFunction<S> implements StepCostFunction<S> {

	@Override
	public double c(S s, Action a, S sDelta) {
		// TODO Auto-generated method stub
		return 1;
	}

}
