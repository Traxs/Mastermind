package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class StoneSelection_View extends Stone_View implements MouseListener
{
	private static final long serialVersionUID = -795637293536622894L;
	
	private int colorLength;

	public StoneSelection_View(int colorLength)
	{
		super(0);
		this.colorLength = colorLength;
		addMouseListener(this);
		setComponentPopupMenu(new PopUp());
	}

	class PopUp extends JPopupMenu
	{
	    /**
		 * 
		 */
		private static final long serialVersionUID = -9101520749960960679L;

		public PopUp()
		{
	    	JMenuItemCode jMenuItemCode;
	    		
	        for(int i = 0; i < colorLength; i++)
	        	{
	        		jMenuItemCode = new JMenuItemCode(i);
	        		jMenuItemCode.setBackground(Mastermind_View.backgroundColor);
	        		jMenuItemCode.setBorderPainted(false);
	        		jMenuItemCode.setIcon(new ImageIcon(BUFFERED_IMAGES[i].getScaledInstance(32, 32, 10000)));
	        		add(jMenuItemCode);
	        	}
	        	setBackground(Mastermind_View.backgroundColor);
	        	setBorderPainted(false);
		}
	}
	
	class JMenuItemCode extends JMenuItem implements ActionListener
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 2019732443956607122L;
		private int cCode;
		
		public JMenuItemCode(int code)
		{
			this.cCode = code;
			addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{
			setCode(cCode);
		}
	}
	
	public void setCode(int code)
	{
	    this.code = code;
		repaint();
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0)
	{
		if(arg0.getButton() == MouseEvent.BUTTON1)
		{
			setCode((code + 1) >= colorLength ? 0 : code + 1);
			repaint();
		}
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
