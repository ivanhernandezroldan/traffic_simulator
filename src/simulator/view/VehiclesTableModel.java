package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;

public class VehiclesTableModel extends AbstractTableModel implements TrafficSimObserver {
	private List<Vehicle> vehicles;
	private String[] columns = {"Id", "Location", "Itinerary", "CO2 Class", "Max. Speed", "Speed", "Total CO2", "Distance"};
	public VehiclesTableModel(Controller _ctrl) {
		_ctrl.addObserver(this);
	}

	@Override
	public int getRowCount() {
		return this.vehicles.size();
	}

	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return columns[columnIndex];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		String value = "";
		Vehicle v = this.vehicles.get(rowIndex);
		switch(columnIndex) {
		case 0:{
			value += v.getId();
			break;
		}
		case 1:{
			switch(v.getStatus()) {
			case PENDING:{
				value += "Pending";
				break;
			}
			case ARRIVED:{
				value += "Arrived";
				break;
			}
			case TRAVELING:
				value += v.getRoad() + ":" + v.getLocation();
				break;
			case WAITING:
				value += "Waiting:" + v.getItinerary().get(v.getJunctionIndex());
				break;
			default:{
				break;
			}
			}
			break;
		}
		case 2:{
			value += v.getItinerary();
			break;
		}
		case 3:{
			value += v.getPollutionGrade();
			break;
		}
		case 4:{
			value += v.getMaxSpeed();
			break;
		}
		case 5:{
			value += v.getSpeed();
			break;
		}
		case 6:{
			value += v.getTotalPollution();
			break;
		}
		case 7:{
			value += v.getDistanceCovered();
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
		update(map.getVehicles());
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		update(map.getVehicles());
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		update(map.getVehicles());
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		update(map.getVehicles());
	}

	private void update(List<Vehicle> list) {
		this.vehicles = new ArrayList<>(list);
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
	public boolean isCellEditable(int rowIndex, int columnIndex) {return false;}*/
}
