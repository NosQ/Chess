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

import board.ChessColor;
import client.Client;
import client.Connection;
import client.Message;
import piece.Piece;
import piece.PieceType;

/**
 * Visuellt bräde och menybar
 * @author Daniel
 *
 */
public class GraphicBoard{
	private Client client;
	private GraphicSquare[][] squares = new GraphicSquare[8][8];
	private JPanel mainPanel = new JPanel(new BorderLayout());
	private JPanel menuBar = new JPanel(new BorderLayout());
	private JPanel boardPanel;
	private JPanel infoPnl = new JPanel();
	private JPanel txtPanels = new JPanel(new GridLayout(0, 1));
	private StyledDocument	document = new DefaultStyledDocument();
	private JTextPane txtPane = new JTextPane(document);
	private JScrollPane scroll = new JScrollPane(txtPane);
	private GraphicController controller;
	private JFrame frame;
	private int move = 0;
	private int moveIndex = 0;
	private Piece tempPiece;
	private boolean whitePlayer = false;
	private boolean blackPlayer = false;

	//------Varibler som håller "gammal" informaion om rutan-----


	public GraphicBoard(GraphicController controller, Client client) {
		this.controller = controller;
		this.client = client;
		frame = new JFrame("Nätverksbaserat schack " + client.getName());
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

					s.setBackground(new Color(255, 206, 158));
					s.setPiece(controller.getBoard().getSquare(value).getPiece());

				} else {

					s.setBackground(new Color(209, 139, 71));
					s.setPiece(controller.getBoard().getSquare(value).getPiece());
				}

				if (s.getValue() >= 0) {

					s.addMouseListener(new MouseAdapter() { 

						public void mousePressed(MouseEvent e) {

							if (s.getPiece().getPieceType() != PieceType.EMPTY) {
									if(s.getPiece().getColor()==ChessColor.WHITE && whitePlayer == true){
										if(move==0){
											controller.movePiece(s.getValue(), controller.whitePlayer());
											moveIndex = s.getValue();
											move++;
											setTempPiece(s.getPiece());
									}
									}

									else if(move==1 && whitePlayer==true){
										controller.movePiece(s.getValue(), controller.whitePlayer());
										client.addToBuffer(new Message(controller.whitePlayer(), controller.blackPlayer(), moveIndex, s.getValue(),1));
										move = 0;
									}
								
									else if(s.getPiece().getColor()==ChessColor.BLACK && blackPlayer == true){
										if(move==0){
											controller.movePiece(s.getValue(), controller.blackPlayer());
											moveIndex = s.getValue();
											move++;
											setTempPiece(s.getPiece());
									}}
									else if(move==1 && blackPlayer==true){
										controller.movePiece(s.getValue(), controller.blackPlayer());
										client.addToBuffer(new Message(controller.blackPlayer(), controller.whitePlayer(), moveIndex, s.getValue(),1));
										move = 0;
									}

								
									else{
										setInfoText("inte dina pj�ser");
									}
							}

								else {
									
									if(whitePlayer==true){

										if(move==0){
											controller.movePiece(s.getValue(), controller.whitePlayer());
											moveIndex = s.getValue();
											move++;
										}
										else if(move==1){
											controller.movePiece(s.getValue(), controller.whitePlayer());
											client.addToBuffer(new Message(controller.whitePlayer(), controller.blackPlayer(), moveIndex, s.getValue(),1));
											move = 0;
										}

									}
									else if(blackPlayer==true){

										if(move==0){
											controller.movePiece(s.getValue(), controller.blackPlayer());
											moveIndex = s.getValue();
											move++;
										}
										else if(move==1){
											controller.movePiece(s.getValue(), controller.blackPlayer());
											client.addToBuffer(new Message(controller.blackPlayer(), controller.whitePlayer(), moveIndex, s.getValue(),1));
											move = 0;
										}
									}
								
								else{
									setInfoText("\n"+ "Inte dina pj�ser");
								}
							}
					}
					}


							);
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


			JButton reset = new JButton("Reset move");


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
			//		txtPanels.add(loginPanel);

			txtPanels.add(scroll);


			menuBar.add(reset, BorderLayout.SOUTH);
			menuBar.add(txtPanels, BorderLayout.CENTER);



			mainPanel.add(boardPanel, BorderLayout.CENTER);
			mainPanel.add(menuBar, BorderLayout.EAST);

			setInfoText(controller.blackPlayer() +" �r svart");
			setInfoText("\n");
			setInfoText(controller.whitePlayer() +" �r vit");


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
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
		
		public void setTempPiece(Piece piece){
			this.tempPiece = piece;
		}
		public void setWhitePlayer(){
			whitePlayer = true;
		}
		public void setBlackPlayer(){
			blackPlayer = true;
		}

	}

	//från Daniels