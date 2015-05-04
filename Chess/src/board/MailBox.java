package board;

import piece.Piece;
import piece.PieceType;
import piece.pieces.Bishop;
import piece.pieces.EmptyPiece;
import piece.pieces.King;
import piece.pieces.Knight;
import piece.pieces.Pawn;
import piece.pieces.Queen;
import piece.pieces.Rook;
import square.Square;

public class MailBox {

	private Square[] squares = new Square[120];
	private MailBox64 mailbox64 = new MailBox64();	
	
	private EmptyPiece emtpyPiece;
	
	//-------Konsruktor---------
	public MailBox() {
		
		emtpyPiece = new EmptyPiece(ChessColor.NEUTRAL);
		initMailbox(squares);
		
	}

	//-------Metoder----------
		
	/**
	 * Metoden retunerar bara squares som är valid. de som är valid är de som finns i mailbox64-indexes
	 * 
	 * @param mailboxNbr Position 0..63 i mailbox64
	 * @return Square Den ruta man refarerar till i det "riktiga" brädet
	 */
	public Square getSquare(int mailboxNbr){		
		return squares[mailbox64.getValue(mailboxNbr)];		
	}
	//Kan returnera en Ilegal ruta
	public Square getSquare(int mailboxNbr, int offset){
		return squares[mailbox64.getValue(mailboxNbr) + offset];	
	}
	public Square[] getSquareList(){
		
		Square[] validSquares = new Square[64];
		
		for(int i = 0; i < 64; i++){
			validSquares[i] = getSquare(i);
		}
		return validSquares;
	}
	/**
	 * Metoden ska retunera om square-objektet är illegal 
	 * 
	 * @param wholeMailbox
	 * @return
	 */
	public boolean getLegality(int wholeMailbox){
		return squares[wholeMailbox].isLegal();
	}
	
	public void setPiece(int index, Piece piece){
		squares[index].setPiece(piece);
	}
	
	/*
	 * Generar ett bräde med squares och pjäser
	 * i första raden och resterande rutor får tomma pjäser
	 */
	private void initMailbox(Square[] mailbox) {
			
		int mailBoxNbr = 0;

		for (int i = 0; i < mailbox.length; i++) {
			
			// Placerar Illegala Square-Objekt här sedan istället för int:ar
			if (i < 20 || i > 100 || (i % 10 == 0) || (i % 10 == 9)) {
				
				mailbox[i] = new Square();//Illegal square
				
			}else {	// Placerar Square-Objekt här sedan istället för int:ar
				
//				mailbox[i] = new Square(true, mailbox64.getValue(mbNr), new EmptyPiece());			
				
				//initierar tomma rutor och fyller sen på med svarta - vita pjäser
				
				initNeutralPieces(i, mailBoxNbr);	
				initBlackPieces(i, mailBoxNbr);
				initWhitePieces(i, mailBoxNbr);

				mailBoxNbr++;
			}
		}// for-loop end		
	}
	
	private void initBlackPieces(int i, int mbNr) {
		
		//Torn
		if(i == mailbox64.getValue(0) || i == mailbox64.getValue(7)){
			squares[i] = new Square(this, true, mbNr, new Rook(ChessColor.BLACK));
//			System.out.println("torn " + squares[mailbox64.getValue(mbNr)].getPiece().getPieceType().getPieceValue() )
			setSquareToPiece(i);
			
		}
		//Häst
		else if(i == mailbox64.getValue(1) || i == mailbox64.getValue(6)){
			squares[i] = new Square(this, true, mbNr, new Knight(ChessColor.BLACK));
//			System.out.println("häst " + squares[mailbox64.getValue(mbNr)].getPiece().getPieceType().getPieceValue() );
			setSquareToPiece(i);
		} 
		//Löpare
		else if(i == mailbox64.getValue(2) || i == mailbox64.getValue(5)){
			squares[i] = new Square(this, true, mbNr, new Bishop(ChessColor.BLACK));
//			System.out.println("löpare " + squares[mailbox64.getValue(mbNr)].getPiece().getPieceType().getPieceValue() );
			setSquareToPiece(i);
		}
		//Drottning
		else if(i == mailbox64.getValue(3)){
			squares[i] = new Square(this, true, mbNr, new Queen(ChessColor.BLACK));
//			System.out.println("drott " + squares[mailbox64.getValue(mbNr)].getPiece().getPieceType().getPieceValue() );
			setSquareToPiece(i);
		}
		//Kung
		else if(i == mailbox64.getValue(4)){
			squares[i] = new Square(this, true, mbNr, new King(ChessColor.BLACK));
//			System.out.println("knug " + squares[mailbox64.getValue(mbNr)].getPiece().getPieceType().getPieceValue() );
			setSquareToPiece(i);
		} 
	 		
		//Bönder
		else if(i >= mailbox64.getValue(8) && i <= mailbox64.getValue(15)){
			squares[i] = new Square(this, true, mbNr, new Pawn(ChessColor.BLACK));	
			setSquareToPiece(i);
		}
		
	}
	
