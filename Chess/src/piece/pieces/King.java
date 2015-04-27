package piece.pieces;

import board.ChessColor;
import piece.MoveType;
import piece.Piece;
import piece.PieceType;
import square.Square;

public class King extends Piece{

	public King(ChessColor color) {
		super(PieceType.KING, color);
	}

}