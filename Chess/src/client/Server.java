package client;


import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Observable;
import java.util.Observer;

/**
 * @ author Vlastimil
 * this class is to start the server in order to initiate chat
 */
public class Server implements Runnable, Observer {
	private ServerSocket serverSocket;
    private static LinkedList<Connection> connections;

    /**
     * Constructor
     * @param ss serversocket
     * @throws IOException
     */
    public Server(ServerSocket ss) throws IOException {
        serverSocket = ss;
        newListener();
        connections = new LinkedList<Connection>();
       
        
    }
/**
 * This method will start the server
 */
    public void run() {
        try {
            Socket socket = serverSocket.accept();
            newListener(); //s� fort en ny socket �ppnas(dvs en klient connectar)->
            // starta en ny tr�d(som ocks� k�r run)
            Connection thisThreadsConnection = new Connection("Server", socket.getInetAddress(), socket.getPort());
            thisThreadsConnection.addObserver(this);
            thisThreadsConnection.openConnection(socket);
            connections.add(thisThreadsConnection);
            System.out.println("Number of connected clients: " + connections.size());
    		String z = thisThreadsConnection.getConnectedToUserName() + " has connected.";
    		
            

        } catch (IOException e) {
        	// CAN NOT CLOSE CONNECTION DUE TO UNINITIALIZED ERROR
        	// NEED HELP WITH THIS
            System.out.println("Client died: " + e.getMessage());
            
            e.printStackTrace();
        }
    }


    /**
     * Creates a new thread waiting for the next user to connect 
     */
    private void newListener() {
        (new Thread(this)).start();
    } // calls run()

    public static void main(String args[]) {
        int port = 6000;
        System.out.println("\nServer Started\n" + "on port: " + port);
        try {
            ServerSocket ss = new ServerSocket((port));
            new Server(ss);
        } catch (IOException e) {
            System.out.println("Unable to start Server: " + e.getMessage());
            e.printStackTrace();
        }
    }

   /**
    *This method has observable which notify message
    */
    public void update(Observable o, Object arg) {
        System.out.println("Server updating send messages");
        for(Connection connection : connections) {
            Message message;
            try {
                message = connection.recieveMessage();
                
                //om klienter skriver all som username skickas meddelande till alla
                if(message.getType()==2){
                	for (int i = 0; i<connections.size(); i++){
                		if(connections.get(i).getConnectedToUserName().equals(message.getUserNameFrom())){
                			System.out.println(message.getUserNameFrom()+" has disconnected");
                			             			
                			connections.remove(i);
     
                			System.out.println(connections.size()+ " users connected");
                		}
                	}
                
                } else {
                    //om inte, m�ste vi kolla alla connections och se vilken connection
                    for (Connection connectionToSendTo : connections) {
                        if (connectionToSendTo.getConnectedToUserName().equals(message.getUserNameTo())) {
                            connectionToSendTo.sendMessage(message);
                           
                        	}
                        else{
                        	System.out.println("User not online");
                        }
                        	
                        }
                    }
                } catch (NoSuchElementException e) {
                //do nothing if there was no messages on this connection
                	}
        }
    }

	public String dateStamp(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
		String formattedDate = sdf.format(date);
		return formattedDate;
	}
    

    
}
