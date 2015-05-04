package board;

import java.util.ArrayList;
import java.util.Iterator;

import piece.Piece;
import square.Square;

public class DefenceBoard {
    
	private ChessColor color;
	private Board board;
	private ArrayList<Square> escapeSquares = new ArrayList<Square>();
	
	public DefenceBoard(ChessColor color, Board board) {
		this.color = color;
		this.board = board;
	}
	
	public void generateEscapeSquares(){
		boolean inCheck = true;
		escapeSquares.clear();
		Square[] boardSquares = board.getBoardSquares();
		
		for(int sqNr = 0; sqNr < boardSquares.length; sqNr++){
			
			Piece piece = board.getSquare(sqNr).getPiece();
			Square sqAt = piece.getSquareAt();
			
			
			if (piece.getColor() == color) {
				
				piece.printPossibleMoves();
				
				for(int sqMvNr = 0; sqMvNr < piece.getMoves().getPossibleSquares().size(); sqMvNr++){
					
					Square sqMv = piece.getMoves().getPossibleSquares().get(sqMvNr);
					
					board.forceMovePiece(sqAt.getValueNbr(), sqMv.getValueNbr());					
					board.updateAttackBoards();					
					inCheck = board.blackKingInCheck();
					
					board.printBoard();										
					System.out.println("Black King inCheck =  " + board.getWAttackBoard().inCheck());
					
					//Om kungen INTE är i schack
					if (inCheck == false) {
						
						escapeSquares.add(sqMv);						
						board.forceMovePiece(sqMv.getValueNbr(), sqAt.getValueNbr());
						break;
					} else {
						board.forceMovePiece(sqMv.getValueNbr(), sqAt.getValueNbr());
					}
				}
				
			}
		}
		
	}
	
	public boolean isEmpty(){
		return escapeSquares.isEmpty();
	}
	
	public ArrayList<Square> getEscapeSquares(){
		return escapeSquares;
	}
	public void printEscapeSquares(){
		System.out.print("escapeSquares: ");
		for(Square sq : escapeSquares){
			System.out.printf(sq.getValueNbr() + ",");
		}
		System.out.println();
	}
}

























/*
 public boolean simulateCheck() {
		
		boolean inCheck = true;
		
		System.out.println("testing simulation for " + color);
		Square[] boardSquares = board.getBoardSquares();
		
		//för alla rutor i brädet
		for (int sqNr = 0; sqNr < boardSquares.length; sqNr++) {
			
			if (boardSquares[sqNr].getPiece().getColor() == color) {
				
//				ArrayList<Square> possibleMoves = boardSquares[sqNr].getPiece().getMoves().getPossibleSquares();
				System.out.println("antal drag = "+ possibleMoves.size());
				
				int pieceStartIndex = boardSquares[sqNr].getValueNbr();
				boardSquares[sqNr].getPiece().printPossibleMoves();
				
				//för alla moves 
				for (int possSqNr = 0; possSqNr < possibleMoves.size(); possSqNr++) {
					
					System.out.println("lol " + possSqNr);
					int pieceNextSq = possibleMoves.get(possSqNr).getValueNbr();
					
					//flyttar pjäsen till nästa possible square
					board.forceMovePiece(pieceStartIndex, pieceNextSq);
					
					board.updateAttackBoards();
					
					board.printBoard();					
					System.out.println("Black King inCheck =  " + board.getWAttackBoard().inCheck());
					
					//Om kungen INTE är i schack
					if (board.blackKingInCheck() == false) {
						
						board.forceMovePiece(pieceNextSq, pieceStartIndex);
						return false;
					} else {
						board.forceMovePiece(pieceNextSq, pieceStartIndex);
					}
				}
			}
		}
		System.out.println("SIMULERING KLAR");
		return true;
	}
 
 */















