package brain.problem;

import java.util.Set;

import brain.agent.Action;

public interface ActionsFunction<S> {
	/**
	 * Dado um estado particular s, retorna uma cole��o de a��es que podem ser
	 * executadas em s.
	 * 
	 * @param s
	 *            um estado particular.
	 * @return um conjunto de a��es que podem ser executadas em s
	 * 
	 */
	Set<Action> actions(S s);
}
