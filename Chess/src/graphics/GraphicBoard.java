package graphics;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.LineBorder;

import piece.Piece;
import piece.PieceType;
import piece.pieces.EmptyPiece;
import square.Square;

public class GraphicBoard extends JFrame {
	private GraphicSquare[][] squares = new GraphicSquare[8][8];
	private JPanel main = new JPanel(new BorderLayout(3,3));
	private JPanel board;
	private GraphicController controller;
	private JFrame frame = new JFrame("Testar");
	
	public GraphicBoard(GraphicController controller) {
		this.controller = controller;
		initializeGui();
	}
	
	private void initializeGui() {
		
		main.setPreferredSize(new Dimension(800,600));
		
		board = new JPanel(new GridLayout(0,9));
		board.setBorder(new LineBorder(Color.BLACK));		
		main.add(board);
		int value = 0;
		for(int i = 0; i < squares.length; i++) {
			for(int j = 0; j < squares[i].length; j++) {
				
				final GraphicSquare s = new GraphicSquare(value);
				
				if ((j % 2 == 1 && i % 2 == 1) || (j % 2 == 0 && i % 2 == 0)) {
					
					s.setBackground(Color.WHITE);
					s.setPiece(controller.getBoard().getSquare(value).getPiece());
					
				} else {
					
					s.setBackground(Color.RED);
					s.setPiece(controller.getBoard().getSquare(value).getPiece());
				}
				
				if (s.getValue() >= 0) {

					s.addMouseListener(new MouseAdapter() {

						public void mousePressed(MouseEvent e) {
							if (s.getPiece() != null) {
								controller.movePiece(s.getValue());
						//		s.setBackground(Color.GREEN);
							}
						}
					});
				}
			
				squares[i][j] = s;
				value++;
			}
		}
		
		for (int i = 0; i < 8; i++) {
			
            for (int j = 0; j < 8; j++) {
            	
                switch (j) {
                    case 0:
                    	
                        board.add(new JLabel("" + (i + 1), SwingConstants.CENTER));
                        
                    default:
                    	
                        board.add(squares[i][j]);
                        
                }
            }
        }				
	}
	
	public void changePiece(int index, int to){
		for (int i = 0; i < squares.length; i++) {
			for (int z = 0; z < squares.length; z++) {
				int y = squares[i][z].getValue();
				if (y == index) {
					for (int h = 0; h < squares.length; h++) {
						for (int j = 0; j < squares.length; j++) {
							int x = squares[h][j].getValue();
							if (x == to) {
								squares[h][j].setPiece(controller.getBoard().getSquare(x).getPiece());
								squares[i][z].setPiece(controller.getBoard().getSquare(y).getPiece());
							}
						}
					}
				}
			}
		}		
	}
	
	
	public JFrame getGui() {
		return this;
	}
	
	public void updateDisplay(){
		frame.add(main);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}

//från Daniel
