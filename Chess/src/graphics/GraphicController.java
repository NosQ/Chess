package graphics;

import javax.swing.JFrame;

import board.Board;
import board.ChessColor;

public class GraphicController {
	
	private Board chessB;
	private int move = 0;
	private int moveIndex;
	private GraphicBoard viewer;
	private boolean bturn = false;
	private boolean wturn = true;
	
	public GraphicController(Board board){
		chessB = board;
	}
	
	public void movePiece(int ruta){
		if(move == 0){
			if(chessB.getSquare(ruta).getPiece().getColor()==ChessColor.BLACK && bturn == true){
			moveIndex = ruta;
			move++;
			setBTurn(false);
			setWTurn(true);
			}
			else if(chessB.getSquare(ruta).getPiece().getColor()==ChessColor.WHITE && wturn == true){
				moveIndex = ruta;
				move++;
				setWTurn(false);
				setBTurn(true);
			}
			else{
				System.out.println("inte din tur");
			}
		}
		else if(move == 1){
			chessB.movePiece(moveIndex, ruta);
			move = 0;
			viewer.changePiece(moveIndex, ruta);
			moveIndex = 0;
			
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