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
		
		if(piece.getColor() == ChessColor.WHITE) {
		
			switch (piece.getPieceType()) {
			
			case EMPTY:
				setIcon(null);
				break;
			case PAWN:
				setIcon("C:/Users/Daniel/Desktop/Pjäser/wp.GIF");
				break;
			case ROOK:
				setIcon("C:/Users/Daniel/Desktop/Pjäser/wr.GIF");
				break;
			case KNIGHT:
				setIcon("C:/Users/Daniel/Desktop/Pjäser/wn.GIF");
				break;
			case BISHOP:
				setIcon("C:/Users/Daniel/Desktop/Pjäser/wb.GIF");
				break;
			case QUEEN:
				setIcon("C:/Users/Daniel/Desktop/Pjäser/wq.GIF");
				break;
			case KING:
				setIcon("C:/Users/Daniel/Desktop/Pjäser/wk.GIF");
				break;
			}
			

		} else {

			switch (piece.getPieceType()) {
			
			case EMPTY:
				setIcon(null);
				break;
			case PAWN:
				setIcon("C:/Users/Daniel/Desktop/Pjäser/bp.GIF");
				break;
			case ROOK:
				setIcon("C:/Users/Daniel/Desktop/Pjäser/br.GIF");
				break;
			case KNIGHT:
				setIcon("C:/Users/Daniel/Desktop/Pjäser/bn.GIF");
				break;
			case BISHOP:
				setIcon("C:/Users/Daniel/Desktop/Pjäser/bb.GIF");
				break;
			case QUEEN:
				setIcon("C:/Users/Daniel/Desktop/Pjäser/bq.GIF");
				break;
			case KING:
				setIcon("C:/Users/Daniel/Desktop/Pjäser/bk.GIF");
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
