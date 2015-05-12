package client;

import java.net.InetAddress;
import graphics.*;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;


public class Client implements Runnable, Observer {
    private static Connection connection;
    private final String userName;
    private LinkedList<Message> message = new LinkedList<Message>();
    private GraphicController game;
    
 
    public Client(Connection connection, String userName, GraphicController game) throws UnknownHostException {
    	this.game = game;
        this.connection = connection;
        this.userName = userName;
        connection.openConnection(null);
        connection.addObserver(this);

    }


    /**
     * method observable to sets user online
     */
    public void update(Observable o, Object arg) {
        Message recieveMessage = connection.recieveMessage();
        
        if(recieveMessage.getType()==4){
        		game.newGame(recieveMessage.getUserNameFrom());
        	}
        else if(recieveMessage.getType()==0){
        	game.movePiece(recieveMessage.getRuta1());
        	game.movePiece(recieveMessage.getRuta2());

        }
        
        

		
    }
/**
 * this method will add message to buffer
 * @param message message which comes in
 */ 
    public void addToBuffer(Message message){
    	this.message.addFirst(message);
    }
    
    /**
     * thsi method will close socket when user disconnect
     */
    public void socketClose(){
    	connection.socketClose();
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
