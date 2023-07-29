package firstGameTry;

import java.awt.Color;


public class Ball {
int width;
int height;
Color colorB;
int xCordB ;
int yCordB ;
int xVel;
int yVel;

public Ball(int width, int height, Color colorB, int xCordB, int yCordB) {
	this.width = width;
	this.height = height;
	this.colorB = colorB;
	this.xCordB = xCordB;
	this.yCordB = yCordB;
	this.xVel = 1;
	this.yVel = -1;
}

}
