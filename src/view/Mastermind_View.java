package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Frame;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.UIManager;

import mastermind.Mastermind;
import mastermind.Row;
import mastermind.State;
import file.AwtDesktopNotSupported;
import file.Mastermind_File;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * The GUI class.
 * <p>
 * Here will everything be handled what the user can see and interact with.
 * <p>
 * <p>
 * 
 * @author Birk Kauer
 * @author Raphael Pavlidis
 * @author Nico
 * @version %I%, %G%
 * @since 1.0
 */
public class Mastermind_View extends JFrame
{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -809208636941136548L;

    /**
     * The mastermind.
     * 
     * @see mastermind.Mastermind
     */
    private Mastermind mastermind;

    /** The row length. */
    private int rowLength;

    /** The code length. */
    private int codeLength;

    /** The color length. */
    private int colorLength;

    /** The vertical box_ playfield. */
    private Box verticalBox_Playfield;

    /** The scroll pane_ playfield. */
    private JScrollPane scrollPane_Playfield;

    /** The scroll pane_ selection. */
    private JScrollPane scrollPane_Selection;

    /** The scroll pane_ hint. */
    private JScrollPane scrollPane_Hint;

    /** The horizontal box_ selection stone. */
    private Box horizontalBox_Selection;

    /** The horizontal box_ hint. */
    private Box horizontalBox_Hint;

    /** The j label_ row. */
    private JLabel jLabel_Row;

    /** The jbutton_ hint. */
    private JButton jbutton_Hint;

    /** The jbutton_ add. */
    private JButton jbutton_Add;

    /** The pos y. */
    private int posX = 0, posY = 0;

    /** The status bar. */
    private JLabel jLabel_statusBar;

    /** The Constant backgroundColor. */
    public static final Color backgroundColor = new Color(42, 42, 42);

    /** The Constant foregroundColor. */
    public static final Color foregroundColor = Color.white;

    /** The Constant borderColor. */
    public static final Color borderColor = Color.black;

