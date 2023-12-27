package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import simulator.misc.SortedArrayList;

public class RoadMap {
	private List<Junction> junctionList;
	private List<Road> roadList;
	private List<Vehicle> vehicleList;
	private Map<String,Junction> junctionMap;
	private Map<String,Road> roadMap;
	private Map<String,Vehicle> vehicleMap;
	
	RoadMap(){
		this.reset();
	}
	
	void addJunction(Junction j) {
		if(!this.junctionMap.containsKey(j._id)) {
			this.junctionList.add(j);
			this.junctionMap.put(j._id, j);
		}
		else {
			throw new IllegalArgumentException("The map already contains the junction: " + j._id);
		}

	}
	
	void addRoad(Road r) {
		if(!this.roadMap.containsKey(r._id)) {
			if(this.junctionMap.containsKey(r.getStart()._id) && this.junctionMap.containsKey(r.getEnd()._id)) {
				this.roadList.add(r);
				this.roadMap.put(r._id, r);
			}
			else {
				throw new IllegalArgumentException("The junctions of the road do not exist.");
			}
		}
		else {
			throw new IllegalArgumentException("The map alrady contains this road.");
		}
	}
	
	void addVehicle(Vehicle v) {
		if(!this.vehicleMap.containsKey(v._id)) {
			boolean exito = true;
			int i = 0;
			while(i < v.getItinerary().size() - 1 && exito) {
				if(v.getItinerary().get(i).roadTo(v.getItinerary().get(i+1)) == null) {
					exito = false;
				}
				i++;
			}
			if(exito) {
				this.vehicleList.add(v);
				this.vehicleMap.put(v._id, v);
			}
			else {
				throw new IllegalArgumentException("The itinerary of the vehicle is wrong.");
			}
		}
		else {
			throw new IllegalArgumentException("The map already contains this vehicle.");
		}
	}
	
	public Junction getJunction(String id) {
		if(this.junctionMap.containsKey(id)) {
			return this.junctionMap.get(id);
		}
		else {
			return null;
		}
	}
	
	public Road getRoad(String id) {
		if(this.roadMap.containsKey(id)) {
			return this.roadMap.get(id);
		}
		else {
			return null;
		}
	}
	
	public Vehicle getVehicle(String id) {
		if(this.vehicleMap.containsKey(id)) {
			return this.vehicleMap.get(id);
		}
		else {
			return null;
		}
	}
	
	public List<Junction> getJunctions(){
		List<Junction> list = Collections.unmodifiableList(new ArrayList<>(this.junctionList));
		return list;
	}
	
	public List<Road> getRoads(){
		List<Road> list = Collections.unmodifiableList(new ArrayList<>(this.roadList));
		return list;
	}
	
	public List<Vehicle> getVehicles(){
		List<Vehicle> list = Collections.unmodifiableList(new ArrayList<>(this.vehicleList));
		return list;
	}
	
	void reset(){
		this.junctionList = new SortedArrayList<>();
		this.junctionMap = new HashMap<>();
		this.roadList = new SortedArrayList<>();
		this.roadMap = new HashMap<>();
		this.vehicleList = new SortedArrayList<>();
		this.vehicleMap = new HashMap<>();
	}
	
	public JSONObject report() {
		JSONObject object = new JSONObject();
		List<JSONObject> junctionList = new ArrayList<JSONObject>();
		List<JSONObject> roadList = new ArrayList<JSONObject>();
		List<JSONObject> vehicleList = new ArrayList<JSONObject>();
		for(int i = 0; i < this.junctionList.size(); i++) {
			junctionList.add(this.junctionList.get(i).report());
		}
		for(int i = 0; i < this.roadList.size(); i++) {
			roadList.add(this.roadList.get(i).report());
		}
		for(int i = 0; i < this.vehicleList.size(); i++) {
			vehicleList.add(this.vehicleList.get(i).report());
		}
		object.accumulate("junctions", junctionList);
		object.accumulate("roads", roadList);
		object.accumulate("vehicles", vehicleList);
		
		return object;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
