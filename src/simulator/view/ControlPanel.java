package simulator.view;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.NewSetContClassEvent;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.SetWeatherEvent;
import simulator.model.TrafficSimObserver;
import simulator.model.Weather;

public class ControlPanel extends JPanel implements TrafficSimObserver{
	
	private Frame _parent;
	private Controller ctrl;
	private ChangeCO2ClassDialog co2Dialog;
	private ChangeWeatherDialog weatherDialog;
	private RoadsWeatherHistroryDialog rwhDialog;
	private JFileChooser fc;
	private JButton folder;
	private JButton exitButton;
	private JButton runButton;
	private JButton stopButton;
	private JButton changeCO2Button;
	private JButton changeWeatherButton;
	private boolean _stopped;
	private JToolBar toolBar;
	private JSpinner ticksSpinner;
	private int time;
	private RoadMap roadMap;
	private JButton roadsWeatherHistoryButton;
	private List<List<Pair<String, Weather>>> roadsHistory;
	
	public ControlPanel(Controller _ctrl, Frame parent) {
		_ctrl.addObserver(this);
		ctrl = _ctrl;
		_stopped = true;
		_parent = parent;
		roadsHistory = new ArrayList();
		initGUI();
	}

	private void initGUI() {
		
		toolBar = new JToolBar();
		
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		toolBar.add(Box.createRigidArea(new Dimension(20, 0)));
		
		initEventFileChooser();
		
		toolBar.add(Box.createRigidArea(new Dimension(20, 0)));
		
		initChangeCO2Button();
		
		toolBar.add(Box.createRigidArea(new Dimension(20, 0)));
		
		initChangeWeatherButton();
		
		toolBar.add(Box.createRigidArea(new Dimension(20, 0)));
		
		initRoadsWeatherHistrory();
		
		toolBar.add(Box.createRigidArea(new Dimension(20, 0)));
		
		initRunStopTicks();
		
		toolBar.add(Box.createHorizontalGlue());
		
		initExitButton();
		
		toolBar.add(Box.createRigidArea(new Dimension(20, 0)));
		
		this.add(toolBar);
	}

