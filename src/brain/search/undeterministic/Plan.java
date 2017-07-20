package brain.search.undeterministic;

import java.util.ArrayList;
import java.util.List;

import brain.agent.Action;

public class Plan<S> {
	List<Condition<S>> conditions;
	List<Action> actions = new ArrayList<Action>();

	public Plan() {
		conditions = new ArrayList<Condition<S>>();

	}

	public Plan<S> prepend(Action action) {
		actions.add(action);
		return this;
	}

	public void addCondition(S state, Plan<S> plan) {
		conditions.add(new Condition<S>(state, plan));

	}
}
