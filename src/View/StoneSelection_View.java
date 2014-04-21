package View;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import Mastermind.StoneCode;

public class StoneSelection_View extends Stone_View implements MouseListener
{
	private static final long serialVersionUID = -795637293536622894L;
	
	private int ColorLength;

	public StoneSelection_View(StoneCode Code, int ColorLength)
	{
		super(Code);
		this.ColorLength = ColorLength;
		addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent arg0)
	{
		int NewCode = Code.getCode() + 1;
		
		if(NewCode >= ColorLength)
		{
			NewCode = 0;
		}
		
		Code = StoneCode.getStoneCode(NewCode);
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}
}
