package firstGameTry;

import java.awt.Color;

public class Paddle {
int xCord;
int yCord;
int height;
int width;
Color color;

public Paddle(int xCord, int yCord, int width, int height, Color color) {
	this.xCord = xCord;
	this.yCord = yCord;
	this.width = width;
	this.height = height;
	this.color = color;
}

}
