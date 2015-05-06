package board;

import java.util.ArrayList;
import java.util.Scanner;

import piece.MoveList;
import piece.Piece;
import piece.PieceType;
import piece.pieces.EmptyPiece;
import piece.pieces.King;
import square.Square;


public class Board {
	
	private MailBox mailbox;
	private AttackBoard whiteAttckBoard, blackAttckBoard;
	private DefenceBoard whiteDefenceBoard, blackDefenceBoard;
	private boolean isMate;
	
	//--------Konstruktor----------
	public Board(){		
		mailbox = new MailBox();			
		whiteAttckBoard = new AttackBoard(this, ChessColor.WHITE);
		blackAttckBoard = new AttackBoard(this, ChessColor.BLACK);
		whiteDefenceBoard = new DefenceBoard(ChessColor.WHITE, this);
		blackDefenceBoard = new DefenceBoard(ChessColor.BLACK, this);		
		updateAttackBoards();	
	//	printAttackBoards();
	}	
	
	public boolean kingInCheck(ChessColor color){
		
		if(color == ChessColor.WHITE){
			for(Square attackSq : blackAttckBoard.getAttackSquares()){
				if(attackSq == getKingPosition(ChessColor.WHITE)){
					System.out.println("White King in check");
					return true;
				}
			}
		}
		
		if(color == ChessColor.BLACK){			
			for(Square attackSq : whiteAttckBoard.getAttackSquares()){
				if(attackSq == getKingPosition(ChessColor.BLACK)){
					System.out.println("Black King in check");
					return true;
				}
			}
		}
		return false;
	}
	
	public Square getKingPosition(ChessColor color){		
		
		Square[] boardSquares = getBoardSquares();
		
		for(Square square : boardSquares){			
			if(square.getPiece().getPieceType() == PieceType.KING && square.getPiece().getColor() == color ){
				return square;
			}
		}
		return null;
	}	
	
	//-----------Move and Check-logic------------	
	public boolean movePiece(int index, int moveTo){
			
		Piece piece = mailbox.getSquare(index).getPiece();		
			
			if(piece.movement(moveTo)){
				
				mailbox.getSquare(moveTo).setPiece(mailbox.getSquare(index).getPiece());
				mailbox.getSquare(index).setPiece(mailbox.getEmptyPiece());
				piece.setSquare(getSquare(moveTo));
				
				mailbox.getSquare(index).setOccupied(false);
				mailbox.getSquare(moveTo).setOccupied(true);
				
				updateAttackBoards();	
//				printAttackBoards();
				//---------checkforcheck----------
				if (kingInCheck(ChessColor.BLACK) == true) {
					
//					System.out.println("\nblack king in check...");
					blackDefenceBoard.generateEscapeSquares();
					blackDefenceBoard.printEscapeSquares();
					
					if(blackDefenceBoard.isEmpty()){						
						System.out.println("GAME OVER BITCH, Black lost");
						setMate(true);
					}
					else{
						System.out.println("inte schack matt ");
					}					
				}
				
				if (kingInCheck(ChessColor.WHITE) == true) {
//					System.out.println("white king in check...");	
					whiteDefenceBoard.generateEscapeSquares();
					whiteDefenceBoard.printEscapeSquares();
					
					if(whiteDefenceBoard.isEmpty()){						
						System.out.println("GAME OVER BITCH, White lost");
						setMate(true);
					}
					else{
						System.out.println("inte schack matt ");
					}					
				}				
				System.out.println("Giltligt drag");
				return true;
					
			}
			else{
				System.out.println("Ogiltligt drag");
				return false;
			}
			
	}	
	
	public void updateAttackBoards(){
		whiteAttckBoard.updateAttackSquares();
		blackAttckBoard.updateAttackSquares();		
	}
		
	public void forceMovePiece(Square startSquare, Square endSquare){
			
			Piece pieceMvTo = endSquare.getPiece();
			
			//Sätter moveToSquare pjäs till squareAts pjäs.
			endSquare.setPiece(startSquare.getPiece());
			
			startSquare.setPiece(pieceMvTo);
			
			pieceMvTo.setSquare(endSquare);
			
			startSquare.setOccupied(false);
			endSquare.setOccupied(true);
		}
	
	public void setMate(boolean isMate) {
		this.isMate = isMate;
	}
	
	public boolean isMate() {
		return isMate;
	}
	
	//--------Get-Methods----------
		
	public boolean getLegacy(int index){
		return mailbox.getLegality(index);		
	}	
	
	public Square getSquare(int mailboxNbr){
		return mailbox.getSquare(mailboxNbr);
	}
	
	public int pieceOnSquare(int mailboxNbr){
		return mailbox.getSquare(mailboxNbr).getPiece().getPieceType().getPieceValue();
	}
	
	public Square[] getBoardSquares(){
		return mailbox.getSquareList();
	}

	
	//--------Print-methods---------
	public void printBoard(){
		mailbox.printboard();			
		System.out.println();
	}
	public void printSquareValues(){
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