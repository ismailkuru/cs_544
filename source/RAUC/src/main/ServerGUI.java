package main;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ServerGUI {

	private JFrame frmAcsServer;
	private JTextField txtPort;
	private JButton stopStart;
	private JPanel center;
	private JPanel cards;
	private JPanel south;
	private JComboBox cmbConn;
	private ArrayList<String> activeConnections;

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
		
		activeConnections = new ArrayList<String>();
		
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
		
		stopStart = new JButton("Start");
		//stopStart.addActionListener(this);
		north.add(stopStart);
		
		center = new JPanel();
		frmAcsServer.getContentPane().add(center, BorderLayout.CENTER);
		
		cards = new JPanel();
		center.add(cards);
		cards.setLayout(new CardLayout(0, 0));
		
		south = new JPanel();
		frmAcsServer.getContentPane().add(south, BorderLayout.SOUTH);
		
		
		final DefaultComboBoxModel model = new DefaultComboBoxModel();
		cmbConn = new JComboBox(model);
		south.add(cmbConn);
	}
	
	protected void display(String msg) {
		
	}

	protected void display(String card, String msg) {
		
	}
	
	protected ConnectionPanel addConnection(String id){
		ConnectionPanel cp = new ConnectionPanel(id);
		// add cp to cards panel
		
		// return cp to client so thread knows where to write
		return cp;
	}
	
	protected class ConnectionPanel extends JPanel {
		JTextArea convo;
		String connectionId;
		
		ConnectionPanel(String cid) {
			super();
			connectionId = cid;
		}
		
		JTextArea getConversationArea() {
			return convo;
		}
		
		void display(String s) {
			convo.append(s + "\n");
		}
	}
}
