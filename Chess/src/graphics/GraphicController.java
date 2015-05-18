package graphics;


import javax.swing.JOptionPane;

import client.Client;
import board.Board;
import board.ChessColor;
import board.Player;
/*
 * Kommentar
 */
public class GraphicController {
	
	private Board chessB = new Board();
	private int move = 0;
	private int moveIndex;
	private GraphicBoard viewer;
	private boolean moveable = true;
	

	private Player playerWhite = chessB.getPlayer(ChessColor.WHITE);
	private Player playerBlack = chessB.getPlayer(ChessColor.BLACK);
	private boolean bturn = playerBlack.getTurn();
	private boolean wturn = playerWhite.getTurn();
	private String whitePlayer;
	private String blackPlayer;
	private boolean white = false;
	private boolean black = false;
	
	public GraphicController(String player1, String player2) {
		this.whitePlayer = player1;
		this.blackPlayer = player2;
		playerBlack.setTurn(false);
	}
	
	public void movePiece(int ruta, String player){
		if(moveable) {	
			
			if (move == 0) {
				
				moveIndex = ruta;
				move++;
				}
			

			else if (move == 1) {
				if (chessB.getSquare(moveIndex).getPiece().getColor() == ChessColor.BLACK && bturn == true && player.equals(blackPlayer)) {
					
					if (chessB.movePiece(playerBlack.getColor(), moveIndex, ruta) == false) {
						move = 0;
						viewer.changePiece(moveIndex, ruta);
						moveIndex = 0;
					} else {
						viewer.setInfoText("Giltligt drag svart\n");
						move = 0;
						viewer.changePiece(moveIndex, ruta);
						moveIndex = 0;
						viewer.setInfoText("\n" +blackPlayer+" skickat drag");
						
						if(chessB.kingInCheck(ChessColor.WHITE) && !chessB.isWMate()) {
							viewer.setInfoText("Vit kung i schack!\n");
						}
						//kollar som svart är i matt
						if(chessB.isWMate()){
							moveable = false;
							JOptionPane.showMessageDialog(null, "Schackmatt\nSvart vann!");
							viewer.setInfoText("Spelet är slut\n");
						}
						
						setBTurn(false);
						setWTurn(true);
					}
				} 
				else if (chessB.getSquare(moveIndex).getPiece().getColor() == ChessColor.WHITE && wturn == true && player.equals(whitePlayer)) {
					
					if (chessB.movePiece(playerWhite.getColor(), moveIndex, ruta) == false) {
						move = 0;
						viewer.changePiece(moveIndex, ruta);
						moveIndex = 0;
						viewer.setInfoText("\n" +whitePlayer+" skickat drag");
						
					} else {
						viewer.setInfoText("Giltligt drag vit\n");
						move = 0;
						viewer.changePiece(moveIndex, ruta);
						moveIndex = 0;
						viewer.setInfoText(whitePlayer+" skickat drag");
						
						if(chessB.kingInCheck(ChessColor.BLACK) && !chessB.isBMate()) {
							viewer.setInfoText("Svart kung i schack!\n");
						}
						//kollar om vit är i matt
						if (chessB.isBMate()) {
							moveable = false;
							JOptionPane.showMessageDialog(null, "Schackmatt\nVit vann!");
							viewer.setInfoText("Spelet är slut\n");
						}
						
						setBTurn(true);
						setWTurn(false);

					}

				} else {
					move = 0;
					System.out.println("inte din tur");
				}
			}
		}
	}
	
	public Board getBoard(){
		return chessB;
	}
	public void setGraphicBoard(GraphicBoard gb){
		viewer = gb;
	}
	public void setBTurn(boolean turn){
		bturn = turn;
	}
	public void setWTurn(boolean turn){
		wturn = turn;
	}
	public String whitePlayer(){
		return whitePlayer;
	}
	public String blackPlayer(){
		return blackPlayer;
	}
	public void setWhitePlayer(){
		white = true;
		viewer.setWhitePlayer();
	}
	public void setBlackPlayer(){
		black = true;
		viewer.setBlackPlayer();
	}

	public void execute(Client client) {
		GraphicBoard grafik = new GraphicBoard(this, client);
		viewer = grafik;
		setGraphicBoard(grafik);
		grafik.updateDisplay();
	}
}