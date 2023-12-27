package simulator.view;

import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class StatusBar extends JPanel implements TrafficSimObserver{
	
	private int time;
	private String event;
	private JLabel timeText;
	private JLabel eventText;
	
	public StatusBar(Controller _ctrl) {
		_ctrl.addObserver(this);
		initGUI();
	}
	
	private void initGUI() {
		
		this.setOpaque(true);
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		timeText = new JLabel("Time: " + this.time);
		timeText.setLocation(JLabel.LEFT, 0);
		
		eventText = new JLabel(event);
		eventText.setLocation(JLabel.RIGHT, 0);
		this.add(timeText);
		this.add(Box.createHorizontalGlue());
		this.add(eventText);
		
	}
	
	private void update() {
		timeText.setText("Time: " + this.time);
		eventText.setText(event);
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		this.event = "";
		this.time = time;
		update();
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		this.event = "Event added: " + e.toString();
		this.time = time;
		update();
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		this.event = "";
		this.time = time;
		update();
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {}

	@Override
	public void onError(String err) {}

}