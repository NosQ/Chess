package piece;

public class MoveType {

	private int[] offsets;
	
	public MoveType(PieceType pieceType){
		
		if (pieceType == PieceType.EMPTY){
			offsets = new int[]{};
		}		
		if (pieceType == PieceType.PAWN){
			offsets = new int[]{10, 9, 11, -9, -11};
		}
		if (pieceType == PieceType.ROOK) {
			offsets = new int[]{10, -10, 1, -1};
		}
		if (pieceType == PieceType.KNIGHT){
			offsets = new int[]{21, 19, 12, 8, -21, -19, -12, -8};
		}
		if (pieceType == PieceType.BISHOP){
			offsets = new int[]{11, 9, -9, -11};
		}
		if (pieceType == PieceType.QUEEN){
			offsets = new int[]{10, 11, 9, 1, -1, -9, -11, -10};
		}
		if (pieceType == PieceType.KING){
			offsets = new int[]{10, 11, 9, 1, -1, -9, -11, -10};
		}		
		
	}
	
	public int[] getOffsets(){
		return offsets;
	}
	public int getOffset(int i){
		return offsets[i];
	}
}
