package test.exercises.ex337;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import brain.agent.Action;
import brain.agent.DynamicAction;
import brain.problem.ActionsFunction;
import brain.problem.ResultFunction;
import brain.problem.StepCostFunction;
import brain.util.math.geometry.Point2D;

public class RobotFunctions {
	private static ActionsFunction _actionsFunction;
	private static ResultFunction _resultFunctions;
	private static StepCostFunction _sCFunction;

	public static ActionsFunction getActions() {
		if (_actionsFunction == null) {
			_actionsFunction = new RobotActionsFunction();
		}

		return _actionsFunction;
	}

	public static ResultFunction getResult() {
		if (_resultFunctions == null) {
			_resultFunctions = new RobotResultFunction();
		}

		return _resultFunctions;
	}

	public static StepCostFunction getStepCostFunction() {
		if (_sCFunction == null) {
			_sCFunction = new RobotStepCostFunction();
		}

		return _sCFunction;

	}

	private static class RobotActionsFunction implements ActionsFunction {
		@Override
		public Set<Action> actions(Object s) {
			Robot robot = (Robot) s;
			List<Point2D> nextMovements = robot.getNextMovements();
			Set<Action> movements = new HashSet<Action>();

			for (Point2D nextMov : nextMovements) {
				movements.add(new Movement(nextMov));
			}

			return movements;
		}
	}

	private static class RobotStepCostFunction implements StepCostFunction {
		@Override
		public double c(Object s, Action a, Object sDelta) {
			Robot robot = (Robot) s;
			Robot next = (Robot) sDelta;
			return robot.distanceOf(next.getState());
		}
	}

	private static class RobotResultFunction implements ResultFunction {
		@Override
		public Object result(Object s, Action a) {
			Robot robot = (Robot) s;
			Movement action = (Movement) a;
			Point2D result = new Point2D(action.getMovement());
			robot.moveTo(result);
			return result;
		}
	}

	private static class Movement extends DynamicAction {
		private Point2D movement;

		public Movement(Point2D point) {
			super("Move" + point.toString());
			this.movement = point;
		}

		public Point2D getMovement() {
			return this.movement;
		}
	}
}
