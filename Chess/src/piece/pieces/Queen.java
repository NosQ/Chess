package piece.pieces;

import board.ChessColor;
import piece.MoveType;
import piece.Piece;
import piece.PieceType;
import square.Square;

public class Queen extends Piece{

	public Queen(ChessColor color) {
		super(PieceType.QUEEN, color);
	}

}