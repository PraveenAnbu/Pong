package firstGameTry;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JOptionPane;

public class Game implements Runnable {
Display display;
int height;
int width;
String title = "Pong";
Thread thread;
private boolean running = false;
private BufferStrategy bs;
private Graphics g;
Paddle rightPaddle;
Paddle leftPaddle;
Ball ball;
private KeyManager keyManager;
String leftScore = "0";
String rightScore = "0";
int initialVel = 1;
int winScore;
int startCounter=0;
int fps;

public Game(int height, int width, int winScore, int fps) {
	this.height = height;
	this.width = width;
	keyManager = new KeyManager();
	this.winScore = winScore;
	this.fps = fps;
	
}

private void init() {
	display = new Display(this.height, this.width,title);
	display.getFrame().addKeyListener(keyManager);
	leftPaddle = new Paddle(0,((this.height/2)-75),20,150,Color.BLUE);
	rightPaddle = new Paddle(this.width-20,this.height/2 -75,20,150,Color.RED);
	ball = new Ball(12,12, Color.BLACK, this.width/2, this.height/2);
}

private void update() {
//gameStart();
ballMovement();
keyManager();
gameEnd();
}

private void render() {
	bs = display.getCanvas().getBufferStrategy();
	if(bs == null) {
		display.getCanvas().createBufferStrategy(3);
		return;
	}
	g = bs.getDrawGraphics();
	// clear screen
	g.clearRect(0, 0, this.width, this.height);
	//start draw
	g.setColor(rightPaddle.color);
	g.fillRect(rightPaddle.xCord, rightPaddle.yCord, rightPaddle.width, rightPaddle.height);
	g.setColor(leftPaddle.color);
	g.fillRect(leftPaddle.xCord, leftPaddle.yCord, leftPaddle.width, leftPaddle.height);
	g.setColor(ball.colorB);
	g.fillOval(ball.xCordB, ball.yCordB, ball.width, ball.height);
	g.setFont(new Font("Arial", 1, 30));
	g.drawString(leftScore, (this.width * 2)/5, this.height/8);
	g.setFont(new Font("Arial", 1, 30));
	g.drawString(rightScore, (this.width * 3)/5, this.height/8);
	//stop draw
	bs.show();
	g.dispose();
}

public void run() {
		init();
		
		
		double timePerTick = 1000000000/this.fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		int ticks = 0;
		
		while(running) {
			now = System.nanoTime();
			delta += ((now - lastTime));
			timer += (now - lastTime);
			lastTime = now;
			
			if(delta>=timePerTick) {
			update();
			render();
			ticks++;
			delta -= timePerTick;
			}
			
			if(timer >= 1000000000) {
				System.out.println("Ticks and frames:" + ticks);
				ticks = 0;
				timer = 0;
			}
		}
		stop();
}

public KeyManager getKeyManager() {
	return keyManager;
}

public synchronized void start() {
	if(running) {
		return;
	}
	running = true;
	thread = new Thread(this);
	thread.start();
}
public synchronized void stop() {
	if(!running) {
		return;
	}
	running = false;
	try {
		thread.join();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

private void keyManager() {
	keyManager.tick();
	if(keyManager.leftUp) {
		leftPaddle.yCord--;
	}
	if(keyManager.leftDown) {
		leftPaddle.yCord++;
	}
	if(keyManager.rightUp) {
		rightPaddle.yCord--;
	}
	if(keyManager.rightDown) {
		rightPaddle.yCord++;
	}
	if(keyManager.escape) {
		System.exit(1);
	}
}
private void ballMovement() {
	ball.xCordB += ball.xVel;
	ball.yCordB += ball.yVel;
	//hits right paddle
	if((ball.xCordB + ((ball.width)/2)) == (rightPaddle.xCord - ((rightPaddle.width)/2))) {
		if(((ball.yCordB > rightPaddle.yCord) && (ball.yCordB < (rightPaddle.yCord + rightPaddle.height)))) {
			ball.xVel = ball.xVel * -1;
			
		}
	}
	//passes right paddle
	if((ball.xCordB - ((ball.width)/2)) >= this.width) {
		int leftScoreNum = Integer.parseInt(leftScore);
		leftScoreNum++;
		leftScore = leftScoreNum + "";
		ball.xCordB = (this.width*7)/10;
		ball.xVel = ball.xVel * -1;

	}
	//hits edge of right paddle
	if(((ball.xCordB - ((ball.width)/2)) < this.width) && ((ball.xCordB + ((ball.width)/2)) > (rightPaddle.xCord - ((rightPaddle.width)/2)))) 
	{
		if(((ball.yCordB + ball.width/2) == (rightPaddle.yCord )||((ball.yCordB - ball.width/2) == (rightPaddle.yCord + rightPaddle.height))))  {
			ball.xVel = ball.xVel * -1;
			ball.yVel = ball.yVel * -2;
		}
	}
	
	//passes left paddle
	if((ball.xCordB + ((ball.width)/2)) <= 0) {
		int rightScoreNum = Integer.parseInt(rightScore);
		rightScoreNum++;
		rightScore = "" + rightScoreNum ;
		ball.xCordB = (this.width*3)/10;
		ball.xVel = ball.xVel * -1;

	}
	
	//hits left paddle
	if((ball.xCordB - ((ball.width)/2)) == (leftPaddle.xCord + ((leftPaddle.width)/2))) {
		if((ball.yCordB > leftPaddle.yCord) && (ball.yCordB < (leftPaddle.yCord + leftPaddle.height))) {
			ball.xVel = ball.xVel * -1;
			
		}
	}
	//hits edge left paddle
	if(((ball.xCordB + ((ball.width)/2)) > 0) && ((ball.xCordB - ((ball.width)/2)) < (leftPaddle.xCord + ((leftPaddle.width)/2)))) 
	{
		if((((ball.yCordB + (ball.width/2)) == leftPaddle.yCord ))||((ball.yCordB - (ball.width/2)) == (leftPaddle.yCord + (leftPaddle.height))))  {
			ball.xVel = ball.xVel * -1;
			ball.yVel = ball.yVel * -2;
		}
	}
	//hits bottom
	if(((ball.yCordB - (ball.width/2)) <= 0  )){
		if(ball.yVel <= -1 ) {
			ball.yVel = 1;
		}
	}
	//hits top 
	if(((ball.yCordB + (ball.width/2)) >= this.height)){

		if(ball.yVel >= 1) {
			ball.yVel = -1;
		}
	
	}
}

/*private void gameStart() {
	while(startCounter==0) {
		startCounter++;
		JOptionPane.showMessageDialog(null, "Play");
	}
}*/

private void gameEnd() {
	if(Integer.parseInt(rightScore) == this.winScore ||Integer.parseInt(leftScore) == this.winScore ) {
	if(Integer.parseInt(rightScore) == this.winScore) {
		
		JOptionPane.showMessageDialog(null, "Right Paddle Player Won!");

	}
	if(Integer.parseInt(leftScore) == this.winScore) {

		JOptionPane.showMessageDialog(null, "Left Paddle Player Won!");
	}
	int option = JOptionPane.showConfirmDialog(null, "Play Again?", "Pong", JOptionPane.YES_NO_OPTION);
	if(option == 0) {
		leftScore = 0 + "";
		rightScore = 0 + "";
		//int startCounter = 0;
		leftPaddle = new Paddle(0,((this.height/2)-90),20,180,Color.BLUE);
		rightPaddle = new Paddle(this.width-20,this.height/2 -90,20,180,Color.RED);
		ball = new Ball(12,12, Color.BLACK, this.width/2, this.height/2);
		this.start();
	}
	else {
		System.exit(1);
	}
}
}


}

