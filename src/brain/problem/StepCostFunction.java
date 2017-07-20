package brain.problem;

import brain.agent.Action;

public interface StepCostFunction<S> {
	/**
	 * Calculate the step cost of taking action a in state s to reach state s'.
	 * Calcula o custo que a��o no estado s ter� para alcan�ar o estado s'
	 * 
	 * @param s
	 *            o estado que a a��o a pertence.
	 * @param a
	 *            a a��o a ser executada.
	 * 
	 * @param sDelta
	 *            o estado resultante da a��o a.
	 * @return custo que a��o no estado s ter� para alcan�ar o estado s'.
	 */
	double c(S s, Action a, S sDelta);

}
