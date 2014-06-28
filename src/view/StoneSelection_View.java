package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 * The Class StoneSelection_View.
 */
public class StoneSelection_View extends Stone_View implements ActionListener
{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -795637293536622894L;

    /** The color length. */
    private int colorLength;

    /**
     * Instantiates a new stone selection_ view.
     * 
     * @param colorLength
     *            the color length
     */
    public StoneSelection_View(int colorLength)
    {
        super(0);
        this.colorLength = colorLength;
        addActionListener(this);
        setComponentPopupMenu(new PopUp());
    }

    /**
     * The inner class PopUp will create the dialog to choose the color in the
     * selection Box. the PopUp will show every possible color in the current
     * game verticaly.
     */
    class PopUp extends JPopupMenu
    {

        /** The Constant serialVersionUID. */
        private static final long serialVersionUID = -9101520749960960679L;

        /**
         * Instantiates a new pop up.
         */
        public PopUp()
        {
            JMenuItemCode jMenuItemCode;

            for (int i = 0; i < colorLength; i++)
            {
                jMenuItemCode = new JMenuItemCode(i);
                jMenuItemCode.setBackground(Mastermind_View.backgroundColor);
                jMenuItemCode.setBorderPainted(false);
                jMenuItemCode.setIcon(new ImageIcon(BUFFERED_IMAGES[i]
                        .getScaledInstance(32, 32, 10000)));
                add(jMenuItemCode);
            }
            setBackground(Mastermind_View.backgroundColor);
            setBorderPainted(false);
        }
    }

    /**
     * The Class JMenuItemCode.
     */
    class JMenuItemCode extends JMenuItem implements ActionListener
    {

        /** The Constant serialVersionUID. */
        private static final long serialVersionUID = 2019732443956607122L;

        /** The c code. */
        private int cCode;

        /**
         * Instantiates a new j menu item code.
         * 
         * @param code
         *            the code
         */
        public JMenuItemCode(int code)
        {
            this.cCode = code;
            addActionListener(this);
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
         * )
         */
        @Override
        public void actionPerformed(ActionEvent e)
        {
            setCode(cCode);
        }
    }

    /**
     * Sets the code.
     * 
     * @param code
     *            the new code
     */
    public void setCode(int code)
    {
        this.code = code;
        repaint();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        setCode((code + 1) >= colorLength ? 0 : code + 1);
    }
}
