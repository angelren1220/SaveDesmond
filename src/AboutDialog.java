import java.awt.Color;

import javax.swing.JDialog;
import javax.swing.JEditorPane;

public class AboutDialog extends JDialog{
	private JEditorPane contenTextArea;
	public AboutDialog() {
		setTitle("About the game");
		setModal(false);
		setSize(700,600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		//setLayout(null);
		contenTextArea=new JEditorPane();
		//contenTextArea.setBounds(10, 10, 50, 50);
		contenTextArea.setContentType("text/html");
		String text="<html>";
		text=text+"<p>Desmond is lost in a grid. you need help him escape,"+
		" while avoiding the roaming zombies that after him.</p>";
		text=text+"<p>Before a new game start, you need to specify the level(mode) of challenge to this game, and press the \"start game\" button to start the game.</p>";
		
		text=text+"<p>Desmond will be in a random location. You need to click on the grid to find him. Be careful not to touch the zombies!! "+
				"Once a zombie gets to him before you, the game is over. For convenience, you can press the \"cheat\" button to display Desmond's current location in the grid.</p>";
		
		text=text+"<p>After you find him, you need to guide him to the exit by moving him upward, down, left or right by pressing those four buttons. Be careful not to enter the 5 by 5 grid with the zombie at the center, otherwise the zombie will follow you.</p>";
		text=text+"<p>Desmond can not cross the path that he has already walked, so do not let him go down a dead end.</p>";
		text=text+"</html>";
		contenTextArea.setText(text);
		contenTextArea.setEditable(false);
		contenTextArea.setOpaque(true);
		contenTextArea.setBackground(Color.WHITE);

		getContentPane().add(contenTextArea);
	}
}
