package board;

import java.util.ArrayList;
import java.util.Iterator;

import square.Square;

public class DefenceBoard {

	private ChessColor color;
	private Board board;

	public DefenceBoard(ChessColor color, Board board) {
		this.color = color;
		this.board = board;
	}

	public boolean simulateCheck() {
		System.out.println("testar simulering f�r " + color);
		Square[] squares = board.getBoardSquares();
		int i = 0;
		//för alla rutor i brädet
		while (i < squares.length) {
			
			if (squares[i].getPiece().getColor() == color) {
				// System.out.println(squares[i].getPiece().getPieceType());
				ArrayList<Square> possibleMoves = squares[i].getPiece().getMoves().getPossibleSquares();
				
				int startIndex = squares[i].getValueNbr();
				squares[i].getPiece().printPossibleMoves();
				
				int z = 0;
				//för alla moves 
				while (z < possibleMoves.size()) {
					
					int nextValue = possibleMoves.get(z).getValueNbr();
					board.forceMovePiece(startIndex, nextValue);
					board.updateAttackBoards();
					board.printBoard();

					if (board.blackKingInCheck() == false) {
						board.forceMovePiece(nextValue, startIndex);
						return false;
					} else {
						board.forceMovePiece(nextValue, startIndex);
					}
					z++;
				}
			}
			i++;
		}
		System.out.println("SIMULERING KLAR");
		return true;
//		System.out.println("testar simulering f�r " + color);
//		Square[] squares = board.getBoardSquares();
//
//		ArrayList<Square> possibleMoves = new ArrayList<>();
//		
//		for (Square square : board.getBoardSquares()) {
//
//			if (square.getPiece().getColor() == color) {
//				// System.out.println(squares[i].getPiece().getPieceType());
//			    possibleMoves = square.getPiece().getMoves().getPossibleSquares();
//			    
//				int startIndex = square.getValueNbr();
//				square.getPiece().printPossibleMoves();
//
//				for (Square possibleMove : temp) {
//					
//					int nextValue = possibleMove.getValueNbr();
//
//					board.forceMovePiece(startIndex, nextValue);
//					board.updateAttackBoards();
//					board.printBoard();
//
//					if (board.blackKingInCheck() == false) {
//						board.forceMovePiece(nextValue, startIndex);
//						return false;
//					} else {
//						board.forceMovePiece(nextValue, startIndex);
//					}
//				}//second for-loop
//			}//first if
//		}//first for-loop
//			System.out.println("SIMULERING KLAR");
//			return true;
//
//		}
	}
}