package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;

import simulator.control.Controller;

public class MainWindow extends JFrame {
	private Controller _ctrl;
	
	public MainWindow(Controller ctrl) {
		super("Traffic Simulator");
		_ctrl = ctrl;
		initGUI();
	}
	
	private void initGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		mainPanel.add(new ControlPanel(_ctrl, this), BorderLayout.PAGE_START);
		mainPanel.add(new StatusBar(_ctrl),BorderLayout.PAGE_END);
		JPanel viewsPanel = new JPanel(new GridLayout(1, 2));
		mainPanel.add(viewsPanel, BorderLayout.CENTER);
		JPanel tablesPanel = new JPanel();
		tablesPanel.setLayout(new BoxLayout(tablesPanel, BoxLayout.Y_AXIS));
		viewsPanel.add(tablesPanel);
		JPanel mapsPanel = new JPanel();
		mapsPanel.setLayout(new BoxLayout(mapsPanel, BoxLayout.Y_AXIS));
		viewsPanel.add(mapsPanel);
		// tables
		Border b = BorderFactory.createLineBorder(Color.black, 2); 
        JPanel eventsView = createViewPanel(new JTable(new EventsTableModel(_ctrl)), "Events");
        eventsView.setPreferredSize(new Dimension(500, 200));
        eventsView.setBorder(BorderFactory.createTitledBorder(b, "Events"));
        tablesPanel.add(eventsView);
        JPanel vehiclesView = createViewPanel(new JTable(new VehiclesTableModel(_ctrl)), "Events");
        vehiclesView.setPreferredSize(new Dimension(500, 200));
        vehiclesView.setBorder(BorderFactory.createTitledBorder(b, "Vehicles"));
        tablesPanel.add(vehiclesView);
        JPanel roadsView = createViewPanel(new JTable(new RoadsTableModel(_ctrl)), "Events");
        roadsView.setPreferredSize(new Dimension(500, 200));
        roadsView.setBorder(BorderFactory.createTitledBorder(b, "Roads"));
        tablesPanel.add(roadsView);
        JPanel junctionsView = createViewPanel(new JTable(new JunctionsTableModel(_ctrl)), "Events");
        junctionsView.setPreferredSize(new Dimension(500, 200));
        junctionsView.setBorder(BorderFactory.createTitledBorder(b, "Junctions"));
        tablesPanel.add(junctionsView);
		// TODO add other tables
		// ...
		// maps
		JPanel mapView = createViewPanel(new MapComponent(_ctrl), "Map");
		mapView.setPreferredSize(new Dimension(500, 400));
		mapView.setBorder(BorderFactory.createTitledBorder(b, "Map"));
		mapsPanel.add(mapView);
		JPanel map2View = createViewPanel(new MapByRoadComponent(_ctrl), "Map");
		map2View.setPreferredSize(new Dimension(500, 400));
		map2View.setBorder(BorderFactory.createTitledBorder(b, "Map by Road"));
		mapsPanel.add(map2View);
		// TODO add a map for MapByRoadComponent
		// ...
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		this.addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(WindowEvent e) {}

            @Override
            public void windowClosing(WindowEvent e) {
                quit();
            }

			@Override
            public void windowClosed(WindowEvent e) {}

            @Override
            public void windowIconified(WindowEvent e) {}

            @Override
            public void windowDeiconified(WindowEvent e) {}

            @Override
            public void windowActivated(WindowEvent e) {}

            @Override
            public void windowDeactivated(WindowEvent e) {}

        });
		
		this.pack();
		this.setVisible(true);
	}
	
    private void quit() {
    	int option = JOptionPane.showOptionDialog(this, "Are you sure you want to quit?", "quit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, 1); // el 1 es para q x defecto la opcion se�alada sea NO
        if (option == 0) {
            System.exit(0);
        }
	}
	
	private JPanel createViewPanel(JComponent c, String title) {
		JPanel p = new JPanel( new BorderLayout() );
		p.add(new JScrollPane(c));
		return p;
	}
}