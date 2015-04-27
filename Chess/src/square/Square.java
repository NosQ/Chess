package square;

import javax.swing.JLabel;

import board.MailBox;
import piece.Piece;
import piece.PieceType;
import piece.pieces.EmptyPiece;


public class Square {
	
	private final MailBox board;
	private final boolean illegal;
	private final int valueNbr;
	private Piece piece;
	private boolean occupied = false;
	
	//-----Illegal Square------
	public Square(){
		this(null, false, -1, new EmptyPiece(null));
	}
	
	//-----Legal Square------
	public Square(MailBox board, boolean legality, int valueNbr, Piece piece){
		this.board = board;
		this.illegal = legality;
		this.valueNbr = valueNbr;		
		this.piece = piece;
	}
	
	public boolean isOccupied(){
		return occupied;
	}
	public void setOccupied(boolean occupied){
		this.occupied = occupied; 
	}
	
	public boolean isLegal() {
		return illegal;
	}

	public int getValueNbr() {
		return valueNbr;
	}

	public Piece getPiece() {
		return piece;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}
	
	public MailBox getBoard(){
		return board;
	}
}
