package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

public class Vehicle extends SimulatedObject implements Comparable<Vehicle>{

	private List<Junction> itinerary;
	private int speed;
	private int maxSpeed;
	private int junctionIndex;
	private VehicleStatus status;
	private Road road;
	private int location;
	private int pollutionGrade;
	private int totalPollution;
	private int distanceCovered;
	Vehicle(String id, int maxSpeed, int contClass,
			List<Junction> itinerary) {
			super(id);
			
			if(maxSpeed > 0) {
				this.maxSpeed = maxSpeed;
			}
			else {
				throw new IllegalArgumentException("MaxSpeed < 0");
			}
			
			if(contClass >= 0 && contClass <= 10) {
				this.pollutionGrade = contClass;
			}
			else {
				throw new IllegalArgumentException("ContClass < 0 || > 10");
			}
	
			if(itinerary.size() >= 2) {
				this.itinerary = Collections.unmodifiableList(new ArrayList<>(itinerary));

			}
			else {
				throw new IllegalArgumentException("Itinerary Size < 2");
			}
			
			this.speed = 0;
			this.status = VehicleStatus.PENDING;
			this.location = 0;
			this.totalPollution = 0;
			this.distanceCovered = 0;
			this.junctionIndex = 0;
	}
	
	public void setSpeed(int s) {
		if(this.speed >= 0) {
			if(s <= this.maxSpeed) {
				this.speed = s;
			}
			else {
				this.speed = this.maxSpeed;
			}
		}
		else {
			throw new IllegalArgumentException("Negative speed.");
		}
	}
	
	public Road getRoad() {
		return road;
	}
	
	public List<Junction> getItinerary(){
		List<Junction> list = Collections.unmodifiableList(new ArrayList<>(this.itinerary));
		return list;
	}

	public void setContaminationClass(int c) {
		if(0 <= c && c <= 10) {
			this.pollutionGrade = c;
		}
		else {
			throw new IllegalArgumentException("Contamination class < 0 || > 10");
		}
	}
	
	@Override
	public void advance(int time) {
		if(this.status == VehicleStatus.TRAVELING) {
			int extrapollution = 0;
			int distanceCovered = 0;
			int distanceToEnd = this.road.getLength() - this.location;
			if(this.location + this.speed < this.road.getLength()) {
				this.location += this.speed;
				distanceCovered = this.speed;
			}
			else {
				this.location += distanceToEnd;
				distanceCovered = distanceToEnd;
			}
			this.distanceCovered += distanceCovered;
			extrapollution = distanceCovered * this.pollutionGrade;
			
			this.road.addContamination(extrapollution);
			this.totalPollution += extrapollution;
			
			if(this.location == this.road.getLength()) {
				this.itinerary.get(this.junctionIndex + 1).enter(this);
                this.junctionIndex++;
                this.status = VehicleStatus.WAITING;

			}
		}
	}

	public void moveToNextRoad() {
		
		if(this.status == VehicleStatus.PENDING) {
			this.road = this.itinerary.get(this.junctionIndex).roadTo(this.itinerary.get(this.junctionIndex + 1));
			this.road.enter(this);
			this.status = VehicleStatus.TRAVELING;
		}
		else if(this.status == VehicleStatus.WAITING){
			this.road.exit(this);
            this.speed = 0;
            this.location = 0;
			if(this.junctionIndex == this.itinerary.size() - 1) {
				this.status = VehicleStatus.ARRIVED;
			}
			else {
				this.road = this.itinerary.get(this.junctionIndex).roadTo(this.itinerary.get(this.junctionIndex + 1));
				this.road.enter(this);
				this.status = VehicleStatus.TRAVELING;
			}
		}
		else {
			throw new IllegalArgumentException("Vehicle status is not WAITING or PENDING");
		}
	}
	
	@Override
	public JSONObject report() {
		JSONObject vehiculo = new JSONObject();
		
		vehiculo.accumulate("id", this._id);
		if(this.status == VehicleStatus.TRAVELING) {
			vehiculo.accumulate("speed", this.speed);
		}
		else {
			vehiculo.accumulate("speed", 0);
		}
		vehiculo.accumulate("distance", this.distanceCovered);
		vehiculo.accumulate("co2", this.totalPollution);
		vehiculo.accumulate("class", this.pollutionGrade);
		vehiculo.accumulate("status", this.status);
		if(this.status == VehicleStatus.ARRIVED || this.status == VehicleStatus.PENDING) {
			
		}
		else {
			vehiculo.accumulate("road", this.road);
			vehiculo.accumulate("location", this.location);
		}
		return vehiculo;
	}
	
	@Override
	public int compareTo(Vehicle v) {
		return this.getId().compareTo(v.getId());
	}
	
	public int getLocation() {
		return location;
	}
	
	public int getSpeed() {
		return speed;
	}
	public int getPollutionGrade() {
		return pollutionGrade;
	}

	public VehicleStatus getStatus() {
		return this.status;
	}

	public int getDistanceCovered() {
		return this.distanceCovered;
	}

	public int getTotalPollution() {
		return this.totalPollution;
	}

	public int getJunctionIndex() {
		return junctionIndex;
	}

	public int getMaxSpeed() {
		return this.maxSpeed;
	}
}
