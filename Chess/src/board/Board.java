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
	
	//--------Konstruktor----------
	public Board(){		
		mailbox = new MailBox();			
		whiteAttckBoard = new AttackBoard(this, ChessColor.WHITE);
		blackAttckBoard = new AttackBoard(this, ChessColor.BLACK);
		whiteDefenceBoard = new DefenceBoard(ChessColor.WHITE, this);
		blackDefenceBoard = new DefenceBoard(ChessColor.BLACK, this);		
		updateAttackBoards();	
		printAttackBoards();
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
		else if(color == ChessColor.BLACK){			
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
	public void movePiece(int index, int moveTo){
			
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
						System.out.println("GAME OVER BITCH");
					}
					else{
						System.out.println("inte schack matt ");
					}					
				}
				else if (kingInCheck(ChessColor.WHITE) == true) {
//					System.out.println("white king in check...");	
					whiteDefenceBoard.generateEscapeSquares();
					whiteDefenceBoard.printEscapeSquares();
					
					if(blackDefenceBoard.isEmpty()){						
						System.out.println("GAME OVER BITCH");
					}
					else{
						System.out.println("inte schack matt ");
					}					
				}				
				System.out.println("Giltligt drag");
					
			}
			else{
				System.out.println("Ogiltligt drag");
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*//Fungerar enbart om man bytar till en tom ruta. 	
	public void forceMovePiece(int index, int moveTo){
		
		Piece pieceMvTo = mailbox.getSquare(moveTo).getPiece();
		Piece pieceBackup = pieceMvTo;
		
		//Sätter moveToSquare pjäs till squareAts pjäs.
		mailbox.getSquare(moveTo).setPiece(mailbox.getSquare(index).getPiece());
		
		mailbox.getSquare(index).setPiece(pieceMvTo);
		
		pieceMvTo.setSquare(getSquare(moveTo));
		
		mailbox.getSquare(index).setOccupied(false);
		mailbox.getSquare(moveTo).setOccupied(true);
	}*/
	
	
	
	
	//--------Print-methods DOWN!!!---------
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
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