package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class NewVehicleEvent extends Event{
	private String id;
	private int maxSpeed;
	private int contClass;
	private List<String> itinerary;
	public NewVehicleEvent(int time, String id, int maxSpeed, int
			contClass, List<String> itinerary) {
			super(time);
			this.id = id;
			this.maxSpeed = maxSpeed;
			this.contClass = contClass;
			this.itinerary = itinerary;
			}

	@Override
	void execute(RoadMap map) {
		List<Junction> listJunctions = new ArrayList<>();
		for(int i = 0; i < itinerary.size(); i++) {
			Junction j = map.getJunction(itinerary.get(i));
			if(j != null) {
				listJunctions.add(j);
			}
			else {
				throw new IllegalArgumentException("There is a junction that does not exist: " + itinerary.get(i));
			}
		}
		Vehicle v = new Vehicle(this.id, this.maxSpeed, this.contClass, listJunctions);
		map.addVehicle(v);
	    v.moveToNextRoad();
	}
	
	@Override
	public String toString() {
		return "New Vehicle '"+this.id+"'";
	}
}