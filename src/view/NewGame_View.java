package view;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;

import mastermind.Mastermind;
import mastermind.State;

public class NewGame_View extends JPanel implements ItemListener
{
	private static final long serialVersionUID = 691420212962120955L;
	private JSlider jSliderColorNumber;
	private JSlider jSliderRowNumber;
	private JSlider jSliderCodeNumber;
	private JCheckBox jCheckBoxRowNumber;
	private JRadioButton jRadioButtonHuman;
	private JRadioButton jRadioButtonHumanHelp;
	private JRadioButton jRadioButtonKI;
	
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
		
		jCheckBoxRowNumber = new JCheckBox("Infinity");
		jCheckBoxRowNumber.addItemListener(this);

		jRadioButtonHuman = new JRadioButton("Normal");
		jRadioButtonHuman.setSelected(true);
		jRadioButtonHumanHelp = new JRadioButton("with Assistent");
		jRadioButtonKI = new JRadioButton("against KI");
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(jRadioButtonHuman);
		buttonGroup.add(jRadioButtonHumanHelp);
		buttonGroup.add(jRadioButtonKI);

		add(new JLabel("how many different colors?\n"));
		add(jSliderColorNumber);
		add(new JLabel("\n\nhow many stones?\n"));
		add(jSliderCodeNumber);
		add(new JLabel("\n\nhow many tries?\n"));
		add(jSliderRowNumber);
		add(jCheckBoxRowNumber);
		add(new JLabel("\n\nMode:\n"));
		add(jRadioButtonHuman);
		add(jRadioButtonHumanHelp);
		add(jRadioButtonKI);
		
	
	}
	
	public State getState()
	{
	    if(jRadioButtonHuman.isSelected())
	        return State.playingHuman;
	    
	    if(jRadioButtonHumanHelp.isSelected())
	        return State.playingHumanHelp;
	       
	    return State.playingKI;
	}
	
	public int getColorNumber()
	{
		return jSliderColorNumber.getValue();
	}
	
	public int getRowNumber()
	{
		return jCheckBoxRowNumber.isSelected() ? -1 : jSliderRowNumber.getValue();
	}
	
	public int getCodeNumber()
	{
		return jSliderCodeNumber.getValue();
	}

    @Override
    public void itemStateChanged(ItemEvent e)
    {
        jSliderRowNumber.setEnabled(e.getStateChange() == ItemEvent.DESELECTED);
    }
}
