package board;

import java.util.ArrayList;
import java.util.Iterator;

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
		Square[] sq = board.getBoardSquares();
		int i = 0;
		while(i<sq.length){
			if(sq[i].getPiece().getColor() == color){
				System.out.println(sq[i].getPiece().getPieceType());
				ArrayList<Square> possibleMoves = sq[i].getPiece().getMoves().getPossibleSquares();
				int startIndex = sq[i].getValueNbr();
				Iterator<Square> iter = possibleMoves.iterator();
				while(iter.hasNext()){
					int nextValue =iter.next().getValueNbr();
					board.forceMovePiece(startIndex, nextValue);
					board.updateAttackBoards();
					board.printBoard();
					if(board.blackKingInCheck() == false){
						board.forceMovePiece(nextValue, startIndex);
						iter.remove();
						return false;
						}
					else{
						board.forceMovePiece(nextValue, startIndex);
						System.out.println("Fortfrande schack");
					}
					
				}
			}
			i++;
		}
		
	
		
		System.out.println("SIMULERING KLAR");
		return true;
		
	}

}
