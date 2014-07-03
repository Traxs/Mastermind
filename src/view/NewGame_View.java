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

/**
 * The New Game GUI class.
 * <p>
 * In this class will the NewGame Window be created.
 * <p>
 * <p>
 * 
 * @author Raphael Pavlidis
 * @author Nico
 * @version %I%, %G%
 * @since 1.0
 */
public class NewGame_View extends JPanel implements ItemListener
{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 691420212962120955L;

    /** The j slider color number. */
    private JSlider jSliderColorNumber;

    /** The j slider row number. */
    private JSlider jSliderRowNumber;

    /** The j slider code number. */
    private JSlider jSliderCodeNumber;

    /** The j check box row number. */
    private JCheckBox jCheckBoxRowNumber;

    /** The j radio button human. */
    private JRadioButton jRadioButtonHuman;

    /** The j radio button ki. */
    private JRadioButton jRadioButtonKI;

    /**
     * Instantiates a new new game_ view. to parse parameters for creating a new
     * Game. Alot of functions can be initialized here.
     */
    public NewGame_View()
    {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        jSliderColorNumber =
                new JSlider(JSlider.HORIZONTAL, 3, 15,
                        Mastermind.DEFAULTCOLORLENGTH);
        jSliderColorNumber.setMajorTickSpacing(3);
        jSliderColorNumber.setMinorTickSpacing(1);
        jSliderColorNumber.setPaintTicks(true);
        jSliderColorNumber.setPaintLabels(true);

        jSliderCodeNumber =
                new JSlider(JSlider.HORIZONTAL, 3, 15,
                        Mastermind.DEFAULTCODELENGTH);
        jSliderCodeNumber.setMajorTickSpacing(3);
        jSliderCodeNumber.setMinorTickSpacing(1);
        jSliderCodeNumber.setPaintTicks(true);
        jSliderCodeNumber.setPaintLabels(true);

        jSliderRowNumber =
                new JSlider(JSlider.HORIZONTAL, 10, 50,
                        Mastermind.DEFAULTROWLENGTH);
        jSliderRowNumber.setMajorTickSpacing(5);
        jSliderRowNumber.setMinorTickSpacing(1);
        jSliderRowNumber.setPaintTicks(true);
        jSliderRowNumber.setPaintLabels(true);

        jCheckBoxRowNumber = new JCheckBox("Infinity");
        jCheckBoxRowNumber.addItemListener(this);

        jRadioButtonHuman = new JRadioButton("Normal");
        jRadioButtonHuman.setSelected(true);
        jRadioButtonKI = new JRadioButton("against KI");

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(jRadioButtonHuman);
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
        add(jRadioButtonKI);

    }

    /**
     * Gets the state.
     * 
     * @return the state
     */
    public State getState()
    {
        if (jRadioButtonHuman.isSelected())
            return State.playingHuman;

        return State.setCode;
    }

    /**
     * Gets the color number.
     * 
     * @return the color number
     */
    public int getColorNumber()
    {
        return jSliderColorNumber.getValue();
    }

    /**
     * Gets the row number.
     * 
     * @return the row number
     */
    public int getRowNumber()
    {
        return jCheckBoxRowNumber.isSelected() ? -1 : jSliderRowNumber
                .getValue();
    }

    /**
     * Gets the code number.
     * 
     * @return the code number
     */
    public int getCodeNumber()
    {
        return jSliderCodeNumber.getValue();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
     */
    @Override
    public void itemStateChanged(ItemEvent e)
    {
        jSliderRowNumber.setEnabled(e.getStateChange() == ItemEvent.DESELECTED);
    }
}
