package piece.pieces;

import board.ChessColor;
import piece.MoveType;
import piece.Piece;
import piece.PieceType;
import square.Square;

public class Rook extends Piece{

	public Rook(ChessColor color) {
		super(PieceType.ROOK, color);
	}
}
