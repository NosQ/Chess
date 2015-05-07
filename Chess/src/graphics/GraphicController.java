package graphics;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import board.Board;
import board.ChessColor;

public class GraphicController {
	
	private Board chessB = new Board();
	private int move = 0;
	private int moveIndex;
	private GraphicBoard viewer = new GraphicBoard(this);
	private boolean moveable = true;
	private boolean bturn = false;
	private boolean wturn = true;
	
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
						viewer.setInfoText("Giltligt drag svart\n");
						move = 0;
						viewer.changePiece(moveIndex, ruta);
						moveIndex = 0;
						
						//kollar som svart är i matt
						if(chessB.isWMate()){
							moveable = false;
							JOptionPane.showMessageDialog(null, "Schackmatt\nSvart vann!");
						}
						
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
						viewer.setInfoText("Giltligt drag vit\n");
						move = 0;
						viewer.changePiece(moveIndex, ruta);
						moveIndex = 0;
						
						//kollar om vit är i matt
						if (chessB.isBMate()) {
							moveable = false;
							JOptionPane.showMessageDialog(null, "Schackmatt\nVit vann!");
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

	public void execute() {
		GraphicBoard grafik = new GraphicBoard(this);
		setGraphicBoard(grafik);
		grafik.updateDisplay();
	}
}