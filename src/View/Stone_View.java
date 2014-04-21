package View;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JComponent;

import Mastermind.StoneCode;

public class Stone_View extends JComponent
{
	private static final long serialVersionUID = -2760172724481463167L;

	protected StoneCode Code;

	public Stone_View(StoneCode Code)
	{
		this.Code = Code;

		setMinimumSize(new Dimension(55, 55));
		setMaximumSize(new Dimension(55, 55));
		setPreferredSize(new Dimension(55, 55));
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
