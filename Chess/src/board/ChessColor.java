package board;

public enum ChessColor {
	
	BLACK (0),
	WHITE (1),
	NEUTRAL(2);
	
	private int color;
	
	ChessColor(int color){
		this.color = color;
	}

	public int getColor(){
		return color;
	}
}
