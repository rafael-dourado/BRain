package brain.problem;

import java.util.Set;

import brain.agent.Action;

public interface ActionsFunction<S> {
	/**
	 * Dado um estado particular s, retorna uma coleção de ações que podem ser
	 * executadas em s.
	 * 
	 * @param s
	 *            um estado particular.
	 * @return um conjunto de ações que podem ser executadas em s
	 * 
	 */
	Set<Action> actions(S s);
}
