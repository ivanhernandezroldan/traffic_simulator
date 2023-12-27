package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.NewSetContClassEvent;

public class SetContClassEventBuilder extends Builder<Event>{

	public SetContClassEventBuilder() {
		super("set_cont_class");
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		try{
			int time = data.getInt("time");
			JSONArray info = data.getJSONArray("info");
			List<Pair<String,Integer>> ws = new ArrayList<>();
			for(int i = 0; i < info.length(); i++) {
				JSONObject json = info.getJSONObject(i);
				String vehicle = json.getString("vehicle");
				int contClass = json.getInt("class");
				Pair<String, Integer> p = new Pair<>(vehicle, contClass);
				ws.add(p);
			}
			NewSetContClassEvent contClassEvent = new NewSetContClassEvent(time, ws);
			return contClassEvent;
		}
		catch(JSONException e){
			return null;
		}
	}

}
