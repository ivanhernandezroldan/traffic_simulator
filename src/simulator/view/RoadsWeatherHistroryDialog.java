package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.Road;
import simulator.model.Weather;

public class RoadsWeatherHistroryDialog extends JDialog implements ActionListener{
	List<List<Pair<String, Weather>>> roadsHistory;
	Controller ctrl;
	JComboBox<Weather> weatherList;
	private JButton closeButton, updateButton;
	Weather weatherLevels[] = {Weather.SUNNY, Weather.CLOUDY, Weather.RAINY, Weather.STORM, Weather.WINDY};
	Weather weather;
	protected int _status;
	public RoadsWeatherHistroryDialog(Frame _parent, List<Road> roads, Controller c, List<List<Pair<String, Weather>>> roadsHistory) {
		super(_parent, true);
		weather = Weather.SUNNY;
		this.roadsHistory = roadsHistory;
		this.ctrl = c;
		initGUI();
	}

	private void initGUI() {
		setTitle("Roads Weather Histrory");
		JPanel mainPanel = new JPanel(new BorderLayout());
		
		JLabel title = new JLabel("Select a weather and press UPDATE to show the roads that have this weather in each tick");
		title.setHorizontalAlignment(JLabel.LEFT);
		mainPanel.add(title, BorderLayout.PAGE_START);
		
		weatherList = new JComboBox<Weather>(weatherLevels);
		weatherList.setMaximumSize(new Dimension(80, 300));
		weatherList.addActionListener(this);
		
		JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		closeButton = new JButton("Close");
		closeButton.addActionListener(this);
		buttonsPanel.add(closeButton);

		updateButton = new JButton("Update");
		updateButton.addActionListener(this);
		buttonsPanel.add(updateButton);

		JPanel centralPanel = new JPanel();
		centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.Y_AXIS));
		
		centralPanel.add(weatherList);
		centralPanel.add(buttonsPanel);
		
		mainPanel.add(centralPanel, BorderLayout.CENTER);
		
		//JPanel table = createViewPanel(new JTable(new RoadWeatherHistoryTableModel(ctrl, roadsHistory)), "Events");
		//table.setPreferredSize(new Dimension(500, 200));
		//mainPanel.add(table);
		
		setContentPane(mainPanel);
		setLocation(((getParent().getWidth() - getParent().getLocation().x)/4) , (int) ((getParent().getHeight() - getParent().getLocation().y)/2.5));
		setMinimumSize(new Dimension(600,125));
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == updateButton) {
			
		}
		else if(e.getSource() == weatherList) {
			weather = weatherLevels[weatherList.getSelectedIndex()];
		}
		else if(e.getSource() == closeButton){
			setVisible(false);
		}
	}
	
	private JPanel createViewPanel(JComponent c, String title) {
		JPanel p = new JPanel( new BorderLayout() );
		// TODO add a framed border to p with title
		p.add(new JScrollPane(c));
		return p;
	}

}
