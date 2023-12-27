package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.MostCrowdedStrategy;

public class MostCrowdedStrategyBuilder extends Builder<LightSwitchingStrategy>{

	public MostCrowdedStrategyBuilder() {
		super("most_crowded_lss");
	}

	@Override
	protected LightSwitchingStrategy createTheInstance(JSONObject data) {
		if(data.get("timeslot") != null) {
			int i = data.getInt("timeslot");
			MostCrowdedStrategy strategy = new MostCrowdedStrategy(i);
			return strategy;
		}
		else {
			MostCrowdedStrategy strategy = new MostCrowdedStrategy(1);
			return strategy;
		}
	}

}
