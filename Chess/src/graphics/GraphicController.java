package graphics;

import javax.swing.JFrame;

import board.Board;

public class GraphicController {
	
	private Board chessB;
	private int move = 0;
	private int moveIndex;
	private GraphicBoard viewer;
	
	public GraphicController(Board board){
		chessB = board;
	}
	
	public void movePiece(int ruta){
		if(move == 0){
			moveIndex = ruta;
			move++;
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
	
	
	public static void main(String[] args) {
		Board board = new Board();
		GraphicController cont  = new GraphicController(board);
		GraphicBoard grafik = new GraphicBoard(cont);
		cont.setGraphicBoard(grafik);
		grafik.updateDisplay();
	}
}

