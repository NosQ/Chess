package board;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class GraphicBoard extends JComponent {
	private Ruta[][] squares = new Ruta[8][8];
	private JPanel main = new JPanel(new BorderLayout(3,3));
	private JPanel board;
	private Controller controller;
	private JFrame frame = new JFrame("Testar");
	
	public GraphicBoard(Controller controller) {
		this.controller = controller;
		initializeGui();
	}
	
	public void initializeGui() {
		
		main.setPreferredSize(new Dimension(800,600));
		
		board = new JPanel(new GridLayout(0,9));
		board.setBorder(new LineBorder(Color.BLACK));		
		main.add(board);
		int z = 0;
		for(int i = 0; i < squares.length; i++) {
			for(int j = 0; j < squares[i].length; j++) {
				
				Ruta s = new Ruta(i,j, z);
				
				if ((j % 2 == 1 && i % 2 == 1) || (j % 2 == 0 && i % 2 == 0)) {
					
					s.setBackground(Color.WHITE);
					s.setPiece(controller.getBoard().getSquare(z).getPiece().getPieceType().getPieceValue());
					
				} else {
					
					s.setBackground(Color.RED);
					s.setPiece(controller.getBoard().getSquare(z).getPiece().getPieceType().getPieceValue());
				}
				
				s.addMouseListener(new MouseAdapter() {
					
				public void mousePressed(MouseEvent e) {
					controller.movePiece(s.getValue());
				}
			});
				squares[i][j] = s;
				z++;
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
		for(int i = 0; i<squares.length; i++){
			for(int z = 0; z<squares.length; z++){
				int y = squares[i][z].getValue();
			if(y == index){
				for(int h = 0; h<squares.length; h++){
					for(int j = 0; j<squares.length; j++){
					int x = squares[h][j].getValue();
					if(x==to){
						squares[h][j].setPiece(controller.getBoard().getSquare(x).getPiece().getPieceType().getPieceValue());
						
						squares[i][z].setPiece(controller.getBoard().getSquare(y).getPiece().getPieceType().getPieceValue());
						updateDisplay();
					
					}
			}
				}
			}
		}
		}
		updateDisplay();
	}
	
	public JComponent getGui() {
		return main;
	}
	public void updateDisplay(){
		frame.add(main);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}
