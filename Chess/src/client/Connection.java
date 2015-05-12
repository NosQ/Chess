package client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Observable;

/**
 * @ author: Zvonimir och Felix
 * This class is a common class shared by both client and server class
 */
public class Connection extends Observable {
    private final InetAddress inet;
    private final int port;
    private String userName;
    private String connectedToUserName = "";
    private LinkedList<Message> outgoingMessages;
    private LinkedList<Message> ingoingMessages;
    private Socket socket;
    private final Sender sender;
    private final Receiver receiver;

    
    /**
     * 
     * @param userName username of client who logged in
     * @param inet inet address of client
     * @param port port of application
     */
    public Connection(String userName, InetAddress inet, int port) {
        this.userName = userName;
        this.inet = inet;
        this.port = port;
        outgoingMessages = new LinkedList<Message>();
        ingoingMessages = new LinkedList<Message>();
        sender = new Sender();
        receiver = new Receiver();
    }

    /**
     * This method will open connection for client
     * @param socket socket which connect
     */
    public void openConnection(Socket socket) {
        try {
            if (socket != null) {
                this.socket = socket;
            } else {
                this.socket = new Socket(inet, port);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to open socket on IP: " + inet.toString() + " and port: " + port);
        }
        sender.start();
        receiver.start();
    }

    /**
     * this method will send message 
     * @param message message which comes in from user
     */
    public void sendMessage(Message message) {
        outgoingMessages.add(message);
    }
    
    /**
     * this method will return user name
     * @return user name
     */
    public String getUserName(){
    	return userName;
    }

    public Message recieveMessage() throws NoSuchElementException {
        return ingoingMessages.pop();
    }
    
    /**
     * This method will close the socket 
     */
    public void socketClose(){
    	try {
    		sender.interrupt();
    		receiver.interrupt();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    public String getConnectedToUserName() {
        return connectedToUserName;
    }

    private class Sender extends Thread {
    	private ObjectOutputStream oos;
        public void run() {
            try {
                oos = new ObjectOutputStream(socket.getOutputStream());
                //handshake, du talar om för den som är på andra sidan vem du är.
                oos.writeObject(new Message(userName, userName, userName, 3));
                oos.flush();
                System.out.println("Sender Started");
                while (true) {
                    sleep(200);
                    if (!(outgoingMessages.size() == 0)) {
                        Message poppedMessage = outgoingMessages.pop();
                        if(poppedMessage.getType()==2){
                        	oos.writeObject(poppedMessage);
                            oos.flush();
                            oos.close();
                            socket.close();
                            receiver.interrupt();
                        }
                        else{
                        oos.writeObject(poppedMessage);
                        oos.flush();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    oos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } catch (InterruptedException e) {
                System.out.println("skit du i det");
            }
        }
    }

    private class Receiver extends Thread {
    	private ObjectInputStream ois;
  
        public void run() {
            try {
                ois = new ObjectInputStream(socket.getInputStream());
                //handshake, när du mottar en uppkoppling, vem är på andra sidan?
                connectedToUserName = ((Message) ois.readObject()).getMessage();
                while (true) {
                	try{
                    Message receivedMessage = (Message) ois.readObject();
                    ingoingMessages.add(receivedMessage);
                    if(receivedMessage.getType()==2){
                        ois.close();
                        socket.close();
                        setChanged();
                        notifyObservers();
                    }else{
                    	setChanged();
                    	notifyObservers();
                    }
                	}catch (IOException e){
                		ois.close();}
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
