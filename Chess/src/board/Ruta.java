package board;

import javax.swing.*;

public class Ruta extends JPanel {
	private int x;
	private int y;
	private JLabel sLbl;
	private int value;
	private Icon icon;
	private int piece;
//	private Piece piece;
	
	Ruta(int x, int y, int z) {
		sLbl = new JLabel();
		add(sLbl);
		this.x = x;
		this.y = y;
		value = z;
	}
	
	public int getValue() {
		return value;
	}
	public void setIcon(Icon icon){
		this.icon = icon;
	}
	public Icon getIcon(){
		return icon;
	}
	public void setPiece(int piece){
		this.piece = piece;
		sLbl.setText(""+ piece);
	}
	public int getPiece(){
		return piece;
	}
}
