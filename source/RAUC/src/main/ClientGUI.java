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
			
			btnDefaultMessage = new JButton("Default Messages");
			btnDefaultMessage.addActionListener(this);
			btnDefaultMessage.setEnabled(false);
			southPanel.add(btnDefaultMessage, BorderLayout.EAST);
			
			btnMessageBuilder = new JButton("Message Builder");
			btnMessageBuilder.addActionListener(this);
			btnMessageBuilder.setEnabled(false);
			southPanel.add(btnMessageBuilder, BorderLayout.WEST);

			setDefaultCloseOperation(EXIT_ON_CLOSE);
			setSize(800, 800);
			setVisible(true);
		// --------------------------------------------------------------------------
		// END GUI BUILD
		
	}
	
	// called by the client if the connection ends
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
	* Handle various actions performed across the GUI
	*/
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		// if the logout button is clicked
		if(o == logout) {
			client.disconnect(true);
			return;
		}

		// if the login button is clicked
		if(o == login) {
			// get connection info 
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
			// disable gui elements that should not be edited during session
			portTF.setEnabled(false);
			userTF.setEnabled(false);
			passTF.setEnabled(false);

			// try creating a new Client with GUI
			client = new Client(server, port, username, pass, this);
			// test if we can start the Client
			if(!client.start()) {
				return;
			}
			
			// boolean connected used for some unimplemented features, retain for posterity
			connected = true;
			
			// disable connection fields and login button / enable logout button
			login.setEnabled(false);
			logout.setEnabled(true);
			serverTF.setEditable(false);
			portTF.setEditable(false);
			btnDefaultMessage.setEnabled(true);
			btnMessageBuilder.setEnabled(true);
		}
		
		// if user wants to send a default message
		if (o == btnDefaultMessage) {
			// pick a default message from a dropdown box
			Object[] possibilities = {"User Authentication", "Utility Control Request", "Utility State Query", "Termination"};
			String s = (String)JOptionPane.showInputDialog(
			                    this,
			                    "Select a default message to send:",
			                    "Send New Message",
			                    JOptionPane.PLAIN_MESSAGE,
			                    null,
			                    possibilities,
			                    "Message Choice");
			// send that default message
			sendDefault(s);
		}
		
		// if user wants to build a new message
		if (o == btnMessageBuilder) {
			Object[] possibilities = {"Utility Control Request", "Utility State Query"}; // only 2 types of messages allow user input
			// choose the type of message to build
			String s = (String)JOptionPane.showInputDialog(
			                    this,
			                    "Select a message type to send:",
			                    "Message Builder",
			                    JOptionPane.PLAIN_MESSAGE,
			                    null,
			                    possibilities,
			                    "Message Choice");
			// build that message
			sendMessageBuilder(s);
		}
		
		

	}
	
	// dialog box to send default messages
	public void sendDefault(String msg) {
		Message m = null;
		switch (msg) {
			case "User Authentication": 	m = new UserAuthenMessage(userTF.getText(), passTF.getText()); // should never send but there for demonstration
											break;
			case "Utility Control Request": m = new UtilityControlReqMessage("au1", "0" , "power", "off"); // turns default component off
											break;
			case "Utility State Query": 	m = new UtilityStateQueryMessage("au1"); // list components on auto 1
											break;
			case "Termination":	 			client.disconnect(true); // use client's built in disconnect (sends Termination message)
											return;
			
			default: 						break;
		}
		try {
			client.sendMessage(m);
		} catch (IOException e) {
			display("Error sending message:");
		}
	}
	
	public void sendMessageBuilder(String msg) {
		Message m = null;
		MessageBuilder mb = new MessageBuilder(this);
		
		// launch specific builder based on user choice
		switch (msg) {
			case "Utility Control Request": m = mb.buildUCR();
											break;
			case "Utility State Query": 	m = mb.buildQRY();
											break;
			
			default: 						break;
		}
		try {
			// send the message we built
			client.sendMessage(m);
		} catch (IOException e) {
			display("Error sending message:");
		}
	}

	// to start the GUI with default host address and port
	public static void main(String[] args) {
		new ClientGUI("localhost", 1500);
	}
	
	// print something to output window
	public void display(String s) {
		ta.append(s);
	}
	
	
	
	
	// a series of dialog boxes that help user build a dynamic message to send across the RAUC connection
	protected class MessageBuilder {
		
		// where to send the dialogs
		JFrame parent;
		
		MessageBuilder(JFrame par) {
			parent = par;
		}
		
		// builds a UCR message from user input dialogs
		protected  UtilityControlReqMessage buildUCR() {
			String auto= null;
			String comp= null;
			String attr= null;
			String val = null;
			
			// get auto id to send to
			auto = (String) JOptionPane.showInputDialog(
					parent,
					"Enter the automobile name to send this message to:",
					"UCR Builder: Enter Auto",
					JOptionPane.PLAIN_MESSAGE,
					null,
					null,
					null
					);
			
			// get component to send to
			comp = (String) JOptionPane.showInputDialog(
					parent,
					"Enter the component on " + auto + " to control",
					"UCR Builder: Enter Component",
					JOptionPane.PLAIN_MESSAGE,
					null,
					null,
					null
					);
			
			// get attribute to adjust
			attr = (String) JOptionPane.showInputDialog(
					parent,
					"Enter the attribute of " + auto + "/" + comp + " to adjust",
					"UCR Builder: Enter Attribute",
					JOptionPane.PLAIN_MESSAGE,
					null,
					null,
					null
					); 
			
			// get value to set attribute to
			val = (String) JOptionPane.showInputDialog(
					parent,
					"Enter the new value of " + auto + "/" + comp + "/" + attr,
					"UCR Builder: Set Value",
					JOptionPane.PLAIN_MESSAGE,
					null,
					null,
					null
					);
			// create the message and return it
			return new UtilityControlReqMessage(auto, comp, attr, val);
		}
		
		protected UtilityStateQueryMessage buildQRY() {
			String auto= null;
			String comp= null;
			String attr= null;
			String val = null;

			// see below - messy way to fill "What is your query?" input box
			UCRType type = new UCRType();
			UCRType[] ask = type.ucrs();
		
			// here user decides how many arguments to send with this message
			 type = (UCRType) JOptionPane.showInputDialog(
					parent,
					"What is your query?",
					"QRY Builder",
					JOptionPane.PLAIN_MESSAGE,
					null,
					ask,
					null
					);
			 
			 // use an int to make case statement cleaner
			 int buildSeq = type.getVal();
			 
			 // build the message in reverse order based on previous decision
			 switch (buildSeq) {
			 	case 1: 
			 		val = "1"; // val is only ever a dummy content chunk if included in QRY
			 	case 2:
			 		attr = getAttr();
			 	case 3:
			 		comp = getComp();
			 	case 4:
			 		auto = getAuto();
			 	case 5:
			 		break;
			 }
			 // create the message and return it
			 // NULL arguments will not be added as content chunks
			 return new UtilityStateQueryMessage(auto, comp, attr, val);
		}
		
		/*
		 * helper methods for buildQRY() to show relevant input dialog
		 */
		
		private String getAuto() {
			String res = (String) JOptionPane.showInputDialog(
					parent,
					"Which automobile do you want to know about?",
					"QRY Builder: Enter Auto",
					JOptionPane.PLAIN_MESSAGE,
					null,
					null,
					null
					); 
			return res;
		}
		private String getComp() {
			String res = (String) JOptionPane.showInputDialog(
					parent,
					"Which component do you want to know about?",
					"QRY Builder: Enter Component",
					JOptionPane.PLAIN_MESSAGE,
					null,
					null,
					null
					); 
			return res;
		}
		private String getAttr() {
			String res = (String) JOptionPane.showInputDialog(
					parent,
					"Which attribute do you want to know about?",
					"QRY Builder: Enter Attribute",
					JOptionPane.PLAIN_MESSAGE,
					null,
					null,
					null
					); 
			return res;
		}
		

		
		// VERY messy subclass just to clean up above, really should be an enum or similar but unable to do that in private MessageBuilder subclass
		// TODO: low priority, refactor
		private class UCRType {
			
			String askLine;
			int val;
			
			UCRType(String ask, int value) {
				askLine = ask;
				val = value;
			}
			
			UCRType() {
			}
			
			UCRType[] ucrs() {
				UCRType[] res = new UCRType[5];
				
				res[0] = new UCRType("List autos on this account", 5);
				res[1] = new UCRType("List components on an auto", 4);
				res[2] = new UCRType("List attributes of a component", 3);
				res[3] = new UCRType("List possible values of an attribute", 2);
				res[4] = new UCRType("Show the current value of an attribute", 1);
				
				return res;
				
			}
			
			int getVal() {
				return val;
			}
			
			public String toString() {
				return askLine;
			}
		}
		
	}
}
