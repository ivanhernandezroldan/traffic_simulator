package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

public class Junction extends SimulatedObject implements Comparable<Junction>{
	
	private List<Road> roadList;
	private Map<Junction,Road> exitRoadMap;
	private List<List<Vehicle>> vehicleQueueList;
	private Map<Road,List<Vehicle>> queueRoadMap;
	private int greenTrafficLight;
	private int lastTrafficLightChange;
	private LightSwitchingStrategy lightStrategy;
	private DequeuingStrategy queueStrategy;
	private int xCoor;
	private int yCoor;
	
	Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy
			dqStrategy, int xCoor, int yCoor) {
			super(id);
			this.roadList = new ArrayList<>(); // carreteras que entran al cruce
			this.exitRoadMap = new HashMap<>(); // this ea conectado al cruce Junction a traves de la carretera saliente de this Road
			this.vehicleQueueList = new ArrayList<>();
			this.greenTrafficLight = -1;
			this.queueRoadMap = new HashMap<>();
			this.lastTrafficLightChange = 0;
			
			if(lsStrategy != null) {
				if(dqStrategy != null) {
					this.lightStrategy = lsStrategy;
					this.queueStrategy = dqStrategy;
				}
				else {
					throw new IllegalArgumentException("Dequeuing strategy is null");
				}
			}
			else {
				throw new IllegalArgumentException("LightSwitching strategy is null");
			}
			
			if(xCoor >= 0 && yCoor >= 0) {
				this.xCoor = xCoor;
				this.yCoor = yCoor;
			}
			else {
				throw new IllegalArgumentException("Coordenates < 0");
			}
			
	}

	void addIncommingRoad(Road r) {
		if(r.getEnd() == this) {
			this.roadList.add(r);
			List<Vehicle> list = new LinkedList<>();
			this.vehicleQueueList.add(list);
			this.queueRoadMap.put(r,list);
		}
		else {
			throw new IllegalArgumentException("The end of the road is not this junction");
		}
	}
	
	void addOutGoingRoad(Road r) {
		if(!this.exitRoadMap.containsKey(r.getEnd())) {
			if(r.getStart() == this) {
				this.exitRoadMap.put(r.getEnd(), r);
			}
			else {
				throw new IllegalArgumentException("The road does not start in this junction");
			}
		}
		else {
			throw new IllegalArgumentException("This junction already contains this road");
		}
	}
	
	void enter(Vehicle v) {
		this.queueRoadMap.get(v.getRoad()).add(v); 
	}
	
	Road roadTo(Junction j) {
		return this.exitRoadMap.get(j);
	}
	
	@Override
	void advance(int time) {
		if(greenTrafficLight != -1) {
			List<Vehicle> list = this.queueStrategy.dequeue(this.vehicleQueueList.get(greenTrafficLight));
			while(!list.isEmpty()) {
				this.vehicleQueueList.get(greenTrafficLight).remove(list.get(0));
				list.get(0).moveToNextRoad();
				list.remove(0);
			}
		}
		
		int nextGreen = this.lightStrategy.chooseNextGreen(roadList, vehicleQueueList, greenTrafficLight, lastTrafficLightChange, time);
		if(nextGreen != greenTrafficLight) {
			greenTrafficLight = nextGreen;
			lastTrafficLightChange = time;
		}
	}

	@Override
	public JSONObject report() {
		
		JSONObject object = new JSONObject();
		List<JSONObject> queueList = new ArrayList<>();
		
		object.accumulate("id", this._id);
		if(this.greenTrafficLight == -1) {
			object.accumulate("green", "none");
		}
		else {
			object.accumulate("green", this.roadList.get(greenTrafficLight).getId());
		}
		
		for(int i = 0; i < this.vehicleQueueList.size(); i++) {
			JSONObject queue = new JSONObject();
			queue.accumulate("road", this.roadList.get(i).getId());
			List<String> ids = new ArrayList<String>();
			for(int j = 0; j < this.vehicleQueueList.get(i).size(); j++) {
				ids.add(this.vehicleQueueList.get(i).get(j).getId()); 
			}
			queue.accumulate("vehicles", ids);
			queueList.add(queue);
		}
		object.accumulate("queues", queueList);
		
		return object;
	}
	
	@Override
	public int compareTo(Junction j) {
		return this.getId().compareTo(j.getId());
	}

	public int getX() {
		return this.xCoor;
	}

	public int getY() {
		return this.yCoor;
	}

	public int getGreenTrafficLight() {
		return this.greenTrafficLight;
	}

	public List<Road> getInRoads() {
		List<Road> list = Collections.unmodifiableList(new ArrayList<>(this.roadList));
		return list;
	}

	public Map<Road,List<Vehicle>> getQueueRoadMap() {
		return queueRoadMap;
	}
}
