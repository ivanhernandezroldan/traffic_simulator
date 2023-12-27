package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class RoadsTableModel extends AbstractTableModel implements TrafficSimObserver {
	private List<Road> roads;
	private String[] columns = {"Id", "Lenght", "Weather", "Max.Speed", "Speed Limit", "Total CO2", "CO2 Limit"};
	public RoadsTableModel(Controller _ctrl) {
		this.roads = new ArrayList<Road>();
		_ctrl.addObserver(this);
	}

	@Override
	public int getRowCount() {
		return this.roads.size();
	}

	@Override
	public int getColumnCount() {
		return this.columns.length;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return this.columns[columnIndex];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		String value = "";
		Road r = this.roads.get(rowIndex);
		switch(columnIndex) {
		case 0:{
			value += r.getId();
			break;
		}
		case 1:{
			value += r.getLength();
			break;
		}
		case 2:{
			value += r.getWeather();
			break;
		}
		case 3:{
			value += r.getMaxSpeed();
			break;
		}
		case 4:{
			value += r.getSpeedLimit();
			break;
		}
		case 5:{
			value += r.getTotalPollution();
			break;
		}
		case 6:{
			value += r.getMaxPollution();
			break;
		}
		default:{
			assert (false);
			break;
		}
		}
		return value;
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		update(map.getRoads());
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		update(map.getRoads());
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		update(map.getRoads());
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		update(map.getRoads());
	}

	private void update(List<Road> roads2) {
		this.roads = roads2;
		this.fireTableDataChanged();
	}

	@Override
	public void onError(String err) {}
	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {}

	/*@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {}

	@Override
	public void addTableModelListener(TableModelListener l) {}

	@Override
	public void removeTableModelListener(TableModelListener l) {}
		
	@Override
	public Class<?> getColumnClass(int columnIndex) {return null;}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {return false;}
	*/
}

