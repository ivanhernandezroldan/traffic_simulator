package simulator.factories;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.NewJunctionEvent;

public class NewJunctionEventBuilder extends Builder<Event>{
	
	private Factory<LightSwitchingStrategy> lssFactory;
	private Factory<DequeuingStrategy> dqsFactory;
	
	public NewJunctionEventBuilder(Factory<LightSwitchingStrategy>
	lssFactory, Factory<DequeuingStrategy> dqsFactory) {
		super("new_junction");
		this.lssFactory = lssFactory;
		this.dqsFactory = dqsFactory;
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		try{
			int time = data.getInt("time");
			String id = data.getString("id");
			JSONArray coors = data.getJSONArray("coor");
			int coorX = coors.getInt(0);
			int coorY = coors.getInt(1);
			LightSwitchingStrategy lss = lssFactory.createInstance(data.getJSONObject("ls_strategy"));
			DequeuingStrategy dqs = dqsFactory.createInstance(data.getJSONObject("dq_strategy"));
 			NewJunctionEvent jEvent = new NewJunctionEvent(time, id, lss, dqs, coorX, coorY);
 			return jEvent;
		}
		catch(JSONException e){
			return null;
		}	}

}
