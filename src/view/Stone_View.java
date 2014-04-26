package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

import file.Mastermind_File;

public class Stone_View extends JComponent
{
	private static final long serialVersionUID = -2760172724481463167L;

	protected int code;
	public static final int STONESIZE = 55; //Groesse eines Spielsteins
	private static final BufferedImage HOLE = Mastermind_File.loadImage("hole.png");
	private static final BufferedImage WHITE = Mastermind_File.loadImage("white.png");
	private static final BufferedImage BLACK = Mastermind_File.loadImage("black.png");
	private static final BufferedImage RED = Mastermind_File.loadImage("red.png");
	private static final BufferedImage GREEN = Mastermind_File.loadImage("green.png");
	private static final BufferedImage ORANGE = Mastermind_File.loadImage( "orange.png");
	private static final BufferedImage PINK = Mastermind_File.loadImage("pink.png");
	private static final BufferedImage BROWN = Mastermind_File.loadImage("brown.png");
	private static final BufferedImage YELLOW = Mastermind_File.loadImage("yellow.png");
	private static final BufferedImage CYAN = Mastermind_File.loadImage("cyan.png");
	private static final BufferedImage LIME = Mastermind_File.loadImage("lime.png");
	private static final BufferedImage PURPLE = Mastermind_File.loadImage("purple.png");
	private static final BufferedImage MAGENTA = Mastermind_File.loadImage("magenta.png");
	private static final BufferedImage GREY = Mastermind_File.loadImage("grey.png");
	private static final BufferedImage DARKBLUE = Mastermind_File.loadImage("darkblue.png");
	private static final BufferedImage DARKGREEN = Mastermind_File.loadImage("darkgreen.png");
	private static final BufferedImage[] BUFFERED_IMAGES = {WHITE, BLACK, RED, GREEN, ORANGE, PINK, BROWN, YELLOW, CYAN, LIME, PURPLE, MAGENTA, GREY, DARKBLUE, DARKGREEN};

	public Stone_View(int code)
	{
		//Code Zuweisen
		this.code = code;
		//Gr��e Setzen
		setMinimumSize(new Dimension(STONESIZE, STONESIZE));
		setMaximumSize(new Dimension(STONESIZE, STONESIZE));
		setPreferredSize(new Dimension(STONESIZE, STONESIZE));		
	}
	
	public int getCode()
	{
		return this.code;
	}
	
	@Override
	public void paint(Graphics arg0)
	{
		arg0.drawImage(HOLE,  0, 0, this);
		if(code != -1)
		{
			arg0.drawImage(BUFFERED_IMAGES[code],  0, 0, this);
		}
	}
}
