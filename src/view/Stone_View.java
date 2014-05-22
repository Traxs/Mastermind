/*
 * 
 */
package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JButton;

import file.Mastermind_File;

// TODO: Auto-generated Javadoc
/**
 * The Class Stone_View.
 */
public class Stone_View extends JButton
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2760172724481463167L;

	/** The code. */
	protected int code;
	
	/** The Constant STONESIZE. */
	public static final int STONESIZE = 55; //Groesse eines Spielsteins
	
	/** The Constant HOLE. */
	private static final BufferedImage HOLE = Mastermind_File.loadImage("hole.png");
	
	/** The Constant WHITE. */
	private static final BufferedImage WHITE = Mastermind_File.loadImage("white.png");
	
	/** The Constant BLACK. */
	private static final BufferedImage BLACK = Mastermind_File.loadImage("black.png");
	
	/** The Constant RED. */
	private static final BufferedImage RED = Mastermind_File.loadImage("red.png");
	
	/** The Constant GREEN. */
	private static final BufferedImage GREEN = Mastermind_File.loadImage("green.png");
	
	/** The Constant ORANGE. */
	private static final BufferedImage ORANGE = Mastermind_File.loadImage( "orange.png");
	
	/** The Constant PINK. */
	private static final BufferedImage PINK = Mastermind_File.loadImage("pink.png");
	
	/** The Constant BROWN. */
	private static final BufferedImage BROWN = Mastermind_File.loadImage("brown.png");
	
	/** The Constant YELLOW. */
	private static final BufferedImage YELLOW = Mastermind_File.loadImage("yellow.png");
	
	/** The Constant CYAN. */
	private static final BufferedImage CYAN = Mastermind_File.loadImage("cyan.png");
	
	/** The Constant LIME. */
	private static final BufferedImage LIME = Mastermind_File.loadImage("lime.png");
	
	/** The Constant PURPLE. */
	private static final BufferedImage PURPLE = Mastermind_File.loadImage("purple.png");
	
	/** The Constant MAGENTA. */
	private static final BufferedImage MAGENTA = Mastermind_File.loadImage("magenta.png");
	
	/** The Constant GREY. */
	private static final BufferedImage GREY = Mastermind_File.loadImage("grey.png");
	
	/** The Constant DARKBLUE. */
	private static final BufferedImage DARKBLUE = Mastermind_File.loadImage("darkblue.png");
	
	/** The Constant DARKGREEN. */
	private static final BufferedImage DARKGREEN = Mastermind_File.loadImage("darkgreen.png");
	
	/** The Constant BUFFERED_IMAGES. */
	protected static final BufferedImage[] BUFFERED_IMAGES = {WHITE, BLACK, RED, GREEN, ORANGE, PINK, BROWN, YELLOW, CYAN, LIME, PURPLE, MAGENTA, GREY, DARKBLUE, DARKGREEN};

	/**
	 * Instantiates a new stone_ view.
	 *
	 * @param code the code
	 */
	public Stone_View(int code)
	{
        setFocusable(false);
		//Code Zuweisen
		this.code = code;
		//Gr��e Setzen
		setMinimumSize(new Dimension(STONESIZE, STONESIZE));
		setMaximumSize(new Dimension(STONESIZE, STONESIZE));
		setPreferredSize(new Dimension(STONESIZE, STONESIZE));		
	}
	
	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public int getCode()
	{
		return this.code;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
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
