package View;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JComponent;
import Mastermind.Row;

public class Result_View extends JComponent
{
	private static final long serialVersionUID = 3428603070137675412L;
	
	private int Red;
	private int White;
	
	public Result_View(Row row)
	{
		this.Red = row.getRed();
		this.White = row.getWhite();
		
		setMinimumSize(new Dimension(55, 55));
		setMaximumSize(new Dimension(55, 55));
		setPreferredSize(new Dimension(55, 55));
	}
	
	@Override
	public void paint(Graphics arg0)
	{
		arg0.drawString("R: " + Red, 10, arg0.getFont().getSize() + 10);
		arg0.drawString("W: " + White, 10, arg0.getFont().getSize() * 2 + 10);
	}
}
