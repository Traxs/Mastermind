package view;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import mastermind.Mastermind;

public class NewGame_View extends JPanel
{
	private static final long serialVersionUID = 691420212962120955L;
	private JSlider jSliderColorNumber;
	private JSlider jSliderRowNumber;
	private JSlider jSliderCodeNumber;
	
	public NewGame_View()
	{
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		jSliderColorNumber = new JSlider(JSlider.HORIZONTAL, 3, 15, Mastermind.DEFAULTCOLORLENGTH);
		jSliderColorNumber.setMajorTickSpacing(3);
		jSliderColorNumber.setMinorTickSpacing(1);
		jSliderColorNumber.setPaintTicks(true);
		jSliderColorNumber.setPaintLabels(true);
		
		jSliderCodeNumber = new JSlider(JSlider.HORIZONTAL, 3, 15, Mastermind.DEFAULTCODELENGTH);
		jSliderCodeNumber.setMajorTickSpacing(3);
		jSliderCodeNumber.setMinorTickSpacing(1);
		jSliderCodeNumber.setPaintTicks(true);
		jSliderCodeNumber.setPaintLabels(true);
		
		jSliderRowNumber = new JSlider(JSlider.HORIZONTAL, 10, 50, Mastermind.DEFAULTROWLENGTH);
		jSliderRowNumber.setMajorTickSpacing(5);
		jSliderRowNumber.setMinorTickSpacing(1);
		jSliderRowNumber.setPaintTicks(true);
		jSliderRowNumber.setPaintLabels(true);
		
		add(new JLabel("Anzahl der Farben:\n"));
		add(jSliderColorNumber);
		add(new JLabel("\n\nLänge des Codes:\n"));
		add(jSliderCodeNumber);
		add(new JLabel("\n\nAnzahl der max. Versuche:\n"));
		add(jSliderRowNumber);
	}
	
	public int getColorNumber()
	{
		return jSliderColorNumber.getValue();
	}
	
	public int getRowNumber()
	{
		return jSliderRowNumber.getValue();
	}
	
	public int getCodeNumber()
	{
		return jSliderCodeNumber.getValue();
	}
}
