package board;
			
public class Player {
	
	private String name;
	private ChessColor color;
	private boolean turn = true;
	
	private Board  board;
	
	public Player(String name, ChessColor color, Board board){
		this.name = name;				
		this.color = color;
		this.board = board;
	}
	
	public void move(int startSquare, int endSquare){

        board.movePiece(color, startSquare, endSquare);
    }
	
	public boolean getTurn(){ return turn;}
	public void setTurn(boolean turn){
		this.turn = turn;
	}

    public ChessColor getColor(){
        return color;
    }

}
