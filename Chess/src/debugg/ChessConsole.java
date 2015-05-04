package debugg;

import java.util.Scanner;

import board.Board;

public class ChessConsole {
	
	//-----Main-method for Console-----
		public static void main(String[] args) {
			
			Board board = new Board();
			board.printEverything();
			board.printPiecesSquare();
			
			Scanner scanner = new Scanner(System.in);
			int stsq = 0, tosq = 0;
			
			while(true){
				
				System.out.println("\nSkriv in vad du vill flytta: t.ex. " + "0,16");
				
				if(scanner.hasNext("exit")){
					System.out.println("closed");
					break;
				}
				
				String inputMove = scanner.nextLine();
				String[] positions = inputMove.split(",");
				
				stsq = Integer.parseInt(positions[0]);
				tosq = Integer.parseInt(positions[1]);
				
				board.movePiece(stsq, tosq);
				board.printEverything();	
				board.printPiecesSquare();
				
			}		
		}
}

