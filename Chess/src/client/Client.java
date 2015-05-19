package client;

import java.net.InetAddress;

import graphics.*;

import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;


public class Client implements Runnable, Observer {
    private static Connection connection;
    private final String userName;
    private LinkedList<Message> message = new LinkedList<Message>();
    private GraphicController game;
    private LoginFrame login;
    
 
    public Client(Connection connection, String userName, LoginFrame frame) throws UnknownHostException {
        this.connection = connection;
        this.userName = userName;
        connection.openConnection(null);
        connection.addObserver(this);
        login = frame;

    }


    /**
     * method observable to sets user online
     */
    public void update(Observable o, Object arg) {
        Message recieveMessage = connection.recieveMessage();
        
        if(recieveMessage.getType()==4){
        		System.out.println("recieved challenge");
        		login.acceptChallenge(recieveMessage);
        	}
        else if(recieveMessage.getType()==1){
        	game.movePiece(recieveMessage.getRuta1(), recieveMessage.getUserNameFrom());
        	game.movePiece(recieveMessage.getRuta2(), recieveMessage.getUserNameFrom());

        }
        else if(recieveMessage.getType()==3){
        	login.setUsersOnline(recieveMessage.getUsers());
        }
        else if(recieveMessage.getType()==5){
        	login.startGame(recieveMessage.getUserNameFrom());
        }
        else if(recieveMessage.getType()==6){
        	JOptionPane.showMessageDialog(null, "Challenge denied from: "+ recieveMessage.getUserNameFrom());
        }
     
        

		
    }
/**
 * this method will add message to buffer
 * @param message message which comes in
 */ 
    public void addToBuffer(Message message){
    	this.message.addFirst(message);
    	System.out.println("skickat drag");
    }
    
    /**
     * thsi method will close socket when user disconnect
     */
    public void socketClose(){
    	connection.socketClose();
    }
    public void setController(GraphicController controller){
    	game = controller;
    }
    
    /**
     * ADD OBSERVER LATER instead of WHILE TRUE CHECK
     * ANd update to send message
     */
    
    @Override
    public void run() {
    	while (true){
            if(!this.message.isEmpty()){            	
            	connection.sendMessage(this.message.pop());
            }
        	try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
    }
}
