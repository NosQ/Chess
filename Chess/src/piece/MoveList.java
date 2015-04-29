package piece;

import java.util.ArrayList;
import board.ChessColor;
import square.Square;

public class MoveList {
	
	private ArrayList<Square> possibleSquares = new ArrayList<Square>();
	private Piece piece;
	
	public MoveList(Piece piece){		
		this.piece = piece;
	}
	
	public void genPossibleMoves(){
		
		switch (piece.getPieceType()){
			
		case PAWN: 	
//			System.out.println("bonne startpos: " + piece.getSquareAt().getValueNbr());
			pawnMoves();
//			printPossibleMoves();
			break; 
			
		case ROOK:
//			System.out.println("torn startpos: " + piece.getSquareAt().getValueNbr());
			rookMoves();
//			printPossibleMoves();
			break;
			
		case KNIGHT:
//			System.out.println("hästpolle startpos: " + piece.getSquareAt().getValueNbr());
			knightMoves();
//			printPossibleMoves();
			break;
			
		case BISHOP:
//			System.out.println("löpare startpos: " + piece.getSquareAt().getValueNbr());
			bishopMoves();
//			printPossibleMoves();
			break;
		
		case QUEEN:
//			System.out.println("drottning startpos: " + piece.getSquareAt().getValueNbr());
			queenMoves();
//			printPossibleMoves();
			break;
			
		case EMPTY:
			break;
			
		case KING:
//			System.out.println("kungen startpos: " + piece.getSquareAt().getValueNbr());
			kingMoves();
//			printPossibleMoves();
			break;	
		default:
			break;
		}		
	}
	
	private void pawnMoves(){		
		
		possibleSquares.clear();
		
		
		int standsOn = piece.getSquareAt().getValueNbr();
		
		Square squareAhead = piece.getSquareAt().getBoard().getSquare(standsOn, 10);
		
		if (piece.getColor() == ChessColor.WHITE) {
			squareAhead = piece.getSquareAt().getBoard().getSquare(standsOn, -10);
		} 
		
		if(squareAhead.getValueNbr() != -1 && (!squareAhead.isOccupied() && squareAhead.getPiece().getColor() != piece.getColor())){
			
			possibleSquares.add(squareAhead);
			
			// Denna sats ändrades så att man enbart kollar efter en bondes position istället för om den är flyttad.
			if(piece.getSquareAt().getValueNbr() >= 8 && piece.getSquareAt().getValueNbr() <= 15 || piece.getSquareAt().getValueNbr() >= 48 && piece.getSquareAt().getValueNbr() <= 55){
				Square squareAhead2 = piece.getSquareAt().getBoard().getSquare(standsOn, 20);
				
				if (piece.getColor() == ChessColor.WHITE) {
					squareAhead2 = piece.getSquareAt().getBoard().getSquare(standsOn, -20);
				}
				
				if(squareAhead2.getValueNbr() != -1 && (!squareAhead2.isOccupied() && squareAhead2.getPiece().getColor() != piece.getColor())){
					possibleSquares.add(squareAhead2);
					
				}			
			}
			
			Square squareFromRightSide = null;
			Square squareFromLeftSide = null;
			
			//-----------PawnAttackRight-----------
			if(piece.getColor() == ChessColor.WHITE) {
				squareFromRightSide = piece.getSquareAt().getBoard().getSquare(standsOn, -9);			
			}
			
			else if(piece.getColor() == ChessColor.BLACK) {
				squareFromRightSide = piece.getSquareAt().getBoard().getSquare(standsOn, 9);
			}
			
			if(( isValidSquare(squareFromRightSide) && !isOccupiedByOwn(squareFromRightSide) && squareFromRightSide.getPiece().getPieceType() != PieceType.EMPTY ) || isOccupiedByOpponent(squareFromRightSide) ){
			
				if ((isValidSquare(squareFromRightSide) && !isOccupiedByOwn(squareFromRightSide)) || isOccupiedByOpponent(squareFromRightSide)) {

					if (isOccupiedByOpponent(squareFromRightSide)) {
						possibleSquares.add(squareFromRightSide);
					
					} 

				}//first inner if

			}//outer if
			
			//-----------PawnAttackLeft-----------
			
			if(piece.getColor() == ChessColor.WHITE) {
				squareFromLeftSide = piece.getSquareAt().getBoard().getSquare(standsOn, -11);			
			}
			
			else if(piece.getColor() == ChessColor.BLACK) {
				squareFromLeftSide = piece.getSquareAt().getBoard().getSquare(standsOn, 11);
			}
			
			if(( isValidSquare(squareFromLeftSide) && !isOccupiedByOwn(squareFromLeftSide) && squareFromLeftSide.getPiece().getPieceType() != PieceType.EMPTY ) || isOccupiedByOpponent(squareFromLeftSide) ){
			
				if ((isValidSquare(squareFromLeftSide) && !isOccupiedByOwn(squareFromLeftSide)) || isOccupiedByOpponent(squareFromLeftSide)) {

					if (isOccupiedByOpponent(squareFromLeftSide)) {
						possibleSquares.add(squareFromLeftSide);
					
					} 
				}//first inner if

			}//outer if
		}//absolut outer if	
	}
	
