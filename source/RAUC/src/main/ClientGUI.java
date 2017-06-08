package main;

import javax.swing.*;

import pdu.Message;
import pdu.MessageImpl.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


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
	private JTextField messageINP;
	private JTextField serverTF, portTF;
	private JButton login, logout;
	private JTextArea ta;
	private JPanel loginPanel;
	private JPanel southPanel;
	private JButton btnMessageBuilder;
	private JButton btnDefaultMessage;
	private JLabel lblSendNewMessage;

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
			southPanel.setPreferredSize(new Dimension(300, 300));
			getContentPane().add(southPanel, BorderLayout.SOUTH);

			lblSendNewMessage = new JLabel("Send new message:");
			southPanel.add(lblSendNewMessage);
			
			btnDefaultMessage = new JButton("Message Builder");
			btnDefaultMessage.addActionListener(this);
			btnDefaultMessage.setEnabled(false);
			southPanel.add(btnDefaultMessage, BorderLayout.EAST);
			
			messageINP = new JTextField();
			messageINP.setPreferredSize(new Dimension(400, 20));
			southPanel.add(messageINP,BorderLayout.WEST);

			
			//btnMessageBuilder = new JButton("Message Builder");
			//btnMessageBuilder.addActionListener(this);
			//btnMessageBuilder.setEnabled(false);
			//southPanel.add(btnMessageBuilder, BorderLayout.WEST);

			setDefaultCloseOperation(EXIT_ON_CLOSE);
			setSize(800, 800);
			setVisible(true);
		// --------------------------------------------------------------------------
		// END GUI BUILD
		
	}
	
	// called by the client if the connection failed
	// reset our buttons, labels, etc
	void reset() {
		portTF.setEnabled(true);
		userTF.setEnabled(true);
		passTF.setEnabled(true);
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
		serverTF.setEditable(true);
		portTF.setEditable(true);
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
			btnDefaultMessage.setEnabled(true);
			btnMessageBuilder.setEnabled(true);
		}
		
		if (o == btnDefaultMessage) {
			Object[] possibilities = {"User Authentication", "Utility Control Request", "Utility State Query", "Termination"};
			String s = (String)JOptionPane.showInputDialog(
			                    this,
			                    "Select a default message to send:",
			                    "Send New Message",
			                    JOptionPane.PLAIN_MESSAGE,
			                    null,
			                    possibilities,
			                    "Message Choice");
			sendDefault(s);
		}
		
		

	}
	
	public void sendDefault(String msg) {
		Message m = null;
		String mStr = null;
		String[] splited = null;
		switch (msg) {
			case "User Authentication": 	m = new UserAuthenMessage(userTF.getText(), passTF.getText());
											break;
			case "Utility Control Request": 
											mStr = messageINP.getText();
											splited = mStr.split("\\s+");
											m = new UtilityControlReqMessage(splited[0], splited[1] , splited[2], splited[3]);
											break;
			case "Utility State Query": 	
											mStr = messageINP.getText();
											splited = mStr.split("\\s+");
											m = new UtilityStateQueryMessage(splited);
											break;
			case "Termination":	 			client.disconnect(true); // use client's built in disconnect rather than make a new message
											return;
			
			default: 						break;
		}
		try {
			client.sendMessage(m);
		} catch (IOException e) {
			display("Error sending message:");
		}
		messageINP.setText("");
	}

	// to start the whole thing the server
	public static void main(String[] args) {
		new ClientGUI("localhost", 1500);
	}
	
	// print something to output window
	public void display(String s) {
		ta.append(s);
	}
}
