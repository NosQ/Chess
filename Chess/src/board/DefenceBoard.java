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
				
				iteratePiecePossSquares(piece, sqAt);				
			}
		}		
	}
	
	private void iteratePiecePossSquares(Piece piece, Square sqAt){
		
		//För alla rutor i pjäsens possibleMoves.
		for(int sqMvToNr = 0; sqMvToNr < piece.getMoves().getPossibleSquares().size(); sqMvToNr++){
			
			Square sqMvTo = piece.getMoves().getPossibleSquares().get(sqMvToNr);
			
			//För att fixa bugg:445
			pieceBackup = sqMvTo.getPiece();					
			System.out.println("pieceBackup = " + pieceBackup.getPieceType() + " " + pieceBackup.getColor().name() + " pos = " + pieceBackup.getSquareAt().getValueNbr());
			
			
			
			board.forceMovePiece(sqAt, sqMvTo);						
			board.updateAttackBoards();			
			board.printAttackBoards();
			System.out.println("\nKungens Ruta: " + board.getKingPosition(color).getValueNbr());
			
			//kollar efter schack vid simul-drag
			inCheck = board.kingInCheck(color);
			
			printDebuggAfterSimul();					
			
			addEscapeSquare(inCheck, pieceBackup, sqAt, sqMvTo);
		}		
	}
	
	private void addEscapeSquare(boolean inCheck, Piece pieceBackup, Square squareBeforeMove, Square squareMovedTo){
		//Om kungen INTE är i schack
		if(inCheck == false){
			
			escapeSquares.add(squareMovedTo);			
			board.reverseMove(squareBeforeMove, squareMovedTo, pieceBackup);
			
			printDebuggReverse(pieceBackup);			
		}
		else{			
			board.reverseMove(squareBeforeMove, squareMovedTo, pieceBackup);		
			
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







































