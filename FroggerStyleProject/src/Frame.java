import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.JLabel;
public class Frame extends JPanel implements ActionListener, MouseListener, KeyListener {
	
	private JLabel scoreLabel;
	
	//for any debugging code we add
	public static boolean debugging = false;
	
	//Timer related variables
	int waveTimer = 5; //each wave of enemies is 20s
	long ellapseTime = 0;
	Font timeFont = new Font("Courier", Font.BOLD, 70);
	int level = 0;
	int lives = 6;
	int score = 0;
	boolean riding = false;
	Font myFont = new Font("Courier", Font.BOLD, 40);
	SimpleAudioPlayer backgroundMusic = new SimpleAudioPlayer("Halloween Theme.wav", true);
//	Music soundBang = new Music("bang.wav", false);
//	Music soundHaha = new Music("haha.wav", false);
	
	Ghost ghost = new Ghost();
	Bat bat = new Bat();
	Bat bat2 = new Bat(100, 200);
	Background bg = new Background();
	GameOverScreen gos = new GameOverScreen();
	WinScreen ws = new WinScreen();
	River river = new River();
	PumpkinScrolling pumpkin = new PumpkinScrolling();
	PumpkinScrolling2 pumpkin2 = new PumpkinScrolling2();
	
	
	//rows of objects
	BatScrolling[] row1 = new BatScrolling[5];
	ArrayList<Bat> row1List = new ArrayList<Bat>();
	ArrayList<LifeImage> life = new ArrayList<LifeImage>();
	
	BatScrolling2[] row2 = new BatScrolling2[5];
	
	PumpkinScrolling[] row3 = new PumpkinScrolling[4];
	PumpkinScrolling2[] row4 = new PumpkinScrolling2[4];
	
	
	//frame width/height
	static int width = 600;
	static int height = 800;	
	
	

	public void paint(Graphics g) {
		super.paintComponent(g);

		//paint the other objects on the screen
		
		bg.paint(g);
		river.paint(g);
		ghost.paint(g);
		

		
		//have the row1 objects paint on the screen
		//for each obj in row1
		for(BatScrolling obj : row1) {
			obj.paint(g);
		}
		
		for(Bat obj : row1List) {
			
		}
		
		for(BatScrolling2 obj : row2) {
			obj.paint(g);
		}
		
		for(PumpkinScrolling obj : row3) {
			obj.paint(g);
		}
		
		for(PumpkinScrolling2 obj : row4) {
			obj.paint(g);
		}
		
		//make sure ghost still isnt riding after getting off pumpkin
		if(ghost.getY() >= 230) {
			ghost.vx = 0;
			riding = false;
		}
		
		if(ghost.getY() <= 416) {
			ghost.vx = 0;
			riding = false;
		}
	//die if going off screen
	if(ghost.getX() >= 585) {
		ghost.x = 275;
		ghost.y = 675;
		System.out.println("Lives left: " + (lives - 1));
		lives--;
	}
	
	if(ghost.getX() <= -65) {
		ghost.x = 275;
		ghost.y = 675;
		System.out.println("Lives left: " + (lives - 1));
		lives--;
	}
	//Collision Detection
		//for every object, invoke the collision
		
		for(PumpkinScrolling obj : row3) {
			if(obj.collided(ghost)) {
				ghost.setVx(obj.getVx());
				riding = true;
				break;
			}
		}
		
		 riding = false;
		for(PumpkinScrolling2 obj : row4) {
			if(obj.collided(ghost)) {
				ghost.setVx(obj.getVx());
				riding = true;
				break;
			}else if(!obj.collided(ghost)) {
				riding = false;
			}
		}
		
		
		//main character stops if not on rideable object
		//limit Y too
	
		
		if(!riding && river.collided(ghost)) {
		riding = false;
		ghost.setVx(0);
		ghost.x = 275;
		ghost.y = 675;
		System.out.println("Lives left: " + (lives - 1));
		lives--;
		}
		
		
		
		
			for(BatScrolling obj : row1) {
				//invoke the collided method for your
				//class - pass the main character as your argument
				while(obj.collided(ghost)) {
					System.out.println("Lives left: " + (lives - 1));
					ghost.x = 275;
					ghost.y = 675;
					lives--;
				
			}
		}
			
			for(BatScrolling2 obj : row2) {
				//invoke the collided method for your
				//class - pass the main character as your argument
				while(obj.collided(ghost) && lives > 0) {
					System.out.println("Lives left: " + (lives - 1));
					ghost.x = 275;
					ghost.y = 675;
					lives--;
				}
			}
			//paint game over screen
			if(lives <= 0) {
				gos.paint(g);
			}
			//show score in console
			if(ghost.y <= 200) {
				score++;
				ghost.x = 275;
				ghost.y = 675;
				System.out.println("Score : " + score);
			}
			//paint win screen
			if(score >= 3) {
				ws.paint(g);
			}
		
			//drawing the life counter images
			for(LifeImage obj : life) {
				//obj.paint(g);
				
		//draw the text for lives, score, and restart in the top right
			g.setColor(Color.RED);
			g.drawString("Score: " + score, 8, 50);
			g.drawString("Lives: " + lives, 80, 50);
			g.drawString("Press R to Restart", 475, 50);

			}
			
	}
			
	
	
	
	
	
	public static void main(String[] arg) {
		Frame f = new Frame();
		
	}
	
	public Frame() {
		JFrame f = new JFrame("Duck Hunt");
		f.setSize(new Dimension(width, height));
		f.setBackground(Color.white);
		f.add(this);
		f.setResizable(false);
 		f.addMouseListener(this);
		f.addKeyListener(this);
	
	//	backgroundMusic.play();
		
		
		//Setup any 1D array here! - create the objects that go in them
		//traverse the array
		for(int i = 0; i < row1.length; i++) {
			row1[i] = new BatScrolling(i*125 ,500);
		}
	
		
		for(int i = 0; i < row2.length; i++) {
			row2[i] = new BatScrolling2(i*125 ,600);
		}
		
		for(int i = 0; i < row3.length; i++) {
			row3[i] = new PumpkinScrolling(i*100 ,290);
		}
		
		for(int i = 0; i < row4.length; i++) {
			row4[i] = new PumpkinScrolling2(i*100 ,360);
		}
		
		//practice row for ArrayList
		for(int i = 0; i < 5; i++) {
			this.row1List.add(new Bat(i*125, 500));
		}
		
		
		//Start with 6 attempts
		for(int i = 0; i < 6; i++) {
			this.life.add(new LifeImage(i*30, 10));
		}
		
		if(life.size() > 0 && lives == lives - 1)
			life.remove(life.size()- 1);
		
		
		
		//the cursor image must be outside of the src folder
		//you will need to import a couple of classes to make it fully 
		//functional! use eclipse quick-fixes
		setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
				new ImageIcon("torch.png").getImage(),
				new Point(0,0),"custom cursor"));	
		
		
		Timer t = new Timer(16, this);
		t.start();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
	
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent m) {
		
	
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getKeyCode() == 101) {
			//get position for other code
			System.out.println(ghost.getX());
			System.out.println(ghost.getY());
		}
		if(arg0.getKeyCode() == 82) {
			ghost.x = 275;
			ghost.y = 675;
			lives = 6;
			score = 0;
		}
			
		//move main character up
		if(arg0.getKeyCode() == 87) {
			ghost.move(0);
		//move main character down	
		}else if(arg0.getKeyCode() == 83) {
			ghost.move(1);
			
		}else if(arg0.getKeyCode() == 65) {
			ghost.move(2);
		
		}else if(arg0.getKeyCode() == 68) {
			ghost.move(3);
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
