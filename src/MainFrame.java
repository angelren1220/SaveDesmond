import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Random;


import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.border.BevelBorder;

public class MainFrame extends JFrame{
	public static int gridSize;
	public static int frameWidth;
	public static int frameHeight;
//	private static int cellSize=30;
	private JPanel panel;
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem menuItem1;
	//private JMenuItem menuItem2;
	private String userName;
	private Cell[][] cells;
	private JLabel[] labels;
	private JComboBox<String> numberOfZombiesBox;
	private JButton startButton;
	private JButton giveUpButton;
	private JButton cheatButton;
	private JButton quitButton;
	private JButton moveTopButton;
	private JButton moveLeftButton;
	private JButton moveRightButton;
	private JButton moveBottomButton;
	private JLabel numberOfGuessLabel;
	private int numberOfGuess;
	private Cell desmondCell;
	//private Cell desmondCellPrev;
	private JLabel info;
	private ArrayList<Cell> zombiesCells;
	int status;//0 The game is not in progress 1 The game is not in progress
	int stage; //0 finding 1 moving
	public MainFrame() {
		status=0;
		stage=0;
		numberOfGuess=0;
		
		setSize(frameWidth,frameHeight);
		setLocationRelativeTo(null);
		zombiesCells=new ArrayList<>();
		userName=JOptionPane.showInputDialog("please input your name:");
		if(userName==null||userName.equals("")) {
			JOptionPane.showMessageDialog(null, "name can not be empty");
			System.exit(0);
		}
		initComponents();
	}
	private void initComponents() {
		menuBar=new JMenuBar();
		menu=new JMenu("Help");
		menuItem1=new JMenuItem("About");
		menuItem1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new AboutDialog().setVisible(true);
			}
		});
		menu.add(menuItem1);
		menuBar.add(menu);
		setJMenuBar(menuBar);
		panel=new JPanel();
		panel.setLayout(null);
		
		ImageIcon icon=new ImageIcon("src/desmond.jpg");
		icon.setImage(icon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
//		JLabel label=new JLabel(icon);
//		label.setBounds(10, 10, 50, 50);
//		label.setBorder(new BevelBorder(0));
//		panel.add(label);
		cells=new Cell[gridSize][gridSize];
		for(int i=0;i<gridSize;i++) {
			for(int j=0;j<gridSize;j++) {
				cells[i][j]=new Cell(i,j);
				cells[i][j].setBorder(new BevelBorder(0));
				cells[i][j].setBounds(50+j*30, 50+i*30, 30, 30);
				panel.add(cells[i][j]);
			}
		}
		
		labels=new JLabel[gridSize];
		for(int i=0;i<gridSize;i++) {
			labels[i]=new JLabel(String.valueOf(i+1),JLabel.CENTER);
			labels[i].setBounds(50+i*30, 25, 30, 30);
			panel.add(labels[i]);
			labels[i]=new JLabel(String.valueOf(i+1),JLabel.CENTER);
			labels[i].setBounds(50+i*30, 50+gridSize*30, 30, 30);
			panel.add(labels[i]);
			labels[i]=new JLabel(String.valueOf((char)(i+'A')),JLabel.CENTER);
			labels[i].setBounds(20, 50+i*30, 30, 30);
			panel.add(labels[i]);
			labels[i]=new JLabel(String.valueOf((char)(i+'A')),JLabel.CENTER);
			labels[i].setBounds(50+gridSize*30, 50+i*30, 30, 30);
			panel.add(labels[i]);
		}
		Font font=new Font("Dialog",0,15);
		JLabel label=new JLabel("The cell where the exit is located: A1");
		label.setBounds(700, 30, 400, 20);
		label.setFont(font);
		panel.add(label);
		
		label=new JLabel("mode:");
		label.setBounds(700,70,50,20);
		label.setFont(font);
		panel.add(label);
		String[] modes= {"easy","normal","hard"};
		numberOfZombiesBox=new JComboBox<String>(modes);
		numberOfZombiesBox.setBounds(label.getX()+label.getWidth(), label.getY(), 250, 20);
		panel.add(numberOfZombiesBox);
		
		startButton=new JButton("Start game");
		startButton.setBounds(700, 110, 150, 30);
		startButton.setFont(font);
		panel.add(startButton);
		
		giveUpButton=new JButton("Give up");
		giveUpButton.setBounds(850, 110, 150, 30);
		giveUpButton.setFont(font);
		giveUpButton.setEnabled(false);
		panel.add(giveUpButton);
		
		cheatButton=new JButton("Cheat");
		cheatButton.setBounds(700, 150, 150, 30);
		cheatButton.setFont(font);
		cheatButton.setEnabled(false);
		panel.add(cheatButton);
		
		quitButton=new JButton("Quit");
		quitButton.setBounds(850, 150, 150, 30);
		quitButton.setFont(font);
		quitButton.setEnabled(false);
		panel.add(quitButton);
		
		moveTopButton=new JButton("Move Upward");
		moveTopButton.setBounds(700, 190, 300, 30);
		moveTopButton.setFont(font);
		moveTopButton.setEnabled(false);
		panel.add(moveTopButton);
		
		moveLeftButton=new JButton("Move Left");
		moveLeftButton.setBounds(700, 230, 150, 30);
		moveLeftButton.setFont(font);
		moveLeftButton.setEnabled(false);
		panel.add(moveLeftButton);
		
		moveRightButton=new JButton("Move Right");
		moveRightButton.setBounds(850, 230, 150, 30);
		moveRightButton.setFont(font);
		moveRightButton.setEnabled(false);
		panel.add(moveRightButton);
		
		moveBottomButton=new JButton("Move Down");
		moveBottomButton.setBounds(700, 270, 300, 30);
		moveBottomButton.setFont(font);
		moveBottomButton.setEnabled(false);
		panel.add(moveBottomButton);
		
		numberOfGuessLabel=new JLabel(String.format("Number of guesses: %d",numberOfGuess));
		numberOfGuessLabel.setBounds(700, 310, 400, 30);
		numberOfGuessLabel.setFont(font);
		panel.add(numberOfGuessLabel);
		
		label=new JLabel("Welcome, "+userName);
		label.setFont(font);
		label.setBounds(700, 0, 300, 30);
		panel.add(label);
		
		info=new JLabel();
		info.setFont(font);
		info.setBounds(700, 350, 300, 30);
		info.setForeground(Color.red);
		panel.add(info);
		
		startButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				initGame();
			}
		});
		giveUpButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				gameFailed();
			}
		});
		cheatButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int i=desmondCell.getI();
				int j=desmondCell.getJ();
				String position=String.format("%c%d", (char)(i+'A'),(j+1));
				int ret=JOptionPane.showConfirmDialog(MainFrame.this,"Desmond is located at "+position+", show it?" ,"title",JOptionPane.YES_NO_OPTION);
				if(ret!=JOptionPane.OK_OPTION) {
					return;
				}
				desmondCell.setDisplay(true);
				for(i=0;i<gridSize;i++) {
					for(j=0;j<gridSize;j++) {
//						if(cells[i][j].getType()==Cell.DESMOND) {
//							cells[i][j].setDisplay(true);
//						}
						cells[i][j].setBorder(new BevelBorder(0));
					}
				}
				stage=1;
				info.setText("");
				moveLeftButton.setEnabled(true);
				moveTopButton.setEnabled(true);
				moveRightButton.setEnabled(true);
				moveBottomButton.setEnabled(true);
			}
		});
		quitButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
		moveTopButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int i=desmondCell.getI();
				int j=desmondCell.getJ();
				desmondCell.setType(Cell.NOTHING);
				desmondCell.setPath(true);
				i-=1;
				if(cells[i][j].getType()==Cell.ZOMBY) {
					gameFailed();
					return;
				}
				desmondCell=cells[i][j];
				desmondCell.setType(Cell.DESMOND);
				desmondCell.setDisplay(true);
				checkDesmondPosition();
				moveZombies();
			}
		});
		moveLeftButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int i=desmondCell.getI();
				int j=desmondCell.getJ();
				desmondCell.setType(Cell.NOTHING);
				desmondCell.setPath(true);
				j-=1;
				if(cells[i][j].getType()==Cell.ZOMBY) {
					gameFailed();
					return;
				}
				desmondCell=cells[i][j];
				desmondCell.setType(Cell.DESMOND);
				desmondCell.setDisplay(true);
				checkDesmondPosition();
				moveZombies();
			}
		});
		moveRightButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int i=desmondCell.getI();
				int j=desmondCell.getJ();
				desmondCell.setType(Cell.NOTHING);
				desmondCell.setPath(true);
				j+=1;
				if(cells[i][j].getType()==Cell.ZOMBY) {
					gameFailed();
					return;
				}
				desmondCell=cells[i][j];
				desmondCell.setType(Cell.DESMOND);
				desmondCell.setDisplay(true);
				checkDesmondPosition();
				moveZombies();
			}
		});
		moveBottomButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int i=desmondCell.getI();
				int j=desmondCell.getJ();
				desmondCell.setType(Cell.NOTHING);
				desmondCell.setPath(true);
				i+=1;
				if(cells[i][j].getType()==Cell.ZOMBY) {
					gameFailed();
					return;
				}
				desmondCell=cells[i][j];
				desmondCell.setType(Cell.DESMOND);
				desmondCell.setDisplay(true);
				checkDesmondPosition();
				moveZombies();
			}
		});
		setContentPane(panel);
		
	}
	public void cellClicked(int i,int j) {
		if(stage==0&&status==1) {
			numberOfGuess+=1;
			cells[i][j].setBorder(new BevelBorder(1));
			updateNumberOfGuessLabel();
			if(cells[i][j].getType()==Cell.ZOMBY) {
				JOptionPane.showMessageDialog(null, "You touch a zombie!");
				gameFailed();
				return;
			}
			if(cells[i][j].getType()==Cell.DESMOND) {
				stage=1;
				info.setText("");
				cheatButton.setEnabled(true);
				cells[i][j].setDisplay(true);
				for(int m=0;m<gridSize;m++) {
					for(int n=0;n<gridSize;n++) {
						cells[m][n].setBorder(new BevelBorder(0));
					}
				}
				checkDesmondPosition();
			}
			else {
				if(Math.abs(i-desmondCell.getI())<=2&&Math.abs(j-desmondCell.getJ())<=2) {
					if(Math.abs(i-desmondCell.getI())<=1&&Math.abs(j-desmondCell.getJ())<=1) {
						info.setText("You are very close to Desmond");
					}
					else {
						info.setText("You are close to Desmond");
					}
				}
				else {
					info.setText("");
				}
			}
			moveZombies();
		}
	}
	private void initCells() {
		for(int i=0;i<gridSize;i++) {
			for(int j=0;j<gridSize;j++) {
				cells[i][j].setType(Cell.NOTHING);
				cells[i][j].setDisplay(false);
				cells[i][j].setBorder(new BevelBorder(0));
				cells[i][j].setPath(false);
			}
		}
	}
	private void updateNumberOfGuessLabel() {
		numberOfGuessLabel.setText(String.format("number of guesses: %d",numberOfGuess));
	}
	private void initGame() {
		status=1;
		stage=0;
		numberOfGuess=0;
		updateNumberOfGuessLabel();
		giveUpButton.setEnabled(true);
		cheatButton.setEnabled(true);
		quitButton.setEnabled(true);
		moveLeftButton.setEnabled(false);
		moveTopButton.setEnabled(false);
		moveRightButton.setEnabled(false);
		moveBottomButton.setEnabled(false);
		initCells();
		
		int random=new Random().nextInt(gridSize*gridSize-1);
		random+=1;
		//System.out.println(String.format("Desmond is at %d", random));
		cells[random/gridSize][random%gridSize].setType(Cell.DESMOND);
		desmondCell=cells[random/gridSize][random%gridSize];
		zombiesCells.clear();
		int numberOfZombies=1;
		String mode=(String)numberOfZombiesBox.getSelectedItem();
		if(mode.equals("easy")) {
			numberOfZombies=1;
		}
		else if(mode.equals("normal")) {
			numberOfZombies=4;
		}
		else if(mode.equals("hard")) {
			numberOfZombies=8;
		}
		for(int i=0;i<numberOfZombies;i++) {
			while(true) {
				random=new Random().nextInt(gridSize*gridSize);
				if(cells[random/gridSize][random%gridSize].getType()!=Cell.NOTHING) {
					continue;
				}
				cells[random/gridSize][random%gridSize].setType(Cell.ZOMBY);
				cells[random/gridSize][random%gridSize].setDisplay(true);
				zombiesCells.add(cells[random/gridSize][random%gridSize]);
				break;
			}
		}
	}
	private void gameFailed() {
		if(stage==0) {
			desmondCell.setDisplay(true);
		}
		JOptionPane.showMessageDialog(null, "Dear "+ userName+", game fails! One more Time?");
		status=0;
		stage=0;
		cheatButton.setEnabled(false);
		giveUpButton.setEnabled(false);
		moveLeftButton.setEnabled(false);
		moveTopButton.setEnabled(false);
		moveRightButton.setEnabled(false);
		moveBottomButton.setEnabled(false);
		cheatButton.setEnabled(false);
	}
	private void gameWin() {
		File winSound=new File("src/win.wav");
		try {
			Clip clip=AudioSystem.getClip();
			try {
				clip.open(AudioSystem.getAudioInputStream(winSound));
				clip.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedAudioFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JOptionPane.showMessageDialog(null, "Congratulations, "+userName+", you win!");
		status=0;
		stage=0;
		cheatButton.setEnabled(false);
		giveUpButton.setEnabled(false);
		moveLeftButton.setEnabled(false);
		moveTopButton.setEnabled(false);
		moveRightButton.setEnabled(false);
		moveBottomButton.setEnabled(false);
		cheatButton.setEnabled(false);
	}
	void moveZombies() {
		for(int n=0;n<zombiesCells.size();n++) {
			int i=zombiesCells.get(n).getI();
			int j=zombiesCells.get(n).getJ();
			int left=i-2<0?0:(i-2);
			int up=j-2<0?0:(j-2);
			int right=i+2>=gridSize?(gridSize-1):(i+2);
			int bottom=j+2>=gridSize?(gridSize-1):(j+2);
			int desmondi=desmondCell.getI();
			int desmondj=desmondCell.getJ();

			if(desmondi>=left&&desmondi<=right&&desmondj>=up&&desmondj<=bottom) {
				//System.out.println("i see you");
				if(Math.abs(i-desmondi)==1&&Math.abs(j-desmondj)==0||Math.abs(i-desmondi)==0&&Math.abs(j-desmondj)==1) {
					gameFailed();
					return;
				}
				else {
					
					if(desmondi>i) {
						if(desmondj>j) {//try going down or right
							ArrayList<Cell> canTo=new ArrayList<>();
							if(cells[i+1][j].getType()!=Cell.ZOMBY) {
								canTo.add(cells[i+1][j]);
							}
							if(cells[i][j+1].getType()!=Cell.ZOMBY) {
								canTo.add(cells[i][j+1]);
							}
							if(canTo.size()!=0) {
								cells[i][j].setType(Cell.NOTHING);
								int direction=new Random().nextInt(canTo.size());
								i=canTo.get(direction).getI();
								j=canTo.get(direction).getJ();
								cells[i][j].setType(Cell.ZOMBY);
								zombiesCells.set(n, cells[i][j]);
							}
						}
						else if(desmondj==j) {//try going down
							if(cells[i+1][j].getType()!=Cell.ZOMBY) {
								cells[i][j].setType(Cell.NOTHING);
								i+=1;
								cells[i][j].setType(Cell.ZOMBY);
								zombiesCells.set(n, cells[i][j]);
							}
						}
						else {//try going down or left
							ArrayList<Cell> canTo=new ArrayList<>();
							if(cells[i+1][j].getType()!=Cell.ZOMBY) {
								canTo.add(cells[i+1][j]);
							}
							if(cells[i][j-1].getType()!=Cell.ZOMBY) {
								canTo.add(cells[i][j-1]);
							}
							if(canTo.size()!=0) {
								cells[i][j].setType(Cell.NOTHING);
								int direction=new Random().nextInt(canTo.size());
								i=canTo.get(direction).getI();
								j=canTo.get(direction).getJ();
								cells[i][j].setType(Cell.ZOMBY);
								zombiesCells.set(n, cells[i][j]);
							}
						}
					}
					else if(desmondi==i){//On the same horizontal line
						if(desmondj>j) {//try going right
							if(cells[i][j+1].getType()!=Cell.ZOMBY) {
								cells[i][j].setType(Cell.NOTHING);
								j+=1;
								cells[i][j].setType(Cell.ZOMBY);
								zombiesCells.set(n, cells[i][j]);
							}
						}
						else {//try going left
							if(cells[i][j-1].getType()!=Cell.ZOMBY) {
								cells[i][j].setType(Cell.NOTHING);
								j-=1;
								cells[i][j].setType(Cell.ZOMBY);
								zombiesCells.set(n, cells[i][j]);
							}
						}
					}
					else {
						if(desmondj>j) {//try going up or right
							ArrayList<Cell> canTo=new ArrayList<>();
							if(cells[i-1][j].getType()!=Cell.ZOMBY) {
								canTo.add(cells[i-1][j]);
							}
							if(cells[i][j+1].getType()!=Cell.ZOMBY) {
								canTo.add(cells[i][j+1]);
							}
							if(canTo.size()!=0) {
								cells[i][j].setType(Cell.NOTHING);
								int direction=new Random().nextInt(canTo.size());
								i=canTo.get(direction).getI();
								j=canTo.get(direction).getJ();
								cells[i][j].setType(Cell.ZOMBY);
								zombiesCells.set(n, cells[i][j]);
							}
						}
						else if(desmondj==j) {//try going up
							if(cells[i-1][j].getType()!=Cell.ZOMBY) {
								cells[i][j].setType(Cell.NOTHING);
								i-=1;
								cells[i][j].setType(Cell.ZOMBY);
								zombiesCells.set(n, cells[i][j]);
							}
						}
						else {//try going up or left
							ArrayList<Cell> canTo=new ArrayList<>();
							if(cells[i-1][j].getType()!=Cell.ZOMBY) {
								canTo.add(cells[i-1][j]);
							}
							if(cells[i][j-1].getType()!=Cell.ZOMBY) {
								canTo.add(cells[i][j-1]);
							}
							if(canTo.size()!=0) {
								cells[i][j].setType(Cell.NOTHING);
								int direction=new Random().nextInt(canTo.size());
								i=canTo.get(direction).getI();
								j=canTo.get(direction).getJ();
								cells[i][j].setType(Cell.ZOMBY);
								zombiesCells.set(n, cells[i][j]);
							}
						}
					}
				}
			}
			else {
				//System.out.println("random walk");
				
				ArrayList<Cell> canTo=new ArrayList<>();
				try {
					if(cells[i-1][j].getType()!=Cell.ZOMBY)
						canTo.add(cells[i-1][j]);
				} catch (Exception e) {
				}
				try {
					if(cells[i+1][j].getType()!=Cell.ZOMBY)
					canTo.add(cells[i+1][j]);
				} catch (Exception e) {
				}
				try {
					if(cells[i][j-1].getType()!=Cell.ZOMBY)
					canTo.add(cells[i][j-1]);
				} catch (Exception e) {
				}
				try {
					if(cells[i][j+1].getType()!=Cell.ZOMBY)
					canTo.add(cells[i][j+1]);
				} catch (Exception e) {
				}
				if(canTo.size()==0) {
					continue;
				}
				cells[i][j].setType(Cell.NOTHING);
				int direction=new Random().nextInt(canTo.size());
				i=canTo.get(direction).getI();
				j=canTo.get(direction).getJ();
				cells[i][j].setType(Cell.ZOMBY);
				zombiesCells.set(n, cells[i][j]);
			}
		}
	}
	private void checkDesmondPosition() {
		if(desmondCell.getI()==0&&desmondCell.getJ()==0) {
			gameWin();
			return;
		}
		if(desmondCell.getI()==0||cells[desmondCell.getI()-1][desmondCell.getJ()].isPath()) {
			moveTopButton.setEnabled(false);
		}
		else {
			moveTopButton.setEnabled(true);
		}
		if(desmondCell.getI()==gridSize-1||cells[desmondCell.getI()+1][desmondCell.getJ()].isPath()) {
			moveBottomButton.setEnabled(false);
		}
		else {
			moveBottomButton.setEnabled(true);
		}
		if(desmondCell.getJ()==0||cells[desmondCell.getI()][desmondCell.getJ()-1].isPath()) {
			moveLeftButton.setEnabled(false);
		}
		else {
			moveLeftButton.setEnabled(true);
		}
		if(desmondCell.getJ()==gridSize-1||cells[desmondCell.getI()][desmondCell.getJ()+1].isPath()) {
			moveRightButton.setEnabled(false);
		}
		else {
			moveRightButton.setEnabled(true);
		}
	}
}
