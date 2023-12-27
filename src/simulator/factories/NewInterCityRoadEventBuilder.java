package simulator.factories;

import org.json.JSONException;
import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewInterCityRoad;
import simulator.model.Weather;

public class NewInterCityRoadEventBuilder extends Builder<Event>{

	public NewInterCityRoadEventBuilder() {
		super("new_inter_city_road");
	}

	
	@Override
	protected Event createTheInstance(JSONObject data) {
		try{
			int time = data.getInt("time");
			String id = data.getString("id");
			String juncSrc = data.getString("src");
 			String juncDest = data.getString("dest");
 			int length = data.getInt("length");
 			int co2limit = data.getInt("co2limit");
 			int maxspeed = data.getInt("maxspeed");
 			Weather w = data.getEnum(Weather.class, "weather");
 			NewInterCityRoad revent = new NewInterCityRoad(time, id, juncSrc, juncDest, length, co2limit, maxspeed, w);
 			return revent;
		}
		catch(JSONException e){
			return null;
		}
	}
	
}
