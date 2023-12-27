package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class JunctionsTableModel extends AbstractTableModel implements TrafficSimObserver {
	private List<Junction> junctions;
	private String[] cols = {"Id", "Green", "Queues"};
	public JunctionsTableModel(Controller _ctrl) {
		junctions = new ArrayList<Junction>();
		_ctrl.addObserver(this);
	}

	@Override
	public int getRowCount() {
		return junctions.size();
	}

	@Override
	public int getColumnCount() {
		return cols.length;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return cols[columnIndex];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		String value = "";
		Junction j = junctions.get(rowIndex);
		switch(columnIndex) {
		case 0:{
			value += j.getId();
			break;
		}
		case 1:{
			if(j.getGreenTrafficLight() != -1) {
				value += j.getInRoads().get(j.getGreenTrafficLight()).getId();
			}
			else {
				value += "NONE";
			}
			break;
		}
		case 2:{
			for(Road r: j.getInRoads()) {
				value += " " + r.getId() + ":" + j.getQueueRoadMap().get(r);
			}
			break;
		}
		default:{
			assert(false);
			break;
		}
		}
		return value;
	}
	
	private void update(List<Junction> junctions2) {
		this.junctions = junctions2;
		fireTableDataChanged();
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		update(map.getJunctions());
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		update(map.getJunctions());
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		update(map.getJunctions());
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		update(map.getJunctions());
	}

	@Override
	public void onError(String err) {}
	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {}
	/*

	@Override
	public Class<?> getColumnClass(int columnIndex) {return null;} // de momento vac�o pq no creo que JTable lo vaya a utilizar

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {return false;} // igual q getColumnClass
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {} // igual que getColumnClass

	@Override
	public void addTableModelListener(TableModelListener l) {} // igual que getColumnClass

	@Override
	public void removeTableModelListener(TableModelListener l) {} // igual que getColumnClass*/
}
