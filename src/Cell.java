
import java.awt.Component;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class Cell extends JLabel{
	public static int DESMOND=1;
	public static int ZOMBY=2;
	public static int NOTHING=3;
	private int type;
	private int i;
	private int j;
	private boolean display;
	private boolean path;
	public Cell(int i,int j) {
		//super();
		this.i=i;
		this.j=j;
		this.type=NOTHING;
		path=false;
		setDisplay(false);
		addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				Component component=(Component)e.getSource();
				((MainFrame)SwingUtilities.getRoot(component)).cellClicked(i,j);
			}
		});
	}
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
		if(type==ZOMBY) {
			setDisplay(true);
		}
		else if(type==NOTHING) {
			setDisplay(false);
		}
		
		setDisplay(display);
	}
	public boolean isDisplay() {
		return display;
	}
	public void setDisplay(boolean display) {
		this.display = display;
		if(this.display) {
			if(type==DESMOND) {
				ImageIcon icon=new ImageIcon("src/desmond.jpg");
				icon.setImage(icon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
				setIcon(icon);
			}
			else if(type==ZOMBY) {
				ImageIcon icon=null;
				if(!path)
					icon=new ImageIcon("src/zomby.png");
				else {
					icon=new ImageIcon("src/zomby1.png");
				}
				icon.setImage(icon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
				setIcon(icon);
			}
			else if(type==NOTHING){
				
				if(path) {
					ImageIcon icon=new ImageIcon("src/yellow.png");
					icon.setImage(icon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
					setIcon(icon);
				}
				else {
					setIcon(null);
				}
			}
		}
		else {
			if(path) {
				ImageIcon icon=new ImageIcon("src/yellow.png");
				icon.setImage(icon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
				setIcon(icon);
			}
			else {
				setIcon(null);
			}
		}
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public int getJ() {
		return j;
	}

	public void setJ(int j) {
		this.j = j;
	}

	public boolean isPath() {
		return path;
	}

	public void setPath(boolean path) {
		this.path = path;
		setDisplay(display);
	}
	
}
