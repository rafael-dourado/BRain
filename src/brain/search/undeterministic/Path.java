package brain.search.undeterministic;

import java.util.ArrayList;
import java.util.List;

public class Path<S> {
	List<S> path = new ArrayList<S>();

	public boolean contains(S state) {
		// TODO Auto-generated method stub
		return path.contains(state);
	}

	public Path<S> prepend(S state) {
		// TODO Auto-generated method stub
		path.add(0, state);
		return this;
	}

}
