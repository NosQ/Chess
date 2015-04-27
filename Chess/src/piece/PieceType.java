package piece;

public enum PieceType {
	
	EMPTY (0),
	PAWN  (1),
	ROOK  (2),
	KNIGHT(3),
	BISHOP(4),	
	QUEEN (5),
	KING  (6);
	
	private int pieceValue;
	
	PieceType(int value) {
		pieceValue = value;
	}
	
	public int getPieceValue(){
		return pieceValue;
	}
	
}
