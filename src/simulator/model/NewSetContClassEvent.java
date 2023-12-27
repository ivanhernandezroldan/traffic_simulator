package simulator.model;

import java.util.List;

import simulator.misc.Pair;

public class NewSetContClassEvent extends Event{
	private List<Pair<String,Integer>> cs;
	public NewSetContClassEvent(int time, List<Pair<String,Integer>> cs) {
		super(time);
		if(cs != null) {
			this.cs = cs;
		}
		else {
			throw new IllegalArgumentException("The contamination class is null");
		}
		}

	@Override
	void execute(RoadMap map) {
		for (Pair<String, Integer> w: this.cs) {
			Vehicle v = map.getVehicle(w.getFirst());
			if(v != null) {
				v.setContaminationClass(w.getSecond());
			}
			else {
				throw new IllegalArgumentException("The vehicle does not exist");
			}
		}
	}
	
	@Override
	public String toString() {
		String descripcion;
		descripcion = "Change CO2 Class: ";
		descripcion += this.cs;
		return descripcion;
	}
}
