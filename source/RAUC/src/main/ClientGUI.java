package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ClientGUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	// the Client object bound to this GUI
	private Client client;

	// if client has established conn to server
	private boolean connected;
	
	// the default port number and host
	private int defaultPort;
	private String defaultHost;
	
	// GUI elements
	private JLabel lblAuth;
	private JTextField userTF;
	private JTextField passTF;
	private JTextField serverTF, portTF;
	private JButton login, logout;
	private JTextArea ta;
	private JPanel loginPanel;
	private JPanel southPanel;
	private JTextField msgField;
	private JButton btnMessageBuilder;
	private JLabel lblEnterMessage;

	// Build the GUI and load a default hostname and port number
	public ClientGUI(String host, int port) {

		super("RAUC Client");
		defaultPort = port;
		defaultHost = host;
		
		// BUILD GUI ELEMENTS
		// --------------------------------------------------------------------------
			setResizable(false);
			// The NorthPanel with:
			JPanel northPanel = new JPanel();
			northPanel.setLayout(new GridLayout(0, 1, 0, 0));
			// the server name and the port number
			JPanel serverAndPort = new JPanel(new GridLayout(1,5, 1, 3));
			// the two JTextField with default value for server address and port number
			serverTF = new JTextField(host);
			portTF = new JTextField("" + port);
			portTF.setHorizontalAlignment(SwingConstants.RIGHT);
			
			serverAndPort.add(new JLabel("Server Address:  "));
			serverAndPort.add(serverTF);
			serverAndPort.add(new JLabel("Port Number:  "));
			serverAndPort.add(portTF);
			northPanel.add(serverAndPort);
			
			loginPanel = new JPanel();
			northPanel.add(loginPanel);
			
			lblAuth = new JLabel("Enter username and password: ", SwingConstants.CENTER);
			loginPanel.add(lblAuth);
			userTF = new JTextField("User");
			userTF.setPreferredSize(new Dimension(100, 20));
			loginPanel.add(userTF);
			userTF.setBackground(Color.WHITE);
			passTF = new JTextField("Pass");
			passTF.setPreferredSize(new Dimension(100, 20));
			loginPanel.add(passTF);
			passTF.setBackground(Color.WHITE);
					
			// login and logout buttons
			login = new JButton("Login");
			loginPanel.add(login);
			login.addActionListener(this);
			logout = new JButton("Logout");
			loginPanel.add(logout);
			logout.addActionListener(this);
			logout.setEnabled(false);
			userTF.requestFocus();
			getContentPane().add(northPanel, BorderLayout.NORTH);

			// The CenterPanel, containing the conversation area
			ta = new JTextArea("Waiting to connect...\n", 80, 80);
			JPanel centerPanel = new JPanel(new GridLayout(1,1));
			centerPanel.add(new JScrollPane(ta));
			ta.setEditable(false);
			getContentPane().add(centerPanel, BorderLayout.CENTER);
			
			
			// the SouthPanel, which contains controls for sending a new message
			southPanel = new JPanel();
			getContentPane().add(southPanel, BorderLayout.SOUTH);
			
			lblEnterMessage = new JLabel("Enter Message: ");
			southPanel.add(lblEnterMessage);
			
			msgField = new JTextField();
			msgField.setPreferredSize(new Dimension(300, 20));
			southPanel.add(msgField, BorderLayout.CENTER);
			msgField.setColumns(10);
			
			btnMessageBuilder = new JButton("Message Builder");
			btnMessageBuilder.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			southPanel.add(btnMessageBuilder, BorderLayout.WEST);

			setDefaultCloseOperation(EXIT_ON_CLOSE);
			setSize(600, 600);
			setVisible(true);
		// --------------------------------------------------------------------------
		// END GUI BUILD
		
	}
	
	// called by the client if the connection failed
	// reset our buttons, labels, etc
	void reset() {
		login.setEnabled(true);
		logout.setEnabled(false);
		//reset login info panel
		lblAuth.setText("Enter username and password: ");
		userTF.setText("User");
		passTF.setText("Pass");
		// reset port number and host to defaults
		portTF.setText("" + defaultPort);
		serverTF.setText(defaultHost);
		// let the user change them
		serverTF.setEditable(false);
		portTF.setEditable(false);
		// don't react to a <CR> after the username
		userTF.removeActionListener(this);
		connected = false;
	}
		
	/*
	* Button clicked
	*/
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		// if it is the Logout button
		if(o == logout) {
			client.disconnect(true);
			portTF.setEnabled(true);
			userTF.setEnabled(true);
			passTF.setEnabled(true);
			return;
		}

		// if its the login button;
		if(o == login) {
			// action is a login request
			String username = userTF.getText().trim();
			// empty username, ignore it
			if(username.length() == 0)
				return;
			String pass = passTF.getText().trim();
			// empty password, ignore it
			if(pass.length() == 0)
				return;
			// empty serverAddress, ignore it
			String server = serverTF.getText().trim();
			if(server.length() == 0)
				return;
			// empty or invalid port number, ignore it
			String portNumber = portTF.getText().trim();
			if(portNumber.length() == 0)
				return;
			int port = 0;
			try {
				port = Integer.parseInt(portNumber);
			}
			catch(Exception en) {
				return;   // can't do anything if port# invalid
			}
			
			portTF.setEnabled(false);
			userTF.setEnabled(false);
			passTF.setEnabled(false);

			// try creating a new Client with GUI
			client = new Client(server, port, username, pass, this);
			// test if we can start the Client
			if(!client.start()) {
				return;
			}
			
			connected = true;
			
			// disable connection fields and login button / enable logout button
			login.setEnabled(false);
			logout.setEnabled(true);
			serverTF.setEditable(false);
			portTF.setEditable(false);
			// Action listener for client sending a message
			msgField.addActionListener(this);
		}

	}

	// to start the whole thing the server
	public static void main(String[] args) {
		new ClientGUI("localhost", 1500);
	}
	
	public void display(String s) {
		ta.append(s);
	}
}
