 package simulator.model;

import java.util.ArrayList;
import java.util.List;
import simulator.misc.SortedArrayList;
import org.json.JSONObject;

public class TrafficSimulator implements Observable<TrafficSimObserver>{
	// (I)-> El metodo on error puede ser que no este bien implementado pq necesite llamarse en mas ocasiones , no se...
		private RoadMap map;
		private List<Event> eventList;
		private int time;
		private List<TrafficSimObserver> observers; 
		
		public TrafficSimulator() {
			this.observers = new ArrayList<TrafficSimObserver>();
			this.reset();
		}
		
		public void addEvent(Event e) {
			this.eventList.add(e);
			onEventAdded(this.map, this.eventList, e, this.time); // (I)-> Este time es el del evento o this.time??
		}
		
		
		private void onEventAdded(RoadMap map2, List<Event> eventList2, Event e, int time2) {
			for(TrafficSimObserver o: this.observers) {
				o.onEventAdded(map2, eventList2, e, time2);
			}
		}

		public void advance() { // 
			this.time++;
			onAdvanceStart(this.map, this.eventList, this.time);
			int k = eventList.size();
			for(int i = 0; i < k; i++) {
				try {
					if(this.eventList.get(0).getTime() == this.time) {
						this.eventList.get(0).execute(this.map);
						this.eventList.remove(0);
					}
				}
				catch(Exception e) {
					onError(e.getMessage());
					throw e;
				}
			}
			for(int j = 0; j < this.map.getJunctions().size(); j++) {
				try {
					this.map.getJunctions().get(j).advance(this.time);
				}
				catch(Exception e) {
					onError(e.getMessage());
					throw e;
				}
			}

			for(int j = 0; j < this.map.getRoads().size(); j++) {
				try {	
					this.map.getRoads().get(j).advance(this.time);
				}
				catch(Exception e) {
					onError(e.getMessage());
					throw e;
				}
			}
			this.onAdvanceEnd(this.map, this.eventList, this.time);
		}
		
		private void onAdvanceStart(RoadMap map2, List<Event> eventList2, int time2) {
			for(TrafficSimObserver o: this.observers) {
				o.onAdvanceStart(map2, eventList2, time2);
			}
		}

		private void onError(String message) {
			for(TrafficSimObserver o: this.observers) {
				o.onError(message);
			}
		}

		private void onAdvanceEnd(RoadMap map2, List<Event> eventList2, int time2) {
			for(TrafficSimObserver o: this.observers) {
				o.onAdvanceEnd(map2, eventList2, time2);
			}
		}

		public void reset() { 
			this.map = new RoadMap();
			this.eventList = new SortedArrayList<Event>();
			this.time = 0;
			this.onReset(this.map, this.eventList, this.time); // (I)-> en la constructora de TrafficSimulator llamamos al reset, esto afecta en algo??
		}
		private void onReset(RoadMap map2, List<Event> eventList2, int time2) {
			for(TrafficSimObserver o: this.observers) {
				o.onReset(map2, eventList2, time2);
			}
		}

		public JSONObject report() {
			JSONObject simulador = new JSONObject();
			simulador.accumulate("time", this.time);
			simulador.accumulate("state", this.map.report());
			return simulador;
		}

		@Override
		public void addObserver(TrafficSimObserver o) {
			this.observers.add(o);
			o.onRegister(this.map, this.eventList, this.time);
		}

		@Override
		public void removeObserver(TrafficSimObserver o) {
			this.observers.remove(o);
		}

		public int getTime() {
			return time;
		}

		public void setTime(int time) {
			this.time = time;
		}

		public RoadMap getMap() {
			return map;
		}

		public void setMap(RoadMap map) {
			this.map = map;
		}
		
		
		
		
}
