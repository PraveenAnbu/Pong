package firstGameTry;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener{
	private boolean[] keys;
	public boolean rightUp, rightDown, leftUp, leftDown, escape;
	
	
	public KeyManager() {
		keys = new boolean[256];
	}
	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}
	public void tick() {
		rightUp = keys[KeyEvent.VK_UP];
		rightDown = keys[KeyEvent.VK_DOWN];
		leftUp = keys[KeyEvent.VK_W];
		leftDown = keys[KeyEvent.VK_S];
		escape = keys[KeyEvent.VK_ESCAPE];
	}
	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
		System.out.println("Pressed");
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
		
	}

}
