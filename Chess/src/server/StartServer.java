package server;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class StartServer implements ActionListener{
	private Server server;
	private JFrame frame = new JFrame("Start server");
	private JPanel panel = new JPanel();
	private JButton button = new JButton("Start Server");
	private JButton button2 = new JButton("Close server");
	
	public StartServer(){
		button.addActionListener(this);
		button2.addActionListener(this);
		frame.add(panel);
		panel.add(button);
		panel.add(button2);
		panel.setPreferredSize(new Dimension(190,100));
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==button){
			 int port = 6000;
		        System.out.println("\nServer Started\n" + "on port: " + port);
		        try {
		            ServerSocket ss = new ServerSocket((port));
		            Server serv = new Server(ss);
		            server = serv;
		            
		            
		            
		        } catch (IOException e1) {
		            System.out.println("Unable to start Server: " + e1.getMessage());
		            e1.printStackTrace();
		        }
			
		}
		if(e.getSource()==button2){
			server.closeServer();
			
		}
		
	}
	
	public static void main(String args[]) {
		StartServer st = new StartServer();
        
        
        
    }
	

}
