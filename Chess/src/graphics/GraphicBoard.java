package graphics;

import java.awt.*;
import java.awt.event.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyledDocument;

import client.Client;
import client.Connection;
import client.Message;
import piece.PieceType;

/**
 * Visuellt bräde och menybar
 * @author Daniel
 *
 */
public class GraphicBoard implements ActionListener {
	private Client client;
	private GraphicSquare[][] squares = new GraphicSquare[8][8];
	private JPanel mainPanel = new JPanel(new BorderLayout());
	private JPanel menuBar = new JPanel(new BorderLayout());
	private JPanel boardPanel;
	private JPanel infoPnl = new JPanel();
	private JPanel loginPanel = new JPanel();
	private JPanel txtPanels = new JPanel(new GridLayout(0, 1));
	private StyledDocument	document = new DefaultStyledDocument();
	private JTextPane txtPane = new JTextPane(document);
	private JScrollPane scroll = new JScrollPane(txtPane);
	private GraphicController controller;
	private JFrame frame = new JFrame("Testar");
	private JTextField loginField = new JTextField();
	private JTextField connectField = new JTextField();
	private JButton connectButton = new JButton("Connect");
	private int move = 0;
	private int moveIndex = 0;
	
	//------Varibler som håller "gammal" informaion om rutan-----
	private GraphicSquare temp;
	private Color oldSquareColor;
	
	public GraphicBoard(GraphicController controller) {
		this.controller = controller;
		connectButton.addActionListener(this); 
		initializeGui();
	}
	
	private void initializeGui() {
		
		mainPanel.setPreferredSize(new Dimension(900,660));		
		boardPanel = new JPanel(new GridLayout(0,8));
		boardPanel.setBorder(new LineBorder(Color.BLACK));	
		menuBar.setBorder(new LineBorder(Color.BLACK, 1));	
		boardPanel.setPreferredSize(new Dimension(650,650));
		
		int value = 0;
		for(int i = 0; i < squares.length; i++) {
			for(int j = 0; j < squares[i].length; j++) {
				
				final GraphicSquare s = new GraphicSquare(value);
				
				if ((j % 2 == 1 && i % 2 == 1) || (j % 2 == 0 && i % 2 == 0)) {
					
					s.setBackground(Color.WHITE);
					s.setPiece(controller.getBoard().getSquare(value).getPiece());
					
				} else {
					
					s.setBackground(Color.BLUE);
					s.setPiece(controller.getBoard().getSquare(value).getPiece());
				}
				
				if (s.getValue() >= 0) {

					s.addMouseListener(new MouseAdapter() { 
						
						public void mousePressed(MouseEvent e) {
						
							if (s.getPiece().getPieceType() != PieceType.EMPTY) {
								controller.movePiece(s.getValue());
								temp = s;
								// hämtar den gamla färgen på rutan
								oldSquareColor = s.getBackground(); 
								temp.setBackground(Color.GREEN);
								if(move==0){
									moveIndex = s.getValue();
									move++;
								}
									else if(move==1){
										client.addToBuffer(new Message(getUser(), getOpponent(), moveIndex, s.getValue(),1));
										move = 0;
									}
								
							} else {
								controller.movePiece(s.getValue());
								temp.setBackground(oldSquareColor);
								if(move==0){
									moveIndex = s.getValue();
									move++;
								}
									else if(move==1){
										client.addToBuffer(new Message(getUser(), getOpponent(), moveIndex, s.getValue(),1));
										move = 0;
									}
							}
					
						}
					});
				}
			
				squares[i][j] = s;
				value++;
			}
		}
		
		//--------Skapar rutor på brädet------
		
		for (int i = 0; i < 8; i++) {
			
            for (int j = 0; j < 8; j++) {
           
				boardPanel.add(squares[i][j]);
  
            }
        }
		
		//--------Skapar en menubar-----------
		
		JButton newGame = new JButton("New game");
		JButton reset = new JButton("Reset move");
		
		newGame.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean yes = JOptionPane.showConfirmDialog(null, "Vill du starta nytt spel?", "Nytt spel", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
				if (yes) {
					setInfoText("Denna funktion finns ej än\n");
				}
			}
		});
		
		reset.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean yes = JOptionPane.showConfirmDialog(null, "Vill du ångra drag?", "Ångra drag", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
				if (yes) {
					setInfoText("Denna funktion finns ej än\n");
				}

			}
		});
				
		infoPnl.setPreferredSize(new Dimension(200,400));
		scroll.setPreferredSize(new Dimension(190,300));
		txtPanels.setPreferredSize(new Dimension(200,200));
		txtPane.setEditable(false);
		txtPane.setFont(new Font("SansSerif", Font.PLAIN, 15));
		infoPnl.add(scroll);
		
		//------logIn panel--------
		
			
	
		
	
		
		connectButton.setPreferredSize(new Dimension(190,300));
	
		connectField.setPreferredSize(new Dimension(190,30));
		loginField.setPreferredSize(new Dimension(190, 30));
		loginPanel.add(loginField);
		loginPanel.add(connectField);
		txtPanels.add(loginPanel);
		txtPanels.add(connectButton);
		
		txtPanels.add(scroll);
		
		
		menuBar.add(reset, BorderLayout.SOUTH);
		menuBar.add(newGame, BorderLayout.NORTH);
		menuBar.add(txtPanels, BorderLayout.CENTER);
		
		
		
		mainPanel.add(boardPanel, BorderLayout.CENTER);
		mainPanel.add(menuBar, BorderLayout.EAST);
		
		
	}
	
	public void changePiece(int index, int to){
		for (int i = 0; i < squares.length; i++) {
			for (int z = 0; z < squares.length; z++) {
				int y = squares[i][z].getValue();
				if (y == index) {
					for (int h = 0; h < squares.length; h++) {
						for (int j = 0; j < squares.length; j++) {
							int x = squares[h][j].getValue();
							if (x == to) {
								squares[h][j].setPiece(controller.getBoard().getSquare(x).getPiece());
								squares[i][z].setPiece(controller.getBoard().getSquare(y).getPiece());
							}
						}
					}
				}
			}
		}		
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==connectButton){
	    	int port = 6000;
	    	try {
				Client client = new Client(new Connection(getUser(), InetAddress.getByName("localhost"), port), getUser(), controller);
				this.client = client;
				new Thread(client).start();
			} catch (UnknownHostException c) {
				c.printStackTrace();
			}
		}
		
	}
	public void setInfoText(String info) {
		
		try {
			
			document.insertString(document.getLength(), info, null);
			
		} catch (BadLocationException e) {}
		
	}
	
	
	public JFrame getGui() {
		return frame;
	}
	
	public void updateDisplay(){
		frame.add(mainPanel);
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public String getUser(){
		return loginField.getText();
	}
	public String getOpponent(){
		return connectField.getText();
	}
	
}

//från Daniels