package piece;

import java.util.ArrayList;

import board.ChessColor;
import square.Square;

public abstract class Piece {
	
	private PieceType pieceType;
	private MoveType moveType;
	private ChessColor color;
	private Square squareAt;
	private MoveList possibleMoves;	
	
	public Piece(PieceType pieceType, ChessColor color) {

		this.pieceType = pieceType;		
		this.color = color;
		
		moveType = new MoveType(pieceType);
		possibleMoves = new MoveList(this);
	}
	
	/**
	 * Metoden ska flytta en pjäs till en nya ruta	 * 
	 * @param index Den nuvarande positionen pjäsen står på
	 * @param moveTo Den ny positionen pjäsen ska flytta till. Den måste bestämmas så att den är valid.
	 * @return
	 */
	public boolean movement(int moveTo){
		
		Square square = squareAt.getMailbox().getSquare(moveTo);
		
//		System.out.print("Du vill flytta till RUTA: " + square.getValueNbr() + " från RUTA: " +
//				squareAt.getBoard().getSquare(squareAt.getValueNbr()).getValueNbr() + "\n");
		
		possibleMoves.genPossibleMoves();
		
		for(int i = 0; i < possibleMoves.getPossibleSquares().size(); i++){
			
			if(square.getValueNbr() == possibleMoves.getPossibleSquares().get(i).getValueNbr()){
				return true;
			}
		}
	
		return false;
	}
	
	
	public MoveType getMoveType(){
		return moveType;
	}
	
	public void setSquare(Square square){
		this.squareAt = square;
	}
	public Square getSquareAt(){
		return squareAt;
	}
	
	public PieceType getPieceType(){
		return pieceType;
	}
	
	public ChessColor getColor(){
		return color;
	}
	public MoveList getMoves(){
		possibleMoves.genPossibleMoves();
		return possibleMoves;

	}
	public void printPossibleMoves(){
		System.out.println(pieceType.name() + ": ");
		for(Square sq : possibleMoves.getPossibleSquares()){
			System.out.printf(sq.getValueNbr() + ", ");
		}
		System.out.println();
	}
}
