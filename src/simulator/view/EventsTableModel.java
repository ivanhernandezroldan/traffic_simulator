package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class EventsTableModel extends AbstractTableModel implements TrafficSimObserver {
	
	private List<Event> list;
	private String[] columnNames = {"Time", "Desc."};
	
	public EventsTableModel(Controller _ctrl) {
		_ctrl.addObserver(this);
		list = new ArrayList<Event>();
	}

	
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return list.size();
	}
	
	@Override
	public String getColumnName(int columnIndex) {
		return columnNames[columnIndex];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(columnIndex == 0) {
			return list.get(rowIndex).getTime();
		}
		else {
			return list.get(rowIndex).toString();
		}
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		update(events);
	}

	private void update(List<Event> events) {
		this.list = events;
		this.fireTableDataChanged();
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		update(events);
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		update(events);
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		update(events);
	}

	@Override
	public void onError(String err) {}



}
