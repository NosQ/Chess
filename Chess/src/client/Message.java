package client;

import java.io.Serializable;
import java.util.LinkedList;

import javax.swing.ImageIcon;

/**
 * @ author: Vlastimil, Shipra, Zvonemir.
 * This class is a common class shared by both client and server
 */
public final class Message implements Serializable{

    private final String userNameFrom;
    private final String userNameTo;
    private int ruta1 = 0;
    private int ruta2 = 0;
    static final int WHOISIN = 0, MESSAGE = 1, LOGOUT = 2, LOGIN = 3, NEWGAME = 4;
    private final int type;

    /**
     * Constructor
     * @param userNameFrom user who logged in
     * @param userNameTo to whom send message
     * @param message message type in
     * @param image image attach in
     * @param type
     */
    public Message(String userNameFrom, String userNameTo, int ruta1, int ruta2, int type) {
        this.userNameFrom = userNameFrom;
        this.userNameTo = userNameTo;
        this.setRuta1(ruta1);
        this.setRuta2(ruta2);
        this.type = type;
    }
    public Message(String userNameFrom, String userNameTo, int type) {
        this.userNameFrom = userNameFrom;
        this.userNameTo = userNameTo;
        this.type = type;
    }
    
    public Message(int type){
    	this.type = type;
    	this.userNameFrom = null;
    	this.userNameTo = null;
    }
/**
 * 
 * @return user name from text comes in 
 */
    public String getUserNameFrom() {
        return userNameFrom;
    }

    /**
     * 
     * @return username to which text goes
     */
    public String getUserNameTo() {
        return userNameTo;
    }

	public int getType(){
		return type;
	}

	public int getRuta2() {
		return ruta2;
	}

	public void setRuta2(int ruta2) {
		this.ruta2 = ruta2;
	}
	public int getRuta1() {
		return ruta1;
	}
	public void setRuta1(int ruta1) {
		this.ruta1 = ruta1;
	}
}
