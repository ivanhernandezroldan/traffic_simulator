package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import simulator.model.Vehicle;


public class ChangeCO2ClassDialog extends JDialog implements ActionListener{

	List<Vehicle> vehicleList;
	JComboBox<String> list;
	JComboBox<String> co2class;
	JSpinner ticks;
	private JButton cancelButton;
	private JButton OKButton;
	protected int _status;
	String vehicle;
	int co2level;
	
	public ChangeCO2ClassDialog(Frame parent, List<Vehicle> _vehicleList) {
		super(parent, true);
		this.vehicleList = _vehicleList;
		vehicle = vehicleList.get(0).toString(); // sin esto no funciona y no entiendo porque, incluso utilizando el .setselectedindex(0) para que ya exista un valor por defecto
		initGUI();
	}
	
	private void initGUI() {
		setTitle("Change CO2 Class");
		JPanel mainPanel = new JPanel(new BorderLayout());
		
		JLabel title = new JLabel("Schedule an event to change the CO2 class of a vehicle after a given number of ticks from now");
		title.setHorizontalAlignment(JLabel.CENTER);
		mainPanel.add(title, BorderLayout.PAGE_START);
		
		String[] names = new String[vehicleList.size()];
		for(int i = 0; i < vehicleList.size(); i++) {
			names[i] = vehicleList.get(i).toString();
		}
		
		String[] co2levels = new String[11];
		for(int i = 0; i < 11; i++) {
			co2levels[i] = String.valueOf(i);
		}
		
		list = new JComboBox<String>(names);
		list.addActionListener(this);
		co2class = new JComboBox<String>(co2levels);		
		co2class.addActionListener(this);
		ticks = new JSpinner(new SpinnerNumberModel(1, 1, 10000, 1));
		JLabel vehiclesTitle = new JLabel("Vehicles: ");
		JLabel classTitle = new JLabel("CO2 Class: ");
		JLabel ticksTitle = new JLabel("Ticks: ");
		
		JPanel selections = new JPanel();
		selections.setLayout(new GridLayout(1, 6, 10,10));
		
		selections.add(Box.createRigidArea(new Dimension(10, 0)));
		selections.add(classTitle);
		selections.add(co2class);
		selections.add(vehiclesTitle);
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

	
	/*public void actionPerformed1(ActionEvent e) {
		if(e.getSource() == ok) {
			List<Pair<String, Integer>> event = new ArrayList<>();
			event.add(new Pair<String, Integer>(vehicle, co2level));
			ctrl.addEvent(new NewSetContClassEvent(getTicks(), event));
		}
		else if(e.getSource() == list) {
			vehicle = vehicleList.get(list.getSelectedIndex()).toString();
		}
		else if(e.getSource() == co2class) {
			co2level = co2class.getSelectedIndex();
		}
	}*/
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == list) {
			vehicle = vehicleList.get(list.getSelectedIndex()).toString();
		}
		else if(e.getSource() == co2class) {
			co2level = co2class.getSelectedIndex();
		}
		else if(e.getSource() == cancelButton){
			_status = 0;
			setVisible(false);
		}
		else if(e.getSource() == OKButton){
			_status = 1;
			setVisible(false);
		}
	}
	
	public int open() {
		setResizable(false);
		setVisible(true);
		return _status;
	}

	public int getTicks() {
		return (int) ticks.getValue();
	}

	public String getVehicle() {
		return vehicle;
	}

	public int getCo2level() {
		return co2level;
	}
}
