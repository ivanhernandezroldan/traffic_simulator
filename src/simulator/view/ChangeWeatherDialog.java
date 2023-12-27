package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.NewSetContClassEvent;
import simulator.model.Road;
import simulator.model.SetWeatherEvent;
import simulator.model.Vehicle;
import simulator.model.Weather;

public class ChangeWeatherDialog extends JDialog implements ActionListener{

	Controller ctrl;
	List<Road> roadList;
	JComboBox<String> list;
	JComboBox<Weather> weatherList;
	JSpinner ticks;
	private JButton cancelButton, OKButton;
	Weather weatherLevels[] = {Weather.SUNNY, Weather.CLOUDY, Weather.RAINY, Weather.STORM, Weather.WINDY};
	Weather weather;
	String road;
	protected int _status;
	
	public ChangeWeatherDialog(Frame parent, List<Road> _roadList) {
		super(parent, true);
		this.roadList = _roadList;
		weather = Weather.SUNNY;
		road = roadList.get(0).toString();
		initGUI();
	}
	
	public void initGUI() {
		setTitle("Change Weather");
		JPanel mainPanel = new JPanel(new BorderLayout());
		
		JLabel title = new JLabel("Schedule an event to change the weather of a road after a given number of ticks from now");
		title.setHorizontalAlignment(JLabel.CENTER);
		mainPanel.add(title, BorderLayout.PAGE_START);
		
		String[] names = new String[roadList.size()];
		for(int i = 0; i < roadList.size(); i++) {
			names[i] = roadList.get(i).toString();
		}
		
		
		list = new JComboBox<String>(names);
		list.addActionListener(this);
		weatherList = new JComboBox<Weather>(weatherLevels);
		weatherList.addActionListener(this);
		ticks = new JSpinner(new SpinnerNumberModel(1, 1, 10000, 1));
		JLabel roadsTitle = new JLabel("Road: ");
		JLabel weatherTitle = new JLabel("Weather: ");
		JLabel ticksTitle = new JLabel("Ticks: ");

		JPanel selections = new JPanel();
		selections.setLayout(new GridLayout(1, 6, 10,10));
		
		selections.add(Box.createRigidArea(new Dimension(10, 0)));
		selections.add(weatherTitle);
		selections.add(weatherList);
		selections.add(roadsTitle);
		selections.add(list);
		selections.add(ticksTitle);
		selections.add(ticks);
		selections.add(Box.createRigidArea(new Dimension(10, 0)));
		
		mainPanel.add(selections, BorderLayout.CENTER);
		
		JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		mainPanel.add(buttonsPanel, BorderLayout.PAGE_END);

		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		buttonsPanel.add(cancelButton);

		OKButton = new JButton("OK");
		OKButton.addActionListener(this);
		buttonsPanel.add(OKButton);
		
		setContentPane(mainPanel);
		setLocation(((getParent().getWidth() - getParent().getLocation().x)/4) , (int) ((getParent().getHeight() - getParent().getLocation().y)/2.5));
		setMinimumSize(new Dimension(600,125));
		setVisible(false);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == OKButton) {
			_status = 1;
			setVisible(false);
		}
		else if(e.getSource() == list) {
			road = roadList.get(list.getSelectedIndex()).toString();
		}
		else if(e.getSource() == weatherList) {
			weather = weatherLevels[weatherList.getSelectedIndex()];
		}
		else if(e.getSource() == cancelButton){
			_status = 0;
			setVisible(false);
		}
	}
	
	public int open() {
		setResizable(false);
		setVisible(true);
		//this.setLocationRelativeTo(getParent());
		return _status;
	}
	
	public int getTicks() {
		return (int)ticks.getValue();
	}
	
	public String getRoad() {
		return this.road;
	}
	
	public Weather getWeather() {
		return this.weather;
	}

}