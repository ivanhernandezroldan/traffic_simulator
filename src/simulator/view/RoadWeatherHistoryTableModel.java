package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Weather;

public class RoadWeatherHistoryTableModel extends AbstractTableModel implements TrafficSimObserver {
	private List<List<Pair<String, Weather>>> roadsHistory;
	private int ticks;
	private String[] columns = {"Tick", "Roads"};
	public RoadWeatherHistoryTableModel(Controller _ctrl, List<List<Pair<String, Weather>>> roadList) {
		this.ticks = _ctrl.getSimulator().getTime();
		this.roadsHistory = roadList;
		_ctrl.addObserver(this);
	}
	
	@Override
	public int getRowCount() {
		return this.ticks;
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
		/*if(columnIndex == 0) {
			return roadsHistory.get(rowIndex).getTime();
		}
		else {
			return roadsHistory.get(rowIndex).toString();
		}*/
		//me ha faltado completar esto
		return null;
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
		/*this.roadsHistory = roads2;
		this.fireTableDataChanged();*/
	}

	@Override
	public void onError(String err) {}
	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {}

}
