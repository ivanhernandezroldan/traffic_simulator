package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

public abstract class Road extends SimulatedObject implements Comparable<Road>{
	
	private Junction start;
	private Junction end;
	private int length;
	private int maxSpeed;
	private int speedLimit;
	private int maxPollution;
	private Weather weather;
	private int totalPollution;
	private List<Vehicle> vehicleList;
	Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed,
			int contLimit, int length, Weather weather) {
		super(id);
		
		if(maxSpeed > 0) {
			this.maxSpeed = maxSpeed;
		}
		else {
			throw new IllegalArgumentException("Speed <= 0");
		}
		
		if(contLimit >= 0) {
			this.maxPollution = contLimit;
		}
		else {
			throw new IllegalArgumentException("ContLimit < 0");
		}
		
		if(length > 0) {
			this.length = length;
		}
		else {
			throw new IllegalArgumentException("Length <= 0");
		}
		
		if(srcJunc != null) {
			this.start = srcJunc;
		}
		else {
			throw new IllegalArgumentException("SrcJunc is null");
		}

		if(destJunc != null) {
			this.end = destJunc;	
		}
		else {
			throw new IllegalArgumentException("destJunc is null");
		}
		if(weather != null) {
			this.weather = weather;
		}
		else {
			throw new IllegalArgumentException("Weather is null");
		}
		
		this.speedLimit = maxSpeed;	
		this.totalPollution = 0;
		this.vehicleList = new ArrayList<>();
		srcJunc.addOutGoingRoad(this);
        destJunc.addIncommingRoad(this);
	}

	@Override
	void advance(int time) {
		this.reduceTotalContamination();
		this.updateSpeedLimit();
		for(int i = 0; i < this.vehicleList.size(); i++) {
			int speed = this.calculateVehicleSpeed(this.vehicleList.get(i));
			this.vehicleList.get(i).setSpeed(speed);
			this.vehicleList.get(i).advance(time);
		}
		int j;
		for(int i = 0; i < this.vehicleList.size(); i++) {
			j = 0;
			while(j < i) {
				if(this.vehicleList.get(j).getLocation() < this.vehicleList.get(i).getLocation()) {
					Vehicle aux = this.vehicleList.get(j);
					this.vehicleList.set(j, this.vehicleList.get(i));
					this.vehicleList.set(i, aux);
				}
				j++;
			}
		}
	}

	@Override
	public int compareTo(Road r) {
		return this.getId().compareTo(r.getId());
	}
	
	public Weather getWeather() {
		return weather;
	}

	public int getTotalPollution() {
		return totalPollution;
	}
	
	public List<Vehicle> getVehicleList() {
		return vehicleList;
	}

	public void setVehicleList(List<Vehicle> vehicleList) {
		this.vehicleList = vehicleList;
	}

	public void setTotalPollution(int pollution) {
		this.totalPollution = pollution;
	}
	
	public int getSpeedLimit() {
		return speedLimit;
	}
	
	public void setSpeedLimit(int speed) {
		this.speedLimit = speed;
	}
	
	public int getMaxPollution() {
		return maxPollution;
	}
	
	public Junction getStart() {
		return start;
	}
	
	public int getMaxSpeed() {
		return maxSpeed;
	}
	
	public Junction getEnd() {
		return end;
	}
	
	@Override
	public JSONObject report() {
		JSONObject road = new JSONObject();
		List<String> ids = new ArrayList<String>();
		for(int i = 0; i < this.vehicleList.size(); i++) {
			ids.add(this.vehicleList.get(i).getId());
		}
		List<String> unmodifiableIdList = Collections.unmodifiableList(new ArrayList<>(ids)); 
		road.accumulate("id", this._id);
		road.accumulate("speedlimit", this.speedLimit);
		road.accumulate("weather", this.weather);
		road.accumulate("co2", this.totalPollution);
		road.accumulate("vehicles", unmodifiableIdList);
		return road;
	}
	
	void enter(Vehicle v) {
		if(v.getLocation() == 0) {
			if(v.getSpeed() == 0) {
				this.vehicleList.add(v);
			}
			else{
				throw new IllegalArgumentException("Speed of the vehicle is not 0");
			}
		}
		else {
			throw new IllegalArgumentException("Location of the vehicle is not 0");
		}
	}
	
	void exit(Vehicle v) {
		this.vehicleList.remove(v);
	}
	
	void setWeather(Weather w) {
		if(w != null) {
			this.weather = w;
		}
		else {
			throw new IllegalArgumentException("Weather is null");
		}
	}
	
	void addContamination(int c) {
		if(c >= 0) {
			this.totalPollution += c;
		}
		else {
			throw new IllegalArgumentException("Contamination is < 0");
		}
	}
	
	public int getLength() {
		return length;
	}
	
	abstract void reduceTotalContamination();
	abstract void updateSpeedLimit();
	abstract int calculateVehicleSpeed(Vehicle v);
}
