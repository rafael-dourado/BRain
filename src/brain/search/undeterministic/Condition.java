package brain.search.undeterministic;

public class Condition<S> {
	private S state;
	private Plan<S> plan;

	public Condition(S state, Plan<S> plan) {
		this.state = state;
		this.plan = plan;
	}

	public boolean test(S state) {
		return this.state.equals(state);
	}

	public Plan<S> getPlan() {
		return plan;
	}

	@Override
	public String toString() {
		return "if " + state + " then : " + plan;
	}
}
