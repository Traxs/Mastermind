package view;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class StoneSelection_View extends Stone_View implements MouseListener
{
	private static final long serialVersionUID = -795637293536622894L;
	
	private int colorLength;

	public StoneSelection_View(int colorLength)
	{
		super(0);
		this.colorLength = colorLength;
		addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent arg0)
	{
		code = (code + 1) >= colorLength ? 0 : code + 1;

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
