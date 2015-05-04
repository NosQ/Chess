package board;

import java.util.ArrayList;
import java.util.Scanner;

import piece.MoveList;
import piece.Piece;
import piece.PieceType;
import piece.pieces.EmptyPiece;
import square.Square;


public class Board {
	
	private MailBox mailbox;
	private AttackBoard whiteAttckBoard, blackAttckBoard;
	private DefenceBoard whiteDefenceBoard, blackDefenceBoard;
	
	//--------Konstruktor----------
	public Board(){
		
		mailbox = new MailBox();	
		
		whiteAttckBoard = new AttackBoard(this, ChessColor.WHITE);
		blackAttckBoard = new AttackBoard(this, ChessColor.BLACK);
		whiteDefenceBoard = new DefenceBoard(ChessColor.WHITE, this);
		blackDefenceBoard = new DefenceBoard(ChessColor.BLACK, this);
		
		updateAttackBoards();				
	}	
	
	public boolean whiteKingInCheck(){		
		return blackAttckBoard.inCheck();
	}
	public boolean blackKingInCheck(){
		return whiteAttckBoard.inCheck();
	}
	//-----------Move and Check-logic------------
	
	public void movePiece(int index, int moveTo){
			
		Piece piece = mailbox.getSquare(index).getPiece();
		
			
			if(piece.movement(moveTo)){
				
				mailbox.getSquare(moveTo).setPiece(mailbox.getSquare(index).getPiece());
				mailbox.getSquare(index).setPiece(mailbox.getEmptyPiece());
				piece.setSquare(getSquare(moveTo));
				
				mailbox.getSquare(index).setOccupied(false);
				mailbox.getSquare(moveTo).setOccupied(true);
				
				updateAttackBoards();	
				
				//---------checkforcheck----------
				if (blackKingInCheck() == true) {
					
					System.out.println("\nblack king in check...");
					blackDefenceBoard.generateEscapeSquares();
					blackDefenceBoard.printEscapeSquares();
					
					if(blackDefenceBoard.isEmpty()){						
						System.out.println("GAME OVER BITCH");
					}else{
						System.out.println("inte schack matt ");
					}
					
				}
				else if (getBAttackBoard().inCheck()) {
					System.out.println("white king in check...");
	
				}
					System.out.println("Giltligt drag");
					
			}else{
				System.out.println("Ogiltligt drag");
			}
	}		
	
	public void updateAttackBoards(){
		whiteAttckBoard.updateAttackSquares();
		blackAttckBoard.updateAttackSquares();
		
		whiteAttckBoard.updateKingPosition();
		blackAttckBoard.updateKingPosition();
	}

		
	public void forceMovePiece(int index, int moveTo){
		
		Piece pieceMvTo = mailbox.getSquare(moveTo).getPiece();
		Piece pieceBackup = pieceMvTo;
		
		//Sätter moveToSquare pjäs till squareAts pjäs.
		mailbox.getSquare(moveTo).setPiece(mailbox.getSquare(index).getPiece());
		
		mailbox.getSquare(index).setPiece(pieceMvTo);
		
		pieceMvTo.setSquare(getSquare(moveTo));
		
		mailbox.getSquare(index).setOccupied(false);
		mailbox.getSquare(moveTo).setOccupied(true);
	}
	
	
	
	//--------Get-Methods----------
	public AttackBoard getWAttackBoard() {
		return whiteAttckBoard;
	}
	
	public AttackBoard getBAttackBoard() {
		return blackAttckBoard;
	}

	public Square getKingPosition(ChessColor color){
		
		Square[] boardSquares = mailbox.getSquareList();
		
		for(Square square : boardSquares){
			
			if(square.getPiece().getPieceType() == PieceType.KING && square.getPiece().getColor() == color ){
				return square;
			}
		}
		return null;
	}	
	
	public boolean getLegacy(int index){
		return mailbox.getLegality(index);		
	}	
	/**
	 * Metoden retunerar den refarerade rutan
	 * 
	 * @param mailboxNbr En position i schackbrädet   
	 * @return Square man refarerar till
	 */
	public Square getSquare(int mailboxNbr){
		return mailbox.getSquare(mailboxNbr);
	}
	
	public Square[] getBoardSquares(){
		return mailbox.getSquareList();
	}
	
	/**
	 * Metoden retunerar vad för pjäs som står på den refarerade rutan
	 * @param mailboxNbr Positionen för rutan
	 * @return Vilken typ av pjäs som står på den refarerade ruten
	 */
	public int pieceOnSquare(int mailboxNbr){
		return mailbox.getSquare(mailboxNbr).getPiece().getPieceType().getPieceValue();
	}
	
	//--------Print-methods DOWN!!!---------
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//--------Print-methods---------
	public void printBoard(){
		System.out.println();		
		mailbox.printboard();			
		System.out.println();
	}
	public void printSquareValues(){
		System.out.println();
		mailbox.printSquareValues();
		System.out.println();
	}
	public void printSquaresOccupied(){
		mailbox.printSquaresOccupied();
		System.out.println();
	}
	public void printAttackBoards(){
		whiteAttckBoard.printAttackBoard();
		blackAttckBoard.printAttackBoard();
	}
	public void printPiecesSquare(){
		mailbox.printPiecesSquare();
	}
	
	public void printEverything(){
		printBoard();
		printSquaresOccupied();
		printAttackBoards();
		printSquareValues();
	}

}	