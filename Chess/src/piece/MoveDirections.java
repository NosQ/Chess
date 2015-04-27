package piece;

public enum MoveDirections {
	
	//Ray Directions
	NORTHWEST	(7),
	NORTH		(8),
	NORTHEAST	(9),
	WEST		(-1),	
	EAST		(1),
	SOUTHWEST	(-9),
	SOUTH		(-8),	
	SOUTHEAST	(-7),
	
	//Knight Directions
	noNoWe	(15),
	noNoEa	(17),
	noWeWe	(6),
	noEaEa	(10),
	
	soNoWe	(-10),
	soEaEa	(-6),
	soSoWe	(-17),
	soSoEa	(-15);
	
	private int moveDirection;
	
	MoveDirections(int moveDirection){
		this.moveDirection = moveDirection;
	}
	
	public int getMoveDirection(){
		return moveDirection;
	}
	

}
