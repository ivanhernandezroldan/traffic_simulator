package simulator.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.TrafficSimObserver;
import simulator.model.TrafficSimulator;

public class Controller {
	private TrafficSimulator simulator;
	private Factory<Event> eventFactory;
	
	public Controller(TrafficSimulator sim, Factory<Event> eventsFactory)
	{
		if(sim != null && eventsFactory != null) {
			this.simulator = sim;
			this.eventFactory = eventsFactory;
		}
		else throw new IllegalArgumentException("  ");
	}

	public void loadEvents(InputStream in) {
		try {
            JSONObject jo = new JSONObject(new JSONTokener(in));
            JSONArray events = jo.getJSONArray("events");
            for(int i = 0; i < events.length(); i++) {
                Event e = this.eventFactory.createInstance(events.getJSONObject(i));
                this.simulator.addEvent(e);
            }
        }
        catch (JSONException e) {
            System.out.println("ERROR => La entrada JSON no coincide con la esperada");
        }
	}
	
	public void run(int n, OutputStream out) throws IOException {	
		JSONObject object = new JSONObject();
        for(int i = 0; i < n; i++) {
            this.simulator.advance();
            object.accumulate("states", this.simulator.report());
        }
        out.write(object.toString(3).getBytes());
	}
	
	public void run(int n) throws Exception{ // (I)-> es el mismo run que arriba pero sin el OutputStream
        //JSONObject object = new JSONObject();
        for(int i = 0; i < n; i++) {
            this.simulator.advance();
            //object.accumulate("states", this.simulator.report());
        }
        //out.write(object.toString(3).getBytes());
    }
	
	public void reset() {
		this.simulator.reset();
	}
	
	public void addObserver(TrafficSimObserver o) { //(I)-> Se puede pasar una interfaz como parametro???
		this.simulator.addObserver(o);
	}
	
	void removeObserver(TrafficSimObserver o) {
		this.simulator.removeObserver(o);
	}
	
	public void addEvent(Event e) {
		this.simulator.addEvent(e);
	}

	public TrafficSimulator getSimulator() {
		return simulator;
	}

	public void setSimulator(TrafficSimulator simulator) {
		this.simulator = simulator;
	}
	
	
}
