package simulator.model;

import java.util.List;

import simulator.misc.Pair;

public class SetWeatherEvent extends Event{
	private List<Pair<String,Weather>> ws;
	public SetWeatherEvent(int time, List<Pair<String,Weather>> ws) {
		super(time);
		if(ws != null) {
			this.ws = ws;
		}
		else {
			throw new IllegalArgumentException("The weather is null.");
		}
	}

	@Override
	void execute(RoadMap map) {
		for (Pair<String, Weather> w: this.ws) {
			Road r = map.getRoad(w.getFirst());
			if(r != null) {
				r.setWeather(w.getSecond());
			}
			else {
				throw new IllegalArgumentException("The road does not exist.");
			}
		}
	}
	
	@Override
	public String toString() {
		String descripcion;
		descripcion = "Change Weather: ";
		descripcion += this.ws;
		return descripcion;
	}
}
