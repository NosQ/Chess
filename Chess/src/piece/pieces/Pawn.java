package piece.pieces;

import board.ChessColor;
import piece.MoveList;
import piece.MoveType;
import piece.Piece;
import piece.PieceType;
import square.Square;

public class Pawn extends Piece {
	private int index;
	
	public Pawn(ChessColor color){
		super(PieceType.PAWN, color);
	}
	
}