	private void initRoadsWeatherHistrory() {
		this.roadsWeatherHistoryButton = new JButton(new ImageIcon("resources/icons/pie-chart.png"));
		this.roadsWeatherHistoryButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(time!= 0) {
					rwhDialog = new RoadsWeatherHistroryDialog(_parent, roadMap.getRoads(), ctrl, roadsHistory);
				}
			}
		});
		roadsWeatherHistoryButton.setToolTipText("Show road´s weather history");
		toolBar.add(roadsWeatherHistoryButton);
	}

	private void initEventFileChooser() {
		
		Icon icon = new ImageIcon("resources/icons/open.png");
		folder = new JButton(icon);

		folder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fc = new JFileChooser();
				fc.setCurrentDirectory(new File("resources/examples"));
				int returnVal = fc.showOpenDialog(_parent);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					ctrl.reset();
					try{
						InputStream in = new FileInputStream(file);
						ctrl.loadEvents(in);
					}
					catch (FileNotFoundException e1) {
						
						JOptionPane.showMessageDialog(toolBar,"Failed trying to import events","File error", JOptionPane.ERROR_MESSAGE);
					}
					
				}
			}
		});
		
		folder.setToolTipText("Select file");
		
		toolBar.add(folder);
		
	}
	
	private void initChangeCO2Button() {
		this.changeCO2Button = new JButton(new ImageIcon("resources/icons/co2class.png"));
		this.changeCO2Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(time!=0) {
					co2Dialog = new ChangeCO2ClassDialog(_parent, roadMap.getVehicles());
					int opcion = co2Dialog.open();
					if ( opcion == 1) {
						List<Pair<String, Integer>> event = new ArrayList<>();
						event.add(new Pair<String, Integer>(co2Dialog.getVehicle(), co2Dialog.getCo2level()));
						ctrl.addEvent(new NewSetContClassEvent(co2Dialog.getTicks() + time, event));
					} 
				}
			}
		});
		this.changeCO2Button.setToolTipText("Change CO2 Class");
		toolBar.add(changeCO2Button);
	}
	
	private void initChangeWeatherButton() {
		this.changeWeatherButton = new JButton(new ImageIcon("resources/icons/weather.png"));
		this.changeWeatherButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(time!=0) {
					weatherDialog = new ChangeWeatherDialog(_parent, roadMap.getRoads());
					int opcion = weatherDialog.open();
					if ( opcion == 1) {
						List<Pair<String, Weather>> event = new ArrayList<>();
						event.add(new Pair<String, Weather>(weatherDialog.getRoad(), weatherDialog.getWeather()));
						ctrl.addEvent(new SetWeatherEvent(weatherDialog.getTicks() + time, event));
					} 
				}
			}
		});
		this.changeWeatherButton.setToolTipText("Change Weather");
		this.toolBar.add(changeWeatherButton);
	}
	
	private void initExitButton() {
        this.exitButton = new JButton(new ImageIcon("resources/icons/exit.png"));
        this.exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quit();
            }
        });
        
        toolBar.add(this.exitButton, JPanel.RIGHT_ALIGNMENT);
    }

	private void quit() {
        int option = JOptionPane.showOptionDialog(_parent, "Are you sure you want to quit?", "quit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, 1); // el 1 es para q x defecto la opcion se�alada sea NO
        if (option == 0) {
            System.exit(0);
        }
    }
	
	private void initRunStopTicks() {
        this.runButton = new JButton(new ImageIcon("resources/icons/run.png"));
        this.runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	_stopped = false;
            	enableToolBar(false);
                run_sim(getTicks());
            }
        });
        
        toolBar.add(this.runButton);

        this.stopButton = new JButton(new ImageIcon("resources/icons/stop.png"));
        this.stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enableToolBar(true);
            }
        });
        
        toolBar.add(this.stopButton);
        
        ticksSpinner = new JSpinner(new SpinnerNumberModel(10, 1, 10000, 1));
        ticksSpinner.setPreferredSize(new Dimension(80, 35));
        ticksSpinner.setMaximumSize(new Dimension(80, 35));
        
        JLabel titulo = new JLabel("Ticks:");
        titulo.setSize(20, 20);
        titulo.setHorizontalAlignment(JLabel.RIGHT);
        
        toolBar.add(titulo);
        toolBar.add(ticksSpinner);
    }
	
	private void run_sim(int n) {
		if (n > 0 && !_stopped) {
			try {
				ctrl.run(1);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, e.getMessage(), "Run error", JOptionPane.ERROR_MESSAGE);
				_stopped = true;
				return;
			}
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					run_sim(n - 1);
					List<Pair<String,Weather>> list = new ArrayList();
					for(Road r: ctrl.getSimulator().getMap().getRoads()) {
						list.add(new Pair<String, Weather>(r.getId(), r.getWeather()));
					}
					roadsHistory.add(list);
				}
			});
		} else {
			enableToolBar(true);
			_stopped = true;
			
		}
	}
		
	private void enableToolBar(boolean b) {
		folder.setEnabled(b);
		runButton.setEnabled(b);
	    exitButton.setEnabled(b);
	    changeCO2Button.setEnabled(b);
	    changeWeatherButton.setEnabled(b);
	    roadsWeatherHistoryButton.setEnabled(b);
	} 
	
	private int getTicks() {
		return (int)ticksSpinner.getValue();
	}

	private void update(RoadMap map, int time) {
		this.roadMap = map;
		this.time = time;
	}
	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		update(map, time);
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		update(map, time);
	}


	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		update(map, time);
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		update(map, time);
	}

	@Override
	public void onError(String err) {}

}
