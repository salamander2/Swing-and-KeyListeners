package SwingGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;


/********************************
 * This example uses KEY CODES  *
 ********************************/

public class AnimationAndKeys2 extends JFrame implements KeyListener {

	static final int SLEEPTIME = 6;	//in milliseconds
	static final int SCRWIDTH = 800;
	static final int SCRHEIGHT = 600;

	static final int MAXWEAPON = 7;
	
	DrawingPanel panel;
	
	boolean isPlaying = true;
	Rectangle ship = new Rectangle(SCRWIDTH/2, (int) (SCRHEIGHT*0.8), 30,80);
	int speed = 5;
	int weapon = 1;
	boolean shipMoving = false;
	boolean shrinkOn = false;
	boolean isShrinking = false;
	
	
	AnimationAndKeys2() throws InterruptedException { //this exception is required for Thread.sleep()
		initialize();
		createGUI();

		while(isPlaying) {
			if (shipMoving) moveShip();
			if (shrinkOn) squat();
			Thread.sleep(SLEEPTIME);
		}
	}
	
	void initialize() {}
	
	void createGUI() {

		JFrame window = new JFrame();
		window.setTitle("Test of keys: key codes");
		window.setSize(SCRWIDTH, SCRHEIGHT);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLocationRelativeTo(null);

		panel = new DrawingPanel();
		panel.setBackground(Color.WHITE);
		panel.addKeyListener(this);
		panel.setFocusable(true);
		panel.requestFocus();

		window.add(panel);

		window.setVisible(true);
	}
	

	//With this method, two keys can be down at once allowing smooth diagonal movement
	void moveShip() {
		if (isKeyDown(KeyEvent.VK_Z)) ship.x--;		
		if (isKeyDown(KeyEvent.VK_C)) ship.x++;
		if (isKeyDown(KeyEvent.VK_S)) ship.y--;
		if (isKeyDown(KeyEvent.VK_X)) ship.y++;
		if (! isKeyDown(KeyEvent.VK_Z) && ! isKeyDown(KeyEvent.VK_C)) {
			if (! isKeyDown(KeyEvent.VK_S) && ! isKeyDown(KeyEvent.VK_X)) {
				shipMoving = false;
			}
		}
		panel.repaint();		
	}
	
	//this is how you handle something only being done once even on a key being held down.
	// Note that I'm dividing the height by 2, so if I kept doing this, the ship would shrink more and more.
	void squat() {
		if (isKeyDown(KeyEvent.VK_P)) shrinkOn = true;
		else shrinkOn = false;
		
		if (shrinkOn && !isShrinking){
			ship.height /= 2;
			ship.y += ship.height;
			isShrinking = true;
		}
		
		if (!shrinkOn && isShrinking) {
			ship.y -= ship.height;
			ship.height *= 2;
			isShrinking = false;
		}
		panel.repaint();
	}

	void selectWeapon(char keyChar){
		if (keyChar == 'e') weapon++;
		if (keyChar == 'q') weapon--;
		if (weapon > MAXWEAPON) weapon = 1;
		if (weapon < 1) weapon = MAXWEAPON;
		panel.repaint();
	}

	
	

	class DrawingPanel extends JPanel {
		DrawingPanel() {
			this.setFont(new Font("Sans Serif", Font.PLAIN, 20));
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g); //clear and repaint background

			g.setColor(Color.BLUE);
			g.fillRect(ship.x, ship.y, ship.width, ship.height);
			
			g.drawString("Weapon = "+weapon, 30, 20);	
		}
	}


	/** size of keysDown array **/
	private final int numKeyCodes = 256;
	/** Array of booleans representing characters currently held down **/
	private boolean[] keysDown = new boolean [numKeyCodes];

	public synchronized boolean isKeyDown(int key) {
//		if ((key >=0) & (key < numKeyCodes)) 
		return keysDown[key];
//		return false;
	}
	

	/******** Event handler methods ****************/
//	Note that the key-typed event doesn't have key code information, and key-pressed and key-released events don't have key character information
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode ();
		keysDown [key] = true;
		
		switch (key) {
		
		/* This is the normal way of handling key presses. 
		 * There is always a delay after the first press  */
		case KeyEvent.VK_A:
			ship.x -= speed;			
			break;
		case KeyEvent.VK_D:
			ship.x += speed;
			break;
			
		/* This allows smooth instant keymotion. 
		 * It's a bit more complicated to implement, 
		 * but it works well. */
		case KeyEvent.VK_Z:
		case KeyEvent.VK_C:
		case KeyEvent.VK_S:
		case KeyEvent.VK_X:
			shipMoving = true;
			break;
		
		/* This is demonstrating how to handle a key that only does something once 
		 * until it is released. */
		case KeyEvent.VK_P:
			shrinkOn = true;
			break;
		case KeyEvent.VK_SPACE:
			break;
		}
		 
		panel.repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keysDown [e.getKeyCode()] = false;
//		panel.repaint(); //normally not needed
	}
	
	
	/* Use this to type one key at a time.
	 * Note that you can still get multiple key presses if you hold it down.
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		char keyChar = e.getKeyChar();
		if (keyChar == 'q' || keyChar == 'e') selectWeapon(keyChar);
	}


	public static void main(String[] args) throws InterruptedException {
		new AnimationAndKeys2();
	}

}
