package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewVehicleEvent;

public class NewVehicleEventBuilder extends Builder<Event>{

	public NewVehicleEventBuilder() {
		super("new_vehicle");
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		try{
			int time = data.getInt("time");
			String id = data.getString("id");
			int maxspeed = data.getInt("maxspeed");
			int contClass = data.getInt("class");
			JSONArray arrayItinerary = data.getJSONArray("itinerary");
			List<String> itinerary = new ArrayList<>();
			for(int i = 0; i < arrayItinerary.length(); i++) {
				itinerary.add(arrayItinerary.getString(i));
			}
 			NewVehicleEvent vevent = new NewVehicleEvent(time, id, maxspeed, contClass, itinerary);
 			return vevent;
		}
		catch(JSONException e){
			return null;
		}
	}

}
