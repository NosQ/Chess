package board;

import java.util.ArrayList;

import square.Square;

public class DefenceBoard {
	
	private ChessColor color;
	private Board board;
	
	public DefenceBoard(ChessColor color, Board board){
		this.color = color;
		this.board = board;
	}
	
	public boolean simulateCheck(){
		System.out.println("testar simulering f�r "+color);
		for(Square sq :board.getBoardSquares()){
			if(sq.getPiece().getColor() == color){
				ArrayList<Square> possibleMoves = sq.getPiece().getMoves().getPossibleSquares();
				int startIndex = sq.getValueNbr();
				for(Square testSq : possibleMoves){
					board.forceMovePiece(startIndex, testSq.getValueNbr());
					board.updateAttackBoards();
					System.out.println(board.blackKingInCheck());
					if(board.blackKingInCheck() == false){
						System.out.println("inte schack");
						return false;
						}
					else{
						board.forceMovePiece(testSq.getValueNbr(), startIndex);
						System.out.println("Fortfrande schack");
					}
						
					
				}
			}
		}
		
	
		
		System.out.println("SIMULERING KLAR");
		return true;
		
	}

}
