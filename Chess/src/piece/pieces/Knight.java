package piece.pieces;

import board.ChessColor;
import piece.MoveType;
import piece.Piece;
import piece.PieceType;
import square.Square;

public class Knight extends Piece{

	public Knight(ChessColor color) {
		super(PieceType.KNIGHT, color);
	}

}