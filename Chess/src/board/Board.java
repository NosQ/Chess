package board;

import piece.Piece;
import piece.PieceType;
import square.Square;

public class Board {
	
	private Player playerWhite, playerBlack;
	private MailBox mailbox;
	private AttackBoard whiteAttckBoard, blackAttckBoard;
	private DefenceBoard whiteDefenceBoard, blackDefenceBoard;
	private boolean isBMate;
	private boolean isWMate;
	
	//--------Konstruktor----------
	public Board(){		
		mailbox = new MailBox();			
		whiteAttckBoard = new AttackBoard(this, ChessColor.WHITE);
		blackAttckBoard = new AttackBoard(this, ChessColor.BLACK);
		whiteDefenceBoard = new DefenceBoard(ChessColor.WHITE, this);
		blackDefenceBoard = new DefenceBoard(ChessColor.BLACK, this);		
		updateAttackBoards();	
		
		playerWhite = new Player("White", ChessColor.WHITE, this);
		playerBlack = new Player("Black", ChessColor.BLACK, this);
	//	printAttackBoards();
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
	
	public boolean InCheckMate(ChessColor color){
		//---------checkforBlackcheck----------
		if (kingInCheck(color)) {
			
			getDefenceBoard(color).generateEscapeSquares();
			getDefenceBoard(color).printEscapeSquares();
			
			if(getDefenceBoard(color).isEmpty() && color == ChessColor.BLACK){						
				System.out.println("GAME OVER BITCH, " + color.name() + " lost!");				
				setBMate(true);
				
				return true;
				
				
			}
			
			if(getDefenceBoard(color).isEmpty() && color == ChessColor.WHITE) {
				System.out.println("GAME OVER BITCH, " + color.name() + " lost!");				
				setWMate(true);
				
				return true;
			}
			else{
				System.out.println("Kung " + color.name() + " är inte i schack matt ");				
			}					
		}		
		return false;		
	}
	
	//-----------Move and Check-logic------------	
	public boolean movePiece(ChessColor color, int startSquare, int endSquare){
			
		Piece piece = getSquare(startSquare).getPiece();
        Piece pieceBackup = getSquare(endSquare).getPiece();

        if(getPlayer(color).getTurn()) {

			if(piece.movement(endSquare) && piece.getColor() == getPlayer(color).getColor()){

				getSquare(endSquare).setPiece(getSquare(startSquare).getPiece());
				getSquare(startSquare).setPiece(mailbox.getEmptyPiece());
				piece.setSquare(getSquare(endSquare));

				getSquare(startSquare).setOccupied(false);
				getSquare(endSquare).setOccupied(true);

				updateAttackBoards();
//				printAttackBoards();

                if(kingInCheck( color )){
                    reverseMove(getSquare(startSquare), getSquare(endSquare), pieceBackup);
                    System.out.println("Du får inte sätta egen kung i schack, nytt försök!");
                    return false;
                }

                if (InCheckMate(getOppositeColor(color))){
                    System.out.println(color.name() + " Won!");
                    getPlayer(color).setTurn(false);
                    getPlayer( getOppositeColor(color) ).setTurn(false);
                    
                }

				System.out.println("Giltligt drag");
                getPlayer(color).setTurn(false);
                getPlayer( getOppositeColor(color) ).setTurn(true);

				return true;
			}
			else{
				System.out.println("Ogiltligt drag, Nytt försök!");
				return false;
			}
        }
        else{
            System.out.println("Inte din tur");
            return false;
        }
	}
	
	public void updateAttackBoards(){
		whiteAttckBoard.updateAttackSquares();
		blackAttckBoard.updateAttackSquares();		
	}
	

	/**
	 * Metoden växlar pjäser mellan squareAt och squareMvTo. Om det står en riktig pjäs på squareMvTo så blir den till en tomPjäs innan bytet.
	 * Anledning till att den görs om till tom pjäs är för annars hade det inte simulerat ett riktigt drag. Det är INTE tillåtet att 
	 * växla pjäser mellan olika rutor.    
	 * @param squareAt
	 * @param squareMvTo
	 */
	public void forceMovePiece(Square squareAt, Square squareMvTo){
		
		Piece startSqPiece = squareAt.getPiece();//WhiteRook
		Piece mvToSqPiece = squareMvTo.getPiece();//BlackQueen
		
		//Om rutan i attackradie inte är en tom ruta, så gör den tom.
		if (mvToSqPiece.getPieceType() != PieceType.EMPTY){		
			mvToSqPiece = mailbox.getEmptyPiece();//BlackQueen -> EmpteyPiece
//			sqMvTo.setPiece(sqMvTo.getMailbox().getEmptyPiece());						
		}
		
		//Sätter moveToSquare pjäs till squareAts pjäs och.
		squareMvTo.setPiece(startSqPiece);
		startSqPiece.setSquare(squareMvTo);
		
		squareAt.setPiece(mvToSqPiece);		
		mvToSqPiece.setSquare(squareAt);
		
		squareAt.setOccupied(false);
		squareMvTo.setOccupied(true);
	}
	/**
	 * Metoden "Ångrar ett drag", backuppjäsen är den pjäs som blev tagen draget innan. 
	 * @param squareBeforeMove
	 * @param squareMovedTo
	 * @param pieceBackup
	 */
	public void reverseMove(Square squareBeforeMove, Square squareMovedTo,  Piece pieceBackup/*BlackQueen*/){
		
		Piece sqBeforeMovePiece = squareBeforeMove.getPiece();//EmpteyPiece
		Piece sqMovedToPiece = squareMovedTo.getPiece();//WhiteRook
		
		squareBeforeMove.setPiece(sqMovedToPiece);//Set Piece to WhiteRook
		sqMovedToPiece.setSquare(squareBeforeMove);//
		squareBeforeMove.setOccupied(true);
		
		squareMovedTo.setPiece(pieceBackup);//Set Piece to BlackQueen
		pieceBackup.setSquare(squareMovedTo);//
			
		if(squareMovedTo.getPiece().getPieceType() != PieceType.EMPTY){
			squareMovedTo.setOccupied(true);
		}else{
			squareMovedTo.setOccupied(false);
		}
				
	}	
	
	
	
	//----Get/Set-metoder för shackmatt----
	
	public void setBMate(boolean isMate) {
		this.isBMate = isMate;
	}
	
	public void setWMate(boolean isMate) {
		this.isWMate = isMate;
	}
	
	public boolean isBMate() {
		return isBMate;
	}
	
	public boolean isWMate() {
		return isWMate;
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
	
	private DefenceBoard getDefenceBoard(ChessColor color){

		if(color == ChessColor.WHITE) return whiteDefenceBoard;
		if(color == ChessColor.BLACK) return blackDefenceBoard;

		return null;
	}

    public Player getPlayer(ChessColor color){
        if (color == ChessColor.WHITE) return playerWhite;
        if (color == ChessColor.BLACK) return playerBlack;
        return null;
    }

    public ChessColor getOppositeColor(ChessColor color){
        if (color == ChessColor.WHITE) return ChessColor.BLACK;
        if (color == ChessColor.BLACK) return ChessColor.WHITE;
        return null;
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
		//printSquaresOccupied();
		printAttackBoards();
		printSquareValues();
	}
}	