import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class PumpkinScrolling{
	private Image forward; // backward, left, right; 	
	private AffineTransform tx;
	
	int dir = 0; 					//0-forward, 1-backward, 2-left, 3-right
	int width, height;				//collision detection
	int x, y;						//position of the object
	int vx, vy;						//movement variables
	double scaleWidth = 1.25;		//change to scale image
	double scaleHeight = 1.25; 		//change to scale image

	public PumpkinScrolling() {
		forward 	= getImage("/imgs/"+"pumpkin log sprite small.png"); //load the image for Tree

		//width and height for hitbox
		width = 55;
		height = 55;
		
		//used for placement on the JFrame
		x = -width; //offscreen for now
		y = 500;
		
		//if your movement will not be "hopping" base
		vx = 5;
		vy = 0;
		
		int vx2 = -5;
		int vy2 = 0;
		
		tx = AffineTransform.getTranslateInstance(0, 0);
		
		init(x, y); 				//initialize the location of the image
									//use your variables
		
	}
	
	public int getVx() {
		return vx;
	}
	
public boolean collided(Ghost character) {
		
		//represent each object as a rectangle
		//then check if they are intersecting
		Rectangle main = new Rectangle(
			character.getX(),
			character.getY(),
			character.getWidth(),
			character.getWidth()
			);
		
		Rectangle thisObject = new Rectangle(x + 20, y + 20, width, height);
		
		//user built-in method to check intersection (collision)
		return main.intersects(thisObject);
	}

	//2nd constructor - allow setting x and  y during construction
	public PumpkinScrolling(int x, int y) {
		
		//call the default constructor for all the normal stuff
		this(); //invokes default constructor
		
		// do the specific task for THIS constructor
		this.x = x;
		this.y = y;
		
		
	}

	public void paint(Graphics g) {
		//these are the 2 lines of code needed draw an image on the screen
		Graphics2D g2 = (Graphics2D) g;
		
		//update x and y if using vx, vy variables
		x += vx;
		y += vy;	
		
			
		//for infinite scrolling - teleport to the other side
		//once it leaves the other side
		if(x > 650) {
			x = -150;
		}
		
		
		init(x,y);
		

		g2.drawImage(forward, tx, null);
			
		//draw hitbox based on x,y, width, heigh
		//for collision detection
		if(Frame.debugging) {
			//draw hitbox only if debugging
			g.setColor(Color.green);
			g.drawRect(x + 20, y + 20, width, height);
		}
		
	}
	
	private void init(double a, double b) {
		tx.setToTranslation(a, b);
		tx.scale(scaleWidth, scaleHeight);
	}

	private Image getImage(String path) {
		Image tempImage = null;
		try {
			URL imageURL = PumpkinScrolling.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}

}
