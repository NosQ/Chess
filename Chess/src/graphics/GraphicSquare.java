package graphics;

import java.awt.Color;

import javax.swing.*;

import board.ChessColor;
import piece.Piece;
import piece.PieceType;

public class GraphicSquare extends JPanel {
	private int x;
	private int y;
	private JLabel sLbl;
	private int value;
	private ImageIcon icon;
	private Piece piece;
	
	GraphicSquare(int value) {
		sLbl = new JLabel();
		add(sLbl);
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setIcon(String file){
		icon = new ImageIcon(file);
		sLbl.setIcon(icon);
	}
	
	public void setPiece(Piece piece){
		this.piece = piece;
		String path = "/home/zvonimir/git/Chess/Chess/src/PiecePictures/";
		if(piece.getColor() == ChessColor.WHITE) {
		
			switch (piece.getPieceType()) {
			
			case EMPTY:
				setIcon(null);
				break;
			case PAWN:
				setIcon(path+"wp.gif");
				break;
			case ROOK:
				setIcon(path+"wr.gif");
				break;
			case KNIGHT:
				setIcon(path+"wn.gif");
				break;
			case BISHOP:
				setIcon(path+"wb.gif");
				break;
			case QUEEN:
				setIcon(path+"wq.gif");
				break;
			case KING:
				setIcon(path+"wk.gif");
				break;
			}
			

		} else {

			switch (piece.getPieceType()) {
			
			case EMPTY:
				setIcon(null);
				break;
			case PAWN:
				setIcon(path+"bp.gif");
				break;
			case ROOK:
				setIcon(path+"br.gif");
				break;
			case KNIGHT:
				setIcon(path+"bn.gif");
				break;
			case BISHOP:
				setIcon(path+"bb.gif");
				break;
			case QUEEN:
				setIcon(path+"bq.gif");
				break;
			case KING:
				setIcon(path+"bk.gif");
				break;
			}

		}
	}
	
	public Piece getPiece(){
		return piece;
	}
	
	public ImageIcon getIcon() {
		if (icon != null) {
			return icon;
		}
		return null;
	}
}
