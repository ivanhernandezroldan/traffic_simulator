package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;

public class SetWeatherEventBuilder extends Builder<Event>{

	public SetWeatherEventBuilder() {
		super("set_weather");
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		try{
			int time = data.getInt("time");
			JSONArray info = data.getJSONArray("info");
			List<Pair<String,Weather>> ws = new ArrayList<>();
			for(int i = 0; i < info.length(); i++) {
				JSONObject json = info.getJSONObject(i);
				String road = json.getString("road");
				Weather w = json.getEnum(Weather.class, "weather");
				Pair<String, Weather> p = new Pair<>(road, w);
				ws.add(p);
			}
			SetWeatherEvent wevent = new SetWeatherEvent(time, ws);
			return wevent;
		}
		catch(JSONException e){
			return null;
		}
	}

}
