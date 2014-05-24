package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

import mastermind.Row;
import file.Mastermind_File;

/**
 * The Class Result_View.
 */
public class Result_View extends JComponent
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3428603070137675412L;
	
	/** The Constant RESULTHOLE. */
	private static final BufferedImage RESULTHOLE = Mastermind_File.loadImage("resulthole.png");
	
	/** The Constant RESULTWHITE. */
	private static final BufferedImage RESULTWHITE = Mastermind_File.loadImage("resultwhite.png");
	
	/** The Constant RESULTRED. */
	private static final BufferedImage RESULTRED = Mastermind_File.loadImage("resultred.png");
	
	/** The Constant RESULTSIZE. */
	public static final int RESULTSIZE = 27;
	
	/** The red. */
	private int red;
	
	/** The white. */
	private int white;
	
	/** The code length. */
	private int codeLength;

	/**
	 * Instantiates a new result_ view.
	 *
	 * @param row the row
	 */
	public Result_View(Row row)
	{
		this.red = row.getRed();
		this.white = row.getWhite();
		
		codeLength = row.getCodeLength();
		int xSize = codeLength % 2 == 0 ? codeLength / 2 : (codeLength + 1) / 2;

		setMinimumSize(new Dimension(RESULTSIZE * xSize, Stone_View.STONESIZE));
		setMaximumSize(new Dimension(RESULTSIZE * xSize, Stone_View.STONESIZE));
		setPreferredSize(new Dimension(RESULTSIZE * xSize, Stone_View.STONESIZE));
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics arg0)
	{		
		int red = this.red;
		int white = this.white;
		int x = 0,y = 0;
		
		for(int i = 0; i < codeLength; i++)
		{
			y = i % 2 == 0 ? 0 : RESULTSIZE;
			x = RESULTSIZE * (i / 2);

			if(red > 0)
			{
				arg0.drawImage(RESULTRED, x, y, null);
				red--;
			}
			else if(white > 0)
			{
				arg0.drawImage(RESULTWHITE, x, y, null);
				white--;
			}
			else
			{
				arg0.drawImage(RESULTHOLE, x, y, null);
			}
		}
	}
}
