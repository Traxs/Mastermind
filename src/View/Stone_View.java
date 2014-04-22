package View;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JComponent;

import Mastermind.StoneCode;

public class Stone_View extends JComponent
{
	private static final long serialVersionUID = -2760172724481463167L;

	protected StoneCode Code;
	public static int StoneSize = 55; //Groesse eines Spielsteins

	public Stone_View(StoneCode Code)
	{
		this.Code = Code;

		setMinimumSize(new Dimension(StoneSize, StoneSize));
		setMaximumSize(new Dimension(StoneSize, StoneSize));
		setPreferredSize(new Dimension(StoneSize, StoneSize));
	}
	
	public StoneCode getCode()
	{
		return this.Code;
	}
	
	@Override
	public void paint(Graphics arg0)
	{
		arg0.setColor(Code.getColor());
		arg0.fillOval( 0, 0, 50, 50);
	}
}
