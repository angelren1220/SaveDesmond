import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MainFrame.frameWidth=1200;
		MainFrame.frameHeight=800;
		MainFrame.gridSize=20;
		MainFrame frame=new MainFrame();
		frame.setTitle("Save Desmond!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
