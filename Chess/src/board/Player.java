package board;
			
public class 	Player {
	
	private String name;
	private ChessColor color;
	
	private boolean moved = false;
	private boolean turn = true;
	private boolean inCheck;
	private boolean inMate = false;
	
	private Board  board;
	
	public Player(String name, ChessColor color, Board board){
		this.name = name;				
		this.color = color;
		this.board = board;
		inCheck = board.kingInCheck(color);
	}
	
	public void move(int startSquare, int endSquare){
		
		board.movePiece(startSquare, endSquare);
	}
	
	public boolean hasMoved(){
		return moved;
	}
	
	public void setMoved(boolean hasMoved){
		moved = hasMoved;
	}

}