	private void rookMoves(){
		
		possibleSquares.clear();		
		
		int standsOn = piece.getSquareAt().getValueNbr();
		
		for(int offset : piece.getMoveType().getOffsets()){
			
			for(int i = 1; i < 8; i++ ){
				
				Square squareMvTo = piece.getSquareAt().getBoard().getSquare(standsOn, i * offset);
//				System.out.println(squareMvTo.isOccupied() + " " + squareMvTo.getValueNbr());
						
				//om ruta inte är tom OCH rutans pjäs färg inte är samma som denna pjäs färg.
				if(( isValidSquare(squareMvTo) && !isOccupiedByOwn(squareMvTo) ) || isOccupiedByOpponent(squareMvTo) ){
					
					if(isOccupiedByOpponent(squareMvTo)){
						possibleSquares.add(squareMvTo);
						break;						
					}else{
						possibleSquares.add(squareMvTo);
					}
				}
				else{
					break;
				}			
			}//end inner for-loop	
		}//end outer for-loop
	}
	
	private void knightMoves() {
		
		possibleSquares.clear();		
		
		int standsOn = piece.getSquareAt().getValueNbr();
		
		for(int offset : piece.getMoveType().getOffsets()){		
				
			Square squareMvTo = piece.getSquareAt().getBoard().getSquare(standsOn, offset);
			
			if(( isValidSquare(squareMvTo) && !isOccupiedByOwn(squareMvTo) ) || isOccupiedByOpponent(squareMvTo) ){
				
				possibleSquares.add(squareMvTo);
			}
			else{
				continue;
			}			
		
		}//end outer for-loop
		
	}
	
	private void bishopMoves() {
		
	possibleSquares.clear();		
		
		int standsOn = piece.getSquareAt().getValueNbr();
		
		for(int offset : piece.getMoveType().getOffsets()){
			
			for(int i = 1; i < 8; i++ ){
				
				Square squareMvTo = piece.getSquareAt().getBoard().getSquare(standsOn, i * offset);
				
				if(( isValidSquare(squareMvTo) && !isOccupiedByOwn(squareMvTo) ) || isOccupiedByOpponent(squareMvTo) ){
					
					if(isOccupiedByOpponent(squareMvTo)){
						possibleSquares.add(squareMvTo);
						break;						
					}else{
						possibleSquares.add(squareMvTo);
					}
					
				}
				else{
					break;
				}			
			}//end inner for-loop	
		}//end outer for-loop
		
	}
	
	private void queenMoves() {
		rookMoves();
		bishopMoves();
	}	
	
	private void kingMoves() {
		
		possibleSquares.clear();		
		
		int standsOn = piece.getSquareAt().getValueNbr();
		
		for(int offset : piece.getMoveType().getOffsets()){			
				
				Square squareMvTo = piece.getSquareAt().getBoard().getSquare(standsOn, offset);
//				System.out.println(squareMvTo.isOccupied() + " " + squareMvTo.getValueNbr());
						
				//om ruta inte är tom OCH rutans pjäs färg inte är samma som denna pjäs färg.
				if(( isValidSquare(squareMvTo) && !isOccupiedByOwn(squareMvTo) ) || isOccupiedByOpponent(squareMvTo) ){
					
					possibleSquares.add(squareMvTo);					
				}
				else{
					continue;
				}			
		}//end outer for-loop	
	}
	
	private boolean isValidSquare(Square squareMvTo){
		
		return squareMvTo.getValueNbr() != -1;
	}
	
	private boolean isOccupiedByOwn(Square squareMvTo){
		
		return squareMvTo.isOccupied() && squareMvTo.getPiece().getColor() == piece.getColor();
	}
	
	private boolean isOccupiedByOpponent(Square squareMvTo){
		
		return squareMvTo.isOccupied() && squareMvTo.getPiece().getColor() != piece.getColor();
	}
		
	public ArrayList<Square> getPossibleSquares(){
		return possibleSquares;
	}
	
	private void printPossibleMoves(){
		
		System.out.println("\n Possible moves (mailboxNbrs): ");
		
		for(Square square : possibleSquares){
			
			System.out.print(square.getValueNbr() + ", ");
			
		}
		System.out.println();		
	}
}
