import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class StartMenu extends JFrame implements ActionListener {

    private final int width = 400;
    private final int height = 400;

    private final JButton newGame = new JButton("New Game");
    private final JButton continuee = new JButton("Continue");
    private final JButton exit = new JButton("Exit");
    private final CardLayout layout = new CardLayout();
    private final JButton[][] buttons = new JButton[6][6];
    private final Glass g = new Glass(buttons, width / 6, (height - 40)  / 6);
    private final JPanel panel = new JPanel();
    private final Tabla game = new Tabla(g, buttons, width, height);
    protected  JPanel menu = new JPanel();
    private final JMenu m;
    private final JMenuItem save, mainMenu, exitt;

    public StartMenu() {
        panel.setLayout(layout);
        addButtons();

        JMenuBar mb = new JMenuBar();
        m = new JMenu("Menu");
        save = new JMenuItem("Save");
        mainMenu = new JMenuItem("Main Menu");
        exitt = new JMenuItem("Exit");

        save.addActionListener(this);
        mainMenu.addActionListener(this);
        exitt.addActionListener(this);

        mb.add(m);
        mb.setSize(new Dimension(400,20));
        setJMenuBar(mb);

        setSize(width, height);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        setTitle("Masyu Game");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        requestFocus();

    }

    private void addButtons() {

        newGame.addActionListener(this);
        continuee.addActionListener(this);
        exit.addActionListener(this);

        menu.add(newGame);
        menu.add(continuee);
        menu.add(exit);

        game.setBackground(Color.PINK);
        menu.setBackground(Color.CYAN);

        panel.add(menu, "Menu");
        panel.add(game, "Game");

        add(panel);
        layout.show(panel, "Menu");

    }

    public void actionPerformed(ActionEvent event) {

        Object source = event.getSource();

        if (source == exit) {
            System.exit(0);
        } else if (source == newGame) {
            m.add(save);
            m.add(mainMenu);
            m.add(exitt);

            for(int i=0;i<6;i++) {
                for (int j = 0; j < 6; j++) {
                    buttons[i][j].setIcon(null);
                }
            }

            game.ujPalya(game.getRandom(game.z));
            g.vonalak.ujCellak(game.cellak);
            setGlassPane(g);
            g.setVisible(true);
            layout.show(panel, "Game");

        } else if (source == continuee) {
            m.add(save);
            m.add(mainMenu);
            m.add(exitt);

            game.load("palya.txt");
            g.vonalak.loadVonal("vonal.txt");
            setGlassPane(g);
            g.setVisible(true);
            layout.show(panel, "Game");
        }
        else if( source == save){
            game.saveTabla("palya.txt");
            g.vonalak.saveVonal("vonal.txt");
        }
        else if( source == exitt){
            System.exit(0);
        }
        else if( source == mainMenu){
            g.setVisible(false);

            layout.show(panel, "Menu");
        }
    }
}
