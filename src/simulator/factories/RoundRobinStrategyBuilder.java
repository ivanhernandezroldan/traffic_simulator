package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.RoundRobinStrategy;

public class RoundRobinStrategyBuilder extends Builder<LightSwitchingStrategy>{

	public RoundRobinStrategyBuilder() {
		super("round_robin_lss");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected LightSwitchingStrategy createTheInstance(JSONObject data) {
		if(data.get("timeslot") != null) {
			int i = data.getInt("timeslot");
			RoundRobinStrategy strategy = new RoundRobinStrategy(i);
			return strategy;
		}
		else {
			RoundRobinStrategy strategy = new RoundRobinStrategy(1);
			return strategy;
		}
	}

}
