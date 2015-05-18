package graphics;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import client.Client;
import client.Connection;
import client.Message;

import javax.swing.*;

public class LoginFrame implements ActionListener {
	private Client klient;
	private JFrame frame = new JFrame("Login");
	private JPanel mainPanel = new JPanel();
	private JTextField loginField = new JTextField();
	private JTextField connectField = new JTextField();
	private JButton connectButton = new JButton("Connect");
	private JButton disconnectButton = new JButton("Disconnect");
	private JLabel userName = new JLabel("Username");
	private JLabel opponent = new JLabel("Opponent");
	private JButton newGame = new JButton("New game");
	private JButton challenge = new JButton("Challenge");
	private JTextField usersOnline = new JTextField();
	private JFrame cFrame = new JFrame("challenge opponent");
	private JPanel cPanel = new JPanel();
	private JButton sendChallenge = new JButton("Send challenge");
	private Boolean Connected;
	private String player;
	
	
	
	
	
	
	public LoginFrame(){
		connectButton.addActionListener(this);
		disconnectButton.addActionListener(this);
		newGame.addActionListener(this);
		challenge.addActionListener(this);
		sendChallenge.addActionListener(this);
	}
	
	public void startUi(){
		frame.add(mainPanel);
		mainPanel.setPreferredSize(new Dimension(190,500));
		
		connectButton.setPreferredSize(new Dimension(190,40));
		disconnectButton.setPreferredSize(new Dimension(190,40));
		connectField.setPreferredSize(new Dimension(190,30));
		loginField.setPreferredSize(new Dimension(190, 30));
		usersOnline.setPreferredSize(new Dimension(190, 200));
		challenge.setPreferredSize(new Dimension(190, 40));
		sendChallenge.setPreferredSize(new Dimension(190, 40));
		
		
		mainPanel.add(userName);
		mainPanel.add(loginField);

		
		mainPanel.add(connectButton);
		mainPanel.add(disconnectButton);
		mainPanel.add(challenge);
		mainPanel.add(usersOnline);
		
		cFrame.setPreferredSize(new Dimension(200, 200));
		cFrame.add(cPanel);
		cPanel.add(opponent);
		cPanel.add(connectField);
		cPanel.add(sendChallenge);

	
		
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==connectButton){
	    	int port = 6000;
	    	setPlayer(loginField.getText());
	    	try {
				Client client = new Client(new Connection(loginField.getText(), InetAddress.getByName("localhost"), port), player, this);
				this.klient = client;
				new Thread(client).start();
				Connected = true;
	
			} catch (UnknownHostException c) {
				c.printStackTrace();
			}
		}
		if(e.getSource()==challenge){
			cFrame.setResizable(false);
			cFrame.pack();
			cFrame.setVisible(true);
			cFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
		if(e.getSource()==disconnectButton){
			klient.addToBuffer(new Message(loginField.getText(), loginField.getText(),2));
		}
		if(e.getSource()==sendChallenge){
			klient.addToBuffer(new Message(player, connectField.getText(), 4));
			JOptionPane.showMessageDialog(null, "Challenge sent");
		}
		
	}
	
	public void setUsersOnline(String usersOnlineIn){
		String usersOnlineToTextArea = "--Users--";
	try{
		if (usersOnlineIn != null){
			usersOnline.setText(null);
			String[] usersOnline = usersOnlineIn.split(";");
			for ( String user : usersOnline){
				usersOnlineToTextArea += "\n" + user;
			}
			
		}
		else{
			System.out.println("Users Online is null?");
		}

	}

		catch (ArrayIndexOutOfBoundsException e){
			System.out.println("Somehow it failed.");
	}

		usersOnline.setText(usersOnlineToTextArea);
	}
	public void acceptChallenge(Message message){
		boolean yes = JOptionPane.showConfirmDialog(null, "Vill du acceptera spel från: "+ message.getUserNameFrom(), "Nytt spel", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
		if (yes) {
			GraphicController control = new GraphicController(player, message.getUserNameFrom());
			klient.addToBuffer(new Message(player, message.getUserNameFrom(), 5));
			klient.setController(control);
			control.execute(klient);
			control.setWhitePlayer();
			
			
	}
		else{
			klient.addToBuffer(new Message(player, message.getUserNameFrom(), 6));
		}
	}
	public void startGame(String name){
		GraphicController control = new GraphicController(name, player);
		klient.setController(control);
		control.execute(klient);
		control.setBlackPlayer();
	}
	public void setPlayer(String name){
		player = name;
	}
	public static void main(String[] args){
		LoginFrame lg = new LoginFrame();
		lg.startUi();

		
	}

	


}