	private void initWhitePieces(int i, int mbNr) {
		//Torn
		if(i == mailbox64.getValue(56) || i == mailbox64.getValue(63)){
			squares[i] = new Square(this, true, mbNr, new Rook(ChessColor.WHITE));
//			System.out.println("torn " + squares[mailbox64.getValue(mbNr)].getPiece().getPieceType().getPieceValue() )
			setSquareToPiece(i);
			
		}
		//Häst
		else if(i == mailbox64.getValue(57) || i == mailbox64.getValue(62)){
			squares[i] = new Square(this, true, mbNr, new Knight(ChessColor.WHITE));
//			System.out.println("häst " + squares[mailbox64.getValue(mbNr)].getPiece().getPieceType().getPieceValue() );
			setSquareToPiece(i);
		} 
		//Löpare
		else if(i == mailbox64.getValue(58) || i == mailbox64.getValue(61)){
			squares[i] = new Square(this, true, mbNr, new Bishop(ChessColor.WHITE));
//			System.out.println("löpare " + squares[mailbox64.getValue(mbNr)].getPiece().getPieceType().getPieceValue() );
			setSquareToPiece(i);
		}
		//Drottning
		else if(i == mailbox64.getValue(59)){
			squares[i] = new Square(this, true, mbNr, new Queen(ChessColor.WHITE));
//			System.out.println("drott " + squares[mailbox64.getValue(mbNr)].getPiece().getPieceType().getPieceValue() );
			setSquareToPiece(i);
		}
		//Kung
		else if(i == mailbox64.getValue(60)){
			squares[i] = new Square(this, true, mbNr, new King(ChessColor.WHITE));
//			System.out.println("knug " + squares[mailbox64.getValue(mbNr)].getPiece().getPieceType().getPieceValue() );
			setSquareToPiece(i);
		} 
	 		
		//Bönder
		else  if(i >= mailbox64.getValue(48) && i <= mailbox64.getValue(55)){
			squares[i] = new Square(this, true, mbNr, new Pawn(ChessColor.WHITE));	
			setSquareToPiece(i);
		}
	}
	
	private void initNeutralPieces(int i, int mbNr) {
		squares[i] = new Square(this, true, mbNr, emtpyPiece);	
		setSquareToPiece(i);
	}
	
	private void setSquareToPiece(int i){
		Piece piece = squares[i].getPiece();
		piece.setSquare(squares[i]);
		
		if(piece.getPieceType() != PieceType.EMPTY){
			squares[i].setOccupied(true);
		}
	}
	
	public EmptyPiece getEmptyPiece(){
		return emtpyPiece;
	}
	
	//-------Print Board, all pieces--------
		public void printboard(){	
			
			for (int i = 0; i < squares.length; i++) {
				
				//---Formaterar brädet---
				if(i % 10 == 0){
					System.out.println();
				}
				
				//-----Skriver ut de värden de illegala rutorna har.-------
				if (i < 20 || i > 100 || (i % 10 == 0) || (i % 10 == 9)) {
					
					System.out.printf("%2d,", squares[i].getValueNbr()); //dåligt namn
					
				}else {	//----Skriver ut pjäsernas värde som står på de lagliga rutorna--------
					
					System.out.printf("%2d,", squares[i].getPiece().getPieceType().getPieceValue());//skrivet ut pjäsernas värde
//					System.out.printf("%2d,", squares[i].getValueNbr());// skriver ut rutornas mailboxNbr
				}			

			}// for-loop end		
		} //method end
		
		//-------Print Board, all pieces--------
		public void printSquareValues(){	
			
			for (int i = 0; i < squares.length; i++) {
				
				//---Formaterar brädet---
				if(i % 10 == 0){
					System.out.println();
				}
				
				//-----Skriver ut de värden de illegala rutorna har.-------
				if (i < 20 || i > 100 || (i % 10 == 0) || (i % 10 == 9)) {
					
					System.out.printf("%2d,", squares[i].getValueNbr()); //dåligt namn
					
				}else {	//----Skriver ut pjäsernas värde som står på de lagliga rutorna--------
					
					System.out.printf("%2d,", squares[i].getValueNbr());// skriver ut rutornas mailboxNbr
				}			

			}// for-loop end		
		} //method end
		
		public void printSquaresOccupied(){
			
			for (int i = 0; i < squares.length; i++) {
				
				//---Formaterar brädet---
				if(i % 10 == 0){
					System.out.println();
				}
				
				//-----Skriver ut de värden de illegala rutorna har.-------
				if (i < 20 || i > 100 || (i % 10 == 0) || (i % 10 == 9)) {
					
					System.out.printf("%2d,", squares[i].getValueNbr()); //dåligt namn
					
				}else {	//----Skriver ut pjäsernas värde som står på de lagliga rutorna--------
					
					System.out.printf("%5s,", squares[i].isOccupied());// skriver ut rutornas mailboxNbr
				}			

			}// for-loop end	
			
		}
		
	public void printPiecesSquare(){
			
			for (int i = 0; i < squares.length; i++) {
				
				//---Formaterar brädet---
				if(i % 10 == 0){
					System.out.println();
				}
				
				//-----Skriver ut de värden de illegala rutorna har.-------
				if (i < 20 || i > 100 || (i % 10 == 0) || (i % 10 == 9)) {
					
					System.out.printf(""); 
					
				}else {	//----Skriver ut vilka pjäser som står på vilka rutor.--------
					
					System.out.println("Ruta: " + squares[i].getValueNbr() + " står pjäsen: " + squares[i].getPiece().getColor() +
							" " + squares[i].getPiece().getPieceType());
					
					System.out.println(squares[i].getPiece().getColor() + " " + squares[i].getPiece().getPieceType() +
							" står på rutan: " + squares[i].getPiece().getSquareAt().getValueNbr());
				
					System.out.println();
				}			

			}// for-loop end				
		}

}
