package board;

import java.util.ArrayList;
import java.util.Iterator;

import piece.Piece;
import piece.PieceType;
import square.Square;

public class DefenceBoard {
    
	private boolean inCheck = true;
	private ChessColor color;
	private Board board;
	private ArrayList<Square> escapeSquares = new ArrayList<Square>();
	
	public DefenceBoard(ChessColor color, Board board) {
		this.color = color;
		this.board = board;
	}
	
	public void generateEscapeSquares(){
		
		escapeSquares.clear();
		Square[] boardSquares = board.getBoardSquares();
		
		//För alla rutor i spelet.
		for(int sqNr = 0; sqNr < boardSquares.length; sqNr++){
			
			Piece piece = board.getSquare(sqNr).getPiece();
			Square sqAt = piece.getSquareAt();			
			
			if (piece.getColor() == color) {
				
				piece.printPossibleMoves();
				
				//För alla rutor i pjäsens possibleMoves.
				for(int sqMvNr = 0; sqMvNr < piece.getMoves().getPossibleSquares().size(); sqMvNr++){
					
					Square sqMv = piece.getMoves().getPossibleSquares().get(sqMvNr);
					
					//För att fixa bugg:445
					Piece pieceBackup = sqMv.getPiece();					
					System.out.println("pieceBackup = " + pieceBackup.getPieceType());
					
					//Om rutan i attackradie inte är en tom ruta, så gör den tom.
					if (sqMv.getPiece().getPieceType() != PieceType.EMPTY){						
						sqMv.setPiece(sqMv.getMailbox().getEmptyPiece());						
					}
					
					board.forceMovePiece(sqAt, sqMv);						
					board.updateAttackBoards();			
					board.printAttackBoards();
					System.out.println("\nKungens Ruta: " + board.getKingPosition(ChessColor.BLACK).getValueNbr());
					
					//kollar efter schack vid simul-drag
					inCheck = board.blackKingInCheck();
					
					board.printBoard();						
					System.out.println("First forcePieceMove ");
					board.printSquaresOccupied();
					System.out.println("Black King inCheck =  " + inCheck);
					
					//Om kungen INTE är i schack
					if (inCheck == false) {
						
						escapeSquares.add(sqMv);
	
						sqAt.setPiece(pieceBackup);
						
						board.forceMovePiece(sqMv, sqAt);
						
						if(sqMv.getPiece().getPieceType() != PieceType.EMPTY){
							sqMv.setOccupied(true);
						}
						
						System.out.println("reverse forcePieceMove ");
						board.printSquaresOccupied();
						
					} else {
						
						sqAt.setPiece(pieceBackup);
						board.forceMovePiece(sqMv, sqAt);
						
						if(sqMv.getPiece().getPieceType() != PieceType.EMPTY)
							sqMv.setOccupied(true);
						
						System.out.println("\nreverse forcePieceMove ");
						board.printSquaresOccupied();
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















