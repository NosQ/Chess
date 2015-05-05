package board;

import java.util.ArrayList;

import piece.PieceType;
import square.Square;

public class AttackBoard {
	
	private Board board;
	private ChessColor color;
	private ArrayList<Square> attackSquares = new ArrayList<>();
	
	public AttackBoard(Board board, ChessColor color){
		this.board = board;
		this.color = color;
	}
	
	public void updateAttackSquares(){
		
		attackSquares.clear();
		
		for(Square square : board.getBoardSquares()){
			
			if(square.getPiece().getColor() == color){
				attackSquares.addAll(square.getPiece().getMoves().getPossibleSquares());
			}
		}		
	}	
	
	public ArrayList<Square> getAttackSquares() {
		return attackSquares;
	}
	
	public void printAttackBoard(){
		System.out.printf("\n" + color + "AttackSquares: \n");
		for(Square square : attackSquares ){
			System.out.printf(square.getValueNbr() + ", ");
		}
	}
	
}
