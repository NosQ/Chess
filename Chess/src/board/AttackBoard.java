package board;

import java.util.ArrayList;

import piece.PieceType;
import square.Square;

public class AttackBoard {
	
	private Board board;
	private ChessColor color;
	private ChessColor kingColor;
	private ArrayList<Square> attackSquares = new ArrayList<>();
	private ArrayList<Square> kingSquares = new ArrayList<>();
	private Square kingPosition;
//	private boolean inCheck = false;
	
	public AttackBoard(Board board, ChessColor color){
		this.board = board;
		this.color = color;
		updateKingPosition();
		kingColor(color);
	}
	
	public void kingColor(ChessColor color){
		if(color.getColor() == 1){
			kingColor = ChessColor.BLACK;
		}
		if(color.getColor() == 0){
			kingColor = ChessColor.WHITE;
		}
		
	}
	
	public void updateKingPosition(){
		
		kingPosition = board.getKingPosition(kingColor);
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
	
	public ChessColor getKingColor(){
		return kingColor;
	}
	public void kingPossibleMoves(){
		kingSquares.clear();
		for(Square square : board.getBoardSquares()){
			if(square.getPiece().getColor() != color && square.getPiece().getPieceType() == PieceType.KING){
				kingSquares.addAll(square.getPiece().getMoves().getPossibleSquares());
			}
		}
	}
	
	public boolean inCheck() {
		for (Square square : attackSquares) {
			if(square.equals(kingPosition)) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean inMate(){
		
		kingPossibleMoves();
		updateAttackSquares();
		printKingMoves();
		for (Square square : attackSquares) {
			for (Square ksquare : kingSquares) {
			
				if (square.equals(ksquare)) {
		
					return true;
				}
			}
		}
		
		return false;
	}
	
	public void printKingMoves() {
		for(Square s : kingSquares) {
			System.out.printf(s.getValueNbr() + ", ");
		}
	}
	
	
//	public boolean checkMate() {
//		
//		
//		
//	}
	
	public void printAttackBoard(){
		System.out.printf("\n" + color + "AttackSquares: \n");
		for(Square square : attackSquares ){
			System.out.printf(square.getValueNbr() + ", ");
		}
	}
	
}
