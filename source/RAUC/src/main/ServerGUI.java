package main;


import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

import com.sun.prism.paint.Stop;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ServerGUI {

	private JFrame frmAcsServer;
	private JTextField txtPort;
	private JButton btnStart;
	private JButton btnStop;
	private JPanel center;
	private JPanel cards;
	private JPanel south;
	private JComboBox<String> cmbConn;
	private DefaultComboBoxModel<String> connModel;
	private JPanel logCard;
	private LogPanel serverLog;
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerGUI window = new ServerGUI();
					window.frmAcsServer.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ServerGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		connModel = new DefaultComboBoxModel<String>();
		cmbConn = new JComboBox<String>(connModel);
		
		frmAcsServer = new JFrame();
		frmAcsServer.setTitle("ACS Server");
		frmAcsServer.setBounds(100, 100, 450, 623);
		frmAcsServer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel north = new JPanel();
		frmAcsServer.getContentPane().add(north, BorderLayout.NORTH);
		
		JLabel lblPort = new JLabel("Port Number");
		north.add(lblPort);
		
		txtPort = new JTextField();
		north.add(txtPort);
		txtPort.setColumns(10);
		
		btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				startServer();
			}
		});
		//start.addActionListener(this);
		north.add(btnStart);
		
		btnStop = new JButton("Stop");
		btnStop.setEnabled(false);
		btnStop.setVisible(false);
		north.add(btnStop);
		
		center = new JPanel();
		frmAcsServer.getContentPane().add(center, BorderLayout.CENTER);
		center.setLayout(new BorderLayout(0, 0));
		
		
		cards = new JPanel();
		cards.setBorder(new CompoundBorder(new EmptyBorder(5, 3, 5, 3), new LineBorder(new Color(0, 0, 0))));
		center.add(cards);
		cards.setLayout(new CardLayout(0, 0));
		
		logCard = addLog("Server Log");
		serverLog = (LogPanel) logCard;
		serverLog.display("Server not yet running.");
		
		south = new JPanel();
		frmAcsServer.getContentPane().add(south, BorderLayout.SOUTH);
;
		
		//switch cards based on combobox choice
		cmbConn.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				CardLayout cl = (CardLayout) cards.getLayout();
				cl.show(cards, (String) arg0.getItem());
			}
		});
		
		CardLayout cl = (CardLayout) cards.getLayout();
		cl.show(cards, "Server Log");
		
		south.add(cmbConn);
	}
	
	private void startServer() {
		int port = 0;
		String portString = txtPort.getText().trim();
		// exit if no port was provided
		if (portString.length() == 0) {
			serverLog.display("Error starting server. Please provide a port number.");
			return;
		}
		
		try {
			// if port is valid, create a new server hooked to this gui
			port = Integer.parseInt(portString);
			Server server = new Server(port, this);
			serverLog.display("Server listening on port " + port + "...");
			
			// disable start button / enable stop button
			txtPort.setEditable(false);
			btnStart.setVisible(false);
			btnStart.setEnabled(false);;
			btnStop.setVisible(true);
			btnStop.setEnabled(true);;
		} catch (Exception e) {
			// if port is invalid, can't do anything
			serverLog.display("Error starting server. Please provide a valid port number.");
		}
	}
	
	protected LogPanel addConnection(String conn) {
		LogPanel lp = addLog(conn);
		serverLog.display("Connection accepted from " + conn + ", starting new connection thread...");
		lp.display("Waiting for auth");
		return lp;
	}
	
	private LogPanel addLog(String id) {
		LogPanel cp = new LogPanel(id);
		connModel.addElement(id);
		cards.add(cp, id);
		return cp;
	}
	
	protected LogPanel getServerLog() {
		return serverLog;
		
	}
	
	
	void addLog(JPanel panel) {
		panel.add(new JTextArea());
	}
	
	protected class LogPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		
		JTextArea ta;
		String id;
		
		LogPanel(String cid) {
			super();
			setLayout(new BorderLayout(0, 0));
			id = cid;
			ta = new JTextArea();
			ta.setEditable(false);
			add(ta, BorderLayout.CENTER);
		}
		
		void display(String s) {
			ta.append(s + "\n");
		}
		
		String identify() {
			return id;
		}

	}
}
