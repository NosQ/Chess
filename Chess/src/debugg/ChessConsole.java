package debugg;

import java.util.Scanner;

import board.Board;
import board.ChessColor;
import board.Player;

public class ChessConsole {
	
	//-----Main-method for Console-----
		public static void main(String[] args) {
			
			Board board = new Board();
            Player playerWhite = board.getPlayer(ChessColor.WHITE);
            Player playerBlack = board.getPlayer(ChessColor.BLACK);

			board.printEverything();
			
			Scanner scanner = new Scanner(System.in);
			int startSquare = 0, MvToSquare = 0;
			
			while(playerWhite.getTurn() || playerBlack.getTurn()){

                System.out.println("\nSkriv in vad du vill flytta: enligt: " + "x,y");

                if (playerWhite.getTurn()){
                    System.out.println("PlayerWhite det är din tur");

                    if(scanner.hasNext("exit")){
                        System.out.println("closed");
                        break;
                    }

                    String inputMove = scanner.nextLine();
                    String[] positions = inputMove.split(",");

                    startSquare = Integer.parseInt(positions[0]);
                    MvToSquare = Integer.parseInt(positions[1]);

                    playerWhite.move(startSquare,MvToSquare);

                    board.printEverything();

                }else{
                    System.out.println("PlayerBlack det är din tur");

                    if(scanner.hasNext("exit")){
                        System.out.println("closed");
                        break;
                    }

                    String inputMove = scanner.nextLine();
                    String[] positions = inputMove.split(",");

                    startSquare = Integer.parseInt(positions[0]);
                    MvToSquare = Integer.parseInt(positions[1]);

                    playerBlack.move(startSquare, MvToSquare);

                    board.printEverything();
                }
			}		
		}
}

