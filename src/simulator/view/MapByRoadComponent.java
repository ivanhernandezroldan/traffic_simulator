package simulator.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;

public class MapByRoadComponent extends JComponent implements TrafficSimObserver{
	
	private static final int _JRADIUS = 10;
	
	private static final Color _BG_COLOR = Color.WHITE;
	private static final Color _GREEN_LIGHT_COLOR = Color.GREEN;
	private static final Color _RED_LIGHT_COLOR = Color.RED;
	private static final Color _JUNCTION_COLOR = Color.BLUE;
	private static final Color _JUNCTION_LABEL_COLOR = new Color(200, 100, 0);
	private static final Color _BLACK_COLOR = Color.BLACK;

	private RoadMap map;
	private Image _car;
	private Image SUNNY;
	private Image CLOUDY;
	private Image RAINY;
	private Image WINDY;
	private Image STORM;

	public MapByRoadComponent(Controller _ctrl) {
		initGUI();
		_ctrl.addObserver(this);
		//setPreferredSize (new Dimension (300, 200));
	}

	private void initGUI() {
		_car = loadImage("car.png");
	}
	
	private Image loadImage(String img) {
		Image i = null;
		try {
			return ImageIO.read(new File("resources/icons/" + img));
		} catch (IOException e) {
		}
		return i;
	}

	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		// clear with a background color
		g.setColor(_BG_COLOR);
		g.clearRect(0, 0, getWidth(), getHeight());

		if (map == null || map.getRoads().size() == 0) {
			g.setColor(Color.red);
			g.drawString("No map yet!", getWidth() / 2 - 50, getHeight() / 2);
		} else {
			drawMap(g);
		}
	}

	private void drawMap(Graphics2D g) {
		drawRoads(g);
	}

	private void drawRoads(Graphics2D g) {
		int index = 0;
		for(Road r: map.getRoads()) {
			g.setColor(_BLACK_COLOR);
			g.drawString(r.getId(), 18, (index+1)*50 + 4);
			g.drawLine(50, (index+1)*50, getWidth() - 100, (index+1)*50);
			drawWeather(g, r, index);
			drawContLevl(g, r, index);
			drawVehicles(g,r, index);
			drawJunctions(g,r, index);
			index++;
		}
	}

	private void drawJunctions(Graphics2D g, Road r, int index) {
		g.setColor(_JUNCTION_COLOR);
		g.fillOval(50 - _JRADIUS / 2, (index+1)*50 - _JRADIUS / 2, _JRADIUS, _JRADIUS);
		g.setColor(_JUNCTION_LABEL_COLOR);
		g.drawString(r.getStart().getId(), 50, (index+1)*50 - 8);
		Color junctionColor = _RED_LIGHT_COLOR;
		int idx = r.getEnd().getGreenTrafficLight();
		if (idx != -1 && r.equals(r.getEnd().getInRoads().get(idx))) {
			junctionColor = _GREEN_LIGHT_COLOR;
		}
		g.setColor(junctionColor);
		g.fillOval(getWidth() - 100 - _JRADIUS / 2, (index+1)*50 - _JRADIUS / 2, _JRADIUS, _JRADIUS);
		g.setColor(_JUNCTION_LABEL_COLOR);
		g.drawString(r.getEnd().getId(), getWidth() - 100, (index+1)*50 - 8);
	}

	private void drawContLevl(Graphics2D g, Road r, int index) {
		int C = (int) Math.floor(Math.min((double) r.getTotalPollution()/(1.0 + (double) r.getMaxPollution()),1.0) / 0.19);
		Image contLevel = loadImage("cont_" + C + ".png");
		g.drawImage(contLevel, getWidth() - 40, (index+1)*50 - 17, 32, 32, this);
	}

	private void drawWeather(Graphics2D g, Road r, int index) {
		Image weather = loadImage(r.getWeather().getImageSrc());
		g.drawImage(weather, getWidth() - 83, (index+1)*50 - 17, 32, 32, this);
	}

	private void drawVehicles(Graphics2D g, Road r, int index) {
		for(Vehicle v: r.getVehicleList()) {
			g.drawImage(_car, 50 + (int) ((getWidth() - 100 - 50) * ((double) v.getLocation() / (double) v.getRoad().getLength())), (index +1)*50 - 9, 16, 16, this);
			g.drawString(v.getId(), 50 + (int) ((getWidth() - 100 - 50) * ((double) v.getLocation() / (double) v.getRoad().getLength())), (index+1)*50 - 9);
		}
	}

	private void update(RoadMap map) {
		this.map = map;
		repaint();
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		update(map);
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		update(map);
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		update(map);
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		update(map);
	}

	@Override
	public void onError(String err) {}
	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {}

}
