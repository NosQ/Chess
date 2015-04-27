package piece.pieces;

import board.ChessColor;
import piece.MoveType;
import piece.Piece;
import piece.PieceType;
import square.Square;

public class EmptyPiece extends Piece {
	
	public EmptyPiece(ChessColor color){
		super(PieceType.EMPTY, color);
	}

}
