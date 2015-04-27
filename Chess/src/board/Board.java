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
	
	
	//--------Konstruktor----------
	public Board(){
		
		mailbox = new MailBox();	
		
		whiteAttckBoard = new AttackBoard(this, ChessColor.WHITE);
		blackAttckBoard = new AttackBoard(this, ChessColor.BLACK);
		
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
				
		if(blackKingInCheck()){
			
//			if(!escapeCheck()){
//				System.out.println("checkmate, white won");
//			}
			
		}else{
			
			if(piece.movement(moveTo)){
				
				mailbox.getSquare(moveTo).setPiece(mailbox.getSquare(index).getPiece());
				mailbox.getSquare(index).setPiece(mailbox.getEmptyPiece());
				piece.setSquare(getSquare(moveTo));
				
				mailbox.getSquare(index).setOccupied(false);
				mailbox.getSquare(moveTo).setOccupied(true);
				
				updateAttackBoards();	
				
				//---------checkforcheck----------
				if (getWAttackBoard().inCheck()) {
					System.out.println("black king in check...");
				}
				if (getBAttackBoard().inCheck()) {
					System.out.println("white king in check...");
	
				}
					System.out.println("\nGiltligt drag");
					
			}else{
				System.out.println("\nOgiltligt drag");
			}			
		}		
	}
		
	public void updateAttackBoards(){
		whiteAttckBoard.updateAttackSquares();
		blackAttckBoard.updateAttackSquares();
		
		whiteAttckBoard.updateKingPosition();
		blackAttckBoard.updateKingPosition();
	}
	
	public void forceMove() {
		
		
		
	}
		
//	public void forceMovePiece(int index, int moveTo){
//		Piece piece = mailbox.getSquare(moveTo).getPiece();
//		mailbox.getSquare(moveTo).setPiece(mailbox.getSquare(index).getPiece());
//		mailbox.getSquare(index).setPiece(piece);
//		
//	}
	
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
	public void printEverything(){
		printBoard();
		printSquaresOccupied();
		printAttackBoards();
		printSquareValues();
	}
	
	//--------Main---------
//	public static void main(String[] args) {
//		
//		Board board = new Board();
//		board.printEverything();
//		
//		Scanner scanner = new Scanner(System.in);
//		int stsq = 0, tosq = 0;
//		
//		while(true){
//			
//			System.out.println("\nSkriv in vad du vill flytta: t.ex. " + "0,16");
//			
//			if(scanner.hasNext("exit")){
//				System.out.println("closed");
//				break;
//			}
//			
//			String inputMove = scanner.nextLine();
//			String[] positions = inputMove.split(",");
//			
//			stsq = Integer.parseInt(positions[0]);
//			tosq = Integer.parseInt(positions[1]);
//			
//			board.movePiece(stsq, tosq);
//			board.printEverything();			
//			
//		}
//		
//	}
	
	/*
	 * kollar om den får flytta sig
	 * och returnerar true eller false
	 */
	/*public boolean ifAllowedMovement(int index, int moveTo){
		Piece piece = mailbox[mailbox64[index]].getPiece();
		return piece.movement(index, moveTo);
	
	}*/
		
}	 