package main;


import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
/* =============================================================================
* CS544 - Computer Networks - Drexel University, Spring 2017
* Protocol Implementation: Remote Automobile Utility Control
* Group 2:
* - Ismail Kuru
* - Max Mattes
* - Lewis Cannalongo
***************************************************
* File name: ServerGUI.java
* **************************************************
* Definition: This file includes implementation of GUI which aims to provide server’s logs.
* *******************************************************
* Requirements:
* - UI : Entire file’s relevant requirement is satisfying UI.
* 
* ==============================================================================
*/
public class ServerGUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
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
	public Server server;
	private ServerThread initThread;
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerGUI window = new ServerGUI(1500);
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
	public ServerGUI(int port) {
		server = null;
		initialize();
		txtPort.setText(Integer.toString(port));
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
		btnStart.addActionListener(this);
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
	
	// start/stop the server
	public void actionPerformed(ActionEvent e) {
		// stop the server if already running
		if(server != null) {
			stopServer();
		} else {
		// start it otherwise
			startServer();
		}
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
			port = Integer.parseInt(portString);
		} catch (Exception e2) {
			serverLog.display("Error starting server. Please provide a valid port number.");
			return;
		}
		
		server = new Server(port, this);
		new ServerThread().start();
		btnStart.setText("Stop");
		txtPort.setEditable(false);
	}
	
	private void stopServer() {
		
			server.shutdown();
			server = null;
			txtPort.setEditable(true);
			btnStart.setText("Start");
			return;
	}
	
	public LogPanel addConnection(String conn) {
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
	
	public LogPanel getServerLog() {
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
	
	private class ServerThread extends Thread {
		public void run() {
		server.run(); // runs main server loop
		
		// reached when server stops running
		btnStop.setVisible(false);
		btnStop.setEnabled(false);
		btnStart.setVisible(true);
		btnStart.setEnabled(true);
		txtPort.setEditable(true);
		serverLog.display("Server stopped running unexpectedly.");
		server = null;
		}
	}
}
