package brain.search.undeterministic;

import java.util.ArrayList;
import java.util.List;

import brain.agent.Action;
import brain.problem.ActionsFunction;
import brain.problem.ResultsFunction;
import brain.problem.UndeterministicProblem;

/**
 * 
 * Inteligência Artificial: Uma Abordagem Moderna (3a Edição). Figura 4.11 Um
 * algoritmo de busca em grafos E-OU gerado em ambientes não determinísticos.
 * Retorna um plano condicional que atinge um estado objetivo em todas as
 * circunstâncias. (A notação [x | l] refere-se à lista formada pela adição do
 * objeto x à frente da lista l.)
 * 
 * <pre>
 * função BUSCA-EM-GRAFOS-E-OU(problema) retorna um plano condicional ou falha
 * 
 * 	BUSCA-OU(problema.Estado-Inicial, problem, [ ])
 * _____________________________________________________________________________________________________________
 * função BUSCA-OU(estado, problema, caminho) retorna um plano condicional ou
 * falha 
 * 	se problema.TESTE-OBJETIVO(estado) então retorna o plano vazio 
 * 	se estado estiver no caminho então retorna falha 
 * 	para cada ação no problema.AÇÕES(estado) faça 
 * 		plano ← BUSCA-E(RESULTADO(estado, ação), problema, [estado | caminho]) 
 * 		se plano ≠ falha então retorna [ação | plano] retorna falha
 * _____________________________________________________________________________________________________________
 * função BUSCA-E (estados, problema, caminho) retorna um plano condicional ou falha 
 * 	para cada si em estados faça planoi ← BUSCA-OU(si, problema, caminho)
 * 		se o planoi ← falha então retorna falha 
 * 		retorna [se s<sub>1</sub> então plano<sub>1</sub> senão se s<sub>2</sub> então plano<sub>2</sub> senão . . . se s<sub>n−1</sub> então plano<sub>n−1</sub> senão plano<sub>n</sub>
 * </pre>
 * 
 * @author cpd
 *
 */
public class AndOrGraphSearch<S> {

	/**
	 * <pre>
	 * função BUSCA-OU(estado, problema, caminho) retorna um plano condicional ou
	 * falha 
	 * 	se problema.TESTE-OBJETIVO(estado) então retorna o plano vazio 
	 * 	se estado estiver no caminho então retorna falha 
	 * 	para cada ação no problema.AÇÕES(estado) faça 
	 * 		plano ← BUSCA-E(RESULTADO(estado, ação), problema, [estado | caminho]) 
	 * 		se plano ≠ falha então retorna [ação | plano] 
	 * 	retorna falha
	 * </pre>
	 * 
	 * @param state
	 * @param problem
	 * @param path
	 * @return
	 */
	public Plan<S> orSearch(S state, UndeterministicProblem<S> problem, Path<S> path) {
		ActionsFunction<S> aFunc = problem.getActionsFunction();
		ResultsFunction<S> rFunc = problem.getResultsFunction();

		// se problema.TESTE-OBJETIVO(estado) então retorna o plano vazio
		if (problem.isGoalState(state))
			return new Plan<S>();

		// se estado estiver no caminho então retorna falha
		if (path.contains(state))
			return null;

		// para cada ação no problema.AÇÕES(estado) faça
		for (Action action : aFunc.actions(state)) {

			// plano ← BUSCA-E(RESULTADO(estado, ação), problema, [estado |
			// caminho])
			Plan<S> plan = andSearch(rFunc.getResults(state, action), problem, path.prepend(state));
			if (plan != null)
				return plan.prepend(action);
		}

		return null;
	}

	/**
	 * <pre>
	 * função BUSCA-E (estados, problema, caminho) retorna um plano condicional ou falha 
	 * 	para cada si em estados faça 
	 * 		planoi ← BUSCA-OU(si, problema, caminho)
	 * 		se o planoi ← falha então retorna falha 
	 * 		retorna [se s<sub>1</sub> então plano<sub>1</sub> senão se s<sub>2</sub> então plano<sub>2</sub> senão . . . se s<sub>n−1</sub> então plano<sub>n−1</sub> senão plano<sub>n</sub>
	 * </pre>
	 * 
	 * @param results
	 * @param problem
	 * @param path
	 * @return
	 */
	public Plan<S> andSearch(List<S> results, UndeterministicProblem<S> problem, Path<S> path) {

		List<Plan<S>> plans = new ArrayList<Plan<S>>();
		Plan<S> finalPlan = new Plan<S>();
		// para cada si em estados faça planoi ← BUSCA-OU(si, problema, caminho)
		for (S result : results) {

			// planoi ← BUSCA-OU(si, problema, caminho)
			Plan<S> plan = orSearch(result, problem, path);

			plans.add(plan);

			// se o planoi ← falha então retorna falha
			if (plan == null)
				return null;
		}

		// retorna [se s<sub>1</sub> então plano<sub>1</sub> senão se
		// s<sub>2</sub> então plano<sub>2</sub> senão ... se
		// s<sub>n−1</sub> então plano<sub>n−1</sub> senão plano<sub>n</sub>
		for (int i = 0; i < results.size(); i++) {
			finalPlan.addCondition(results.get(i), plans.get(i));
		}
		return finalPlan;
	}
}
