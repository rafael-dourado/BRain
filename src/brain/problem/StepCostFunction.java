package brain.problem;

import brain.agent.Action;

public interface StepCostFunction<S> {
	/**
	 * Calculate the step cost of taking action a in state s to reach state s'.
	 * Calcula o custo que ação no estado s terá para alcançar o estado s'
	 * 
	 * @param s
	 *            o estado que a ação a pertence.
	 * @param a
	 *            a ação a ser executada.
	 * 
	 * @param sDelta
	 *            o estado resultante da ação a.
	 * @return custo que ação no estado s terá para alcançar o estado s'.
	 */
	double c(S s, Action a, S sDelta);

}
