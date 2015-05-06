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
	private Piece pieceBackup;
	
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
					pieceBackup = sqMv.getPiece();					
					System.out.println("pieceBackup = " + pieceBackup.getPieceType() + " " + pieceBackup.getColor().name() + " pos = " + pieceBackup.getSquareAt().getValueNbr());
					
					//Om rutan i attackradie inte är en tom ruta, så gör den tom.
					if (sqMv.getPiece().getPieceType() != PieceType.EMPTY){						
						sqMv.setPiece(sqMv.getMailbox().getEmptyPiece());						
					}
					
					board.forceMovePiece(sqAt, sqMv);						
					board.updateAttackBoards();			
					board.printAttackBoards();
					System.out.println("\nKungens Ruta: " + board.getKingPosition(color).getValueNbr());
					
					//kollar efter schack vid simul-drag
					inCheck = board.kingInCheck(color);
					
					printDebuggAfterSimul();					
					
					genEscapeSquares(inCheck, pieceBackup, sqAt, sqMv);
				}
				
			}
		}
		
	}
	
	private void genEscapeSquares(boolean inCheck, Piece pieceBackup, Square sqAt, Square sqMv){
		//Om kungen INTE är i schack
		if(inCheck == false){
			escapeSquares.add(sqMv);
			sqAt.setPiece(pieceBackup);
			board.forceMovePiece(sqMv, sqAt);
			
			if(sqMv.getPiece().getPieceType() != PieceType.EMPTY){
				sqMv.setOccupied(true);
			}
			
			pieceBackup.setSquare(sqMv);	//solved bugg:445			
			printDebuggReverse(pieceBackup);
			
		}
		else{
			sqAt.setPiece(pieceBackup);			
			board.forceMovePiece(sqMv, sqAt);
			
			if(sqMv.getPiece().getPieceType() != PieceType.EMPTY){
				sqMv.setOccupied(true);
			}
			
			pieceBackup.setSquare(sqMv);	//solved bugg:445			
			printDebuggReverse(pieceBackup);
		}		
	}
	
	public boolean isEmpty(){
		return escapeSquares.isEmpty();
	}
	
	public ArrayList<Square> getEscapeSquares(){
		return escapeSquares;
	}
	
	//----------Print-methods-----------
	private void printDebuggAfterSimul(){
		board.printBoard();						
		System.out.println("First forcePieceMove ");
		board.printSquaresOccupied();
		System.out.println("Black King inCheck =  " + inCheck);
	}
	
	private void printDebuggReverse(Piece pieceBackup){
		System.out.println("reverse forcePieceMove ");
		board.printBoard();
		System.out.println("Backup Pjäsens possible moves: ");
		pieceBackup.printPossibleMoves();
		System.out.println("pieceBackup = " + pieceBackup.getPieceType() + " " + pieceBackup.getColor().name() + " pos = " + pieceBackup.getSquareAt().getValueNbr());
		board.printSquaresOccupied();
	}
	
	public void printEscapeSquares(){
		System.out.print(color.name() + " escapeSquares: ");
		for(Square sq : escapeSquares){
			System.out.printf(sq.getValueNbr() + ",");
		}
		System.out.println();
	}
}







