    /**
     * The main method.
     * <p>
     * Start a new Thread to ensure the GUI is always user interactive.
     * <p>
     * 
     * @param args
     *            the arguments
     */
    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                new Mastermind_View();
            }
        });
    }

    /**
     * Instantiates a new mastermind_ view.
     * <p>
     * Familiarise the GUI with the Main class.
     * <p>
     * Start the generation of the Window with {@link #createView()}
     * <p>
     * 
     * @see mastermind.Mastermind
     * @see #createView()
     */
    public Mastermind_View()
    {
        this.mastermind = new Mastermind(this);
        createView();
    }

    /**
     * Creates the view.
     * <p>
     * Method will create whole GUI including Listener and every Popupmenu.
     * <p>
     * 
     * 
     * Describe GUI
     * 
     * 
     * <p>
     * <p>
     * 
     * @see #setIconImage(java.awt.Image)
     * @see #setContentPane(java.awt.Container)
     * @see #addMouseListener(java.awt.event.MouseListener)
     * @see #addMouseMotionListener(java.awt.event.MouseMotionListener)
     * @see #scrollPane_Hint
     * @see #scrollPane_Playfield
     * @see #scrollPane_Selection
     * @see #jbutton_Add
     * @see #jbutton_Hint
     * @see #jLabel_Row
     * @see #statusBar
     * @see #startNewGame(int, int, int, State, int[])
     * @see #verticalBox_Playfield
     * @see #horizontalBox_Hint
     * @see #horizontalBox_Selection
     * @see #addHint(int[])
     * @see #addRow(Row)
     * @see #createBox(JScrollPane, Box)
     * 
     */
    public void createView()
    {
        setIconImage(new ImageIcon("MIcon").getImage());

        // Making GUI's fancy.
        String[] usedComponents =
                { "Menu", "MenuBar", "MenuItem", "Panel", "RadioButton",
                        "Slider", "Label", "ComboBox", "CheckBox", "Button",
                        "OptionPane" };

        for (String usedComponent : usedComponents)
        {
            UIManager.put(usedComponent + ".background", backgroundColor);
            UIManager.put(usedComponent + ".foreground", foregroundColor);
        }

        UIManager.put("Button.border",
                BorderFactory.createLineBorder(borderColor, 2));
        UIManager.put("Menu.border",
                BorderFactory.createLineBorder(borderColor, 5));
        UIManager.put("OptionPane.messageForeground", foregroundColor);
        UIManager.put("ScrollBar.border", borderColor);
        UIManager.put("ScrollBar.darkShadow", borderColor);
        UIManager.put("ScrollBar.highlight", borderColor);
        UIManager.put("ScrollBar.thumb", borderColor);
        UIManager.put("ScrollBar.thumbShadow", borderColor);
        UIManager.put("ScrollBar.thumbDarkShadow", borderColor);
        UIManager.put("ScrollBar.thumbLightShadow", borderColor);
        UIManager.put("ScrollBar.shadow", borderColor);
        UIManager.put("control", borderColor);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);
        // Adding a mouse Listener for dragging the GUI around since we deleted
        // the standard Box
        addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent e)
            {
                posX = e.getX();
                posY = e.getY();
            }
        });

        addMouseMotionListener(new MouseAdapter()
        {
            public void mouseDragged(MouseEvent evt)
            {
                // sets frame position when mouse dragged
                setLocation(evt.getXOnScreen() - posX, evt.getYOnScreen()
                        - posY);

            }
        });

        // *** Playfield Box***
        scrollPane_Playfield = new JScrollPane();

        verticalBox_Playfield = Box.createVerticalBox();
        createBox(scrollPane_Playfield, verticalBox_Playfield);
        contentPane.add(scrollPane_Playfield);

        // *** Selection Box ***
        scrollPane_Selection = new JScrollPane();
        horizontalBox_Selection = Box.createHorizontalBox();
        createBox(scrollPane_Selection, horizontalBox_Selection);
        contentPane.add(scrollPane_Selection);

        // *** Hint Box ***
        scrollPane_Hint = new JScrollPane();
        horizontalBox_Hint = Box.createHorizontalBox();
        createBox(scrollPane_Hint, horizontalBox_Hint);
        contentPane.add(scrollPane_Hint);
        jbutton_Add = new JButton("Add");
        jbutton_Add.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (!jbutton_Add.isEnabled())
                    return;

                int[] newCode = new int[codeLength];
                // Adding the Code into the Stone_View Box
                for (int i = 0; i < codeLength; i++)
                {
                    newCode[i] =
                            ((Stone_View) horizontalBox_Selection
                                    .getComponent(i)).getCode();
                }
                // Checks if the button has a different Text for different modes
                if (jbutton_Add.getText().equals("Set Code"))
                {
                    if (mastermind.setSecretCode(newCode))
                    {
                        horizontalBox_Hint.removeAll();
                        // Adding the Secret Code to the Secret Code field
                        for (int code : newCode)
                        {
                            horizontalBox_Hint.add(new Stone_View(code));
                        }
                        horizontalBox_Selection.removeAll();
                    }
                }
                else if (mastermind.getState() == State.playingHumanHelp)
                {
                    mastermind.isPossible(newCode);
                }
                else
                {
                    mastermind.addRow(newCode);
                }
            }
        });

        contentPane.add(jbutton_Add);
        // Button "Hint"
        jbutton_Hint = new JButton();
        jbutton_Hint.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (!jbutton_Hint.isEnabled())
                    return;
                // Checks if the Button has the correct Text to start the
                // getHint()
                if (jbutton_Hint.getText().equals("Hint"))
                {
                    mastermind.getHint();
                }
                else
                {
                    mastermind.stopKI();
                }
            }
        });
        contentPane.add(jbutton_Hint);

        jLabel_Row = new JLabel();
        contentPane.add(jLabel_Row);

        // Status Bar
        jLabel_statusBar = new JLabel();
        contentPane.add(jLabel_statusBar);

        // *** JMenuBar ***
        {
            JMenuBar jMenuBar = new JMenuBar();
            jMenuBar.setBorder(BorderFactory.createLineBorder(borderColor));
            setJMenuBar(jMenuBar);

            JMenu jMenu_File = new JMenu("File");
            jMenu_File.setBorderPainted(false);
            jMenuBar.add(jMenu_File);

            JMenuItem jMenuItem_NewGame =
                    new JMenuItem("New Game",
                            Mastermind_File.loadIcon("new.png"));
            jMenuItem_NewGame.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent arg0)
                {
                    NewGame_View newGame_View = new NewGame_View();
                    // New Game button in Menu opening the New Game Panel
                    if (JOptionPane.showConfirmDialog(null, newGame_View,
                            "New Game", JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION)
                    {
                        startNewGame(newGame_View.getColorNumber(),
                                newGame_View.getRowNumber(),
                                newGame_View.getCodeNumber(),
                                newGame_View.getState());
                    }
                }
            });
            jMenu_File.add(jMenuItem_NewGame);

            JMenuItem jMenuItem_Save =
                    new JMenuItem("Save", Mastermind_File.loadIcon("save.png"));
            jMenuItem_Save.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent arg0)
                {
                    // Saving the current Mastermind Object
                    JFileChooser jFileChooser = new JFileChooser();
                    jFileChooser.setDialogType(JFileChooser.SAVE_DIALOG);

                    if (jFileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
                    {
                        try
                        {
                            mastermind.saveMastermind(jFileChooser
                                    .getSelectedFile());
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Error");
                        }
                    }
                }
            });
            jMenu_File.add(jMenuItem_Save);
            // Button to load a Mastermind Object
            JMenuItem jMenuItem_load =
                    new JMenuItem("Load", Mastermind_File.loadIcon("load.png"));
            jMenuItem_load.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent arg0)
                {
                    // Choose the File
                    JFileChooser jFileChooser = new JFileChooser();
                    jFileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
                    // Loading the Mastermind object
                    if (jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
                    {
                        try
                        {
                            mastermind.loadMastermind(jFileChooser
                                    .getSelectedFile());
                        }
                        catch (ClassNotFoundException e)
                        {
                            JOptionPane.showMessageDialog(null,
                                    "Class not found.");
                            return;
                        }
                        catch (IOException e)
                        {
                            JOptionPane.showMessageDialog(null,
                                    "Wrong or corrupted file.");
                            return;
                        }

                        update();
                    }
                }
            });
            jMenu_File.add(jMenuItem_load);
            // Button to load the Help Dokument
            JMenu jMenu_Help = new JMenu("Help");
            jMenu_Help.setBorderPainted(false);
            jMenuBar.add(jMenu_Help);

            JMenuItem jMenuItem_Help =
                    new JMenuItem("Help", Mastermind_File.loadIcon("Help.png"));
            jMenuItem_Help.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent arg0)
                {
                    // loading the Manual via AWT Desktop
                    try
                    {
                        Mastermind_File.loadPDF();
                    }
                    catch (IOException e)
                    {
                        JOptionPane.showMessageDialog(null,
                                "Cannot open the PDF");
                    }
                    catch (AwtDesktopNotSupported e)
                    {
                        JOptionPane.showMessageDialog(null,
                                "Awt Desktop is not supported");
                    }
                    catch (URISyntaxException e)
                    {
                        JOptionPane.showMessageDialog(null, "URI Syntax error");
                    }
                }
            });
            jMenu_Help.add(jMenuItem_Help);
            // Credits
            JMenuItem jMenuItem_about =
                    new JMenuItem("About",
                            Mastermind_File.loadIcon("about.png"));
            jMenuItem_about.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent arg0)
                {
                    JOptionPane
                            .showMessageDialog(
                                    null,
                                    "Version 1.0.0 \u00A9 by \n- Birk Kauer\n- Raphael Pavlidis\n- Nico Meier\n- Bettina Schuller");
                }
            });
            jMenu_Help.add(jMenuItem_about);

            jMenuBar.add(Box.createHorizontalGlue());

            JButton jbutton_Min = new JButton("  _  ");
            jbutton_Min.setBorderPainted(false);
            jbutton_Min.setFocusable(false);
            jbutton_Min.addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    setState(Frame.ICONIFIED);
                }
            });
            jMenuBar.add(jbutton_Min);

            JButton jbutton_Exit = new JButton("  X  ");
            jbutton_Exit.setFocusable(false);
            jbutton_Exit.setBorderPainted(false);
            jbutton_Exit.addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    System.exit(0);
                }
            });
            jMenuBar.add(jbutton_Exit);
        }
        // Repainting/updating the GUI
        update();
        setTitle("Mastermind");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        setVisible(true);
    }

    /**
     * Creates the box.
     * 
     * @param jScrollPane
     *            the j scroll pane
     * @param box
     *            the box
     */
    private void createBox(JScrollPane jScrollPane, Box box)
    {
        jScrollPane.setBorder(BorderFactory.createLineBorder(Color.black));
        box.setOpaque(true);
        box.setBackground(backgroundColor);
        box.setForeground(foregroundColor);
        box.setBorder(BorderFactory.createLineBorder(borderColor));
        jScrollPane.setViewportView(box);
    }

    /**
     * Add a row to the Playfield.
     * 
     * @param newRow
     *            the new row
     */
    public void addRow(Row newRow)
    {
        Box horizontalBox = Box.createHorizontalBox();

        for (int code : newRow.getCode())
        {
            horizontalBox.add(new Stone_View(code));
        }

        horizontalBox.add(new Result_View(newRow));

        verticalBox_Playfield.add(horizontalBox, 1);
        jLabel_Row.setText(verticalBox_Playfield.getComponentCount() - 1 + "/"
                + rowLength);
        scrollPane_Playfield.validate();
    }

    /**
     * Adds the hint.
     * 
     * @param code
     *            the code
     */
    public void addHint(int[] code)
    {
        for (int i = 0; i < code.length; i++)
        {
            ((StoneSelection_View) horizontalBox_Selection.getComponent(i))
                    .setCode(code[i]);
        }
    }

    /**
     * Update every JComponent after loading.
     * 
     * @see #createView()
     */
    public void update()
    {
        verticalBox_Playfield.removeAll();
        horizontalBox_Hint.removeAll();
        horizontalBox_Selection.removeAll();

        rowLength = mastermind.getRowLength();
        codeLength = mastermind.getCodeLength();
        colorLength = mastermind.getColorLength();
        // Calculates the size of the Window dynamically out of the Codelength
        int rowSize =
                Stone_View.STONESIZE
                        * codeLength
                        + (Result_View.RESULTSIZE * (codeLength % 2 == 0
                                ? codeLength / 2 : codeLength / 2 + 1)) + 30;

        // Set size of Window
        setSize(rowSize + 135, 610);
        scrollPane_Playfield.setBounds(12, 12, rowSize, 410);
        scrollPane_Selection.setBounds(12, 434, rowSize, 60);
        scrollPane_Hint.setBounds(12, 500, rowSize, 60);

        jbutton_Add.setBounds(rowSize + 24, 434, 98, 25);
        jbutton_Hint.setBounds(rowSize + 24, 397, 98, 25);
        jLabel_Row.setBounds(rowSize + 24, 12, 80, 30);

        jbutton_Add.setEnabled(true);
        jbutton_Hint.setEnabled(true);
        jLabel_Row.setText(0 + "/" + rowLength);
        jLabel_Row.setVisible(rowLength != -1);
        jLabel_statusBar.setBounds(12, 560, rowSize, 25);
        jLabel_statusBar.setEnabled(true);

        Box startBox = Box.createHorizontalBox();

        for (int i = 0; i < mastermind.getCodeLength(); i++)
        {
            startBox.add(new Stone_View(-1));
        }
        startBox.add(new Result_View(new Row(new int[codeLength], 0, 0)));
        verticalBox_Playfield.add(startBox);

        for (int i = 0; i < mastermind.getRowSize(); i++)
        {
            addRow(mastermind.getRow(i));
        }

        Stone_View stoneSelection_View;
        for (int i = 0; i < codeLength; i++)
        {
            stoneSelection_View = new StoneSelection_View(colorLength);
            horizontalBox_Selection.add(stoneSelection_View);
        }

        setState(mastermind.getState());
    }

    /**
     * Start new game.
     * <p>
     * will start a new Game with Parameters. it will call the constructor
     * {@link mastermind.Mastermind#Mastermind(Mastermind_View, int, int, int, State, State, int[])}
     * <p>
     * 
     * @param newColorLength
     *            the new color length
     * @param newRowLength
     *            the new row length
     * @param newCodeLength
     *            the new code length
     * @param state
     *            the state
     * @param secretCode
     *            the secret code
     * @see mastermind.Mastermind#Mastermind(Mastermind_View, int, int, int,
     *      State, State, int[])
     * @see #update()
     */
    public void startNewGame(int newColorLength, int newRowLength,
            int newCodeLength, State state)
    {
        // New Mastermind
        this.mastermind =
                new Mastermind(this, newCodeLength, newRowLength,
                        newColorLength, state);
        update();
    }

    /**
     * Display of it's not possible.
     */
    public void isNotPossible()
    {
        JOptionPane.showMessageDialog(null, "This combination is not Possible");
    }

    /**
     * Applying the settings to Display the right current State. As well as
     * displaying to the User if he did win or not.
     * 
     * @param state
     *            the new state
     * @see mastermind.Mastermind#getState()
     */
    public void setState(State state)
    {
        switch (state)
        {
        case setCode:
            jbutton_Add.setEnabled(true);
            jbutton_Add.setText("Set Code");
            jbutton_Add.setToolTipText("Choose your Code against the KI");
            jbutton_Hint.setEnabled(false);
            jLabel_statusBar.setText("Set your secret Code...");
            break;
        case playingHuman:
        case playingHumanHelp:
            jbutton_Add.setEnabled(true);
            jbutton_Add.setText("Add");
            jbutton_Add.setToolTipText("Add the choosen Code into the Field");
            jbutton_Hint.setText("Hint");
            jbutton_Hint.setEnabled(true);
            jbutton_Hint.setText("Hint");
            jbutton_Hint
                    .setToolTipText("This will give you a hint to find the Solution");
            jLabel_statusBar.setText("your turn...");
            break;
        case playingKI:
            jbutton_Add.setEnabled(false);
            jLabel_statusBar.setText("Ki playing now...");
            break;
        case checkPossible:
            jbutton_Add.setEnabled(false);
            jbutton_Hint.setEnabled(false);
            jLabel_statusBar.setText("Checking for possibilitys...");
            break;
        case stopCaculateKI:
        case stopCaculateHelpKI:
        case stopCheckPossible:
            jbutton_Add.setEnabled(false);
            jbutton_Hint.setEnabled(false);
            jLabel_statusBar.setText("stopping Ki...");
            break;
        case caculateKI:
        case caculateHelpKI:
            jbutton_Add.setEnabled(false);
            jbutton_Hint.setEnabled(true);
            jbutton_Hint.setText("Cancel");
            jbutton_Hint
                    .setToolTipText("Stop the Calculating if it lasts to long for you");
            jLabel_statusBar.setText("calculating...");
            break;
        default:
            verticalBox_Playfield.remove(0);
            jbutton_Add.setEnabled(false);
            jbutton_Hint.setEnabled(false);
            jbutton_Hint.setText("Hint");
            jbutton_Hint
                    .setToolTipText("This will give you a hint to find the Solution");
            horizontalBox_Hint.removeAll();
            for (int code : mastermind.getSecretCode())
            {
                horizontalBox_Hint.add(new Stone_View(code));
            }

            jLabel_Row.setText(verticalBox_Playfield.getComponentCount() + "/"
                    + rowLength);
            JOptionPane.showMessageDialog(this, (state == State.win ? "Win"
                    : "Lose"));

            jLabel_statusBar.setText("done...");
            break;
        }

        horizontalBox_Selection.validate();
        horizontalBox_Hint.validate();
        verticalBox_Playfield.validate();

        scrollPane_Selection.repaint();
        scrollPane_Hint.repaint();
        scrollPane_Playfield.repaint();

    }
}
