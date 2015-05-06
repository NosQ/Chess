package graphics;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import board.Board;
import board.ChessColor;

public class GraphicController {
	
	private Board chessB;
	private int move = 0;
	private int moveIndex;
	private GraphicBoard viewer;
	private boolean moveable = true;
	private boolean bturn = false;
	private boolean wturn = true;
	
	public GraphicController(Board board){
		chessB = board;
	}
	
	public void movePiece(int ruta){
		if(moveable) {	
			
			if (move == 0) {
				moveIndex = ruta;
				move++;
			}

			else if (move == 1) {
				if (chessB.getSquare(moveIndex).getPiece().getColor() == ChessColor.BLACK && bturn == true) {
					
					if (chessB.movePiece(moveIndex, ruta) == false && bturn == true) {
						move = 0;
						viewer.changePiece(moveIndex, ruta);
						moveIndex = 0;
					} else {
						move = 0;
						viewer.changePiece(moveIndex, ruta);
						moveIndex = 0;
						setBTurn(false);
						setWTurn(true);
					}
				} 
				else if (chessB.getSquare(moveIndex).getPiece().getColor() == ChessColor.WHITE && wturn == true) {
					
					if (chessB.movePiece(moveIndex, ruta) == false && wturn == true) {
						move = 0;
						viewer.changePiece(moveIndex, ruta);
						moveIndex = 0;
					} else {
						move = 0;
						viewer.changePiece(moveIndex, ruta);
						moveIndex = 0;
						
						// Kollar med board så att det inte är matt och sätter pjäser orörligbara
						if(chessB.isBMate() || chessB.isWMate()) {
							if(chessB.isBMate()) {
								moveable = false;
								JOptionPane.showMessageDialog(null, "Schackmatt\nVit vann!");
							} else {
								moveable = false;
								JOptionPane.showMessageDialog(null, "Schackmatt\nSvart vann!");
							}
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
	
	
	
	public static void main(String[] args) {
		Board board = new Board();
		GraphicController cont  = new GraphicController(board);
		GraphicBoard grafik = new GraphicBoard(cont);
		cont.setGraphicBoard(grafik);
		grafik.updateDisplay();
	}
}