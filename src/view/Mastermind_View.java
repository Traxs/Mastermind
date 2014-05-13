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
import file.Mastermind_File;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class Mastermind_View extends JFrame
{
    private static final long serialVersionUID = -809208636941136548L;
    private Mastermind mastermind;
    private NewGame_View newGame_View = null;
    private int rowLength;
    private int codeLength;
    private int colorLength;
    private Box verticalBox_Playfield;
    private JScrollPane scrollPane_Playfield;
    private JScrollPane scrollPane_Selection;
    private JScrollPane scrollPane_Hint;
    private Box horizontalBox_SelectionStone;
    private Box horizontalBox_Hint;
    private JLabel jLabel_Row;
    private JButton jbutton_Hint;
    private JButton jbutton_Add;
    private int posX = 0, posY = 0;
    private static final String[] usedComponents = {"Menu","MenuBar", "MenuItem", "Panel", "RadioButton", "Slider", "Label", "ComboBox", "CheckBox", "Button"};
    public static final Color backgroundColor = new Color(42, 42, 42);
    private static final ImageIcon icon = new ImageIcon("MIcon");
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

    public Mastermind_View()
    {
        this.mastermind = new Mastermind(this);
        createView();
    }

    
    private void colorUI(Color colorBackground, Color colorForeground,Color colorBorder)
    {
    	for(int i = 0; i < usedComponents.length; i++)
    	{
    		UIManager.put(usedComponents[i] + ".background", colorBackground);
    		UIManager.put(usedComponents[i] + ".foreground", colorForeground);	
    	}
    	
    	UIManager.put("Menu.border", BorderFactory.createLineBorder(colorBorder));
    	UIManager.put("MenuItem.selectionBackground", Color.WHITE);
    	UIManager.put("MenuItem.selectionForeground", backgroundColor);
    	UIManager.put("OptionPane.background", backgroundColor);
    	UIManager.put("OptionPane.foreground", colorForeground);
    	UIManager.put("OptionPane.messageForeground", colorForeground);;
    	UIManager.put("ScrollBar.border", Color.DARK_GRAY);
    	UIManager.put("ScrollBar.darkShadow", Color.DARK_GRAY);
    	UIManager.put("ScrollBar.highlight", Color.DARK_GRAY);
    	UIManager.put("ScrollBar.thumb", Color.DARK_GRAY);
        UIManager.put("ScrollBar.thumbShadow", Color.DARK_GRAY);
        UIManager.put("ScrollBar.thumbDarkShadow", Color.DARK_GRAY);
        UIManager.put("ScrollBar.thumbLightShadow", Color.DARK_GRAY);
        UIManager.put("ScrollBar.shadow", Color.DARK_GRAY);
        UIManager.put("control", Color.DARK_GRAY);
    }
    
    
    public void createView()
    {
    	setIconImage(icon.getImage());
    	colorUI(backgroundColor,Color.WHITE,Color.BLACK);
    	JPanel contentPane = new JPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);

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
        horizontalBox_SelectionStone = Box.createHorizontalBox();
        createBox(scrollPane_Selection, horizontalBox_SelectionStone);
        contentPane.add(scrollPane_Selection);

        // *** Hint Box ***
        scrollPane_Hint = new JScrollPane();
        horizontalBox_Hint = Box.createHorizontalBox();
        createBox(scrollPane_Hint, horizontalBox_Hint);
        contentPane.add(scrollPane_Hint);
        	jbutton_Add = new JButton("Add");
            jbutton_Add.addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    if (!jbutton_Add.isEnabled())
                        return;

                    int[] newCode = new int[codeLength];
                    for (int i = 0; i < codeLength; i++)
                    {
                        newCode[i] =
                                ((Stone_View) horizontalBox_SelectionStone
                                        .getComponent(i)).getCode();
                    }

                    
                    if(jbutton_Add.getText() == "Set Code")
                    {
                    	startNewGame(newGame_View.getColorNumber(),
                            newGame_View.getRowNumber(),
                            newGame_View.getCodeNumber(),
                            newGame_View.getState(),
                            newCode);
                    	setState(State.codeSet);
                    }
                    else
                    {
	                    
	                    if (mastermind.getState() == State.playingHumanHelp)
	                    {
	                        mastermind.isPossible(newCode);
	                    }
	                    else
	                    {
	                        mastermind.addRow(newCode);
	                    }
                    }
                }
            });

        
                contentPane.add(jbutton_Add);

        jbutton_Hint = new JButton();
        jbutton_Hint.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if (!jbutton_Hint.isEnabled())
                    return;

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

        jLabel_Row = new JLabel("0/" + rowLength);
        contentPane.add(jLabel_Row);

        // *** JMenuBar ***
        {
            JMenuBar jMenuBar = new JMenuBar();
            jMenuBar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            jMenuBar.setBorderPainted(true);
            setJMenuBar(jMenuBar);

            JMenu jMenu_File = new JMenu("File");
            jMenu_File.setBorderPainted(false);
            jMenuBar.add(jMenu_File);

            JMenuItem jMenuItem_NewGame = new JMenuItem("New Game",Mastermind_File.loadIcon("new.png"));
            jMenuItem_NewGame.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent arg0)
                {
                    newGame_View = new NewGame_View();

                    if (JOptionPane.showConfirmDialog(null, newGame_View,
                            "New Game", JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION)
                    {
                    	if(newGame_View.getState() == State.playingKI)
                    	{
                    		setState(State.playingKI);
                    	}
                    	else
                    	{
                    		startNewGame(newGame_View.getColorNumber(),
                                    newGame_View.getRowNumber(),
                                    newGame_View.getCodeNumber(),
                                    newGame_View.getState(),
                                    null);
                    	}
                        
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

            JMenuItem jMenuItem_load =
                    new JMenuItem("Load", Mastermind_File.loadIcon("load.png"));
            jMenuItem_load.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent arg0)
                {
                    JFileChooser jFileChooser = new JFileChooser();
                    jFileChooser.setDialogType(JFileChooser.OPEN_DIALOG);

                    if (jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
                    {
                        try
                        {
                            mastermind.loadMastermind(jFileChooser
                                    .getSelectedFile());
                        }
                        catch (ClassNotFoundException e)
                        {
                            e.printStackTrace();
                            JOptionPane.showMessageDialog(null,
                                    "Class not found.");
                            return;
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                            JOptionPane.showMessageDialog(null,
                                    "Wrong or corrupted file.");
                            return;
                        }

                        update();
                    }
                }
            });
            jMenu_File.add(jMenuItem_load);
            
            JMenu jMenu_Help = new JMenu("Help");
            jMenu_Help.setBorderPainted(false);
            jMenuBar.add(jMenu_Help);

            JMenuItem jMenuItem_Help = new JMenuItem("Help",Mastermind_File.loadIcon("Help.png"));
            jMenuItem_Help.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent arg0)
                {
                	//LOAD BENUTZERHANDBUCH GENAU HIER !!
                }
            });
            jMenu_Help.add(jMenuItem_Help);

            JMenuItem jMenuItem_about = new JMenuItem("About",Mastermind_File.loadIcon("about.png"));
            jMenuItem_about.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent arg0)
                {
                	JOptionPane.showMessageDialog(null, "Version 1.0.0 \u00A9 by \n- Birk Kauer\n- Raphael Pavlidis\n- Nico\n- Bettina");
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

        update();
        setTitle("Mastermind");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        setVisible(true);
    }
    
    

    private void createBox(JScrollPane jScrollPane, Box box)
    {
        jScrollPane.setBorder(BorderFactory.createLineBorder(Color.black));
        box.setOpaque(true);
        box.setBackground(backgroundColor);
        jScrollPane.setViewportView(box);
    }

    

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
        scrollPane_Playfield.updateUI();
    }

    public void addHint(int[] code)
    {
        for (int i = 0; i < code.length; i++)
        {
            ((StoneSelection_View)horizontalBox_SelectionStone.
                    getComponent(i)).setCode(code[i]);
        }
    }

    // Zum Updaten nach einem Laden
    public void update()
    {
        verticalBox_Playfield.removeAll();
        horizontalBox_Hint.removeAll();
        horizontalBox_SelectionStone.removeAll();

        rowLength = mastermind.getRowLength();
        codeLength = mastermind.getCodeLength();
        colorLength = mastermind.getColorLength();

        // Berechnung der Gr��en der Fensterinhalte dynamisch anhand der
        // Codel�nge
        int rowSize =
                Stone_View.STONESIZE
                        * codeLength
                        + (Result_View.RESULTSIZE * (codeLength % 2 == 0
                                ? codeLength / 2 : codeLength / 2 + 1)) + 30;

        // Setze Fenstergr��e
        setSize(rowSize + 135, 620);
        scrollPane_Playfield.setBounds(12, 12, rowSize, 410);
        scrollPane_Selection.setBounds(12, 434, rowSize, 58);
        scrollPane_Hint.setBounds(12, 500, rowSize, 58);

        jbutton_Add.setBounds(rowSize + 24, 434, 98, 25);
        jbutton_Hint.setBounds(rowSize + 24, 397, 98, 25);
        jLabel_Row.setBounds(rowSize + 24, 12, 80, 30);

        jbutton_Add.setEnabled(true);
        jbutton_Hint.setEnabled(true);
        jLabel_Row.setText(0 + "/" + rowLength);
        jLabel_Row.setVisible(rowLength != -1);

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
            horizontalBox_SelectionStone.add(stoneSelection_View);
        }

        setState(mastermind.getState());

        scrollPane_Selection.updateUI();
        scrollPane_Hint.updateUI();
        scrollPane_Playfield.updateUI();
    }

    // Wird aufgerufen wenn der Benutzer w�hrend eines laufenden Spiels ein
    // neues Spiel startet
    public void startNewGame(int newColorLength, int newRowLength,
            int newCodeLength, State state, int[] secretCode)
    {
        // Neues Mastermind
        this.mastermind =
                new Mastermind(this, newCodeLength, newRowLength,
                        newColorLength, state, secretCode);
        update();
    }

    public void isNotPossible()
    {
        JOptionPane.showMessageDialog(null, "This combination is not Possible");
    }

    public void setState(State state)
    {
        switch (state)
        {
        case codeSet:
        	horizontalBox_Hint.removeAll();
            for(int code : mastermind.getSecretCode())
            {
                horizontalBox_Hint.add(new Stone_View(code));
            }
            horizontalBox_SelectionStone.removeAll();
            jbutton_Add.setEnabled(false);
            break;
        case playingHuman:
        	jbutton_Add.setText("Add");
        	jbutton_Hint.setEnabled(true);
        	jbutton_Add.setEnabled(true);
        case playingHumanHelp:
            jbutton_Add.setEnabled(true);
            jbutton_Hint.setEnabled(true);
            jbutton_Hint.setText("Hint");
            break;
        case playingKI:
        	jbutton_Add.setEnabled(true);
        	jbutton_Hint.setEnabled(false);
        	jbutton_Add.setText("Set Code");
        	break;
        case checkPossible:
            jbutton_Add.setEnabled(false);
            jbutton_Hint.setEnabled(false);
            break;
        case caculateKI:
        case caculateHelpKI:
            jbutton_Add.setEnabled(false);
            jbutton_Hint.setEnabled(true);
            jbutton_Hint.setText("Cancel");
            break;
        default:
            verticalBox_Playfield.remove(0);
            jbutton_Add.setEnabled(false);
            jbutton_Hint.setEnabled(false);
            jbutton_Hint.setText("Hint");

            horizontalBox_Hint.removeAll();
            for(int code : mastermind.getSecretCode())
            {
                horizontalBox_Hint.add(new Stone_View(code));
            }

            jLabel_Row.setText(verticalBox_Playfield.getComponentCount() + "/"
                    + rowLength);
            
            JOptionPane.showMessageDialog(this, (state == State.win ? "Win"
                    : "Lose"));
            break;
        }
        
        scrollPane_Selection.updateUI();
        scrollPane_Hint.updateUI();
        scrollPane_Playfield.updateUI();
    }
}
