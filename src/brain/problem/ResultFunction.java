package brain.problem;

import brain.agent.Action;

/**
 * Artificial Intelligence A Modern Approach (3rd Edition): page 67.<br>
 * <br>
 * A description of what each action does; the formal name for this is the
 * transition model, specified by a function RESULT(s, a) that returns the state
 * that results from doing action a in state s. We also use the term successor
 * to refer to any state reachable from a given state by a single action.
 * 
 * @author Ravi Mohan
 * @author Ciaran O'Reilly
 */

public interface ResultFunction<S> {
	/**
	 * Retorna o estado que resulta da execução de uma determinada ação no
	 * estado s.
	 * 
	 * @param s
	 *            um estado particular.
	 * @param a
	 *            uma ação para ser executada em s.
	 * @return o estado que resulta da execução de uma determinada ação no
	 *         estado s.
	 */
	S result(S s, Action a);
}
